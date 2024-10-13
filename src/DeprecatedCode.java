import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Scanner;

public class DeprecatedCode {
    // from Equation.java
//    public int createTree(int start, int end){
//        // should return the index of the head of the tree
//        // P_E_MD_AS
//
//        StringBuilder partialEqP = new StringBuilder(); // idk what to call this
//        // start with parenthesis
//        int skip = 0;
//        for (int i = start; i < end; i++) {
//            if (eq.charAt(i) == '('){
//                partialEqP.append("{");
//                partialEqP.append(createTree(i+1, findCloseParenthesis(i)));
//                skip = findCloseParenthesis(i)-i;
//                partialEqP.append("}");
//            } else {
//                if (skip > 0){
//                    skip--;
//                }else{
//                    partialEqP.append(eq.charAt(i));
//                }
//            }
//        }
//
////        System.out.println(partialEqP + " P " + start + " Start " + length + " Length");
//
//
//        // next is exponents
//        String partialEqE = "";
//        skip = 0;
//        for (int i = 0; i < partialEqP.length(); i++) {
//            if(partialEqP.charAt(i) == '^'){
//                // go left till reach operator
//                String leftRaw = "";
//                for (int j = i-1; j > -1 && (!isOperator(partialEqP.charAt(j))); j--){
//                    leftRaw = partialEqP.charAt(j) + leftRaw;
//                }
//                // right until operator
//                String rightRaw = "";
//                for (int j = i+1; j < partialEqP.length() && !isOperator(partialEqP.charAt(j)); j++){
//                    rightRaw = partialEqP.charAt(j) + rightRaw;
//                }
//                // I turn these two "raw" values to new nodes or recognize that they are already nodes
//
//                addNodes(leftRaw, rightRaw, "^");
//
//                partialEqE = partialEqE.substring(0,i-leftRaw.length());
//                skip = rightRaw.length();
//                partialEqE = partialEqE + "{" + (tree.size()-1) + "}"; // the last node is the one that we just made, so we can assign the node at this location to be that head node
//            }
//            else{
//                if (skip <1) {
//                    partialEqE = partialEqE + partialEqP.charAt(i);
//                } else{
//                    skip--;
//                }
//            }
//        }
//
////        System.out.println(partialEqE + " Exp " + start + " Start " + length + " Length");
//
//        // next is multiplication and division
//        String partialEqMD = "";
//        skip = 0;
//        for (int i = 0; i < partialEqE.length(); i++) {
//            if(partialEqE.charAt(i) == '*'){
//                // go left till reach operator
//                String leftRaw = "";
//                for (int j = i-1; j > -1 && !isOperator(partialEqE.charAt(j)) ; j--){
//                    leftRaw = partialEqE.charAt(j) + leftRaw;
//                }
//                // right until operator
//                String rightRaw = "";
//                for (int j = i+1; j < partialEqE.length() && !isOperator(partialEqE.charAt(j)); j++){
//                    rightRaw = partialEqE.charAt(j) + rightRaw;
//                }
//                System.out.println("leftRaw:" + leftRaw + " rightRaw:" + rightRaw);
//                // I turn these two "raw" values to new nodes or recognize that they are already nodes
//
//                addNodes(leftRaw, rightRaw, "*");
//
//                partialEqMD = partialEqMD.substring(0,i-leftRaw.length());
//                skip = rightRaw.length();
//                partialEqMD = partialEqMD + "{" + (tree.size()-1) + "}"; // the last node is the one that we just made, so we can assign the node at this location to be that head node
//            } else if (partialEqE.charAt(i) == '/') {
//                // go left till reach operator
//                String leftRaw = "";
//                for (int j = i-1; j > -1 && !isOperator(partialEqE.charAt(j)); j--){
//                    leftRaw = partialEqE.charAt(j) + leftRaw;
//                }
//                // right until operator
//                String rightRaw = "";
//                for (int j = i+1; j < partialEqE.length() && !isOperator(partialEqE.charAt(j)); j++){
//                    rightRaw = partialEqE.charAt(j) + rightRaw;
//                }
//                // I turn these two "raw" values to new nodes or recognize that they are already nodes
//
//                addNodes(leftRaw, rightRaw, "/");
//
//                partialEqMD = partialEqMD.substring(0,i-leftRaw.length());
//                skip = rightRaw.length();
//                partialEqMD = partialEqMD + "{" + (tree.size()-1) + "}"; // the last node is the one that we just made, so we can assign the node at this location to be that head node
//            } else{
//                if (skip < 1){
//                    partialEqMD = partialEqMD + partialEqE.charAt(i);
//                }else {
//                    skip--;
//                }
//            }
//        }
//
////        System.out.println(partialEqMD + " MD " + start + " Start " + length + " Length");
//
//
//        // next is subtraction and addition
//        String partialEqSA = "";
//        skip = 0;
//        for (int i = 0; i < partialEqMD.length(); i++) {
//            if(partialEqMD.charAt(i) == '+'){
//                // go left till reach operator
//                String leftRaw = "";
//                for (int j = i-1; j > -1 && !isOperator(partialEqMD.charAt(j)); j--){
//                    leftRaw = partialEqMD.charAt(j) + leftRaw;
//                }
//                // right until operator
//                String rightRaw = "";
//                for (int j = i+1; j < partialEqMD.length() && !isOperator(partialEqMD.charAt(j)); j++){
//                    rightRaw = partialEqMD.charAt(j) + rightRaw;
//                }
//                // I turn these two "raw" values to new nodes or recognize that they are already nodes
//
//                addNodes(leftRaw, rightRaw, "+");
//
//                partialEqSA = partialEqSA.substring(0,i-leftRaw.length());
//                skip = rightRaw.length();
//                partialEqSA = partialEqSA + "{" + (tree.size()-1) + "}"; // the last node is the one that we just made, so we can assign the node at this location to be that head node
//            } else if (partialEqMD.charAt(i) == '-') {
//                // go left till reach operator
//                String leftRaw = "";
//                for (int j = i-1; j > -1 && !isOperator(partialEqMD.charAt(j)); j--){
//                    leftRaw = partialEqMD.charAt(j) + leftRaw;
//                }
//                // right until operator
//                String rightRaw = "";
//                for (int j = i+1; j < partialEqMD.length() && !isOperator(partialEqMD.charAt(j)); j++){
//                    rightRaw = partialEqMD.charAt(j) + rightRaw;
//                }
//                // I turn these two "raw" values to new nodes or recognize that they are already nodes
//
//                addNodes(leftRaw, rightRaw, "-");
//
//                partialEqSA = partialEqSA.substring(0,i-leftRaw.length());
//                skip = rightRaw.length();
//                partialEqSA = partialEqSA + "{" + (tree.size()-1) + "}"; // the last node is the one that we just made, so we can assign the node at this location to be that head node
//            } else {
//                if (skip < 1){
//                    partialEqSA = partialEqSA + partialEqMD.charAt(i);
//                }
//                else {
//                    skip--;
//                }
//            }
//        }
//
////        System.out.println(partialEqSA + " SA " + start + " Start " + length + " Length");
////        System.out.println(partialEqSA);
////        System.out.println("tree item" + (tree.size()-1));
//        return tree.size()-1;
////        return -1; // this means it failed
//    }
//    //


