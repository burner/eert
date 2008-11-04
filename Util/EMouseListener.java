package Util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class EMouseListener implements MouseListener/*, MouseMotionListener*/ {

    private Camera cam;

    public EMouseListener(Camera cam) {
        this.cam = cam;
    }

    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
/*
    public void mouseDragged(MouseEvent arg0) {
        int x = this.cam.loc.x;
        int y = this.cam.loc.y;
        Dimension size = e.getComponent().getSize();

        float thetaY = 360.0f * ((float) (x - prevMouseX) / (float) size.width);
        float thetaX = 360.0f * ((float) (prevMouseY - y) / (float) size.height);

        prevMouseX = x;
        prevMouseY = y;

        view_rotx += thetaX;
        view_roty += thetaY;
    }
*/
    public void mouseMoved(MouseEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
