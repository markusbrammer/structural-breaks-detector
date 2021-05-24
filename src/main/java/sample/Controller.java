package sample;

import bp.Statics;
import data.TimeSeries;
import fitness.FitnessRectangle;
import ga.Individual;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.UnaryOperator;

public class Controller {

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addObserver(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /* ====================================================================== */
    /* FXML elements                                                          */
    /* ====================================================================== */

    @FXML private AnchorPane anchorPaneRoot;

    /* ----- Left Button-Menu ----- */

    @FXML private AnchorPane leftMenuPane;
    @FXML private Button loadTimeSeriesSmallBtn;
    @FXML private Button btnSettingsPane;
    @FXML private Button runSmallBtn;


    /* ----- Settings Pane ----- */

    @FXML private ScrollPane scrollPaneSettings;
    @FXML private Text currentDataFile;
    @FXML private ChoiceBox<String> fitnessMethodChooser;
    @FXML private Button runAlgorithmBtn;

    // Sliders
    @FXML private Slider popSz;
    @FXML private Slider maxBPSlider;
    @FXML private Slider mutationProbInput;
    @FXML private Slider onePointCrossInput;
    @FXML private Slider uniCrossInput;
    @FXML private Slider alphaInput;

    // Text fields showing the values of their respective sliders
    @FXML private Text popSizeVal;
    @FXML private Text maxBPVal;
    @FXML private Text mutationProbVal;
    @FXML private Text onePointCrossVal;
    @FXML private Text uniCrossVal;
    @FXML private Text alphaVal;


    /* ----- Line Chart / Data Graph ----- */

    @FXML private LineChart graphPlaceHolder;


    /* ====================================================================== */
    /* Other Controller Class Fields                                          */
    /* ====================================================================== */

    private File dataFile;
    private FileChooser fileChooser;

    // The actual Object which will show the time series and fitness
    private DataGraph<Number, Number> dataGraph;

    @FXML
    public void initialize() {

        // Hide settings panel on launch
        scrollPaneSettings.setVisible(false);
        scrollPaneSettings.setManaged(false);

        // Replace "Place holder graph" with actual Data Graph Object
        dataGraph = new DataGraph<>(new NumberAxis(), new NumberAxis());
        AnchorPane.setLeftAnchor(dataGraph, AnchorPane.getLeftAnchor(graphPlaceHolder));
        AnchorPane.setTopAnchor(dataGraph, 0.);
        AnchorPane.setBottomAnchor(dataGraph, 0.);
        AnchorPane.setRightAnchor(dataGraph, 0.);
        anchorPaneRoot.getChildren().add(dataGraph);
        anchorPaneRoot.getChildren().remove(graphPlaceHolder);

        // Add fitness methods to drop-down menu.
        fitnessMethodChooser.getItems().add("Rectangle");

        // Add tooltips to buttons in the Left Menu.
        loadTimeSeriesSmallBtn.setTooltip(new Tooltip("Load time series data file"));
        btnSettingsPane.setTooltip(new Tooltip("Parameter settings (open/close)"));
        runSmallBtn.setTooltip(new Tooltip("Run algorithm"));


        addListenerIntSlider(popSz, popSizeVal);
        addListenerIntSlider(maxBPSlider, maxBPVal);

        mutationProbInput.valueProperty().addListener((obs, oldVal, newVal) -> {
            mutationProbVal.setText(newVal.intValue() + "%");
            int diff = 100 - newVal.intValue();
            int newOp = (int) Math.ceil(diff / 2.);
            int newUni = diff / 2;
            onePointCrossInput.adjustValue(newOp);
            uniCrossInput.adjustValue(newUni);
            onePointCrossInput.setDisable(diff == 0);
        });

        onePointCrossInput.valueProperty().addListener((obs, oldVal, newVal) -> {
            onePointCrossVal.setText(newVal.intValue() + "%");
            int diff = 100 - ((int) mutationProbInput.getValue()) - newVal.intValue();
            int maxValue = (int) (100 - mutationProbInput.getValue());
            onePointCrossInput.adjustValue(diff >= 0 ? newVal.intValue() : maxValue);
            uniCrossInput.adjustValue(Math.max(diff, 0));
        });

        uniCrossInput.valueProperty().addListener((obs, oldVal, newVal) -> {
            uniCrossVal.setText(newVal.intValue() + "%");
        });

        uniCrossInput.setDisable(true);

        alphaInput.valueProperty().addListener((obs, oldVal, newVal) ->
            alphaVal.setText(String.format(Locale.US, "%.2f", newVal)));


    }

    private void addListenerIntSlider(Slider slider, Text text) {
        slider.valueProperty().addListener((obs, oldVal, newVal) ->
                text.setText(String.valueOf(newVal.intValue())));
    }

    @FXML
    public void openFileChooser(MouseEvent mouseEvent) {
        if (fileChooser == null)
            fileChooser = new FileChooser();

        fileChooser.setTitle("Select time series data file");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON", "*.json")
        );
        File tempDataFile = fileChooser.showOpenDialog(Main.getPrimaryStage());
        if (tempDataFile != null) {
            dataFile = tempDataFile;
            support.firePropertyChange("dataFile", null, dataFile);
            displayTimeSeries();
        }
    }

