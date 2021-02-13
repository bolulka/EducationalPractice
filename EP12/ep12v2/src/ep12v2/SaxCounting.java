package ep12v2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class SaxCounting extends DefaultHandler {
	private int oldestCartoon = 3000;
	private int newestCartoon = 2000;
	private int size = 0;
	private int maxRate = 0;
	private int minRate = Integer.MAX_VALUE / 10;
	private int averageRate = 0;
	private String lastElementName;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (qName.equals("characters")) {
			size = 0;
			maxRate = 0;
			averageRate = 0;
			minRate = Integer.MAX_VALUE / 10;
		}
		if (qName.equals("character")) {
			size++;
		} else {
			lastElementName = qName;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if (qName.equals("characters")) {
			averageRate /= size;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String information = new String(ch, start, length);

		information = information.replace("\n", "").trim();

		if (!information.isEmpty()) {
			if (lastElementName.equals("year")) {
				if (oldestCartoon == 3000) {
					oldestCartoon = Integer.valueOf(information);
				}
				if (newestCartoon == 2000) {
					newestCartoon = Integer.valueOf(information);
				}
				oldestCartoon = min(oldestCartoon, Integer.valueOf(information));
				newestCartoon = max(newestCartoon, Integer.valueOf(information));
			}

			if (lastElementName.equals("rating")) {
				if (maxRate == 0) {
					maxRate = Integer.valueOf(information);
				}
				if (minRate == Integer.MAX_VALUE / 2) {
					minRate = Integer.valueOf(information);
				}
				minRate = min(minRate, Integer.valueOf(information));
				maxRate = max(maxRate, Integer.valueOf(information));
				averageRate += Integer.valueOf(information);
			}
		}
	}

	public int getOldestCartoon() {
		return oldestCartoon;
	}

	public void setOldestCartoon(int oldestCharacter) {
		this.oldestCartoon = oldestCharacter;
	}

	public int getNewestCartoon() {
		return newestCartoon;
	}

	public void setNewestCartoon(int newestCharacter) {
		this.newestCartoon = newestCharacter;
	}

	public int getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(int maxRate) {
		this.maxRate = maxRate;
	}

	public int getMinRate() {
		return minRate;
	}

	public void setMinRate(int minRate) {
		this.minRate = minRate;
	}

	public int getAverageRate() {
		return averageRate;
	}

	public void setAverageRate(int averageRate) {
		this.averageRate = averageRate;
	}
}