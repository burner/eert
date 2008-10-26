package Engine;

public class ERun implements Runnable {
	private EFrame frame;
	public ERun() {
		this.frame = new EFrame();
	}
	
	public void run() {
		this.frame.canvas.display();
	}
}
