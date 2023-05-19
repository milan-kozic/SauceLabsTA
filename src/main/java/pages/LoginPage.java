package pages;

import data.PageUrlPaths;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.PropertiesUtils;


public class LoginPage {

    private WebDriver driver;

    public final String LOGIN_PAGE_URL = PropertiesUtils.getBaseUrl() + PageUrlPaths.LOGIN_PAGE;

    // Locators
    private final By usernameTextFieldLocator = By.id("user-name");
    private final By passwordTextFieldLocator = By.xpath("//input[@type='password']");
    private final By loginButtonLocator = By.cssSelector("input.submit-button");
    private final By errorMessageLocator = By.xpath("//h3[@data-test='error']");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Open
    public LoginPage open() {
        driver.get(LOGIN_PAGE_URL);
        return this;
    }

    public void typeUsername(String sUsername) {
        WebElement usernameTextField = driver.findElement(usernameTextFieldLocator);
        usernameTextField.sendKeys(sUsername);
    }

    public void typePassword(String sPassword) {
        WebElement passwordTextField = driver.findElement(passwordTextFieldLocator);
        passwordTextField.sendKeys(sPassword);
    }

    public void clickLoginButton() {
        WebElement loginButton = driver.findElement(loginButtonLocator);
        loginButton.click();
    }

    public boolean isErrorMessageDisplayed() {
        WebElement errorMessage = driver.findElement(errorMessageLocator);
        return errorMessage.isDisplayed();
    }

    public String getErrorMessage() {
        WebElement errorMessage = driver.findElement(errorMessageLocator);
        return errorMessage.getText();
    }

    public void login(String sUsername, String sPassword) {
        typeUsername(sUsername);
        typePassword(sPassword);
        clickLoginButton();
    }

}
