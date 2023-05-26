package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class LoginPage extends BasePageClass {

    public final String LOGIN_PAGE_URL = getPageUrl(PageUrlPaths.LOGIN_PAGE);

    // Locators
//    private final By usernameTextFieldLocator = By.id("user-name");
//    private final By passwordTextFieldLocator = By.xpath("//input[@type='password']");
//    private final By loginButtonLocator = By.cssSelector("input.submit-button");
//    private final By errorMessageLocator = By.xpath("//h3[@data-test='error']");

    @FindBy(id = "user-name")
    private WebElement usernameTextField;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordTextField;

    @FindBy(css = "input.submit-button")
    private WebElement loginButton;

    @FindBy(xpath = "//h3[@data-test='error']")
    private WebElement errorMessage;

    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Open
    public LoginPage open() {
        return open(true);
    }

    public LoginPage open(boolean bVerify) {
        openUrl(LOGIN_PAGE_URL);
        if (bVerify) {
            verifyPage();
        }
        return this;
    }

    public LoginPage verifyPage() {
        waitForUrlChangeToExactUrl(LOGIN_PAGE_URL, Time.SHORTER);
        waitUntilPageIsReady(Time.SHORTER);
        return this;
    }

    public boolean isUsernameTextFieldDisplayed() {
        log.debug("isUsernameTextFieldDisplayed()");
        return isWebElementDisplayed(usernameTextField);
    }

    public boolean isUsernameTextFieldEnabled() {
        log.debug("isUsernameTextFieldEnabled()");
        Assert.assertTrue(isUsernameTextFieldDisplayed(), "Username TextField is NOT displayed on LoginPage!");
        return isWebElementEnabled(usernameTextField);
    }

    public LoginPage typeUsername(String sUsername) {
        log.debug("typeUsername(" + sUsername + ")");
        Assert.assertTrue(isUsernameTextFieldEnabled(), "Username TextField is NOT enabled on LoginPage!");
        clearAndTypeTextToWebElement(usernameTextField, sUsername);
        return this;
    }

    public String getUsername() {
        log.debug("getUsername()");
        Assert.assertTrue(isUsernameTextFieldDisplayed(), "Username TextField is NOT displayed on LoginPage!");
        return getValueFromWebElement(usernameTextField);
    }

    public String getUsernamePlaceholder() {
        log.debug("getUsernamePlaceholder()");
        Assert.assertTrue(isUsernameTextFieldDisplayed(), "Username TextField is NOT displayed on LoginPage!");
        return getPlaceholderFromWebElement(usernameTextField);
    }

    public boolean isPasswordTextFieldDisplayed() {
        log.debug("isPasswordTextFieldDisplayed()");
        return isWebElementDisplayed(passwordTextField);
    }

    public boolean isPasswordTextFieldEnabled() {
        log.debug("isPasswordTextFieldEnabled()");
        Assert.assertTrue(isPasswordTextFieldDisplayed(), "Password TextField is NOT displayed on LoginPage!");
        return isWebElementEnabled(passwordTextField);
    }

    public LoginPage typePassword(String sPassword) {
        log.debug("typePassword(" + sPassword + ")");
        Assert.assertTrue(isPasswordTextFieldEnabled(), "Password TextField is NOT enabled on LoginPage!");
        clearAndTypeTextToWebElement(passwordTextField, sPassword);
        return this;
    }

    public boolean isLoginButtonDisplayed() {
        log.debug("isLoginButtonDisplayed()");
        return isWebElementDisplayed(loginButton);
    }

    public boolean isLoginButtonEnabled() {
        log.debug("isLoginButtonEnabled()");
        Assert.assertTrue(isLoginButtonDisplayed(), "Login Button is NOT displayed on LoginPage!");
        return isWebElementEnabled(loginButton, Time.SHORTER);
    }

    private void clickLoginButtonNoVerification() {
        Assert.assertTrue(isLoginButtonEnabled(), "Login Button is NOT enabled on LoginPage!");
        clickOnWebElement(loginButton);
    }

    public InventoryPage clickLoginButton() {
        log.debug("clickLoginButton()");
        clickLoginButtonNoVerification();
        InventoryPage inventoryPage = new InventoryPage(driver);
        return inventoryPage.verifyPage();
    }

    public LoginPage clickLoginButtonNoProgress() {
        log.debug("clickLoginButtonNoProgress()");
        clickLoginButtonNoVerification();
        LoginPage loginPage = new LoginPage(driver);
        return loginPage.verifyPage();
    }

    public boolean isErrorMessageDisplayed() {
        log.debug("isErrorMessageDisplayed()");
        return isWebElementDisplayed(errorMessage, Time.SHORTER);
    }

    public String getErrorMessage() {
        log.debug("getErrorMessage()");
        Assert.assertTrue(isErrorMessageDisplayed(), "Error Message is NOT displayed on LoginPage!");
        return getTextFromWebElement(errorMessage);
    }

    /**
     * Login to SauceLabs
     * @param sUsername - Username of User
     * @param sPassword - Password of User
     * @return New instance of Inventory Page
     */
    public InventoryPage login(String sUsername, String sPassword) {
        log.info("login(" + sUsername + ", " + sPassword + ")");
        typeUsername(sUsername);
        typePassword(sPassword);
        return clickLoginButton();
    }

}
