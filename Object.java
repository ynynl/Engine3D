import java.util.Vector;

public class Object {

    static public class vec3d {
        float x,y,z;

        public vec3d(float x, float y, float z) {
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
    }

    static public class mesh {
        Vector<triangle> tris = new Vector<triangle>();
    }

    static mesh creation = meshCube();

    public static mesh meshCube() {
        vec3d v0 = new vec3d(0,0,0);
        vec3d v1 = new vec3d(0,0,1);
        vec3d v2 = new vec3d(0,1,0);
        vec3d v3 = new vec3d(0,1,1);
        vec3d v4 = new vec3d(1,0,0);
        vec3d v5 = new vec3d(1,0,1);
        vec3d v6 = new vec3d(1,1,0);
        vec3d v7 = new vec3d(1,1,1);

        mesh cube = new mesh();
        cube.tris.add(new triangle(v1,v7,v5));
        cube.tris.add(new triangle(v1,v3,v7));
        cube.tris.add(new triangle(v3,v6,v7));
        cube.tris.add(new triangle(v3,v2,v6));
        cube.tris.add(new triangle(v5,v6,v4));
        cube.tris.add(new triangle(v5,v7,v6));
        cube.tris.add(new triangle(v0,v5,v4));
        cube.tris.add(new triangle(v0,v1,v5));
        cube.tris.add(new triangle(v0,v4,v6));
        cube.tris.add(new triangle(v0,v6,v2));
        cube.tris.add(new triangle(v0,v3,v1));
        cube.tris.add(new triangle(v0,v2,v3));

        return cube;
    }

    public static vec3d transform(vec3d o) {
        float theta = 90;
        float far = 1000;
        float near = 10;
        float canvasRatio = StdDraw.height/StdDraw.width;
        float transXY = (float) (1/(Math.tan(theta/2*3.1415926/180)));
        float transZ = far/(far-near);

        float[][] transformMatrix = {
                {canvasRatio * transXY,0,0,0},
                {0,transXY,0,0},
                {0,0,transZ,1},
                {0,0,-transZ*near,0}
        };

        float[][] m = {{o.x,o.y,o.z,1}};

        float[] m4 =  matrixMultiplier(m, transformMatrix)[0];

        vec3d transformed = new vec3d(m4[0], m4[1], m4[2]);

        if (m4[3] != 0) {
            transformed.x /= m4[3];
            transformed.y /= m4[3];
            transformed.z /= m4[3];
        }

        return transformed;
    }

    static public float[][] matrixMultiplier(float[][] m1, float[][] m2) {
        float[][] res = new float[m1.length][m2[0].length];

        for (int c = 0; c < m2[0].length; c+=1) {
            for (int r = 0; r < m1.length; r+=1) {
                for (int i = 0; i < m1[0].length; i+=1) {
                        res[r][c] += m1[r][i] * m2[i][c];
                    }
                }
            }
        return res;
    };

    public static void drawTri(triangle t) {
        StdDraw.line(t.a.x, t.a.y, t.b.x, t.b.y);
        StdDraw.line(t.a.x, t.a.y, t.c.x, t.c.y);
        StdDraw.line(t.b.x, t.b.y, t.c.x, t.c.y);
    }


    public static void main(String[] args) {

        for (triangle tri : creation.tris) {
            triangle transformedT = new triangle(transform(tri.a), transform(tri.b), transform(tri.c));
            drawTri(transformedT);
        }

    }
}


