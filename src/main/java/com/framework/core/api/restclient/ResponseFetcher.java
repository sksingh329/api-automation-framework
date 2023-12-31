package com.framework.core.api.restclient;

import io.restassured.http.Headers;
import io.restassured.response.Response;

public class ResponseFetcher {
    private final Response response;

    public ResponseFetcher(Response response){
        this.response = response;
    }

    public Response getResponse(){
        return response;
    }
    public int getStatusCode(){
        return response.getStatusCode();
    }
    public Headers getHeaders(){
        return response.getHeaders();
    }
    public String getHeader(String headerKey){
        //TODO : throw exception if headerKey not found
        return response.getHeader(headerKey);
    }
    public ResponseBodyParser getResponseBodyParser(){
        return new ResponseBodyParser(response);
    }
    public String getResponseBody(){
        return response.getBody().toString();
    }
    public String getResponseCookie(String cookieName){
        return response.cookie(cookieName);
    }
}
