package sample;

import data.TimeSeries;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.function.UnaryOperator;

public class Controller {

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addObserver(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    private File dataFile;
    private FileChooser fileChooser;


    @FXML private LineChart<Double, Double> timeSeriesGraph;
    @FXML private Text currentDataFile;
    @FXML private TextField populationSizeInput;
    @FXML private TextField maxNoOfBreakPoints;
    @FXML private TextField alphaParameter;
    @FXML private TextField uniformCrossoverProb;
    @FXML private TextField onePointCrossoverProb;
    @FXML private TextField mutationProb;
    @FXML private Button runAlgorithmBtn;


    @FXML
    public void initialize() {
        // Initialises nodes in the FXML file.
        currentDataFile.setText("No file loaded.");
        // populationSizeInput.setTextFormatter(intFormatter);

        assignIntegerFilter(populationSizeInput, "[1-9]?[0-9]{0,2}", 50);
        assignIntegerFilter(maxNoOfBreakPoints, "[1-9]?[0-9]{0,1}", 3);

        maxNoOfBreakPoints.textProperty().addListener((support, oldValue, newValue) -> {
            System.out.println("Textfield changed from " + oldValue + " to " + newValue);
        });
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

        // TODO / idea: Send update as hashmap in stead of like this.
        // HashMap<String, Number> ...

        support.firePropertyChange("populationSize", null, null);
        support.firePropertyChange("maxNoOfBreakPoints", null, null);
        support.firePropertyChange("alphaParameter", null, null);
        support.firePropertyChange("uniformCrossoverProb", null, null);
        support.firePropertyChange("onePointCrossoverProb", null, null);
        support.firePropertyChange("mutationProb", null, null);
        support.firePropertyChange("run", null, null);

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
//            runAlgorithmBtn.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"),
//                    textField.getText().matches("[^1-9]*"));

            // TODO FIX - this does not work! If two fields are wrong, correcting one enables button
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
        XYChart.Series<Double, Double> coordinates = readTimeSeriesPoints(timeSeries);
        timeSeriesGraph.getData().add(coordinates);
        currentDataFile.setText("Current: " + dataFile.getName());

    }

    private XYChart.Series<Double, Double> readTimeSeriesPoints(TimeSeries timeSeries) {
        // TODO: From DataGraph.java, temporary fix
        /*
         * Read all points from the time series into a XYChart object. Add time
         * series name as legend name for data points.
         *
         * Important: This can only be called, when the TimeSeries field for a
         * DataGraph is not null.
         *
         * Made by: Markus B. Jensen (s183816)
         */

        XYChart.Series<Double, Double> graphPoints = new XYChart.Series<>();

        double[] timeSeriesTimes = timeSeries.getTimes();
        int noOfElementsInTimeSeries = timeSeries.getLength();
        for (int i = 0; i < noOfElementsInTimeSeries; i++) {
            double x = timeSeriesTimes[i];
            double y = timeSeries.getObservations()[1][i];
            graphPoints.getData().add(new XYChart.Data<Double,Double>(x, y));
        }

        String timeSeriesName = timeSeries.getName();
        graphPoints.setName(timeSeriesName);
        return graphPoints;

    }
}
