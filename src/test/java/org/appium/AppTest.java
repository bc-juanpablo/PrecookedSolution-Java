package org.appium;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import common.csvUtils.CsvUtils;
import pages.google.HomePage;
import pages.google.SearchResultsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AppTest {
    private AppiumDriver appiumDriver;
    private WebDriverWait webDriverWait;
    private DesiredCapabilities desiredCapabilities;

    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private List<Map<String,String>> csvData;
    private CsvUtils csvUtils;


    @BeforeClass
    public void setup() throws IOException {
        String currentDir = Paths.get(System.getProperty("user.dir"),"/CsvFiles","/google","/google.csv").toFile().getPath();
        csvUtils = new CsvUtils();
        csvData = csvUtils.getCsvData(currentDir);
        boolean webExecution = true;
        desiredCapabilities = new DesiredCapabilities();
        if (webExecution) {
            WebDriverManager wdm = WebDriverManager.chromedriver().browserVersion("103");
            wdm.setup();
            String chromedriverPath = wdm.getDownloadedDriverPath();
            desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2");
            desiredCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");
            desiredCapabilities.setCapability("version", "104");
            desiredCapabilities.setCapability("chromedriverExecutable", chromedriverPath);

        } else {
            desiredCapabilities.setCapability("appPackage", "com.google.android.calculator");
            desiredCapabilities.setCapability("appActivity", "com.android.calculator2.Calculator");
            desiredCapabilities.setCapability("noReset ", true);
        }
        //df5d8e7a id emulator-5554
        //edson name sdk_gphone64_x86_64
        desiredCapabilities.setCapability("deviceName", "edson");
        desiredCapabilities.setCapability("udid", "df5d8e7a");
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("platformVersion", "12");

        //
        //
        appiumDriver = new AppiumDriver(new URL("http://localhost:4723/wd/hub"), desiredCapabilities);
        webDriverWait = new WebDriverWait(appiumDriver, Duration.ofSeconds(10));
        System.out.println("Application Started");
        homePage = new HomePage(appiumDriver);
        searchResultsPage = new SearchResultsPage(appiumDriver);
    }

    @DataProvider
    public Iterator<Object[]> getTestData(){
        ArrayList<Object[]> dataIteration = csvUtils.fillDataProviderIterations();
        return dataIteration.iterator();
    }

    @Test
    public void testWithAppium() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("com.google.android.calculator:id/digit_1"))).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("com.google.android.calculator:id/op_add"))).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("com.google.android.calculator:id/digit_1"))).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("com.google.android.calculator:id/eq"))).click();
        //com.google.android.calculator:id/result_final
        appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        String result = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.google.android.calculator:id/result_final"))).getText();

        Assert.assertEquals(result,1+1);
        System.out.println("The result is: " + result);
    }

    @Test(dataProvider = "getTestData")
    public void testWebAppium(int iteration) throws InterruptedException {
        Map<String,String> currentRow = csvData.get(iteration);
        String textToSearch = currentRow.get("SearchText");
        String linkText = currentRow.get("LinkTextToValidate");
        appiumDriver.navigate().to("https://www.google.com");
        homePage.waitForPage(3);
        homePage.search(textToSearch);
        homePage.waitForPage(3);
        Assert.assertTrue(searchResultsPage.pageByLinkTextIsDisplayed(linkText));
    }

    @AfterTest
    public void teardown() {
        appiumDriver.close();
        appiumDriver.quit();
    }
}
