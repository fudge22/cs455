package CS355.LWJGL;

import java.io.File;
import java.util.ArrayList;

public class Player {
	public ArrayList<Quad> polygons = new ArrayList<>();
	public Color3D color;
	public Point3D position;
	public double angle;

	public boolean isHit;
	
	public Player(Color3D _color, Point3D start_position){
		OBJReader objReader = new OBJReader(new File("wizard.obj"));
		polygons = objReader.getQuadrilaterals();
		color = _color;
		position = start_position;
		isHit = false;
		angle = 0;
	}
}
