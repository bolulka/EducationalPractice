package ep12v2;

import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class EP12 extends JFrame {
	private JTable table;
	private CharactersTableModel tableModel;
	private JMenuBar bar;
	private JMenu menu;
	private JMenuItem openXml;
	private JMenuItem saveXml;
	private JMenuItem deleteRows;
	private JMenuItem addRow;
	private JMenuItem saveBinary;
	private JMenuItem openBinary;
	private JMenuItem saxCalculation;

	public EP12() throws HeadlessException {
		super("Educational practice 12");

		setPreferredSize(new Dimension(700, 700));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JWindow window = new JWindow();
		window.getContentPane()
				.add(new JLabel("",
						new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("ep12.jpg")),
						SwingConstants.CENTER));
		window.setBounds(500, 250, 1000, 500);
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

		bar.add(menu);

		table = new JTable();
		tableModel = new CharactersTableModel();
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
				tableModel = new CharactersTableModel(chooser.getSelectedFile());
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
			tableModel.getChacacters().add(new Character());
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
					str.append("The oldest chartoon: " + handler.getOldestCartoon() + "\n")
							.append("The newest: " + handler.getNewestCartoon() + "\n")
							.append("Average rate: " + handler.getAverageRate() + "\n")
							.append("Min rate: " + handler.getMinRate() + "\n")
							.append("Max rate: " + handler.getMaxRate());
					JOptionPane.showMessageDialog(null, str.toString());
				} catch (IOException | SAXException | ParserConfigurationException exc) {
					JOptionPane.showMessageDialog(this, exc.getMessage());
				}
			}
		});

		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		new EP12();
	}
}
