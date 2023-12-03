package qa.core.utils.properties;

import java.io.InputStream;

public class FrameworkProperties {
    private static final String FRAMEWORK_PROP = "framework.properties";
    private static PropertiesUtils frameworkProperties;
    private static void loadFrameworkProperties(){
        ClassLoader classLoader = FrameworkProperties.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(FRAMEWORK_PROP);
        frameworkProperties = new PropertiesUtils(inputStream);
    }
    public static PropertiesUtils getFrameworkProperties(){
        if(frameworkProperties == null){
            loadFrameworkProperties();
        }
        return frameworkProperties;
    }
}
