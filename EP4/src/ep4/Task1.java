package ep4;

import javax.swing.*;
import java.awt.*;

public class Task1 extends JPanel {

    private int width, height;
    private int angle;
    
    private final int x0 = 250;
    private final int y0 = 250;
    private final int radius = 200;

    public Task1() {
        angle = 0;
        width = 400;
        height = 400;
        setPreferredSize(new Dimension(width + 10, height + 10));
        repaint();
        Timer timer = new Timer(1000, e -> {
            repaint();
            angle += 6;
            if (angle >= 360) {
                angle = 0;
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0, 0, width + 50, height + 50);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        double radAngle = Math.toRadians(angle);
        int x1 = x0 - (int) (radius* Math.sin(-radAngle));
        int y1 = y0 - (int) (radius* Math.cos(-radAngle));
        g2.drawLine(x0, y0, x1, y1);
        g2.drawOval(50, 50, width, height);
    }

}
