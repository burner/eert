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
    private String[] textures;
    private GL gl;
    private float expanse;
    private int listID;
    private int[] textureHandles;
    private ETexture texImage0;
    private ETexture texImage1;
    private ETexture texImage2;
    private ETexture texImage3;
    private ETexture texImage4;
    private ETexture texImage5;


    public ESkyBox(GL gl, Engine engine, Camera cam, String[] textures, float expanse) {
        this.gl = gl;
        this.engine = engine;
        this.cam = cam;
        this.textures = textures;
        this.expanse = expanse;
    }

    private void makeSkyBox() throws FileNotFoundException {
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

    }
}
