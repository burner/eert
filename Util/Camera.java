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
    float heading = 0.0f; // up-down
    float maxPitch = 90.0f;
    float minPitch = -90.0f;
    float pitch = 0.0f; // left-right
    float roll = 0.0f;    // Vector in camera direction: look-at-vector
    float speed;

    public Camera() {
        this.loc = new Vector(0.0f, 0.0f, 8.0f);
        this.ori = new Vector(0.0f, 0.0f, 1.0f);

        this.heading = 0.0f;
        this.pitch = 0.0f;
        this.roll = 0.0f;

        this.turnSens = 0.5f;
        this.keyTurn = 0.5f;

        this.speed = 0.025f;
    }

    public void forward() {
        float mov = ((System.nanoTime() - UHPT.lastFrame) / 10000000.0f);
        this.loc.x -= this.ori.x * mov;
        this.loc.y -= this.ori.y * mov;
        this.loc.z -= this.ori.z * mov;
        System.out.println(mov);

    }

    public void backward() {
        float mov = ((System.nanoTime() - UHPT.lastFrame) / 10000000.0f);
        this.loc.x += this.ori.x * mov;
        this.loc.y += this.ori.y * mov;
        this.loc.z += this.ori.z * mov;
    }

    public void strafeLeft() {
        float mov = ((System.nanoTime() - UHPT.lastFrame) / 10000000.0f);
        Vector slide = new Vector(0.0f, 0.0f, 0.0f);
        float x;
        float z;

        x = (float) Math.sin((90.0f + this.heading) * Math.PI / 180);
        z = -(float) Math.cos((90.0f + this.heading) * Math.PI / 180);

        slide.x = x;
        slide.z = z;
        slide.normalize();
        slide.mult(mov);

        this.loc.sub(slide);
    }

    public void strafeRight() {
        float mov = ((System.nanoTime() - UHPT.lastFrame) / 10000000.0f);
        Vector slide = new Vector(0.0f, 0.0f, 0.0f);
        float x;
        float z;

        x = (float) Math.sin((270.0f + this.heading) * Math.PI / 180);
        z = -(float) Math.cos((270.0f + this.heading) * Math.PI / 180);

        slide.x = x;
        slide.z = z;
        slide.normalize();
        slide.mult(mov);

        this.loc.sub(slide);
    }

    public void giveInfo() {
        System.out.println("Loc: x " + this.loc.x + " y " + this.loc.y + " z " + this.loc.z);
        System.out.println("Ori x " + this.ori.x + " y " + this.ori.y + " z " + this.ori.z);
        System.out.println("heading " + this.heading + " pitch " + this.pitch);
    }

    public void turnLeft(int delta) {
        // Wenn delta -1 ist, dann wurde eine Taste gedrueckt und daher wird der keyTurn Parameter verwendet.
        if (delta == -1) {
            this.heading -= 0.5f * delta;
        } else {
            this.heading -= (this.turnSens * (float) delta);
        }
        updateDirection();
    }

    public void turnRight(int delta) {
        if (delta == -1) {
            this.heading += 0.5f * delta;
        } else {
            this.heading += (this.turnSens * (float) delta);
        }
        updateDirection();

    }

    public void turnUp(int delta) {
        if (this.pitch < this.maxPitch) {
            if (delta == -1) {
                this.pitch -= this.keyTurn;
            } else {
                this.pitch -= (this.turnSens * (float) delta);
            }
        }
        updateDirection();
    }

    public void turnDown(int delta) {
        if (this.pitch > this.minPitch) {
            if (delta == -1) {
                this.pitch += this.keyTurn;
            } else {
                this.pitch += (this.turnSens * (float) delta);
            }
            updateDirection();
        }
    }

    private void updateDirection() {
        float x;
        float y = 0.0f;
        float z;

        x = -(float) Math.sin(Math.toRadians(this.heading));
        y = (float) Math.sin(Math.toRadians(this.pitch));
        z = (float) Math.cos(Math.toRadians(this.heading));
        this.ori = new Vector(x, y, z);

    }

    public void translateAccordingToCameraPosition(GL gl) {

        //gl.glTranslatef(-loc.x, -loc.y, -loc.z);
        gl.glRotatef(this.pitch, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(this.heading, 0.0f, 1.0f, 0.0f);
        gl.glTranslatef(-loc.x, -loc.y, -loc.z);

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
        gl.glRotatef(-this.heading, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(180.0f - this.pitch, 0.0f, 1.0f, 0.0f);
    }
}
