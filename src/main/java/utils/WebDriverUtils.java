package utils;

import data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.net.URL;
import java.time.Duration;

public class WebDriverUtils {

    public static WebDriver setUpDriver() {

        String sBrowser = PropertiesUtils.getBrowser();
        boolean bRemote = PropertiesUtils.getRemote();
        boolean bHeadless = PropertiesUtils.getHeadless();
        String sHubUrl = PropertiesUtils.getHubUrl();

        String sChromeDriverPath = "C:\\Selenium\\chromedriver.exe";
        String sFirefoxDriverPath = "C:\\Selenium\\geckodriver.exe";
        String sEdgeDriverPath = "C:\\Selenium\\msedgedriver.exe";

        WebDriver driver = null;

        try {
            switch (sBrowser) {
                case "chrome" : {
                    ChromeOptions options = new ChromeOptions();
                    if(bHeadless) {
                        //options.setHeadless(true);
                        options.addArguments("--headless");
                        options.addArguments("--window-size=1600x900pix");
                    }
                    if(bRemote) {
                        RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(sHubUrl), options);
                        remoteDriver.setFileDetector(new LocalFileDetector());
                        driver = remoteDriver;
                    } else {
                        System.setProperty("webdriver.chrome.driver", sChromeDriverPath);
                        driver = new ChromeDriver(options);
                    }
                    break;
                }

                case "firefox" : {
                    FirefoxOptions options = new FirefoxOptions();
                    if(bHeadless) {
                        options.addArguments("--headless");
                        options.addArguments("--window-size=1600x900pix");
                    }
                    if(bRemote) {
                        RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(sHubUrl), options);
                        remoteDriver.setFileDetector(new LocalFileDetector());
                        driver = remoteDriver;
                    } else {
                        System.setProperty("webdriver.gecko.driver", sFirefoxDriverPath);
                        driver = new FirefoxDriver(options);
                    }
                    break;
                }

                case "edge" : {
                    EdgeOptions options = new EdgeOptions();
                    if(bHeadless) {
                        options.addArguments("--headless");
                        options.addArguments("--window-size=1600x900pix");
                    }
                    if(bRemote) {
                        RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(sHubUrl), options);
                        remoteDriver.setFileDetector(new LocalFileDetector());
                        driver = remoteDriver;
                    } else {
                        System.setProperty("webdriver.edge.driver", sEdgeDriverPath);
                        driver = new EdgeDriver(options);
                    }
                    break;
                }

                default : {
                    Assert.fail("Browser " + sBrowser + " is NOT recognized!");
                }
            }
        } catch (Exception e) {
            Assert.fail("Cannot create driver! Message: " + e.getMessage());
        }
        // Initialize Driver Instance


        // Maximize Browser
        DateTimeUtils.wait(3);
        driver.manage().window().maximize();

        // Set Implicit Wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Time.IMPLICIT_WAIT));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Time.PAGE_LOAD_TIMEOUT));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(Time.SHORT));

        return driver;

    }

    public static void quitDriver(WebDriver driver) {
        if (driver !=null) {
            driver.quit();
        }
    }
}
