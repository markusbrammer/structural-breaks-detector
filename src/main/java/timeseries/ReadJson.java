package timeseries;// package fagprojekt;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.stage.Stage;

public class ReadJson extends Application {
	private TimeSeries ts = new TimeSeries("./OMX-logReturn.json");
	
	/*
    public static void main(String[] args) {
    	TimeSeries ts = new TimeSeries("/home/markus/Documents/DTU/semester-2-f21/02122-software-technology-project/OneDim-set01/OneDim/1Breaks_1K.json");
    	System.out.println(ts.getT());
    	System.out.println(ts.getValues(9));
    }
   */
	
	
	@Override
	public void start(Stage stage) {
		
		stage.setTitle("Time series line chart example");
		LineChart<Integer,Double> lineChart = ts.drawGraph();
		
		Scene scene  = new Scene(lineChart,800,600);
       
        stage.setScene(scene);
        stage.show();
		
	}
	
	public static void main(String[] args) {
        launch(args);
    }

}
