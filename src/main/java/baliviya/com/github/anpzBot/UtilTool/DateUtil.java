package baliviya.com.github.anpzBot.UtilTool;

import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static SimpleDateFormat formatDateAndTimeSS = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
//    private static SimpleDateFormat formatDateAndTime = new SimpleDateFormat("dd.MM.yyyy HH:mm");
//    private static SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
//    private static SimpleDateFormat formatTimeSS = new SimpleDateFormat("HH:mm:ss");

    public static String getDbMmYyyyHhMmSs(Date date) {
        formatDateAndTimeSS.setLenient(false);
        return formatDateAndTimeSS.format(date);
    }
//    public static String getString(Date date, String format) {
//        return getResult(new SimpleDateFormat(format), date);
//    }
//    public static String getDayDate(Date date) {
//        formatDateAndTime.setLenient(false);
//        return formatDate.format(date);
//    }
//    private static String getResult(SimpleDateFormat simpleDateFormat, Date date) {
//        simpleDateFormat.setLenient(false);
//        return simpleDateFormat.format(date);
//    }
//    public static String getTimeAndDate(Date date) {
//        formatDateAndTime.setLenient(false);
//        return formatDate.format(date);
//    }
//    public static String getTimeDate(Date date) {
//        return getString(date,"HH:mm:ss");
//    }
//    public static Date setTime(Date day, String time_Hhmmss) {
//        String result = getDayDate(day) + " " + time_Hhmmss;
//        formatTimeSS.setLenient(false);
//        try {
//            return formatDateAndTimeSS.parse(result);
//        } catch (ParseException e) {
//            LoggerFactory.getLogger(DateUtil.class).error("Error parse", e);
//            return null;
//        }
//    }
}
