package org.example;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AppTest {
    private AppiumDriver appiumDriver;
    private WebDriverWait webDriverWait;
    private DesiredCapabilities desiredCapabilities;

    @BeforeClass
    public void setup() throws MalformedURLException {

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


        System.out.println("The result is: " + result);
    }

    @Test
    public void testWebAppium() throws InterruptedException {
        appiumDriver.navigate().to("https://www.google.com");
        appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q"))).sendKeys("Hello world on Java");
        Thread.sleep(Duration.ofSeconds(2).toMillis());
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q"))).sendKeys(Keys.ENTER);
        Thread.sleep(Duration.ofSeconds(4).toMillis());
    }

    @AfterTest
    public void teardown() {
        appiumDriver.close();
        appiumDriver.quit();
    }
}
