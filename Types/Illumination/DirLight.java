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
import javax.media.opengl.GL;

class DirLight implements ELight {

    private Vector4 dir;
    private Vector4 col;
    private float[] ambiant;

    public DirLight(Vector4 dir, Vector col) {
        this.dir = dir;
        this.col = new Vector4(col.x, col.y, col.z, 1f);
        float[] foo = {0.3f, 0.3f, 0.3f, 1f};
        this.ambiant = foo;

    }

    public void draw(GL gl) {
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, this.dir.toArray(), 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, this.ambiant, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, col.toArray(),0);
        gl.glEnable(GL.GL_LIGHT0);
    }
}
