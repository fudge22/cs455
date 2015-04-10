//You might notice a lot of imports here.
//You are probably wondering why I didn't just import org.lwjgl.opengl.GL11.*
//Well, I did it as a hint to you.
//OpenGL has a lot of commands, and it can be kind of intimidating.
//This is a list of all the commands I used when I implemented my project.
//Therefore, if a command appears in this list, you probably need it.
//If it doesn't appear in this list, you probably don't.
//Of course, your milage may vary. Don't feel restricted by this list of imports.
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.BufferUnderflowException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

/**
 *
 * @author Brennan Smith
 */
public class StudentLWJGLController implements CS355LWJGLController {
	
	private Point3D cameraPos = new Point3D(-295,10,-18);
	private float cameraRot = 0;
	private Maze maze = new Maze();
    private Player player1 = new Player(new Color3D(.5, .4,.3), new Point3D(295,0,0));
    private Player player2 = new Player(new Color3D(.7, .4,.3), new Point3D(0,0,-295));
    private Player player3 = new Player(new Color3D(.6, .4,.2), new Point3D(0,0,295));
    private float spellX = (float)cameraPos.x;
    private float spellY = (float)cameraPos.y;
    private float spellZ = (float)cameraPos.z;
    private boolean spellCast = false;

    // Create light components
    float ambientLight[] = { 0.2f, 0.2f, 0.2f, 1.0f };
    float diffuseLight[] = { 0f, 0f, 1f, 1.0f };
    float specularLight[] = { 0.5f, 0.5f, 0.5f, 1.0f };
    OBJReader objReader;

