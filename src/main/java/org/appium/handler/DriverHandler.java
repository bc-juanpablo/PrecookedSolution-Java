package org.appium.handler;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DriverHandler {
    private WebDriver driver;
    private WebElement element;
    private WebDriverWait webDriverWait;

    public DriverHandler(WebDriver driver) {
        this.driver = driver;
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        element = null;
    }

    public void performAction(Actions action) {
        performAction(action,null,null,null);
    }

    public void performAction(Actions action, String value) {
        performAction(action, value, null);

    }

    public void performAction(Actions action, By locator) {
        performAction(action,null, null, locator);
    }

    public void performAction(Actions action, String value, By locator) {
        performAction(action, value, null, locator);
    }


    private void performAction(Actions action, String value, By fromLocator, By toLocator) {
        Select select = null;
        org.openqa.selenium.interactions.Actions actions = null;
        JavascriptExecutor js = (JavascriptExecutor) driver;

        switch (action) {
            case Type:
                element = webDriverWait.until(ExpectedConditions.elementToBeClickable(toLocator));
                element.click();
                element.sendKeys(value);
                break;
            case Click:
                webDriverWait.until(ExpectedConditions.elementToBeClickable(toLocator)).click();
                break;
            case DoubleClick:
                element = webDriverWait.until(ExpectedConditions.elementToBeClickable(toLocator));
                actions = new org.openqa.selenium.interactions.Actions(driver);
                actions.doubleClick(element).perform();
                break;
            case SelectByText:
                element = webDriverWait.until(ExpectedConditions.elementToBeClickable(toLocator));
                select = new Select(element);
                select.selectByVisibleText(value);
                break;
            case SelectByValue:
                element = webDriverWait.until(ExpectedConditions.elementToBeClickable(toLocator));
                select = new Select(element);
                select.selectByValue(value);
                break;
            case ScrollDown:
                js.executeScript("window.scrollBy(0," + value + ")", "");
                break;
            case Wait:
                try {
                    int valueAsInteger = Integer.parseInt(value);
                    Thread.sleep(Duration.ofSeconds(valueAsInteger).toMillis());
                } catch (Exception ex) {

                }
                break;
            case ExecuteJavascript:
                performAction(Actions.Wait,"2");
                js.executeScript(value,"");
                break;
            case DragAndDrop:
                actions = new org.openqa.selenium.interactions.Actions(driver);
                element=webDriverWait.until(ExpectedConditions.elementToBeClickable(fromLocator));
                WebElement toElement = webDriverWait.until(ExpectedConditions.elementToBeClickable(toLocator));
                actions.dragAndDrop(element,toElement).build().perform();
                break;
            case EnterKey:
                element = webDriverWait.until(ExpectedConditions.elementToBeClickable(toLocator));
                element.sendKeys(Keys.ENTER);
                break;
        }

    }

    public String getElementText(By locator){
        try{
            WebElement element = webDriverWait.until(ExpectedConditions.elementToBeClickable(locator));
            return element.getText();
        }catch (Exception ex){
            return "";
        }
    }

    public boolean elementByLocatorIsVisible(By locator){
        return elementByLocatorIsVisible(locator, 10);
    }

    private boolean elementByLocatorIsVisible(By locator, int secondsToWait) {
        try {
            WebDriverWait webDriverWait = new WebDriverWait(driver,Duration.ofSeconds(secondsToWait));
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        }catch (Exception ex){
            return false;
        }
    }



}
