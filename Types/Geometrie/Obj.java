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
package Types.Geometrie;

import Engine.Engine;
import Util.Logic.Camera;
import Util.Logic.UHPT;
import Util.Prelude.ObjParse;
import com.sun.opengl.util.texture.Texture;
import java.io.IOException;
import javax.media.opengl.GL;

import Util.*;
import com.sun.opengl.util.texture.TextureIO;
import java.io.File;
import java.util.LinkedList;
import java.util.Random;

public class Obj {

    public int number;
    public LinkedList<Vector[]> vec = null;
    public LinkedList<Normal[]> nor = null;
    public LinkedList<TexCoor[]> tex = null;
    public LinkedList<Face[]> fac = null;
    public LinkedList<ObjIns> objIns = null;
    public String name;
    public Vector origin;                 //Object origin
    private float xR = 0.0f;                //Object rotation
    private float yR = 0.0f;
    private float zR = 0.0f;
    public float bR = 0.0f;                //bounding Sphere radius
    private int display_list_handle;
    private Camera cam;
    private GL gl;
    public int[] facNum;
    public int facesRendered;
    private Engine engine;
    private Texture regularTexture;
    public String[] textures;

    public Obj(Camera cam, String[] file, int number, GL gl, Engine engine) throws IOException {
        this.textures = new String[6];
        this.engine = engine;
        this.facNum = new int[6];
        this.vec = new LinkedList<Vector[]>();
        this.nor = new LinkedList<Normal[]>();
        this.tex = new LinkedList<TexCoor[]>();
        this.fac = new LinkedList<Face[]>();
        this.objIns = new LinkedList<ObjIns>();
        this.gl = gl;
        this.cam = cam;
        this.origin = new Vector(0.0f, 0.0f, 0.0f);
        this.number = number;
        for (int i = 0; i < file.length; i++) {
            ObjParse parse = new ObjParse(file[i]);
            this.vec.add(parse.getVec());
            this.nor.add(parse.getNor());
            this.tex.add(parse.getTex());
            this.fac.add(parse.getFace());
        }
        calcObjCenter();
        makeBoundingSphere();

        //Put Object into glLists
        this.display_list_handle = gl.glGenLists(file.length);
        Face[] tmpFac;
        Normal[] tmpNor;
        TexCoor[] tmpTex;
        Vector[] tmpVec;
        int a;
        this.textures = this.engine.textures;

        for (int j = 0; j < this.fac.size(); j++) {
            Texture tmp = this.engine.texMaster.textures.get(this.textures[j]);
            tmp.bind();
            tmpFac = (Face[]) this.fac.get(j);
            tmpNor = (Normal[]) this.nor.get(j);
            tmpTex = (TexCoor[]) this.tex.get(j);
            tmpVec = (Vector[]) this.vec.get(j);

            this.gl.glNewList(display_list_handle + j, GL.GL_COMPILE);

            this.gl.glBegin(GL.GL_TRIANGLES);
            for (int i = 0; i < this.fac.get(j).length - 1; i++) {

                gl.glColor3f(1.0f, 1.0f, 1.0f);
                //Vector one                
                if (this.nor.get(j) != null) {
                    a = tmpFac[i].vn1;
                    if (a <= this.nor.get(j).length) {
                        //System.out.println("Normal " + tmpNor[tmpFac[i].vn1].x + " " + tmpNor[tmpFac[i].vn1].y + " " + tmpNor[tmpFac[i].vn1].z);
                        this.gl.glNormal3f(tmpNor[tmpFac[i].vn1].x, tmpNor[tmpFac[i].vn1].y, tmpNor[tmpFac[i].vn1].z);
                    }
                }
                if (this.tex.get(j) != null) {
                    a = tmpFac[i].vt1;
                    if (a <= this.tex.get(j).length) {
                        //System.out.println("Texture " + tmpTex[tmpFac[i].vt1].x + " " + tmpTex[tmpFac[i].vt1].y + " " + tmpTex[tmpFac[i].vt1].z);
                        this.gl.glTexCoord2f(tmpTex[tmpFac[i].vt1].x, tmpTex[tmpFac[i].vt1].y);
                    }
                }
                a = tmpFac[i].v1;
                if (a <= this.vec.get(j).length) {
                    this.gl.glVertex3f(tmpVec[tmpFac[i].v1].x, tmpVec[tmpFac[i].v1].y, tmpVec[tmpFac[i].v1].z);
                }
                //Vector two
                if (this.nor.get(j) != null) {
                    a = tmpFac[i].vn2;
                    if (a <= nor.get(j).length) {
                        //System.out.println("Normal " + tmpNor[tmpFac[i].vn2].x + " " + tmpNor[tmpFac[i].vn2].y + " " + tmpNor[tmpFac[i].vn2].z);
                        this.gl.glNormal3f(tmpNor[tmpFac[i].vn2].x, tmpNor[tmpFac[i].vn2].y, tmpNor[tmpFac[i].vn2].z);
                    }
                }
                if (this.tex.get(j) != null) {
                    a = tmpFac[i].vt2;
                    if (a <= this.tex.get(j).length) {
                       // System.out.println("Texture " + tmpTex[tmpFac[i].vt2].x + " " + tmpTex[tmpFac[i].vt2].y + " " + tmpTex[tmpFac[i].vt2].z);
                        this.gl.glTexCoord2f(tmpTex[tmpFac[i].vt2].x, tmpTex[tmpFac[i].vt2].y);
                    }
                }
                a = tmpFac[i].v2;
                if (a <= this.vec.get(j).length) {
                    this.gl.glVertex3f(tmpVec[tmpFac[i].v2].x, tmpVec[tmpFac[i].v2].y, tmpVec[tmpFac[i].v2].z);
                }
                //Vector three
                if (this.nor.get(j) != null) {
                    a = tmpFac[i].vn3;
                    if (a <= nor.get(j).length) {
                       // System.out.println("Normal " + tmpNor[tmpFac[i].vn3].x + " " + tmpNor[tmpFac[i].vn3].y + " " + tmpNor[tmpFac[i].vn3].z);
                        this.gl.glNormal3f(tmpNor[tmpFac[i].vn3].x, tmpNor[tmpFac[i].vn3].y, tmpNor[tmpFac[i].vn3].z);
                    }
                }
                if (this.tex.get(j) != null) {
                    a = tmpFac[i].vt1;
                    if (a <= this.tex.get(j).length) {
                       // System.out.println("Texture " + tmpTex[tmpFac[i].vt3].x + " " + tmpTex[tmpFac[i].vt3].y + " " + tmpTex[tmpFac[i].vt3].z);
                        this.gl.glTexCoord2f(tmpTex[tmpFac[i].vt1].x, tmpTex[tmpFac[i].vt1].y);
                    }
                }
                a = tmpFac[i].v3;
                if (a <= this.vec.get(j).length) {
                    this.gl.glVertex3f(tmpVec[tmpFac[i].v3].x, tmpVec[tmpFac[i].v3].y, tmpVec[tmpFac[i].v3].z);
                }
                this.facNum[j] = tmpFac.length;
            }
            this.gl.glEnd();
            this.gl.glEndList();
        }
    }

