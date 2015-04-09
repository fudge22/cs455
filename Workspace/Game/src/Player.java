import java.util.ArrayList;

/**
 * Created by Jessica on 4/6/2015.
 */
public class Player {
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();
    public Color3D color;
    public Point3D position;
    public double angle;

    public Player(Color3D _color, Point3D start_position){
        //front
        polygons.add(new Polygon(new Point3D(-1,0,0),new Point3D(0,0,0),new Point3D(0,4,0),new Point3D(-1,4,0)));
        polygons.add(new Polygon(new Point3D(1,0,0),new Point3D(2,0,0),new Point3D(2,4,0),new Point3D(1,4,0)));
        polygons.add(new Polygon(new Point3D(-1,4,0),new Point3D(2,4,0),new Point3D(2,10,0),new Point3D(-1,10,0)));
        polygons.add(new Polygon(new Point3D(-1,11,0),new Point3D(2,11,0),new Point3D(2,14,0),new Point3D(-1,14,0)));
        polygons.add(new Polygon(new Point3D(-3,8,0),new Point3D(-2,7,0),new Point3D(-1,9,0),new Point3D(-1,10,0)));
        polygons.add(new Polygon(new Point3D(2,9,0),new Point3D(4,7,0),new Point3D(5,8,0),new Point3D(2,10,0)));
        //back
        polygons.add(new Polygon(new Point3D(-1,0,-1),new Point3D(0,0,-1),new Point3D(0,4,-1),new Point3D(-1,4,-1)));
        polygons.add(new Polygon(new Point3D(1,0,-1),new Point3D(2,0,-1),new Point3D(2,4,-1),new Point3D(1,4,-1)));
        polygons.add(new Polygon(new Point3D(-1,4,-1),new Point3D(2,4,-1),new Point3D(2,10,-1),new Point3D(-1,10,-1)));
        polygons.add(new Polygon(new Point3D(-1,11,-1),new Point3D(2,11,-1),new Point3D(2,14,-1),new Point3D(-1,14,-1)));
        polygons.add(new Polygon(new Point3D(-3,8,-1),new Point3D(-2,7,-1),new Point3D(-1,9,-1),new Point3D(-1,10,-1)));
        polygons.add(new Polygon(new Point3D(2,9,-1),new Point3D(4,7,-1),new Point3D(5,8,-1),new Point3D(2,10,-1)));
        //sides
        polygons.add(new Polygon(new Point3D(-1,0,0),new Point3D(0,0,0),new Point3D(0,0,-1),new Point3D(-1,0,-1)));
        polygons.add(new Polygon(new Point3D(1,0,0),new Point3D(2,0,0),new Point3D(2,0,-1),new Point3D(1,0,-1)));
        polygons.add(new Polygon(new Point3D(0,4,0),new Point3D(1,4,0),new Point3D(1,4,-1),new Point3D(0,4,-1)));
        polygons.add(new Polygon(new Point3D(-1,14,0),new Point3D(2,14,0),new Point3D(2,14,-1),new Point3D(-1,14,-1)));
        polygons.add(new Polygon(new Point3D(-1,11,0),new Point3D(2,11,0),new Point3D(2,11,-1),new Point3D(-1,11,-1)));
        polygons.add(new Polygon(new Point3D(-1,10,0),new Point3D(2,10,0),new Point3D(2,10,-1),new Point3D(-1,10,-1)));
        polygons.add(new Polygon(new Point3D(2,10,0),new Point3D(2,10,-1),new Point3D(2,0,-1),new Point3D(2,0,0)));
        polygons.add(new Polygon(new Point3D(-1,10,0),new Point3D(-1,10,-1),new Point3D(-1,0,-1),new Point3D(-1,0,0)));
        polygons.add(new Polygon(new Point3D(0,4,0),new Point3D(0,4,-1),new Point3D(0,0,-1),new Point3D(0,0,0)));
        polygons.add(new Polygon(new Point3D(1,4,0),new Point3D(1,4,-1),new Point3D(1,0,-1),new Point3D(1,0,0)));
        polygons.add(new Polygon(new Point3D(2,14,0),new Point3D(2,14,-1),new Point3D(2,11,-1),new Point3D(2,11,0)));
        polygons.add(new Polygon(new Point3D(-1,14,0),new Point3D(-1,14,-1),new Point3D(-1,11,-1),new Point3D(-1,11,0)));
        //arms (sides)
        polygons.add(new Polygon(new Point3D(-1,10,0),new Point3D(-1,10,-1),new Point3D(-3,8,-1),new Point3D(-3,8,0)));
        polygons.add(new Polygon(new Point3D(-3,8,0),new Point3D(-3,8,-1),new Point3D(-2,7,-1),new Point3D(-2,7,0)));
        polygons.add(new Polygon(new Point3D(-2,7,0),new Point3D(-2,7,-1),new Point3D(-1,9,-1),new Point3D(-1,9,0)));
        polygons.add(new Polygon(new Point3D(2,10,0),new Point3D(2,10,-1),new Point3D(5,8,-1),new Point3D(5,8,0)));
        polygons.add(new Polygon(new Point3D(5,8,0),new Point3D(5,8,-1),new Point3D(4,7,-1),new Point3D(4,7,0)));
        polygons.add(new Polygon(new Point3D(4,7,0),new Point3D(4,7,-1),new Point3D(2,9,-1),new Point3D(2,9,0)));
        //neck
        polygons.add(new Polygon(new Point3D(0,10,-.33),new Point3D(0,10,-.66),new Point3D(0,11,-.66),new Point3D(0,11,-.33)));
        polygons.add(new Polygon(new Point3D(1,10,-.33),new Point3D(1,10,-.66),new Point3D(1,11,-.66),new Point3D(1,11,-.33)));
        polygons.add(new Polygon(new Point3D(0,10,-.33),new Point3D(1,10,-.33),new Point3D(1,11,-.33),new Point3D(1,11,-.33)));
        polygons.add(new Polygon(new Point3D(0,10,-.66),new Point3D(1,10,-.66),new Point3D(1,11,-.66),new Point3D(0,11,-.66)));
        color = _color;
        position = start_position;
        angle = 0;
    }
}
