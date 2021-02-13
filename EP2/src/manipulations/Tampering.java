package manipulations;

import java.util.List;
import java.util.stream.Collectors;
import appliances.*;
public class Tampering {

	public static List<ElectricalAppliance> sortByPower(List<ElectricalAppliance> list) {
		List<ElectricalAppliance> sortedList = null;
		sortedList = list.stream().sorted(new PowerComparator()).collect(Collectors.toList());
		return sortedList;
	}

	public static int countPower(List<ElectricalAppliance> list) {
		int allPower = list.stream().filter(p -> p.status == true).mapToInt(p -> p.getPower()).sum();
		return allPower;
	}

	public static List<ElectricalAppliance> findByParametrs(List<ElectricalAppliance> list, int minPower, int maxPower,
			boolean status, int minUsage, int maxUsage) {
		List<ElectricalAppliance> byParametrs = list.stream()
				.filter(p -> (p.getPower() > minPower && p.getPower() < maxPower && p.status == status
						&& p.getFrequencyOfUse() > minUsage && p.getFrequencyOfUse() < maxUsage))
				.collect(Collectors.toList());
		return byParametrs;
	}

	public static void searchByParametrs(List<ElectricalAppliance> list, int minPower, int maxPower, boolean status,
			int minUsage, int maxUsage) {
		List<ElectricalAppliance> result = findByParametrs(list, minPower, maxPower, status, minUsage, maxUsage);
		System.out.println("Sorted by parametrs: power = [" + minPower + "," + maxPower + "], status: " + status
				+ " frequency of usage = [" + minUsage + "," + maxUsage + "]");
		for (ElectricalAppliance el : result) {
			System.out.println(el);
		}
	}

	public static void show(String message, List<ElectricalAppliance> list, int res) {
		if (list != null) {
			System.out.println(message);
			for (ElectricalAppliance el : list) {
				System.out.println(el);
			}
		} else
			System.out.println(message + res);
	}


}
