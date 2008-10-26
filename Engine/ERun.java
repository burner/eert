package Engine;

import Util.EFrame;

public class ERun implements Runnable {

    private EFrame frame;

    public ERun() {
        this.frame = new EFrame();
    }

    public void run() {
        while (!this.frame.quit) {
            this.frame.canvas.display();
        }
    }
}
