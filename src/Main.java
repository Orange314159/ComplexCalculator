import org.jetbrains.annotations.NotNull;

// TODO: put last two parts together to create a 4d representation of graph
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
// TODO: add in integrals ?

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
        Equation e1 = new Equation("\\\\log_{23.2}{7*x-3}");
//        System.out.println(e1.tree.get(e1.length));
//        e1.printTree();
        System.out.println(e1.evaluateEquation(new ComplexNumber(0,0), e1.length) + " @x=" + new ComplexNumber(0,0));
        System.out.println(e1.evaluateEquation(new ComplexNumber(1,0), e1.length) + " @x=" + new ComplexNumber(1,0));
        System.out.println(e1.evaluateEquation(new ComplexNumber(0,1), e1.length) + " @x=" + new ComplexNumber(0,1));

//        e1.createTreeSpecialFunctions("\\log_{23.2}{7*x-3}");
//        e1.printTree();
//        System.out.println(e1.tree.get(e1.tree.size()-1));


//        SweepXValues sweepXValues = new SweepXValues(0,10,0,10,9,e1);
//        sweepXValues.calculateYValues();
//        System.out.println(sweepXValues.xValues);
//        System.out.println(sweepXValues.yValues);
//        ComplexNumber c1 = new  ComplexNumber(23.2,0);
//        ComplexNumber c2 = new  ComplexNumber(4,0);
//        ComplexNumber c3 = c2.log(c1, new ComplexNumber(1,0));
//        System.out.println(c3);
    }

}
