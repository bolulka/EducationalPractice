package ep13;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class SaxCounting extends DefaultHandler {
	private int oldestCar = 3000;
	private int newestCar = 2000;
	private int size = 0;
	private int maxPrice = 0;
	private int minPrice = Integer.MAX_VALUE / 10;
	private int averagePrice = 0;
	private String lastElementName;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (qName.equals("sportcars")) {
			size = 0;
			maxPrice = 0;
			averagePrice = 0;
			minPrice = Integer.MAX_VALUE / 10;
		}
		if (qName.equals("sportcar")) {
			size++;
		} else {
			lastElementName = qName;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if (qName.equals("sportcars")) {
			averagePrice /= size;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String information = new String(ch, start, length);

		information = information.replace("\n", "").trim();

		if (!information.isEmpty()) {
			if (lastElementName.equals("year")) {
				if (oldestCar == 3000) {
					oldestCar = Integer.valueOf(information);
				}
				if (newestCar == 2000) {
					newestCar = Integer.valueOf(information);
				}
				oldestCar = min(oldestCar, Integer.valueOf(information));
				newestCar = max(newestCar, Integer.valueOf(information));
			}

			if (lastElementName.equals("price")) {
				if (maxPrice == 0) {
					maxPrice = Integer.valueOf(information);
				}
				if (minPrice == Integer.MAX_VALUE / 2) {
					minPrice = Integer.valueOf(information);
				}
				minPrice = min(minPrice, Integer.valueOf(information));
				maxPrice = max(maxPrice, Integer.valueOf(information));
				averagePrice += Integer.valueOf(information);
			}
		}
	}

	public int getOldestCar() {
		return oldestCar;
	}

	public void setOldestCar(int oldestCar) {
		this.oldestCar = oldestCar;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}

	public int getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

	public int getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(int averagePrice) {
		this.averagePrice = averagePrice;
	}

	public int getNewestCar() {
		return newestCar;
	}

	public void setNewestCar(int newestCar) {
		this.newestCar = newestCar;
	}
}