// TODO: add in method to clean node
// TODO: add in d/dx at a point (using slope formula)
// TODO: add in indefinite integrals? This seems very very difficult
//  ^something about u substitution could help (i don't know how to integral)
// TODO: add in different "modes", different ways to represent the points
// TODO: connect the dots created by visualization to make surfaces (this sounds especially hard)



import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner; // although I don't use this I still will include it if someone wishes to use it in their code
import javax.swing.*;
import java.awt.event.*;



public class Main {
    public static void main(String[] args) {
        // debugON is can be set to true if you wish for more verbose prints (includes player x.y.z,pitch,roll, etc)
        // autoUpdated can be set to false if you wish for the graph to only update when you press a key
        // autoUpdate = true means that it will update the graph every time you move through an imaginary slice
        boolean autoUpdate = true;
        boolean debugOn = false;

        //------------------------------ Equation Stuff ------------------------------\\
        System.out.println("Hello world!"); // this is still here to make sure that the code actually runs and isn't broken
        // Testing Derivative function
        Equation e0 = new Equation("\\sin{x^2}");
//        System.out.println(e0.tree.get(e0.length));
//        System.out.println(e0.evaluateNode(new ComplexNumber(0,0), e0.tree.get(e0.length)));
//        System.out.println(e0.evaluateNode(new ComplexNumber(1,0), e0.tree.get(e0.length)));
//        System.out.println(e0.evaluateNode(new ComplexNumber(2,0), e0.tree.get(e0.length)));
//        System.out.println(e0.createDerivativeNode(e0.tree.get(e0.length)));
//        System.out.println(e0.evaluateNode(new ComplexNumber(12,0), e0.createDerivativeNode(e0.tree.get(e0.length))));
//        System.out.println(e0.ddxTree);
        // Testing of Cleaning Function
        Node n01 = new Node("+", new Node(1,10), new Node(6, 10));
        Node n02 = new Node("+", new Node(5,0), new Node(8, 0));
        Node n0 = new Node("*", n01, n02);
        Node n1 = new Node("*", new Node(0,0), new Node(new ComplexNumber("x")));

        System.out.println(n1 + "\tNot Clean");
        System.out.println(e0.cleanNode(n1) + "\tClean");


        // Testing of Complex Function
//        Equation e0 = new Equation("x^{5.0}*(2.7182818)^{-x}");
//        System.out.println(e0.riemannSumOfDefiniteIntegral(0,30,0.2));
//        ComplexNumber c0 = new ComplexNumber(1, 0);
//        System.out.println(c0.asin(c0));
        // Other options for e1 that can be interesting
//        Equation e1 = new Equation("\\log_{23.2}{7*x-3}");
//        Equation e1 = new Equation("x^2");
        Equation e1 = new Equation("x*\\sin{x}");
        // Print out three values of the equation for you to check and make sure that the function seems approximately right
        System.out.println(e1.evaluateEquation(new ComplexNumber(0,0), e1.length) + " @x=" + new ComplexNumber(0,0));
        System.out.println(e1.evaluateEquation(new ComplexNumber(1,0), e1.length) + " @x=" + new ComplexNumber(1,0));
        System.out.println(e1.evaluateEquation(new ComplexNumber(0,1), e1.length) + " @x=" + new ComplexNumber(0,1));
        //------------------------------ Equation Stuff ------------------------------\\

        //------------------------------ Sweep Stuff ------------------------------\\
        // Instantiate a sweepXValues class to solve the equation (e1) at x values from -10 to 10 real and 0 to 10 imaginary.
        // The detail 1 tells how many dots will be drawn and the detail 2 tells how many imaginary steps you can move through by scrolling.
        int grain = 100; // detail 2
        double minIm = 0.0;
        double maxIm = 10.0;
        SweepXValues sweepXValues = new SweepXValues(-10.0, 10.0, minIm,maxIm,10_000, grain,e1);
        final Integer[] bValue = {0};
        int bMax = sweepXValues.imaginaryValues;
        // The initial array of points
        final Vector[][] points = {sweepXValues.calculateYValuesVector(bValue[0])};
        //------------------------------ Sweep Stuff ------------------------------\\


        //------------------------------ Swing Stuff ------------------------------\\
        // Create the frame and give it a title
        JFrame frame = new JFrame("Complex Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 480);
        frame.setVisible(true);
        //------------------------------ Swing Stuff ------------------------------\\


        //------------------------------ Scene Stuff ------------------------------\\
        // Give the camera a starting position
        Vector camera = new Vector(2,1,0,1);
        // Test set of points that are easy to understand (used for debug)
//        Vector[] points = {new Vector(0,0,0,1), new Vector(1,0,0,1), new Vector(0,1,0,1), new Vector(0,0,1,1), new Vector(-1,0,0,1)};
        // I set all of these values to begin with, but nearly all of them can be updated later
        // For example, to zoom in I adjust the FOV
        Scene scene = new Scene(camera, 90, 3*Math.PI/2, 0, 0.1, 1000, points[0], 480, 720);
        DrawPoint drawPoint = new DrawPoint();
        drawPoint.points = new ArrayList<>();
        frame.add(drawPoint);
        //
        JTextField textField = new JTextField(20);
        textField.setText(" Im(x)=0");
        textField.setForeground(Color.BLUE);
        textField.setBounds(0, 0, 200, 30);
        frame.add(textField);
        // "Start Now" lets the user know that they can start to move in the scene
        // If they attempt to do something before this it might not work
        System.out.println("Start Now");
        //------------------------------ Scene Stuff ------------------------------\\

        // this code is called every time that a key is pressed
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                textField.repaint();
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    // remove the window VERY IMPORTANT
                    frame.dispose();
                } else if(e.getKeyCode() == KeyEvent.VK_H){
                    scene.moveCameraInCircle(0.1,  0);
                } else if(e.getKeyCode() == KeyEvent.VK_F){
                    scene.moveCameraInCircle(-0.1, 0);
                } else if(e.getKeyCode() == KeyEvent.VK_T){
                    scene.moveCameraInCircle(0,    0.1);
                } else if(e.getKeyCode() == KeyEvent.VK_G){
                    scene.moveCameraInCircle(0,   -0.1);
                } else if (e.getKeyCode() == KeyEvent.VK_Z) {
                    scene.FOV -= 1;
                    // zoom in
                } else if (e.getKeyCode() == KeyEvent.VK_X) {
                    scene.FOV += 1;
                }
                if (debugOn){
                    System.out.println(scene.camera.x + "," + scene.camera.y + "," + scene.camera.z + "\t Yaw=" + scene.yaw + " Pitch=" + scene.pitch);
                }
                // Here I create all the points and transform them depending on scene
                // I also create the axis which are useful for user reference (and just make it look better)
                Vector[] drawPoints = scene.drawFrame();
                drawPoint.points = new ArrayList<>();
                drawPoint.axisPoints = new ArrayList<>();
                // Loop through all the points that I created and add them to my drawing object
                for (Vector point : drawPoints){
                    drawPoint.addPoint((int)point.x, (int)point.y);
                }
                // Finally draw the points and axis
                Vector[] drawAxis = scene.drawAxis();
                drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[1].x , (int)drawAxis[0].y, (int)drawAxis[1].y);
                drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[3].x , (int)drawAxis[0].y, (int)drawAxis[3].y);
                drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[5].x , (int)drawAxis[0].y, (int)drawAxis[5].y);
                textField.repaint();
            }

        });

        // This is called each time that the mouseWheel changes (this can be from a mouse or trackpad)
        // I highly recommend using a mouse because it gives much higher precision
        frame.addMouseWheelListener(e -> {
            textField.repaint();
            int notches = e.getWheelRotation();
            bValue[0] += notches;
            if (bValue[0] < 0){
                bValue[0] = bMax-1; // loops up to the end
            } else if (bValue[0] >= bMax) {
                bValue[0] = 0; // loops back to the start
            }
            scene.points = sweepXValues.calculateYValuesVector(bValue[0]);
            textField.setText(" Im(x)=" + (((Integer.parseInt(bValue[0].toString()))/(double)grain)*(maxIm-minIm) + minIm));

            if (autoUpdate){
                if (debugOn){
                    System.out.println(scene.camera.x + "," + scene.camera.y + "," + scene.camera.z + "\t Yaw=" + scene.yaw + " Pitch=" + scene.pitch);
                }
                // This is the same code that is used in the keyListener
                Vector[] drawPoints = scene.drawFrame();
                drawPoint.points = new ArrayList<>();
                drawPoint.axisPoints = new ArrayList<>();
                for (Vector point : drawPoints){
                    drawPoint.addPoint((int)point.x, (int)point.y);
                }
                Vector[] drawAxis = scene.drawAxis();
                drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[1].x , (int)drawAxis[0].y, (int)drawAxis[1].y);
                drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[3].x , (int)drawAxis[0].y, (int)drawAxis[3].y);
                drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[5].x , (int)drawAxis[0].y, (int)drawAxis[5].y);
            } // end autoUpdate
            textField.repaint();
        }); // end wheelListener
    } // end main
} // end Main
