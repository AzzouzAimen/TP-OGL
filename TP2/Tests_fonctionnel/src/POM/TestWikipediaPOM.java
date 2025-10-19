package POM;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestWikipediaPOM {
    private WebDriver driver;

    @BeforeEach
    void init() {
        driver = new ChromeDriver();
        driver.get("https://wikipedia.org");
    }

    @Test
    void testSearchWithPOM() {
        //Create an object
        WikipediaHomePage homePage = new WikipediaHomePage(driver);
        //return the next page object.
        ResultPage resultPage = homePage.performSearch("Mutation testing", "English");
        //Use the result page object to get information.
        String actualTitle = resultPage.getArticleTitle();
        //assert
        String expectedTitle = "Mutation testing";
        assertEquals(expectedTitle, actualTitle, "The page title is not correct!");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}