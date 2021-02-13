package ep6;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Task1 extends JFrame {
	final static String PATH = "E:\\EclipseWorkspace\\EP6\\src";
	final static int NUM = 2;
	private ImageIcon image;
	private JPanel panel;
	private int[][] field;
	private ImageIcon[] icons;
	private JPanel hintPanel;
	private int partHeight, partWidth;
	private int imageHeight, imageWidth;
	private BufferedImage bufferedImage;

	public Task1() {
		super("First task");
		setLayout(new GridLayout(1, 2, 0, 0));
		panel = new JPanel(new GridLayout(NUM, NUM, 0, 0));
		hintPanel = new JPanel(new GridLayout(1, 0, 0, 0));
		field = new int[NUM][NUM];

		try {
			Image img = ImageIO.read(new File("src\\barash.jpg"));
			image = new ImageIcon(img);

		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Please, open right file!");
		}
		imageHeight = image.getIconHeight();
		imageWidth = image.getIconWidth();

		partHeight = imageHeight / NUM;
		partWidth = imageWidth / NUM;

		setResizable(false);
		add(panel);
		add(hintPanel);
		hintPanel.add(new JLabel(image));
		setVisible(true);

		bufferedImage = toBufferedImage(image.getImage());

		createMenu();
		initialisePartsOfImage();
		generateNewGame();

		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void createMenu() {
		JMenuBar menu = new JMenuBar();
		JMenu fileMenu = new JMenu("File");

		for (String fileItem : new String[] { "New", "Open...", /* "TestFeature", */ "Exit" }) {
			JMenuItem item = new JMenuItem(fileItem);
			item.setActionCommand(fileItem);
			item.addActionListener(e -> {
				if (e.getActionCommand().equals("New")) {
					generateNewGame();
					repaintPanel();
				} else if (e.getActionCommand().equals("Exit")) {
					System.exit(0);
				} else if (e.getActionCommand().equals("Open...")) {
					openFile();
				}
			});
			fileMenu.add(item);

		}
		fileMenu.insertSeparator(1);
		fileMenu.insertSeparator(3);
		fileMenu.insertSeparator(5);

		menu.add(fileMenu);
		setJMenuBar(menu);
	}

	public void openFile() {
		JFileChooser jfc = new JFileChooser(PATH);

		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			try {
				Image img = ImageIO.read(selectedFile);
				image = new ImageIcon(img);
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(null, "Please, open right file!");
			}
		}
		imageHeight = image.getIconHeight();
		imageWidth = image.getIconWidth();
		partHeight = imageHeight / NUM;
		partWidth = imageWidth / NUM;

		panel.setPreferredSize(new Dimension(imageWidth + 10, imageHeight + 10));
		hintPanel.setPreferredSize(new Dimension(imageWidth + 10, imageHeight + 10));
		setPreferredSize(new Dimension(imageWidth * 2 + 20, imageHeight + 10));
		setBounds(0, 0, imageWidth * 2 + 20, imageHeight + 10);
		pack();

		hintPanel.removeAll();
		hintPanel.add(new JLabel(image));

		initialisePartsOfImage();
		generateNewGame();

	}

	public void generateNewGame() {
		int[] myField = new int[NUM * NUM];
		Random rand = new Random();
		int row, column;
		do {
			for (int i = 0; i < NUM; i++) {
				for (int j = 0; j < NUM; j++) {
					myField[i * NUM + j] = 0;
					field[i][j] = 0;
				}
			}

			for (int i = 0; i < NUM * NUM; i++) {
				do {
					row = rand.nextInt(NUM);
					column = rand.nextInt(NUM);
				} while (field[row][column] != 0);
				myField[row * NUM + column] = i;
				field[row][column] = i;
			}
		} while (canBeSolved(myField));

		repaintPanel();
	}

	private boolean canBeSolved(int[] numbersOnField) {
		int sum = 0;
		for (int i = 0; i < NUM * NUM; i++) {
			if (numbersOnField[i] == 0) {
				sum += i / NUM;
				continue;
			}
			for (int j = i + 1; j < NUM * NUM; j++) {
				if (numbersOnField[j] < numbersOnField[i]) {
					sum++;
				}
			}
		}
		return sum % 2 == 0;
	}

	public void repaintPanel() {
		panel.removeAll();
		int index = 0;
		for (int i = 0; i < NUM; i++) {
			for (int j = 0; j < NUM; j++) {
				JButton button = new JButton();
				button.setPreferredSize(new Dimension(partWidth, partHeight));
				button.setFocusable(false);
				index = field[i][j];
				if (index == 0)
					index = NUM * NUM - 1;
				else
					index--;
				button.setIcon(icons[index]);
				button.setText(Integer.toString(field[i][j]));
				panel.add(button);

				if (field[i][j] == 0) {
					button.setVisible(false);
				} else
					button.addActionListener(new MyActionListener());
			}
		}

		panel.validate();
		panel.repaint();
	}

	public void initialisePartsOfImage() {
		icons = new ImageIcon[NUM * NUM];
		BufferedImage bufferedImage = toBufferedImage(image.getImage());
		BufferedImage part;
		for (int i = 0; i < NUM; i++) {
			for (int j = 0; j < NUM; j++) {
				part = bufferedImage.getSubimage((j) * partWidth, (i) * partHeight, partWidth, partHeight);
				icons[i * NUM + j] = new ImageIcon(part);
			}
		}
	}

	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();
		return bimage;
	}

	private class MyActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			int row = 0, column = 0;
			int num = Integer.parseInt(button.getText());
			for (int i = 0; i < NUM; i++) {
				for (int j = 0; j < NUM; j++) {
					if (field[i][j] == num) {
						row = i;
						column = j;
						break;
					}
				}
			}
			if (column > 0) {
				if (field[row][column - 1] == 0) {
					field[row][column - 1] = num;
					field[row][column] = 0;
				}
			}
			if (column < NUM - 1) {
				if (field[row][column + 1] == 0) {
					field[row][column + 1] = num;
					field[row][column] = 0;
				}
			}
			if (row > 0) {
				if (field[row - 1][column] == 0) {
					field[row - 1][column] = num;
					field[row][column] = 0;
				}
			}
			if (row < NUM - 1) {
				if (field[row + 1][column] == 0) {
					field[row + 1][column] = num;
					field[row][column] = 0;
				}
			}

			if (checkAnswer()) {
				panel.removeAll();
				Graphics2D gr = (Graphics2D) panel.getGraphics();
				gr.drawImage(toBufferedImage(image.getImage()), 0, 0, null);
			} else
				repaintPanel();
//            for (int i = 0; i < NUM; i++) {
//                for (int j = 0; j < NUM; j++) {
//                    System.out.print(field[i][j] + " ");
//                }
//                System.out.println("");
//            }
		}
	}

	public boolean checkAnswer() {
		for (int i = 0; i < NUM; i++) {
			for (int j = 0; j < NUM; j++) {
				if (i == NUM - 1 && j == NUM - 1)
					return field[i][j] == 0;
				else if (field[i][j] != i * NUM + j + 1)
					return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		new Task1();
	}

}