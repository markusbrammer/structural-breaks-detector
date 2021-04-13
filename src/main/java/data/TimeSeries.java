package data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TimeSeries {

    private JsonDataFileReader fileReader;
    private int noOfObservations;
    private int noOfDimensions;
    private double[][] observations;
    private String name;


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

        fileReader = new JsonDataFileReader(filePath);

        // Number of dimensions is the amount of values for each time stamp.
        // For some reason, the value cannot be converted to int right away
        noOfDimensions = (int) ((long) fileReader.get("dimension"));

        name = (String) fileReader.get("timeseries");
        readObservations();


    }

    private void readObservations() {

        JSONArray jsonObservations = (JSONArray) fileReader.get("observations");
        noOfObservations = jsonObservations.size();
        observations = new double[noOfDimensions + 1][noOfObservations];
        for (int i = 0; i < noOfObservations; i++) {

            JSONObject jsonObservation = (JSONObject) jsonObservations.get(i);

            // Read time into 0th row of observations array
            observations[0][i] = (double) jsonObservation.get("time");

            // Read corresponding values into following rows
            JSONArray values = (JSONArray) jsonObservation.get("values");
            for (int j = 0; j < noOfDimensions; j++)
                observations[j + 1][i] = (double) values.get(j);

        }
    }

    public double[][] getObservations() {
        return observations;
    }

    public int getLength() {
        return noOfObservations;
    }

    public int getNoOfDimensions() {
        return noOfDimensions;
    }

    public String getName() {
        return name;
    }

    public double[] getTimes() {
        return observations[0];
    }

}
