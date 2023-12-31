package com.framework.core.api.restclient;

import com.framework.core.report.ReportLevel;
import com.framework.core.report.ReporterUtils;
import com.framework.core.utils.files.FileUtils;
import com.framework.core.utils.properties.FrameworkProperties;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.io.File;
import java.nio.file.Paths;


public class ResponseSchemaValidator {

    private ResponseSchemaValidator(){}

    public static void validateSchema(ResponseFetcher response,String schemaFileName){
        String schemaFilePath = FrameworkProperties.getFrameworkProperties().getProperty("testDir")+FrameworkProperties.getFrameworkProperties().getProperty("schemaDirName")+schemaFileName;
        File schemaFile = FileUtils.getFile(schemaFilePath);
        String schemaString = FileUtils.readStringFromFile(Paths.get(System.getProperty("user.dir")+schemaFilePath));

        try{
            response.getResponse().then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
            ReporterUtils.log(ReportLevel.PASS,"Schema Validation","Response body " + response.getResponseBody() + " matches with schema " + schemaString);
        }
        catch (AssertionError ex){
            ReporterUtils.log(ReportLevel.FAIL,"Schema Validation","Response body " + response.getResponseBody() + "not matched with schema " + schemaString + " ,Trace : " + ex);
        }
    }
}
