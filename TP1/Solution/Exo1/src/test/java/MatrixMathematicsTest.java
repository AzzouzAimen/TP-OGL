package test.java;

import com.exception.NoSquareException;
import com.model.Matrix;
import com.service.MatrixMathematics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixMathematicsTest {


    @Test
    void MatrixIsNotSquare() {
        Matrix m = new Matrix(1, 3);

        assertThrows(NoSquareException.class, () -> {
            MatrixMathematics.determinant(m);
        });
    }

    @Test
    public void testMatrixEmpty() throws NoSquareException {
        Matrix m = new Matrix(0,0);
        double det = MatrixMathematics.determinant(m);
        assertTrue(det == 0);
    }

    @Test
    public void testDeterminant1x1() throws NoSquareException {
        Matrix m = new Matrix(1, 1);
        m.setValueAt(0, 0, 1);

        double det = MatrixMathematics.determinant(m);

        assertEquals(1.0, det);
    }

    @Test
    public void testDeterminant2x2() throws NoSquareException {
        Matrix m = new Matrix(2, 2);
        m.setValueAt(0, 0, 1);
        m.setValueAt(0, 1, 2);
        m.setValueAt(1, 0, 3);
        m.setValueAt(1, 1, 4);

        double det = MatrixMathematics.determinant(m);

        assertEquals(-2.0, det);
    }


    @Test
    public void testDeterminant3x3() throws NoSquareException {
        Matrix m = new Matrix(3, 3);
        m.setValueAt(0,0,6);  m.setValueAt(0,1,1);  m.setValueAt(0,2,1);
        m.setValueAt(1,0,4);  m.setValueAt(1,1,-2); m.setValueAt(1,2,5);
        m.setValueAt(2,0,2);  m.setValueAt(2,1,8);  m.setValueAt(2,2,7);

        double det = MatrixMathematics.determinant(m);

        assertEquals(-306, det);

    }

}