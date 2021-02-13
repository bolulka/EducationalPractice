package ep1;

import java.util.Objects;

public class Engine {

	private String model;
	private boolean condition = false;

	Engine() {
	};

	Engine(String model) {
		this.model = model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setCondition(boolean condition) {
		this.condition = condition;
	}

	String getCondition() {
		if (condition == false)
			return "off";
		else
			return "on";
	}

	public String getModel() {
		return model;
	}

	public String toString() {
		return "Engine model: " + model + " ,condition: " + getCondition();
	}

	@Override
	public boolean equals(Object o) {
		Engine temp = (Engine) o;
		return model.equals(temp.model) &&  condition == temp.condition;
	}

	@Override
	public int hashCode() {
		return Objects.hash(model,condition) ;
	}
}
