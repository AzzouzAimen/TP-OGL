package POMAmazon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchResultsPage extends PageObjet {

    // Locator
    private final By productLinkXpath(String productName) {
        return By.xpath("//span[text()='" + productName + "']");
    }

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public ProductDetailsPage clickOnProduct(String productName) {
        WebElement book = wait.until(ExpectedConditions.elementToBeClickable(productLinkXpath(productName)));
        book.click();
        return new ProductDetailsPage(driver);
    }
}