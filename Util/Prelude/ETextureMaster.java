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
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

public class ETextureMaster {

    ETexture[] textures;
    private BufferedImage bufferedImage;

    public ETextureMaster() {
        parse();
    }

    void parse() {
        File dir = new File("./Textures/");
        String[] children = dir.list();
        this.textures = new ETexture[children.length];
        for (String foo : children) {
            System.out.println(foo);
        }
        for (int i = 0; i < children.length; i++) {
            this.textures[i] = new ETexture(new StringBuffer().append("./Textures/").append(children[i]).toString());
        }
    }
}

