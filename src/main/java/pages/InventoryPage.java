package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class InventoryPage extends BasePageClass {

    public final String INVENTORY_PAGE_URL = getPageUrl(PageUrlPaths.INVENTORY_PAGE);

    // Locators
    //By inventoryPageTitleLocator = By.xpath("//div[@id='header_container']//span[@class='title']");

    @FindBy(xpath = "//div[@id='header_container']//span[@class='title']")
    WebElement inventoryPageTitle;

    // Constructor
    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    // Open
    public InventoryPage open() {
        return open(true);
    }

    public InventoryPage open(boolean bVerify) {
        openUrl(INVENTORY_PAGE_URL);
        if (bVerify) {
            verifyPage();
        }
        return this;
    }

    public InventoryPage verifyPage() {
        waitForUrlChange(INVENTORY_PAGE_URL, Time.SHORTER);
        waitUntilPageIsReady(Time.SHORTER);
        return this;
    }

    public boolean isInventoryPageTitleDisplayed() {
        log.debug("isInventoryPageTitleDisplayed()");
        return isWebElementDisplayed(inventoryPageTitle);
    }

    public String getInventoryPageTitle() {
        log.debug("getInventoryPageTitle()");
        Assert.assertTrue(isInventoryPageTitleDisplayed(), "Inventory Page Title is NOT displayed!");
        return getTextFromWebElement(inventoryPageTitle);
    }
}
