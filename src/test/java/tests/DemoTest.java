package tests;

import data.CommonStrings;
import data.Time;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.*;

import java.util.Date;

public class DemoTest extends LoggerUtils {


    @Test
    public void testDate() {

        Date currentDate = DateTimeUtils.getCurrentDateTime();
        // Monday  MM = 08, MMM = Aug, MMMM = August
        String sFormattedDate = DateTimeUtils.getFormattedDateTime(currentDate, "EEEE dd-MMMM-yyyy HH:mm:ss zzzz");
        log.info("DATE: " + sFormattedDate);
    }

    @Test
    public void testSuccessfulLogin() {

        WebDriver driver = null;

        String sUsername = PropertiesUtils.getUsername();
        String sPassword = PropertiesUtils.getPassword();

        String sTestName = "testSuccessfulLogin";

        boolean bSuccess = false;

        try {
            log.info("Starting Test '" + sTestName + "'");
            driver = WebDriverUtils.setUpDriver();

            Date date = new Date();

            LoginPage loginPage = new LoginPage(driver).open();
            DateTimeUtils.wait(Time.DEMONSTRATION);

            // -> Setting Changed

            loginPage.typeUsername(sUsername);
            DateTimeUtils.wait(Time.DEMONSTRATION);

            loginPage.typePassword(sPassword);
            DateTimeUtils.wait(Time.DEMONSTRATION);

            InventoryPage inventoryPage = loginPage.clickLoginButton();
            DateTimeUtils.wait(Time.DEMONSTRATION);

            // -> Document Uploaded

            String sActualInventoryPageTitle = inventoryPage.getInventoryPageTitle();
            String sExpectedInventoryPageTitle = CommonStrings.getInventoryPageTitle();
            Assert.assertEquals(sActualInventoryPageTitle, sExpectedInventoryPageTitle, "Wrong Inventory Page Title!");
            bSuccess = true;

        } finally {
            log.info("Ending Test '" + sTestName + "'");
            if(!bSuccess) {
                log.info("Capturing ScreenShot...");
                ScreenShotUtils.takeScreenShot(driver, sTestName);
            }

            // -> Rollback Setting
            // -> Delete Document

        }
    }

    @Test
    public void testUnsuccessfulLoginWrongPassword() {

        WebDriver driver = null;
        String sUsername = PropertiesUtils.getUsername();
        String sPassword = PropertiesUtils.getPassword() + "!";
        //String sPassword = PropertiesUtils.getPassword();

        String sTestName = "testUnsuccessfulLoginWrongPassword";

        try {

            log.info("Starting Test '" + sTestName + "'");
            driver = WebDriverUtils.setUpDriver();

            LoginPage loginPage = new LoginPage(driver).open();

            loginPage.typeUsername(sUsername);
            DateTimeUtils.wait(Time.DEMONSTRATION);

            loginPage.typePassword(sPassword);
            DateTimeUtils.wait(Time.DEMONSTRATION);

            loginPage = loginPage.clickLoginButtonNoProgress();
            DateTimeUtils.wait(Time.DEMONSTRATION);

            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error Message is NOT displayed!");

            String sActualMessage = loginPage.getErrorMessage();
            String sExpectedMessage = CommonStrings.getLoginErrorMessageWrongCredentials();

            Assert.assertEquals(sActualMessage, sExpectedMessage, "Wrong Error Message!");

        } finally {
            log.info("Ending Test '" + sTestName + "'");
            ScreenShotUtils.takeScreenShot(driver, sTestName);
            WebDriverUtils.quitDriver(driver);
        }
    }

}
