import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class TestAmazon {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        //driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.amazon.com");
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    void testBuyItem() throws InterruptedException {

        WebElement searchInput = driver.findElement(By.id("twotabsearchtextbox"));
        WebElement searchDropdownBox = driver.findElement(By.id("searchDropdownBox"));
        Select categorySelect = new Select(searchDropdownBox);
        categorySelect.selectByVisibleText("Books");
        searchInput.sendKeys("python in easy steps");
        // Wait for suggestions to appear and find the one with matching text
        WebElement selectFirst = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='s-suggestion s-suggestion-ellipsis-direction' and contains(text(), 'python in easy steps')]")
        ));
        selectFirst.click();

        // Wait for and click on the book
        WebElement book = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[text()='Python in easy steps']")
        ));
        book.click();

        WebElement quantityDropdownTrigger = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("span.a-button-dropdown")
        ));
        quantityDropdownTrigger.click();

        WebElement quantityOption2 = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("quantity_1")
        ));
        quantityOption2.click();

        WebElement addToCarte = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("add-to-cart-button")
        ));
        addToCarte.click();

        WebElement addedToCartMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("h1.a-size-medium-plus.a-color-base.sw-atc-text.a-text-bold")
        ));
        assertEquals("Added to cart", addedToCartMessage.getText().trim());


        WebElement goToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#sw-gtc a, #nav-cart")
        ));
        goToCartButton.click();

        WebElement bookTitleInCart = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[@class='a-truncate-cut' and contains(text(),'Python in easy steps')]")
        ));
        assertTrue(bookTitleInCart.isDisplayed(), "Book title is not visible in the cart.");
        System.out.println("SUCCESS: Book title is present in the cart.");


        WebElement quantityInCart = driver.findElement(By.cssSelector("span[data-a-selector='value']"));
        assertEquals("2", quantityInCart.getText(), "The quantity in the cart is not correct.");
        System.out.println("SUCCESS: Quantity is correct.");



        WebElement priceInCart = driver.findElement(By.cssSelector(".sc-list-item-content .sc-product-price"));
        assertTrue(priceInCart.isDisplayed(), "Price is not displayed in the cart.");
        assertTrue(priceInCart.getText().contains("$"), "Price text does not contain a currency symbol.");
        System.out.println("SUCCESS: Price is displayed: " + priceInCart.getText());
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
