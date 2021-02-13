package ep13;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class SportcarsTableModel extends AbstractTableModel {

	private List<Sportcars> cars = new ArrayList<>();
	private final static String[] COLUMN_NAMES = { "brend", "model", "power", "year", "price" };
	private boolean valid;

	public SportcarsTableModel() {

	}

	public SportcarsTableModel(File xmlFile, boolean validate) {
		cars.clear();

		int year;
		int power;
		int price;
		String model;
		String brend;
		try {
			if (validate) {
				SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
				Schema schema = factory
						.newSchema(Thread.currentThread().getContextClassLoader().getResource("schema.xsd"));
				Validator validator = schema.newValidator();
				validator.validate(new StreamSource(xmlFile));
				valid = true;
			}
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(xmlFile);
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("sportcar");
			int size = nodeList.getLength();
			for (int i = 0; i < size; i++) {
				Node node = nodeList.item(i);
				if (Node.ELEMENT_NODE == node.getNodeType()) {
					Element element = (Element) node;
					brend = element.getAttribute("brend");
					model = element.getAttribute("model");
					power = Integer.valueOf(element.getElementsByTagName("power").item(0).getTextContent());
					year = Integer.valueOf(element.getElementsByTagName("year").item(0).getTextContent());
					price = Integer.valueOf(element.getElementsByTagName("price").item(0).getTextContent());
					cars.add(new Sportcars(brend, model, power, year, price));
				}
			}
		} catch (NumberFormatException | SAXException | ParserConfigurationException | IOException exc) {
			JOptionPane.showMessageDialog(null, exc.getMessage());
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex < 2) {
			return String.class;
		} else
			return Integer.class;
	}

	@Override
	public int getRowCount() {
		return cars.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
			return cars.get(row).getBrend();
		case 1:
			return cars.get(row).getModel();
		case 2:
			return cars.get(row).getPower();
		case 3:
			return cars.get(row).getYear();
		default:
			return cars.get(row).getPrice();
		}
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		switch (column) {
		case 0:
			cars.get(row).setBrend((String) aValue);
			break;
		case 1:
			cars.get(row).setModel((String) aValue);
			break;
		case 2:
			cars.get(row).setPower((Integer) aValue);
			break;
		case 3:
			cars.get(row).setYear((Integer) aValue);
			break;
		case 4:
			cars.get(row).setPrice((Integer) aValue);
			break;
		}
	}

	public void deleteRows(int[] rows) {
		for (int i = rows.length - 1; i >= 0; --i)
			if (rows[i] < cars.size())
				cars.remove(rows[i]);
	}

	public String toXmlString() {
		StringBuilder str = new StringBuilder();
		str.append("<sportcars>");
		for (Sportcars elem : cars) {
			str.append(elem.convertToXml());
		}
		str.append("</sportcars>");
		return str.toString();
	}

	public void saveXml(File selectedFile) throws IOException {
		FileWriter writer = new FileWriter(selectedFile);
		writer.write(toXmlString());
		writer.flush();
	}

	public List<Sportcars> getCars() {
		return cars;
	}

	public void setCars(List<Sportcars> cars) {
		this.cars = cars;
	}

	public void saveBinary(File selectedFile) throws IOException {
		ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(selectedFile));
		stream.writeObject(cars);
		stream.close();
	}

	public SportcarsTableModel getBinary(File selectedFile) throws IOException, ClassNotFoundException {
		ObjectInputStream stream = new ObjectInputStream(new FileInputStream(selectedFile));
		cars.clear();
		SportcarsTableModel model = new SportcarsTableModel();
		cars = (List<Sportcars>) stream.readObject();
		model.setCars(cars);
		stream.close();
		return model;
	}
}