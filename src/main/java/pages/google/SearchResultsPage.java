package pages.google;

import org.appium.handler.DriverHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchResultsPage {
    private DriverHandler driverHandler;

    public SearchResultsPage(WebDriver driver){
        driverHandler = new DriverHandler(driver);
    }

    public boolean pageByLinkTextIsDisplayed(String linkText){
        //By locator = By.linkText(linkText);
        By locator = By.xpath("//div[text() = '"+linkText+"' and @role='link']");
        return driverHandler.elementByLocatorIsVisible(locator);
    }
}
