package utils;

import org.testng.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

public class PropertiesUtils {

    private static final String sTestPropertiesPath = "test.properties";
    private static Properties testProperties = readPropertiesFile(sTestPropertiesPath);

    public static Properties readPropertiesFile(String relativePathToFile) {
        InputStream steam = PropertiesUtils.class.getClassLoader().getResourceAsStream(relativePathToFile);
        Properties properties = new Properties();
        try {
            properties.load(steam);
        } catch (IOException e) {
            Assert.fail("Error reading properties file '" + relativePathToFile + "'! Message: " + e.getMessage());
        }
        return properties;
    }

    private static String getTestProperty(String sProperty) {
        String sResult = testProperties.getProperty(sProperty);
        Assert.assertNotNull(sResult, "Cannot find property '" + sProperty + "' in '" + sTestPropertiesPath + "' file!");
        return sResult;
    }

    public static String getBrowser() {
        return getTestProperty("browser");
    }

    public static String getEnvironment() {
        return getTestProperty("environment");
    }

    public static String getLocale() {
        return getTestProperty("locale");
    }

    private static String getLocalBaseUrl() {
        return getTestProperty("localBaseUrl");
    }

    private static String getTestBaseUrl() {
        return getTestProperty("testBaseUrl");
    }

    private static String getProdBaseUrl() {
        return getTestProperty("prodBaseUrl");
    }

    public static String getBaseUrl() {
        String sEnvironment = getEnvironment();
        String sBaseUrl=null;
        switch (sEnvironment) {
            case "local" : {
                sBaseUrl = getLocalBaseUrl();
                break;
            }
            case "test" : {
                sBaseUrl = getTestBaseUrl();
                break;
            }
            case "prod" : {
                sBaseUrl = getProdBaseUrl();
                break;
            }
            default : {
                Assert.fail("Cannot get BaseUrl! Environment '" + sEnvironment + "' is NOT recognized!");
            }
        }
        return sBaseUrl;
    }

    public static String getUsername() {
        return getTestProperty("username");
    }

    public static String getPassword() {
        return getTestProperty("password");
    }

    public static boolean getRemote() {
        String sRemote = getTestProperty("remote");
        sRemote = sRemote.toLowerCase();
        if(!(sRemote.equals("true") || sRemote.equals("false"))) {
            Assert.fail("Cannot convert 'Remote' property value '" + sRemote + "' to boolean!");
        }
        return Boolean.parseBoolean(sRemote);
    }

    public static boolean getHeadless() {
        String sHeadless = getTestProperty("headless");
        sHeadless = sHeadless.toLowerCase();
        if(!(sHeadless.equals("true") || sHeadless.equals("false"))) {
            Assert.fail("Cannot convert 'Headless' property value '" + sHeadless + "' to boolean!");
        }
        return Boolean.parseBoolean(sHeadless);
    }

    public static String getHubUrl() {
        return getTestProperty("hubUrl");
    }

    public static String getScreenShotsFolder() {
        return getTestProperty("screenshotsFolder");
    }
}
