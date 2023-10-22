package qa.core.api.restclient;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

public class Request {
    private final RequestSpecification requestSpec;
    private HashMap<String,String> queryParam;

    public Request(String baseUri,String basePath){
        requestSpec = RestAssured.given();
        requestSpec.baseUri(baseUri);
        requestSpec.basePath(basePath);
    }

    public void setQueryParam(String queryKey, String queryValue){
        if(queryParam == null){
            queryParam = new HashMap<>();
        }
        queryParam.put(queryKey,queryValue);
    }

    public RequestSpecification createRequest(){
        if(queryParam != null){
            requestSpec.queryParams(queryParam);
        }
        return requestSpec;
    }
}
