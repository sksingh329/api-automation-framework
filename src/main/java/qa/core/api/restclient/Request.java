package qa.core.api.restclient;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import qa.core.reports.ReporterUtils;

import java.util.HashMap;

public class Request {
    private final String baseUri;
    private final String basePath;
    private final RequestSpecification requestSpec;
    private HashMap<String,String> queryParams;
    private HashMap<String,String> pathParams;
    private HashMap<String,String> requestHeaders;
    private Object requestBody;

    public Request(String baseUri,String basePath){
        this.baseUri = baseUri;
        this.basePath = basePath;
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
        ReporterUtils.log(ReporterUtils.Level.INFO,"Request Base URI",baseUri);
        ReporterUtils.log(ReporterUtils.Level.INFO,"Request base Path",basePath);

        if(pathParams != null){
            requestSpec.pathParams(pathParams);
            ReporterUtils.log(ReporterUtils.Level.INFO,"Request path params",pathParams.toString());
        }
        if(queryParams != null){
            requestSpec.queryParams(queryParams);
            ReporterUtils.log(ReporterUtils.Level.INFO,"Request query params",queryParams.toString());
        }
        if(requestHeaders != null){
            requestSpec.headers(requestHeaders);
            ReporterUtils.log(ReporterUtils.Level.INFO,"Request Headers",requestHeaders.toString());
        }
        if(requestBody != null){
            requestSpec.body(requestBody);
            ReporterUtils.log(ReporterUtils.Level.INFO,"Request payload",requestBody.toString());
        }
        return requestSpec;
    }
}
