package qa.core.utils;

import qa.core.exceptions.NoPropertyFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
    private final Properties properties = new Properties();

    public PropertiesUtils(InputStream fis){
        try(fis){
            properties.load(fis);
        } catch (IOException e){
            e.printStackTrace();
        }

    }
    public String getProperty(String key){
        String propertyValue = properties.getProperty(key);
        if(propertyValue == null){
            throw new NoPropertyFoundException("No property found for key : "+key);
        }
        return propertyValue;
    }
}
