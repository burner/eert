/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EKeyListener implements KeyListener {
    private Camera cam;
    public EKeyListener(Camera cam) {
        this.cam = cam;
    }
    public void keyTyped(KeyEvent e) {
        switch(e.getKeyChar()) {
            case 'a': 
                this.cam.x -=0.09f;
                break;
            case 'd':
                this.cam.x +=0.09f;
                break;
            case 's':
                this.cam.z -=0.09f;
                break;
            case 'w':
                this.cam.z +=0.09f;
                break;
            case 'o':
                this.cam.y -=0.09f;
                break;                
            case 'l':
                this.cam.y +=0.09f;
                break;
        }
    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {
        
    }

}
