package tests.login;

import data.CommonStrings;
import data.Groups;
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

@Test(groups = {Groups.REGRESSION, Groups.SANITY, Groups.LOGIN})
public class SuccessfulLogin extends BaseTestClass {

    private final String sTestName = this.getClass().getName();
    private WebDriver driver;

    private final String sUsername = PropertiesUtils.getUsername();
    private final String sPassword = PropertiesUtils.getPassword();

    private final String sExpectedInventoryPageTitle = CommonStrings.getInventoryPageTitle();

    boolean bChanged = false;
    boolean bUploaded = false;

    @BeforeMethod
    public void setUpTest(ITestContext testContext) {
        log.info("[SETUP TEST] " + sTestName);
        driver = setUpDriver();
    }

    @Test
    public void testSuccessfulLogin() {

        log.info("[START TEST] " + sTestName);

        LoginPage loginPage = new LoginPage(driver).open();
        DateTimeUtils.wait(Time.DEMONSTRATION);

        // -> Setting Changed
        bChanged = true;

        loginPage.typeUsername(sUsername);
        DateTimeUtils.wait(Time.DEMONSTRATION);

        loginPage.typePassword(sPassword);
        DateTimeUtils.wait(Time.DEMONSTRATION);

        InventoryPage inventoryPage = loginPage.clickLoginButton();
        DateTimeUtils.wait(Time.DEMONSTRATION);

        // -> Document Uploaded
        bUploaded = true;

        String sActualInventoryPageTitle = inventoryPage.getInventoryPageTitle();

        Assert.assertEquals(sActualInventoryPageTitle, sExpectedInventoryPageTitle, "Wrong Inventory Page Title!");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
         log.info("[END TEST] " + sTestName);
         tearDown(driver, testResult);
         cleanUp();
    }

    private void cleanUp() {
        log.debug("cleanUp()");
        try {
            if (bChanged) {
                // RollBack Settings
                if (bUploaded) {
                    // Delete Document
                }
            }
        } catch (Exception | AssertionError e) {
            log.error("Clean up failed! Message: " + e.getMessage());
        }
    }
}
