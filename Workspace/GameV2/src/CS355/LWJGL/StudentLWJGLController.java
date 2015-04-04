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
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex3d;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.util.glu.GLU.gluPerspective;

/**
 *
 * @author Brennan Smith
 */
public class StudentLWJGLController implements CS355LWJGLController {
	
	private Point3D cameraPos = new Point3D(-295,650,20);
	private float cameraRot = 0;
	private Maze maze = new Maze();
	private Player player1 = new Player(new Color3D(.5, .4,.3), new Point3D(295,0,0));
	private Player player2 = new Player(new Color3D(.7, .4,.3), new Point3D(0,0,-295));
	private Player player3 = new Player(new Color3D(.6, .4,.2), new Point3D(0,0,295));

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
    	//use an ik solver to update the angle of each player, then move them towards the camera
    	player1.angle = getAngle(player1);
    	if(player1.angle > 360){
    		player1.angle = player1.angle - 360;
   		}
		if(player1.angle < -360){
			player1.angle = player1.angle + 360;
   		}
		
    	player2.angle = getAngle(player2);
    	if(player2.angle > 360){
    		player2.angle = player2.angle - 360;
   		}
		if(player2.angle < -360){
			player2.angle = player2.angle + 360;
   		}
		
    	player3.angle = getAngle(player3);
    	if(player3.angle > 360){
    		player3.angle = player3.angle - 360;
   		}
		if(player3.angle < -360){
			player3.angle = player3.angle + 360;
   		}
    	
    	Point3D endPoint = new Point3D(0,0,0);
		double currentDegrees = player1.angle;
		if(currentDegrees < 0){
   			currentDegrees = 360+currentDegrees;
   		}
   		if(currentDegrees > 360){
   			currentDegrees = currentDegrees -360;
   		}
   		if(currentDegrees >= 0 && currentDegrees <= 90){
   			endPoint = new Point3D(player1.position.x+.3 * Math.cos(Math.toRadians(currentDegrees)),0,player1.position.z+.3 * Math.cos(Math.toRadians(90-(currentDegrees))));
   		}
   		else if(currentDegrees > 90 && currentDegrees <= 180){
   			endPoint = new Point3D(player1.position.x-.3 * Math.cos(Math.toRadians(180-currentDegrees)),0,player1.position.z+.3 * Math.cos(Math.toRadians(90-(180-currentDegrees))));
   		}
   		else if(currentDegrees > 180 && currentDegrees <= 270){
   			endPoint = new Point3D(player1.position.x-.3 * Math.cos(Math.toRadians(currentDegrees-180)),0,player1.position.z-.3 * Math.cos(Math.toRadians(90-(currentDegrees-180))));
   		}
   		else if(currentDegrees > 270 && currentDegrees <= 360){
   			endPoint = new Point3D(player1.position.x+.3 * Math.cos(Math.toRadians(360-currentDegrees)),0,player1.position.z-.3 * Math.cos(Math.toRadians(90-(360-currentDegrees))));
   		}
   		player1.position = endPoint;
    	
   		endPoint = new Point3D(0,0,0);
		currentDegrees = player2.angle;
		if(currentDegrees < 0){
   			currentDegrees = 360+currentDegrees;
   		}
   		if(currentDegrees > 360){
   			currentDegrees = currentDegrees -360;
   		}
   		if(currentDegrees >= 0 && currentDegrees <= 90){
   			endPoint = new Point3D(player2.position.x+.3 * Math.cos(Math.toRadians(currentDegrees)),0,player2.position.z+.3 * Math.cos(Math.toRadians(90-(currentDegrees))));
   		}
   		else if(currentDegrees > 90 && currentDegrees <= 180){
   			endPoint = new Point3D(player2.position.x-.3 * Math.cos(Math.toRadians(180-currentDegrees)),0,player2.position.z+.3 * Math.cos(Math.toRadians(90-(180-currentDegrees))));
   		}
   		else if(currentDegrees > 180 && currentDegrees <= 270){
   			endPoint = new Point3D(player2.position.x-.3 * Math.cos(Math.toRadians(currentDegrees-180)),0,player2.position.z-.3 * Math.cos(Math.toRadians(90-(currentDegrees-180))));
   		}
   		else if(currentDegrees > 270 && currentDegrees <= 360){
   			endPoint = new Point3D(player2.position.x+.3 * Math.cos(Math.toRadians(360-currentDegrees)),0,player2.position.z-.3 * Math.cos(Math.toRadians(90-(360-currentDegrees))));
   		}
   		player2.position = endPoint;
   		
