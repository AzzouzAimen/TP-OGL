package stepDefinitions;

import POMAmazon.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class AmazonSteps {

    private WebDriver driver;
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private ProductDetailsPage productDetailsPage;
    private AddedToCartPage addedToCartPage;
    private CartPage cartPage;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Given("I am on the Amazon homepage")
    public void iAmOnTheAmazonHomepage() {
        driver.get("https://www.amazon.com");
        homePage = new HomePage(driver);
    }

    @When("I select {string} category from the search dropdown")
    public void iSelectCategoryFromSearchDropdown(String category) {
        homePage.selectCategory(category);
    }

    @And("I search for {string}")
    public void iSearchFor(String searchTerm) {
        homePage.enterSearchTerm(searchTerm);
    }

    @And("I click on the first search suggestion")
    public void iClickOnTheFirstSearchSuggestion() {
        searchResultsPage = homePage.selectSearchSuggestion("python in easy steps");
    }

    @And("I click on the {string} book from results")
    public void iClickOnTheBookFromResults(String bookTitle) {
        productDetailsPage = searchResultsPage.clickOnProduct(bookTitle);
    }

    @And("I select quantity {string} from the dropdown")
    public void iSelectQuantityFromTheDropdown(String quantity) {
        productDetailsPage.selectQuantity(Integer.parseInt(quantity));
    }

    @And("I click the {string} button")
    public void iClickTheButton(String buttonName) {
        addedToCartPage = productDetailsPage.clickAddToCart();
    }

    @Then("I should see {string} confirmation message")
    public void iShouldSeeConfirmationMessage(String expectedMessage) {
        assertEquals(expectedMessage, addedToCartPage.getConfirmationMessage(), 
                "Confirmation message is incorrect.");
    }

    @When("I go to the cart")
    public void iGoToTheCart() {
        cartPage = addedToCartPage.goToCart();
    }

    @Then("I should see {string} in the cart")
    public void iShouldSeeInTheCart(String bookTitle) {
        assertTrue(cartPage.isProductInCart(bookTitle), "Book title is not visible in the cart.");
        System.out.println("SUCCESS: Book title is present in the cart.");
    }

    @And("the quantity should be {string}")
    public void theQuantityShouldBe(String expectedQuantity) {
        assertEquals(expectedQuantity, cartPage.getQuantity(), "The quantity in the cart is not correct.");
        System.out.println("SUCCESS: Quantity is correct.");
    }

    @And("the price should be displayed")
    public void thePriceShouldBeDisplayed() {
        String price = cartPage.getPrice();
        assertTrue(price.contains("$"), "Price text does not contain a currency symbol.");
        System.out.println("SUCCESS: Price is displayed: " + price);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
