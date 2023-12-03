package qa.core.utils.files;

import qa.core.exceptions.FrameworkException;
import qa.core.utils.properties.FrameworkProperties;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileUtils {
    public static void createDir(String dirName){
        File directory = new File(dirName);
        if (!directory.exists()) {
            directory.mkdirs();  // Creates the directory and its parent directories if they don't exist
        }
    }
    public static File getFile(String filePath){
        try{
            return new File(System.getProperty("user.dir") + FrameworkProperties.getFrameworkProperties().getProperty("testDir") + filePath);
        }
        catch (NullPointerException ex){
            throw new FrameworkException("File not found - " + filePath + " Trace : " + ex);
        }
    }
    public static String readJsonStringFromFile(File filePath){
        try{
            return org.apache.commons.io.FileUtils.readFileToString(filePath, StandardCharsets.UTF_8);
        }
        catch (IOException ex){
            throw new FrameworkException("Error while reading content from file " + ex);
        }
    }
}
