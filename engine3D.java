import java.util.Vector;

public class engine3D {

    static public class vec3d {
        double x,y,z;

        public vec3d(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    static public class triangle {
        vec3d a, b, c;

        public triangle(vec3d a, vec3d b, vec3d c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public triangle project() {
            return new triangle(projection(this.a), projection(this.b), projection(this.c));
        }

        public void rotateX(double Theta) {
            this.a = rotation(this.a, Theta,0);
            this.b = rotation(this.b, Theta,0);
            this.c = rotation(this.c, Theta,0);
        }

        public void rotateZ(double Theta) {
            this.a = rotation(this.a, Theta,1);
            this.b = rotation(this.b, Theta,1);
            this.c = rotation(this.c, Theta,1);
        }

        public void rotateY(double Theta) {
            this.a = rotation(this.a, Theta,2);
            this.b = rotation(this.b, Theta,2);
            this.c = rotation(this.c, Theta,2);
        }

        public vec3d aNormal() {

            /* cross product of ab and bc*/
            vec3d ab = line(a,b);
            vec3d ac = line(a,c);

            vec3d normal = crossProduct(ab, ac);

            double length = Math.sqrt(normal.x * normal.x + normal.y * normal.y + normal.z * normal.z);

            normal.x /= length;
            normal.y /= length;
            normal.z /= length;

            return normal;
        }
    }

    static public class mesh {
        Vector<triangle> tris = new Vector<triangle>();
    }

    mesh object;
    vec3d camera;

    public static mesh meshCube() {
//        vec3d v0 = new vec3d(0,0,0);
//        vec3d v1 = new vec3d(0,0,1);
//        vec3d v2 = new vec3d(0,1,0);
//        vec3d v3 = new vec3d(0,1,1);
//        vec3d v4 = new vec3d(1,0,0);
//        vec3d v5 = new vec3d(1,0,1);
//        vec3d v6 = new vec3d(1,1,0);
//        vec3d v7 = new vec3d(1,1,1);

        vec3d v0 = new vec3d(0,0,-1);
        vec3d v1 = new vec3d(0,0,2);
        vec3d v2 = new vec3d(0,1,-1);
        vec3d v3 = new vec3d(0,1,2);
        vec3d v4 = new vec3d(1,0,-1);
        vec3d v5 = new vec3d(1,0,2);
        vec3d v6 = new vec3d(1,1,-1);
        vec3d v7 = new vec3d(1,1,2);

        mesh cube = new mesh();

        /* south*/
        cube.tris.add(new triangle(v1,v5,v7));
        cube.tris.add(new triangle(v1,v7,v3));

        /* east*/
        cube.tris.add(new triangle(v5,v4,v6));
        cube.tris.add(new triangle(v5,v6,v7));


        /* north*/
        cube.tris.add(new triangle(v4,v0,v2));
        cube.tris.add(new triangle(v4,v2,v6));

        /* west*/
        cube.tris.add(new triangle(v0,v1,v3));
        cube.tris.add(new triangle(v0,v3,v2));

        /* top*/
        cube.tris.add(new triangle(v3,v7,v6));
        cube.tris.add(new triangle(v3,v6,v2));

        /* bottom*/
        cube.tris.add(new triangle(v0,v4,v5));
        cube.tris.add(new triangle(v0,v5,v1));

        return cube;
    }

    private static vec3d line(vec3d v1, vec3d v2) {
        return new vec3d(v1.x - v2.x, v1.y - v2.y, v1.z - v1.z);
    };

    public static vec3d crossProduct(vec3d startL1, vec3d endL1, vec3d startL2, vec3d endL2) {

        /* cross product of ab and bc*/
        vec3d L1 = line(startL1, endL1);
        vec3d L2 = line(startL2, endL2);

        return crossProduct(L1, L2);
    }

    public static vec3d crossProduct(vec3d L1, vec3d L2) {

        return new vec3d(
                L1.y * L2.z - L1.z * L2.y,
                L1.z * L2.x - L1.x * L2.z,
                L1.x * L2.y - L1.y * L2.x
        );
    }

    public static double dotProduct(vec3d v1, vec3d v2) {

        double [][] m1 = {
                {v1.x, v1.y, v1.z}
        };

        double [][] m2 = {
                {v2.x},
                {v2.y},
                {v2.z}
        };

        return matrixMultiplier(m1, m2)[0][0];
    }

    private static vec3d projection(vec3d o) {
        double theta = 90;
//        double far = 1000;
//        double near = 0;
        double canvasRatio = StdDraw.height/StdDraw.width;
        double transXY = (1/(Math.tan(theta*0.5*3.1415927/180)));
//        double transZ = far/(far-near);

//        double[][] transformMatrix = {
//                {canvasRatio * transXY,0,0,0},
//                {0,transXY,0,0},
//                {0,0,transZ,1},
//                {0,0,-near * transZ,0}
//        };

//        double[][] transformMatrix = {
//                {canvasRatio * transXY,0,0,0},
//                {0,transXY,0,0},
//                {0,0,transZ,1},
//                {0,0,0,0}
//        };

        double[][] transformMatrix = {
                {canvasRatio * transXY,0},
                {0,transXY},
        };

        double offsetZ = 5;

//        double[][] m = {{o.x,o.y,o.z + offsetZ,1}};
        double[][] m = {{o.x,o.y}};


        double[] m4 =  matrixMultiplier(m, transformMatrix)[0];

//        vec3d transformed = new vec3d(m4[0], m4[1] ,m4[2]);
        vec3d transformed = new vec3d(m4[0], m4[1], o.z + offsetZ);


        if (o.z + offsetZ != 0) {
//            transformed.x /= Math.abs(m4[3]);
//            transformed.y /= Math.abs(m4[3]);
            transformed.x /= o.z + offsetZ;
            transformed.y /= o.z + offsetZ;
//            transformed.z /= Math.abs(m4[3]);
//            transformed.x /= m4[3];
//            transformed.y /= m4[3];
//            transformed.z /= m4[3];
        }

//        if (m4[2] != 0) {
//            transformed.x /= m4[2];
//            transformed.y /= m4[2];
//            transformed.z /= m4[2];
//        }

//        else {
//            transformed.y = (transformed.y > 0)? far: -far;
//            transformed.x = (transformed.x > 0)? far: -far;
//        }

//        if (offsetZ != 0) {
//            transformed.x /= offsetZ;
//            transformed.y /= offsetZ;
//            transformed.z /= offsetZ;
//        }

        /* scale to view. */
        transformed.x = (transformed.x + .5);
        transformed.y = (transformed.y + .5);

        return transformed;
    }

    private static vec3d rotation(vec3d o, double Theta, int AxisID) { // x: 0, y: 1, z:2

        double[][] matrix = {
                {o.x, o.y, o.z}
        };

        double[][] mRotation = new double[0][];

        if (AxisID == 0) {
            mRotation = new double[][]{
                    {1, 0, 0},
                    {0, Math.cos(Theta), Math.sin(Theta)},
                    {0, -Math.sin(Theta), Math.cos(Theta)},
            };
        }

        else if  (AxisID == 1) {
            mRotation = new double[][]{
                    {Math.cos(Theta), 0, Math.sin(Theta)},
                    {0, 1, 0},
                    {-Math.sin(Theta), 0, Math.cos(Theta)},
            };
        }

        else if (AxisID == 2) {
            mRotation = new double[][]{
                    {Math.cos(Theta), Math.sin(Theta), 0},
                    {-Math.sin(Theta), Math.cos(Theta), 0},
                    {0, 0, 1},
            };
        }

        double[] m3 =  matrixMultiplier(matrix, mRotation)[0];
        return new vec3d(m3[0], m3[1], m3[2]);
    }

    static public double[][] matrixMultiplier(double[][] m1, double[][] m2) {

        double[][] res = new double[m1.length][m2[0].length];

        for (int c = 0; c < m2[0].length; c+=1) {
            for (int r = 0; r < m1.length; r+=1) {
                for (int i = 0; i < m1[0].length; i+=1) {
                    res[r][c] += m1[r][i] * m2[i][c];
                }
            }
        }
        return res;
    }

    public static void drawTri(triangle t) {
        StdDraw.line(t.a.x, t.a.y, t.b.x, t.b.y);
        StdDraw.line(t.a.x, t.a.y, t.c.x, t.c.y);
        StdDraw.line(t.b.x, t.b.y, t.c.x, t.c.y);
    }

    public static void main(String[] args) {

        mesh object = meshCube();
        vec3d camera = new vec3d(0,0,0);

        StdDraw.enableDoubleBuffering();

        while (true) {

            /* set black background*/
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledSquare(0, 0, 1);

            for (triangle tri : object.tris) {

                StdDraw.setPenColor(StdDraw.GRAY);

                tri.rotateX(0.002);
                tri.rotateY(-0.005);
                tri.rotateZ(0.001);

                triangle transformed = tri.project();

                vec3d o = line(transformed.a, camera);
                vec3d c = new vec3d(transformed.a.x - camera.x,transformed.a.y - camera.y,transformed.a.z - camera.z );

                if (dotProduct(c, transformed.aNormal()) < 0) {
                    drawTri(transformed);
                }

                if (dotProduct(o, transformed.aNormal()) < 0) {
                    drawTri(transformed);
                }
            }

            StdDraw.show();
            StdDraw.pause(10);
        }
    }
}


//                if (StdDraw.isKeyPressed(69)) {
//                    camera.y +=0.1;
//                };
//                if (StdDraw.isKeyPressed(68)) {
//                    camera.y -=0.1;
//                };

//                if (StdDraw.isKeyPressed('A')) {
//                    camera.x -=0.1;
//                    System.out.print('A');
//                    System.out.println();
//
//
//                };
//                if (StdDraw.isKeyPressed('D')) {
//                    camera.x +=0.1;
//                    System.out.println('D');
//                    System.out.println();
//                };
//
//                System.out.println(camera.x);

