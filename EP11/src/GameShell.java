import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.sound.sampled.*;
import java.io.*;
import javax.swing.*;

public class GameShell extends Applet implements KeyListener, MouseListener, MouseMotionListener, Runnable {

	private int SDV = -9; // set doodle velocity
	private int PSDS = 5; // platform scroll down speed
	private int DSDS = 3; // doodle scroll down speed
	private int level = 0;
	private int score = 0;

	private ArrayList<Character> myGuys;
	private ArrayList<Image> myImages;
	private ArrayList<Character> myPlatforms;
	private ArrayList<Player> players;

	private Graphics offScreenBuffer;
	private Image offScreenImage;
	private MediaTracker mt;
	private Image gridImg, topbar;
	private Image doodleRImg, doodleLImg;
	private Image greenP, blueP, whiteP, dblueP;
	private Image brownP1, brownP2, brownP3, brownP4, brownP5, brownP6;
	private Image intro0, intro1, intro3, scores0, scores1, gameover0, gameover1;
	private Character doodle;
	private boolean gameOver2 = false;
	private boolean nameEntered = false;
	private int moveSide = 0;
	private int menuHoverOver = 0;
	private int scoresHoverOver = 0;
	private int gameOverHover = 0;
	private int creationCounter = 0;
	private Platform lastHitPlatform = new Platform(0, 0, 0, 0, 0);
	boolean shiftDown;
	boolean samePlatform;
	private Thread gameloop;
	private boolean menuOn = true;
	private boolean gameOver = false;
	private boolean gameOn = false;
	private boolean scoresOn = false;

	private AudioClip acFall, ac;
	private Clip clip;

	public void init() {
		ac = getAudioClip(getDocumentBase(), "sounds/mystery.wav");
		acFall = getAudioClip(getDocumentBase(), "sounds/fall.wav");
		score = 0;

		// add keyboard listener
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		// prepares the double buffered image
		offScreenImage = createImage(450, 600);
		offScreenBuffer = offScreenImage.getGraphics();

		// prepare images
		mt = new MediaTracker(this);
		gridImg = Toolkit.getDefaultToolkit().getImage("images/bg-grid.png");
		topbar = Toolkit.getDefaultToolkit().getImage("images/topbar.png");

		intro0 = Toolkit.getDefaultToolkit().getImage("images/menu/intro0.png");
		intro1 = Toolkit.getDefaultToolkit().getImage("images/menu/intro1.png");
		intro3 = Toolkit.getDefaultToolkit().getImage("images/menu/intro3.png");
		scores0 = Toolkit.getDefaultToolkit().getImage("images/menu/scores0.png");
		scores1 = Toolkit.getDefaultToolkit().getImage("images/menu/scores1.png");
		gameover0 = Toolkit.getDefaultToolkit().getImage("images/menu/gameover0.png");
		gameover1 = Toolkit.getDefaultToolkit().getImage("images/menu/gameover1.png");

		doodleRImg = Toolkit.getDefaultToolkit().getImage("images/doodleR.png");
		doodleLImg = Toolkit.getDefaultToolkit().getImage("images/doodleL.png");

		greenP = Toolkit.getDefaultToolkit().getImage("images/p-green.png");
		blueP = Toolkit.getDefaultToolkit().getImage("images/p-blue.png");
		whiteP = Toolkit.getDefaultToolkit().getImage("images/p-white.png");
		dblueP = Toolkit.getDefaultToolkit().getImage("images/p-dblue.png");

		brownP1 = Toolkit.getDefaultToolkit().getImage("images/brown/p-brown-1.png");
		brownP2 = Toolkit.getDefaultToolkit().getImage("images/brown/p-brown-2.png");
		brownP3 = Toolkit.getDefaultToolkit().getImage("images/brown/p-brown-3.png");
		brownP4 = Toolkit.getDefaultToolkit().getImage("images/brown/p-brown-4.png");
		brownP5 = Toolkit.getDefaultToolkit().getImage("images/brown/p-brown-5.png");
		brownP6 = Toolkit.getDefaultToolkit().getImage("images/brown/p-brown-6.png");

		myImages = new ArrayList();
		myImages.add(doodleRImg);// 0
		myImages.add(greenP); // 1
		myImages.add(blueP); // 2
		myImages.add(brownP1); // 3
		myImages.add(brownP2); // 4
		myImages.add(brownP3); // 5
		myImages.add(brownP4); // 6
		myImages.add(brownP5); // 7
		myImages.add(brownP6); // 8
		myImages.add(whiteP); // 9
		myImages.add(dblueP); // 10

		// load images to Media Tracker
		for (Image i : myImages) {
			mt.addImage(i, 0);
		}
		try {
			mt.waitForAll();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}

		doodle = new Doodle(1, 190, 540, 60, 59);

		myGuys = new ArrayList<Character>();
		myGuys.add(doodle);

		myPlatforms = new ArrayList<Character>();
		// generates first 12 random platforms
		for (int i = 0; i < 12; i++) {
			Platform plat = randomPlatform();
			myPlatforms.add(plat);
		}
		int yp = 500;
		int xp = (int) (Math.random() * 400);

		myPlatforms.add(new Platform(1, xp, 500, 58, 15));
	}

