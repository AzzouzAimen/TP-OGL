import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/features",
        glue = "stepDefinitions",
        plugin = {
                "pretty",
                "html:test-reports/cucumber-report.html",
        },
        monochrome = true
)
public class TestRunner {
}
