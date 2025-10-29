package POMAmazon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddedToCartPage extends PageObjet {

    private final By addedToCartMessage = By.cssSelector("h1.a-size-medium-plus.a-color-base.sw-atc-text.a-text-bold");
    private final By goToCartButton = By.cssSelector("#sw-gtc a, #nav-cart");

    public AddedToCartPage(WebDriver driver) {
        super(driver);
    }

    public String getConfirmationMessage() {
        WebElement messageElement = wait.until(ExpectedConditions.presenceOfElementLocated(addedToCartMessage));
        return messageElement.getText().trim();
    }

    public CartPage goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(goToCartButton)).click();
        return new CartPage(driver);
    }
}
