import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestWikipedia {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void init() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://wikipedia.org");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    void testSearchWithLanguageSelector() {
        WebElement searchInput = driver.findElement(By.id("searchInput"));
        searchInput.sendKeys("Mutation testing");

        // language dropdown element
        WebElement languageDropdownElement = driver.findElement(By.id("searchLanguage"));
        // to select from menu
        Select languageSelect = new Select(languageDropdownElement);
        languageSelect.selectByVisibleText("English");

        // click search
        WebElement searchButton = driver.findElement(By.cssSelector("button[type='submit']"));
        searchButton.click();

        // explicit wait
        WebElement pageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstHeading")));

        // trouver le titre
        String actualTitle = pageTitle.getText();
        String expectedTitle = "Mutation testing";

        // Assert
        assertEquals(expectedTitle, actualTitle, "The page title is not correct!");
    }

    @AfterEach
    void tearDown() {
        try {
            Thread.sleep(2000); // pause for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }
}