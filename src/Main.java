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

import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import java.awt.Robot;
import java.awt.AWTException;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;


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
        // read input
        System.out.println("Hello world!");
        

        Equation e1 = new Equation("\\\\log_{23.2}{7*x-3}");
        System.out.println(e1.evaluateEquation(new ComplexNumber(0,0), e1.length) + " @x=" + new ComplexNumber(0,0));
        System.out.println(e1.evaluateEquation(new ComplexNumber(1,0), e1.length) + " @x=" + new ComplexNumber(1,0));
        System.out.println(e1.evaluateEquation(new ComplexNumber(0,1), e1.length) + " @x=" + new ComplexNumber(0,1));


        JFrame frame = new JFrame("Complex Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // fill the screen
        frame.setSize(720, 480);


        Vector camera = new Vector(-1,-1,-1,1);

        Vector[] points = {new Vector(0,0,0,1), new Vector(1,0,0,1)};

        Scene scene = new Scene(camera, 90, 0, 0, 0.1, 1000, points, 480, 720);

        DrawPoint drawPoint = new DrawPoint();
        drawPoint.points = new ArrayList<>();

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    // remove the window VERY IMPORTANT
                    frame.dispose();
                }
                Vector[] drawPoints = scene.drawFrame();
                for (Vector point : drawPoints){
                    System.out.println(point);
//                    frame.add(new DrawPoint(512, 382));
//                    frame.add(new DrawPoint(305, 382));
                }

            }
        });

        frame.setVisible(true);
    }
}
