package POMAmazon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class HomePage extends PageObjet {

    // Locators
    private final By searchInput = By.id("twotabsearchtextbox");
    private final By searchDropdownBox = By.id("searchDropdownBox");
    private final By searchSuggestionXpath(String suggestionText) {
        return By.xpath("//div[@class='s-suggestion s-suggestion-ellipsis-direction' and contains(text(), '" + suggestionText + "')]");
    }

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Public methods to interact with the page
    public void selectCategory(String category) {
        Select categorySelect = new Select(driver.findElement(searchDropdownBox));
        categorySelect.selectByVisibleText(category);
    }

    public void enterSearchTerm(String term) {
        driver.findElement(searchInput).sendKeys(term);
    }

    public SearchResultsPage selectSearchSuggestion(String suggestionText) {
        WebElement suggestion = wait.until(ExpectedConditions.presenceOfElementLocated(searchSuggestionXpath(suggestionText)));
        suggestion.click();
        return new SearchResultsPage(driver);
    }
}
