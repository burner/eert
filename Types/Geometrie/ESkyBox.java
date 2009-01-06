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
import java.io.FileNotFoundException;
import javax.media.opengl.GL;

public class ESkyBox {

    private Engine engine;
    private Camera cam;
    private String textures;
    private GL gl;
    private float expanse;
    private int listID;
    private int[] textureHandles;
    private int texHandle0;
    private ETexture texImage0;

    public ESkyBox(GL gl, Engine engine, Camera cam, String textures, float expanse) throws FileNotFoundException {
        this.gl = gl;
        this.engine = engine;
        this.cam = cam;
        this.textures = textures;
        this.expanse = expanse;
        makeSkyBox();
    }

    private void makeSkyBox() throws FileNotFoundException {

        this.textureHandles = new int[1];
        gl.glGenTextures(1, this.textureHandles, 0);

        //Front
        this.texImage0 = new ETexture(this.textures);

        gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureHandles[0]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
                GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
                GL.GL_NEAREST);
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, this.texImage0.getWidth(), this.texImage0.getHeight(),
                0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, this.texImage0.getBuffer());


        this.listID = gl.glGenLists(1);

        gl.glNewList(this.listID, GL.GL_COMPILE);
        //Front
        gl.glBegin(GL.GL_QUADS);
        gl.glBindTexture(GL.GL_TEXTURE_2D, this.texHandle0);
        gl.glTexCoord2f(0.25f, 0.6666f);
        gl.glVertex3f(-this.expanse, this.expanse, -this.expanse);
        gl.glTexCoord2f(0.25f, 0.3333f);
        gl.glVertex3f(-this.expanse, -this.expanse, -this.expanse);
        gl.glTexCoord2f(0.5f, 0.3333f);
        gl.glVertex3f(this.expanse, -this.expanse, -this.expanse);
        gl.glTexCoord2f(0.5f, 0.6666f);
        gl.glVertex3f(this.expanse, this.expanse, -this.expanse);

        //Back
        gl.glTexCoord2f(0.75f, 0.6666f);
        gl.glVertex3f(this.expanse, this.expanse, this.expanse);
        gl.glTexCoord2f(0.75f, 0.3333f);
        gl.glVertex3f(this.expanse, -this.expanse, this.expanse);
        gl.glTexCoord2f(1f, 0.3333f);
        gl.glVertex3f(-this.expanse, -this.expanse, this.expanse);
        gl.glTexCoord2f(1f, 0.6666f);
        gl.glVertex3f(-this.expanse, this.expanse, this.expanse);

        //Right
        gl.glTexCoord2f(0.5f, 0.3333f);
        gl.glVertex3f(this.expanse, -this.expanse, -this.expanse);
        gl.glTexCoord2f(0.75f, 0.3333f);
        gl.glVertex3f(this.expanse, -this.expanse, this.expanse);
        gl.glTexCoord2f(0.75f, 0.6666f);
        gl.glVertex3f(this.expanse, this.expanse, this.expanse);
        gl.glTexCoord2f(0.5f, 0.6666f);
        gl.glVertex3f(this.expanse, this.expanse, -this.expanse);

        //Left
        gl.glTexCoord2f(0, 0.6666f);
        gl.glVertex3f(-this.expanse, this.expanse, this.expanse);
        gl.glTexCoord2f(0, 0.3333f);
        gl.glVertex3f(-this.expanse, -this.expanse, this.expanse);
        gl.glTexCoord2f(0.25f, 0.3333f);
        gl.glVertex3f(-this.expanse, -this.expanse, -this.expanse);
        gl.glTexCoord2f(0.25f, 0.6666f);
        gl.glVertex3f(-this.expanse, this.expanse, -this.expanse);

        //Top
        gl.glTexCoord2f(0.25f, 1f);
        gl.glVertex3f(-this.expanse, this.expanse, this.expanse);
        gl.glTexCoord2f(0.25f, 0.6666f);
        gl.glVertex3f(-this.expanse, this.expanse, -this.expanse);
        gl.glTexCoord2f(0.5f, 0.6666f);
        gl.glVertex3f(this.expanse, this.expanse, -this.expanse);
        gl.glTexCoord2f(0.5f, 1f);
        gl.glVertex3f(this.expanse, this.expanse, this.expanse);

        //Bottom
        gl.glTexCoord2f(0.25f, 0.3333f);
        gl.glVertex3f(-this.expanse, -this.expanse, -this.expanse);
        gl.glTexCoord2f(0.25f, 0f);
        gl.glVertex3f(-this.expanse, -this.expanse, this.expanse);
        gl.glTexCoord2f(0.5f, 0f);
        gl.glVertex3f(this.expanse, -this.expanse, this.expanse);
        gl.glTexCoord2f(0.5f, 0.3333f);
        gl.glVertex3f(this.expanse, -this.expanse, -this.expanse);

        gl.glEnd();
        gl.glEndList();
    }

    public void draw() {
        gl.glPushMatrix();
        gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureHandles[0]);
        gl.glTranslatef(this.cam.loc.x, this.cam.loc.y, this.cam.loc.z);
        gl.glCallList(this.listID);
        gl.glPopMatrix();
    }
}
