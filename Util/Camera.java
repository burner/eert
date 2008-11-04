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

import Types.Vector;
import javax.media.opengl.GL;
import javax.media.opengl.GLDrawable;
import javax.media.opengl.glu.GLU;

public class Camera {

    public Vector loc;
    public Vector ori;


    public Camera() {
        this.loc = new Vector(0.0f, 0.0f, 8.0f);
        this.ori = new Vector(0.0f, 0.0f, 1.0f);

    }

    public void update() {
    }

    public void rotate(float angleX, float angleY) {
        this.ori.x = (this.ori.x + angleX) % 360.0f;
        this.ori.y = (this.ori.y + angleY) % 360.0f;
    }

    public void update(GL gl) {

    }

    public Vector toVectorInFixedSystem1(float dx, float dy, float dz) {
        //Don't calculate for nothing ...
        if (dx == 0.0f & dy == 0.0f && dz == 0.0f) {
            return new Vector(0.0f, 0.0f, 0.0f);        //Convert to Radian : 360° = 2PI
        }
        double xRot = Math.toRadians(ori.x);    //Math.toRadians is toRadians in Java 1.5 (static import)
        double yRot = Math.toRadians(ori.y);

        //Calculate the formula
        float x = (float) (dx * Math.cos(yRot) + dy * Math.sin(xRot) * Math.sin(yRot) - dz * Math.cos(xRot) * Math.sin(yRot));
        float y = (float) (+dy * Math.cos(xRot) + dz * Math.sin(xRot));
        float z = (float) (dx * Math.sin(yRot) - dy * Math.sin(xRot) * Math.cos(yRot) + dz * Math.cos(xRot) * Math.cos(yRot));

        //Return the vector expressed in the global axis system
        return new Vector(x, y, z);
    }

    public void lookAt(GLDrawable glDrawable) {
        //Get upward and forward vector, convert vectors to fixed coordinate sstem (similar than for translation 1)
        Vector up = toVectorInFixedSystem1(0.0f, 1.0f, 0.0f);        //Note: need to calculate at each frame
        Vector forward = toVectorInFixedSystem1(0.0f, 0.0f, 1.0f);
        Vector pos = this.loc;

        /*
         * Read Lesson 02 for more explanation of gluLookAt.
         */
        GLU glu = new GLU();
        glu.gluLookAt(
                //Position
                this.loc.x, this.loc.y, this.loc.z,
                //Orientation
                this.ori.x, this.ori.y, this.ori.z,
                //Upward vector
                up.x, up.y, up.z);
    }
}
