package tests.login;

import data.CommonStrings;
import data.Time;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.PropertiesUtils;
import utils.WebDriverUtils;

public class UnsuccessfulLoginWrongPassword extends BaseTestClass {

    private final String sTestName = this.getClass().getName();
    private WebDriver driver;

    private final String sUsername = PropertiesUtils.getUsername();
    private final String sPassword = PropertiesUtils.getPassword() + "!";

    private final String sExpectedLoginErrorMessage = CommonStrings.getLoginErrorMessageWrongCredentials();

    @BeforeMethod
    public void setUpTest(ITestContext testContext) {
        log.info("[SETUP TEST " + sTestName);
        driver = setUpDriver();
    }

    @Test
    public void testUnsuccessfulLoginWrongPassword() {

        log.info("[START TEST] " + sTestName);

        LoginPage loginPage = new LoginPage(driver).open();
        DateTimeUtils.wait(Time.DEMONSTRATION);

        loginPage.typeUsername(sUsername);
        DateTimeUtils.wait(Time.DEMONSTRATION);

        loginPage.typePassword(sPassword);
        DateTimeUtils.wait(Time.DEMONSTRATION);

        loginPage = loginPage.clickLoginButtonNoProgress();
        DateTimeUtils.wait(Time.DEMONSTRATION);

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error Message is NOT displayed!");

        String sActualLoginErrorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(sActualLoginErrorMessage, sExpectedLoginErrorMessage, "Wrong Error Message!");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        log.info("END TEST] " + sTestName);
        tearDown(driver, testResult);
    }
}
