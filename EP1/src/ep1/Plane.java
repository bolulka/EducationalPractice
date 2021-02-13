package ep1;

import java.util.List;
import java.util.Objects;

public class Plane {

	private String name;
	private String route;
	private List<Engine> engine;
	private List<Wings> wings;
	private List<Chassis> chassis;

	Plane(String name, List<Engine> eng, List<Wings> wings, List<Chassis> chassis) {
		this.name = name;
		this.chassis = chassis;
		this.engine = eng;
		this.wings = wings;
	};

	void setRoute(String startingPoint, String endpoint) {
		this.route = startingPoint + " - " + endpoint;
	}

	void showRoute() {
		System.out.println("Route: " + route);
	}

	void toFly() {
		for (Chassis chassis : this.chassis) {
			chassis.setCondition(true);
		}
		for (Engine engine : this.engine) {
			engine.setCondition(true);
		}
		for (int i = 0; i < chassis.size(); i++) {
			System.out.println("Chassis " + (i + 1) + ": " + chassis.get(i).getCondition());
		}
		for (int i = 0; i < engine.size(); i++) {
			System.out.println("Engine " + (i + 1) + ": " + engine.get(i).getCondition());
		}
		System.out.println("Data about the flight: ");
		System.out.println("Speed: 550 km/h");
		System.out.println("Flight altitude: 10 km");
		System.out.println("The outside temperature: -45°C");
		showRoute();
	}
	
	void infoAboutPlane() {
		System.out.println("Info about plane:");
		System.out.println("Name: " + name);
		System.out.println("Info about engines: ");
		for(Engine engine : engine) {
			System.out.println(engine.toString());
		}
		System.out.println("Info about chassis: ");
		for(Chassis chassis : chassis) {
			System.out.println(chassis.toString());
		}
		System.out.println("Info about wings: ");
		for(Wings wings : wings) {
			System.out.println(wings.toString());
		}
	}

	public String toString() {
		return "Name : " + name + " ,engine count: " + engine.size() + " , wings count: " + wings.size()
				+ " ,chassis count: " + chassis.size();
	}

	@Override
	public boolean equals(Object o) {
		Plane temp = (Plane) o;
		return name.equals(temp.name) && engine.equals(temp.engine) && chassis.equals(temp.chassis)
				&& wings.equals(temp.wings) && route.equals(temp.route);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name,route,engine,wings,chassis); 
	}
}
