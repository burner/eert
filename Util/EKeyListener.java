/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EKeyListener implements KeyListener {

    private Camera cam;
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;

    public EKeyListener(Camera cam) {
        this.cam = cam;
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                break;
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
        }
    }

    public void keyTyped(KeyEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