	public Platform randomPlatform() {
		int yp = (int) (Math.random() * 450);
		int xp = (int) (Math.random() * 400);

		Platform plat = new Platform(1, xp, yp, 58, 15);
		return plat;
	}

	public void start() {
		gameloop = new Thread(this);
		gameloop.start();
	}

	public void run() {
		while (!gameOver2) {
			try {
				Thread.sleep(19);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}

	public void updateHorizontalPlat(int k, Character plat) {
		Platform tempPlat = (Platform) plat;

		int tempX = tempPlat.getX();
		int tempV = tempPlat.getxVelocity();

		if (tempV == 2) {
			tempPlat.changeX(tempV);
			if (tempX > 400) {
				tempPlat.setxVelocity(-2);
			}
		}
		if (tempV == -2) {
			tempPlat.changeX(tempV);
			if (tempX < 0) {
				tempPlat.setxVelocity(2);
			}
		}

		myPlatforms.set(k, tempPlat);
		offScreenBuffer.drawImage(myImages.get(tempPlat.getId()), tempPlat.getX(), tempPlat.getY(), this);
	}

	public void updateBreakingPlat(int k, Character plat) {
		Platform brownPlat = (Platform) plat;

		if (brownPlat.getBrownAnimation() == true) {
			if (brownPlat.getId() == 8) {
				myPlatforms.remove(k);
			}
			if (brownPlat.getId() < 8) {
				offScreenBuffer.drawImage(myImages.get(brownPlat.getId()), brownPlat.getX(), brownPlat.getY(), this);
				brownPlat.setId(plat.getId() + 1);
				myPlatforms.set(k, brownPlat);
			}
		}
		if (brownPlat.getBrownAnimation() == false) {
			offScreenBuffer.drawImage(myImages.get(brownPlat.getId()), brownPlat.getX(), brownPlat.getY(), this);
		}
	}

	public void updateVerticalPlat(int k, Character plat) {
		Platform tempPlat = (Platform) plat;

		int tempV = tempPlat.getyVelocity();
		int vcount = tempPlat.getVcount();

		if (tempV == -1) {
			if (vcount >= 100) {
				tempPlat.setyVelocity(1);
			}
			if (vcount < 100) {
				tempPlat.changeY(tempV);
				tempPlat.setVcount(tempPlat.getVcount() + 1);
			}
		}

		if (tempV == 1) {
			if (vcount <= -100) {
				tempPlat.setyVelocity(-1);
			}
			if (vcount > -100) {
				tempPlat.changeY(tempV);
				tempPlat.setVcount(tempPlat.getVcount() - 1);
			}
		}

		myPlatforms.set(k, tempPlat);
		offScreenBuffer.drawImage(myImages.get(tempPlat.getId()), tempPlat.getX(), tempPlat.getY(), this);
	}

	//private int fCount = 0;

	// one step of game - draw to buffer and displays all at the end
	public void update(Graphics g) {
		if ((gameOver == true) /*&& (fCount == 0)*/) {
			acFall.play();
			//fCount = 1;
		}
		if (menuOn == true) {
			if (menuHoverOver == 0) {
				offScreenBuffer.drawImage(intro0, 0, 0, this);
			}
			if (menuHoverOver == 1) {
				offScreenBuffer.drawImage(intro1, 0, 0, this);
			}
			if (menuHoverOver == 3) {
				offScreenBuffer.drawImage(intro3, 0, 0, this);
			}
		}
		if (gameOver == true) {
			if (gameOverHover == 0) {
				offScreenBuffer.drawImage(gameover0, 0, 0, this);
			}
			if (gameOverHover == 1) {
				offScreenBuffer.drawImage(gameover1, 0, 0, this);
			}

			offScreenBuffer.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
			offScreenBuffer.drawString("" + score, 246, 412);

			if (nameEntered == false) {
				nameEntered = true;
				calculateScore();
			}
		}
		if (scoresOn == true) {
			if (scoresHoverOver == 0) {
				offScreenBuffer.drawImage(scores0, 0, 0, this);
			}
			if (scoresHoverOver == 1) {
				offScreenBuffer.drawImage(scores1, 0, 0, this);
			}
			drawScores();
		}

		if (gameOn == true) {
			offScreenBuffer.drawImage(gridImg, 0, 0, this);
			Doodle tempDoodle = (Doodle) myGuys.get(0);

			// if doodle is moving up
			shiftDown = false;

			if (tempDoodle.getVelocity() < 0) {
				shiftDown = true;
			}

			for (int k = 0; k < myPlatforms.size(); k++) {
				Character tempPlatform = (Platform) myPlatforms.get(k);
				
				// light blue - horizontal scroll
				if (tempPlatform.getId() == 2) {
					updateHorizontalPlat(k, tempPlatform);
				}
				// brown
				if ((tempPlatform.getId() >= 3) && (tempPlatform.getId() <= 9)) {
					updateBreakingPlat(k, tempPlatform);
				}

				// dark blue - vertical scroll
				if (tempPlatform.getId() == 10) {
					updateVerticalPlat(k, tempPlatform);
				}

				// if its a normal platform
				else {
					offScreenBuffer.drawImage(myImages.get(tempPlatform.getId()), tempPlatform.getX(),
							tempPlatform.getY(), this);
				}

				// move platform down if doodle is moving up
				if ((shiftDown == true) && (samePlatform == false)) {
					tempPlatform.changeY(PSDS);
					score += 1;
				}

				// if platform moves off bottom of screen, create new platform
				if (tempPlatform.getY() > 400) {
					// interval for every Y to create new platform
					if (creationCounter > ((int) (Math.random() * 7) + 5)) {
						generateLiveRandomPlatform();
						creationCounter = 0;
					}
				}

				if (tempPlatform.getY() > 650) {
					myPlatforms.remove(k);
				}
			}

			if (myPlatforms.size() < 13) {
				generateLiveRandomPlatform();
			}

			// draw doodle, last character
			offScreenBuffer.drawImage(myImages.get(tempDoodle.show()), tempDoodle.getX(), tempDoodle.getY(), this);
			tempDoodle.move();
			if ((shiftDown == true) && (samePlatform == false)) {
				tempDoodle.changeY(DSDS);
				creationCounter++;
			}

			// draw top bar and score
			offScreenBuffer.drawImage(topbar, 0, 0, this);
			offScreenBuffer.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
			offScreenBuffer.drawString("" + score, 20, 28);
			offScreenBuffer.drawString("Level: " + level, 350, 28);

			checkPlatformHit();
			checkDoodleGameOver();
		}
		g.drawImage(offScreenImage, 0, 0, this);
	}

	public void checkDoodleGameOver() {
		Doodle doodle = (Doodle) myGuys.get(0);
		if (doodle.getY() > 620) {
			gameOver = true;
			gameOn = false;
		}
	}

	public void processLevel() {
		if (score >= 40000) {
			level = 5;
		}
		if ((score > 30000) && (score <= 40000)) {
			level = 4;
		}
		if ((score > 20000) && (score <= 30000)) {
			level = 3;
		}
		if ((score > 10000) && (score <= 20000)) {
			level = 2;
		}
		if ((score > 5000) && (score <= 10000)) {
			level = 1;
		}
		if (score <= 5000) {
			level = 0;
		}
	}

	public void generateLiveRandomPlatform() {
		int color = 1;
		processLevel();

		color = (int) (Math.random() * 110) + 1;

		if (level == 0) {
			color = 1;
		}

		int xp = (int) (Math.random() * 400);
		int yp = (int) (Math.random() * 10);
		yp = yp * -1;

		// green
		if ((color > 0) && (color <= 50)) {
			if (level == 2) {
				color = (int) (Math.random() * 99) + 1;
			} else if (level == 3) {
				color = (int) (Math.random() * 70) + 30;
			} else if (level == 4) {
				color = (int) (Math.random() * 63) + 45;
			} else if (level == 5) {
				color = (int) (Math.random() * 63) + 45;
			}

			if ((color > 0) && (color <= 50)) {
				Platform plat = new Platform(1, xp, yp, 58, 15);
				myPlatforms.add(plat);
			}
		}

		// light blue LR
		if ((color > 50) && (color <= 60) && (level > 1)) {
			Platform plat = new Platform(2, xp, yp, 56, 16);
			myPlatforms.add(plat);
		}

		if ((color > 50) && (color <= 60) && (level < 2)) {
			color = 62;
		}

		// brown
		if ((color > 60) && (color <= 70)) {
			// makes sure there are not 2 brown in a row
			if (myPlatforms.get(myPlatforms.size() - 1).getId() == 3) {
				Platform plat1 = new Platform(1, xp, yp, 58, 15);
				myPlatforms.add(plat1);
			} else {
				Platform plat3 = new Platform(3, xp, yp, 68, 20);
				myPlatforms.add(plat3);
			}
		}

		// white
		if ((color > 70) && (color <= 80)) {
			if (level == 5) {
				color = (int) (Math.random() * 63) + 45;
			}
			if ((color > 70) && (color <= 80)) {
				Platform plat9 = new Platform(9, xp, yp, 58, 15);
				myPlatforms.add(plat9);
			}
		}

		// dark blue - vertical scroll
		if ((color > 90) && (color <= 100)) {
			Platform plat10 = new Platform(10, xp, yp, 57, 15);
			myPlatforms.add(plat10);
		}

		if ((color > 100) && (color <= 105)) {
			Platform plat = new Platform(1, xp, yp, 58, 15);
			myPlatforms.add(plat);
		}

	}

	public void checkPlatformHit() {
		Doodle doodle = (Doodle) myGuys.get(0);

		for (int a = 0; a < myPlatforms.size(); a++) {
			// only compare if doodle is falling, ignore if bouncing up
			if (doodle.getVelocity() > 0) {
				// if a doodle hits a platform
				if (doodle.checkHitPlatform(myPlatforms.get(a))) {
					Platform hitPlat = (Platform) myPlatforms.get(a);

					// if doodle hits a brown platform, play animation and skip the "Hit jump"
					if ((hitPlat.getId() >= 3) && (hitPlat.getId() < 9)) {
						Platform newBrown = (Platform) myPlatforms.get(a);
						newBrown.setBrownAnimation(true);
						myPlatforms.set(a, newBrown);
					} else {
						Platform t2 = (Platform) myPlatforms.get(a);

						Doodle temp = (Doodle) myGuys.get(0);
						temp.setVelocity(SDV);
						myGuys.set(0, temp);

						if (hitPlat.getId() == 9) {
							myPlatforms.remove(a);
						}
						// if doodle stays on same platform, dont move platforms down
						if ((lastHitPlatform.getX() != t2.getX()) && (temp.getY() < lastHitPlatform.getY())) {
							score += 100;
							samePlatform = false;
						} else {
							samePlatform = true;
						}
						lastHitPlatform = (Platform) myPlatforms.get(a);
					}
				}
			}
		}
	}

//	public void wavRunner(String s) {
//		try {
//			File file = new File(s);
//			AudioInputStream audiosource = AudioSystem.getAudioInputStream(file);
//			DataLine.Info info = new DataLine.Info(Clip.class, audiosource.getFormat());
//			clip = (Clip) AudioSystem.getLine(info);
//			clip.open(audiosource);
//		} catch (UnsupportedAudioFileException e) {
//		} catch (LineUnavailableException e) {
//		} catch (IOException e) {
//		}
//		clip.loop(0);
//	}

	public void calculateLR(int x) {
		Doodle doodle = (Doodle) myGuys.get(0);
		int doodleX = doodle.getX();

		// if already facing left, dont do anything, same with right
		if ((x < doodleX)) {
			myImages.set(0, doodleLImg);
			doodle.setX(doodle.getX());
			moveSide = -1;
			doodle.setMoveSide(-1);
		} else if ((x > doodleX + doodle.getWidth()) && (moveSide < 1)) {
			myImages.set(0, doodleRImg);
			doodle.setX(doodle.getX());
			moveSide = 1;
			doodle.setMoveSide(1);
		} else if (moveSide != 0) {
			moveSide = 0;
			doodle.setMoveSide(0);
		}
		myGuys.set(0, doodle);
	}

	public void resetGame() {
		Doodle doodle = new Doodle(1, 190, 540, 60, 59);
		myGuys.set(0, doodle);
		score = 0;
		level = 0;
		nameEntered = false;

		myPlatforms = new ArrayList<Character>();

		spaceCount = 0;
		//fCount = 0;

		for (int i = 0; i < 12; i++) {
			Platform plat = randomPlatform();
			myPlatforms.add(plat);
		}

		int yp = 500;
		int xp = (int) (Math.random() * 400);

		myPlatforms.add(new Platform(1, xp, 500, 58, 15));
	}

	public void overrideLR(int side) {
		Doodle doodle = (Doodle) myGuys.get(0);
		doodle.setMoveSide(side);

		if (side == -1) {
			myImages.set(0, doodleLImg);
			doodle.setX(doodle.getX());
		} else if (side == 1) {
			myImages.set(0, doodleRImg);
			doodle.setX(doodle.getX());
		}
		myGuys.set(0, doodle);
	}

	public void paint(Graphics g) {
		update(g);
	}

	public void readScores() {
		players = new ArrayList<Player>();
		try {
			Scanner sc = new Scanner(new File("scores.txt"));
			while (sc.hasNext()) {
				players.add(new Player(sc.next(), sc.nextInt()));
			}
			sc.close();
		} catch (IOException e) {
		}

		players.sort(new Comparator<Player>() {
			public int compare(Player arg0, Player arg1) {
				return arg1.getScore() - arg0.getScore();
			}
		});
	}

	public void writeScores() throws IOException {
		FileWriter output = new FileWriter("scores.txt");
		for (Player pl : players) {
			output.write(pl.getName() + " " + pl.getScore() + "\n");
		}
		output.close();
	}

	public void drawScores() {
		int yi = 45;

		for (int i = 0; i < players.size(); i++) {
			Player temp = players.get(i);

			int newY = 166 + (yi * i);

			offScreenBuffer.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
			offScreenBuffer.drawString(temp.getName(), 85, newY);
			offScreenBuffer.drawString("" + temp.getScore(), 350, newY);
		}
	}

	public void calculateScore() {
		readScores();

		if (score > players.get(5).getScore()) {

			String name = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

			name = JOptionPane.showInputDialog(null,
					"You have gotten a new high score!\nYour Score: " + score + "\nPlease enter your name below:",
					"High Score", JOptionPane.INFORMATION_MESSAGE);

			if (name == null) {
				name = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
			}

			while ((name.length() > 20) || (name.length() == 0)) {
				name = JOptionPane.showInputDialog(null,
						"You have gotten a new high score!\nYour Score: " + score
								+ "\nPlease enter your name below:\n\nMust be less than 20 characters.",
						"High Score", JOptionPane.INFORMATION_MESSAGE);
				if (name == null) {
					name = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
				}
			}

			// write score to file
			Player player = new Player(name, score);
			players.add(player);

			players.sort(new Comparator<Player>() {

				public int compare(Player arg0, Player arg1) {
					return arg1.getScore() - arg0.getScore();
				}

			});
			// keep array size at 6
			players.remove(6);
			try {
				writeScores();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void mousePressed(MouseEvent me) {
		if (menuOn == true) {
			if ((me.getX() >= 70) && (me.getX() <= 230)) {
				if ((me.getY() >= 160) && (me.getY() <= 210)) {
					menuOn = false;
					gameOn = true;
					gameOver = false;
					resetGame();
				}
			}
			if ((me.getX() >= 280) && (me.getX() <= 400)) {
				if ((me.getY() >= 400) && (me.getY() <= 450)) {
					scoresOn = true;
					menuOn = false;
					gameOver = false;
					readScores();
				}
			}
		}
		if (scoresOn == true) {
			if ((me.getX() >= 250) && (me.getX() <= 400)) {
				if ((me.getY() >= 520) && (me.getY() <= 570)) {
					scoresOn = false;
					menuOn = true;
					gameOver = false;
					scoresHoverOver = 0;
					menuHoverOver = 0;
				}
			}
		}
		if (gameOver == true) {
			if ((me.getX() >= 68) && (me.getX() <= 225)) {
				if ((me.getY() >= 155) && (me.getY() <= 208)) {
					gameOver = false;
					menuOn = true;
					scoresOn = false;
					scoresHoverOver = 0;
					menuHoverOver = 0;
				}
			}
		}
	}

	public void mouseReleased(MouseEvent me) {
		if (gameOn == true) {
			myImages.set(0, doodleRImg);
		}
	}

	public void mouseEntered(MouseEvent me) {
	}

	public void mouseExited(MouseEvent me) {
	}

	public void mouseClicked(MouseEvent me) {
	}

	public void mouseMoved(MouseEvent me) {
		if (menuOn == true) {
			if ((me.getX() >= 70) && (me.getX() <= 230)) {
				if ((me.getY() >= 160) && (me.getY() <= 210)) {
					menuHoverOver = 1;
				}
			} else if ((me.getX() >= 280) && (me.getX() <= 400)) {
				if ((me.getY() >= 400) && (me.getY() <= 450)) {
					menuHoverOver = 3;
				}
			} else {
				menuHoverOver = 0;
			}
		} else if (scoresOn == true) {
			if ((me.getX() >= 250) && (me.getX() <= 400)) {
				if ((me.getY() >= 520) && (me.getY() <= 570)) {
					scoresHoverOver = 1;
				}
			} else {
				scoresHoverOver = 0;
			}
		} else if (gameOver == true) {
			if ((me.getX() >= 68) && (me.getX() <= 225)) {
				if ((me.getY() >= 155) && (me.getY() <= 208)) {
					gameOverHover = 1;
				}
			} else {
				gameOverHover = 0;
			}
		}
	}

	public void mouseDragged(MouseEvent me) {
	}

	public void keyReleased(KeyEvent e) {
		// set back to 0
		if (gameOn == true) {
			if ((e.getKeyCode() == 37) || (e.getKeyCode() == (39))) {
				overrideLR(0);
			}
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	private int spaceCount;

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 32: {
			// space key
			if (spaceCount == 0) {
				samePlatform = true;
				Doodle temp = (Doodle) myGuys.get(0);
				temp.setVelocity(SDV);
				myGuys.set(0, temp);
				spaceCount = 1;
			}
			break;
		}

		case 37:
			// left arrow
			overrideLR(-1);
			break;

		case 39:
			// right arrow
			overrideLR(1);
			break;

		// escape key, return to main menu
		case 27: {
			gameOn = false;
			menuOn = true;
			scoresOn = false;
			gameOver = false;
			break;
		}
		}
	}
}
