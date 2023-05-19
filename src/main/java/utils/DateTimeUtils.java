package utils;

public class DateTimeUtils {

    public static void wait(int timeout) {
        try {
            Thread.sleep(timeout * 1000L);
        } catch (InterruptedException e) {
            System.out.println("Exception in Thread.sleep()! Message: " + e.getMessage());
        }
    }
}
