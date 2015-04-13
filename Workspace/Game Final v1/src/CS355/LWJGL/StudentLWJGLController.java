package CS355.LWJGL;


import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;


import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Brennan Smith
 */
public class StudentLWJGLController implements CS355LWJGLController {
	
	private Point3D cameraPos = new Point3D(-276,10,20);
	private float cameraRot = 0;
	private Player player1 = new Player(new Color3D(.5, .4,.3), new Point3D(295,3,0));
	private Player player2 = new Player(new Color3D(.7, .4,.3), new Point3D(0,3,-295));
	private Player player3 = new Player(new Color3D(.6, .4,.2), new Point3D(0,3,295));
	private Player user = new Player(new Color3D(.6, .4,.2), new Point3D(0,0,295));

	private float spellX = (float)cameraPos.x;
    private float spellY = (float)cameraPos.y;
    private float spellZ = (float)cameraPos.z;
    private boolean spellCast = false;
    private boolean lumos = false;
	
	private boolean paused = false;
	private float pausedCameraRot = 0;
	private boolean pChecker = false;
	private boolean sChecker = false;
	private boolean lChecker = false;
	private Point3D pausedCameraPos = new Point3D(0,0,0);
	
	// Create light components
    float ambientLight[] = { 0.2f, 0.2f, 0.2f, 1.0f };
    float diffuseLight[] = { 0f, 0f, 1f, 1.0f };
    float specularLight[] = { 0.5f, 0.5f, 0.5f, 1.0f };
    OBJReader objReader;
	Texture textureMaze;
	Texture textureP1;
	Texture textureP2;
	Texture textureP3;
	Texture textureUser;
	Texture textureSpell;

