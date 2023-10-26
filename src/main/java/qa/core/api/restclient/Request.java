package qa.core.api.restclient;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

public class Request {
    private final RequestSpecification requestSpec;
    private HashMap<String,String> queryParams;
    private HashMap<String,String> pathParams;
    private HashMap<String,String> requestHeaders;
    private Object requestBody;

    public Request(String baseUri,String basePath){
        requestSpec = RestAssured.given();
        requestSpec.baseUri(baseUri);
        requestSpec.basePath(basePath);
    }

    public void setQueryParams(String queryKey, String queryValue){
        if(queryParams == null){
            queryParams = new HashMap<>();
        }
        queryParams.put(queryKey,queryValue);
    }
    public void setPathParams(String pathKey, String pathValue){
        if(pathParams == null){
            pathParams = new HashMap<>();
        }
        pathParams.put(pathKey,pathValue);
    }
    public void setRequestHeaders(String headerKey,String headerValue){
        if(requestHeaders == null){
            requestHeaders = new HashMap<>();
        }
        requestHeaders.put(headerKey,headerValue);
    }
    public void setRequestBody(Object requestBody){
        if(this.requestBody == null){
            this.requestBody = requestBody;
        }
    }

    public RequestSpecification createRequest(){
        if(queryParams != null){
            requestSpec.queryParams(queryParams);
        }
        if(pathParams != null){
            requestSpec.pathParams(pathParams);
        }
        if(requestHeaders != null){
            requestSpec.headers(requestHeaders);
        }
        if(requestBody != null){
            requestSpec.body(requestBody);
        }
        return requestSpec;
    }
}
