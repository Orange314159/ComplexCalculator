import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawPoint extends JPanel {
    // while it is called DrawPoint it also is the class that is used to draw the axis which are lines (not points)
    public List<Point> points ;
    public ArrayList<Integer> colors = new ArrayList<>();
    public List<int[]> axisPoints;

    public DrawPoint(){
        points = new ArrayList<>();
        axisPoints = new ArrayList<>();
    }

    public void addPoint(int x, int y, int color) {
        points.add(new Point(x, y));
        colors.add(color);
        repaint();
    }
    public void addAxis(int x1, int x2, int y1, int y2) {
        axisPoints.add(new int[]{x1, y1, x2, y2});
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw all of my points in black with a diameter of 5
        int counter = 0;
        for (Point point : points) {
            switch (colors.get(counter)){
                case 0:
                    g.setColor(Color.BLACK);
                    break;
                case 1:
                    g.setColor(Color.RED);
                    break;
                case 2:
                    g.setColor(Color.YELLOW);
                    break;
                case 3:
                    g.setColor(Color.BLUE);
                    break;
                case 4:
                    g.setColor(Color.GREEN);
                    break;
                default:
                    System.out.println(colors.get(counter));
                    break;
            }

            g.fillOval(point.x,point.y, 5, 5);
            counter++;
        }
        counter = 0;
        // there are only three axis that are drawn so this is hard coded
        for(int[] point : axisPoints){
            if (counter == 0){
                g.setColor(Color.GREEN);
            } else if (counter == 1){
                g.setColor(Color.BLUE);
            } else {
                g.setColor(Color.RED);
            }
            g.drawLine(point[0], point[1], point[2], point[3]);
            counter++;
        }

    }



    @Override
    public String toString() {
        return points.toString();
    }
}