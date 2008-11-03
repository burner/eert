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
package Util;

import javax.media.opengl.GL;

public class Camera {

    public float x;
    public float y;
    public float z;
    public float xRot;
    public float yRot;
    public float zRot;

    public Camera() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.xRot = 0.0f;
        this.yRot = 0.0f;
        this.zRot = 0.0f;
    }

    public void drawCam(GL gl) {
        gl.glTranslatef(x, y, z);        
        gl.glRotatef(xRot, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(yRot, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(zRot, 0.0f, 0.0f, 1.0f);
    }

    float x = (float)( dx*cos(yRot) + dy*sin(xRot)*sin(yRot) - dz*cos(xRot)*sin(yRot) );
    float y = (float)(              + dy*cos(xRot)           + dz*sin(xRot)           );
    float z = (float)( dx*sin(yRot) - dy*sin(xRot)*cos(yRot) + dz*cos(xRot)*cos(yRot) );
}
