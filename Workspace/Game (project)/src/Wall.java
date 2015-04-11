import java.util.ArrayList;

public class Wall {
	public ArrayList<Point3D> corners = new ArrayList<Point3D>();
	public Color3D color;
	
	public Wall(Point3D corner1, Point3D corner2, Point3D corner3,
			Point3D corner4, Color3D _color) {
		
		corners.add(corner1);
		corners.add(corner2);
		corners.add(corner3);
		corners.add(corner4);
		
		color = _color;
	}
}
