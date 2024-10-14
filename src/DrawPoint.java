import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class DrawPoint extends JPanel {
    public List<Point> points = new ArrayList<>();

    public DrawPoint(){
        points = new ArrayList<>();
    }
    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (Point point : points) {
            g.fillOval(point.x,point.y, 5, 5);
        }
    }


    @Override
    public String toString() {
        return points.toString();
    }
}