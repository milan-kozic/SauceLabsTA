package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    public static String getLocalizedDateTime(Date date, String pattern, String locale) {
        Locale loc = new Locale(locale);
        DateFormat dateFormat = new SimpleDateFormat(pattern, loc);
        return dateFormat.format(date);
    }

    public static String getFormattedCurrentDateTime(String pattern) {
        Date date = getCurrentDateTime();
        return getFormattedDateTime(date, pattern);
    }

    public static String getDateTimeStamp() {
        return getFormattedCurrentDateTime("yyMMddHHmmss");
    }

    public static Date getParsedDateTime(String sDateTime, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = dateFormat.parse(sDateTime);
        } catch (ParseException e) {
            Assert.fail("Cannot parse date '" + sDateTime + "' using pattern '" + pattern + "'! Message: " + e.getMessage());
        }
        return date;
    }

    // Monday, 29 May 2023 at 12:15:05 CEST
    public static String getBrowserDateTimeString(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String sJavaScript = "var browserDateTime = new Date().getTime(); return Intl.DateTimeFormat('en-GB', { dateStyle: 'full', timeStyle: 'long' }).format(browserDateTime);";
        String sBrowserDateTime = (String) js.executeScript(sJavaScript);
        sBrowserDateTime = sBrowserDateTime.replace(" at ", " ");
        return sBrowserDateTime;
    }

    public static Date getBrowserDateTime(WebDriver driver) {
        String sBrowserDateTime = getBrowserDateTimeString(driver);
        String sPattern = "EEEE, dd MMMM yyyy HH:mm:ss zzz";
        return getParsedDateTime(sBrowserDateTime, sPattern);
    }

    public static String getBrowserTimeZone(WebDriver driver) {
        String sBrowserDateTime = getBrowserDateTimeString(driver);
        int i = sBrowserDateTime.lastIndexOf(" ");
        String sTimeZone = sBrowserDateTime.substring(i+1);
        return sTimeZone;
    }

    public static String getBrowserTimeZoneB(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String sJavaScript = "return Intl.DateTimeFormat().resolvedOptions().timeZone;";
        String sBrowserTimeZone = (String) js.executeScript(sJavaScript);
        return sBrowserTimeZone;
    }
}
