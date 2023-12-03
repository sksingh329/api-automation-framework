package qa.core.utils.testdata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import qa.core.exceptions.FrameworkException;
import qa.core.utils.files.FileUtils;


import java.io.File;
import java.util.HashMap;
import java.util.List;

public class JsonDataReader {
    private JsonDataReader() {}
    public static List<HashMap<String, String>> readJson(File filePath)  {
        String jsonContent = FileUtils.readJsonStringFromFile(filePath);

        ObjectMapper mapper = new ObjectMapper();
        try{
            return mapper.readValue(jsonContent, new TypeReference<>() {});
        }
        catch(JsonProcessingException ex){
            throw new FrameworkException("Error occurred while mapping json to HashMap " + ex);
        }

    }
}