    // Equation.java
//    public int findCloseBrackets(int start){
//        int counter = 1; // this signifies that there is one open bracket (the one that we start with)
//        // once we find a close bracket we will decrement this, once we find an open bracket we increment
//        for (int i = start+1; i < eq.length(); i++){
//            if(eq.charAt(i) == '['){
//                counter++;
//            }
//            else if(eq.charAt(i) == ']'){
//                counter--;
//            }
//
//            // close has been found
//            if (counter == 0){
//                return i;
//            }
//        }
//        return -1; // this is if the close can't be found, this should result in the user fixing their input
//    }

//    // Main.java
//    SweepXValues sweepXValues = new SweepXValues(0,10,0,10,9,e1);
//        sweepXValues.calculateYValues();
//        System.out.println(sweepXValues.xValues);
//        System.out.println(sweepXValues.yValues);
//        ComplexNumber c1 = new  ComplexNumber(23.2,0);
//        ComplexNumber c2 = new  ComplexNumber(4,0);
//        ComplexNumber c3 = c2.log(c1, new ComplexNumber(1,0));
//        System.out.println(c3);


    // Main.java
//    Scanner scanner = new Scanner(System.in);
//    String input = scanner.nextLine();
//        scanner.close();

// FULL MOUSE CONFINER (THIS WAS NEVER USED AND WRITTEN IN PART BY AI)
//    import java.awt.*;
//import java.awt.event.*;
//
//    class MouseConfiner implements MouseMotionListener {
//        private final Rectangle bounds;
//
//        public MouseConfiner(Rectangle bounds) {
//            this.bounds = bounds;
//        }
//
//        @Override
//        public void mouseDragged(MouseEvent e) {
//            // Handle mouse dragging if needed
//            Point p = e.getPoint();
//            // Calculate the closest point within the bounds
//            int x = Math.min(Math.max(p.x, bounds.x), bounds.x + bounds.width - 1);
//            int y = Math.min(Math.max(p.y, bounds.y), bounds.y + bounds.height - 1);
//            p.setLocation(x, y);
//            setCursor(p); // Set the cursor to the calculated point
//
//        }
//
//        @Override
//        public void mouseMoved(MouseEvent e) {
//            Point p = e.getPoint();
//            // Calculate the closest point within the bounds
//            int x = Math.min(Math.max(p.x, bounds.x), bounds.x + bounds.width - 1);
//            int y = Math.min(Math.max(p.y, bounds.y), bounds.y + bounds.height - 1);
//            p.setLocation(x, y);
//            setCursor(p); // Set the cursor to the calculated point
//        }
//
//        private void setCursor(Point p) {
//            // Set the cursor to the calculated point
//            PointerInfo pointerInfo = MouseInfo.getPointerInfo();
//            Robot robot = null;
//            try {
//                robot = new Robot();
//                robot.mouseMove(p.x, p.y);
//            } catch (AWTException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }


    // OTHER MOUSE STUFF THAT WAS IN MAIN.java
// Transparent 16 x 16 pixel cursor image.
//    BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
//
//    // Create a new blank cursor.
//    Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
//
//    // Set the blank cursor to the JFrame.
//        frame.getContentPane().setCursor(blankCursor);


}
