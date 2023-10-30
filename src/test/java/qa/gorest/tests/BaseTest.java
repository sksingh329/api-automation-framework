package qa.gorest.tests;

import org.testng.annotations.BeforeTest;
import qa.core.utils.PropertiesUtils;

import java.io.InputStream;
import java.util.Properties;

public class BaseTest {
    protected Properties properties;
    @BeforeTest
    public void testSetup(){
        ClassLoader classLoader = getClass().getClassLoader();
        //TODO - Add option to parameterize appname and envname when ran from testng xml else take qa
        InputStream inputStream = classLoader.getResourceAsStream("env/gorest.qa.properties");
        PropertiesUtils propertiesUtils = new PropertiesUtils(inputStream);
        properties = propertiesUtils.getProperties();
    }
}
