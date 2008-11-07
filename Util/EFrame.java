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

import Engine.*;
import com.sun.opengl.util.Animator;
import javax.media.opengl.GLCanvas;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EFrame extends Frame {

    public boolean quit;
    public GLCanvas canvas;
    private Camera cam;
    private final Animator animator;

    public EFrame() {
        this.cam = new Camera();
        this.canvas = new GLCanvas();
        this.animator = new Animator(this.canvas);
        this.add(this.canvas);
        this.setTitle("EERT");
        this.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        this.setSize(1024, 640);

        this.setVisible(true);
        this.animator.start();
        this.canvas.requestFocus();

    }
}

