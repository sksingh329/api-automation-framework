package qa.core.utils;

import java.io.File;

public class FileUtils {
    public static void createDir(String dirName){
        File directory = new File(dirName);
        if (!directory.exists()) {
            directory.mkdirs();  // Creates the directory and its parent directories if they don't exist
        }
    }
}
