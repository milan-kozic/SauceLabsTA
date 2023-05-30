package pages;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class DemoPage extends BasePageClass {

    //public final String DEMO_PAGE_URL = "http://demo.seleniumeasy.com/basic-checkbox-demo.html";
    //public final String DEMO_PAGE_URL =  "https://demo.automationtesting.in/Register.html";
    //public final String DEMO_PAGE_URL =  "https://demoqa.com/droppable";
    public final String DEMO_PAGE_URL = "https://demoqa.com/dragabble";

    @FindBy(id="isAgeSelected")
    private WebElement exampleCheckBox;

    @FindBy(xpath="//select[@ng-model='monthbox']")
    private WebElement selectDropDownMenu;

    @FindBy(id="draggable")
    private WebElement draggableElement;

    @FindBy(id="droppable")
    private WebElement dropArea;

    @FindBy(id="dragBox")
    private WebElement dragBox;

    public DemoPage(WebDriver driver) {
        super(driver);
    }

    // Open
    public DemoPage open() {
        return open(true);
    }

    public DemoPage open(boolean bVerify) {
        openUrl(DEMO_PAGE_URL);
        if (bVerify) {
            verifyPage();
        }
        return this;
    }

    public DemoPage verifyPage() {
        waitForUrlChangeToExactUrl(DEMO_PAGE_URL, Time.SHORTER);
        waitUntilPageIsReady(Time.SHORTER);
        return this;
    }

    public boolean isExampleCheckboxDisplayed() {
        log.debug("isExampleCheckboxDisplayed()");
        return isWebElementDisplayed(exampleCheckBox);
    }

    public boolean isExampleCheckboxEnabled() {
        log.debug("isExampleCheckboxEnabled()");
        Assert.assertTrue(isExampleCheckboxDisplayed(), "Example CheckBox is NOT displayed on DemoPage!");
        return isWebElementEnabled(exampleCheckBox);
    }

    public boolean isExampleCheckboxChecked() {
        log.debug("isExampleCheckboxChecked()");
        Assert.assertTrue(isExampleCheckboxDisplayed(), "Example CheckBox is NOT displayed on DemoPage!");
        return isWebElementSelected(exampleCheckBox);
    }

    private void clickExampleCheckbox() {
        Assert.assertTrue(isExampleCheckboxEnabled(), "Example CheckBox is NOT enabled on DemoPage!");
        clickOnWebElement(exampleCheckBox);
    }

    public DemoPage checkExampleCheckbox() {
        log.debug("checkExampleCheckbox()");
        if (!isExampleCheckboxChecked()) {
            clickExampleCheckbox();
        }
        return this;
    }

    public DemoPage uncheckExampleCheckbox() {
        log.debug("uncheckExampleCheckbox()");
        if (isExampleCheckboxChecked()) {
            clickExampleCheckbox();
        }
        return this;
    }

    public boolean isSelectDropDownMenuDisplayed() {
        log.debug("isSelectDropDownMenuDisplayed()");
        return isWebElementDisplayed(selectDropDownMenu);
    }

    public boolean isSelectDropDownMenuEnabled() {
        log.debug("isSelectDropDownMenuEnabled()");
        Assert.assertTrue(isSelectDropDownMenuDisplayed(), "Select DropDown Menu is NOT displayed on DemoPage!");
        return isWebElementEnabled(exampleCheckBox);
    }

    public String getSelectedOptionFromDropDownMenu() {
        log.debug("getSelectedOptionFromDropDownMenu()");
        return getFirstSelectedOptionOnWebElement(selectDropDownMenu);
    }

    public DemoPage selectOptionFromDropDownMenu(String sOption) {
        log.debug("selectOptionFromDropDownMenu(" + sOption + ")");
        selectOptionOnWebElement(selectDropDownMenu, sOption);
        return this;
    }

    public void selectRadioGroupOption(String sOption) {
        String xPath = "//input[@name='radiooptions' and @value='" + sOption + "']";
        WebElement option = getWebElement(By.xpath(xPath));
        clickOnWebElement(option);
    }

    public void dragAndDropBox() {
        doDragAndDrop(draggableElement, dropArea);
    }

    public void moveBox(int x, int y) {
        doDragAndDropBy(dragBox, x, y);
    }
}
