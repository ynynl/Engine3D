public class Calculator {
    static public float[][] matrixMultiplier(float[][] m1, float[][] m2) {
        float[][] res = new float[m1.length][m2[0].length];

        for (int i = 0; i < m1.length; i+=1) {
            for (int j = 0; j < m2[0].length; j+=1) {
                for (int h = 0; h < m1[0].length; h+=1) {
                    for (int k = 0; k < m2.length; k+=1) {
                        res[i][j] += m1[i][h] * m2[k][j];
                    }
                }
            }
        }
        return res;
    };
}
