package appliances;
public abstract class ElectricalAppliance {

	String name;
	int power;
	public boolean status;
	int frequencyOfUse;

	ElectricalAppliance(String name, int power, boolean status, int frequencyOfUse) {
		this.name = name;
		this.power = power;
		this.status = status;
		this.frequencyOfUse = frequencyOfUse;
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	public int getPower() {
		return this.power;
	}

	void setPower(int power) {
		this.power = power;
	}

	String getStatus() {
		if (this.status == true)
			return "on";
		else
			return "off";
	}

	void setStatus(boolean status) {
		this.status = status;
	}

	public int getFrequencyOfUse() {
		return this.frequencyOfUse;
	}

	void setFrequencyOfUse(int frequencyOfUse) {
		this.frequencyOfUse = frequencyOfUse;
	}
	
	

	public String toString() {
		return "Name: " + name+ ", power: " + power+", status: " + this.getStatus() + ", frequency of use: " + frequencyOfUse;
	}
	
	public int compare(ElectricalAppliance o) {
		
		return (int) (power - o.power == 0 ? name.compareTo(o.name) :(frequencyOfUse - o.frequencyOfUse) );
		
	}
	
	

}
