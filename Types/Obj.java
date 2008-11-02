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
package Types;

import javax.media.opengl.GL;

import Util.*;
import java.util.Random;

public class Obj {

    public Vector[] vec = null;
    public Normal[] nor = null;
    public TexCoor[] tex = null;
    public Face[] fac = null;
    public String name;
    private float x = 0.0f;                 //Object origin

    private float y = 0.0f;
    private float z = -8.0f;
    private float xR = 0.0f;                //Object rotation

    private float yR = 0.0f;
    private float zR = 0.0f;
    private float bR = 0.0f;                //bounding Sphere radius

    private long time = System.nanoTime();

    public Obj(String file) {
        ObjParse parse = new ObjParse(file);
        this.vec = parse.getVec();
        this.nor = parse.getNor();
        this.tex = parse.getTex();
        this.fac = parse.getFace();
        makeBoundingSphere();
    }

    private void makeBoundingSphere() {
        for (int i = 0; i < this.vec.length; i++) {
            float dis = (float) Math.sqrt(Math.pow(this.vec[i].x, 2) + Math.pow(this.vec[i].y, 2) + Math.pow(this.vec[i].z, 2));
            if (dis > this.bR) {
                this.bR = dis;
            }
        }
    }

    public void conMove(float x, float y, float z) {
        this.x += x * UHPT.getETime() / 1000000000;
        this.y += y * UHPT.getETime() / 1000000000;
        this.z += z * UHPT.getETime() / 1000000000;
    }

    public void conRotate(float rX, float rY, float rZ) {
        this.xR += rX * UHPT.getETime() / 1000000000;
        this.yR += rY * UHPT.getETime() / 1000000000;
        this.zR += rZ * UHPT.getETime() / 1000000000;
    }

    public void setPos(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setRot(float xR, float yR, float zR) {
        this.xR = xR;
        this.yR = yR;
        this.zR = zR;
    }

    public void render(GL gl) {
        Random r = new Random();
        gl.glPushMatrix();
        gl.glColor3f(0.65f, 0.32f, 0.89f);
        gl.glTranslatef(x, y, z);
        gl.glRotatef(xR, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(yR, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(zR, 0.0f, 0.0f, 1.0f);

        gl.glBegin(GL.GL_TRIANGLES);
        for (int i = 0; i < fac.length; i++) {
            gl.glColor3f(r.nextFloat(), r.nextFloat(), r.nextFloat());
            gl.glNormal3f(nor[fac[i].v1].x, nor[fac[i].v1].y, nor[fac[i].v1].z);
            gl.glVertex3f(vec[fac[i].v1].x, vec[fac[i].v1].y, vec[fac[i].v1].z);
            gl.glNormal3f(nor[fac[i].v2].x, nor[fac[i].v2].y, nor[fac[i].v2].z);
            gl.glVertex3f(vec[fac[i].v2].x, vec[fac[i].v2].y, vec[fac[i].v2].z);
            gl.glNormal3f(nor[fac[i].v3].x, nor[fac[i].v3].y, nor[fac[i].v3].z);
            gl.glVertex3f(vec[fac[i].v3].x, vec[fac[i].v3].y, vec[fac[i].v3].z);
        }
        gl.glEnd();
        gl.glPopMatrix();
    }
}
		
