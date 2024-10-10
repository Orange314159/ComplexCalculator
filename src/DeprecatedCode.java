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


}
