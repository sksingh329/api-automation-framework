package qa.gorest.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import qa.core.utils.PropertiesUtils;

import java.io.InputStream;

public class BaseTest {
    protected PropertiesUtils properties;

    @Parameters({"appName","envName"})
    @BeforeTest
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
