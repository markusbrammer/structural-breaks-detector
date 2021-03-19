package fagprojekt;
// example usage

import java.util.Arrays;

import timeseries.TimeSeries;

public class TestClass {
	public static void main(String[] args) {
		TimeSeries ts = new TimeSeries("./1Breaks_1K.json");
		Algorithm alg = new Algorithm(ts,4);
		Solution bestSolution = alg.findBreakPoints(null);
		System.out.println(Arrays.toString(bestSolution.returnBreakpoints()));
	}
}