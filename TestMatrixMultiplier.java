import org.junit.Test;
import static org.junit.Assert.*;

public class TestMatrixMultiplier {

    float[][] m1 = {{1,2},{3,4}};
    float[][] m2 = {{5, 6},{0, 7}};

    float[][] res = {{5,20},{15,46}};

    float[][] m = Object.matrixMultiplier(m1, m2);

    @Test
    public void testMatrixMultiplier() {
        assertArrayEquals(res, m);

    }


}
