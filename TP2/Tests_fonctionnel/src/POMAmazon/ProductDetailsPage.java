package POMAmazon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductDetailsPage extends PageObjet {

    // Locators
    private final By quantityDropdownTrigger = By.cssSelector("span.a-button-dropdown");
    private final By quantityOptionId(int quantity) {
        // Amazon's quantity ID is zero-indexed, e.g., quantity '2' has id 'quantity_1'
        return By.id("quantity_" + (quantity - 1));
    }
    private final By addToCartButton = By.id("add-to-cart-button");

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }

    public void selectQuantity(int quantity) {
        wait.until(ExpectedConditions.elementToBeClickable(quantityDropdownTrigger)).click();
        wait.until(ExpectedConditions.elementToBeClickable(quantityOptionId(quantity))).click();
    }

    public AddedToCartPage clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
        return new AddedToCartPage(driver);
    }
}