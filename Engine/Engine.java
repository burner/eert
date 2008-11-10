/*
 *EERT = EERT enhanced rendering technology
 *
 *Copyright (C) [2008] [Robert "BuRnEr" Schadek]

 *This program is free software; you can redistribute it and/or modify it under 
 *the terms of the GNU General Public License as published by the Free Software
 *Foundation; either version 3 of the License, 
 *or (at your option) any later version.

 *This program is distributed in the hope that it will be useful, but WITHOUT 
 *ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 *FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 *You should have received a copy of the GNU General Public License along with 
 *this program; if not, see <http://www.gnu.org/licenses/>.
 */
package Engine;

import javax.media.opengl.GL;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import Types.*;
import Util.Camera;
import Util.EFrame;
import Util.EInput;
import Util.UHPT;
import java.util.Calendar;

public class Engine implements GLEventListener {

    private Obj obj;
    private Calendar now = null;
    private long ms = 0;
    private int frames = 0;
    public Camera cam;
    private EFrame frame;
    private EInput input;
    private long lastFrame;

    public Engine(Camera cam, EFrame frame) {
        this.cam = cam;
        this.frame = frame;
    }

    public void display(GLAutoDrawable glDrawable) {
        GL gl = glDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        cam.translateAccordingToCameraPosition(gl);
        //cam.camRot(gl);
        //cam.giveInfo();

        this.obj.setRot(0.0f, 0.0f, 0.0f);
        this.obj.render(gl);

        frame();
        UHPT.lastFrame = System.nanoTime();
    }

    public void displayChanged(GLAutoDrawable gl, boolean modeChanged, boolean devChanged) {
    }

    public void init(GLAutoDrawable glDrawable) {
        final GL gl = glDrawable.getGL();
        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL.GL_LIGHTING);
        gl.glPushMatrix();
        gl.glTranslatef(4.0f, 4.0f, 4.0f);
        gl.glEnable(GL.GL_LIGHT0);
        gl.glTranslatef(-4.0f, -4.0f, -4.0f);
        gl.glPopMatrix();
        gl.glEnable(GL.GL_CULL_FACE);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        obj = new Obj("suzann2.obj", gl);
        this.input = new EInput(this.cam);
        glDrawable.addKeyListener(this.input);
        glDrawable.addMouseListener(this.input);
        glDrawable.addMouseMotionListener(this.input);
    }

    public void reshape(GLAutoDrawable glDrawable, int x, int y, int width, int height) {
        final GL gl = glDrawable.getGL();
        GLU glu = new GLU();
        if (height <= 0) {
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50.0f, h, 1.0, 1000.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    private void frame() {
        now = Calendar.getInstance();
        if (now.getTimeInMillis() >= (ms + 1000)) {
            ms = now.getTimeInMillis();
            this.frame.setTitle(obj.fac.length + " faces at " + frames + " FPS");
            frames = 1;
        } else {
            frames++;
        }
    }
}