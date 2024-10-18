// TODO: add in trig
// TODO: add in d/dx
// TODO: connect the dots created by visualization to make surfaces (this sounds especially hard)
// TODO: add in a way to visualize the infinite number of complex exponents
// TODO: add in integrals ? This seems very very difficult
//  ^something about u replacement could help (i don't know how to integral)


import java.util.ArrayList;
import java.util.Arrays;
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

        boolean autoUpdate = true;
        boolean debugOn = false;

        //------------------------------ Equation Stuff ------------------------------\\
        System.out.println("Hello world!");

        ComplexNumber c0 = new ComplexNumber(0,1);
        System.out.println(c0.gam(c0));
//        Equation e0 = new Equation("\\sin{x-\\tan{2}}");
//        System.out.println(e0);
//        System.out.println(e0.evaluateEquation(new ComplexNumber(1,0), e0.length));
//        Equation e1 = new Equation("\\log_{23.2}{7*x-3}");
//        Equation e1 = new Equation("x^2");
        Equation e1 = new Equation("\\gam{x}");
        System.out.println(e1.evaluateEquation(new ComplexNumber(0,0), e1.length) + " @x=" + new ComplexNumber(0,0));
        System.out.println(e1.evaluateEquation(new ComplexNumber(1,0), e1.length) + " @x=" + new ComplexNumber(1,0));
        System.out.println(e1.evaluateEquation(new ComplexNumber(0,1), e1.length) + " @x=" + new ComplexNumber(0,1));
        //------------------------------ Equation Stuff ------------------------------\\

        //------------------------------ Sweep Stuff ------------------------------\\
        SweepXValues sweepXValues = new SweepXValues(0, 2.0, 0.0,10,10000, 100,e1);
        final Integer[] bValue = {0};
        int bMax = sweepXValues.imaginaryValues;
        final Vector[][] points = {sweepXValues.calculateYValuesVector(bValue[0])};
        //------------------------------ Sweep Stuff ------------------------------\\


        //------------------------------ Swing Stuff ------------------------------\\
        JFrame frame = new JFrame("Complex Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 480);
        frame.setVisible(true);
        //------------------------------ Swing Stuff ------------------------------\\


        //------------------------------ Scene Stuff ------------------------------\\
        Vector camera = new Vector(2,1,0,1);
//        Vector[] points = {new Vector(0,0,0,1), new Vector(1,0,0,1), new Vector(0,1,0,1), new Vector(0,0,1,1), new Vector(-1,0,0,1)};
        Scene scene = new Scene(camera, 90, 3*Math.PI/2, 0, 0.1, 1000, points[0], 480, 720);
        DrawPoint drawPoint = new DrawPoint();
        drawPoint.points = new ArrayList<>();
        frame.add(drawPoint);
        System.out.println("Start Now");
        //------------------------------ Scene Stuff ------------------------------\\


        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                double theta1 = 0;
                double theta2 = 0;
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    // remove the window VERY IMPORTANT
                    frame.dispose();
//                } else if (e.getKeyCode() == KeyEvent.VK_W) {
//                    scene.camera.y += 0.1;
//                } else if (e.getKeyCode() == KeyEvent.VK_S) {
//                    scene.camera.y -= 0.1;
//                } else if (e.getKeyCode() == KeyEvent.VK_A) {
//                    scene.camera.x += 0.1;
//                } else if (e.getKeyCode() == KeyEvent.VK_D) {
//                    scene.camera.x -= 0.1;
//                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
//                    scene.camera.z += 0.1;
//                } else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
//                    scene.camera.z -= 0.1;
//                } else if(e.getKeyCode() == KeyEvent.VK_LEFT){
//                    scene.yaw +=0.1;
//                } else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
//                    scene.yaw -=0.1;
//                } else if(e.getKeyCode() == KeyEvent.VK_UP){
//                    scene.pitch +=0.1;
//                } else if(e.getKeyCode() == KeyEvent.VK_DOWN){
//                    scene.pitch -=0.1;
                } else if(e.getKeyCode() == KeyEvent.VK_H){
                    scene.moveCameraInCircle(0.1, 0);
                } else if(e.getKeyCode() == KeyEvent.VK_F){
                    scene.moveCameraInCircle(-0.1, 0);
                } else if(e.getKeyCode() == KeyEvent.VK_T){
                    scene.moveCameraInCircle(0, 0.1);
                } else if(e.getKeyCode() == KeyEvent.VK_G){
                    scene.moveCameraInCircle(0, -0.1);
                }
                if (debugOn){
                    System.out.println(scene.camera.x + "," + scene.camera.y + "," + scene.camera.z + "\t Yaw=" + scene.yaw + " Pitch=" + scene.pitch);
                }
                Vector[] drawPoints = scene.drawFrame();
                drawPoint.points = new ArrayList<>();
                drawPoint.axisPoints = new ArrayList<>();
//                System.out.println(Arrays.toString(drawPoints));
                for (Vector point : drawPoints){
                    drawPoint.addPoint((int)point.x, (int)point.y);
                }
                Vector[] drawAxis = scene.drawAxis();
//                System.out.println(Arrays.toString(drawAxis));
                drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[1].x , (int)drawAxis[0].y, (int)drawAxis[1].y);
                drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[3].x , (int)drawAxis[0].y, (int)drawAxis[3].y);
                drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[5].x , (int)drawAxis[0].y, (int)drawAxis[5].y);
            }
        });

        frame.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int notches = e.getWheelRotation();
                bValue[0] += notches;


                if (bValue[0] < 0){
                    bValue[0] = bMax-1;
                } else if (bValue[0] >= bMax) {
                    bValue[0] = 0; // loops
                }
//                System.out.println(Arrays.toString(bValue));
                scene.points = sweepXValues.calculateYValuesVector(bValue[0]);
//                System.out.println(Arrays.toString(scene.points));

                if (autoUpdate){
                    if (debugOn){
                        System.out.println(scene.camera.x + "," + scene.camera.y + "," + scene.camera.z + "\t Yaw=" + scene.yaw + " Pitch=" + scene.pitch);
                    }
                    Vector[] drawPoints = scene.drawFrame();
//                    System.out.println(Arrays.toString(drawPoints));
                    drawPoint.points = new ArrayList<>();
                    drawPoint.axisPoints = new ArrayList<>();
                    for (Vector point : drawPoints){
                        drawPoint.addPoint((int)point.x, (int)point.y);
                    }
                    Vector[] drawAxis = scene.drawAxis();
                    drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[1].x , (int)drawAxis[0].y, (int)drawAxis[1].y);
                    drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[3].x , (int)drawAxis[0].y, (int)drawAxis[3].y);
                    drawPoint.addAxis((int)drawAxis[0].x, (int)drawAxis[5].x , (int)drawAxis[0].y, (int)drawAxis[5].y);
                }

            }
        });



    }
}
