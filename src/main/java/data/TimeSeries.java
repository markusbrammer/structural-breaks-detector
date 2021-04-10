package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import bp.Rectangle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TimeSeries {
    private double[] time;
    private double[] values;
    private int length;
    private String name;
    private double rectangleArea;


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
            this.length = observations.size();
            this.time = new double[this.length];
            this.values = new double[this.length];
            readObservations(observations);
            rectangleArea = Rectangle.getTimeSeriesGraphRectangleArea(this);

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
        for (int i = 0; i < this.length; i++) {

            JSONObject observation = (JSONObject) observations.get(i);

            double time = (double) observation.get("time");

            // Values are (in the data file) stored in array to compensate for
            // dimensions. Here, only one dimension is allowed.
            JSONArray valuesArray = (JSONArray) observation.get("values");
            double value = (double) valuesArray.get(0);

            this.time[i] = time;
            this.values[i] = value;
        }
    }

    public double[] getTime() {
        return time;
    }

    public double getValueAtIndex(int i) {
        return values[i];
    }

    public double[] getValues() {
        return values;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public double getRectangleArea() {
        return rectangleArea;
    }
}
