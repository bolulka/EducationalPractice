package appliances;
public class Fridge extends ElectricalAppliance {

	private int numberOfViewsPerDay;

	Fridge(String name, int power, boolean status, int levelOfPopularity) {
		super(name, power, status, levelOfPopularity);
	}

	public Fridge(String name, int power, boolean status, int levelOfPopularity, int numberOfViewsPerDay) {
		super(name, power, status, levelOfPopularity);
		this.numberOfViewsPerDay = numberOfViewsPerDay;
	}

	public int getNumberOfViewsPerDay() {
		return numberOfViewsPerDay;
	}

	public void setNumberOfViewsPerDay(int numberOfViewsPerDay) {
		this.numberOfViewsPerDay = numberOfViewsPerDay;
	}
	
	@Override
	public String toString() {
		return super.toString() + ", number of views per day: " + numberOfViewsPerDay;

	}

}
