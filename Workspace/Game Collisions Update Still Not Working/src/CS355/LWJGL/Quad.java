package CS355.LWJGL;

/**
 * Created by Jessica on 1/28/2015.
 */
public class Quad {
    private Point3D v1;
    private Point3D v2;
    private Point3D v3;
    private Point3D v4;
    private  Point3D n1;
    private  Point3D n2;
    private  Point3D n3;
    private  Point3D n4;
    private Point3D faceNormal;

    public Quad() {
    }

    public void addVertex(Point3D v) {
        if(v1 == null) {
            v1 = v;
        }
        else if(v2 == null) {
            v2 = v;
        }
        else if(v3 == null) {
            v3 = v;
        }
        else if(v4 == null) {
            v4 = v;
        }
    }

    public void addNormal(Point3D n) {
        if(n1 == null) {
            n1 = n;
        }
        else if(n2 == null) {
            n2 = n;
        }
        else if(n3 == null) {
            n3 = n;
        }
        else if(n4 == null) {
            n4 = n;
            calculateFaceNormal();
        }
    }

    private void calculateFaceNormal() {
        double x = n1.x + n2.x + n3.x;
        double y = n1.y + n2.y + n3.y;
        double z = n1.z + n2.z + n3.z;

        faceNormal = new Point3D(x/3.0, y/3.0, z/3.0);
    }

    public Point3D getV1() {
        return v1;
    }

    public Point3D getV2() {
        return v2;
    }

    public Point3D getV3() {
        return v3;
    }

    public Point3D getV4() {
        return v4;
    }

    public Point3D getFaceNormal() {
        calculateFaceNormal();
        return faceNormal;
    }

    public Point3D getN1() {
        return n1;
    }

    public Point3D getN2() {
        return n2;
    }

    public Point3D getN3() {
        return n3;
    }

    public Point3D getMiddlePosition() {
        double x = v1.x + v2.x + v3.x;
        double y = v1.y + v2.y + v3.y;
        double z = v1.z + v2.z + v3.z;

        if(v4 != null) {
            x += v4.x;
            y += v4.y;
            z += v4.z;
            return new Point3D(x/4.0, y/4.0, z/4.0);
        }
        return new Point3D(x/3.0, y/3.0, z/3.0);
    }

}

//glEnable(GL_CONSTANT_ATTENUATION);
//glEnable(GL_LINEAR_ATTENUATION);
//glEnable(GL_QUADRATIC_ATTENUATION);
//glLightf(GL_LIGHT0, GL_CONSTANT_ATTENUATION, 2f);
//glLightf(GL_LIGHT0, GL_LINEAR_ATTENUATION, 1f);
//glLightf(GL_LIGHT0, GL_QUADRATIC_ATTENUATION, .5f);