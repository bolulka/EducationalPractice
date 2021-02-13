package ep1;

import java.util.ArrayList;
import java.util.List;

public class EP1 {

	public static void main(String[] args) {

		List<Engine> engines = new ArrayList<Engine>();
		List<Wings> wings = new ArrayList<Wings>();
		List<Chassis> chassis = new ArrayList<Chassis>();

		Engine engine1 = new Engine("turbocondition");
		engine1.setCondition(true);
		Engine engine2 = new Engine("turbocondition");
		engine2.setCondition(false);
		Engine engine3 = new Engine("turbocondition");
		engine2.setCondition(false);
		Engine engine4 = new Engine("turbocondition");
		engine4.setCondition(true);
		engines.add(engine1);
		engines.add(engine2);
		engines.add(engine3);
		engines.add(engine4);

		Wings wing1 = new Wings();
		wing1.setWing(25);
		Wings wing2 = new Wings();
		wing2.setWing(25);
		wings.add(wing1);
		wings.add(wing2);

		for (int i = 0; i < 16; i++) {
			if (i % 2 == 0)
				chassis.add(new Chassis(true));
			else
				chassis.add(new Chassis(false));
		}

		if (engines.size() % 2 == 0) {
			if (wings.size() == 2) {
				if (wings.get(0).getLength() == wings.get(1).getLength()) {
					if ((chassis.size() < 20 || chassis.size() > 8) && (chassis.size() % 2 == 0)) {
						Plane plane = new Plane("FAMCS", engines, wings, chassis);
						plane.setRoute("Minsk", "Tokio");
						System.out.println(plane.toString());
						plane.showRoute();
						plane.infoAboutPlane();
						plane.toFly();
					} else
						System.out.println("Wrong number of chassis");
				} else
					System.out.println("Wings must have same length!");
			} else
				System.out.println("Planes have 2 wings!");
		} else
			System.out.println("There must be even number of engines!!!");
	}
}
