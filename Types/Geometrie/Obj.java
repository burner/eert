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
import Util.Prelude.JObjParse;
import com.sun.opengl.util.texture.TextureCoords;
import com.sun.opengl.util.texture.TextureIO;
import java.awt.Image;
import java.io.File;
import java.util.LinkedList;
import java.util.Random;
import javax.media.opengl.glu.GLU;

public class Obj {

    public int number;
    public LinkedList<Vector[]> vec = null;
    public LinkedList<Vector[]> nor = null;
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
    private EImage image[];
    private int textureId;

    public Obj(Camera cam, String[] file, int number, GL gl, Engine engine) throws IOException {
        this.image = new EImage[6];
        this.textures = new String[6];
        this.engine = engine;
        this.facNum = new int[6];
        this.vec = new LinkedList<Vector[]>();
        this.nor = new LinkedList<Vector[]>();
        this.tex = new LinkedList<TexCoor[]>();
        this.fac = new LinkedList<Face[]>();
        this.objIns = new LinkedList<ObjIns>();
        this.gl = gl;
        this.cam = cam;
        this.origin = new Vector(0.0f, 0.0f, 0.0f);
        this.number = number;
        GLU glu = new GLU();
        for (int i = 0; i < file.length; i++) {
            JObjParse parse = new JObjParse(file[i]);
            this.vec.add(parse.getVector());
            this.nor.add(parse.getNormal());
            this.tex.add(parse.getTex());
            this.fac.add(parse.getFace());
        }
        //calcObjCenter();
        //makeBoundingSphere();

        //Put Object into glLists
        this.display_list_handle = gl.glGenLists(file.length);
        Face[] tmpFac;
        Normal[] tmpNor;
        TexCoor[] tmpTex;
        Vector[] tmpVec;
        int a;
        this.textures = this.engine.textures;


        this.image[0] = new EImage(this.textures[5]);
        this.image[1] = new EImage(this.textures[4]);
        this.image[2] = new EImage(this.textures[3]);
        this.image[3] = new EImage(this.textures[2]);
        this.image[4] = new EImage(this.textures[1]);
        this.image[5] = new EImage(this.textures[0]);

        final int[] tmp = new int[6];
        gl.glGenTextures(6, tmp, 0);


        for (int j = 0; j < this.fac.size(); j++) {
            System.out.println(this.textures[j] + " " + j + " " + this.fac.get(j).length);
            /*gl.glEnable(GL.GL_TEXTURE_2D);
            Texture tmp = this.engine.texMaster.textures.get(this.textures[5 - j]);
            TextureCoords coords = tmp.getImageTexCoords();
            System.out.println("top - left = " + coords.top() + " " + coords.left());
            System.out.println("bootom - right = " + coords.bottom() + " " + coords.right());
            System.out.println(tmp.getMustFlipVertically());
            
            //          tmp.bind();
             */
            this.textureId = tmp[j];
            gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureId);
            //gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, image.getWidth(), image.getHeight(), 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, image.getBuffer());
            glu.gluBuild2DMipmaps(GL.GL_TEXTURE_2D, GL.GL_RGBA, this.image[j].getWidth(), image[j].getHeight(), GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, image[j].getBuffer());
            //glu.gluBuild2DMipmapLevels(GL.GL_TEXTURE_2D, GL.GL_RGBA, image.getWidth(), image.getHeight(), GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, 0, 0, 10, image.getBuffer());
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_BASE_LEVEL, 0);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAX_LEVEL, 10);

            this.gl.glNewList(display_list_handle + j, GL.GL_COMPILE);
            this.gl.glEnable(GL.GL_TEXTURE_2D);
            this.gl.glBegin(GL.GL_TRIANGLES);
            System.out.println(this.textureId);
            gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureId);
            gl.glColor3f(1.0f, 1.0f, 1.0f);
            for (int i = 0; i < this.fac.get(j).length - 1; i++) {
                Face tmpFace = this.fac.get(j)[i];

                if (tmpFace.vn1 != null) {
                    gl.glNormal3f(tmpFace.vn1.x, tmpFace.vn1.y, tmpFace.vn1.z);
                } else {
                    System.out.println("vn1");
                }
                if (tmpFace.vt1 != null) {
                    gl.glTexCoord2f(tmpFace.vt1.s, tmpFace.vt1.t);
                } else {
                    System.out.println("vt1");
                }
                gl.glVertex3f(tmpFace.v1.x, tmpFace.v1.y, tmpFace.v1.z);


                if (tmpFace.vn2 != null) {
                    gl.glNormal3f(tmpFace.vn2.x, tmpFace.vn2.y, tmpFace.vn2.z);
                } else {
                    System.out.println("vn2");
                }
                if (tmpFace.vt2 != null) {
                    gl.glTexCoord2f(tmpFace.vt2.s, tmpFace.vt2.t);
                } else {
                    System.out.println("vt2");
                }
                gl.glVertex3f(tmpFace.v2.x, tmpFace.v2.y, tmpFace.v2.z);


                if (tmpFace.vt3 != null) {
                    gl.glTexCoord2f(tmpFace.vt3.s, tmpFace.vt3.t);
                } else {
                    System.out.println("vn3");
                }
                if (tmpFace.vn3 != null) {
                    gl.glNormal3f(tmpFace.vn3.x, tmpFace.vn3.y, tmpFace.vn3.z);
                } else {
                    System.out.println("vt3");
                }
                gl.glVertex3f(tmpFace.v3.x, tmpFace.v3.y, tmpFace.v3.z);
            }
            this.gl.glEnd();
            this.gl.glDisable(GL.GL_TEXTURE_2D);
            this.gl.glEndList();
            this.textureId = tmp[0];
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
        //dis = (float) Math.abs(Math.sqrt(Math.pow(this.cam.loc.x - this.origin.x, 2) + Math.pow(this.cam.loc.y - this.origin.y, 2) + Math.pow(this.cam.loc.z - this.origin.z, 2)));
        gl.glPushMatrix();

        //get ObjIns and adjust the matrix
        ObjIns tmp = this.objIns.get(number);
        gl.glTranslatef(tmp.origin.x, tmp.origin.y, tmp.origin.z);
        gl.glRotatef(tmp.rotation.x, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(tmp.rotation.y, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(tmp.rotation.z, 0.0f, 0.0f, 1.0f);

        if (dis < 10.0f) {
            gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureId + 0);
            gl.glCallList(display_list_handle);
            this.facesRendered += this.facNum[0];
        } else if (dis < 20.0f) {
            gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureId + 1);
            gl.glCallList(display_list_handle + 1);
            this.facesRendered += this.facNum[1];
        } else if (dis < 40.0f) {
            gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureId + 2);
            gl.glCallList(display_list_handle + 2);
            this.facesRendered += this.facNum[2];
        } else if (dis < 60.0f) {
            gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureId + 3);
            gl.glCallList(display_list_handle + 3);
            this.facesRendered += this.facNum[3];
        } else if (dis < 120.0f) {
            gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureId + 4);
            gl.glCallList(display_list_handle + 4);
            this.facesRendered += this.facNum[4];
        } else {
            gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureId + 5);
            gl.glCallList(display_list_handle + 5);
            this.facesRendered += this.facNum[5];
        }
        gl.glPopMatrix();
    }
}
		
