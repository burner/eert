package Util;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


/**
 * @author Stefan
 *
 */
public final class EInput implements KeyListener, MouseListener, MouseMotionListener, Runnable {

	private int mouseOldX;
	private int mouseOldY;

	private boolean keyForward;
	private boolean keyBackward;
	private boolean keySlideLeft;
	private boolean keySlideRight;
	private boolean keyUp;
	private boolean keyDown;

	private Camera camera;

	private Thread camAnimator;


	/**
	 *
	 */
	public EInput(Camera camera) {
		this.keyForward = false;
		this.keyBackward = false;
		this.keySlideLeft = false;
		this.keySlideRight = false;
		this.mouseOldX = 0;
		this.mouseOldY = 0;

		this.camera = camera;

		this.camAnimator = new Thread(this);
		this.camAnimator.start();
	}


	public void run() {
		while (true) {
			if (this.keyForward) {
				this.camera.forward();
			}
			if (this.keyBackward) {
				this.camera.backward();
			}
			if (this.keySlideLeft) {
				this.camera.strafeLeft();
			}
			if (this.keySlideRight) {
				this.camera.strafeRight();
			}
			if (this.keyUp) {
			}
			if (this.keyDown) {
			}

			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {

			}
		}
	}


	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (keyCode == 'w') {
			this.keyForward = true;
		}
		if (keyCode == 's') {
			this.keyBackward = true;
		}
		if (keyCode =='a') {
			this.keySlideLeft = true;
		}
		if (keyCode == 'd') {
			this.keySlideRight = true;
		}
		if (keyCode == ' ') {
			this.keyUp = true;
		}
		if (keyCode == 'c') {
			this.keyDown = true;
		}
	}


	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == 'w') {
			this.keyForward = false;
		}
		if (keyCode == 's') {
			this.keyBackward = false;
		}
		if (keyCode == 'a') {
			this.keySlideLeft = false;
		}
		if (keyCode == 'd') {
			this.keySlideRight = false;
		}
		if (keyCode == ' ') {
			this.keyUp = false;
		}
		if (keyCode == 'c') {
			this.keyDown = false;
		}
	}


	public void keyTyped(KeyEvent arg0) {
	}


	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		this.mouseOldX = x;
		this.mouseOldY = y;
	}


	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		this.mouseOldX = x;
		this.mouseOldY = y;
	}


	public void mouseClicked(MouseEvent arg0) {
	}


	public void mouseEntered(MouseEvent arg0) {
	}


	public void mouseExited(MouseEvent arg0) {
	}


	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int dx = Math.abs(x - this.mouseOldX);
		int dy = Math.abs(y - this.mouseOldY);


		// Calculate mouse movements
		if (x < this.mouseOldX) {
			this.camera.turnLeft(dx);
		} else if (x > this.mouseOldX) {
			this.camera.turnRight(dx);
		}
		if (y < this.mouseOldY) {
			this.camera.turnUp(dy);
		} else if (y > this.mouseOldY) {
			this.camera.turnDown(dy);
		}

		this.mouseOldX = x;
		this.mouseOldY = y;
	}


	public void mouseMoved(MouseEvent arg0) {
	}
}
