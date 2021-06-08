package data;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

/**
 * A class for reading data files using the JSON-Simple package.
 * Help from here:
 * https://howtodoinjava.com/java/library/json-simple-read-write-json-examples/
 */
public class JsonDataFileReader {

    private JSONParser parser;
    private FileReader reader;
    private JSONObject jsonFile;

    public JsonDataFileReader(String pathToJsonFile) throws Exception {

        parser = new JSONParser();
        reader = new FileReader(pathToJsonFile);
        jsonFile = (JSONObject) parser.parse(reader);

    }

    public Object get(String objectKey) {
        return jsonFile.get(objectKey);
    }

}
