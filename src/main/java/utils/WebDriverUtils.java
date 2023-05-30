package utils;

import data.Time;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Cookie;
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
import java.util.Set;

public class WebDriverUtils extends LoggerUtils {

    public static WebDriver setUpDriver() {

        String sBrowser = PropertiesUtils.getBrowser();
        boolean bRemote = PropertiesUtils.getRemote();
        boolean bHeadless = PropertiesUtils.getHeadless();
        String sHubUrl = PropertiesUtils.getHubUrl();

        String sChromeDriverPath = "C:\\Selenium\\chromedriver.exe";
        String sFirefoxDriverPath = "C:\\Selenium\\geckodriver.exe";
        String sEdgeDriverPath = "C:\\Selenium\\msedgedriver.exe";

        WebDriver driver = null;

        log.info("setUpDriver(Browser: " + sBrowser + ", Remote: " + bRemote + ", Headless: " + bHeadless + ")");

        try {
            switch (sBrowser) {
                case "chrome" : {
                    ChromeOptions options = new ChromeOptions();
                    if(bHeadless) {
                        //options.setHeadless(true);
                        options.addArguments("--headless");
                        options.addArguments("--window-size=1600x900pix");
                    }
                    //WebDriverManager.chromedriver().setup();
                    if(bRemote) {
                        //driver = WebDriverManager.chromedriver().capabilities(options).remoteAddress(sHubUrl).create();
                        RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(sHubUrl), options);
                        remoteDriver.setFileDetector(new LocalFileDetector());
                        driver = remoteDriver;
                    } else {
                        //System.setProperty("webdriver.chrome.driver", sChromeDriverPath);
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
                    //WebDriverManager.firefoxdriver().setup();
                    if(bRemote) {
                        //driver = WebDriverManager.firefoxdriver().capabilities(options).remoteAddress(sHubUrl).create();
                        RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(sHubUrl), options);
                        remoteDriver.setFileDetector(new LocalFileDetector());
                        driver = remoteDriver;
                    } else {
                        //System.setProperty("webdriver.gecko.driver", sFirefoxDriverPath);
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
                    //WebDriverManager.edgedriver().setup();
                    if(bRemote) {
                        //driver = WebDriverManager.edgedriver().capabilities(options).remoteAddress(sHubUrl).create();
                        RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(sHubUrl), options);
                        remoteDriver.setFileDetector(new LocalFileDetector());
                        driver = remoteDriver;
                    } else {
                        //System.setProperty("webdriver.edge.driver", sEdgeDriverPath);
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

    public static void setImplicitWait(WebDriver driver, int timeout) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
    }

    public static String getCookies(WebDriver driver) {
        Set<Cookie> cookies = driver.manage().getCookies();
        StringBuilder sCookies = new StringBuilder();
        for(Cookie cookie : cookies) {
            sCookies.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
        }
        return sCookies.toString();
    }
}
