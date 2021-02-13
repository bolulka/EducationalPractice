package appliances;
public class MicrowaveOven extends ElectricalAppliance {

	public MicrowaveOven(String name, int power, boolean status, int levelOfPopularity) {
		super(name, power, status, levelOfPopularity);
	}

	@Override
	public String toString() {
		return super.toString() ;

	}
}
