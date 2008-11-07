package Util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class EMouseListener implements MouseListener {

    private Camera cam;

    public EMouseListener(Camera cam) {
        this.cam = cam;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        /*this.cam.prevX = e.getX();
        this.cam.prevY = e.getY();
        if ((e.getModifiers() & e.BUTTON3_MASK) != 0) {
            this.cam.mouseRButtonDown = true;
        }*/
    }

    public void mouseReleased(MouseEvent e) {
        /*if ((e.getModifiers() & e.BUTTON3_MASK) != 0) {
            this.cam.mouseRButtonDown = false;
        }*/
    }

    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