    private void calcObjCenter() {
        for (Vector vecIdx : this.vec.get(0)) {
            this.origin.x += (vecIdx.x / this.vec.get(0).length);
            this.origin.y += (vecIdx.y / this.vec.get(0).length);
            this.origin.z += (vecIdx.z / this.vec.get(0).length);
        }
        this.origin.x = this.origin.x * -1;
        this.origin.y = this.origin.y * -1;
        this.origin.z = this.origin.z * -1;
    }

    private void makeBoundingSphere() {
        for (int i = 0; i < this.vec.get(1).length; i++) {
            Vector[] tmpVec = (Vector[]) this.vec.get(1);
            float dis = (float) Math.sqrt(Math.pow(tmpVec[i].x, 2) + Math.pow(tmpVec[i].y, 2) + Math.pow(tmpVec[i].z, 2));
            if (dis > this.bR) {
                this.bR = dis;
            }
        }
    }

    public void conMove(float x, float y, float z) {
        this.origin.x += x * UHPT.getETime() / 1000000000;
        this.origin.y += y * UHPT.getETime() / 1000000000;
        this.origin.z += z * UHPT.getETime() / 1000000000;
    }

    public void conRotate(float rX, float rY, float rZ) {
        this.xR += rX * UHPT.getETime() / 1000000000;
        this.yR += rY * UHPT.getETime() / 1000000000;
        this.zR += rZ * UHPT.getETime() / 1000000000;
    }

    public void setPos(float x, float y, float z) {
        this.origin.x = x;
        this.origin.y = y;
        this.origin.z = z;
    }

    public void setRot(float xR, float yR, float zR) {
        this.xR = xR;
        this.yR = yR;
        this.zR = zR;
    }

    public void render(int number, float dis) {
        //float dis = (float) Math.abs(Math.sqrt(Math.pow(this.cam.loc.x - this.origin.x, 2) + Math.pow(this.cam.loc.y - this.origin.y, 2) + Math.pow(this.cam.loc.z - this.origin.z, 2)));
        gl.glPushMatrix();

        //get ObjIns and adjust the matrix
        ObjIns tmp = this.objIns.get(number);
        gl.glTranslatef(tmp.origin.x, tmp.origin.y, tmp.origin.z);
        gl.glRotatef(tmp.rotation.x, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(tmp.rotation.y, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(tmp.rotation.z, 0.0f, 0.0f, 1.0f);

        if (dis < 10.0f) {
            gl.glCallList(display_list_handle);
            this.facesRendered += this.facNum[0];
        } else if (dis < 20.0f) {
            gl.glCallList(display_list_handle + 1);
            this.facesRendered += this.facNum[1];
        } else if (dis < 40.0f) {
            gl.glCallList(display_list_handle + 2);
            this.facesRendered += this.facNum[2];
        } else if (dis < 60.0f) {
            gl.glCallList(display_list_handle + 3);
            this.facesRendered += this.facNum[3];
        } else if (dis < 120.0f) {
            gl.glCallList(display_list_handle + 4);
            this.facesRendered += this.facNum[4];
        } else {
            gl.glCallList(display_list_handle + 5);
            this.facesRendered += this.facNum[5];
        }
        gl.glPopMatrix();
    }
}
		
