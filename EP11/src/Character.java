import java.awt.*;
import java.applet.*;

public abstract class Character {
	protected int x, y, id;
	protected int width, height;

	public Character() {
		x = 0;
		y = 0;
		width = 0;
		height = 0;
		id = 0;
	}

	public Character(int id, int x, int y, int w, int h) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}

	// returns the index number of the needed image from the ArrayList
	public abstract int show();

	// implement to define how each new character moves
	public abstract void move();

	public int getId() {
		return id;
	}

	public void setId(int i) {
		id = i;
	}

	// accessor methods
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void changeX(int k) {
		x += k;
	}

	public void changeY(int k) {
		y += k;
	}

	// overridden in Fireball to remove from ArrayList
	public boolean inScene() {
		return true;
	}

	// one place for 2 characters
	public boolean equals(Character obj) {
		Character other = obj;
		if (getX() + getWidth() >= other.getX() && getX() <= other.getX() + other.getWidth()
				&& getY() + getHeight() >= other.getY() && getY() <= other.getY() + other.getHeight())
			return true;
		return false;
	}

}