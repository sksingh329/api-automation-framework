package qa.core.api.restclient;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;

public class ResponseBodyParser {
    private JsonPath jsonPath;

    public ResponseBodyParser(Response response){
        jsonPath = response.jsonPath();
    }
    public void setRootPath(String rootPath){
        jsonPath.setRootPath(rootPath);
    }
    public void resetRootPath(){
        jsonPath.setRootPath("");
    }
    public <T>T get(String path){
        return jsonPath.get(path);
    }
    public <T> List<T> getList(String path){
        return jsonPath.getList(path);
    }

}
