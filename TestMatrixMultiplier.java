import org.junit.Test;
import static org.junit.Assert.*;

public class TestMatrixMultiplier {

    engine3D.vec3d v1 = new engine3D.vec3d(1,2,3);
    engine3D.vec3d v2 = new engine3D.vec3d(1,5,7);
    engine3D.vec3d v3 = new engine3D.vec3d(0,1,0);
    engine3D.vec3d v4 = new engine3D.vec3d(0,0,1);


    @Test
    public void testMatrixMultiplier() {


    double[][] m1 = {{1,2},{3,4}};
    double[][] m2 = {{5, 6},{0, 7}};
    double[][] res = {{5,20},{15,46}};
    double[][] m = engine3D.matrixMultiplier(m1, m2);

    assertArrayEquals(res, m);

//        double theta = 90;
//        double far = 1000;
//        double near = 0.1;
//        double canvasRatio = StdDraw.height/StdDraw.width;
//        double transXY = (1/(Math.tan(theta*0.5*3.14159/180)));
//        double transZ = far/(far-near);

//    double[][] transformMatrix = {
//            {canvasRatio * transXY,0,0,0},
//            {0,transXY,0,0},
//            {0,0,transZ,1},
//            {0,0,-near * transZ,0}
//    };

//    double[][] m3 = {{1,1,0,1}};
//    double[][] m4 = {{1,1,1,1}};
//
//    double[][] a = engine3D.matrixMultiplier(m3, transformMatrix);
//    double[][] b = engine3D.matrixMultiplier(m4, transformMatrix);
//
//    assertEquals(a[0][3], b[0][3],0.001);
    }

    @Test
    public void testProjection() {


//        double fNear = 0.1;
//        double fFar = 1000.0;
//        double fFov = 90.0;
//        double fAspectRatio = StdDraw.height/StdDraw.width;
//        double fFovRad = 1.0 / Math.tan(fFov * 0.5 / 180.0 * 3.14159);

//        double[][] m0 = new double[4][4];
//
//        m0[0][0] = fAspectRatio * fFovRad;
//        m0[1][1] = fFovRad;
//        m0[2][2] = fFar / (fFar - fNear);
//        m0[3][2] = (-fFar * fNear) / (fFar - fNear);
//        m0[2][3] = 1.0f;
//        m0[3][3] = 0.0f;
//
//
//        engine3D.vec3d temp = new engine3D.vec3d(1, 2, 0 );
//        engine3D.vec3d expected = projectionSolution.MultiplyMatrixVector(temp, m0);
//        engine3D.vec3d actual = engine3D.projection(temp);
//
//
//        assertEquals(expected.x, actual.x, 0.0001);
//        assertEquals(expected.y, actual.y, 0.0001);
//        assertEquals(expected.z, actual.z, 0.0001);

    }

    @Test
    public void testDotProduct() {

        assertEquals(32, engine3D.dotProduct(v1,v2), 0.001);
    }

    @Test
    public void testNormal() {
        engine3D.vec3d actual =  engine3D.crossProduct(v1,v2);

        assertEquals(-1, actual.x, 0.01);
        assertEquals(-4, actual.y, 0.01);
        assertEquals(3, actual.z, 0.01);
    }

    @Test
    public  void testComparator() {
        engine3D.triangle a = new engine3D.triangle(v1,v1,v3);
        engine3D.triangle b = new engine3D.triangle(v1,v1,v4);

//        assertTrue(b > a);

    }








}
