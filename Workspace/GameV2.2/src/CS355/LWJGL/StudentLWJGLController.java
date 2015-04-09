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
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.util.glu.GLU.gluPerspective;

/**
 *
 * @author Brennan Smith
 */
public class StudentLWJGLController implements CS355LWJGLController {
	
	private Point3D cameraPos = new Point3D(-295,10,20);
	private float cameraRot = 90;
	private Maze maze = new Maze();
	private Player player1 = new Player(new Color3D(.5, .4,.3), new Point3D(295,0,0));
	private Player player2 = new Player(new Color3D(.7, .4,.3), new Point3D(0,0,-295));
	private Player player3 = new Player(new Color3D(.6, .4,.2), new Point3D(0,0,295));

	// Create light components
	float ambientLight[] = { 0.2f, 0.2f, 0.2f, 1.0f };
	float diffuseLight[] = { 0.1f, 0.8f, 0.1f, 1.0f };
	float specularLight[] = { 0.5f, 0.5f, 0.5f, 1.0f };

	private static FloatBuffer asFloatBuffer(float[] values) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}

	@Override
	public void resizeGL()  {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(60, 500/500, 1, 1000);
		glMatrixMode(GL_MODELVIEW);

		//lighting
		// Somewhere in the initialization part of your program…
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		GL11.glLightModeli(GL11.GL_LIGHT_MODEL_TWO_SIDE, GL11.GL_TRUE);


		GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);//allows you to add lighting to already color polygons

	}

    @Override
    public void update() 
    {
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
            cameraRot = cameraRot - 1;
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_E)) 
        {
            //turn right
            cameraRot = cameraRot + 1;
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) 
        {
        	//do I hit a wall?
        	
        	//if I hit a wall, I can't move any further left. 
        	
        	//If I am at an angle, check for a collision if only moves in the up/down direction. 
        	
        	//If no collision, update the up/down direction.
        	
        	
        	//move left
            cameraPos = new Point3D(cameraPos.x-Math.cos(Math.toRadians(cameraRot)),cameraPos.y,cameraPos.z- Math.sin(Math.toRadians(cameraRot)));
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) 
        {
        	//do I hit a wall?
        	
        	//if I hit a wall, I can't move any further right. 
        	
        	//If I am at an angle, check for a collision if only moves in the up/down direction. 
        	
        	//If no collision, update the up/down direction.
        	
        	
        	//move right
            cameraPos = new Point3D(cameraPos.x + Math.cos(Math.toRadians(cameraRot)),cameraPos.y ,cameraPos.z + Math.sin(Math.toRadians(cameraRot)));
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_UP)) 
        {
        	//do I hit a wall?
        	
        	//if I hit a wall, I can't move any further up. 
        	
        	//If I am at an angle, check for a collision if only moves in the left/right direction. 
        	
        	//If no collision, update the left/right direction.
        	
        	
        	//move forward
            cameraPos = new Point3D(cameraPos.x + Math.sin(Math.toRadians(cameraRot)),cameraPos.y,cameraPos.z - Math.cos(Math.toRadians(cameraRot)));
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) 
        {
        	//do I hit a wall?
        	
        	//if I hit a wall, I can't move any further down. 
        	
        	//If I am at an angle, check for a collision if only moves in the left/right direction. 
        	
        	//If no collision, update the left/right direction.
        	
        	
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
		glShadeModel(GL_SMOOTH);
		glRotatef(cameraRot, 0, 1, 0);
        //glRotatef((float)90, 1, 0, 0); //used to look down
		glTranslatef(-(float) cameraPos.x, -(float) cameraPos.y, -(float) cameraPos.z);

		//*************************lighting stuff****************************/
		//glLight(GL_LIGHT0, GL_AMBIENT, asFloatBuffer(ambientLight));
		//glLight(GL_LIGHT0, GL_DIFFUSE, asFloatBuffer(diffuseLight));
		//glLight(GL_LIGHT0, GL_SPECULAR, asFloatBuffer(specularLight));
		float position[] = { (float)cameraPos.x,(float)cameraPos.y,(float)cameraPos.z, 1.0f };

		glLight(GL_LIGHT0, GL_POSITION, asFloatBuffer(position));
		glColor3d(1, 1, 1);
		drawSquare(new Point3D(-295, 10, 50));

		for(int a = 0; a < maze.Walls.size();a++){
	        glBegin(GL_POLYGON);
			//glNormal3d(maze.Walls.get(a).faceNormal.x, maze.Walls.get(a).faceNormal.y, maze.Walls.get(a).faceNormal.z);
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
