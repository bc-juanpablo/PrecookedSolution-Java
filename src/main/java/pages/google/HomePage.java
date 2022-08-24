package pages.google;

import org.appium.handler.Actions;
import org.appium.handler.DriverHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private By searchBar = By.name("q");
    private DriverHandler driverHandler;

    public HomePage(WebDriver driver){
        driverHandler = new DriverHandler(driver);
    }

    public void search(String text){
        driverHandler.performAction(Actions.Type,text,searchBar);
        driverHandler.performAction(Actions.EnterKey,searchBar);
    }

    public void waitForPage(int secondsToWait) {
        driverHandler.performAction(Actions.Wait,secondsToWait +"");
    }
}
