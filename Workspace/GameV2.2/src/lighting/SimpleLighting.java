

import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;


public class SimpleLighting {

    public static void main(String arg[])
    {
        new SimpleLighting();
    }

    public SimpleLighting()
    {
        try {
            boolean close = false;
            float rotation = 0;
            DisplayMode mode = Display.getDesktopDisplayMode();
            Display.create();
            GL11.glEnable(GL11.GL_DEPTH_TEST); // enable depth testing
            GL11.glEnable(GL11.GL_LIGHTING); // enable lighting
            GL11.glEnable(GL11.GL_LIGHT0); // enable light 0

            // calculate the lighting for both front and back faces correctly
            GL11.glLightModeli(GL11.GL_LIGHT_MODEL_TWO_SIDE,GL11.GL_TRUE);

            // use the defined color as the material for the square
            GL11.glEnable(GL11.GL_COLOR_MATERIAL);
            GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK,GL11.GL_AMBIENT_AND_DIFFUSE);

            // set viewport to full screen
            GL11.glViewport(0, 0, mode.getWidth(), mode.getHeight());
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            float ratio = 1.0f * mode.getHeight() / mode.getWidth();
            GL11.glFrustum(-1.0f, 1.0f, -ratio, ratio, 5.0f, 60.0f);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);

            while (!close) {
                rotation = rotation + 0.1f;
                GL11.glClearColor(0.0f, 0.2f, 0.5f, 1.0f);
                // clear both color and depth buffer
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
                GL11.glLoadIdentity();
                // position  thelight to the right of the square
                GL11.glLight(GL11.GL_LIGHT0,GL11.GL_POSITION,floatBuffer(20,0,-20,1));
                // translate the coordinates to -20 z
                GL11.glTranslatef(0, 0, -20);
                // rotate the coordinates about the y axis
                GL11.glRotatef(rotation, 0, 1, 0);

                // draw square
                GL11.glBegin(GL11.GL_QUADS);
                // define the normal to the square
                GL11.glNormal3f(0, 0, 1.0f);
                // define colour
                GL11.glColor3f(1.0f, 0.0f, 1.0f);
                // define the shape with front face facing you.
                GL11.glVertex3f(1.0f, 1.0f, 0);
                GL11.glVertex3f(-1.0f, 1.0f, 0);
                GL11.glVertex3f(-1.0f, -1.0f, 0);
                GL11.glVertex3f(1.0f, -1.0f, 0);
                GL11.glEnd();

                Display.update();
                close = Display.isCloseRequested();
            }
            Display.destroy();
            System.exit(0);
        } catch (LWJGLException ex) {
            Logger.getLogger(SimpleLighting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // method to populate a FloatBuffer with 4 values.
    public FloatBuffer floatBuffer(float a, float b, float c, float d)
    {
        float[] data = new float[]{a,b,c,d};
        FloatBuffer fb = BufferUtils.createFloatBuffer(data.length);
        fb.put(data);
        fb.flip();
        return fb;
    }


}
   