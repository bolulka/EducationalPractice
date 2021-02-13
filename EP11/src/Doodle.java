import java.awt.*;
import java.applet.*;

public class Doodle extends Character {
	private int stepy;
	private int id;
	private int yVelocity;
	private int xVelocity;
	private int count;
	private int moveSide;

	public Doodle(int id, int x, int y, int w, int h) {
		super(id, x, y, w, h);
		yVelocity = 0;
		// L and R velocity
		xVelocity = 10;
		count = 0;
	}

	public int getVelocity() {
		return yVelocity;
	}

	public void setVelocity(int velocity) {
		this.yVelocity = velocity;
	}

	public void setMoveSide(int side) {
		moveSide = side;
	}

	public int show() {
		return id;
	}

	public String toString() {
		return "Doodle x: " + x + "|y: " + y;
	}

	public void move() {
		int acceleration = 1;

		if (yVelocity != 0) {
			if (yVelocity <= -1)
				acceleration = 1;
			if (yVelocity >= 1)
				acceleration = 1;
			if (count > 2) {
				if (yVelocity < 20) {
					if (yVelocity + acceleration == 0) {
						yVelocity = 0;
					}
					yVelocity = yVelocity + acceleration;
				}
				count = 0;
			}
			count++;
			changeY(yVelocity);
		}

		switch (moveSide) {
		case -1:
			changeX(-xVelocity);
			break;
		case 0:
			changeX(0);
			break;
		case 1:
			changeX(xVelocity);
			break;
		}

		checkLRBounds();
	}

	public boolean checkHitPlatform(Object obj) {
		Platform other = (Platform) obj;

		if (getX() + getWidth() >= other.getX() && getX() <= other.getX() + other.getWidth()
				&& getY() + getHeight() >= other.getY() && getY() + getHeight() <= other.getY() + other.getHeight())
			return true;
		return false;
	}

	public void checkLRBounds() {
		if (getX() > 445)
			setX(-60);

		if (getX() < -60)
			setX(445);

		if (getY() < 10)
			setY(10);
	}

}