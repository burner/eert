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

import Types.Geometrie.ESkyBox;
import Types.Illumination.LightManagement;
import Util.Logic.EObjectHandler;
import javax.media.opengl.GL;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;


import Util.Logic.Camera;
import Util.Logic.EFrame;
import Util.Logic.EInfo;
import Util.Logic.EInput;
import Util.Logic.UHPT;
import java.util.Calendar;

public class Engine implements GLEventListener {

    public int frames = 0;
    public Camera cam;
    public EFrame frame;
    public int fps;
    public boolean drawInfo;
    public EOcMaster root;
    private EInput input;
    private String szene;
    public EObjectHandler objectHandler;
    public EInfo eInfo;
    private EMusicPlayerMP3 music;
    private Calendar now = null;
    private long ms = 0;
    public String[] textures;
    public ESkyBox skybox;

    public LightManagement lights;

    public Engine(Camera cam, String szene, EFrame frame) {
        this.szene = szene;
        this.cam = cam;
        this.frame = frame;
        this.music = new EMusicPlayerMP3("04-portishead-the_rip.mp3");
        this.music.play();
    }

    public void init(GLAutoDrawable glDrawable) {
        final GL gl = glDrawable.getGL();
        this.objectHandler = new EObjectHandler(this.cam, this.szene, gl, this);
        this.root = new EOcMaster(this, this.objectHandler.objIns, this.objectHandler.obj, gl, this.cam);
        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        //gl.glEnable(GL.GL_LIGHTING);
        gl.glPushMatrix();
        gl.glPopMatrix();
        gl.glEnable(GL.GL_CULL_FACE);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        this.input = new EInput(this.cam, this);
        glDrawable.addKeyListener(this.input);
        glDrawable.addMouseListener(this.input);
        glDrawable.addMouseMotionListener(this.input);
        this.eInfo = new EInfo(this);
    }

    public void display(GLAutoDrawable glDrawable) {
        GL gl = glDrawable.getGL();

        //funny timer stuff ... do not change        
        UHPT.updateUHPT();
        
        //update all objsIns
        this.objectHandler.updateObjIns();     

        //OpenGL housekeeping
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glEnable(GL.GL_TEXTURE_2D);

        
        //build octree
        long ocTimeTest = System.currentTimeMillis();
        this.root = new EOcMaster(this, this.objectHandler.objIns, this.objectHandler.obj, gl, this.cam);
        long ocTimeTestA = System.currentTimeMillis();


        //set cam location
        cam.translateAccordingToCameraPosition(gl);


        //draw objects
        this.root.drawOctree(gl);

        this.skybox.draw();

        //if true draw Info on screen
        frame();
        if (this.drawInfo) {            
            this.eInfo.octimeBuild = new Long(ocTimeTestA - ocTimeTest).toString();
            this.eInfo.drawInfo(glDrawable);
        }


        
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

	//Info needed to make the sphere
	//around the frustum
        this.cam.farPlane = 500.0f;
        this.cam.nearPlane = 1.0f;
        this.cam.viewAngle = 45.0f;
	this.cam.zHalf = (this.cam.farPlane - this.cam.nearPlane)/2;

        glu.gluPerspective(45.0f, h, 1.0, 500.0);
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
