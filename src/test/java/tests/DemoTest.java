package tests;

import data.CommonStrings;
import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DateTimeUtils;
import utils.PropertiesUtils;
import utils.WebDriverUtils;

import java.time.Duration;

public class DemoTest {

    @Test
    public void testSuccessfulLogin() {

        WebDriver driver = null;

        String sUsername = PropertiesUtils.getUsername();
        String sPassword = PropertiesUtils.getPassword();

        try {
            driver = WebDriverUtils.setUpDriver();

            LoginPage loginPage = new LoginPage(driver).open();
            DateTimeUtils.wait(Time.DEMONSTRATION);

            loginPage.typeUsername(sUsername);
            DateTimeUtils.wait(Time.DEMONSTRATION);

            loginPage.typePassword(sPassword);
            DateTimeUtils.wait(Time.DEMONSTRATION);

            loginPage.clickLoginButton();
            DateTimeUtils.wait(Time.DEMONSTRATION);

            WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(Time.SHORTER));
            InventoryPage inventoryPage = new InventoryPage(driver);
            String sExpectedUrl = inventoryPage.INVENTORY_PAGE_URL;
            wait3.until(ExpectedConditions.urlToBe(sExpectedUrl));
            DateTimeUtils.wait(Time.DEMONSTRATION);

            String sActualInventoryPageTitle = inventoryPage.getInventoryPageTitle();
            String sExpectedInventoryPageTitle = CommonStrings.getInventoryPageTitle();
            Assert.assertEquals(sActualInventoryPageTitle, sExpectedInventoryPageTitle, "Wrong Inventory Page Title!");


        } finally {
            System.out.println("Quit Driver!");
            WebDriverUtils.quitDriver(driver);
        }
    }

    @Test
    public void testUnsuccessfulLoginWrongPassword() {

        WebDriver driver = null;
        String sUsername = PropertiesUtils.getUsername();
        String sPassword = PropertiesUtils.getPassword() + "!";

        try {

            driver = WebDriverUtils.setUpDriver();

            LoginPage loginPage = new LoginPage(driver).open();

            loginPage.typeUsername(sUsername);
            DateTimeUtils.wait(Time.DEMONSTRATION);

            loginPage.typePassword(sPassword);
            DateTimeUtils.wait(Time.DEMONSTRATION);

            loginPage.clickLoginButton();
            DateTimeUtils.wait(Time.DEMONSTRATION);

            WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(Time.SHORTER));
            String sExpectedUrl = loginPage.LOGIN_PAGE_URL;
            wait3.until(ExpectedConditions.urlToBe(sExpectedUrl));
            DateTimeUtils.wait(Time.DEMONSTRATION);

            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error Message is NOT displayed!");

            String sActualMessage = loginPage.getErrorMessage();
            String sExpectedMessage = CommonStrings.getLoginErrorMessageWrongCredentials();

            Assert.assertEquals(sActualMessage, sExpectedMessage, "Wrong Error Message!");

        } finally {
            System.out.println("Quit Driver!");
            WebDriverUtils.quitDriver(driver);
        }
    }

}
