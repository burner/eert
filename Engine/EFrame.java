package Engine;

import javax.media.opengl.GLCanvas;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EFrame extends Frame {
	public GLCanvas canvas;
	public EFrame() {
		this.canvas = new GLCanvas();
		this.canvas.addGLEventListener(new Engine());
		this.add(this.canvas);
		this.setTitle("EERT");
		this.setSize(1024, 640);
		this.setVisible(true);
	}
}
