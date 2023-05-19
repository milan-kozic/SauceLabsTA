package data;

import org.testng.Assert;
import utils.PropertiesUtils;

import java.util.Properties;

public final class CommonStrings {

    private static final String sLocaleFile = "locale_" + PropertiesUtils.getLocale() + ".loc";
    private static final String sLocalePath = "\\locale\\" + sLocaleFile;

    private static final Properties locale = PropertiesUtils.readPropertiesFile(sLocalePath);

    private static String getLocaleString(String sTitle) {
        String sText = locale.getProperty(sTitle);
        Assert.assertNotNull(sText, "String " + sTitle + " doesn't exist in file " + sLocaleFile + "!");
        return sText;
    }


    public static String getLoginErrorMessageWrongCredentials() {
        return getLocaleString("LOGIN_ERROR_WRONG_CREDENTIALS");
    }

    public static String getInventoryPageTitle() {
        return getLocaleString("INVENTORY_PAGE_TITLE");
    }
}
