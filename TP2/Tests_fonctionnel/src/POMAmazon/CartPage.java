package POMAmazon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends PageObjet {

    // Locators
    private final By productTitleInCartXpath(String productName) {
        return By.xpath("//span[@class='a-truncate-cut' and contains(text(),'" + productName + "')]");
    }
    private final By quantityInCart = By.cssSelector("span[data-a-selector='value']");
    private final By priceInCart = By.cssSelector(".sc-list-item-content .sc-product-price");


    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isProductInCart(String productName) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(productTitleInCartXpath(productName))).isDisplayed();
    }

    public String getQuantity() {
        return driver.findElement(quantityInCart).getText();
    }

    public String getPrice() {
        return driver.findElement(priceInCart).getText();
    }
}
