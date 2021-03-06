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
package Types.Illumination;

import Types.Geometrie.Vector;
import Types.Geometrie.Vector4;
import java.awt.Color;
import java.util.LinkedList;
import javax.media.opengl.GL;

public class LightManagement {
    public LinkedList<ELight> lights;

    public LightManagement() {
        this.lights = new LinkedList<ELight>();
    }

    public void addLight(Vector origin, float radius, Color color) {
        this.lights.add(new PointLight(origin, radius, color));
    }

    public void addLight(Vector4 dir, Vector col) {
        this.lights.add(new DirLight(dir, col));
    }

    public void draw(GL gl) {
        for(ELight light: this.lights) {
            light.draw(gl);
        }
    }
}
