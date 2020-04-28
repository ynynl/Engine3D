import java.util.*;

public class engine3D {

    static public class vec3d {
        double x,y,z;

        public vec3d(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    static public class triangle implements Comparable <triangle> {
        vec3d a, b, c;
        int[] col;

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

        @Override
        public int compareTo(triangle o) {
            double tc = (this.a.z + this.b.z + this.c.z)/3;
            double oc = (o.a.z + o.b.z + o.c.z)/3;
            return tc < oc ? 1 : (tc == oc ? 0 : -1);
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

        vec3d v0 = new vec3d(0,0,0);
        vec3d v1 = new vec3d(0,0,2);
        vec3d v2 = new vec3d(0,1,0);
        vec3d v3 = new vec3d(0,1,2);
        vec3d v4 = new vec3d(1,0,0);
        vec3d v5 = new vec3d(1,0,2);
        vec3d v6 = new vec3d(1,1,0);
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
        return new vec3d(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
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

      /*瞎搞*/
//    private static vec3d projection(vec3d o) {
//
//        double theta = 90;
//        double canvasRatio = StdDraw.height/StdDraw.width;
//        double transXY = (1/(Math.tan(theta*0.5*3.1415927/180)));
//
//
//        double[][] transformMatrix = {
//                {canvasRatio * transXY,0,0},
//                {0,transXY,0},
//                {0,0,1}
//        };
//
//        double offsetZ = 1000;
//
//        double[][] m = {{o.x,o.y,o.z + offsetZ}};
//
//        if (m[0][2] < 0) {
//            m[0][2] *= -2;
//            transformMatrix[0][0] *= 2;
//            transformMatrix[1][1] *= 2;
//        }
//        /*瞎搞*/
//
//        double[] m4 =  matrixMultiplier(m, transformMatrix)[0];
//
//        vec3d transformed = new vec3d(m4[0], m4[1], m4[2]);
//
//        if (transformed.z != 0) {
//            transformed.x /= transformed.z;
//            transformed.y /= transformed.z;
//            transformed.z /= transformed.z;
//        }
//
//        /* scale to view. */
//        transformed.x = (transformed.x + .5);
//        transformed.y = (transformed.y + .3);
//
//        return transformed;
//    }

    /*projection*/
 private static vec3d projection(vec3d o) {
        double theta = 90;
        double far = 1000;
        double near = .1;
        double canvasRatio = StdDraw.height/StdDraw.width;
        double transXY = (1/(Math.tan(theta*0.5*3.1415927/180)));
        double transZ = far/(far-near);

        double[][] transformMatrix = {
                {canvasRatio * transXY,0,0,0},
                {0,transXY,0,0},
                {0,0,transZ,1},
                {0,0,-near * transZ,0}
        };

        double offsetZ = 1000;

        double[][] m = {{o.x,o.y,o.z + offsetZ,1}};

        double[] m4 =  matrixMultiplier(m, transformMatrix)[0];

        vec3d transformed = new vec3d(m4[0], m4[1], m4[2]);


        if (m4[3] != 0) {
            transformed.x /= m4[3];
            transformed.y /= m4[3];
            transformed.z /= m4[3];
        }

        /* scale to view. */
        transformed.x = (transformed.x + .5);
//        transformed.y = (transformed.y + .5);

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

        else if (AxisID == 1) {
            mRotation = new double[][]{
                    {Math.cos(Theta), Math.sin(Theta), 0},
                    {-Math.sin(Theta), Math.cos(Theta), 0},
                    {0, 0, 1},
            };
        }

        else if  (AxisID == 2) {
            mRotation = new double[][]{
                    {Math.cos(Theta), 0, Math.sin(Theta)},
                    {0, 1, 0},
                    {-Math.sin(Theta), 0, Math.cos(Theta)},
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
        StdDraw.setPenColor(t.col[0], t.col[1], t.col[2]);
        StdDraw.line(t.a.x, t.a.y, t.b.x, t.b.y);
        StdDraw.line(t.a.x, t.a.y, t.c.x, t.c.y);
        StdDraw.line(t.b.x, t.b.y, t.c.x, t.c.y);
    }

    public static void fillTri(triangle t) {

        double [] x = {t.a.x,t.b.x,t.c.x};
        double [] y = {t.a.y,t.b.y,t.c.y};

        StdDraw.setPenColor(t.col[0], t.col[1], t.col[2]);
        StdDraw.filledPolygon(x, y);
    }

    public static mesh readOBJ(String f) {
//        HashMap<Integer, vec3d> obj = new HashMap<>();
        ArrayList<vec3d> obj = new ArrayList<>();
        mesh readMesh = new mesh();

        In in;
        try {
            in = new In(f);
            int i = 0;
            while (!in.isEmpty()) {
                String s = in.readLine();
                i +=1;

                if (s.equals("")) {
                    continue;
                }

                String arr[] = s.split(" ");
                String header = (arr[0]);
                if (header.equals("v")) {
                    obj.add(new vec3d(
                            Double.parseDouble(arr[1]),
                            Double.parseDouble(arr[2]),
                            Double.parseDouble(arr[3])
                            )
                    );

//                    System.out.println(i + ": " + arr[1] + " " + arr[2] + " " + arr[3]);
                }
                else if (header.equals("f")) {
                    int va = Integer.parseInt(arr[1].split("/")[0])-1;
                    int vb = Integer.parseInt(arr[2].split("/")[0])-1;
                    int vc = Integer.parseInt(arr[3].split("/")[0])-1;
                    readMesh.tris.add(new triangle(obj.get(va),obj.get(vb),obj.get(vc)));

//                    System.out.println(va+" " + vb+" " + vc +" ");
                }
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        return readMesh;
    }

    public static void main(String[] args) {

        StdDraw.setCanvasSize(StdDraw.width+100, StdDraw.height+100);

//        mesh object = meshCube();
//        mesh object = readOBJ("./cube.obj");
        mesh object = readOBJ("./wolf.obj");

        vec3d camera = new vec3d(0,0,0);

        StdDraw.enableDoubleBuffering();

        while (true) {

            /* set black background*/
            StdDraw.setPenColor(204,204,204);
            StdDraw.filledSquare(0, 0, 1);

            int i = 0;

            if (StdDraw.isKeyPressed('W')) {
                for (triangle tri : object.tris) {
                    tri.rotateX(+0.1);
                }
            }

            if (StdDraw.isKeyPressed('S')) {
                for (triangle tri : object.tris) {
                    tri.rotateX(-0.1);
                }
            }
            if (StdDraw.isKeyPressed('A')) {
                for (triangle tri : object.tris) {
                    tri.rotateY(-0.1);
                }
            }
            if (StdDraw.isKeyPressed('D')) {
                for (triangle tri : object.tris) {
                    tri.rotateY(+0.1);

                }
            }

            ArrayList<triangle> sortT = new ArrayList();
            for (triangle tri : object.tris) {

                tri.rotateX(0.002);
                tri.rotateY(-0.005);
                tri.rotateZ(0.001);

                triangle transformed = tri.project();

                vec3d o = line(transformed.a, camera);

                if (dotProduct(o, transformed.aNormal()) < 0) {
//                    drawTri(transformed);

//                    System.out.println(dotProduct(o, transformed.aNormal()));
                    double light1 = dotProduct(new vec3d(-1,-1,1), tri.aNormal()); //-1 ~ 0
//                    double light2 = -dotProduct(new vec3d(1,0,1), tri.aNormal()); //-1 ~ 0

                    int red = (int)(140 - 60 * light1);
                    int green = (int)(160 - 40 * light1);
                    int blue = (int) (130 - 60 * light1);

                    transformed.col = new int[]{red, green, blue};
                    sortT.add(transformed);
                }
            }

            Collections.sort(sortT);

            for (triangle sorted : sortT) {
                fillTri(sorted);
                drawTri(sorted);
            }

            StdDraw.show();
            StdDraw.pause(10);
        }
    }
}


//
//                System.out.println(camera.x);

