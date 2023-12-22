package com.k6.tests;

import com.framework.core.api.restclient.RequestParam;
import com.framework.core.api.restclient.ResponseBodyParser;
import com.framework.core.api.restclient.ResponseSchemaValidator;
import com.framework.core.report.TestNGListener;
import com.framework.core.utils.files.FileUtils;
import com.practice.auth.app.CrocodilesCredPOJO;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;

@Listeners(TestNGListener.class)
public class CrocodilesTest extends BaseTest{
    private RequestParam request;
    private String baseUri = "https://test-api.k6.io";

    @Test
    public void getMyCrocodiles(){
        request = new RequestParam(baseUri,"/my/crocodiles/");

        request.setRequestHeaders("Cookie","sessionid="+sessionId);
        Response response = request.createRequest().get();

        ResponseSchemaValidator.validateSchema(response,"k6/MyCrocodilesSchema.json");
    }
}
