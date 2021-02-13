package appliances;
public class Slad extends ElectricalAppliance {

	private int numberOfBurners;

	public Slad(String name, int power, boolean status, int levelOfPopularity, int numberOfBurners) {
		super(name, power, status, levelOfPopularity);
		this.numberOfBurners = numberOfBurners;
	}

	Slad(String name, int power, boolean status, int levelOfPopularity) {
		super(name, power, status, levelOfPopularity);
	}

	public int getNumberOfBurners() {
		return numberOfBurners;
	}

	public void setNumberOfBurners(int numberOfBurners) {
		this.numberOfBurners = numberOfBurners;
	}

	@Override
	public String toString() {
		return super.toString() + ", number of burners: " + numberOfBurners;

	}

}
