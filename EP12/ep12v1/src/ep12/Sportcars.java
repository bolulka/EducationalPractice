package ep12;

import java.io.Serializable;
import java.util.StringJoiner;

public class Sportcars implements Serializable {

	private int year;
	private int power;
	private int price;
	private String model;
	private String brend;

	public Sportcars() {
		super();
	}

	public Sportcars(String brend, String model, int power, int year, int price) {
		super();
		this.year = year;
		this.power = power;
		this.price = price;
		this.model = model;
		this.brend = brend;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBrend() {
		return brend;
	}

	public void setBrend(String brend) {
		this.brend = brend;
	}

	public String convertToXml() {
		StringBuilder str = new StringBuilder();
		return str.append("\t<sportcar brend=\"" + brend + "\" model=\"" + model + "\">"+"\n")
				.append("\t\t<power>" + power + "</power>"+"\n").append("\t\t<year>" + year + "</year>"+"\n")
				.append("\t\t<price>" + price + "</price>"+"\n").append("\t</sportcar>"+"\n").toString();
	}

}