    private static FloatBuffer asFloatBuffer(float[] values) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
        buffer.put(values);
        buffer.flip();
        return buffer;
    }
    
	@Override
	public void resizeGL()  {
		//glViewport(0, 0, LWJGLSandbox.DISPLAY_WIDTH, LWJGLSandbox.DISPLAY_HEIGHT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(60, 500/500, 1, 1000);
		glMatrixMode(GL_MODELVIEW);
		
		objReader = new OBJReader(new File("textureMaze2.obj"));
		
        //lighting
        glEnable(GL_DEPTH_TEST);
        glShadeModel(GL_SMOOTH);
        glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
        glLightModeli(GL_LIGHT_MODEL_TWO_SIDE, GL_TRUE);

        glColorMaterial(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE);
        glEnable(GL_COLOR_MATERIAL);//allows you to add lighting to already color polygons

       float position0[] = { 0, 0,-2.3f, 1.0f };
       glLight(GL_LIGHT0, GL_POSITION, asFloatBuffer(position0));
       glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, 20);

		//texturing
		glEnable(GL_TEXTURE_2D);

		try {
			textureMaze = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("maze.png"));
			textureP1 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("wizardColor.png"));
			textureP2 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("wizardColor2.png"));
			textureP3 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("wizardColor3.png"));
			textureUser = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("userColor.png"));
			textureSpell = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("spell.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Before the scene is rendered, update the scene to reflect any changes from the previous scene.
	 */
    @Override
    public void update() 
    {
    	
    	if(!paused){
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
    }

    /**
     * When a key is pressed, update accordingly
     */
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
        else if(Keyboard.isKeyDown(Keyboard.KEY_L)) 
        {
            if(!lChecker){
            	lumos = !lumos;
        	}
        	lChecker = true;
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_P)) 
        {
        	if(!pChecker){
	            if(!paused){
	            	paused = true;
	            	pausedCameraRot = cameraRot;
	            	pausedCameraPos = cameraPos;
	            	cameraRot = 0;
	            	cameraPos = new Point3D(0,500,0);
	            }
	            else{
	            	paused = false;
	            	cameraRot = pausedCameraRot;
	            	cameraPos = pausedCameraPos;
	            }
        	}
        	pChecker = true;
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) 
        {
        	//do I hit a wall?
        	if(detectCollision(cameraPos, new Point3D(cameraPos.x + Math.cos(Math.toRadians(cameraRot)),cameraPos.y,cameraPos.z + Math.sin(Math.toRadians(cameraRot))))){
        		if(!detectCollision(cameraPos, new Point3D(cameraPos.x,cameraPos.y,cameraPos.z + Math.sin(Math.toRadians(cameraRot))))){
        			cameraPos = new Point3D(cameraPos.x,cameraPos.y,cameraPos.z + Math.sin(Math.toRadians(cameraRot)));
            	}
        		else if(!detectCollision(cameraPos, new Point3D(cameraPos.x + Math.cos(Math.toRadians(cameraRot)),cameraPos.y,cameraPos.z))){
        			cameraPos = new Point3D(cameraPos.x + Math.cos(Math.toRadians(cameraRot)),cameraPos.y,cameraPos.z);
            	}
        	}
        	else{
        		cameraPos = new Point3D(cameraPos.x-Math.cos(Math.toRadians(cameraRot)),cameraPos.y,cameraPos.z- Math.sin(Math.toRadians(cameraRot)));
        	}
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) 
        {
        	//do I hit a wall?
        	if(detectCollision(cameraPos, new Point3D(cameraPos.x + Math.cos(Math.toRadians(cameraRot)),cameraPos.y,cameraPos.z + Math.sin(Math.toRadians(cameraRot))))){
        		if(!detectCollision(cameraPos, new Point3D(cameraPos.x,cameraPos.y,cameraPos.z + Math.sin(Math.toRadians(cameraRot))))){
        			cameraPos = new Point3D(cameraPos.x,cameraPos.y,cameraPos.z + Math.sin(Math.toRadians(cameraRot)));
            	}
        		else if(!detectCollision(cameraPos, new Point3D(cameraPos.x + Math.cos(Math.toRadians(cameraRot)),cameraPos.y,cameraPos.z))){
        			cameraPos = new Point3D(cameraPos.x + Math.cos(Math.toRadians(cameraRot)),cameraPos.y,cameraPos.z);
            	}
        	}
        	else{
        		cameraPos = new Point3D(cameraPos.x + Math.cos(Math.toRadians(cameraRot)),cameraPos.y ,cameraPos.z + Math.sin(Math.toRadians(cameraRot)));
        	}
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_UP)) 
        {
        	//do I hit a wall?
        	if(detectCollision(cameraPos, new Point3D(cameraPos.x + Math.sin(Math.toRadians(cameraRot)),cameraPos.y,cameraPos.z - Math.cos(Math.toRadians(cameraRot))))){
        		if(!detectCollision(cameraPos, new Point3D(cameraPos.x,cameraPos.y,cameraPos.z - Math.cos(Math.toRadians(cameraRot))))){
        			cameraPos = new Point3D(cameraPos.x,cameraPos.y,cameraPos.z - Math.cos(Math.toRadians(cameraRot)));
            	}
        		else if(!detectCollision(cameraPos, new Point3D(cameraPos.x + Math.sin(Math.toRadians(cameraRot)),cameraPos.y,cameraPos.z))){
        			cameraPos = new Point3D(cameraPos.x + Math.sin(Math.toRadians(cameraRot)),cameraPos.y,cameraPos.z);
            	}
        	}
        	else{
        		cameraPos = new Point3D(cameraPos.x + Math.sin(Math.toRadians(cameraRot)),cameraPos.y,cameraPos.z - Math.cos(Math.toRadians(cameraRot)));
        	}
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) 
        {
        	//do I hit a wall?
        	if(detectCollision(cameraPos, new Point3D(cameraPos.x - Math.sin(Math.toRadians(cameraRot)),cameraPos.y,cameraPos.z + Math.cos(Math.toRadians(cameraRot))))){
        		if(!detectCollision(cameraPos, new Point3D(cameraPos.x,cameraPos.y,cameraPos.z + Math.cos(Math.toRadians(cameraRot))))){
        			cameraPos = new Point3D(cameraPos.x,cameraPos.y,cameraPos.z + Math.cos(Math.toRadians(cameraRot)));
            	}
        		else if(!detectCollision(cameraPos, new Point3D(cameraPos.x - Math.sin(Math.toRadians(cameraRot)),cameraPos.y,cameraPos.z))){
        			cameraPos = new Point3D(cameraPos.x - Math.sin(Math.toRadians(cameraRot)),cameraPos.y,cameraPos.z);
            	}
        	}
        	else{
        		cameraPos = new Point3D(cameraPos.x - Math.sin(Math.toRadians(cameraRot)),cameraPos.y,cameraPos.z + Math.cos(Math.toRadians(cameraRot)));
        	}
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
        {
        	if(!sChecker){
        		spellCast = !spellCast;
        		lumos = false;
        	}
        	sChecker = true;
        }
        
        if(!Keyboard.isKeyDown(Keyboard.KEY_P)){
        	pChecker = false;
        }
        if(!Keyboard.isKeyDown(Keyboard.KEY_L)){
        	lChecker = false;
        }
        if(!Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
        	sChecker = false;
        }
    }

    /**
     * Render the scene to the screen
     */
    @Override
    public void render() 
    {
    	glLoadIdentity();
        //This clears the screen.
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glRotatef(cameraRot, 0, 1, 0);
        if(paused){
        	glRotatef((float)90, 1, 0, 0); //used to look down
        }
        glTranslatef(-(float) cameraPos.x, -(float) cameraPos.y, -(float) cameraPos.z);

        if(spellCast) {
            glEnable(GL_LIGHT1);
            glDisable(GL_LIGHT0);
            //glColor3d(0, 0, 1);
            drawSquare(new Point3D(spellX, spellY, spellZ));
        } 
        else if(lumos){
            glEnable(GL_LIGHT0);
            glDisable(GL_LIGHT1);
        }
        else{
        	glDisable(GL_LIGHT0);
            glDisable(GL_LIGHT1);
        }

        float position2[] = { spellX, spellY, spellZ, 1.0f };
        glLight(GL_LIGHT1, GL_AMBIENT, asFloatBuffer(ambientLight));
        glLight(GL_LIGHT1, GL_DIFFUSE, asFloatBuffer(diffuseLight));
        glLight(GL_LIGHT1, GL_POSITION, asFloatBuffer(position2));
        glLight(GL_LIGHT1, GL_SPECULAR, asFloatBuffer(specularLight));
        glLightf(GL_LIGHT1, GL_LINEAR_ATTENUATION, .005f);

		textureMaze.bind();
		//maze rendering
        ArrayList<Quad> quads = objReader.getQuadrilaterals();
        for(int i = 0; i < quads.size(); i++) {
            Quad quad = quads.get(i);
            glPolygonMode(GL_FRONT, GL_FILL);
            glBegin(GL_POLYGON);

			glNormal3d(quad.getN1().x, quad.getN1().y, quad.getN1().z);
			glTexCoord2d(quad.getT1().x, 1 - quad.getT1().y);
			glVertex3d(quad.getV1().x, quad.getV1().y, quad.getV1().z);

			glNormal3d(quad.getN2().x, quad.getN2().y, quad.getN2().z);
			glTexCoord2d(quad.getT2().x, 1 - quad.getT2().y);
			glVertex3d(quad.getV2().x, quad.getV2().y, quad.getV2().z);

			glNormal3d(quad.getN3().x, quad.getN3().y, quad.getN3().z);
			glTexCoord2d(quad.getT3().x, 1-quad.getT3().y);
			glVertex3d(quad.getV3().x, quad.getV3().y, quad.getV3().z);
			glEnd();
        }

		glPushMatrix();
		glTranslated(cameraPos.x, cameraPos.y-8, cameraPos.z);
		glRotated(180-cameraRot, 0, 1, 0);
		textureUser.bind();
		for(int b = 0; b < user.polygons.size();b++){
			Quad quad = user.polygons.get(b);
			glBegin(GL_POLYGON);
			glNormal3d(quad.getN1().x, quad.getN1().y, quad.getN1().z);
			glTexCoord2d(quad.getT1().x, 1 - quad.getT1().y);
			glVertex3d(quad.getV1().x, quad.getV1().y, quad.getV1().z);

			glNormal3d(quad.getN2().x, quad.getN2().y, quad.getN2().z);
			glTexCoord2d(quad.getT2().x, 1 - quad.getT2().y);
			glVertex3d(quad.getV2().x, quad.getV2().y, quad.getV2().z);

			glNormal3d(quad.getN3().x, quad.getN3().y, quad.getN3().z);
			glTexCoord2d(quad.getT3().x, 1-quad.getT3().y);
			glVertex3d(quad.getV3().x, quad.getV3().y, quad.getV3().z);
			glEnd();
		}
		glPopMatrix();

    	glPushMatrix();
    	glTranslated(player1.position.x, player1.position.y, player1.position.z);
    	glRotated(90 - player1.angle, 0, 1, 0);
		textureP1.bind();
    	for(int b = 0; b < player1.polygons.size();b++){
			Quad quad = player1.polygons.get(b);
    		glBegin(GL_POLYGON);
			glNormal3d(quad.getN1().x, quad.getN1().y, quad.getN1().z);
			glTexCoord2d(quad.getT1().x, 1 - quad.getT1().y);
			glVertex3d(quad.getV1().x, quad.getV1().y, quad.getV1().z);

			glNormal3d(quad.getN2().x, quad.getN2().y, quad.getN2().z);
			glTexCoord2d(quad.getT2().x, 1 - quad.getT2().y);
			glVertex3d(quad.getV2().x, quad.getV2().y, quad.getV2().z);

			glNormal3d(quad.getN3().x, quad.getN3().y, quad.getN3().z);
			glTexCoord2d(quad.getT3().x, 1-quad.getT3().y);
			glVertex3d(quad.getV3().x, quad.getV3().y, quad.getV3().z);
	        glEnd();
    	}

    	glPopMatrix();
    	glPushMatrix();
    	glTranslated(player2.position.x, player2.position.y, player2.position.z);
    	glRotated(90 - player2.angle, 0, 1, 0);
		textureP2.bind();
    	for(int b = 0; b < player2.polygons.size();b++){
			Quad quad = player1.polygons.get(b);
    		glBegin(GL_POLYGON);
			glNormal3d(quad.getN1().x, quad.getN1().y, quad.getN1().z);
			glTexCoord2d(quad.getT1().x, 1 - quad.getT1().y);
			glVertex3d(quad.getV1().x, quad.getV1().y, quad.getV1().z);

			glNormal3d(quad.getN2().x, quad.getN2().y, quad.getN2().z);
			glTexCoord2d(quad.getT2().x, 1 - quad.getT2().y);
			glVertex3d(quad.getV2().x, quad.getV2().y, quad.getV2().z);

			glNormal3d(quad.getN3().x, quad.getN3().y, quad.getN3().z);
			glTexCoord2d(quad.getT3().x, 1-quad.getT3().y);
			glVertex3d(quad.getV3().x, quad.getV3().y, quad.getV3().z);
	        glEnd();
    	}
    	glPopMatrix();
    	glPushMatrix();
    	glTranslated(player3.position.x, player3.position.y, player3.position.z);
    	glRotated(90 - player3.angle, 0, 1, 0);
		textureP3.bind();
    	for(int b = 0; b < player3.polygons.size();b++){
			Quad quad = player1.polygons.get(b);
    		glBegin(GL_POLYGON);
			glNormal3d(quad.getN1().x, quad.getN1().y, quad.getN1().z);
			glTexCoord2d(quad.getT1().x, 1 - quad.getT1().y);
			glVertex3d(quad.getV1().x, quad.getV1().y, quad.getV1().z);

			glNormal3d(quad.getN2().x, quad.getN2().y, quad.getN2().z);
			glTexCoord2d(quad.getT2().x, 1 - quad.getT2().y);
			glVertex3d(quad.getV2().x, quad.getV2().y, quad.getV2().z);

			glNormal3d(quad.getN3().x, quad.getN3().y, quad.getN3().z);
			glTexCoord2d(quad.getT3().x, 1-quad.getT3().y);
			glVertex3d(quad.getV3().x, quad.getV3().y, quad.getV3().z);
	        glEnd();
    	}
    	glPopMatrix();
    }
    
    private boolean detectCollision(Point3D p1, Point3D p2){
    	ArrayList<Quad> quads = objReader.getQuadsToCheck();
    	ArrayList<Point3D> lineEndpoints = new ArrayList<Point3D>();
    	ArrayList<Line3D> lines = new ArrayList<Line3D>();
    	for(int i = 0; i < quads.size(); i++) {
    		Quad quad = quads.get(i);
    		Point3D preTestPoint = quad.getV1();
        	for(int j = 0; j < 3;j++){
        		Point3D testPoint = new Point3D(0,0,0);
        		if(j == 0){
        			testPoint = quad.getV1();
        		}
        		else if(j == 1){
        			testPoint = quad.getV2();
        		}
        		else if(j == 2){
        			testPoint = quad.getV3();
        		}
        		if((preTestPoint.y >= 10 && testPoint.y <= 10) ||(preTestPoint.y <= 10 && testPoint.y >= 10)){
	        		double xval = 0;
					double zval = 0;
					if(preTestPoint.x == testPoint.x){
						xval = preTestPoint.x;
					}
					else{
						double slopex = (preTestPoint.y - testPoint.y) / (preTestPoint.x - testPoint.x);
						double bx = preTestPoint.y - (slopex * preTestPoint.x);
						xval = (10 - bx) / slopex;
					}
					if(preTestPoint.z == testPoint.z){
						zval = preTestPoint.z;
					}
					else{
						double slopez = (preTestPoint.y - testPoint.y) / (preTestPoint.z - testPoint.z);
						double bz = preTestPoint.y - (slopez * preTestPoint.z);
						zval = (10 - bz) / slopez;
					}
					lineEndpoints.add(new Point3D(xval, 10, zval));
        		}
				preTestPoint = testPoint;
        	}
        	
        	if(lineEndpoints.size() == 1){
        		lines.add(new Line3D(lineEndpoints.get(0), lineEndpoints.get(0)));
			}
			else if(lineEndpoints.size() == 2){
				lines.add(new Line3D(lineEndpoints.get(0), lineEndpoints.get(1)));
			}
        	lineEndpoints = new ArrayList<Point3D>();
        }
//    	System.out.println("lineEndpoints: " +lineEndpoints.size());
//    	System.out.println("lines: " +lines.size());
    	for(int i = 0; i < lines.size(); i++) {
    		//plug the points into the equation
    		Point3D p3 = lines.get(i).end;
    		Point3D p4 = lines.get(i).start;
    		
    		double m1 = 0;
    		if((p2.z - p1.z) == 0){
    			m1 = 0;
    		}
    		else if ((p2.x - p1.x) == 0){
    			m1 = 0;
    		}
    		else{
    			m1 = (p2.z - p1.z)/ (p2.x - p1.x);
    		}
    		double m2 = 0;
    		if((p4.z - p3.z) == 0){
    			m2 = 0;
    		}
    		else if ((p4.x - p3.x) == 0){
    			m2 = 0;
    		}
    		else{
    			m2 = (p4.z - p3.z)/ (p4.x - p3.x);
    		}
    		double b1 = p1.z - (m1 * p1.x);
    		double b2 = p3.z - (m2 * p3.x);
    		double x = 0;
    		if((b2 - b1) == 0){
    			x = 0;
    		}
    		else if((m1 - m2) == 0){
    			x = 0;
    		}
    		else{
    			x = (b2 - b1) / (m1 - m2);
    		}
    		double z = (m1 * x) + b1;
    		//System.out.println(x + ", " + z);
    		//Point3D intersectingPoint = new Point3D(x,10,z);
    		
    		//is it between the 2 line segments?
    		boolean fallsBetweenX1 = false;
    		boolean fallsBetweenZ1 = false;
    		boolean fallsBetweenX2 = false;
    		boolean fallsBetweenZ2 = false;
    		if(p1.x <= x && p2.x >= x){
    			fallsBetweenX1 = true;
    		}
    		if(p3.z <= z && p4.z >= z){
    			fallsBetweenX2 = true;
    		}
    		if(p1.x <= x && p2.x >= x){
    			fallsBetweenZ1 = true;
    		}
    		if(p3.z <= z && p4.z >= z){
    			fallsBetweenZ2 = true;
    		}
//    		System.out.println("x: " + x + ", z: " + z + ", p1.x: " + p1.x + ", p1.z: " + p1.z+ ", p2.x: " + p2.x+ ", p2.z: " + p2.z + ", p3.x: " + p3.x + ", p3.z: " + p3.z+ ", p4.x: " + p4.x+ ", p4.z: " + p4.z);
//    		System.out.println(fallsBetweenX1 + " " + fallsBetweenX2 + " " + fallsBetweenZ1 + " " +fallsBetweenZ2);
    		if(fallsBetweenX1 && fallsBetweenX2 && fallsBetweenZ1 && fallsBetweenZ2){
    			System.out.println(fallsBetweenX1 + " " + fallsBetweenX2 + " " + fallsBetweenZ1 + " " +fallsBetweenZ2);
    			return true;
    		}
    		
    	}
    	
    	return false;
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

		textureSpell.bind();
        //front/back
        glBegin(GL_POLYGON);
		glTexCoord2d(0, 0);
        glVertex3d(xPos, yPos, zNeg);
		glTexCoord2d(0, 1);
        glVertex3d(xPos, yNeg, zNeg);
		glTexCoord2d(1, 1);
        glVertex3d(xNeg, yNeg, zNeg);
		glTexCoord2d(1, 0);
        glVertex3d(xNeg, yPos, zNeg);
        glEnd();
        glBegin(GL_POLYGON);
		glTexCoord2d(0, 0);
        glVertex3d(xPos, yPos, zPos);
		glTexCoord2d(0, 1);
        glVertex3d(xPos, yNeg, zPos);
		glTexCoord2d(1, 1);
        glVertex3d(xNeg, yNeg, zPos);
		glTexCoord2d(1, 0);
        glVertex3d(xNeg, yPos, zPos);
        glEnd();
        
        //side/side
        glBegin(GL_POLYGON);
		glTexCoord2d(0, 0);
        glVertex3d(xPos, yPos, zNeg);
		glTexCoord2d(0, 1);
        glVertex3d(xPos, yPos, zPos);
		glTexCoord2d(1, 1);
        glVertex3d(xPos, yNeg, zPos);
		glTexCoord2d(1, 0);
        glVertex3d(xPos, yNeg, zNeg);
        glEnd();
        glBegin(GL_POLYGON);
		glTexCoord2d(0, 0);
        glVertex3d(xNeg, yPos, zNeg);
		glTexCoord2d(0, 1);
        glVertex3d(xNeg, yPos, zPos);
		glTexCoord2d(1, 1);
        glVertex3d(xNeg, yNeg, zPos);
		glTexCoord2d(1, 0);
        glVertex3d(xNeg, yNeg, zNeg);
        glEnd();
        
        //top
        glBegin(GL_POLYGON);
		glTexCoord2d(0, 0);
        glVertex3d(xPos, yPos, zPos);
		glTexCoord2d(0, 1);
        glVertex3d(xPos, yPos, zNeg);
		glTexCoord2d(1, 1);
        glVertex3d(xNeg, yPos, zNeg);
		glTexCoord2d(1, 0);
        glVertex3d(xNeg, yPos, zPos);
        glEnd();
        
        //bottom
        glBegin(GL_POLYGON);
		glTexCoord2d(0, 0);
        glVertex3d(xPos, yNeg, zPos);
		glTexCoord2d(0, 1);
        glVertex3d(xPos, yNeg, zNeg);
		glTexCoord2d(1, 1);
        glVertex3d(xNeg, yNeg, zNeg);
		glTexCoord2d(1, 0);
        glVertex3d(xNeg, yNeg, zPos);
        glEnd();
    }
    
    /**
     * Find the next position of the player given as a parameter. 
     * Each AI should move towards the user, but should be blocked by walls. 
     * The angle the player moves at has already been updated.
     * 
     * @param player
     * @return The point representing the new position of the player.
     */
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
	   			endPoint = new Point3D(player.position.x+.3 * Math.cos(Math.toRadians(currentDegrees)),3,player.position.z+.3 * Math.cos(Math.toRadians(90-(currentDegrees))));
	   		}
	   		else if(currentDegrees > 90 && currentDegrees <= 180){
	   			endPoint = new Point3D(player.position.x-.3 * Math.cos(Math.toRadians(180-currentDegrees)),3,player.position.z+.3 * Math.cos(Math.toRadians(90-(180-currentDegrees))));
	   		}
	   		else if(currentDegrees > 180 && currentDegrees <= 270){
	   			endPoint = new Point3D(player.position.x-.3 * Math.cos(Math.toRadians(currentDegrees-180)),3,player.position.z-.3 * Math.cos(Math.toRadians(90-(currentDegrees-180))));
	   		}
	   		else if(currentDegrees > 270 && currentDegrees <= 360){
	   			endPoint = new Point3D(player.position.x+.3 * Math.cos(Math.toRadians(360-currentDegrees)),3,player.position.z-.3 * Math.cos(Math.toRadians(90-(360-currentDegrees))));
	   		}
	   		return endPoint;
   		}
		else{
			return player.position;
		}
	}
    
    /**
     * Uses IK solver logic to update the angle of the player so it faces towards the user.
     * 
     * @param player
     * @return The updated angle that faces closer to the USER than the previous angle.
     */
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
    
    /**
     * Gives the dot product of 2 points
     * @param point1
     * @param point2
     * @return
     */
    public double dot(Point3D point1, Point3D point2){
    	double returnval = 0;
    	returnval = point1.x * point2.x + point1.y * point2.y + point1.z * point2.z;
    	return returnval;
    }
    
    /**
     * Gives the cross product of 2 points
     * @param point1
     * @param point2
     * @return
     */
    public Point3D cross(Point3D point1, Point3D point2){
    	return new Point3D((float)(point1.y * point2.z - point1.z * point2.y), (float)(point1.y * point2.x - point1.x * point2.y),(float)(point1.x * point2.y - point1.y * point2.x));
    }
    
    /**
     * Gives the 2D cross product of 2 points
     * @param point1
     * @param point2
     * @return
     */
    public double cross2D(Point3D point1, Point3D point2){
    	return point1.x * point2.y - point2.x * point1.y;
    }
    
    /**
     * Gives the normalized point of a point.
     * @param point
     * @return
     */
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
    
    /**
     * Calculates the distance between 2 points
     * @param p1
     * @param p2
     * @return
     */
    public double distance(Point3D p1, Point3D p2){
    	return Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y) + (p2.z - p1.z) * (p2.z - p1.z));
    }
    
    /**
     * Calculates the distance between the X and Y values of 2 points
     * @param p1
     * @param p2
     * @return
     */
    public double distanceXY(Point3D p1, Point3D p2){
    	return Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y));
    }
    
    /**
     * Calculates the distance between the X and Z values of 2 points
     * @param p1
     * @param p2
     * @return
     */
    public double distanceXZ(Point3D p1, Point3D p2){
    	return Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.z - p1.z) * (p2.z - p1.z));
    }
    
    /**
     * Calculates the distance between the Y and Z values of 2 points
     * @param p1
     * @param p2
     * @return
     */
    public double distanceYZ(Point3D p1, Point3D p2){
    	return Math.sqrt((p2.y - p1.y) * (p2.y - p1.y) + (p2.z - p1.z) * (p2.z - p1.z));
    }
    
    /**
     * Calculates the distance between the X values of 2 points
     * @param p1
     * @param p2
     * @return
     */
    public double distanceX(Point3D p1, Point3D p2){
    	return Math.sqrt((p2.x - p1.x) * (p2.x - p1.x));
    }
    
    /**
     * Calculates the distance between the Y values of 2 points
     * @param p1
     * @param p2
     * @return
     */
    public double distanceY(Point3D p1, Point3D p2){
    	return Math.sqrt((p2.y - p1.y) * (p2.y - p1.y));
    }
    
    /**
     * Calculates the distance between the Z values of 2 points
     * @param p1
     * @param p2
     * @return
     */
    public double distanceZ(Point3D p1, Point3D p2){
    	return Math.sqrt((p2.z - p1.z) * (p2.z - p1.z));
    }
}
