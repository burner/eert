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
import javax.media.opengl.glu.*;

public class Camera {

    public Vector loc;
    public Vector ori;
    public int prevX,  prevY;
    boolean mouseRButtonDown;
    int prevMouseX;
    int prevMouseY;    // Camera angle in degree (0-360).

    float keyTurn;
    float turnSens;
    float camPitch = 0.0f; // up-down

    float camHeading = 90.0f; // left-right

    float camRoll = 0.0f;    // Vector in camera direction: look-at-vector

    float speed;
    


    public Camera() {
        this.loc = new Vector(0.0f, 0.0f, 8.0f);
        this.ori = new Vector(1.0f, 0.0f, 0.0f);

        this.camPitch = 90.0f;
        this.camHeading = 0.0f;

        this.turnSens = 0.5f;
        this.keyTurn = 0.5f;

        this.speed = 0.01f;
        
        
    }

    public void forward() {
        float x = this.loc.x + this.ori.x * this.speed * UHPT.getETime() / 1000000000;
        float y = this.loc.y + this.ori.y * this.speed * UHPT.getETime() / 1000000000;
        float z = this.loc.z + this.ori.z * this.speed * UHPT.getETime() / 1000000000;
        this.loc.x += x;
        this.loc.y += y;
        this.loc.z += z;
        
        this.ori.x += x;
        this.ori.y += y;
        this.ori.z += z;
    }

    public void backward() {
        this.loc.x -= this.ori.x * this.speed * UHPT.getETime() / 1000000000;
        this.loc.y -= this.ori.y * this.speed * UHPT.getETime() / 1000000000;
        this.loc.z -= this.ori.z * this.speed * UHPT.getETime() / 1000000000;
    }

    public void strafeLeft() {
        float x = this.loc.x * this.ori.y - this.loc.y * this.ori.x;
        float z = this.loc.z * this.ori.x - this.loc.x * this.ori.z;

        this.loc.x -= x;
        this.loc.z -= z;
    }

    public void strafeRight() {
        float x = this.loc.x * this.ori.y - this.loc.y * this.ori.x;
        float z = this.loc.z * this.ori.x - this.loc.x * this.ori.z;

        this.loc.x += x;
        this.loc.z += z;
    }

    public void giveInfo() {
        System.out.println("Loc: x " + this.loc.x + " y " + this.loc.y + " z " + this.loc.z);
        System.out.println("Ori x " + this.ori.x + " y " + this.ori.y + " z " + this.ori.z);
    }

    public void turnLeft(int delta) {
        // Wenn delta -1 ist, dann wurde eine Taste gedrueckt und daher wird der keyTurn Parameter verwendet.
        if (delta == -1) {
            this.camHeading += this.keyTurn;
        } else {
            this.camHeading += (this.turnSens * (float) delta);
        }
        updateDirection();
    }

    public void turnRight(int delta) {
        if (delta == -1) {
            this.camHeading -= this.keyTurn;
        } else {
            this.camHeading -= (this.turnSens * (float) delta);
        }
        updateDirection();
    }

    public void turnUp(int delta) {
        // if delta is -1 we have a keystroke
        if (delta == -1) {
            this.camPitch += this.keyTurn;
        } else {
            this.camPitch += (this.turnSens * (float) delta);
        }
        updateDirection();
    }

    public void turnDown(int delta) {

        // if delta is -1 we have a keystroke
        if (delta == -1) {
            this.camHeading -= this.keyTurn;
        } else {
            this.camHeading -= (this.turnSens * (float) delta);
        }

        updateDirection();
    }

    private void updateDirection() {
        float x;
        float y;
        float z;
        x = (float) Math.sin(Math.toRadians(this.camHeading));
        y = (float) Math.sin(Math.toRadians(this.camRoll));
        z = (float) Math.cos(Math.toRadians(this.camPitch));

        this.ori = new Vector(x, y, z);
        //giveInfo();
    }

    public void drawCam() {
        GLU glu = new GLU();
        glu.gluLookAt(this.loc.x, this.loc.y, this.loc.z,
                this.ori.x, this.ori.y, this.ori.z,
                  0.0f, 1.0f, 0.0f);
    }

    public void camLoc(GL gl) {
        gl.glTranslatef(this.loc.x, this.loc.y, this.loc.z);
    }

    public void camRot(GL gl) {
        gl.glRotatef(-this.camPitch, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(180.0f - this.camHeading, 0.0f, 1.0f, 0.0f);
    }
}
