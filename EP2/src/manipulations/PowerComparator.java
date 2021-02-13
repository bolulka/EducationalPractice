package manipulations;
import java.util.Comparator;
import appliances.*;

public class PowerComparator implements Comparator<ElectricalAppliance> {

	@Override
	public int compare(ElectricalAppliance o1, ElectricalAppliance o2) {
		return o1.getPower() < o2.getPower() ? -1: ((o1.getPower() == o2.getPower()) ? 0 : 1);
	}
}
