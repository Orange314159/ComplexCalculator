import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class DrawPoint extends JPanel {
    public List<Point> points ;
    public List<int[]> axisPoints;

    public DrawPoint(){
        points = new ArrayList<>();
        axisPoints = new ArrayList<>();
    }
    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
        repaint();
    }

    public void addAxis(int x1, int x2, int y1, int y2) {
        axisPoints.add(new int[]{x1, y1, x2, y2});
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (Point point : points) {
            g.fillOval(point.x,point.y, 5, 5);
        }
        int counter = 0;
        for(int[] point : axisPoints){
            if (counter == 0){
                g.setColor(Color.GREEN);
            } else if (counter == 1){
                g.setColor(Color.BLUE);
            } else {
                g.setColor(Color.RED);
            }
            g.drawLine(point[0], point[1], point[2], point[3]);
//            System.out.println(point[0] + "," + point[1]);
            counter++;
        }

    }


    @Override
    public String toString() {
        return points.toString();
    }
}