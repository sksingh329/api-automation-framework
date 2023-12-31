package com.gorest.tests;

import com.framework.core.report.ReportLevel;
import com.framework.core.report.ReporterUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import com.framework.core.api.restclient.RequestParam;
import com.framework.core.utils.files.FileUtils;
import com.framework.core.utils.properties.FrameworkProperties;
import com.framework.core.utils.properties.PropertiesUtils;

import java.io.FileReader;

public class BaseTest {
    protected PropertiesUtils properties;
    protected RequestParam request;
    protected final String appName = "gorest";

    @Parameters({"envName"})
    @BeforeTest (alwaysRun = true)
    public void testSetup(@Optional("defaultEnvName") String envName){
        String envDirName = FrameworkProperties.getFrameworkProperties().getProperty("testEnvDirName");

        if ("defaultEnvName".equals(envName)) {
            envName = "qa";
        }

        ReporterUtils.log(ReportLevel.INFO,"Env Name",envName);
        FileReader envFileReader = FileUtils.getFileReader(FrameworkProperties.getFrameworkProperties().getProperty("testDir") + envDirName + appName + "." +envName+".properties");
        properties = new PropertiesUtils(envFileReader);
    }

    @BeforeMethod(alwaysRun = true)
    public void setup(){
        request = new RequestParam(properties.getProperty("baseUri"),properties.getProperty("usersBasePath"));
        request.setRequestHeaders("Authorization","Bearer "+properties.getProperty("apiKey"));
    }
}
