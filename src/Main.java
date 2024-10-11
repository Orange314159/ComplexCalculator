import org.jetbrains.annotations.NotNull;
// TODO: add in latex support for fractions (\frac{n}{d})
//
// TODO: add in latex support for exponents (x^{y})
// TODO: add in latex support for logarithms (log_{b}{a})
// TODO: add in graphical view of inputs (2d)
// TODO: add in graphical view of output (2d)
// TODO: put last two parts together to create a 4d representation of graph
// TODO: connect the dots created by visualization to make surfaces (this sounds especially hard)
// TODO: add in a way to visualize the infinite number of complex exponents


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
//        System.out.println(e1.tree.get(e1.length));
//        e1.printTree();
        System.out.println(e1.evaluateEquation(new ComplexNumber(0,0), e1.length) + " @x=" + new ComplexNumber(0,0));
        System.out.println(e1.evaluateEquation(new ComplexNumber(1,0), e1.length) + " @x=" + new ComplexNumber(1,0));
        System.out.println(e1.evaluateEquation(new ComplexNumber(0,1), e1.length) + " @x=" + new ComplexNumber(0,1));

//        e1.createTreeSpecialFunctions("\\frac{23.2}{7*x-3}");

//        SweepXValues sweepXValues = new SweepXValues(0,10,0,10,9,e1);
//        sweepXValues.calculateYValues();
//        System.out.println(sweepXValues.xValues);
//        System.out.println(sweepXValues.yValues);
    }

}
