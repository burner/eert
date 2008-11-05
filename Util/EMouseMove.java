/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class EMouseMove implements MouseMotionListener {
    private Camera cam;
    public EMouseMove(Camera cam) {
        this.cam = cam;
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Dimension size = e.getComponent().getSize();

        float thetaY = 360.0f * ((float) (x - this.cam.prevMouseX) / (float)1024);
        float thetaX = 360.0f * ((float) (this.cam.prevMouseY - y) / (float)640);

        this.cam.prevMouseX = x;
        this.cam.prevMouseY = y;

        this.cam.rotate(thetaX, thetaY);
    }

    public void mouseMoved(MouseEvent e) {
        
    }
}
