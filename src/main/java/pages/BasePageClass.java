package pages;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.LoggerUtils;
import utils.PropertiesUtils;
import utils.WebDriverUtils;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.function.Function;

public abstract class BasePageClass extends LoggerUtils {

    protected WebDriver driver;

    public BasePageClass(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private WebDriverWait getWebDriverWaitInstance(int timeout) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    protected void openUrl(String sUrl) {
        log.trace("openUrl(" + sUrl + ")");
        driver.get(sUrl);
    }

    protected String getPageUrl(String sPagePath) {
        return PropertiesUtils.getBaseUrl() + sPagePath;
    }

    protected WebElement getWebElement(By locator) {
        log.trace("getWebElement(" + locator + ")");
        return driver.findElement(locator);
    }

    protected WebElement getWebElement(By locator, int timeout) {
        WebDriverWait wait = getWebDriverWaitInstance(timeout);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected WebElement getWebElement(By locator, int timeout, int pollingInterval) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(pollingInterval))
                .ignoring(NoSuchElementException.class);
        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver webDriver) {
                return driver.findElement(locator);
            }
        });
        return element;
    }

    protected boolean isWebElementDisplayed(By locator) {
        try {
            WebElement element = getWebElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isWebElementDisplayed(By locator, int timeout) {
        try {
            WebElement element = getWebElement(locator, timeout);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isWebElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isWebElementDisplayed(WebElement element, int timeout) {
        try {
            WebDriverUtils.setImplicitWait(driver, timeout);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        } finally {
            WebDriverUtils.setImplicitWait(driver, Time.IMPLICIT_WAIT);
        }
    }

    protected WebElement waitForWebElementToBeClickable(WebElement element, int timeout) {
        WebDriverWait wait = getWebDriverWaitInstance(timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebElement waitForWebElementToBeVisible(WebElement element, int timeout) {
        WebDriverWait wait = getWebDriverWaitInstance(timeout);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected boolean waitForWebElementToBeInvisible(WebElement element, int timeout) {
        WebDriverWait wait = getWebDriverWaitInstance(timeout);
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected boolean isWebElementEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isWebElementEnabled(WebElement element, int timeout) {
        try {
            WebElement webElement = waitForWebElementToBeClickable(element, timeout);
            return webElement != null;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isWebElementVisible(WebElement element, int timeout) {
        try {
            WebElement webElement = waitForWebElementToBeVisible(element, timeout);
            return webElement != null;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isWebElementInvisible(WebElement element, int timeout) {
        try {
            return waitForWebElementToBeInvisible(element, timeout);
        } catch (Exception e) {
            return true;
        }
    }

    protected void clickOnWebElement(WebElement element) {
        element.click();
    }

    protected void clickOnWebElementJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);
    }

    protected void clickOnWebElement(WebElement element, int timeout) {
        Assert.assertTrue(isWebElementEnabled(element, timeout));
        element.click();
    }

    protected void typeTextToWebElement(WebElement element, String text) {
        element.sendKeys(text);
    }

    protected void clearTextFromWebElement(WebElement element) {
        element.clear();
    }

    protected void clearAndTypeTextToWebElement(WebElement element, String text) {
        clearTextFromWebElement(element);
        typeTextToWebElement(element, text);
    }

    protected String getTextFromWebElement(WebElement element) {
        return element.getText();
    }

    protected String getAttributeFromWebElement(WebElement element, String sAttribute) {
        return element.getAttribute(sAttribute);
    }

    protected String getValueFromWebElement(WebElement element) {
        return getAttributeFromWebElement(element, "value");
    }

    protected String getValueFromWebElementJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript("return arguments[0].value", element);
    }

    protected String getPlaceholderFromWebElement(WebElement element) {
        return getAttributeFromWebElement(element, "placeholder");
    }

    protected boolean waitForUrlChange(String url, int timeout) {
        WebDriverWait wait = getWebDriverWaitInstance(timeout);
        return wait.until(ExpectedConditions.urlContains(url));
    }

    protected boolean waitForUrlChangeToExactUrl(String url, int timeout) {
        WebDriverWait wait = getWebDriverWaitInstance(timeout);
        return wait.until(ExpectedConditions.urlToBe(url));
    }

    protected boolean isWebPageReady() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript("return document.readyState").equals("complete");
    }

    protected boolean waitUntilPageIsReady(int timeout) {
        WebDriverWait wait = getWebDriverWaitInstance(timeout);
        return wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }
}
