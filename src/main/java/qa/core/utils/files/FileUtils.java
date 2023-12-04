package qa.core.utils.files;

import qa.core.exceptions.FrameworkException;
import qa.core.utils.properties.FrameworkProperties;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileUtils {
    public static void createDir(String dirName){
        File directory = new File(dirName);
        if (!directory.exists()) {
            directory.mkdirs();  // Creates the directory and its parent directories if they don't exist
        }
    }
    public static FileReader getFileReader(String filePath){
        String absoluteFilePath = System.getProperty("user.dir") + filePath;
        try{
            return new FileReader(absoluteFilePath);
        }
        catch (NullPointerException | FileNotFoundException ex){
            throw new FrameworkException("File not found - " + absoluteFilePath + " Trace : " + ex);
        }
    }
    public static File getFile(String filePath){
        String absoluteFilePath = System.getProperty("user.dir") + filePath;
        try{
            return new File(absoluteFilePath);
        }
        catch (NullPointerException ex){
            throw new FrameworkException("File not found - " + absoluteFilePath + " Trace : " + ex);
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
