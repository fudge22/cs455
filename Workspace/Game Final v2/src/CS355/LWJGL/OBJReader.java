package CS355.LWJGL;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Jessica on 1/28/2015.
 */
public class OBJReader {

    BufferedReader reader;
    ArrayList<Quad> quadrilaterals;
    ArrayList<Quad> quadsToCheck;

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
        ArrayList<Point3D> vTextures = new ArrayList<>();
        quadrilaterals = new ArrayList<>();
        quadsToCheck = new ArrayList<>();
        boolean haveTexture = false;

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

            if(line.startsWith("vt")) {
                String[] vLine = line.split(" ");
                Point3D t = new Point3D(Double.parseDouble(vLine[1]), Double.parseDouble(vLine[2]), 0);
                vTextures.add(t);
                haveTexture = true;
            }

            if(line.startsWith("f")) {
                String[] fLine = line.split(" ");
                Quad q = new Quad();
                for(int i = 1; i < fLine.length; i++) {
                    if(haveTexture) {
                        String[] indexes = fLine[i].split("/");
                        int vIndex = Integer.parseInt(indexes[0])-1;
                        int tIndex = Integer.parseInt(indexes[1])-1;
                        int nIndex = Integer.parseInt(indexes[2])-1;
                        q.addVertex(vertices.get(vIndex));
                        q.addNormal(vNormals.get(nIndex));
                        q.addTexture(vTextures.get(tIndex));
                    }
                    else {
                        String[] indexes = fLine[i].split("//");
                        int vIndex = Integer.parseInt(indexes[0]) - 1;
                        int nIndex = Integer.parseInt(indexes[1]) - 1;
                        q.addVertex(vertices.get(vIndex));
                        q.addNormal(vNormals.get(nIndex));
                    }
                }
                quadrilaterals.add(q);
                
                //populate convenience list of quads to check for collisions
                double y1 = q.getV1().y;
                double y2 = q.getV2().y;
                double y3 = q.getV3().y;
                boolean oneIsBelow = false;
                boolean oneIsAbove = false;
                if(y1 < 10 || y2 < 10 || y3 < 10){
                	oneIsBelow = true;
                }
                if(y1 > 10 || y2 > 10 || y3 > 10){
                	oneIsAbove = true;
                }
                if(oneIsBelow && oneIsAbove){
                	quadsToCheck.add(q);
                }
            }
        }
    }

    public ArrayList<Quad> getQuadrilaterals() {
        return quadrilaterals;
    }
    
    public ArrayList<Quad> getQuadsToCheck() {
        return quadsToCheck;
    }
}
