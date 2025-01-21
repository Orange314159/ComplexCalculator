// TODO: add in different "modes", different ways to represent the points
// TODO: connect the dots created by visualization to make surfaces (this sounds especially hard)



import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;



public class Main {
    public static void main(String[] args) {
        // debugON is can be set to true if you wish for more verbose prints (includes player x.y.z,pitch,roll, etc)

        //------------------------------ Equation Stuff ------------------------------\\
        System.out.println("Hello world!"); // this is still here to make sure that the code actually runs and isn't broken
        // Testing Derivative function
//        Equation e0 = new Equation("\\sin{x^2}");
//        System.out.println(e0.tree.get(e0.length));
//        System.out.println(e0.evaluateNode(new ComplexNumber(0,0), e0.tree.get(e0.length)));
//        System.out.println(e0.evaluateNode(new ComplexNumber(1,0), e0.tree.get(e0.length)));
//        System.out.println(e0.evaluateNode(new ComplexNumber(2,0), e0.tree.get(e0.length)));
//        System.out.println(e0.createDerivativeNode(e0.tree.get(e0.length)));
//        System.out.println(e0.evaluateNode(new ComplexNumber(12,0), e0.createDerivativeNode(e0.tree.get(e0.length))));
//        System.out.println(e0.ddxTree);
        // Testing of Cleaning Function

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
        int grain = 10; // detail 2
        double minIm = 0.0;
        double maxIm = 10.0;
        final short[] mode = {0}; // mode 0 = normal (single line), mode 1 = multiple lines, mode 2 = average line
        SweepXValues sweepXValues = new SweepXValues(-10.0, 10.0, minIm,maxIm,5_000, grain,e1);
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

        JLabel jLabel = new JLabel("");

        JLabel equationTitle = new JLabel(e1.title());

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
        drawPoint.add(equationTitle);
        drawPoint.add(jLabel);

        frame.setResizable(false);

        // "Start Now" lets the user know that they can start to move in the scene
        // If they attempt to do something before this it might not work
        System.out.println("Start Now");
        //------------------------------ Scene Stuff ------------------------------\\

        // this code is called every time that a key is pressed
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
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
                } else if (e.getKeyCode() == KeyEvent.VK_M) {
                    if (mode[0] == 1){
                        mode[0] = 0;
                    } else {
                        mode[0] = 1;
                    }
                }

                String rnd = (((bValue[0]) / (double) grain) * (maxIm - minIm) + minIm) + "0000000000";
                jLabel.setText("Im(x)=" + rnd.substring(0,4));

