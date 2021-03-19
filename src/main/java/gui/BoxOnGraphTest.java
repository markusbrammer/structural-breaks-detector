package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.stage.Stage;
import timeseries.TimeSeries;

public class BoxOnGraphTest extends Application {
	
	private TimeSeries ts = new TimeSeries("./1Breaks_1K.json");
	
	@Override
	public void start(Stage stage) {
		
		stage.setTitle("Time series line chart example");
		// LineChart<Integer,Double> lineChart = ts.drawGraph();
		DataGraph dataGraph = new DataGraph(ts);
		// dataGraph.getChildren().add(lineChart);
		
		Scene scene  = new Scene(dataGraph,800,600);
       
        stage.setScene(scene);
        stage.show();
		
	}
	
	public static void main(String[] args) {
        launch(args);
    }

}
