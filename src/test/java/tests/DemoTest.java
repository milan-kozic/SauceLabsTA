package tests;

import data.CommonStrings;
import data.Time;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DemoPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.*;

import java.util.Date;

public class DemoTest extends LoggerUtils {

    @Test
    public void testDemoPage() {

        WebDriver driver = null;
        try {
            driver = WebDriverUtils.setUpDriver();

            DemoPage demoPage = new DemoPage(driver).open();
            DateTimeUtils.wait(Time.SHORT);


            /*
            log.info("CHECKBOX: " + demoPage.isExampleCheckboxChecked());
            demoPage.checkExampleCheckbox();
            DateTimeUtils.wait(Time.DEMONSTRATION);
            log.info("CHECKBOX: " + demoPage.isExampleCheckboxChecked());


            demoPage.checkExampleCheckbox();
            DateTimeUtils.wait(Time.DEMONSTRATION);
            log.info("CHECKBOX: " + demoPage.isExampleCheckboxChecked());

            demoPage.uncheckExampleCheckbox();
            DateTimeUtils.wait(Time.DEMONSTRATION);
            log.info("CHECKBOX: " + demoPage.isExampleCheckboxChecked());

            demoPage.uncheckExampleCheckbox();
            DateTimeUtils.wait(Time.DEMONSTRATION);
            log.info("CHECKBOX: " + demoPage.isExampleCheckboxChecked());

            demoPage.checkExampleCheckbox();
            DateTimeUtils.wait(Time.DEMONSTRATION);
            log.info("CHECKBOX: " + demoPage.isExampleCheckboxChecked());
            */

            /*
            log.info("DEFAULT SELECTED OPTION: " + demoPage.getSelectedOptionFromDropDownMenu());
            demoPage.selectOptionFromDropDownMenu("June");
            DateTimeUtils.wait(Time.DEMONSTRATION);

            log.info("SELECTED OPTION: " + demoPage.getSelectedOptionFromDropDownMenu());
            DateTimeUtils.wait(Time.DEMONSTRATION);

            demoPage.selectOptionFromDropDownMenu("Wrong");
            DateTimeUtils.wait(Time.DEMONSTRATION);

            log.info("SELECTED OPTION: " + demoPage.getSelectedOptionFromDropDownMenu());
             */

            //demoPage.selectRadioGroupOption("FeMale");

            //demoPage.dragAndDropBox();

            demoPage.moveBox(100, 25);

            DateTimeUtils.wait(Time.SHORT);

        } finally {
            WebDriverUtils.quitDriver(driver);
        }
    }

    @Test
    public void testDate() {

        Date currentDate = DateTimeUtils.getCurrentDateTime();
        log.info("CURRENT DATE: " + currentDate);
        // Monday  MM = 08, MMM = Aug, MMMM = August
        String sFormattedDate = DateTimeUtils.getFormattedDateTime(currentDate, "EEE dd-MMM-yyyy HH:mm:ss zzzz");
        log.info("FORMATTED DATE: " + sFormattedDate);
        log.info("FORMATTED LOCALE DATE: " + DateTimeUtils.getLocalizedDateTime(currentDate, "EEEE dd-MMMM-yyyy HH:mm:ss zzzz", "fr"));

        String sDateCreated = "26.05.2023. 14:39";
        Date parsedDate = DateTimeUtils.getParsedDateTime(sDateCreated, "dd.MM.yyyy. HH:mm");
        log.info("PARSED DATE: " + parsedDate);

        WebDriver driver = WebDriverUtils.setUpDriver();
        DateTimeUtils.wait(Time.DEMONSTRATION);

        String sBrowserDateTime = DateTimeUtils.getBrowserDateTimeString(driver);
        log.info("BROWSER DATE STRING: " + sBrowserDateTime);

        Date browserDateTime = DateTimeUtils.getBrowserDateTime(driver);
        log.info("BROWSER DATE: " + browserDateTime);
        log.info("BROWSER TIME ZONE A: " + DateTimeUtils.getBrowserTimeZone(driver));
        log.info("BROWSER TIME ZONE B: " + DateTimeUtils.getBrowserTimeZoneB(driver));
        WebDriverUtils.quitDriver(driver);

        String sFullBrowserDateTime = sDateCreated + " " + "PDT";
        browserDateTime = DateTimeUtils.getParsedDateTime(sFullBrowserDateTime, "dd.MM.yyyy. HH:mm zzz");
        log.info("REAL BROWSER DATE TIME: " + browserDateTime);

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
