package ep13;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.SAXException;

import ep13.EP13;
import ep13.SaxCounting;
import ep13.Sportcars;
import ep13.SportcarsTableModel;

public class EP13 extends JFrame {
	private JTable table;
	private SportcarsTableModel tableModel;
	private JMenuBar bar;
	private JMenu menu;
	private JMenuItem openXml;
	private JMenuItem saveXml;
	private JMenuItem deleteRows;
	private JMenuItem addRow;
	private JMenuItem saveBinary;
	private JMenuItem openBinary;
	private JMenuItem saxCalculation;
	private JMenuItem saveToHtml;
	private JCheckBoxMenuItem schema;

	public EP13() throws HeadlessException {
		super("Educational practice 13");

		setPreferredSize(new Dimension(700, 700));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JWindow window = new JWindow();
		window.getContentPane()
				.add(new JLabel("",
						new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("top_gear.png")),
						SwingConstants.CENTER));
		window.setBounds(500, 250, 700, 350);
		window.setVisible(true);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		window.setVisible(false);

		bar = new JMenuBar();
		menu = new JMenu("file");
		openXml = new JMenuItem("openXML");
		saveXml = new JMenuItem("saveXML");
		deleteRows = new JMenuItem("deleteRows");
		addRow = new JMenuItem("addRow");
		saveBinary = new JMenuItem("saveBinary");
		openBinary = new JMenuItem("openBinary");
		saxCalculation = new JMenuItem("saxCalculation");
		schema = new JCheckBoxMenuItem("use schema");
		saveToHtml = new JMenuItem("saveToHtml");
		schema.setState(false);

		menu.add(openXml);
		menu.add(saveXml);
		menu.addSeparator();
		menu.add(addRow);
		menu.add(deleteRows);
		menu.addSeparator();
		menu.add(openBinary);
		menu.add(saveBinary);
		menu.addSeparator();
		menu.add(saxCalculation);
		menu.addSeparator();
		menu.add(schema);
		menu.addSeparator();
		menu.add(saveToHtml);
		bar.add(menu);

		table = new JTable();
		tableModel = new SportcarsTableModel();
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(tableModel);
		JScrollPane pane = new JScrollPane(table);
		pane.setVisible(true);
		add(pane);

		setJMenuBar(bar);

		openXml.addActionListener(e -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
			int option = chooser.showOpenDialog(this);
			if (option == JFileChooser.APPROVE_OPTION) {
				tableModel = new SportcarsTableModel(chooser.getSelectedFile(), schema.getState());
				table.setModel(tableModel);
			}
		});

		saveXml.addActionListener(e -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
			int option = chooser.showSaveDialog(this);
			if (option == JFileChooser.APPROVE_OPTION) {
				try {
					tableModel.saveXml(chooser.getSelectedFile());
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(this, exc.getMessage());
				}
			}
		});

		addRow.addActionListener(e -> {
			tableModel.getCars().add(new Sportcars());
			tableModel.fireTableDataChanged();
			table.repaint();
			table.revalidate();
		});

		deleteRows.addActionListener(e -> {
			tableModel.deleteRows(table.getSelectedRows());
			table.repaint();
			table.revalidate();
		});

		saveBinary.addActionListener(e -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
			int option = chooser.showSaveDialog(this);
			if (option == JFileChooser.APPROVE_OPTION) {
				try {
					tableModel.saveBinary(chooser.getSelectedFile());
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(this, exc.getMessage());
				}
			}
		});

		openBinary.addActionListener(e -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
			int option = chooser.showSaveDialog(this);
			if (option == JFileChooser.APPROVE_OPTION) {
				try {
					table.setModel(tableModel.getBinary(chooser.getSelectedFile()));
					table.updateUI();
				} catch (IOException | ClassNotFoundException exc) {
					JOptionPane.showMessageDialog(this, exc.getMessage());
				}
			}
		});
		saxCalculation.addActionListener(e -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
			int option = chooser.showOpenDialog(this);
			if (option == JFileChooser.APPROVE_OPTION) {
				try {
					SAXParserFactory factory = SAXParserFactory.newInstance();
					SAXParser parser = factory.newSAXParser();
					SaxCounting handler = new SaxCounting();
					parser.parse(chooser.getSelectedFile(), handler);
					StringBuilder str = new StringBuilder();
					str.append("The oldest car: " + handler.getOldestCar() + "\n")
							.append("The newest: " + handler.getNewestCar() + "\n")
							.append("Average price: " + handler.getAveragePrice() + "\n")
							.append("Min price: " + handler.getMinPrice() + "\n")
							.append("Max price: " + handler.getMaxPrice());
					JOptionPane.showMessageDialog(null, str.toString());
				} catch (IOException | SAXException | ParserConfigurationException exc) {
					JOptionPane.showMessageDialog(this, exc.getMessage());
				}
			}
		});

		saveToHtml.addActionListener(e -> {
			try {
				TransformerFactory factory = TransformerFactory.newInstance();
				Transformer transformer = factory.newTransformer(new StreamSource(
						Thread.currentThread().getContextClassLoader().getResource("html.xsl").openStream()));
				transformer.transform(
						new StreamSource(new InputStreamReader(
								new ByteArrayInputStream(tableModel.toXmlString().getBytes(Charset.forName("UTF-8"))))),
						new StreamResult(new File("lab13.html")));
			} catch (TransformerException | IOException exc) {
				JOptionPane.showMessageDialog(this, exc.getMessage());
			}
		});

		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		new EP13();
	}
}
