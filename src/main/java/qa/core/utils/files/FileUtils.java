package qa.core.utils.files;

import qa.core.exceptions.FrameworkException;

import java.io.File;
import java.util.Objects;

public class FileUtils {
    public static void createDir(String dirName){
        File directory = new File(dirName);
        if (!directory.exists()) {
            directory.mkdirs();  // Creates the directory and its parent directories if they don't exist
        }
    }
    public static File getFile(String filePath){
        try{
            return new File(Objects.requireNonNull(FileUtils.class.getClassLoader().getResource(filePath)).getFile());
        }
        catch (NullPointerException ex){
            throw new FrameworkException("File not found - " + filePath + " Trace : " + ex);
        }
    }
}
