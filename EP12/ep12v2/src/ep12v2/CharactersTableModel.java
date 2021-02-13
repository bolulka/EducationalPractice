package ep12v2;

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
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CharactersTableModel extends AbstractTableModel {

	private List<Character> characters = new ArrayList<>();
	private final static String[] COLUMN_NAMES = { "Name", "Ñartoon", "Year", "Role", "Happy end?", "Rating" };

	public CharactersTableModel() {

	}

	public CharactersTableModel(File xmlFile) {
		characters.clear();

		int year;
		String happyEnd;
		String mainCharacter;
		String name;
		String cartoon;
		int rating;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(xmlFile);
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("character");
			int size = nodeList.getLength();
			for (int i = 0; i < size; i++) {
				Node node = nodeList.item(i);
				if (Node.ELEMENT_NODE == node.getNodeType()) {
					Element element = (Element) node;
					name = element.getAttribute("name");
					cartoon = element.getAttribute("cartoon");
					year = Integer.valueOf(element.getElementsByTagName("year").item(0).getTextContent());
					mainCharacter = String
							.valueOf(element.getElementsByTagName("mainCharacter").item(0).getTextContent());
					happyEnd = String.valueOf(element.getElementsByTagName("happyEnd").item(0).getTextContent());
					rating = Integer.valueOf(element.getElementsByTagName("rating").item(0).getTextContent());
					characters.add(new Character(name, cartoon, year, mainCharacter, happyEnd, rating));
				}
			}
		} catch (NumberFormatException | SAXException | ParserConfigurationException | IOException exc) {
			JOptionPane.showMessageDialog(null, exc.getMessage());
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 2 || columnIndex == 5) {
			return Integer.class;
		} else
			return String.class;

	}

	@Override
	public int getRowCount() {
		return characters.size();
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
			return characters.get(row).getName();
		case 1:
			return characters.get(row).getCartoon();
		case 2:
			return characters.get(row).getYear();
		case 3:
			return characters.get(row).getMainCharacter();
		case 4:
			return characters.get(row).getHappyEnd();

		default:
			return characters.get(row).getRating();
		}

	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		switch (column) {
		case 0:
			characters.get(row).setName((String) aValue);
			break;
		case 1:
			characters.get(row).setCartoon((String) aValue);
			break;
		case 2:
			characters.get(row).setYear((Integer) aValue);
			break;
		case 3:
			characters.get(row).setMainCharacter((String) aValue);
			break;
		case 4:
			characters.get(row).setHappyEnd((String) aValue);
			break;
		case 5:
			characters.get(row).setRating((Integer) aValue);
			break;
		}
	}

	public void deleteRows(int[] rows) {
		for (int i = rows.length - 1; i >= 0; --i)
			if (rows[i] < characters.size())
				characters.remove(rows[i]);
	}

	public String toXmlString() {
		StringBuilder str = new StringBuilder();
		str.append("<characters>");
		for (Character elem : characters) {
			str.append(elem.convertToXml());
		}
		str.append("</characters>");
		return str.toString();
	}

	public void saveXml(File selectedFile) throws IOException {
		FileWriter writer = new FileWriter(selectedFile);
		writer.write(toXmlString());
		writer.flush();
	}

	public List<Character> getChacacters() {
		return characters;
	}

	public void setCharacters(List<Character> character) {
		this.characters = character;
	}

	public void saveBinary(File selectedFile) throws IOException {
		ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(selectedFile));
		stream.writeObject(characters);
		stream.close();
	}

	public CharactersTableModel getBinary(File selectedFile) throws IOException, ClassNotFoundException {
		ObjectInputStream stream = new ObjectInputStream(new FileInputStream(selectedFile));
		characters.clear();
		CharactersTableModel model = new CharactersTableModel();
		characters = (List<Character>) stream.readObject();
		model.setCharacters(characters);
		stream.close();
		return model;
	}
}