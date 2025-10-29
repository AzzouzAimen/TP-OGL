package POMAmazon;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AmazonBuyTest {
    protected WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.amazon.com");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testBuyItem() {
        HomePage homePage = new HomePage(driver);
        homePage.selectCategory("Books");
        homePage.enterSearchTerm("python in easy steps");
        SearchResultsPage resultsPage = homePage.selectSearchSuggestion("python in easy steps");

        ProductDetailsPage productPage = resultsPage.clickOnProduct("Python in easy steps");

        productPage.selectQuantity(2);
        AddedToCartPage confirmationPage = productPage.clickAddToCart();

        assertEquals("Added to Cart", confirmationPage.getConfirmationMessage(), "Confirmation message is incorrect.");
        CartPage cartPage = confirmationPage.goToCart();

        assertTrue(cartPage.isProductInCart("Python in easy steps"), "Book title is not visible in the cart.");
        assertEquals("2", cartPage.getQuantity(), "The quantity in the cart is not correct.");
        assertTrue(cartPage.getPrice().contains("$"), "Price text does not contain a currency symbol.");

        System.out.println("SUCCESS: Test completed successfully.");
    }
}
