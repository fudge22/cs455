package CS355.LWJGL;

import java.util.ArrayList;

public class Wall {
	public ArrayList<Point3D> corners = new ArrayList<Point3D>();
	public Color3D color;
	public Point3D faceNormal;
	public Wall(Point3D corner1, Point3D corner2, Point3D corner3,
			Point3D corner4, Color3D _color) {

		corners.add(corner1);
		corners.add(corner2);
		corners.add(corner3);
		corners.add(corner4);
		if(corner1.y == corner2.y && corner1.y == corner3.y && corner1.y == corner4.y)
			faceNormal = new Point3D(0, 1, 0);
		if(corner1.x == corner2.x && corner1.x == corner3.x && corner1.x == corner4.x)
			faceNormal = new Point3D(1, 0, 0);
		if(corner1.z == corner2.z && corner1.z == corner3.z && corner1.z == corner4.z)
			faceNormal = new Point3D(0, 0, 1);
		color = _color;
	}
}
