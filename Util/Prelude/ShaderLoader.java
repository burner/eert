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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;
import java.util.LinkedList;
import javax.media.opengl.GL;

/**
 *
 * @author burner
 */
public class ShaderLoader {

    private int handle;
    private GL gl;
    private int program;

    public ShaderLoader(GL gl, boolean vShaderOrFShader, String filename) throws FileNotFoundException, IOException {
        //save the gl context
        this.gl = gl;

        //make handle depending of the kind of shader
        if (vShaderOrFShader) {
            this.handle = gl.glCreateShader(GL.GL_VERTEX_SHADER);
        } else {
            this.handle = gl.glCreateShader(GL.GL_FRAGMENT_SHADER);
        }

        //open File containing shader
        BufferedReader brv = new BufferedReader(new FileReader(filename));
        String line;

        //read the file line by line and a "\n"
        LinkedList<String> prog = new LinkedList<String>();
        while ((line = brv.readLine()) != null) {
            prog.add(line + "\n");
        }

        //make a stringArray out of it
        String[] progToSend = prog.toArray(new String[prog.size()]);

        //count the lineLength for every line
        int[] n = new int[progToSend.length];
        for (int i = 0; i < progToSend.length; i++) {
            n[i] = progToSend[i].length();
        }

        //compule the shader
        gl.glShaderSource(this.handle, progToSend.length, progToSend, n, 0);
        gl.glCompileShader(this.handle);
        int shaderprogram = gl.glCreateProgram();
        gl.glAttachShader(shaderprogram, this.handle);
        gl.glLinkProgram(shaderprogram);
        gl.glValidateProgram(shaderprogram);
    }

    public void bindProgram() {
        gl.glUseProgram(this.program);
    }
}
