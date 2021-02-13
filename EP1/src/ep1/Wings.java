package ep1;

import java.util.List;

public class Wings {

	private int length;

	Wings() {
	};

	Wings(int length) {
		this.length = length;
	}


	void setWing(int length) {
		if (length < 5 || length > 30)
			System.out.println("Incorrent data");
		else
			this.length = length;
	}
	
	public int getLength() {
		return this.length;
	}

	public String toString() {
		return "Wingspan: " + length;
	}

	@Override
	public boolean equals(Object o) {
		Wings temp = (Wings) o;
		return length == temp.length;
	}

	@Override
	public int hashCode() {
		return length;
	}
}
