package ep4;

import com.google.gson.Gson;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

class MyFrame extends JFrame {

	public MyFrame() {
		SwingUtilities.invokeLater(() -> {
			setVisible(true);
			setSize(600, 600);
			MyFrame.this.setLocationRelativeTo(null);
			this.setResizable(true);
			addComponentListener(new ComponentAdapter() {
				@Override
				public void componentHidden(ComponentEvent e) {
					System.exit(0);
				}
			});
		});
	}

	private static final int MIN_SPEED = 1;
	private static final int MAX_SPEED = 20;
	private static final int INITIAL_SPEED = 2;

	private JSlider slider;
	private JComboBox<String> comboBox;
	private Boolean right = true;

	private JFreeChart chart;

	@Override
	public JRootPane createRootPane() {
		JRootPane pane = new JRootPane();
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		 //firstTask(panel);
		// secondTask(panel);

		try {
			thirdTask(panel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pane.setContentPane(panel);
		return pane;
	}

	private void firstTask(JPanel panel) {
		panel.add(new Task1());
	}

	private void secondTask(JPanel panel) {
		String[] items = new String[] { "Right", "Left" };
		comboBox = new JComboBox<>(items);
		JPanel btnPanel = new JPanel();
		btnPanel.setPreferredSize(new Dimension(460, 50));

		slider = new JSlider(JSlider.HORIZONTAL, MIN_SPEED, MAX_SPEED, INITIAL_SPEED);
		comboBox.addActionListener(e -> {
			String item = (String) comboBox.getSelectedItem();
			assert item != null;
			right = "Right".equals(item);
		});
		slider.setBounds(100, 10, 50, 15);
		btnPanel.add(slider);
		comboBox.setBounds(200, 10, 50, 15);
		btnPanel.add(comboBox);
		panel.add(btnPanel, BorderLayout.NORTH);
		panel.add(new Task2(this), BorderLayout.CENTER);
		setVisible(true);

	}

	public int getSpeed() {
		return slider.getValue();
	}

	public int getDirection() {
		return right ? 1 : -1;
	}

	private void thirdTask(JPanel panel) throws IOException {
		panel.setLayout(new BorderLayout());
		try (FileReader reader = new FileReader("src//info.json")) {
			Gson gson = new Gson();
			Pastime[] pastime = gson.fromJson(reader, Pastime[].class);
			PieDataset pieDataset = createDataset(pastime);
			chart = createChart(pieDataset);
			PiePlot plot = (PiePlot) chart.getPlot();
			PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{0}: {1} часа(ов), {2}");
			plot.setLabelGenerator(gen);
			panel.add(new ChartPanel(chart));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private JFreeChart createChart(final PieDataset dataset) {
		chart = ChartFactory.createPieChart("Распорядок дня", dataset, true, true, false);
		return chart;
	}

	private PieDataset createDataset(Pastime[] container) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Pastime elem : container) {
			dataset.setValue(elem.getName(), elem.getCount());
		}
		return dataset;
	}
}