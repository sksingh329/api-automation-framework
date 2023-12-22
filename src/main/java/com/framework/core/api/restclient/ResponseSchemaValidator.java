package com.framework.core.api.restclient;

import com.framework.core.utils.files.FileUtils;
import com.framework.core.utils.properties.FrameworkProperties;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.io.File;


public class ResponseSchemaValidator {

    private ResponseSchemaValidator(){}

    public static void validateSchema(Response response,String schemaFileName){
        String schemaFilePath = FrameworkProperties.getFrameworkProperties().getProperty("testDir")+FrameworkProperties.getFrameworkProperties().getProperty("schemaDirName")+schemaFileName;
        File schemaFile = FileUtils.getFile(schemaFilePath);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
    }
}
