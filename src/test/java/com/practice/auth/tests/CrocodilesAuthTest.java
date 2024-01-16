package com.practice.auth.tests;

import com.framework.core.api.restclient.RequestParam;
import com.framework.core.api.restclient.JsonParser;
import com.framework.core.api.restclient.ResponseFetcher;
import com.framework.core.report.TestNGListener;
import com.practice.auth.app.CrocodilesCredPOJO;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestNGListener.class)
public class CrocodilesAuthTest {
    private RequestParam request;
    private String baseUri = "https://test-api.k6.io";
    private String sessionId;

    @Test
    public void getCrocodileUsingSessionId(){
        CrocodilesCredPOJO crocodilesCredPOJO = new CrocodilesCredPOJO("TestUser2023020720401211","1");
        RequestParam createSessionCookieRequest = new RequestParam(baseUri,"/auth/cookie/login/");
        createSessionCookieRequest.setRequestHeaders("Content-Type", "application/json");
        createSessionCookieRequest.setRequestBody(crocodilesCredPOJO);

        ResponseFetcher response = createSessionCookieRequest.createRequest().post();

        sessionId = response.getResponseCookie("sessionid");

        System.out.println(sessionId);

        request = new RequestParam(baseUri,"/my/crocodiles/");

        request.setRequestHeaders("Cookie","sessionid="+sessionId);
        response = request.createRequest().get();
        System.out.println(response.getResponseBody());
    }
    @Test
    public void getCrocodileUsingJwt(){
        CrocodilesCredPOJO crocodilesCredPOJO = new CrocodilesCredPOJO("TestUser2023020720401211","1");
        RequestParam createSessionCookieRequest = new RequestParam(baseUri,"/auth/token/login/");
        createSessionCookieRequest.setRequestHeaders("Content-Type", "application/json");
        createSessionCookieRequest.setRequestBody(crocodilesCredPOJO);

        ResponseFetcher tokenResponse = createSessionCookieRequest.createRequest().post();

        JsonParser responseBodyParser = tokenResponse.getJsonParser();
        String accessToken = responseBodyParser.get("access").toString();

        request = new RequestParam(baseUri,"/my/crocodiles/");

        request.setRequestHeaders("Authorization","Bearer "+accessToken);
        ResponseFetcher response = request.createRequest().get();
        System.out.println(response.getResponseBody());
    }
}
