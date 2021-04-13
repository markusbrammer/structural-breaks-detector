package data;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonDataFileReader {

    private JSONParser parser;
    private FileReader reader;
    private JSONObject jsonFile;

    public JsonDataFileReader(String pathToJsonFile) {

        try  {
            parser = new JSONParser();
            reader = new FileReader(pathToJsonFile);
            jsonFile = (JSONObject) parser.parse(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public Object get(String objectKey) {
        return jsonFile.get(objectKey);
    }

}
