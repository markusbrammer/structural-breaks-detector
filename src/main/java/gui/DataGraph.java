package gui;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import timeseries.TimeSeries;

public class DataGraph extends StackPane {
	
	private TimeSeries timeSeries;
	private LineChart lineChart;
	
	public DataGraph(TimeSeries timeSeries) {
		this.timeSeries = timeSeries;
		this.lineChart = getTimeSeriesGraph();
		this.getChildren().add(lineChart);
	}
	
	// TODO Method to draw rectangles from two points 
	
	// TODO Method to make data load fast by showing data points based on 
	// screen resolution
	
	private LineChart getTimeSeriesGraph() {
		/*
		 * Generate line chart from time series data for JavaFX. 
		 * 
		 * Made by: Markus B. Jensen (s183816)
		 */
		
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Time");
		yAxis.setLabel("Value");
		
		LineChart<Integer,Double> lineChart = new LineChart(xAxis,yAxis);
		
		// TODO Rename to dataPoints?
		XYChart.Series<Integer, Double> series = readTimeSeriesPoints();
		
		lineChart.getData().add(series);
		return lineChart;
	}
	
	private XYChart.Series<Integer, Double> readTimeSeriesPoints() {
		/* 
		 * Read all points from the time series into a XYChart object. Add time 
		 * series name as legend name for data points. 
		 * 
		 * Important: This can only be called, when the TimeSeries field for a 
		 * DataGraph is not null. 
		 * 
		 * Made by: Markus B. Jensen (s183816)
		 */
		
		XYChart.Series<Integer, Double> graphPoints = new XYChart.Series<>();
		
		int[] timeSeriesTimes = timeSeries.getTime();
		int noOfElementsInTimeSeries = timeSeries.getT();
		for (int i = 0; i < noOfElementsInTimeSeries; i++) {
			int x = timeSeriesTimes[i];
			double y = timeSeries.getValues(i);
			graphPoints.getData().add(new XYChart.Data<Integer,Double>(x, y));
		}
		
		String timeSeriesName = timeSeries.getName();
		graphPoints.setName(timeSeriesName);
		return graphPoints;
		
	}
}
