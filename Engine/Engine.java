package Engine;

import javax.media.opengl.GL;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import Types.*;

public class Engine implements GLEventListener {

	private Obj[] objects;

	public void display(GLAutoDrawable gl) {
	}

	public void displayChanged(GLAutoDrawable gl, boolean modeChanged, boolean devChanged) {
	}

	public void init(GLAutoDrawable glDrawable) {
		final GL gl = glDrawable.getGL();
		gl.glShadeModel(GL.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		
	}

	public void reshape(GLAutoDrawable glDrawable, int x, int y, int width, int height) {
		final GL gl = glDrawable.getGL();
                GLU glu = new GLU();
		if(height <= 0) {
			height = 1;
		}
		final float h = (float)width / (float)height;   
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(50.0f, h, 1.0, 1000.0);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
}
