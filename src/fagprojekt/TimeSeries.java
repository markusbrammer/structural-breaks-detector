package fagprojekt;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class TimeSeries {
	private int[] time;
	private double[] values;
	private int T;
	private String name;
	private String path;
	
	public TimeSeries(int[] time, double[] values) {
		if (time.length != values.length) {
			throw new IllegalArgumentException("Error!");
		}
		this.time = time;
		this.values = values;
		this.T =  time.length;
	}
	
	public TimeSeries(String filePath) {
		/*
		 * TimeSeries constructed from a data file with a given file path. 
		 * Data file must have same structure as example files given for the 
		 * project.  
		 * 
		 * Inspiration and code snippets from:
		 * https://howtodoinjava.com/java/library/json-simple-read-write-json-examples/
		 * 
		 * Made by: Markus B. Jensen (s183816)
		 */
		
		JSONParser jsonParser = new JSONParser();
		try  {
			
			FileReader reader = new FileReader(filePath);
			JSONObject jsonFile = (JSONObject) jsonParser.parse(reader);
			
			String timeSeriesName = (String) jsonFile.get("timeseres");
			this.name = timeSeriesName;
			
			// TODO Use dimensions to actually read dimensions
			// ? Do dimensions actually matter or is there always one? 
			//   ^ In any case: Check and throw error if != 1 
			int noOfDimensions = (int) ((long) jsonFile.get("dimension"));
			
			JSONArray observations = (JSONArray) jsonFile.get("observations");
			this.T = observations.size();
			this.time = new int[this.T];
			this.values = new double[this.T];
			readObservations(observations);
				
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	private void readObservations(JSONArray observations) {
		/*
		 * Read observations into TimeSeries arrays from JSON file. 
		 * Input JSONArray must be the 'observations' list from JSON data file.
		 * Stores values in TimeSeries fields.
		 * 
		 * Made by: Markus B. Jensen (s183816)
		 */
		for (int i = 0; i < this.T; i++) {
			
			JSONObject observation = (JSONObject) observations.get(i);
			
			double time = (double) observation.get("time");
			
			// Values are (in the data file) stored in array to compensate for
			// dimensions. Here, only one dimension is allowed. 
			JSONArray valuesArray = (JSONArray) observation.get("values");
			double value = (double) valuesArray.get(0);

			this.time[i] = (int) time;
			this.values[i] = value;
		}
	}

	public int[] getTime() {
		return time;
	}

	public double getValues(int i) {
		return values[i];
	}

	public int getT() {
		return T;
	}
	
	public LineChart<Integer, Double> drawGraph() {
		/*
		 * Generate line chart from time series data for JavaFX. 
		 * 
		 * Made by: Markus B. Jensen (s183816)
		 */
		
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Time");
		yAxis.setLabel("Value");
		
		LineChart<Integer,Double> lineChart = 
				new LineChart(xAxis,yAxis);
		
		XYChart.Series<Integer, Double> series = new XYChart.Series<>();
		series.setName(this.name);
		for (int i = 0; i < this.T; i++) {
			int x = time[i];
			double y = values[i];
			series.getData().add(new XYChart.Data<Integer,Double>(x, y));
		}
		
		lineChart.getData().add(series);
		return lineChart;
		
	}
	
	
}
