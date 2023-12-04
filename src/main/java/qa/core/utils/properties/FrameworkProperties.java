package qa.core.utils.properties;

import qa.core.utils.files.FileUtils;

import java.io.FileReader;

public class FrameworkProperties {
    private static final String FRAMEWORK_PROP = "/src/main/resources/framework.properties";
    private static PropertiesUtils frameworkProperties;
    private static void loadFrameworkProperties(){
        FileReader frameworkPropertiesFileReader = FileUtils.getFileReader(FRAMEWORK_PROP);
        frameworkProperties = new PropertiesUtils(frameworkPropertiesFileReader);
    }
    public static PropertiesUtils getFrameworkProperties(){
        if(frameworkProperties == null){
            loadFrameworkProperties();
        }
        return frameworkProperties;
    }
}