    private static FloatBuffer asFloatBuffer(float[] values) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
        buffer.put(values);
        buffer.flip();
        return buffer;
    }
	@Override
	public void resizeGL()  {

        objReader = new OBJReader(new File("realMaze4.obj"));
        glViewport(0, 0, LWJGLSandbox.DISPLAY_WIDTH, LWJGLSandbox.DISPLAY_HEIGHT);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(45.0f, (float)(LWJGLSandbox.DISPLAY_WIDTH/LWJGLSandbox.DISPLAY_HEIGHT), 1f, 1000f);
        //glPushMatrix();

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        //lighting
        // Somewhere in the initialization part of your program�
        glEnable(GL_DEPTH_TEST);
        glShadeModel(GL_SMOOTH);
        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);
        GL11.glLightModeli(GL11.GL_LIGHT_MODEL_TWO_SIDE, GL11.GL_TRUE);


        GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);//allows you to add lighting to already color polygons

       float position0[] = { 0, 0,0, 1.0f };
       glLight(GL_LIGHT0, GL_POSITION, asFloatBuffer(position0));
       glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, 20);
    }

    @Override
    public void update() 
    {

        if(spellCast)
            spellX+=1;
        else{
            spellX = (float)cameraPos.x;
            spellY = (float)cameraPos.y;
            spellZ = (float)cameraPos.z;
        }
        //use an ik solver to update the angle of each player
        player1.angle = getAngle(player1);
        player2.angle = getAngle(player2);
        player3.angle = getAngle(player3);

        //update the position of the players to move them closer to the camera. (increment by 0.3)


        //player1
        player1.position = getPosition(player1);


        //player2
        player2.position = getPosition(player2);

        //player3
        player3.position = getPosition(player3);
    }

    @Override
    public void updateKeyboard() 
    {
        if(Keyboard.isKeyDown(Keyboard.KEY_Q)) 
        {
            //turn left
            cameraRot = cameraRot - 2;
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_E)) 
        {
            //turn right
            cameraRot = cameraRot + 2;
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) 
        {
        	//move left
            cameraPos = new Point3D(cameraPos.x-Math.cos(Math.toRadians(cameraRot)),cameraPos.y,cameraPos.z- Math.sin(Math.toRadians(cameraRot)));
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) 
        {
        	//move right
            cameraPos = new Point3D(cameraPos.x + Math.cos(Math.toRadians(cameraRot)),cameraPos.y ,cameraPos.z + Math.sin(Math.toRadians(cameraRot)));
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_UP)) 
        {
        	//move forward
            cameraPos = new Point3D(cameraPos.x + Math.sin(Math.toRadians(cameraRot)),cameraPos.y,cameraPos.z - Math.cos(Math.toRadians(cameraRot)));
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) 
        {
        	//move backward
            cameraPos = new Point3D(cameraPos.x - Math.sin(Math.toRadians(cameraRot)),cameraPos.y,cameraPos.z + Math.cos(Math.toRadians(cameraRot)));
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
        {
            spellCast = !spellCast;
        }
    }

    //This method is the one that actually draws to the screen.
    @Override
    public void render() 
    {
    	glLoadIdentity();
        //This clears the screen.
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


        glRotatef(cameraRot, 0, 1, 0);
        //glRotatef((float)90, 1, 0, 0); //used to look down
        glTranslatef(-(float) cameraPos.x, -(float) cameraPos.y, -(float) cameraPos.z);

        //glLight(GL_LIGHT0, GL_AMBIENT, asFloatBuffer(ambientLight));
        //glLight(GL_LIGHT0, GL_DIFFUSE, asFloatBuffer(diffuseLight));
        //glLight(GL_LIGHT0, GL_SPECULAR, asFloatBuffer(specularLight));

        if(spellCast) {
            glEnable(GL_LIGHT1);
            //
             glDisable(GL_LIGHT0);
            glColor3d(0, 0, 1);
            drawSquare(new Point3D(spellX, spellY, spellZ));
        }
        else {
            glEnable(GL_LIGHT0);
            glDisable(GL_LIGHT1);
        }

        float position2[] = { spellX, spellY, spellZ, 1.0f };
        glLight(GL_LIGHT1, GL_AMBIENT, asFloatBuffer(ambientLight));
        glLight(GL_LIGHT1, GL_DIFFUSE, asFloatBuffer(diffuseLight));
        glLight(GL_LIGHT1, GL_POSITION, asFloatBuffer(position2));
        glLight(GL_LIGHT1, GL_SPECULAR, asFloatBuffer(specularLight));
        glLightf(GL_LIGHT1, GL_LINEAR_ATTENUATION, .005f);

        ArrayList<Quad> quads = objReader.getQuadrilaterals();
        for(int i = 0; i < quads.size(); i++) {
            Quad quad = quads.get(i);
            glPolygonMode(GL_FRONT, GL_FILL);
            glBegin(GL_POLYGON);
            if(quad.getN1().y == 1 &&  quad.getV1().y < 0.1)
                glColor3d(.8, .2, .2);
            else
                glColor3d(.2, .8, .2);
            glNormal3d(quad.getN1().x, quad.getN1().y, quad.getN1().z);
            glVertex3d(quad.getV1().x, quad.getV1().y, quad.getV1().z);

            glNormal3d(quad.getN2().x, quad.getN2().y, quad.getN2().z);
            glVertex3d(quad.getV2().x, quad.getV2().y, quad.getV2().z);

            glNormal3d(quad.getN3().x, quad.getN3().y, quad.getN3().z);
            glVertex3d(quad.getV3().x, quad.getV3().y, quad.getV3().z);
            glEnd();
        }

        glPopMatrix();
    }

    public Color3D getColor(Point3D p, Color3D c) {
        //Ca = (0.2,0.2,0.2)-->as opposed to black for dark
        //Cl = (.1,.1,0.8)-->light color
        //Cr = (0.2,0.2,0.2)-->color that's reflected

        Point3D light = new Point3D(0,0,0);
        Point3D lightVector = new Point3D(light.x-p.x, light.y-p.y, light.z-p.z);
        double lightLength = Math.sqrt(Math.pow(lightVector.x, 2)+
                Math.pow(lightVector.y, 2)+
                Math.pow(lightVector.z, 2));
        lightVector = new Point3D(lightVector.x/lightLength, lightVector.y/lightLength, lightVector.z/lightLength);//normalizing
        double dot = (p.x*lightVector.x) +
                (p.y*lightVector.y) +
                (p.z*lightVector.z);
        dot = Math.max(0, dot);
        float r, g, b;
        r = (float)(0.8*dot+0.2);
        g = (float)(0.8*dot+0.2);
        b = (float)(0.8*dot+0.2);
        //if(r > 1) {r = 1;}
        //if(g > 1) {g = 1;}
        //if(b > 1) {b = 1;}
        r *= c.r;
        g *= c.g;
        b *= c.b;
        if(g > .16)
            System.out.println("");
        return new Color3D(r, g, b);
    }

    private void drawSquare(Point3D p) {
        if(p == null)
            return;
        double xPos = p.x + 0.5;
        double yPos = p.y + 0.5;
        double xNeg = p.x - 0.5;
        double yNeg = p.y - 0.5;
        double zPos = p.z + 0.5;
        double zNeg = p.z - 0.5;

        //front/back
        glBegin(GL_POLYGON);
        glVertex3d(xPos, yPos, zNeg);
        glVertex3d(xPos, yNeg, zNeg);
        glVertex3d(xNeg, yNeg, zNeg);
        glVertex3d(xNeg, yPos, zNeg);
        glEnd();
        glBegin(GL_POLYGON);
        glVertex3d(xPos, yPos, zPos);
        glVertex3d(xPos, yNeg, zPos);
        glVertex3d(xNeg, yNeg, zPos);
        glVertex3d(xNeg, yPos, zPos);
        glEnd();
        //side/side
        glBegin(GL_POLYGON);
        glVertex3d(xPos, yPos, zNeg);
        glVertex3d(xPos, yPos, zPos);
        glVertex3d(xPos, yNeg, zPos);
        glVertex3d(xPos, yNeg, zNeg);
        glEnd();
        glBegin(GL_POLYGON);
        glVertex3d(xNeg, yPos, zNeg);
        glVertex3d(xNeg, yPos, zPos);
        glVertex3d(xNeg, yNeg, zPos);
        glVertex3d(xNeg, yNeg, zNeg);
        glEnd();
        //top
        glColor3f(0, 0, 0.5f);
        glBegin(GL_POLYGON);
        glVertex3d(xPos, yPos, zPos);
        glVertex3d(xPos, yPos, zNeg);
        glVertex3d(xNeg, yPos, zNeg);
        glVertex3d(xNeg, yPos, zPos);
        glEnd();
        //bottom
        glBegin(GL_POLYGON);
        glVertex3d(xPos, yNeg, zPos);
        glVertex3d(xPos, yNeg, zNeg);
        glVertex3d(xNeg, yNeg, zNeg);
        glVertex3d(xNeg, yNeg, zPos);
        glEnd();
    }

    private Point3D getPosition(Player player) {
        if(distanceXZ(player.position, cameraPos) >30){
            Point3D endPoint = new Point3D(0,0,0);
            double currentDegrees = player.angle;
            if(currentDegrees < 0){
                currentDegrees = 360+currentDegrees;
            }
            if(currentDegrees > 360){
                currentDegrees = currentDegrees -360;
            }
            if(currentDegrees >= 0 && currentDegrees <= 90){
                endPoint = new Point3D(player.position.x+.3 * Math.cos(Math.toRadians(currentDegrees)),0,player.position.z+.3 * Math.cos(Math.toRadians(90-(currentDegrees))));
            }
            else if(currentDegrees > 90 && currentDegrees <= 180){
                endPoint = new Point3D(player.position.x-.3 * Math.cos(Math.toRadians(180-currentDegrees)),0,player.position.z+.3 * Math.cos(Math.toRadians(90-(180-currentDegrees))));
            }
            else if(currentDegrees > 180 && currentDegrees <= 270){
                endPoint = new Point3D(player.position.x-.3 * Math.cos(Math.toRadians(currentDegrees-180)),0,player.position.z-.3 * Math.cos(Math.toRadians(90-(currentDegrees-180))));
            }
            else if(currentDegrees > 270 && currentDegrees <= 360){
                endPoint = new Point3D(player.position.x+.3 * Math.cos(Math.toRadians(360-currentDegrees)),0,player.position.z-.3 * Math.cos(Math.toRadians(90-(360-currentDegrees))));
            }
            return endPoint;
        }
        else{
            return player.position;
        }
    }

    private double getAngle(Player player) {
        double dot = 0;
        Point3D endPoint = new Point3D(0,0,0);
        double currentDegrees = player.angle;
        if(currentDegrees < 0){
            currentDegrees = 360+currentDegrees;
        }
        if(currentDegrees > 360){
            currentDegrees = currentDegrees -360;
        }
        if(currentDegrees >= 0 && currentDegrees <= 90){
            endPoint = new Point3D(player.position.x+1 * Math.cos(Math.toRadians(currentDegrees)),0,player.position.z+1 * Math.cos(Math.toRadians(currentDegrees)));
        }
        else if(currentDegrees > 90 && currentDegrees <= 180){
            endPoint = new Point3D(player.position.x-1 * Math.cos(Math.toRadians(180-currentDegrees)),0,player.position.z+1 * Math.cos(Math.toRadians(180-currentDegrees)));
        }
        else if(currentDegrees > 180 && currentDegrees <= 270){
            endPoint = new Point3D(player.position.x-1 * Math.cos(Math.toRadians(currentDegrees-180)),0,player.position.z-1 * Math.cos(Math.toRadians(currentDegrees-180)));
        }
        else if(currentDegrees > 270 && currentDegrees <= 360){
            endPoint = new Point3D(player.position.x+1 * Math.cos(Math.toRadians(360-currentDegrees)),0,player.position.z-1 * Math.cos(Math.toRadians(360-currentDegrees)));
        }
        double dx = endPoint.x - player.position.x;
        double dz = endPoint.z - player.position.z;
        Point3D n1 = new Point3D(-dz,0,dx);
        Point3D n2 = new Point3D(dz,0,-dx);
        double d1 = distance(cameraPos,n1);
        double d2 = distance(cameraPos,n2);
        if(d1< d2){
            n1 = normalize(new Point3D(n1.x - endPoint.x,0,n1.z - endPoint.z));
        }
        else{
            n1 = normalize(new Point3D(n2.x - endPoint.x, 0, n2.z - endPoint.z));
        }
        dot = dot(n1,normalize(new Point3D(cameraPos.x - endPoint.x, 0, cameraPos.z - endPoint.z)));
        double tempdegrees = currentDegrees + dot * distance(endPoint,cameraPos) *.08;
        if(tempdegrees < 0){
            tempdegrees = 360+tempdegrees;
        }
        if(tempdegrees > 360){
            tempdegrees = tempdegrees -360;
        }
        if(tempdegrees >= 0 && tempdegrees <= 90){
            endPoint = new Point3D(player.position.x+1 * Math.cos(Math.toRadians(tempdegrees)),0,player.position.z+1 * Math.sin(Math.toRadians(tempdegrees)));
        }
        else if(tempdegrees > 90 && tempdegrees <= 180){
            endPoint = new Point3D(player.position.x-1 * Math.cos(Math.toRadians(180-tempdegrees)),0,player.position.z+1 * Math.sin(Math.toRadians(180-tempdegrees)));
        }
        else if(tempdegrees > 180 && tempdegrees <= 270){
            endPoint = new Point3D(player.position.x-1 * Math.cos(Math.toRadians(tempdegrees-180)),0,player.position.z-1 * Math.sin(Math.toRadians(tempdegrees-180)));
        }
        else if(tempdegrees > 270 && tempdegrees <= 360){
            endPoint = new Point3D(player.position.x+1 * Math.cos(Math.toRadians(360-tempdegrees)),0,player.position.z-1 * Math.sin(Math.toRadians(360-tempdegrees)));
        }
        double distanceAdd = distanceXZ(endPoint,cameraPos);
        tempdegrees = currentDegrees - dot * distance(endPoint,cameraPos) *.08;
        if(tempdegrees < 0){
            tempdegrees = 360+tempdegrees;
        }
        if(tempdegrees > 360){
            tempdegrees = tempdegrees -360;
        }
        if(tempdegrees >= 0 && tempdegrees <= 90){
            endPoint = new Point3D(player.position.x+1 * Math.cos(Math.toRadians(tempdegrees)),0,player.position.z+1 * Math.sin(Math.toRadians(tempdegrees)));
        }
        else if(tempdegrees > 90 && tempdegrees <= 180){
            endPoint = new Point3D(player.position.x-1 * Math.cos(Math.toRadians(180-tempdegrees)),0,player.position.z+1 * Math.sin(Math.toRadians(180-tempdegrees)));
        }
        else if(tempdegrees > 180 && tempdegrees <= 270){
            endPoint = new Point3D(player.position.x-1 * Math.cos(Math.toRadians(tempdegrees-180)),0,player.position.z-1 * Math.sin(Math.toRadians(tempdegrees-180)));
        }
        else if(tempdegrees > 270 && tempdegrees <= 360){
            endPoint = new Point3D(player.position.x+1 * Math.cos(Math.toRadians(360-tempdegrees)),0,player.position.z-1 * Math.sin(Math.toRadians(360-tempdegrees)));
        }
        double distanceSubtract = distanceXZ(endPoint,cameraPos);
        if(distanceAdd < distanceSubtract){
            double newangle = player.angle + dot * distance(endPoint,cameraPos) *.08;
            if(newangle > 360){
                newangle = newangle - 360;
            }
            if(newangle < -360){
                newangle = newangle + 360;
            }
            return newangle;
        }
        else{
            double newangle = player.angle - dot * distance(endPoint,cameraPos) *.08;
            if(newangle > 360){
                newangle = newangle - 360;
            }
            if(newangle < -360){
                newangle = newangle + 360;
            }
            return newangle;
        }
    }
    
    public double dot(Point3D point1, Point3D point2){
    	double returnval = 0;
    	returnval = point1.x * point2.x + point1.y * point2.y + point1.z * point2.z;
    	return returnval;
    }
    
    public Point3D cross(Point3D point1, Point3D point2){
    	return new Point3D((float)(point1.y * point2.z - point1.z * point2.y), (float)(point1.y * point2.x - point1.x * point2.y),(float)(point1.x * point2.y - point1.y * point2.x));
    }
    
    public double cross2D(Point3D point1, Point3D point2){
    	return point1.x * point2.y - point2.x * point1.y;
    }
    
    public Point3D normalize(Point3D point) 
    {
        double denominator = Math.sqrt(point.x*point.x+point.y*point.y+point.z*point.z);
        if(denominator == 0){
        	return new Point3D(0,0,0);
        }
        double px = point.x/denominator;
        double py = point.y/denominator;
        double pz = point.z/denominator;
        return new Point3D(px,py,pz);
    }
    
    public double distance(Point3D p1, Point3D p2){
    	return Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y) + (p2.z - p1.z) * (p2.z - p1.z));
    }
    
    public double distanceXY(Point3D p1, Point3D p2){
    	return Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y));
    }
    
    public double distanceXZ(Point3D p1, Point3D p2){
    	return Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.z - p1.z) * (p2.z - p1.z));
    }
    
    public double distanceYZ(Point3D p1, Point3D p2){
    	return Math.sqrt((p2.y - p1.y) * (p2.y - p1.y) + (p2.z - p1.z) * (p2.z - p1.z));
    }
    
    public double distanceX(Point3D p1, Point3D p2){
    	return Math.sqrt((p2.x - p1.x) * (p2.x - p1.x));
    }
    
    public double distanceY(Point3D p1, Point3D p2){
    	return Math.sqrt((p2.y - p1.y) * (p2.y - p1.y));
    }
    
    public double distanceZ(Point3D p1, Point3D p2){
    	return Math.sqrt((p2.z - p1.z) * (p2.z - p1.z));
    }
}
