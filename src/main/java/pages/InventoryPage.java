package pages;

import data.PageUrlPaths;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.PropertiesUtils;

public class InventoryPage {

    private WebDriver driver;

    public final String INVENTORY_PAGE_URL = PropertiesUtils.getBaseUrl() + PageUrlPaths.INVENTORY_PAGE;

    // Locators
    By inventoryPageTitleLocator = By.xpath("//div[@id='header_container']//span[@class='title']");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getInventoryPageTitle() {
        WebElement inventoryPageTitle = driver.findElement(inventoryPageTitleLocator);
        return inventoryPageTitle.getText();
    }
}
