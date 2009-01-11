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
import Types.Illumination.PointLight;
import Util.Logic.Camera;
import Util.Logic.UHPT;
import java.io.IOException;
import javax.media.opengl.GL;

import Util.*;
import Util.Geometrie.VectorUtil;
import Util.Prelude.JObjParse;
import java.util.LinkedList;
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
    private Camera cam;
    private GL gl;
    public int[] facNum;
    public int facesRendered;
    public Engine engine;
    public String[] textures;
    private ETexture image[];
    private int[] listHandles;
    private int[] textureHandles;
    ETexture texImage0;
    ETexture texImage1;
    ETexture texImage2;
    ETexture texImage3;
    ETexture texImage4;
    ETexture texImage5;
    //Shadow stuff below
    public LinkedList<Face> cap;
    public LinkedList<Edge> edgesToExtrude;

    public Obj(Camera cam, String[] file, int number, GL gl, Engine engine) throws IOException {
        this.image = new ETexture[6];
        this.textures = new String[6];
        this.engine = engine;
        this.facNum = new int[6];
        this.vec = new LinkedList<Vector[]>();
        this.nor = new LinkedList<Vector[]>();
        this.tex = new LinkedList<TexCoor[]>();
        this.fac = new LinkedList<Face[]>();
        this.cap = new LinkedList<Face>();
        this.objIns = new LinkedList<ObjIns>();
        this.gl = gl;
        this.cam = cam;
        this.origin = new Vector(0.0f, 0.0f, 0.0f);
        this.number = number;

        for (int i = 0; i < file.length; i++) {
            JObjParse parse = new JObjParse(file[i]);
            this.vec.add(parse.getVector());
            this.nor.add(parse.getNormal());
            this.tex.add(parse.getTex());
            this.fac.add(parse.getFace());
        }
        makeBoundingSphere();
        makeFaceNormals();

        this.facNum[0] = this.fac.get(0).length;
        this.facNum[1] = this.fac.get(1).length;
        this.facNum[2] = this.fac.get(2).length;
        this.facNum[3] = this.fac.get(3).length;
        this.facNum[4] = this.fac.get(4).length;
        this.facNum[5] = this.fac.get(5).length;

        this.listHandles = new int[6];
        this.listHandles[0] = gl.glGenLists(1);
        this.listHandles[1] = gl.glGenLists(1);
        this.listHandles[2] = gl.glGenLists(1);
        this.listHandles[3] = gl.glGenLists(1);
        this.listHandles[4] = gl.glGenLists(1);
        this.listHandles[5] = gl.glGenLists(1);

        this.textures = this.engine.textures;

        this.textureHandles = new int[6];
        gl.glGenTextures(6, this.textureHandles, 0);

        this.texImage0 = new ETexture(this.textures[0]);

        gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureHandles[0]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
                GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
                GL.GL_NEAREST);
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, this.texImage0.getWidth(), this.texImage0.getHeight(),
                0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, this.texImage0.getBuffer());


        this.texImage1 = new ETexture(this.textures[1]);

        gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureHandles[1]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
                GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
                GL.GL_NEAREST);
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, this.texImage1.getWidth(), this.texImage1.getHeight(),
                0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, this.texImage1.getBuffer());

        this.texImage2 = new ETexture(this.textures[2]);

        gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureHandles[2]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
                GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
                GL.GL_NEAREST);
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, this.texImage2.getWidth(), this.texImage2.getHeight(),
                0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, this.texImage2.getBuffer());


        this.texImage3 = new ETexture(this.textures[3]);

        gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureHandles[3]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
                GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
                GL.GL_NEAREST);
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, this.texImage3.getWidth(), this.texImage3.getHeight(),
                0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, this.texImage3.getBuffer());


        this.texImage4 = new ETexture(this.textures[4]);

        gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureHandles[4]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
                GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
                GL.GL_NEAREST);
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, this.texImage4.getWidth(), this.texImage4.getHeight(),
                0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, this.texImage4.getBuffer());


        this.texImage5 = new ETexture(this.textures[5]);

        gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureHandles[5]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
                GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
                GL.GL_NEAREST);
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, this.texImage5.getWidth(), this.texImage5.getHeight(),
                0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, this.texImage5.getBuffer());

        gl.glEnable(GL.GL_TEXTURE_2D);

        //Build the six display lists
        for (int j = 0; j < this.fac.size(); j++) {
            gl.glNewList(this.listHandles[j], GL.GL_COMPILE);
            this.gl.glBegin(GL.GL_TRIANGLES);
            //gl.glColor3f(1.0f, 1.0f, 1.0f);
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
            this.gl.glEndList();

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

        this.engine.eInfo.drawObj++;

        //get ObjIns
        ObjIns tmp = this.objIns.get(number);

        //Translate to position
        gl.glTranslatef(tmp.origin.x, tmp.origin.y, tmp.origin.z);

        //Rotate
        gl.glRotatef(tmp.rotation.x, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(tmp.rotation.y, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(tmp.rotation.z, 0.0f, 0.0f, 1.0f);

        //Call the right list according
        //to the distance from cam location
        if (dis < 10.0f) {
            gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureHandles[0]);
            gl.glCallList(this.listHandles[0]);
            this.facesRendered += this.facNum[0];
        } else if (dis < 20.0f) {
            gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureHandles[1]);
            gl.glCallList(this.listHandles[1]);
            this.facesRendered += this.facNum[1];
        } else if (dis < 40.0f) {
            gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureHandles[2]);
            gl.glCallList(this.listHandles[2]);
            this.facesRendered += this.facNum[2];
        } else if (dis < 60.0f) {
            gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureHandles[3]);
            gl.glCallList(this.listHandles[3]);
            this.facesRendered += this.facNum[3];
        } else if (dis < 120.0f) {
            gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureHandles[4]);
            gl.glCallList(this.listHandles[4]);
            this.facesRendered += this.facNum[4];
        } else {
            gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureHandles[5]);
            gl.glCallList(this.listHandles[5]);
            this.facesRendered += this.facNum[5];
        }
        gl.glPopMatrix();
    }

    public void renderShadow() {
    }

    private void makeFaceNormals() {
        Face face;
        for (int i = 0; i < 6; i++) {
            int facCount = this.fac.get(i).length;
            for (int j = 0; j < facCount; j++) {
                face = this.fac.get(i)[j];
                //Crossprodukt
                face.faceNormal = new Vector(face.v2.y * face.v3.z - face.v2.z * face.v3.y,
                        face.v2.z * face.v3.x - face.v2.x * face.v3.z,
                        face.v1.x * face.v3.y - face.v2.y * face.v2.x);
                //Normalize to later use acos
                //to get angle between face and lightvec
                //angle = acos(v1 dotProduct v2)
                face.faceNormal.normalize();
            }
        }
    }

    public void findFacesFacingLight(int res) {
        //make sphere coord
        //need to make the rotation
        //of the object to appear in the light
        PointLight toLight = new PointLight(this.engine.lights.lights.get(0));

        //radius
        double radius = Math.sqrt(Math.pow(toLight.origin.x, 2) + Math.pow(toLight.origin.y, 2) + Math.pow(toLight.origin.z, 2));

        //make phi
        double phi;
        if(toLight.origin.x >= 0) {
            phi = Math.acos(toLight.origin.x / Math.sqrt((toLight.origin.x * toLight.origin.x) + (toLight.origin.y * toLight.origin.y)));
        } else {
            phi = 2 * Math.PI - Math.acos(toLight.origin.x / Math.sqrt((toLight.origin.x * toLight.origin.x) + (toLight.origin.y * toLight.origin.y)));
        }

        //make theta
        double theta;
        theta = (Math.PI / 2) - Math.atan(toLight.origin.z / Math.sqrt((toLight.origin.x * toLight.origin.x) + (toLight.origin.y * toLight.origin.y)));


        Face[] forTest = this.fac.get(res);
        for (Face toTest : forTest) {
            if (90 > VectorUtil.angle(toTest, vec)) {
                this.cap.add(toTest);
                toTest.lit = true;
            } else {
                toTest.lit = false;
            }
        }
    }

    public void makeSilhouette(int res) {
        /* if a friend is not lit add the Edge to the
         * list to be extruded*/
        for (Face toTest : this.cap) {
            if (!toTest.fr1.lit) {
                this.edgesToExtrude.add(toTest.ed1);
            }
            if (!toTest.fr2.lit) {
                this.edgesToExtrude.add(toTest.ed2);
            }
            if (!toTest.fr3.lit) {
                this.edgesToExtrude.add(toTest.ed3);
            }
        }
    }

    private void makeBoundingSphere() {
        float dis = 0f;
        Vector[] points = this.vec.get(0);
        for (Vector toTest : points) {
            float newDis = (float) Math.sqrt(Math.pow(toTest.x - this.origin.x, 2) +
                    Math.pow(toTest.y - this.origin.y, 2) +
                    Math.pow(toTest.z - this.origin.z, 2));

            if (newDis > dis) {
                dis = newDis;
            }
        }

        this.bR = dis;
    }

    private void drawShadowVolume(int number) {
        
        this.gl.glPushMatrix();
        ObjIns tmp = this.objIns.get(number);

        //Translate to position
        gl.glTranslatef(tmp.origin.x, tmp.origin.y, tmp.origin.z);

        //Rotate
        gl.glRotatef(tmp.rotation.x, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(tmp.rotation.y, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(tmp.rotation.z, 0.0f, 0.0f, 1.0f);

        //Extrude the Silhouette
        for (Edge extrude : this.edgesToExtrude) {
            //make vertex who form
            //quads of shadowcone
            Vector lightEx1 = new Vector(extrude.x1.subR(this.engine.lights.lights.get(0).origin));
            lightEx1.normalize();
            lightEx1.mult(this.engine.lights.lights.get(0).radius);

            Vector lightEx2 = new Vector(extrude.x2.subR(this.engine.lights.lights.get(0).origin));
            lightEx2.normalize();
            lightEx2.mult(this.engine.lights.lights.get(0).radius);


            //Draw the quad
            this.gl.glBegin(GL.GL_QUADS);
            this.gl.glVertex3f(lightEx2.x, lightEx2.y, lightEx2.z);
            this.gl.glVertex3f(lightEx1.x, lightEx1.y, lightEx1.z);
            this.gl.glVertex3f(extrude.x1.x, extrude.x1.y, extrude.x1.z);
            this.gl.glVertex3f(extrude.x2.x, extrude.x2.y, extrude.x2.z);
            this.gl.glEnd();
        }


        //Draw cap
        this.gl.glBegin(GL.GL_TRIANGLES);
        for (Face capFace : this.cap) {
            //draw frontcap
            this.gl.glVertex3f(capFace.v1.x, capFace.v1.y, capFace.v1.z);
            this.gl.glVertex3f(capFace.v2.x, capFace.v2.y, capFace.v2.z);
            this.gl.glVertex3f(capFace.v3.x, capFace.v3.y, capFace.v3.z);

            //make corresponding
            //back face
            Vector backCap1 = new Vector(capFace.v1.subR(this.engine.lights.lights.get(0).origin));
            Vector backCap2 = new Vector(capFace.v2.subR(this.engine.lights.lights.get(0).origin));
            Vector backCap3 = new Vector(capFace.v3.subR(this.engine.lights.lights.get(0).origin));
            backCap1.normalize();
            backCap2.normalize();
            backCap3.normalize();

            backCap1.mult(200f);
            backCap2.mult(200f);
            backCap3.mult(200f);

            //draw backcap
            this.gl.glVertex3f(backCap3.x, backCap3.y, backCap3.z);
            this.gl.glVertex3f(backCap2.x, backCap2.y, backCap2.z);
            this.gl.glVertex3f(backCap1.x, backCap1.y, backCap1.z);
        }
        this.gl.glEnd();

        this.gl.glPopMatrix();
    }
}