                if (mode[0] == 0){
                    // Here I create all the points and transform them depending on scene
                    // I also create the axis which are useful for user reference (and just make it look better)
                    Vector[] drawPoints = scene.drawFrame();
                    drawPoint.points = new ArrayList<>();
                    drawPoint.colors = new ArrayList<>();
                    drawPoint.axisPoints = new ArrayList<>();
                    // Loop through all the points that I created and add them to my drawing object
                    for (Vector point : drawPoints){
                        drawPoint.addPoint((int)point.x, (int)point.y, 0);
                    }
                    // Finally draw the points and axis
                    Vector[] drawAxis = scene.drawAxis();
                    drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[1].x , (int)drawAxis[0].y, (int)drawAxis[1].y);
                    drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[3].x , (int)drawAxis[0].y, (int)drawAxis[3].y);
                    drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[5].x , (int)drawAxis[0].y, (int)drawAxis[5].y);
                } else {
                    scene.points = sweepXValues.calculateYValuesVector(bValue[0]);
                    Vector[] drawPoints = scene.drawFrame();
                    drawPoint.points = new ArrayList<>();
                    drawPoint.colors = new ArrayList<>();
                    drawPoint.axisPoints = new ArrayList<>();
                    for (Vector point : drawPoints){
                        drawPoint.addPoint((int)point.x, (int)point.y, 0);
                    }

                    for (int i = 0; i < 4; i++) {
                        bValue[0] += 1;
                        if (bValue[0] >= bMax) {
                            bValue[0] = 0;
                        }
                        scene.points = sweepXValues.calculateYValuesVector(bValue[0]);
                        drawPoints = scene.drawFrame();
                        for (Vector point : drawPoints){
                            drawPoint.addPoint((int)point.x, (int)point.y, i%2+1);
                        }
                    }

                    bValue[0] -=4;
                    if (bValue[0] < 0){
                        bValue[0] += bMax; // loops up to the end
                    } else if (bValue[0] >= bMax) {
                        bValue[0] -= bMax; // loops back to the start
                    }


                    Vector[] drawAxis = scene.drawAxis();
                    drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[1].x , (int)drawAxis[0].y, (int)drawAxis[1].y);
                    drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[3].x , (int)drawAxis[0].y, (int)drawAxis[3].y);
                    drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[5].x , (int)drawAxis[0].y, (int)drawAxis[5].y);
                } // end mode check
            } // end key pressed
        }); // end key check

        // This is called each time that the mouseWheel changes (this can be from a mouse or trackpad)
        // I highly recommend using a mouse because it gives much higher precision
        frame.addMouseWheelListener(e -> {
            int notches = e.getWheelRotation();
            bValue[0] += notches;
            if (bValue[0] < 0){
                bValue[0] = bMax-1; // loops up to the end
            } else if (bValue[0] >= bMax) {
                bValue[0] = 0; // loops back to the start
            }
            scene.points = sweepXValues.calculateYValuesVector(bValue[0]);
            String rnd = (((bValue[0]) / (double) grain) * (maxIm - minIm) + minIm) + "0000000000";
            jLabel.setText("Im(x)=" + rnd.substring(0,4));

            // This is the same code that is used in the keyListener
            if (mode[0] == 0){
                // Here I create all the points and transform them depending on scene
                // I also create the axis which are useful for user reference (and just make it look better)
                Vector[] drawPoints = scene.drawFrame();
                drawPoint.points = new ArrayList<>();
                drawPoint.colors = new ArrayList<>();
                drawPoint.axisPoints = new ArrayList<>();
                // Loop through all the points that I created and add them to my drawing object
                for (Vector point : drawPoints){
                    drawPoint.addPoint((int)point.x, (int)point.y, 0);
                }
                // Finally draw the points and axis
                Vector[] drawAxis = scene.drawAxis();
                drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[1].x , (int)drawAxis[0].y, (int)drawAxis[1].y);
                drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[3].x , (int)drawAxis[0].y, (int)drawAxis[3].y);
                drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[5].x , (int)drawAxis[0].y, (int)drawAxis[5].y);
            } else {
                scene.points = sweepXValues.calculateYValuesVector(bValue[0]);
                Vector[] drawPoints = scene.drawFrame();
                drawPoint.points = new ArrayList<>();
                drawPoint.colors = new ArrayList<>();
                drawPoint.axisPoints = new ArrayList<>();
                for (Vector point : drawPoints){
                    drawPoint.addPoint((int)point.x, (int)point.y, 0);
                }

                for (int i = 0; i < 4; i++) {
                    bValue[0] += 1;
                    if (bValue[0] >= bMax) {
                        bValue[0] = 0;
                    }
                    scene.points = sweepXValues.calculateYValuesVector(bValue[0]);
                    drawPoints = scene.drawFrame();
                    for (Vector point : drawPoints){
                        drawPoint.addPoint((int)point.x, (int)point.y, i%2+1);
                    }
                }

                bValue[0] -=4;
                if (bValue[0] < 0){
                    bValue[0] += bMax; // loops up to the end
                } else if (bValue[0] >= bMax) {
                    bValue[0] -= bMax; // loops back to the start
                }


                Vector[] drawAxis = scene.drawAxis();
                drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[1].x , (int)drawAxis[0].y, (int)drawAxis[1].y);
                drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[3].x , (int)drawAxis[0].y, (int)drawAxis[3].y);
                drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[5].x , (int)drawAxis[0].y, (int)drawAxis[5].y);
            } // end mode check
        }); // end wheelListener
    } // end main
} // end Main
