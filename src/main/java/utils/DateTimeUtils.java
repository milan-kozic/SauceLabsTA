package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

    public static void wait(int timeout) {
        try {
            Thread.sleep(timeout * 1000L);
        } catch (InterruptedException e) {
            System.out.println("Exception in Thread.sleep()! Message: " + e.getMessage());
        }
    }

    public static Date getCurrentDateTime() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    public static Date getDateTime(long milliseconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milliseconds);
        return cal.getTime();
    }

    public static String getFormattedDateTime(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static String getFormattedCurrentDateTime(String pattern) {
        Date date = getCurrentDateTime();
        return getFormattedDateTime(date, pattern);
    }

    public static String getDateTimeStamp() {
        return getFormattedCurrentDateTime("yyMMddHHmmss");
    }






}
