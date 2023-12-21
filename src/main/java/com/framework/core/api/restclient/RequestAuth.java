package com.framework.core.api.restclient;

import io.restassured.specification.RequestSpecification;

public class RequestAuth {
    private final RequestSpecification requestSpec;

    public RequestAuth(RequestSpecification requestSpec){
        this.requestSpec = requestSpec;
    }
    public RequestSpecification basicAuth(String username,String password){
        return requestSpec.auth().basic(username,password);
    }
    public RequestSpecification preemptiveAuth(String username, String password){
        return requestSpec.auth().preemptive().basic(username,password);
    }
    public RequestSpecification digestAuth(String username, String password){
        return requestSpec.auth().digest(username,password);
    }
    public RequestSpecification oauth(String clientId, String clientSecret, String grantType){
        return requestSpec.formParam("client_id",clientId).formParam("client_secret",clientSecret)
                .formParam("grant_type",grantType);
    }
}
