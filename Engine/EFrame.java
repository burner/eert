package Engine;

import javax.media.opengl.GLCanvas;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EFrame extends Frame {

    private GLCanvas canvas;

    public EFrame() {
        this.setTitle("EERT");
        this.add(this.canvas);
        this.setSize(1024, 640);
        this.setVisible(true);
        this.canvas.display();
    }
}
