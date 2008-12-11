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

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;

public class ETextureMaster {

    public HashMap<String, Texture> textures;
    private BufferedImage bufferedImage;
    private GL gl;
    private Texture regularTexture;

    public ETextureMaster(GL gl) throws IOException {
        this.gl = gl;
        parse();
    }

    void parse() throws IOException {
        File dir = new File("./Textures/");
        String[] texNames = dir.list();
        this.textures = new HashMap<String, Texture>();
        for (String foo : texNames) {
            System.out.println(foo);
        }
        int[] texId = new int[1];
        for (String tmp : texNames) {
            File file2 = new File("./Textures/" + tmp);
            try {
                regularTexture = TextureIO.newTexture(file2, false);
                regularTexture.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
                regularTexture.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
            } catch (IOException e) {
                System.out.println("Error while Texture gen");
                e.printStackTrace();
            }
            this.textures.put(tmp, regularTexture);
        }
    }

    private static BufferedImage readImage(String resourceName) throws IOException {
        return ImageIO.read(new File("./Textures/" + resourceName));
    }
}

