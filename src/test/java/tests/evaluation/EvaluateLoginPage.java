package tests.evaluation;

import data.Time;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import tests.BaseTestClass;
import utils.DateTimeUtils;

public class EvaluateLoginPage extends BaseTestClass {

    private final String sTestName = this.getClass().getName();
    private WebDriver driver;

    @BeforeMethod
    public void setUpTest(ITestContext testContext) {
        log.info("[SETUP TEST " + sTestName);
        driver = setUpDriver();
    }

    @Test
    public void testEvaluateLoginPage() {

        log.info("[START TEST] " + sTestName);

        LoginPage loginPage = new LoginPage(driver).open();
        DateTimeUtils.wait(Time.DEMONSTRATION);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(loginPage.isUsernameTextFieldDisplayed(), "Username TextField is NOT displayed on LoginPage!");
        softAssert.assertTrue(loginPage.isPasswordTextFieldDisplayed(), "Password TextField is NOT displayed on LoginPage!");
        softAssert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login Button is NOT displayed on LoginPage!");

        softAssert.assertAll("One or more Web Elements are not displayed on LoginPage!");

    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        log.info("END TEST] " + sTestName);
        tearDown(driver, testResult);
    }
}
