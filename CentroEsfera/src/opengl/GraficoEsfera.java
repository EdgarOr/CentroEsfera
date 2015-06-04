package opengl;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.GLUT;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import org.apache.commons.math3.fraction.Fraction;



/**
 * PruebaOpenGL.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class GraficoEsfera implements GLEventListener{
    private static GLUT glut; //Una brocha
    private static float r, x, y, z;
    private  static float posy = 1f;
    
    
    public static void mostrarGrafico(float r, float x, float y, float z) {
        final Frame frame = new Frame("Esfera");
        GLCanvas canvas = new GLCanvas();
        glut = new GLUT();
        
        GraficoEsfera.r = r;
        GraficoEsfera.x = x;
        GraficoEsfera.y = y;
        GraficoEsfera.z = z;
        System.out.println("FUNCIONA " + GraficoEsfera.x + " " + GraficoEsfera.y + " " + GraficoEsfera.
                z + " R: " + r);
        
        canvas.addGLEventListener(new GraficoEsfera());
        frame.add(canvas);
        frame.setSize(640, 480);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                frame.dispose();
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        //System.exit(0);
                        
                    }
                }).start();
              
            }
        });
        
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));
        
        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();
        float posx = 1f, posz = 1f;

        if (height <= 0) { // avoid a divide by zero error!
        
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(80.0f, h, 1.0, 10.0);
        glu.gluLookAt(posx, posy, posz, 0, 0, 0, posx, posy+1, posz);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        
        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        //Ejes coordenados
        gl.glColor3f(0,0,0);
        gl.glBegin(GL.GL_LINES);
            gl.glColor3f(1,0,0);//X
            gl.glVertex3f(-100, 0, 0);
            gl.glVertex3f(100, 0, 0);
            gl.glColor3f(0,1,0);//Y
            gl.glVertex3f(0, -100, 0);
            gl.glVertex3f(0, 100, 0);
            gl.glColor3f(0,0,1);//Z
            gl.glVertex3f(0, 0, -100);
            gl.glVertex3f(0, 0, 100);
        gl.glEnd();
        
        //Esfera
        gl.glColor3f(0, 0, 1);
        gl.glTranslatef(x, y, z);
        
       glut.glutWireSphere(r, 20, 20);
        
        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

