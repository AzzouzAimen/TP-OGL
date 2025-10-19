package POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class WikipediaHomePage extends PageObject {

    @FindBy(id = "searchInput")
    private WebElement searchInput;

    @FindBy(id = "searchLanguage")
    private WebElement languageDropdown;

    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;

    public WikipediaHomePage(WebDriver driver) {
        super(driver);
    }

    public ResultPage performSearch(String searchTerm, String language) {
        searchInput.sendKeys(searchTerm);

        Select languageSelect = new Select(languageDropdown);
        languageSelect.selectByVisibleText(language);

        searchButton.click();

        return new ResultPage(driver);
    }
}