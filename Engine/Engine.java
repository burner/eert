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

import Util.Logic.EObjectHandler;
import Types.Geometrie.Obj;
import javax.media.opengl.GL;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;


import Util.Logic.Camera;
import Util.Logic.EFrame;
import Util.Logic.EInfo;
import Util.Logic.EInput;
import Util.Logic.UHPT;
import Util.Prelude.ETextureMaster;
import java.util.Calendar;

public class Engine implements GLEventListener {
    public ETextureMaster texMaster;
    public int frames = 0;
    public Camera cam;
    public EFrame frame;
    public int fps;
    public boolean drawInfo;
    public EOcMaster root;
    private EInput input;
    private String szene;
    private EObjectHandler objectHandler;
    private EInfo eInfo;
    private EMusicPlayerMP3 music;
    private Calendar now = null;
    private long ms = 0;

    public Engine(Camera cam, String szene, EFrame frame) {
        this.szene = szene;
        this.cam = cam;
        this.frame = frame;
    }

    public void init(GLAutoDrawable glDrawable) {
        final GL gl = glDrawable.getGL();
        //this.texMaster = new ETextureMaster(gl);
        this.objectHandler = new EObjectHandler(this.cam, this.szene, gl, this);
        this.root = new EOcMaster(this.objectHandler.objIns, this.objectHandler.obj, gl);
        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        //gl.glEnable(GL.GL_LIGHTING);
        gl.glPushMatrix();
        gl.glTranslatef(4.0f, 4.0f, 4.0f);
        gl.glTranslatef(-4.0f, -4.0f, -4.0f);
        gl.glPopMatrix();
        gl.glEnable(GL.GL_CULL_FACE);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        this.input = new EInput(this.cam, this);
        glDrawable.addKeyListener(this.input);
        glDrawable.addMouseListener(this.input);
        glDrawable.addMouseMotionListener(this.input);
        this.eInfo = new EInfo(this);        
        
        //Music doesn't work right now
        /*
        this.music = new EMusicPlayerWave("04-portishead-the_rip.wav");
        this.music.start();
         */
        this.music = new EMusicPlayerMP3("04-portishead-the_rip.mp3");
       
    }

    public void display(GLAutoDrawable glDrawable) {
        GL gl = glDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glEnable(GL.GL_TEXTURE_2D);
        long ocTimeTest = System.currentTimeMillis();
        this.root = new EOcMaster(this.objectHandler.objIns, this.objectHandler.obj, gl);
        long ocTimeTestA = System.currentTimeMillis();
        
        cam.translateAccordingToCameraPosition(gl);
        //cam.camRot(gl);
        //cam.giveInfo();
        //this.root.drawBox(gl);
        this.root.drawOctree(gl);

        //if true draw Info on screen
        frame();
        if (this.drawInfo) {
            this.eInfo.octimeBuild = new Long(ocTimeTestA - ocTimeTest).toString();
            this.eInfo.drawInfo(glDrawable);            
        }
        UHPT.lastFrame = System.nanoTime();
    }

    public void displayChanged(GLAutoDrawable gl, boolean modeChanged, boolean devChanged) {
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
            this.frame.setTitle(frames + " FPS");
            this.fps = frames;
            frames = 1;
        } else {
            frames++;
        }
    }
}