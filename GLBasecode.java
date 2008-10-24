import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.Animator;
import java.util.Calendar;

public class GLBasecode implements GLEventListener {

	private Calendar now = null;
	private long ms = 0;
	private int frames = 0;
	private static Frame frame = null;
	public GL gl;
	public Obj obj;
	public static String[] files;
	public static void main(String[] args) {
		files = args;
		frame = new Frame("Robert Schadek");
		GLCanvas canvas = new GLCanvas();

		canvas.addGLEventListener(new GLBasecode());

		frame.add(canvas);
		frame.setSize(640, 480);

		final Animator animator = new Animator(canvas);

		frame.addWindowListener(new WindowAdapter() {

				public void windowClosing(WindowEvent e) {
				// Run this on another thread than the AWT event queue to
				// make sure the call to Animator.stop() completes before
				// exiting
				new Thread(new Runnable() {

					public void run() {
					animator.stop();
					System.exit(0);
					}
					}).start();
				}
				});
		frame.setVisible(true);
		animator.start();
	}

	/**
	 * Diese Funktion wird durch den GLEventListener kurz nach dem Start des Programms
	 * aufgerufen, bevor das erste mal die display-Funktion aufgerufen wird. 
	 */
	public void init(GLAutoDrawable drawable) {
		gl = drawable.getGL();

		/* Einige OpenGL-Werte ausgeben.
		   System.out.println("OpenGL Java-Implementation: " + gl.getClass().getName());
		   System.out.println(drawable.getChosenGLCapabilities());
		   System.out.println("GL_EXTENSIONS: " + gl.glGetString(GL.GL_EXTENSIONS));
		   System.out.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
		   System.out.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
		   System.out.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));
		   */
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glShadeModel(GL.GL_SMOOTH);
		gl.glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

		gl.setSwapInterval(1);
		System.out.println(this.files[0]);
		this.obj = new Obj(this.files[0], this.gl);
	}

	/**
	 * Diese Funktion wird aufgerufen, wenn sich das Fenster des Programms in der Groesse
	 * aendert.
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL gl = drawable.getGL();
		GLU glu = new GLU();
		float aspect;


		if (height == 0) {
			height = 1;
		}
		aspect = (float) width / (float) height;

		// Setze die Einstellungen fuer die PROJECTION_MATRIX.
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0f, aspect, 0.1f, 100.0f);

		// Nach diesem Befehl ist die aktuelle Matrix die MODELVIEW_MATRIX.
		gl.glMatrixMode(GL.GL_MODELVIEW);
		// Diese wird hiermit auf die Einheitsmatrix gesetzt.
		gl.glLoadIdentity();
	}

	/**
	 * Diese Funktion wird so oft wie moeglich aufgerufen, hier muss gezeichnet werden.
	 */
	public void display(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(3.0f, 0.0f, -12.0f);

		// Ab hier bitte die Aenderungen durchfuehren
		this.obj.render();

		frame();
	}

	private void frame() {
		now = Calendar.getInstance();
		if (now.getTimeInMillis() >= (ms + 1000)) {
			ms = now.getTimeInMillis();
			System.out.println(frames);
			frames = 1;
		} else {
			frames++;
		}
	}

	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
	}
}
