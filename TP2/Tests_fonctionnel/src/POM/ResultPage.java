package POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ResultPage extends PageObject {

    @FindBy(id = "firstHeading")
    private WebElement articleTitle;

    public ResultPage(WebDriver driver) {
        super(driver);
    }

    public String getArticleTitle() {
        // L'élément est déjà disponible
        waitForElementToBeVisible(articleTitle);
        return articleTitle.getText();
    }
}