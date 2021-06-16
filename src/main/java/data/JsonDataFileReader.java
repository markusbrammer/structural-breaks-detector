package data;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

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

        // File size must not be larger than 120 MB
        long fileSzInBytes = Files.size(Paths.get(pathToJsonFile));
        long fileSzInMB = fileSzInBytes / (1000 * 1024);
        System.out.println(fileSzInMB);
        if (fileSzInMB > 120)
            throw new Exception("Error: File size must not exceed 120 MB");

        jsonFile = (JSONObject) parser.parse(reader);
    }

    public Object get(String objectKey) {
        return jsonFile.get(objectKey);
    }

}
