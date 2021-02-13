package ep3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

class MyPanel extends JFrame {

	public MyPanel() {
		SwingUtilities.invokeLater(() -> {
			setVisible(true);
			setSize(700, 500);
			MyPanel.this.setLocationRelativeTo(null);
			addComponentListener(new ComponentAdapter() {
				@Override
				public void componentHidden(ComponentEvent e) {
					System.exit(0);
				}
			});
		});
	}

	private List<Country> countries;
	private List<Path> paths;
	private JFileChooser chooser = new JFileChooser();
	private Task2 secondTask;
	private Task1 firstPanel;
	private Map<String, String> stringStringMap;

	@Override
	public JRootPane createRootPane() {
		JRootPane pane = new JRootPane();
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		countries = new ArrayList<>();
		paths = new ArrayList<>();
		JTabbedPane tabbedPane = createTabbedPane();
		JMenuBar menuBar = createMenuBar();
		initialMap();

		panel.add(menuBar, BorderLayout.NORTH);
		panel.add(tabbedPane, BorderLayout.CENTER);
		pane.setContentPane(panel);

		return pane;
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu submenu = new JMenu("File");
		JMenuItem open = createOpenButton();
		menuBar.add(submenu);
		submenu.add(open);
		return menuBar;
	}

	private JTabbedPane createTabbedPane() {
		JTabbedPane tabbedPane = new JTabbedPane();
		firstPanel = new Task1(countries);
		secondTask = new Task2(countries);
		tabbedPane.addTab("FirstTask", firstPanel);
		tabbedPane.addTab("SecondTask", secondTask);
		return tabbedPane;
	}

	private JMenuItem createOpenButton() {
		JMenuItem open = new JMenuItem("Open");
		open.addActionListener(e -> {
			try {
				chooser = new JFileChooser();
				chooser.setDialogTitle("Октрытие файла");
				chooser.setCurrentDirectory(new File("."));
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
					fillPaths();
					fillCountries();
					secondTask.update();
					firstPanel.update();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		return open;
	}

	private void fillPaths() throws IOException {
		paths.addAll(Files.walk(Paths.get("")).filter(Files::isRegularFile).collect(Collectors.toList()).stream()
				.filter(f -> f.toString().endsWith("png")).collect(Collectors.toList()));
	}

	private void initialMap() {
		stringStringMap = new TreeMap<>();
		try (Scanner sc = new Scanner(new File("src//input.txt"))) {
			while (sc.hasNext()) {
				stringStringMap.put(sc.next(), sc.next());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void fillCountries() throws IOException {
		//fillPaths();
		for (Path path : paths) {
			Country country = new Country(path);
			if (stringStringMap.get(country.getName()) != null) {
				country.setCapital(stringStringMap.get(country.getName()));
			}
			countries.add(country);
		}
	}

}