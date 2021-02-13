import java.awt.*;
import java.applet.*;
import java.util.*;

public class Platform extends Character {
	private int stepy;
	private int id, xVelocity, yVelocity, vcount;
	private boolean brownAnimation = false;

	public Platform(int id, int x, int y, int w, int h) {
		super(id, x, y, w, h);

		int xVel = (int) (Math.random() * 2) + 1;
		if (xVel == 1)
			xVelocity = -2;
		if (xVel == 2)
			xVelocity = 2;

		int yVel = (int) (Math.random() * 2) + 1;
		if (yVel == 1)
			yVelocity = -1;
		if (yVel == 2)
			yVelocity = 1;

		vcount = 0;
	}

	public void changeStepY(int y) {
		stepy += y;
	}

	public int show() {
		return id;
	}

	public int getVcount() {
		return vcount;
	}

	public void setVcount(int v) {
		vcount = v;
	}

	public boolean getBrownAnimation() {
		return brownAnimation;
	}

	public void setBrownAnimation(boolean b) {
		brownAnimation = b;
	}

	public String toString() {
		return x + " " + y;
	}

	public void move() {
		changeY(stepy);
		stepy = 0;
	}

	public boolean inScene() {
		boolean scene = true;
		if (getX() > 600)
			scene = false;
		return scene;
	}

	public int getxVelocity() {
		return xVelocity;
	}

	public void setxVelocity(int xVelocity) {
		this.xVelocity = xVelocity;
	}

	public int getyVelocity() {
		return yVelocity;
	}

	public void setyVelocity(int yVelocity) {
		this.yVelocity = yVelocity;
	}
}