package qa.core.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
    private Properties properties = new Properties();

    public PropertiesUtils(InputStream fis){
        try{
            properties.load(fis);
        } catch (IOException e){
            // throw Base Exception
            e.printStackTrace();
        }
    }
    public Properties getProperties(){
        return properties;
    }
}
