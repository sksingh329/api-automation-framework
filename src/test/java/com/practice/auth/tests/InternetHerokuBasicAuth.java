package com.practice.auth.tests;

import com.framework.core.api.restclient.RequestParam;
import com.framework.core.asserts.Asserts;
import com.framework.core.report.TestNGListener;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestNGListener.class)
public class InternetHerokuBasicAuth {
    private String baseUri = "http://the-internet.herokuapp.com";
    private String basePath = "/basic_auth";
    private RequestParam request;

    @BeforeMethod
    public void setup(){
        request = new RequestParam(baseUri,basePath);
    }

    @Test
    public void basicAuthTest(){
        request.setRequestAuth("basic","admin","admin");
        Response response = request.createRequest().get();
        String responseBody = response.then().extract().body().asString();

        Asserts.assertTrue(responseBody.contains("Congratulations! You must have the proper credentials."),"Validate authentication is successful");
    }
    @Test
    public void preemptiveAuthTest(){
        request.setRequestAuth("preemptive","admin","admin");
        Response response = request.createRequest().get();
        String responseBody = response.then().extract().body().asString();

        Asserts.assertTrue(responseBody.contains("Congratulations! You must have the proper credentials."),"Validate authentication is successful");
    }
    @Test
    public void digestAuthTest(){
        request.setRequestAuth("digest","admin","admin");
        Response response = request.createRequest().get();
        String responseBody = response.then().extract().body().asString();

        Asserts.assertTrue(responseBody.contains("Congratulations! You must have the proper credentials."),"Validate authentication is successful");
    }
}
