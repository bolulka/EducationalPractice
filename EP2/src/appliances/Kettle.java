package appliances;
public class Kettle extends ElectricalAppliance {

	private int capacity;
	
	Kettle(String name, int power, boolean status, int levelOfPopularity) {
		super(name, power, status, levelOfPopularity);
	}

	public Kettle(String name, int power, boolean status, int levelOfPopularity, int capacity) {
		super(name, power, status, levelOfPopularity);
		this.capacity = capacity;
	}

	
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return super.toString() + ", capacity: " + capacity;

	}
}