   		endPoint = new Point3D(0,0,0);
		currentDegrees = player3.angle;
		if(currentDegrees < 0){
   			currentDegrees = 360+currentDegrees;
   		}
   		if(currentDegrees > 360){
   			currentDegrees = currentDegrees -360;
   		}
   		if(currentDegrees >= 0 && currentDegrees <= 90){
   			endPoint = new Point3D(player3.position.x+.3 * Math.cos(Math.toRadians(currentDegrees)),0,player3.position.z+.3 * Math.cos(Math.toRadians(90-currentDegrees)));
   		}
   		else if(currentDegrees > 90 && currentDegrees <= 180){
   			endPoint = new Point3D(player3.position.x-.3 * Math.cos(Math.toRadians(180-currentDegrees)),0,player3.position.z+.3 * Math.cos(Math.toRadians(90-(180-currentDegrees))));
   		}
   		else if(currentDegrees > 180 && currentDegrees <= 270){
   			endPoint = new Point3D(player3.position.x-.3 * Math.cos(Math.toRadians(currentDegrees-180)),0,player3.position.z-.3 * Math.cos(Math.toRadians(90-(currentDegrees-180))));
   		}
   		else if(currentDegrees > 270 && currentDegrees <= 360){
   			endPoint = new Point3D(player3.position.x+.3 * Math.cos(Math.toRadians(360-currentDegrees)),0,player3.position.z-.3 * Math.cos(Math.toRadians(90-(360-currentDegrees))));
   		}
   		player3.position = endPoint;
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
        glRotatef((float)90, 1, 0, 0); //used to look down
        glTranslatef(-(float)cameraPos.x, -(float) cameraPos.y, -(float) cameraPos.z);
    	for(int a = 0; a < maze.Walls.size();a++){
	        glBegin(GL_POLYGON);
	        glColor3d(maze.Walls.get(a).color.r, maze.Walls.get(a).color.g, maze.Walls.get(a).color.b);
	        for(int b = 0; b < maze.Walls.get(a).corners.size();b++){
	        	glVertex3d(maze.Walls.get(a).corners.get(b).x, maze.Walls.get(a).corners.get(b).y, maze.Walls.get(a).corners.get(b).z);
	        }
	        glEnd();
    	}
    	glPushMatrix();
    	glTranslated(player1.position.x, player1.position.y, player1.position.z);
    	glRotated(90-player1.angle, 0, 1, 0);
    	for(int b = 0; b < player1.polygons.size();b++){
    		glBegin(GL_POLYGON);
	        glColor3d(player1.color.r, player1.color.g, player1.color.b);
	        for(int c = 0; c <player1.polygons.get(b).points.size();c++){
	        	glVertex3d(player1.polygons.get(b).points.get(c).x, player1.polygons.get(b).points.get(c).y, player1.polygons.get(b).points.get(c).z);
	        }
	        glEnd();
    	}
    	glPopMatrix();
    	glPushMatrix();
    	glTranslated(player2.position.x, player2.position.y, player2.position.z);
    	glRotated(90-player2.angle, 0, 1, 0);
    	for(int b = 0; b < player2.polygons.size();b++){
    		glBegin(GL_POLYGON);
	        glColor3d(player2.color.r, player2.color.g, player2.color.b);
	        for(int c = 0; c <player2.polygons.get(b).points.size();c++){
	        	glVertex3d(player2.polygons.get(b).points.get(c).x, player2.polygons.get(b).points.get(c).y, player2.polygons.get(b).points.get(c).z);
	        }
	        glEnd();
    	}
    	glPopMatrix();
    	glPushMatrix();
    	glTranslated(player3.position.x, player3.position.y, player3.position.z);
    	glRotated(90-player3.angle, 0, 1, 0);
    	for(int b = 0; b < player3.polygons.size();b++){
    		glBegin(GL_POLYGON);
	        glColor3d(player3.color.r, player3.color.g, player3.color.b);
	        for(int c = 0; c <player3.polygons.get(b).points.size();c++){
	        	glVertex3d(player3.polygons.get(b).points.get(c).x, player3.polygons.get(b).points.get(c).y, player3.polygons.get(b).points.get(c).z);
	        }
	        glEnd();
    	}
    	glPopMatrix();
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
   		double dy = endPoint.y - player.position.y;
		double dz = endPoint.z - player.position.z;
//		Point3D n1 = new Point3D(-dz,0,dx);
//		Point3D n2 = new Point3D(dz,0,-dx);
		Point3D n1 = new Point3D(-dy,dx,0);
		Point3D n2 = new Point3D(dy,-dx,0);
		double d1 = distance(cameraPos,n1);
		double d2 = distance(cameraPos,n2);
		if(d1< d2){
			n1 = normalize(new Point3D(n1.x - endPoint.x, n1.y - endPoint.y,0));
		}
		else{
			n1 = normalize(new Point3D(n2.x - endPoint.x, n2.y - endPoint.y,0));
		}
		dot = dot(n1,normalize(new Point3D(cameraPos.x - endPoint.x, cameraPos.y - endPoint.y,0)));
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
			return player.angle + dot * distance(endPoint,cameraPos) *.08;
		}
		else{
			return player.angle - dot * distance(endPoint,cameraPos) *.08;
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
