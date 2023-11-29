package qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import qa.core.api.restclient.RequestParam;
import qa.core.utils.PropertiesUtils;

import java.io.InputStream;

public class BaseTest {
    protected PropertiesUtils properties;
    protected RequestParam request;

    @BeforeMethod(alwaysRun = true)
    public void setup(){
        request = new RequestParam(properties.getProperty("baseUri"),properties.getProperty("usersBasePath"));
        request.setRequestHeaders("Authorization","Bearer "+properties.getProperty("apiKey"));
    }

    @Parameters({"appName","envName"})
    @BeforeTest (alwaysRun = true)
    public void testSetup(@Optional("defaultAppName") String appName,@Optional("defaultEnvName") String envName){

        if ("defaultAppName".equals(appName) && "defaultEnvName".equals(envName)) {
            appName = "gorest";
            envName = "qa";
        }

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("env/"+appName+"."+envName+".properties");
        properties = new PropertiesUtils(inputStream);
    }
}
