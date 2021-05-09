package sample;

import bp.Statics;
import data.TimeSeries;
import fitness.FitnessRectangle;
import ga.Individual;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class Controller {

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addObserver(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    private File dataFile;
    private FileChooser fileChooser;

    private Stage primaryStage = Main.getPrimaryStage();

    private DataGraph<Number, Number> dataGraph;

    private boolean settingsShows = false;

    @FXML private AnchorPane anchorPaneRoot;

    @FXML private LineChart<Number, Number> timeSeriesGraph;
    @FXML private Text currentDataFile;

    @FXML private ScrollPane scrollPaneSettings;

    @FXML private TextField populationSizeInput;
    @FXML private TextField maxNoOfBreakPoints;
    @FXML private TextField alphaParameter;
    @FXML private TextField uniformCrossoverProb;
    @FXML private TextField onePointCrossoverProb;
    @FXML private TextField mutationProb;

    @FXML private Button runAlgorithmBtn;

    @FXML private LineChart graphPlaceHolder;


    @FXML
    public void initialize() {


        scrollPaneSettings.setVisible(false);
        scrollPaneSettings.setManaged(false);

        // Initialises nodes in the FXML file.
        currentDataFile.setText("No file loaded.");
        // populationSizeInput.setTextFormatter(intFormatter);
        initTextFields();



        dataGraph = new DataGraph<>(new NumberAxis(), new NumberAxis());
        AnchorPane.setLeftAnchor(dataGraph, AnchorPane.getLeftAnchor(graphPlaceHolder));
        AnchorPane.setTopAnchor(dataGraph, 0.);
        AnchorPane.setBottomAnchor(dataGraph, 0.);
        AnchorPane.setRightAnchor(dataGraph, 0.);
        anchorPaneRoot.getChildren().add(dataGraph);
        anchorPaneRoot.getChildren().remove(graphPlaceHolder);


    }

    @FXML
    public void openFileChooser(MouseEvent mouseEvent) {
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
    public void toggleSettingsMenu(MouseEvent mouseEvent) {

        boolean toggleBool;
        if (!settingsShows) {
            toggleBool = true;
        } else {
            toggleBool = false;
        }

        // Hide or shown settings depending on current visibility
        scrollPaneSettings.setVisible(toggleBool);
        scrollPaneSettings.setManaged(toggleBool);
        scrollPaneSettings.toFront();
        settingsShows = toggleBool;
    }


    private void initTextFields() {

        assignIntegerFilter(populationSizeInput, "[1-9]?[0-9]{0,2}", 50);
        assignIntegerFilter(maxNoOfBreakPoints, "[1-9]?[0-9]{0,1}", 3);

        assignDoubleFilter(alphaParameter, 0.25);

        assignDoubleFilter(uniformCrossoverProb, 0.3);
        assignDoubleFilter(onePointCrossoverProb, 0.3);
        assignDoubleFilter(mutationProb, 0.4);

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
        return Integer.parseInt(populationSizeInput.getText());
    }

    public int getMaxNoOfBreakPoints() {
        return Integer.parseInt(maxNoOfBreakPoints.getText());
    }

    public double getAlphaParameter() {
        return Double.parseDouble(alphaParameter.getText());
    }

    public double getUniformCrossoverProb() {
        return Double.parseDouble(uniformCrossoverProb.getText());
    }

    public double getOnePointCrossoverProb() {
        return Double.parseDouble(onePointCrossoverProb.getText());
    }

    public double getMutationProb() {
        return Double.parseDouble(mutationProb.getText());
    }


    private void displayTimeSeries() {

        // How to find nodes (in this case the line chart with fx:id "tsgraph"
        // ((LineChart<Double, Double>) Main.getPrimaryStage().getScene().lookup("#tsgraph"))

        TimeSeries timeSeries = new TimeSeries(dataFile.getAbsolutePath());
//        XYChart.Series<Number, Number> coordinates = readTimeSeriesPoints(timeSeries);
//        timeSeriesGraph.getData().add(coordinates);
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

//    // public Node getRoot() {
//        return borderPane;
//    }

}
