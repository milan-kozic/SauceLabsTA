package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class InventoryPage extends BasePageClass {

    public final String INVENTORY_PAGE_URL = getPageUrl(PageUrlPaths.INVENTORY_PAGE);

    // Locators
    //By inventoryPageTitleLocator = By.xpath("//div[@id='header_container']//span[@class='title']");

    private final String sInventoryListLocatorString = "//div[@id='inventory_container']/div[@class='inventory_list']";
    private final String sInventoryItemLocatorString = sInventoryListLocatorString + "/div[@class='inventory_item']";

    @FindBy(xpath = "//div[@id='header_container']//span[@class='title']")
    private WebElement inventoryPageTitle;

    @FindBy(xpath = sInventoryListLocatorString)
    private WebElement inventoryList;

    @FindBy(xpath = sInventoryItemLocatorString)
    List<WebElement> inventoryItems;

    // Constructor
    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    private String createXpathForItemNameInInventoryList(String sItemName) {
        return ".//div[@class='inventory_item_name' and text() = '" + sItemName + "']";
    }

    private String createXpathForItemInInventoryList(String sItemName) {
        return createXpathForItemNameInInventoryList(sItemName) + "/ancestor::div[@class='inventory_item']";
    }

    private String createXpathForItemDescriptionInInventoryList(String sItemName) {
        return createXpathForItemInInventoryList(sItemName) + "//div[@class='inventory_item_desc']";
    }

    private String createXpathForItemPriceInInventoryList(String sItemName) {
        return createXpathForItemInInventoryList(sItemName) + "//div[@class='inventory_item_price']";
    }

    private String createXpathForAddToCartButtonInInventoryList(String sItemName) {
        return createXpathForItemInInventoryList(sItemName) + "//div[@class='pricebar']/button[contains(@class, 'btn_primary')]";
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

    public int getNumberOfInventoryItems() {
        log.debug("getNumberOfInventoryItems()");
        //List<WebElement> inventoryItems = inventoryList.findElements(By.xpath("./div[@class='inventory_item']"));
        return inventoryItems.size();
    }

    public boolean isItemPresentInInventoryList(String sItemName) {
        log.debug("isItemPresentInInventoryList(" + sItemName + ")");
        String sXpath = createXpathForItemNameInInventoryList(sItemName);
        return isNestedWebElementDisplayed(inventoryList, By.xpath(sXpath));
    }

    private WebElement getItemDescriptionWebElement(String sItemName) {
        String xPath = createXpathForItemDescriptionInInventoryList(sItemName);
        return getNestedWebElement(inventoryList, By.xpath(xPath));
    }

    public String getItemDescriptionInInventoryList(String sItemName) {
        log.debug("getItemDescriptionInInventoryList(" + sItemName + ")");
        WebElement element = getItemDescriptionWebElement(sItemName);
        return getTextFromWebElement(element);
    }

    private WebElement getItemPriceWebElement(String sItemName) {
        String xPath = createXpathForItemPriceInInventoryList(sItemName);
        return getNestedWebElement(inventoryList, By.xpath(xPath));
    }

    public String getItemPriceInInventoryList(String sItemName) {
        log.debug("getItemPriceInInventoryList(" + sItemName + ")");
        WebElement element = getItemPriceWebElement(sItemName);
        return getTextFromWebElement(element);
    }

    private WebElement getAddToCartButtonWebElement(String sItemName) {
        String xPath = createXpathForAddToCartButtonInInventoryList(sItemName);
        return getNestedWebElement(inventoryList, By.xpath(xPath));
    }

    public String getAddToCartButtonTitle(String sItemName) {
        log.debug("getAddToCartButtonTitle(" + sItemName + ")");
        WebElement element = getAddToCartButtonWebElement(sItemName);
        return getTextFromWebElement(element);
    }

    public void clickAddToCartButton(String sItemName) {
        log.debug("clickAddToCartButton(" + sItemName + ")");
        WebElement element = getAddToCartButtonWebElement(sItemName);
        clickOnWebElement(element);
    }

    private WebElement getRow(String sUsername) {
        List<WebElement> rows = getWebElements(By.xpath("//table[@id='users-table']/tbody/tr"));
        int index = 0;
        for(int i = 1; i <= rows.size(); i++) {
            WebElement username = getNestedWebElement(rows.get(i), By.xpath("//td[1]"));
            if (username.getText().equals(sUsername)) {
                index = i;
                break;
            }
        }
        if (index > 0) {
            return getWebElement(By.xpath("//table[@id='users-table']/tbody/tr[" + index + "]"));
        }
        else {
            return null;
        }
    }

    public String getDisplayName(String sUsername) {
        WebElement row = getRow(sUsername);
        Assert.assertNotNull(row, "User with Username '" + sUsername + "' doesn't exist in Users table");
        WebElement displayName = getNestedWebElement(row, By.xpath(".//td[2]"));
        return displayName.getText();
    }

    public int getHeroCount(String sUsername) {
        WebElement row = getRow(sUsername);
        Assert.assertNotNull(row, "User with Username '" + sUsername + "' doesn't exist in Users table");
        WebElement heroCount = getNestedWebElement(row, By.xpath(".//td[3]"));
        String sHeroCount = heroCount.getText();
        return Integer.parseInt(sHeroCount);
    }

    public void clickHeroCount(String sUsername) {
        WebElement row = getRow(sUsername);
        Assert.assertNotNull(row, "User with Username '" + sUsername + "' doesn't exist in Users table");
        WebElement heroCount = getNestedWebElement(row, By.xpath(".//td[3]"));
        clickOnWebElement(heroCount);
        //UserHeroes userHeroes = new UserHeroes(driver);
        //return userHeroes.verifyDialogBox();
    }

    public void clickUserDetails(String sUsername) {
        WebElement row = getRow(sUsername);
        Assert.assertNotNull(row, "User with Username '" + sUsername + "' doesn't exist in Users table");
        WebElement userDetails = getNestedWebElement(row, By.xpath(".//td[4]/a[contains(@class, 'btn-info')]"));
        clickOnWebElement(userDetails);
        //UserDetails userDetails = new UserDetails(driver);
        //return userDetails.verifyDialogBox();
    }


}
