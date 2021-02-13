package ep1;

import java.util.Objects;

public class Chassis {

	private boolean condition;

	Chassis() {
	};
	
	Chassis(boolean condition){
		this.condition = condition;
	}

	String getCondition() {
		if(condition==false) return "elevated";
		else return "omitted";
		}
	
	void setCondition(boolean condition) {
		this.condition = condition;
	}

	public String toString() {
		return "Chassis condition: " + getCondition();
	}

	@Override
	public boolean equals(Object o) {
		Chassis temp = (Chassis) o;
		return condition == temp.condition;
	}

	@Override
	public int hashCode() {
		return Objects.hash(condition) ;
	}
}
