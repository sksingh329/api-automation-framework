package com.framework.core.api.restclient;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import com.framework.core.report.ReportLevel;
import com.framework.core.report.ReporterUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

public class RequestParam {
    private final String baseUri;
    private final String basePath;
    private final RequestSpecification requestSpec;
    private HashMap<String,Object> queryParams;
    private HashMap<String,String> pathParams;
    private HashMap<String,String> requestHeaders;
    private Object requestBody;
    private String requestCookie;
    private RequestSpecification authRequestSpec;
    private Map<String,String> formParams;

    public RequestParam(String baseUri, String basePath){
        this.baseUri = baseUri;
        this.basePath = basePath;
        requestSpec = RestAssured.given();
        requestSpec.baseUri(baseUri);
        requestSpec.basePath(basePath);
    }

    public <T> void setQueryParams(String queryKey, T queryValue){
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
    public void setRequestCookie(String cookie){
        if(this.requestCookie == null){
            this.requestCookie = cookie;
        }
    }
    public RequestSpecification setRequestAuth(String authType,String userName,String password){
        RequestAuth requestAuth = new RequestAuth(requestSpec);
        authRequestSpec = null;
        switch (authType.toLowerCase(Locale.ROOT)){
            case "basic":
                authRequestSpec = requestAuth.basicAuth(userName,password);
                break;
            case "preemptive":
                authRequestSpec = requestAuth.preemptiveAuth(userName,password);
                break;
            case "digest":
                authRequestSpec = requestAuth.digestAuth(userName,password);
                break;
            default:
                //Replace with exception throw
                System.out.println("Not valid auth type");
        }
        return authRequestSpec;
    }
    public void setFormParams(Map<String,String> formParams){
        if(this.formParams == null){
            this.formParams = formParams;
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
        if(requestCookie != null){
            requestSpec.cookie(requestCookie);
            ReporterUtils.log(ReportLevel.INFO,"Request cookie",requestCookie);
        }
        if(authRequestSpec != null){
            requestSpec.spec(authRequestSpec);
        }
        if(formParams != null){
            for(Map.Entry<String, String> entry : formParams.entrySet()){
                requestSpec.formParam(entry.getKey(),entry.getValue());
            }
        }

        return new RequestSender(requestSpec);
    }
}
