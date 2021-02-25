package fagprojekt;// package fagprojekt;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJson {

    public static void main(String[] args) {
    	TimeSeries ts = new TimeSeries("/home/markus/Documents/DTU/semester-2-f21/02122-software-technology-project/OneDim-set01/OneDim/1Breaks_1K.json");
    	System.out.println(ts.getT());
    	System.out.println(ts.getValues(9));
    }

}
