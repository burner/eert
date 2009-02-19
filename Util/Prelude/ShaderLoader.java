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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util.Prelude;

import Content.Shader.Shader;
import com.sun.opengl.util.StreamUtil;
import java.io.File;
import java.io.FileInputStream;
import javax.media.opengl.GL;

public class ShaderLoader {

    private GL gl;
    public int program;
    private int[] status;
    public int shaderprogram;
    public int shaderprogramG;

    public ShaderLoader(GL gl, boolean verOrFra, String filename) {
        //save the gl context
        this.gl = gl;

        //String extensions = gl.glGetString(GL.GL_EXTENSIONS);
        //System.out.println(extensions.replace(' ', '\n'));

        //make handle depending of the kind of shader
        int Handle;
        if (verOrFra) {
            Handle = gl.glCreateShaderObjectARB(GL.GL_VERTEX_SHADER);
        } else {
            Handle = gl.glCreateShaderObjectARB(GL.GL_FRAGMENT_SHADER);
        }

        System.out.println(new File(".").getAbsolutePath());
        //open File containing shader
        String[] source = null;
        try {
            source = (new String(StreamUtil.readAll(new FileInputStream("Shader/" + filename)))).split("\n");
        } catch (Exception ex) {
            System.err.println(ex);
        }
        gl.glShaderSourceARB(Handle, source.length, source, null);
        gl.glCompileShaderARB(Handle);


        if(verOrFra) {
            check(Handle, 0);
        } else {
            check(Handle, 1);
        }

        //compile shader

        int shaderprogramT = gl.glCreateProgramObjectARB();
        gl.glAttachObjectARB(shaderprogramT, Handle);
        gl.glLinkProgramARB(shaderprogramT);

        gl.glUseProgramObjectARB(shaderprogramT);
    }

    public ShaderLoader(GL gl, String vertFile, String fragFile) {
        this.gl = gl;
        //save the gl context
        int vertHandle =  gl.glCreateShaderObjectARB (GL.GL_VERTEX_SHADER);
        int fragHandle =  gl.glCreateShaderObjectARB (GL.GL_FRAGMENT_SHADER);


        String[] vsrc = null;
		try
		{
			vsrc = (new String (StreamUtil.readAll (Shader.class.getResourceAsStream(vertFile)))).split ("\n");
		}
		catch (Exception ex)
		{
			System.err.println (ex);
		}
		gl.glShaderSource(vertHandle, vsrc.length, vsrc, null);
		gl.glCompileShaderARB (vertHandle);
        check(vertHandle, 0);



        String[] fsrc = null;
		try
		{
			fsrc = (new String (StreamUtil.readAll (Shader.class.getResourceAsStream(fragFile)))).split ("\n");
		}
		catch (Exception ex)
		{
			System.err.println (ex);
		}

		gl.glShaderSource(fragHandle, fsrc.length, fsrc, null);
		gl.glCompileShaderARB (fragHandle);
        check(fragHandle, 1);


        this.shaderprogramG = gl.glCreateProgramObjectARB();
        gl.glAttachObjectARB(shaderprogramG, vertHandle);
        gl.glAttachObjectARB(shaderprogramG, fragHandle);
        gl.glValidateProgram(shaderprogramG);
		gl.glLinkProgramARB(shaderprogramG);

        gl.glUseProgramObjectARB(shaderprogramG);
    }

    //check compiler info for errors
  boolean check(int shader, int type){
    int[]success = new int[1];
    this.gl.glGetShaderiv(shader, GL.GL_COMPILE_STATUS, success, 0);
    if(success[0]!=GL.GL_FALSE) {
      System.out.println("No error in "+(type==0?"vertex":"fragment")+" compilation");
      return true;
    }
    byte[] log = new byte[10000];
    this.gl.glGetShaderInfoLog(shader, GL.GL_OBJECT_INFO_LOG_LENGTH_ARB, success, 0, log, 0);
    System.out.println("Error in "+(type==0?"vertex":"fragment")+" compilation");
    for (int i = 0; i < success[0]; i++) {
      System.out.print((char) log[i]);
    }
    return false;
  }

    public void bindProgram() {
        gl.glUseProgramObjectARB(shaderprogramG);
    }
}
