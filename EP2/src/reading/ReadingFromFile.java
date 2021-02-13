package reading;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import appliances.*;



public class ReadingFromFile {

	public ReadingFromFile(List<ElectricalAppliance> list, File file)
			throws SAXException, IOException, ParserConfigurationException {

		list.clear();
		DocumentBuilderFactory products = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = products.newDocumentBuilder();
		Document document = builder.parse(file);

		NodeList sladElements = document.getDocumentElement().getElementsByTagName("slad");
		for (int i = 0; i < sladElements.getLength(); i++) {
			Node product = sladElements.item(i);
			NamedNodeMap attributes = product.getAttributes();
			list.add(new Slad(String.valueOf(attributes.getNamedItem("name").getNodeValue()),
					Integer.parseInt(attributes.getNamedItem("power").getNodeValue()),
					Boolean.valueOf(attributes.getNamedItem("status").getNodeValue()),
					Integer.parseInt(attributes.getNamedItem("usage").getNodeValue()),
					Integer.parseInt(attributes.getNamedItem("burners").getNodeValue())));
		}

		NodeList kettleElements = document.getDocumentElement().getElementsByTagName("kettle");
		for (int i = 0; i < kettleElements.getLength(); i++) {
			Node product = kettleElements.item(i);
			NamedNodeMap attributes = product.getAttributes();
			list.add(new Kettle(String.valueOf(attributes.getNamedItem("name").getNodeValue()),
					Integer.parseInt(attributes.getNamedItem("power").getNodeValue()),
					Boolean.valueOf(attributes.getNamedItem("status").getNodeValue()),
					Integer.parseInt(attributes.getNamedItem("usage").getNodeValue()),
					Integer.parseInt(attributes.getNamedItem("capacity").getNodeValue())));
		}
		NodeList ovenElements = document.getDocumentElement().getElementsByTagName("oven");
		for (int i = 0; i < ovenElements.getLength(); i++) {
			Node product = ovenElements.item(i);
			NamedNodeMap attributes = product.getAttributes();
			list.add(new MicrowaveOven(String.valueOf(attributes.getNamedItem("name").getNodeValue()),
					Integer.parseInt(attributes.getNamedItem("power").getNodeValue()),
					Boolean.valueOf(attributes.getNamedItem("status").getNodeValue()),
					Integer.parseInt(attributes.getNamedItem("usage").getNodeValue())));
		}
		NodeList fridgeElements = document.getDocumentElement().getElementsByTagName("fridge");
		for (int i = 0; i < fridgeElements.getLength(); i++) {
			Node product = fridgeElements.item(i);
			NamedNodeMap attributes = product.getAttributes();
			list.add(new Fridge(String.valueOf(attributes.getNamedItem("name").getNodeValue()),
					Integer.parseInt(attributes.getNamedItem("power").getNodeValue()),
					Boolean.valueOf(attributes.getNamedItem("status").getNodeValue()),
					Integer.parseInt(attributes.getNamedItem("usage").getNodeValue()),
					Integer.parseInt(attributes.getNamedItem("numberOfViews").getNodeValue())));
		}
	}

}
