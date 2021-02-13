package ep6;

import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;

public class Task2 extends JFrame implements KeyListener {
	private Color3f textColor;
	private Color3f backgroundColor;
	private float lightZPos;
	private Text3D text;
	private Color3f lightColor, lightColor1;
	private PointLight light, light1;

	public Task2() {
		super("Second Task");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		textColor = new Color3f(Color.BLACK);
		backgroundColor = new Color3f(Color.GRAY);
		lightZPos = 15.0f;
		Canvas3D c = new Canvas3D(config);
		add(c, BorderLayout.CENTER);
		c.setDoubleBufferEnable(true);
		SimpleUniverse u = new SimpleUniverse(c);
		BranchGroup scene = createScene();
		u.getViewingPlatform().setNominalViewingTransform();
		u.addBranchGraph(scene);

		c.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				light.setPosition(25.0f - e.getX() / 10.0f, 25.0f - e.getY() / 10.0f, lightZPos);
			}
		});
		c.addKeyListener(this);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setSize(800, 600);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public BranchGroup createScene() {
		lightColor = new Color3f(Color.GREEN);
		lightColor1 = new Color3f(Color.MAGENTA);

		BranchGroup objRoot = new BranchGroup();
		TransformGroup objScale = new TransformGroup();
		Transform3D t3d = new Transform3D();
		t3d.setScale(0.3);
		objScale.setTransform(t3d);
		objRoot.addChild(objScale);

		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		Background background = new Background(backgroundColor);
		background.setApplicationBounds(bounds);
		objScale.addChild(background);

		Material material = new Material(textColor, textColor, textColor, new Color3f(Color.WHITE), 1.0f);
		Appearance appearance = new Appearance();
		material.setLightingEnable(true);
		appearance.setMaterial(material);
		Font font = new Font("Adobe Gothic Std B", Font.PLAIN, 1);
		Font3D f3d = new Font3D(font, 0.001, new FontExtrusion(new Line2D.Double(0, 0, 1, 1)));

		text = new Text3D(f3d, "Second task", new Point3f(0.0f, -0.5f, 0.0f));
		text.setAlignment(Text3D.ALIGN_CENTER);
		text.setCapability(Text3D.ALLOW_STRING_WRITE);
		text.setCapability(Text3D.ALLOW_STRING_READ);
		Shape3D s3D2 = new Shape3D(text, appearance);
		objScale.addChild(s3D2);

		light = new PointLight();
		light.setEnable(true);
		light.setColor(lightColor);
		light.setPosition(10.0f, 3.0f, lightZPos);
		light.setCapability(Light.ALLOW_COLOR_WRITE);
		light.setCapability(PointLight.ALLOW_POSITION_WRITE);
		light.setInfluencingBounds(bounds);
		objScale.addChild(light);

		light1 = new PointLight();
		light1.setEnable(true);
		light1.setColor(lightColor1);
		light1.setPosition(1.0f, 1.0f, 1.0f);
		light1.setCapability(Light.ALLOW_COLOR_WRITE);
		light1.setCapability(PointLight.ALLOW_POSITION_WRITE);
		light1.setInfluencingBounds(bounds);
		objScale.addChild(light1);

		objRoot.compile();

		return objRoot;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE) {
			String newText = text.getString().substring(0, text.getString().length() - 1);
			text.setString(newText);
		} else if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
			Color color = JColorChooser.showDialog(null, "Choose color", Color.black);
			if (color != null) {
				lightColor.set(color);
			}
			light.setColor(lightColor);
		} 
		else if (e.getExtendedKeyCode() == KeyEvent.VK_ALT) {
			Color color = JColorChooser.showDialog(null, "Choose color", Color.black);
			if (color != null) {
				lightColor1.set(color);
			}
			light1.setColor(lightColor1);
		}else {
			char newText = e.getKeyChar();
			if (text.getFont3D().getFont().canDisplay(newText))
				text.setString(text.getString() + newText);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public static void main(String[] args) {
		new Task2();
	}

}