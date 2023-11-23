package qa.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Timestamp {
    public static String getCurrentTimeStamp(){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyyHHmmss");
        return dateFormat.format(currentDate);
    }
}
