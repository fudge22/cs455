package CS355.LWJGL;


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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.lwjgl.input.Keyboard;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPushMatrix;/*might not use*/
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3d;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.util.glu.GLU.gluPerspective;

/**
 *
 * @author Brennan Smith
 */
public class StudentLWJGLController implements CS355LWJGLController {
	
	private Point3D cameraPos = new Point3D(-295,10,50);
	private float cameraRot = 90;
	private Maze maze = new Maze();

	@Override
	public void resizeGL()  {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(60, 500/500, 1, 1000);
		glMatrixMode(GL_MODELVIEW);
		
	}

    @Override
    public void update() 
    {
    	
    }

    @Override
    public void updateKeyboard() 
    {
        if(Keyboard.isKeyDown(Keyboard.KEY_Q)) 
        {
            //turn left
            cameraRot = cameraRot - 1;
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_E)) 
        {
            //turn right
            cameraRot = cameraRot + 1;
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
    }

    //This method is the one that actually draws to the screen.
    @Override
    public void render() 
    {
    	glLoadIdentity();
        //This clears the screen.
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glEnable(GL_DEPTH_TEST);
        glRotatef(cameraRot, 0, 1, 0);
        //glRotatef((float)90, 1, 0, 0); used to look down
        glTranslatef(-(float)cameraPos.x, -(float) cameraPos.y, -(float) cameraPos.z);
    	for(int a = 0; a < maze.Walls.size();a++){
	        glBegin(GL_POLYGON);
	        glColor3d(maze.Walls.get(a).color.r, maze.Walls.get(a).color.g, maze.Walls.get(a).color.b);
	        for(int b = 0; b < maze.Walls.get(a).corners.size();b++){
	        	glVertex3d(maze.Walls.get(a).corners.get(b).x, maze.Walls.get(a).corners.get(b).y, maze.Walls.get(a).corners.get(b).z);
	        }
	        glEnd();
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
