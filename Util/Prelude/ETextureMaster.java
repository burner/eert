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
package Util.Prelude;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import javax.media.opengl.GL;

public class ETextureMaster {

    public ETexture[] textures;
    private BufferedImage bufferedImage;
    private GL gl;

    public ETextureMaster(GL gl) {
        this.gl = gl;
        parse();
    }

    void parse() {
        File dir = new File("./Textures/");
        String[] texNames = dir.list();
        this.textures = new ETexture[texNames.length];
        for (String foo : texNames) {
            System.out.println(foo);
        }
        int[] texId = new int[1];
        for (int i = 0; i < texNames.length; i++) {
            this.textures[i] = new ETexture(new StringBuffer().append("./Textures/").append(texNames[i]).toString());
            gl.glGenTextures(1, texId, 0);
            this.textures[i].texId = texId[0];
            gl.glBindTexture(GL.GL_TEXTURE_2D, texId[0]);
        }
    }
}

