import org.jetbrains.annotations.NotNull;

// TODO: create a 4d representation of graph
//
//  ^
//  |
//  |
//  |           ----- > (X)
//
//  (y real)
//
//              . (y imaginary)
//
// TODO: add in trig
// TODO: add in d/dx
// TODO: connect the dots created by visualization to make surfaces (this sounds especially hard)
// TODO: add in a way to visualize the infinite number of complex exponents
// TODO: add in integrals ? This seems very very difficult
//  ^something about u replacement could help (i don't know how to integral)


import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import java.awt.event.*;



public class Main {

    // Colors for printing
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";



    public static void main(String[] args) {
        //------------------------------ Equation Stuff ------------------------------\\
        System.out.println("Hello world!");
        Equation e1 = new Equation("\\\\log_{23.2}{7*x-3}");
        System.out.println(e1.evaluateEquation(new ComplexNumber(0,0), e1.length) + " @x=" + new ComplexNumber(0,0));
        System.out.println(e1.evaluateEquation(new ComplexNumber(1,0), e1.length) + " @x=" + new ComplexNumber(1,0));
        System.out.println(e1.evaluateEquation(new ComplexNumber(0,1), e1.length) + " @x=" + new ComplexNumber(0,1));
        //------------------------------ Equation Stuff ------------------------------\\


        //------------------------------ Swing Stuff ------------------------------\\
        JFrame frame = new JFrame("Complex Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 480);
        frame.setVisible(true);
        //------------------------------ Swing Stuff ------------------------------\\


        //------------------------------ Scene Stuff ------------------------------\\
        Vector camera = new Vector(-5,0,0,1);
        Vector[] points = {new Vector(0,0,0,1), new Vector(1,0,0,1)};
        Scene scene = new Scene(camera, 90, 0, 0, 0.1, 1000, points, 480, 720);
        DrawPoint drawPoint = new DrawPoint();
        drawPoint.points = new ArrayList<>();
        frame.add(drawPoint);
        //------------------------------ Scene Stuff ------------------------------\\

        //------------------------------ Camera Stuff ------------------------------\\
        double theta1 = 0;
        double theta2 = 0;
        //------------------------------ Camera Stuff ------------------------------\\

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    // remove the window VERY IMPORTANT
                    frame.dispose();
                } else if (e.getKeyCode() == KeyEvent.VK_W) {
                    scene.yaw += 0.1;
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    scene.yaw -= 0.1;
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    scene.pitch += 0.1;
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    scene.pitch -= 0.1;
                }
                Vector[] drawPoints = scene.drawFrame();
                drawPoint.points = new ArrayList<>();
                for (Vector point : drawPoints){
                    System.out.println(point);
                    drawPoint.addPoint((int)point.x+360, (int)point.y+240);
                }

            }
        });


    }
}
