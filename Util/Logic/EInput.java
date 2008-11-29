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
package Util.Logic;

import Engine.Engine;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public final class EInput implements KeyListener, MouseListener, MouseMotionListener, Runnable {

    private int mouseOldX;
    private int mouseOldY;
    private boolean keyForward;
    private boolean keyBackward;
    private boolean keySlideLeft;
    private boolean keySlideRight;
    private boolean keyUp;
    private boolean keyDown;
    private Camera camera;
    private Thread camAnimator;
    private Engine engine;

    public EInput(Camera camera, Engine engine) {
        this.engine = engine;
        this.keyForward = false;
        this.keyBackward = false;
        this.keySlideLeft = false;
        this.keySlideRight = false;
        this.mouseOldX = 0;
        this.mouseOldY = 0;

        this.camera = camera;

        this.camAnimator = new Thread(this);
        this.camAnimator.start();
    }

    public void run() {
        while (true) {

            //System.out.println("Within EInput.Run()");
            if (this.keyForward) {
                //System.out.println("forward");
                this.camera.forward();
            }
            if (this.keyBackward) {
                //System.out.println("backward");
                this.camera.backward();
            }
            if (this.keySlideLeft) {
                this.camera.strafeLeft();
            }
            if (this.keySlideRight) {
                this.camera.strafeRight();
            }
            if (this.keyUp) {
                this.camera.moveUp();
            }
            if (this.keyDown) {
                this.camera.moveDown();
            }

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyChar();
        //System.out.println("keyPressed");
        if (keyCode == 'i') {
            if (this.engine.drawInfo) {
                this.engine.drawInfo = false;
            } else {
                this.engine.drawInfo = true;
            }
        }
        if (keyCode == 'p') {
            System.exit(0);
        }
        if (keyCode == 'w') {
            this.keyForward = true;
        }
        if (keyCode == 's') {
            this.keyBackward = true;
        }
        if (keyCode == 'a') {
            this.keySlideLeft = true;
        }
        if (keyCode == 'd') {
            this.keySlideRight = true;
        }
        if (keyCode == ' ') {
            this.keyUp = true;
        }
        if (keyCode == 'c') {
            this.keyDown = true;
        }
        if (keyCode == 'u') {
            setFullScreenMode();
        }
    }

    public void keyReleased(KeyEvent e) {
        char keyCode = e.getKeyChar();

        if (keyCode == 'w') {
            this.keyForward = false;
        }
        if (keyCode == 's') {
            this.keyBackward = false;
        }
        if (keyCode == 'a') {
            this.keySlideLeft = false;
        }
        if (keyCode == 'd') {
            this.keySlideRight = false;
        }
        if (keyCode == ' ') {
            this.keyUp = false;
        }
        if (keyCode == 'c') {
            this.keyDown = false;
        }

    }

    public void keyTyped(KeyEvent arg0) {
    }

    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        this.mouseOldX = x;
        this.mouseOldY = y;
    }

    public void mousePressed(MouseEvent e) {
        //System.out.println("mousePressed");
        int x = e.getX();
        int y = e.getY();
        this.mouseOldX = x;
        this.mouseOldY = y;
    }

    public void mouseClicked(MouseEvent arg0) {
    }

    public void mouseEntered(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent arg0) {
    }

    public void mouseDragged(MouseEvent e) {
        //System.out.println("Mouse dragged");
        int x = e.getX();
        int y = e.getY();
        int dx = Math.abs(x - this.mouseOldX);
        int dy = Math.abs(y - this.mouseOldY);


        // Calculate mouse movements
        if (x < this.mouseOldX) {
            this.camera.turnLeft(dx);
        } else if (x > this.mouseOldX) {
            this.camera.turnRight(dx);
        }
        if (y < this.mouseOldY) {
            this.camera.turnUp(dy);
        } else if (y > this.mouseOldY) {
            this.camera.turnDown(dy);
        }

        this.mouseOldX = x;
        this.mouseOldY = y;
    }

    public void mouseMoved(MouseEvent arg0) {
    }

    void setFullScreenMode() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gs = ge.getDefaultScreenDevice();
        if (engine.frame.isUndecorated()) {
            this.engine.frame.dispose();
            this.engine.frame.setUndecorated(false);
            this.engine.frame.setSize(1024, 640);
            this.engine.frame.setVisible(true);
        } else {
            this.engine.frame.dispose();
            this.engine.frame.setUndecorated(true);
            gs.setFullScreenWindow(this.engine.frame);
            this.engine.frame.toFront();
        }

    }
}
