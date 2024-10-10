import org.jetbrains.annotations.NotNull;

import java.util.Scanner;
import javax.swing.*;


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
        
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        Equation e1 = new Equation(input);
        System.out.println(e1.tree.get(e1.length));
        System.out.println(e1.evaluateEquation(new ComplexNumber(0,0), e1.length) + " @x=" + new ComplexNumber(0,0));
        System.out.println(e1.evaluateEquation(new ComplexNumber(1,0), e1.length) + " @x=" + new ComplexNumber(1,0));
        System.out.println(e1.evaluateEquation(new ComplexNumber(0,1), e1.length) + " @x=" + new ComplexNumber(0,1));

//        SweepXValues sweepXValues = new SweepXValues(0,10,0,10,9,e1);
//        sweepXValues.calculateYValues();
//        System.out.println(sweepXValues.xValues);
//        System.out.println(sweepXValues.yValues);
    }

}
