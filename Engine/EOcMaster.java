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

import Types.Geometrie.Obj;
import Types.Geometrie.ObjIns;
import Types.Geometrie.Vector;
import Types.Illumination.PointLight;
import Util.Geometrie.VectorUtil;
import Util.Logic.Camera;
import javax.media.opengl.GL;

public class EOcMaster {

    public int numNodes;
    public float[][] frustum = new float[6][4];
    private ObjIns[] objs;
    private Obj[] realObjs;
    public boolean[] drawn;
    public boolean[] lightDrawn;
    private EOcNode root;
    private Vector middle;
    private float radius;
    private float bSize = 0;
    private float bHalf;
    private GL gl;
    public int facRender;
    public Camera cam;
    public Engine engine;
    public int treeDepth;
    public float frustLight;
    private int shadowList;

    public EOcMaster(Engine engine, ObjIns[] allObj, Obj[] realObj, GL gl, Camera cam) {
        this.treeDepth = 4;
        this.engine = engine;
        this.cam = cam;
        this.drawn = new boolean[allObj.length];
        this.lightDrawn = new boolean[allObj.length];
        this.realObjs = realObj;
        this.gl = gl;
        this.objs = allObj;
        this.numNodes = 0;
        makeFirstCubeInfo();
        this.bHalf = this.bSize / 2;
        this.root = new EOcNode(this, this.objs, this.middle, this.radius, this.bSize, this.drawn, 0);
    }

    private void makeFirstCubeInfo() {
        this.middle = new Vector();

        this.radius = 0f;

        //make the middle of all objIns
        for (ObjIns obj : this.objs) {
            //middle
            this.middle.x += obj.origin.x / this.objs.length;
            this.middle.y += obj.origin.y / this.objs.length;
            this.middle.z += obj.origin.z / this.objs.length;
        }

        //make a bounding sphere around all ObjIns
        for (ObjIns obj : this.objs) {
            float dis = (float) Math.sqrt(Math.pow(obj.origin.x - this.middle.x, 2) +
                    Math.pow(obj.origin.y - this.middle.y, 2) +
                    Math.pow(obj.origin.z - this.middle.z, 2));

            dis += Math.abs(obj.boundSph);

            if (dis > this.radius) {
                this.radius = dis;
            }

            dis = obj.origin.x - this.middle.x;
            if (this.bSize < Math.abs(dis)) {
                this.bSize = Math.abs(dis);
            }
            dis = obj.origin.y - this.middle.y;
            if (this.bSize < Math.abs(dis)) {
                this.bSize = Math.abs(dis);
            }
            dis = obj.origin.z - this.middle.z;
            if (this.bSize < Math.abs(dis)) {
                this.bSize = Math.abs(dis);
            }
        }
    }

    public void drawOctree(GL gl) {
        gl.glEnable(GL.GL_TEXTURE);
        //Reset Counter
        for (Obj rObj : this.realObjs) {
            rObj.facesRendered = 0;
        }
        this.drawn = new boolean[this.objs.length];

        float mat_specular[] = {1.0f, 1.0f, 1.0f, 1.0f};
        float mat_shininess[] = {50.0f};

        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, mat_specular, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, mat_shininess, 0);
        //actually draw
        if(this.engine.frame.octree)drawBox(gl);
        extractFrustum();
        this.facRender = 0;
        this.root.draw(gl);

