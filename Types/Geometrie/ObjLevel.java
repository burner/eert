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
import java.io.FileNotFoundException;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

public class ObjLevel {

    public Face[] faces;
    private String texName;
    private Engine engine;
    private GL gl;
    private Texture tex;

    public ObjLevel(GL gl, Engine engine, Face[] faces, String texName) throws FileNotFoundException {
        this.engine = engine;
        this.gl = gl;
        this.faces = faces;
        this.texName = texName;

        this.tex = TextureIO.netTexture(this.texName, false);

        this.listID = gl.glGenLists(1);

        gl.glNewList(this.listID, GL.GL_COMPILE);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        for (int i = 0; i < this.faces.length - 1; i++) {
            Face tmpFace = this.faces[i];

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
    }

    void draw() {
        this.tex.enable();
        this.tex.bind();
        gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
        this.gl.glCallList(this.listID);
        this.tex.disable();
    }
}
