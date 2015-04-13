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
    private Point3D t1;
    private Point3D t2;
    private Point3D t3;

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
        }
    }

    public void addTexture(Point3D t) {
        if(t1 == null) {
            t1 = t;
        }
        else if(t2 == null) {
            t2 = t;
        }
        else if(t3 == null) {
            t3 = t;
        }
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

    public Point3D getN1() {
        return n1;
    }

    public Point3D getN2() {
        return n2;
    }

    public Point3D getN3() {
        return n3;
    }

    public Point3D getT1() {
        return t1;
    }

    public Point3D getT2() {
        return t2;
    }

    public Point3D getT3() {
        return t3;
    }

}