        //make Info
        for (Obj rObj : this.realObjs) {
            this.facRender += rObj.facesRendered;
        }
        gl.glDisable(GL.GL_TEXTURE);
    }

    /*    public void drawLightVolume(GL gl) {
    this.lightDrawn = new boolean[this.objs.length];

    //make the list of shadow volumes
    //this one gone take a lot of juice
    //goes all the way done the octree
    gl.glNewList(this.shadowList, GL.GL_COMPILE);
    this.root.drawLight(gl);
    gl.glEndList();


    //setup Opengl
    gl.glColorMask(false, false, false, false);
    gl.glDepthMask(false);

    gl.glEnable(GL.GL_STENCIL_TEST);
    gl.glStencilFunc(GL.GL_ALWAYS, 0, 0xFFFFFFF);

    //activate stencil
    gl.glStencilOp(GL.GL_KEEP, GL.GL_INCR_WRAP, GL.GL_KEEP);
    gl.glEnable(GL.GL_CULL_FACE);
    gl.glFrontFace(GL.GL_CW);

    //draw frontface
    gl.glCallList(this.shadowList);

    //draw backfaces
    gl.glStencilOp(GL.GL_KEEP, GL.GL_DECR_WRAP, GL.GL_KEEP);
    gl.glFrontFace(GL.GL_CCW);

    gl.glCallList(this.shadowList);


    //skybox
    gl.glStencilFunc(GL.GL_NOTEQUAL, 0, 0xFFFFFFFF);

    gl.glStencilOp(GL.GL_KEEP, GL.GL_DECR, GL.GL_KEEP);
    gl.glDepthFunc(GL.GL_NEVER);
    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glDepthMask(true);

    //draw skybox over the actual shadow
    this.engine.skybox.draw();

    gl.glColorMask(true, true, true, true);
    gl.glStencilFunc(GL.GL_NOTEQUAL, 0, 0xFFFFFFFF);
    gl.glStencilOp(GL.GL_KEEP, GL.GL_KEEP, GL.GL_KEEP);
    gl.glDepthFunc(GL.GL_ALWAYS);
    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glDepthMask(true);


    gl.glColor4f(0.0f, 0.0f, 0.0f, 0.8f);
    gl.glEnable(GL.GL_CULL_FACE);
    gl.glFrontFace(GL.GL_CCW);
    gl.glDisable(GL.GL_LIGHTING);
    gl.glEnable(GL.GL_BLEND);
    gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
    gl.glPushMatrix();
    gl.glLoadIdentity();
    gl.glBegin(GL.GL_TRIANGLE_STRIP);
    gl.glVertex3f(-0.1f, 0.1f, -0.1f);
    gl.glVertex3f(-0.1f, -0.1f, -0.1f);
    gl.glVertex3f(0.1f, 0.1f, -0.1f);
    gl.glVertex3f(0.1f, -0.1f, -0.1f);
    gl.glEnd();
    gl.glPopMatrix();
    gl.glDisable(GL.GL_BLEND);

    gl.glDepthFunc(GL.GL_LESS);

    gl.glDisable(GL.GL_STENCIL_TEST);

    this.engine.skybox.draw();
    gl.glFlush();
    }*/
    public void drawBox(GL gl) {
        gl.glPushMatrix();
        gl.glTranslatef(this.middle.x, this.middle.y, this.middle.z);
        gl.glColor3f(0.4f, 0.2f, 0.7f);

        //Back
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex3f(-this.bHalf, -this.bHalf, -this.bHalf);
        gl.glVertex3f(this.bHalf, -this.bHalf, -this.bHalf);
        gl.glVertex3f(this.bHalf, this.bHalf, -this.bHalf);
        gl.glVertex3f(-this.bHalf, this.bHalf, -this.bHalf);
        gl.glEnd();

        //Front
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex3f(-this.bHalf, -this.bHalf, this.bHalf);
        gl.glVertex3f(this.bHalf, -this.bHalf, this.bHalf);
        gl.glVertex3f(this.bHalf, this.bHalf, this.bHalf);
        gl.glVertex3f(-this.bHalf, this.bHalf, this.bHalf);
        gl.glEnd();

        //Left
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex3f(-this.bHalf, -this.bHalf, -this.bHalf);
        gl.glVertex3f(-this.bHalf, -this.bHalf, this.bHalf);
        gl.glVertex3f(-this.bHalf, this.bHalf, this.bHalf);
        gl.glVertex3f(-this.bHalf, this.bHalf, -this.bHalf);
        gl.glEnd();

        //Right
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex3f(this.bHalf, -this.bHalf, -this.bHalf);
        gl.glVertex3f(this.bHalf, -this.bHalf, this.bHalf);
        gl.glVertex3f(this.bHalf, this.bHalf, this.bHalf);
        gl.glVertex3f(this.bHalf, this.bHalf, -this.bHalf);
        gl.glEnd();

        gl.glPopMatrix();
        gl.glColor3f(1f,1f,1f);
    }

    private void extractFrustum() {
        float[] proj = new float[16];
        float[] modl = new float[16];
        float[] clip = new float[16];
        float t;

        /* Get the current PROJECTION matrix from OpenGL */
        gl.glGetFloatv(GL.GL_PROJECTION_MATRIX, proj, 0);

        /* Get the current MODELVIEW matrix from OpenGL */
        gl.glGetFloatv(GL.GL_MODELVIEW_MATRIX, modl, 0);

        /* Combine the two matrices (multiply projection by modelview) */
        clip[ 0] = modl[ 0] * proj[ 0] + modl[ 1] * proj[ 4] + modl[ 2] * proj[ 8] + modl[ 3] * proj[12];
        clip[ 1] = modl[ 0] * proj[ 1] + modl[ 1] * proj[ 5] + modl[ 2] * proj[ 9] + modl[ 3] * proj[13];
        clip[ 2] = modl[ 0] * proj[ 2] + modl[ 1] * proj[ 6] + modl[ 2] * proj[10] + modl[ 3] * proj[14];
        clip[ 3] = modl[ 0] * proj[ 3] + modl[ 1] * proj[ 7] + modl[ 2] * proj[11] + modl[ 3] * proj[15];

        clip[ 4] = modl[ 4] * proj[ 0] + modl[ 5] * proj[ 4] + modl[ 6] * proj[ 8] + modl[ 7] * proj[12];
        clip[ 5] = modl[ 4] * proj[ 1] + modl[ 5] * proj[ 5] + modl[ 6] * proj[ 9] + modl[ 7] * proj[13];
        clip[ 6] = modl[ 4] * proj[ 2] + modl[ 5] * proj[ 6] + modl[ 6] * proj[10] + modl[ 7] * proj[14];
        clip[ 7] = modl[ 4] * proj[ 3] + modl[ 5] * proj[ 7] + modl[ 6] * proj[11] + modl[ 7] * proj[15];

        clip[ 8] = modl[ 8] * proj[ 0] + modl[ 9] * proj[ 4] + modl[10] * proj[ 8] + modl[11] * proj[12];
        clip[ 9] = modl[ 8] * proj[ 1] + modl[ 9] * proj[ 5] + modl[10] * proj[ 9] + modl[11] * proj[13];
        clip[10] = modl[ 8] * proj[ 2] + modl[ 9] * proj[ 6] + modl[10] * proj[10] + modl[11] * proj[14];
        clip[11] = modl[ 8] * proj[ 3] + modl[ 9] * proj[ 7] + modl[10] * proj[11] + modl[11] * proj[15];

        clip[12] = modl[12] * proj[ 0] + modl[13] * proj[ 4] + modl[14] * proj[ 8] + modl[15] * proj[12];
        clip[13] = modl[12] * proj[ 1] + modl[13] * proj[ 5] + modl[14] * proj[ 9] + modl[15] * proj[13];
        clip[14] = modl[12] * proj[ 2] + modl[13] * proj[ 6] + modl[14] * proj[10] + modl[15] * proj[14];
        clip[15] = modl[12] * proj[ 3] + modl[13] * proj[ 7] + modl[14] * proj[11] + modl[15] * proj[15];
        /* Extract the numbers for the RIGHT plane */
        this.frustum[0][0] = clip[ 3] - clip[ 0];
        this.frustum[0][1] = clip[ 7] - clip[ 4];
        this.frustum[0][2] = clip[11] - clip[ 8];
        this.frustum[0][3] = clip[15] - clip[12];

        /* Normalize the result */
        t = (float) Math.sqrt(this.frustum[0][0] * frustum[0][0] + frustum[0][1] * frustum[0][1] + frustum[0][2] * frustum[0][2]);
        this.frustum[0][0] /= t;
        this.frustum[0][1] /= t;
        this.frustum[0][2] /= t;
        this.frustum[0][3] /= t;

        /* Extract the numbers for the LEFT plane */
        this.frustum[1][0] = clip[ 3] + clip[ 0];
        this.frustum[1][1] = clip[ 7] + clip[ 4];
        this.frustum[1][2] = clip[11] + clip[ 8];
        this.frustum[1][3] = clip[15] + clip[12];

        /* Normalize the result */
        t = (float) Math.sqrt(this.frustum[1][0] * frustum[1][0] + frustum[1][1] * frustum[1][1] + frustum[1][2] * frustum[1][2]);
        this.frustum[1][0] /= t;
        this.frustum[1][1] /= t;
        this.frustum[1][2] /= t;
        this.frustum[1][3] /= t;

        /* Extract the BOTTOM plane */
        this.frustum[2][0] = clip[ 3] + clip[ 1];
        this.frustum[2][1] = clip[ 7] + clip[ 5];
        this.frustum[2][2] = clip[11] + clip[ 9];
        this.frustum[2][3] = clip[15] + clip[13];

        /* Normalize the result */
        t = (float) Math.sqrt(this.frustum[2][0] * frustum[2][0] + frustum[2][1] * frustum[2][1] + frustum[2][2] * frustum[2][2]);
        this.frustum[2][0] /= t;
        this.frustum[2][1] /= t;
        this.frustum[2][2] /= t;
        this.frustum[2][3] /= t;

        /* Extract the TOP plane */
        this.frustum[3][0] = clip[ 3] - clip[ 1];
        this.frustum[3][1] = clip[ 7] - clip[ 5];
        this.frustum[3][2] = clip[11] - clip[ 9];
        this.frustum[3][3] = clip[15] - clip[13];

        /* Normalize the result */
        t = (float) Math.sqrt(this.frustum[3][0] * frustum[3][0] + frustum[3][1] * frustum[3][1] + frustum[3][2] * frustum[3][2]);
        this.frustum[3][0] /= t;
        this.frustum[3][1] /= t;
        this.frustum[3][2] /= t;
        this.frustum[3][3] /= t;

        /* Extract the FAR plane */
        this.frustum[4][0] = clip[ 3] - clip[ 2];
        this.frustum[4][1] = clip[ 7] - clip[ 6];
        this.frustum[4][2] = clip[11] - clip[10];
        this.frustum[4][3] = clip[15] - clip[14];

        /* Normalize the result */
        t = (float) Math.sqrt(this.frustum[4][0] * frustum[4][0] + frustum[4][1] * frustum[4][1] + frustum[4][2] * frustum[4][2]);
        this.frustum[4][0] /= t;
        this.frustum[4][1] /= t;
        this.frustum[4][2] /= t;
        this.frustum[4][3] /= t;

        /* Extract the NEAR plane */
        this.frustum[5][0] = clip[ 3] + clip[ 2];
        this.frustum[5][1] = clip[ 7] + clip[ 6];
        this.frustum[5][2] = clip[11] + clip[10];
        this.frustum[5][3] = clip[15] + clip[14];

        /* Normalize the result */
        t = (float) Math.sqrt(this.frustum[5][0] * frustum[5][0] + frustum[5][1] * frustum[5][1] + frustum[5][2] * frustum[5][2]);
        this.frustum[5][0] /= t;
        this.frustum[5][1] /= t;
        this.frustum[5][2] /= t;
        this.frustum[5][3] /= t;

    /*
    //Boudingsphere around the frustum and all lights
    //       this.engine.cam.updateFrustMiddle();

    //Check if a light is outside of the boundingsphere
    //and if so extend the radius of the sphere
    if (this.engine.lights == null) {
    return;
    }
    for (PointLight light : this.engine.lights.lights) {
    this.frustLight = this.cam.frustRadius;
    float dis = VectorUtil.distance(this.cam.frustMiddle, light.origin);
    if (dis > this.frustLight) {
    this.frustLight = dis;
    }
    }*/

    }
}
