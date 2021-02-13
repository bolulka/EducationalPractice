package ep9;

import java.awt.*;
import java.text.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Objects;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

public class ExcelTable extends JFrame {

	public static int rowCount = 1000;
	public static int columnCount = 1000;
	public static int columnHeaderHeight = 30;
	public static int rowHeaderWidth = 70;
	public static int cellWidth = 150;
	JTable table;
	DefaultTableModel defaultTableModel;
	DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

	public ExcelTable() {
		super("EP 9");
		setSize(1300, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		ListModel listModel = new AbstractListModel() {
			@Override
			public int getSize() {
				return rowCount;
			}

			@Override
			public Object getElementAt(int index) {
				return String.valueOf(index + 1);
			}
		};

		defaultTableModel = new DefaultTableModel(rowCount, columnCount);
		table = new JTable(defaultTableModel) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component comp = super.prepareRenderer(renderer, row, col);
				setCellSelectionEnabled(true);
				comp.setFont(new Font("Sans", Font.PLAIN, 20));
				return comp;
			}

			@Override
			public TableCellRenderer getCellRenderer(int row, int column) {
				return new ExcelTableCellRenderer();
			}
		};

		table.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent arg) {
				table.repaint();
			}
		});

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(30);
		table.getTableHeader().setFont(new Font("Sans", Font.PLAIN, 20));
		table.setFont(new Font("Sans", Font.PLAIN, 20));

		for (int i = 0; i < columnCount; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(cellWidth);
		}
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setCellSelectionEnabled(true);

		JList rowHeader = new JList(listModel);
		rowHeader.setFixedCellHeight(table.getRowHeight());
		rowHeader.setCellRenderer(new RowRenderer(table));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setColumnHeader(new JViewport() {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = columnHeaderHeight;
				return d;
			}
		});

		scrollPane.setRowHeaderView(rowHeader);
		getContentPane().add(scrollPane);

	}

	private Calendar getCalendarAt(int x, int y, HashSet<Pair<Integer, Integer>> visited) {
		String cellText = defaultTableModel.getValueAt(x, y).toString();

		if (visited.contains(new Pair<Integer, Integer>(x, y))) {
			return null;
		} else {
			visited.add(new Pair<Integer, Integer>(x, y));
		}

		if (cellText.length() > 1 && cellText.charAt(0) == '=' && Character.isDigit(cellText.charAt(1))) {
			try {
				Calendar c = GregorianCalendar.getInstance();
				c.setTime(dateFormat.parse(cellText.substring(1, 11)));
				if (cellText.length() > 11)
					c.add(GregorianCalendar.DAY_OF_MONTH, Integer.parseInt(cellText.substring(11, cellText.length())));
				visited.remove(new Pair<Integer, Integer>(x, y));
				return c;
			} catch (Exception e) {
				return null;
			}
		}

		if (cellText.charAt(0) == '=' && (cellText.indexOf('-') != -1 || cellText.indexOf('+') != -1)) {
			int minusPos = cellText.indexOf('-');
			int plusPos = cellText.indexOf('+');
			if (minusPos == -1) {
				minusPos = plusPos;
			}
			if (minusPos < 3)
				return null;
			if (parseAddress(cellText.substring(1, minusPos)) != null) {
				Pair<Integer, Integer> reference = parseAddress(cellText.substring(1, minusPos));
				Calendar c = getCalendarAt(reference.first, reference.second, visited);
				if (c == null) {
					return null;
				}
				try {
					c.add(GregorianCalendar.DAY_OF_MONTH, Integer.parseInt(cellText.substring(minusPos)));
					visited.remove(new Pair<Integer, Integer>(x, y));
					return c;
				} catch (Exception e) {
					return null;
				}
			}
		}

		if (cellText.startsWith("=MIN") || cellText.startsWith("=MAX")) {
			boolean isMin = cellText.startsWith("=MIN");
			cellText = cellText.substring(5);
			int separatorPos = cellText.indexOf(';');
			if (separatorPos == -1)
				separatorPos = cellText.indexOf(')');

			Pair<Integer, Integer> currentPos;

			try {
				currentPos = parseAddress(cellText.substring(0, separatorPos));
			} catch (Exception e) {
				return null;
			}

			Calendar c1 = getCalendarAt(currentPos.first, currentPos.second, visited);
			if (c1 == null)
				return null;
			cellText = cellText.substring(separatorPos + 1, cellText.length());
			while (true) {
				if (cellText.length() == 0 || cellText.startsWith(")"))
					break;
				separatorPos = cellText.indexOf(';');
				if (separatorPos == -1)
					separatorPos = cellText.indexOf(')');

				try {
					currentPos = parseAddress(cellText.substring(0, separatorPos));
				} catch (Exception e) {
					return null;
				}

				Calendar c2 = getCalendarAt(currentPos.first, currentPos.second, visited);
				if (c2 == null)
					return null;

				if ((c1.before(c2) && (!isMin)) || (c2.before(c1) && (isMin))) {
					Calendar c3 = c2;
					c2 = c1;
					c1 = c3;
				}
				cellText = cellText.substring(separatorPos + 1, cellText.length());

			}
			visited.remove(new Pair<Integer, Integer>(x, y));
			return c1;
		}

		return null;
	}

	Pair<Integer, Integer> parseAddress(String address) {
		Pair<Integer, Integer> result = new Pair<Integer, Integer>(0, 0);

		int numPos = 0;
		for (int i = 0; i < address.length(); i++) {
			if (Character.isDigit(address.charAt(i))) {
				numPos = i;
				break;
			}
		}
		try {
			int y = stringToInt(address.substring(0, numPos));
			int x = Integer.parseInt(address.substring(numPos));
			return new Pair<Integer, Integer>(x - 1, y - 1);
		} catch (Exception e) {
		}
		return null;
	}

	int stringToInt(String str) {
		int sum = 0;
		int pow = 1;
		str = (new StringBuilder(str)).reverse().toString();
		for (int i = 0; i < str.length(); i++) {
			sum += ((int) str.charAt(i) - 64) * pow;
			pow *= 26;
		}
		return sum;
	}

	class ExcelTableCellRenderer extends DefaultTableCellRenderer {

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int col) {

			String text = "";

			if (!(table.getModel().getValueAt(row, col) == null
					|| ((String) table.getModel().getValueAt(row, col)).isEmpty())) {
				try {
					Calendar c = getCalendarAt(row, col, new HashSet<Pair<Integer, Integer>>());
					if (c == null) {
						text = "ERROR";
					} else {
						text = (dateFormat.format(c.getTime()));
					}
				} catch (Exception e) {
					text = "ERROR";
				}
			}

			Component c = super.getTableCellRendererComponent(table, text, isSelected, hasFocus, row, col);

			return c;
		}
	}

	public static void main(String[] args) {
		ExcelTable excelTable = new ExcelTable();
		excelTable.setDefaultCloseOperation(EXIT_ON_CLOSE);
		excelTable.setVisible(true);
	}
}

class RowRenderer extends JLabel implements ListCellRenderer {

	public RowRenderer(JTable table) {
		JTableHeader header = table.getTableHeader();
		setOpaque(true);
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		setHorizontalAlignment(CENTER);
		setForeground(header.getForeground());
		setBackground(header.getBackground());
		setFont(header.getFont());
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		setText((value == null) ? "" :  value.toString());
		return this;
	}
}

//class SelectionListener implements ListSelectionListener {
//	JTable table;
//
//	SelectionListener(JTable table) {
//		this.table = table;
//	}
//
//	public void valueChanged(ListSelectionEvent e) {
////		int i1 = e.getFirstIndex();
////		int i2 = e.getLastIndex();
////		System.out.print(i1);
////		System.out.print("   ");
////		System.out.println(i2);
//	}
//}

class Pair<F, S> {
	public final F first;
	public final S second;

	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Pair)) {
			return false;
		}
		Pair<?, ?> p = (Pair<?, ?>) o;
		return Objects.equals(p.first, first) && Objects.equals(p.second, second);
	}

}