import java.io.*;
import java.util.ArrayList;

/**
 * Created by Jessica on 1/28/2015.
 */
public class OBJReader {

    BufferedReader reader;
    ArrayList<Quad> quadrilaterals;

    public OBJReader(File file) {
        try {
            reader = new BufferedReader(new FileReader(file));
            parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parse() throws IOException {
        ArrayList<Point3D> vertices = new ArrayList<>();
        ArrayList<Point3D> vNormals = new ArrayList<>();
        quadrilaterals = new ArrayList<>();

        String line = "";
        while ((line = reader.readLine()) != null) {

            if(line.startsWith("v ")) {
                String[] vLine = line.split(" ");
                Point3D v = new Point3D(Double.parseDouble(vLine[1]), Double.parseDouble(vLine[2]), Double.parseDouble(vLine[3]));
                vertices.add(v);
            }

            if(line.startsWith("vn")) {
                String[] vnLine = line.split(" ");
                Point3D n = new Point3D(Double.parseDouble(vnLine[1]), Double.parseDouble(vnLine[2]), Double.parseDouble(vnLine[3]));
                vNormals.add(n);
            }

            if(line.startsWith("f")) {
                String[] fLine = line.split(" ");
                Quad q = new Quad();
                for(int i = 1; i < fLine.length; i++) {
                    String[] indexes = fLine[i].split("//");
                    int vIndex = Integer.parseInt(indexes[0])-1;
                    int nIndex = Integer.parseInt(indexes[1])-1;
                    q.addVertex(vertices.get(vIndex));
                    q.addNormal(vNormals.get(nIndex));
                }
                quadrilaterals.add(q);
                //check intersects
            }
        }
    }

    public ArrayList<Quad> getQuadrilaterals() {
        return quadrilaterals;
    }
}