    @FXML
    public void runAlgorithm(MouseEvent mouseEvent) {
        /**
         * When pressing a button, the algorithm must run.
         */

        dataGraph.clearFitnessMarkers();
        support.firePropertyChange("runAlgorithm", null, null);

    }

    /**
     * Help from here: https://stackoverflow.com/questions/28558165/javafx-setvisible-hides-the-element-but-doesnt-rearrange-adjacent-nodes
     * @param mouseEvent
     */
    @FXML
    public void toggleSettingsMenu(MouseEvent mouseEvent) throws InterruptedException {

        // Hide or show Settings Panel depending on current visibility
        boolean toggleBool = !scrollPaneSettings.isVisible();
        scrollPaneSettings.setVisible(toggleBool);
        scrollPaneSettings.setManaged(toggleBool);
        scrollPaneSettings.toFront();

        // Match left edge of data graph to the left edge of the program
        double chartAnchor = leftMenuPane.getWidth();
        if (toggleBool)
            chartAnchor += scrollPaneSettings.getPrefWidth();
        AnchorPane.setLeftAnchor(dataGraph, chartAnchor);

    }



    private void assignDoubleFilter(TextField textField, double initValue) {
        String regex = "[0]?\\.[0-9]*";
        UnaryOperator<TextFormatter.Change> doubleFilter = change -> {
            String newText = change.getControlNewText();

            if (newText.matches(regex))
                return change;

            return null;
        };

        textField.setTextFormatter(new TextFormatter<Double>(
                new DoubleStringConverter(), initValue, doubleFilter
        ));
    }

    private void assignIntegerFilter(TextField textField, String regex, int initValue) {

        // TODO Taken from StackOverflow, link in Notion
        // TODO Make custom IntegerFilter class that extends UnaryOperator or something
        // Add to textformatter - only allows integers
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches(regex)) {
                return change;
            }
            return null;
        };

        textField.setTextFormatter(new TextFormatter<Integer>(
                new IntegerStringConverter(), initValue, integerFilter
        ));

        addErrorListener("[^1-9]*", textField);

    }

    private void addErrorListener(String regex, TextField textField) {

        // Source: https://edencoding.com/javafx-textfield/
        PseudoClass errorClass = PseudoClass.getPseudoClass("error");
        textField.textProperty().addListener(event -> {
            boolean matchesRegex = textField.getText().matches(regex);
            textField.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"),
                    matchesRegex);
            runAlgorithmBtn.setDisable(matchesRegex);
        });

    }



    public String getDataFilePath() {
        if (dataFile != null) {
            return dataFile.getAbsolutePath();
        } else {
            return "No data file loaded.";
        }
    }

    public int getPopulationSize() {
        return 50;
    }

    public int getMaxNoOfBreakPoints() {
        return 3;
    }

    public double getAlphaParameter() {
        return 0.25;
    }

    public double getUniformCrossoverProb() {
        return 0.3;
    }

    public double getOnePointCrossoverProb() {
        return 0.3;
    }

    public double getMutationProb() {
        return 0.4;
    }


    private void displayTimeSeries() {

        // How to find nodes (in this case the line chart with fx:id "tsgraph"
        // ((LineChart<Double, Double>) Main.getPrimaryStage().getScene().lookup("#tsgraph"))

        TimeSeries timeSeries = new TimeSeries(dataFile.getAbsolutePath());
        dataGraph.getData().clear();
        dataGraph.clearFitnessMarkers();
        dataGraph.setTimeSeries(timeSeries);
        currentDataFile.setText("Current: " + dataFile.getName());

    }

    public void showFitness(Individual individual, TimeSeries timeSeries) {

        List<Integer> breakPointIndexes = findBreakPointIndexes(individual);
        for (int i = 0; i < breakPointIndexes.size() - 1; i++) {
            int startIndex = 1 + breakPointIndexes.get(i);
            int endIndex = breakPointIndexes.get(i + 1);

            double[] values = timeSeries.getObservations()[1];

            double[] minAndMaxValues = getMinAndMaxInInterval(values, startIndex, endIndex);
            double minValue = minAndMaxValues[0];
            double maxValue = minAndMaxValues[1];

            FitnessRectangle fitnessRectangle = new FitnessRectangle(startIndex, endIndex, minValue, maxValue);
            Rectangle rectangle = new Rectangle();
            rectangle.setId("fitness-box");
            dataGraph.addRectangle(fitnessRectangle.getxMin(), fitnessRectangle.getxMax(), fitnessRectangle.getyMin()
                    , fitnessRectangle.getyMax());

        }

    }

    private static ArrayList<Integer> findBreakPointIndexes(Individual individual) {
        ArrayList<Integer> breakPointIndexes = new ArrayList<>();
        for (int i = 0; i < individual.getNoOfGenes(); i++) {
            char allele = individual.getAllele(i);
            if (allele == Statics.breakPointAllele)
                breakPointIndexes.add(i);
        }
        return breakPointIndexes;
    }

    private static double[] getMinAndMaxInInterval(double[] array, int lowerBound, int upperBound) {
        double min = array[lowerBound];
        double max = array[upperBound];
        for (int i = lowerBound; i <= upperBound; i++) {
            double value = array[i];
            if (value < min) {
                min = value;
            } else if (value > max) {
                max = value;
            }
        }
        return new double[] {min, max};
    }


}
