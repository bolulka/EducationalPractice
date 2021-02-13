package ep3;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Task2 extends JPanel {

	private List<Country> countries = new ArrayList<>();
	boolean selected = false;
	private JTable table;
	private DefaultTableModel tableModel;
	private String[] namesOfColumns = { "Country", "Description", "Price", "Activate trip" };

	private JTextField country = new JTextField();
	private JTextField description = new JTextField();
	private JTextField price = new JTextField();
	JTextField summary = new JTextField();

	public Task2(List<Country> countries) {
		this.countries = countries;
		JPanel panelForTextFields = new JPanel(new GridLayout(3, 4));
		JPanel panelForSum = new JPanel(new GridLayout(3, 1));
		// JButton calcSum = createCalcSumButton();
		JButton addButton = createAddButton();

		panelForTextFields.setPreferredSize(new Dimension(300, 90));
		panelForTextFields.add(new JLabel("Country:"));
		panelForTextFields.add(new JLabel("Description:"));
		panelForTextFields.add(new JLabel("Price:"));
		panelForTextFields.add(country);
		panelForTextFields.add(description);
		panelForTextFields.add(price);
		panelForTextFields.add(new JLabel(""));
		panelForTextFields.add(addButton);

		JPanel panel2 = new JPanel();
		panel2.setPreferredSize(new Dimension(1000, 600));
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVisible(true);
		scrollPane.setPreferredSize(new Dimension(400, 300));
		tableModel = new DefaultTableModel(namesOfColumns, 0) {
			public Class getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}
			public boolean isCellEditable(int row, int column) {
				if (row == 0)
					return false;
				return super.isCellEditable(row, column);
			}
		};
		tableModel.addTableModelListener(new TableModelListener() {
			boolean hasChanged = false;
			@Override
			public void tableChanged(TableModelEvent e) {
				if (!hasChanged) {
					int sum = 0;
					for (int i = 1; i < tableModel.getRowCount(); i++) {
						if ((boolean) tableModel.getValueAt(i, 3)) {
							sum += (int) tableModel.getValueAt(i, 2);
						}
					}
					hasChanged = true;
					tableModel.setValueAt(sum, 0, 2);
					hasChanged = false;
				}
			}

		});
		table.setRowHeight(80);
		table.setModel(tableModel);
		// this.add(table);
		tableModel.addRow(new Object[] { new ImageIcon(), "Summa : ", 0, false });
		this.setSize(new Dimension(600, 600));
		this.add(panelForTextFields);
		this.add(panelForSum);
		this.add(scrollPane);
	}

	private JButton createAddButton() {
		JButton addButton = new JButton("Add");
		addButton.addActionListener(e -> {
			try {
				int priceField = Integer.valueOf(price.getText());
				String countryName = country.getText();
				if (countries.stream().anyMatch(c -> c.getName().equals(countryName))) {
					JOptionPane.showMessageDialog(this, "Already exists");
				} else {
					Country cntr = new Country(countryName);
					countries.add(cntr);
					Object[] obj = new Object[] { cntr.getFlag(), description.getText(), priceField, false };
					tableModel.insertRow(tableModel.getRowCount(), obj);
				}
			} catch (NumberFormatException exc) {
				JOptionPane.showMessageDialog(null, exc.getMessage());
			}
		});
		return addButton;
	}

	public void update() {
		for (Country country : countries) {
			tableModel.insertRow(tableModel.getRowCount(), (new Object[] { country.getFlag(),
					country.getName() + " " + country.getCapital(), country.calcPrice(), false }));
		}
	}
}