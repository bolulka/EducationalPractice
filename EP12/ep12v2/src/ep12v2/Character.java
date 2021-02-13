package ep12v2;

import java.io.Serializable;
import java.util.StringJoiner;

public class Character implements Serializable {

	private int year;
	private int rating;
	private String happyEnd;
	private String mainCharacter;
	private String name;
	private String cartoon;

	public Character() {
		super();
	}

	public Character(String name, String cartoon, int year, String mainCharacter, String happyEnd, int rating) {
		super();
		this.year = year;
		this.happyEnd = happyEnd;
		this.mainCharacter = mainCharacter;
		this.name = name;
		this.cartoon = cartoon;
		this.rating = rating;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getHappyEnd() {
		return happyEnd;
	}

	public void setHappyEnd(String happyEnd) {
		this.happyEnd = happyEnd;
	}

	public String getMainCharacter() {
		return mainCharacter;
	}

	public void setMainCharacter(String mainCharacter) {
		this.mainCharacter = mainCharacter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCartoon() {
		return cartoon;
	}

	public void setCartoon(String creator) {
		this.cartoon = creator;
	}

	public String convertToXml() {
		StringBuilder str = new StringBuilder();
		return str.append("\t<character name=\"" + name + "\" cartoon=\"" + cartoon + "\">" + "\n")
				.append("\t\t<year>" + year + "</year>" + "\n")
				.append("\t\t<mainCharacter>" + mainCharacter + "</mainCharacter>" + "\n")
				.append("\t\t<happyEnd>" + happyEnd + "</happyEnd>" + "\n")
				.append("\t\t<rating>" + rating + "</rating>" + "\n").append("\t</character>" + "\n").toString();
	}
}