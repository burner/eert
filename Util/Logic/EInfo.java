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

import Engine.Engine;
import com.sun.opengl.util.j2d.TextRenderer;
import java.awt.Font;
import java.util.Date;
import javax.media.opengl.GLAutoDrawable;

public class EInfo {
    public String fps;
    public String octimeBuild;
    public String ocNodes;
    public int drawObj;
    public int drawnNodes;
    private Engine engine;

    public EInfo(Engine engine) {
        this.engine = engine;
    }

    public void drawInfo(GLAutoDrawable glDrawable) {
        TextRenderer renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 12));
        renderer.beginRendering(glDrawable.getWidth(), glDrawable.getHeight());
        renderer.setColor(1.0f, 0.2f, 0.2f, 0.8f);
        renderer.draw(new String("ETA = " + new Long(1234567890 - (new Date().getTime() / 1000L)).toString()), 20, glDrawable.getHeight() - 20);
        renderer.draw(new String("FPS = " + this.engine.fps), 20, glDrawable.getHeight() - 32);
        renderer.draw(new String("Faces Rendered = " + this.engine.root.facRender), 20, glDrawable.getHeight() - 44);
        renderer.draw(new String("Octreedepth = " + this.engine.root.treeDepth), 20, glDrawable.getHeight() - 56);
        renderer.draw(new String("OctreeBuildTime = " + this.octimeBuild), 20, glDrawable.getHeight() - 68);
        renderer.draw(new String("OctreeNodes = " + this.engine.root.numNodes), 20, glDrawable.getHeight() - 80);
        renderer.draw(new String("Drawn leaves = " + this.drawnNodes), 20, glDrawable.getHeight() - 92);
        renderer.draw(new String("Camera Pos = " + this.engine.cam.loc.toString()), 20, glDrawable.getHeight() - 104);
        renderer.draw(new String("Camera Lookangle Pitch = " + this.engine.cam.pitch + " Heading = " + this.engine.cam.heading), 20, glDrawable.getHeight() - 116);
        renderer.draw(new String("ObjIns = " + this.engine.objectHandler.objIns.length), 20, glDrawable.getHeight() - 128);
        renderer.draw(new String("Drawn Obj = " + this.drawObj), 20, glDrawable.getHeight() - 140);
        renderer.endRendering();
        renderer.setColor(1f, 1f, 1f, 1f);
        this.drawnNodes = 0;
        this.drawObj = 0;
    }
}
