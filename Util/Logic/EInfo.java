/*
 * /*
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
 
package Util.Logic;

import com.sun.opengl.util.j2d.TextRenderer;
import java.awt.Font;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public class EInfo {
    public String fps;
    public String octimeBuild;
    public String ocNodes;

    public void drawInfo(GLAutoDrawable glDrawable) {
        TextRenderer renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 12));
        renderer.beginRendering(glDrawable.getWidth(), glDrawable.getHeight());
        renderer.setColor(1.0f, 0.2f, 0.2f, 0.8f);
        renderer.draw(new String("FPS = " + this.fps), 20, glDrawable.getHeight() - 20);
        renderer.draw(new String("OctreeBuildTime = " + this.octimeBuild + "\n" + this.ocNodes), 20, glDrawable.getHeight() - 34);
        renderer.endRendering();
    }
}
