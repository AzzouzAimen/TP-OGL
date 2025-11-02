package test.java;

import com.exception.NoSquareException;
import com.model.Matrix;
import com.service.MatrixMathematics;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DeterminantSteps {
    private Matrix matrix;
    private double determinant;

    @Given("I have a 3x3 matrix with the following values:")
    public void i_have_a_3x3_matrix_with_the_following_values(DataTable dataTable) {
        List<List<String>> rows = dataTable.asLists(String.class);
        matrix = new Matrix(3, 3);
        
        for (int i = 0; i < rows.size(); i++) {
            List<String> row = rows.get(i);
            for (int j = 0; j < row.size(); j++) {
                matrix.setValueAt(i, j, Double.parseDouble(row.get(j)));
            }
        }
    }

    @When("I calculate the determinant")
    public void i_calculate_the_determinant() throws NoSquareException {
        determinant = MatrixMathematics.determinant(matrix);
    }

    @Then("the determinant should be {double}")
    public void the_determinant_should_be(Double expectedDeterminant) {
        assertEquals(expectedDeterminant, determinant, 0.001);
    }
}
