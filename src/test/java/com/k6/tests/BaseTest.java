package com.k6.tests;

import com.framework.core.api.restclient.RequestParam;
import com.practice.auth.app.CrocodilesCredPOJO;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    protected String sessionId;
    private String baseUri = "https://test-api.k6.io";

    @BeforeTest
    public void createSessionId(){
        CrocodilesCredPOJO crocodilesCredPOJO = new CrocodilesCredPOJO("TestUser2023020720401211","1");
        RequestParam createSessionCookieRequest = new RequestParam(baseUri,"/auth/cookie/login/");
        createSessionCookieRequest.setRequestHeaders("Content-Type", "application/json");
        createSessionCookieRequest.setRequestBody(crocodilesCredPOJO);

        Response response = createSessionCookieRequest.createRequest().post();

        sessionId = response.cookie("sessionid");
    }
}
