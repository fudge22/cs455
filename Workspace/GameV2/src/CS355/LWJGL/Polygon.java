package CS355.LWJGL;

import java.util.ArrayList;

public class Polygon {
	public ArrayList<Point3D> points = new ArrayList<Point3D>();

	public Polygon(Point3D p1, Point3D p2, Point3D p3, Point3D p4) {
		points.add(p1);
		points.add(p2);
		points.add(p3);
		points.add(p4);
	}
}
