package ep2;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import manipulations.*;
import appliances.*;
import reading.*;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class EP2 {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {

		List<ElectricalAppliance> list = new ArrayList<ElectricalAppliance>();
		File file = new File("src//info");
		new ReadingFromFile(list, file);

		List<ElectricalAppliance> sortedList = Tampering.sortByPower(list);
		int allPower = Tampering.countPower(list);

		Tampering.show("All electrical appliances: ", list, 0);
		Tampering.show("Sorted by power: ", sortedList, 0);
		Tampering.show("Summary power: ", null, allPower);
		Tampering.searchByParametrs(list, 150, 300, false, 3, 10);

	}
}
