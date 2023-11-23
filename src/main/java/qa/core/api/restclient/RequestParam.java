package qa.core.api.restclient;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import qa.core.report.ReportLevel;
import qa.core.report.ReporterUtils;

import java.util.HashMap;

public class RequestParam {
    private final String baseUri;
    private final String basePath;
    private final RequestSpecification requestSpec;
    private HashMap<String,String> queryParams;
    private HashMap<String,String> pathParams;
    private HashMap<String,String> requestHeaders;
    private Object requestBody;

    public RequestParam(String baseUri, String basePath){
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

    public RequestSender createRequest(){
        ReporterUtils.log(ReportLevel.INFO,"Request Base URI",baseUri);
        ReporterUtils.log(ReportLevel.INFO,"Request base Path",basePath);

        if(pathParams != null){
            requestSpec.pathParams(pathParams);
            ReporterUtils.log(ReportLevel.INFO,"Request path params",pathParams.toString());
        }
        if(queryParams != null){
            requestSpec.queryParams(queryParams);
            ReporterUtils.log(ReportLevel.INFO,"Request query params",queryParams.toString());
        }
        if(requestHeaders != null){
            requestSpec.headers(requestHeaders);
            ReporterUtils.log(ReportLevel.INFO,"Request Headers",requestHeaders.toString());
        }
        if(requestBody != null){
            requestSpec.body(requestBody);
            ReporterUtils.log(ReportLevel.INFO,"Request payload",requestBody.toString());
        }

        return new RequestSender(requestSpec);
    }
}
