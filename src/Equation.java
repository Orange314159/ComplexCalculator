import java.util.ArrayList;
public class Equation {
    // important note:
    // When creating the tree I use "~" as a delimiter for pointers DO NOT USE "~" IN YOUR EQUATIONS

    public String eq;
    public int length = -1;
    private static final char[] operators = {'+', '-', '*', '/', '^'};
    ArrayList<Node> tree = new ArrayList<>();
    ArrayList<Node> ddxTree = new ArrayList<>(); // this is a tree of the derivative

    public Equation (String in){
        eq = in;
        removeSpaces();
        includeOmittedMultiplication();
//        System.out.println(eq + "CLEAN");
        length = createTreeSpecialFunctions(eq);
        ddxTree.add(createDerivativeNode(this.tree.get(this.length))); // include first derivative
        ddxTree.add(createDerivativeNode(ddxTree.get(0))); // second derivative
        ddxTree.add(createDerivativeNode(ddxTree.get(1))); // third
        ddxTree.add(createDerivativeNode(ddxTree.get(2))); // fourth (I stop here but this is arbitrary, and you could implement further derivation)
    }

    public int findCloseParenthesis(int start, String input){
        int counter = 1; // this signifies that there is one open parenthesis (the one that we start with)
        // once we find a close parenthesis we will decrement this, once we find an open parenthesis we increment
        for (int i = start+1; i < input.length(); i++){
            if(eq.charAt(i) == '('){
                counter++;
            }
            else if(input.charAt(i) == ')'){
                counter--;
            }

            // close has been found
            if (counter == 0){
                return i;
            }
        }
        return -1; // this is if the close can't be found, this should result in the user fixing their input
    }
    public int findCloseBrace(int start, String input){
        int counter = 1;
        for (int i = start+1; i < input.length(); i++){
            if(input.charAt(i) == '{'){
                counter++;
            }
            else if(input.charAt(i) == '}'){
                counter--;
            }
            // close has been found
            if (counter == 0){
                return i;
            }
        }
        return -1; // this is if the close can't be found, this should result in the user fixing their input
    }

    public void removeSpaces(){
        eq = eq.replace(" ", "");
    }

    public void includeOmittedMultiplication(){
        int j;
        for (int i = 0; i < eq.length()-1; i++){
            j=i+1;
            // I use two different if statements because two numbers in a row are not being multiplied (ex 24 != 2*4)
            if ((eq.charAt(i) == 'x' || eq.charAt(i) == 'y' || eq.charAt(i) == 'z' || Character.isDigit(eq.charAt(i)) || eq.charAt(i) == ')' || eq.charAt(i) == 'i')     &&     (eq.charAt(j) == 'x' || eq.charAt(j) == 'y' || eq.charAt(j) == 'z' || eq.charAt(j) == '(' || eq.charAt(j) == 'i')){
                StringBuilder temp = new StringBuilder(eq);
                temp.insert(j, '*');
                eq = temp.toString();

            } else if ((eq.charAt(i) == 'x' || eq.charAt(i) == 'y' || eq.charAt(i) == 'z' || eq.charAt(i) == ')' || eq.charAt(i) == 'i')     &&     (eq.charAt(j) == 'x' || eq.charAt(j) == 'y' || eq.charAt(j) == 'z' || eq.charAt(j) == '(' || Character.isDigit(eq.charAt(j)) || eq.charAt(j) == 'i')){
                StringBuilder temp = new StringBuilder(eq);
                temp.insert(j, '*');
                eq = temp.toString();

            } else if (eq.charAt(i) == '}' && (eq.charAt(j) == 'x' || eq.charAt(j) == 'y' || eq.charAt(j) == 'z' || Character.isDigit(eq.charAt(j)) || eq.charAt(j) == '(' || eq.charAt(i=j) == 'i')) {
                StringBuilder temp = new StringBuilder(eq);
                temp.insert(j, '*');
                eq = temp.toString();
            }
            // Debug
//            System.out.println("i:" + i + " - " + eq.charAt(i) + " eq:" + eq + " j:"+j + " - " + eq.charAt(j));
        }
    }

    public int createTreeSpecialFunctions(String input){
        // this is similar to that of createTreeParenthesis but is about \frac{a} or \log{a}
        // partialEqSP might be the most important variable in this method and the next few
        // the idea behind this is that it will be equal to the String "input" EXCEPT in places where a function is
        // in this case the function will add in a signifier "~" and an integer to refer to a node in tree that is added (this is not to be confused with a reference like what would be found in a language like C++)
        // it will then skip adding in the characters from the function and just add in this signifier integer combination
        // in addition, I use a stringBuilder class to make the addition of strings more clear and efficient
        // all of these functions will be denoted with a "\" before the function name in order to signal that it's a function
        StringBuilder partialEqSP = new StringBuilder();
        int skip = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '\\'){
                if(input.startsWith("frac", i+1) && skip <1){
                    // this is a fraction
                    // "frac" is 4 characters, so we expect an open brace immediately after (5 chars - i+5)
                    if (input.charAt(i+5) == '{'){
                        // I will make a "leftTree" and "rightTree" which are integers that refer to the location of a node in the array "tree" in this class
                        int leftTree = createTreeSpecialFunctions(input.substring(i+5,findCloseBrace(i+5, input)+1)); // this is a recursive call that will look for special function inside the function
                        int rightTree = 0;
                        if (input.charAt(findCloseBrace(i+5, input)+1) == '{'){
                            rightTree = createTreeSpecialFunctions(input.substring(findCloseBrace(i+5, input)+1, findCloseBrace(findCloseBrace(i+5, input)+1, input)+1));
                        } else {
                            // This is bad
                            System.out.println("ERROR: Missing Second End Brace in a Fraction");
                        }
                        // turns the integer values into strings with tildes to denote that it is reference
                        String leftRaw  = "~" + leftTree  + "~";
                        String rightRaw = "~" + rightTree + "~";
                        // adding nodes is very repetitive
                        addNodes(leftRaw,rightRaw,"/");
                        partialEqSP.append("~").append(tree.size() - 1).append("~");
                    } else {
                        // also very Bad
                        System.out.println("ERROR: No End Brace Found In A Fraction");
                    }
                    // This is how many characters we have to skip in the input (I see the +1 -1, but I left it like that for readability)
                    skip = findCloseBrace(findCloseBrace(i+5, input)+1, input)+1 - i-1;
                } else if (input.startsWith("log_", i+1) && skip <1){
                    // this is very similar to fraction but with log (reference the above section if you don't understand any part of the code here)
                    if (input.charAt(i+5) == '{'){
                        int leftTree = createTreeSpecialFunctions(input.substring(i+5,findCloseBrace(i+5, input)+1));
                        int rightTree = 0;
                        if (input.charAt(findCloseBrace(i+5, input)+1) == '{'){
                            rightTree = createTreeSpecialFunctions(input.substring(findCloseBrace(i+5, input)+1, findCloseBrace(findCloseBrace(i+5, input)+1, input)+1));
                        } else {
                            System.out.println("ERROR: Missing Second End Brace in a Fraction");
                        }
                        String leftRaw  = "~" + leftTree  + "~";
                        String rightRaw = "~" + rightTree + "~";
                        addNodes(leftRaw,rightRaw,"log");
                        partialEqSP.append("~").append(tree.size() - 1).append("~");
                    } else {
                        tree.add(new Node("", new ComplexNumber(input.substring(i+1,i+2))));
                    }
                    skip = findCloseBrace(findCloseBrace(i+5, input)+1, input)+1 - i-1;
                } else if (input.startsWith("log", i+1) && skip <1){
                    // this is a ln, again similar to log_ and frac
                    if (input.charAt(i+4) == '{'){
                        int leftTree = createTreeSpecialFunctions(input.substring(i+5,findCloseBrace(i+5, input)+1));
                        int rightTree = 0;
                        if (input.charAt(findCloseBrace(i+4, input)+1) == '{'){
                            rightTree = createTreeSpecialFunctions(input.substring(findCloseBrace(i+4, input)+1, findCloseBrace(findCloseBrace(i+4, input)+1, input)+1));
                        } else {
                            System.out.println("ERROR: Missing Second End Brace in a Fraction");
                        }
                        String leftRaw  = "~" + leftTree  + "~";
                        String rightRaw = "~" + rightTree + "~";
                        addNodes(leftRaw,rightRaw,"log");
                        partialEqSP.append("~").append(tree.size() - 1).append("~");
                    } else {
                        tree.add(new Node("", new ComplexNumber(input.substring(i+1,i+2))));
                    }
                    skip = findCloseBrace(findCloseBrace(i+5, input)+1, input)+1 - i-1;
                }else if ((input.startsWith("asinh", i+1) || input.startsWith("acosh", i+1) || input.startsWith("atanh", i+1)|| input.startsWith("acoth", i+1)|| input.startsWith("asech", i+1)|| input.startsWith("acsch", i+1))&& skip <1) {
                    // any three letter function (I attempt to make all of my functions like this)
                    String functionName = input.substring(i+1,i+6);
//                    System.out.println(input + "  " + input.substring(i+6,findCloseBrace(i+5, input)));
                    if (input.charAt(i+6) == '{'){
                        int subTree = createTreeSpecialFunctions(input.substring(i+7,findCloseBrace(i+6, input)));
                        tree.add(new Node(functionName, tree.get(subTree), new Node()));
                        partialEqSP.append("~").append(tree.size() - 1).append("~");
                    } else {
                        System.out.println("Error");
                        System.out.println(input.charAt(i+6));
                    }
                    skip = findCloseBrace(i+6, input) - i;
                } else if ((input.startsWith("sinh", i+1) || input.startsWith("cosh", i+1) || input.startsWith("tanh", i+1)|| input.startsWith("coth", i+1)|| input.startsWith("sech", i+1)|| input.startsWith("csch", i+1) || input.startsWith("asin", i+1) || input.startsWith("acos", i+1) || input.startsWith("atan", i+1) || input.startsWith("acot", i+1) || input.startsWith("asec", i+1) || input.startsWith("acsc", i+1))&& skip <1) {
                    // any hyperbolic trig function (they are all very similar and take equal characters to represent, so I will lump them all here)
                    String functionName = input.substring(i+1,i+5);
                    if (input.charAt(i+5) == '{'){
                        int subTree = createTreeSpecialFunctions(input.substring(i+6,findCloseBrace(i+5, input)));
                        tree.add(new Node(functionName, tree.get(subTree), new Node()));
                        partialEqSP.append("~").append(tree.size() - 1).append("~");
                    } else {
                        System.out.println("Error");
                    }
                    skip = findCloseBrace(i+5, input) - i;
                } else if ((input.startsWith("sin", i+1) || input.startsWith("cos", i+1) || input.startsWith("abs", i+1) || input.startsWith("tan", i+1)|| input.startsWith("cot", i+1)|| input.startsWith("sec", i+1)|| input.startsWith("csc", i+1) || input.startsWith("gam", i+1))&& skip <1) {
                    // any three letter function (I attempt to make all of my functions like this)
                    String functionName = input.substring(i+1,i+4);
//                    System.out.println(input + "  " + input.substring(i+6,findCloseBrace(i+5, input)));
                    if (input.charAt(i+4) == '{'){
                        int subTree = createTreeSpecialFunctions(input.substring(i+5,findCloseBrace(i+4, input)));
                        tree.add(new Node(functionName, tree.get(subTree), new Node()));
                        partialEqSP.append("~").append(tree.size() - 1).append("~");
                    } else {
                        System.out.println("Error");
                    }
                    skip = findCloseBrace(i+4, input) - i;
                }
            } else {
                if (skip > 0){
                    skip--;
                }else{
                    // add in the regular character to partialEqSP
                    partialEqSP.append(input.charAt(i));
                }
            }
        }
        return createTreeParenthesis(partialEqSP.toString());
    }

    public int createTreeParenthesis(String input){
        // this is another stringBuilder like in createTreeSpecialFunctions
        // if you don't understand the purpose of it I recommend you read the comments in createTreeSpecialFunctions
        StringBuilder partialEqP = new StringBuilder();
        // start with parenthesis (I allow parenthesis and curly braces) (you should really just use parenthesis though, I prefer braces for functions)
        int skip = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '('){
                partialEqP.append("~");
                partialEqP.append(createTreeParenthesis(input.substring(i+1,findCloseParenthesis(i, input))));
                skip = findCloseParenthesis(i, input)-i;
                partialEqP.append("~");
            } else if (input.charAt(i) == '{'){
                partialEqP.append("~");
                partialEqP.append(createTreeParenthesis(input.substring(i+1,findCloseBrace(i, input))));
                skip = findCloseBrace(i, input)-i;
                partialEqP.append("~");
            } else {
                if (skip > 0){
                    skip--;
                }else{
                    partialEqP.append(input.charAt(i));
                }
            }
        }
        return createTreeExponents(partialEqP.toString());
    }

    public int createTreeExponents(String input){
        // refer to createTreeSpecialFunctions for an explanation of partialEQ
        StringBuilder partialEqE = new StringBuilder();
        int skip = 0;
        // I will loop through the entire input until I have not found an exponent
        // foundOne is set to true if the code finds at least one exponent, this causes the method to run again
        boolean foundOne = false;
        for (int i = 0; i < input.length(); i++) {
            if(input.charAt(i) == '^' && !foundOne){
                // I create "leftRaw" and "rightRaw" to be strings that include either a reference to another node or some number / "x"
                // go left till reach operator
                StringBuilder leftRaw = new StringBuilder();
                for (int j = i-1; j > -1 && (isNotOperator(input.charAt(j))); j--){
                    leftRaw.insert(0, input.charAt(j));
                }
                // right until operator
                StringBuilder rightRaw = new StringBuilder();
                for (int j = i+1; j < input.length() && isNotOperator(input.charAt(j)); j++){
                    rightRaw.append(input.charAt(j));
                }
                // I turn these two "raw" values to new nodes or recognize that they are already nodes, they would include "~" if they are nodes

                addNodes(leftRaw.toString(), rightRaw.toString(), "^");

                partialEqE = new StringBuilder(partialEqE.substring(0, i - leftRaw.length()));
                skip = rightRaw.length();
                foundOne = true;
                partialEqE.append("~").append(tree.size() - 1).append("~"); // the last node is the one that we just made, so we can assign the node at this location to be that head node
            }
            else{
                if (skip <1) {
                    partialEqE.append(input.charAt(i));
                } else{
                    skip--;
                }
            }
        }
        if (foundOne){
            return createTreeExponents(partialEqE.toString()); // recursive
        } else {
            return createTreeMultDiv(partialEqE.toString()); // move on to the next step
        }
    }

    public int createTreeMultDiv(String input){
        // refer to createTreeSpecialFunctions for an explanation of partialEQ
        // very similar to the exponents but just two functions here
        StringBuilder partialEqMD = new StringBuilder();
        int skip = 0;
        boolean found = false;
        for (int i = 0; i < input.length(); i++) {
            if(input.charAt(i) == '*' && !found){
                // go left till reach operator
                StringBuilder leftRaw = new StringBuilder();
                for (int j = i-1; j > -1 && isNotOperator(input.charAt(j)) ; j--){
                    leftRaw.insert(0, input.charAt(j));
                }
                // right until operator
                StringBuilder rightRaw = new StringBuilder();
                for (int j = i+1; j < input.length() && isNotOperator(input.charAt(j)); j++){
                    rightRaw.append(input.charAt(j));
                }
                // I turn these two "raw" values to new nodes or recognize that they are already nodes

                addNodes(leftRaw.toString(), rightRaw.toString(), "*");

                partialEqMD = new StringBuilder(partialEqMD.substring(0, i - leftRaw.length()));
                skip = rightRaw.length();
                found = true;
                partialEqMD.append("~").append(tree.size() - 1).append("~"); // the last node is the one that we just made, so we can assign the node at this location to be that head node
            } else if (input.charAt(i) == '/' && !found) {
                // go left till reach operator
                StringBuilder leftRaw = new StringBuilder();
                for (int j = i-1; j > -1 && isNotOperator(input.charAt(j)); j--){
                    leftRaw.insert(0, input.charAt(j));
                }
                // right until operator
                StringBuilder rightRaw = new StringBuilder();
                for (int j = i+1; j < input.length() && isNotOperator(input.charAt(j)); j++){
                    rightRaw.append(input.charAt(j));
                }
                // I turn these two "raw" values to new nodes or recognize that they are already nodes

                addNodes(leftRaw.toString(), rightRaw.toString(), "/");

                partialEqMD = new StringBuilder(partialEqMD.substring(0, i - leftRaw.length()));
                skip = rightRaw.length();
                found = true;
                partialEqMD.append("~").append(tree.size() - 1).append("~"); // the last node is the one that we just made, so we can assign the node at this location to be that head node

            } else{
                if (skip < 1){
                    partialEqMD.append(input.charAt(i));
                }else {
                    skip--;
                }
            }
        } // end for

        if(found){
            return createTreeMultDiv(partialEqMD.toString());
        } else {
            return createTreeAddSub(partialEqMD.toString());
        }

    }

    public int createTreeAddSub(String input){
        // refer to createTreeSpecialFunctions for an explanation of partialEQ
        StringBuilder partialEqSA = new StringBuilder();
        int skip = 0;
        boolean found = false;
        for (int i = 0; i < input.length(); i++) {
            if(input.charAt(i) == '+' && !found){
                // go left till reach operator
                StringBuilder leftRaw = new StringBuilder();
                for (int j = i-1; j > -1 && isNotOperator(input.charAt(j)); j--){
                    leftRaw.insert(0, input.charAt(j));
                }
                // right until operator
                StringBuilder rightRaw = new StringBuilder();
                for (int j = i+1; j < input.length() && isNotOperator(input.charAt(j)); j++){
                    rightRaw.append(input.charAt(j));
                }
                // I turn these two "raw" values to new nodes or recognize that they are already nodes
//                System.out.println(leftRaw + " LEFT AND RIGHT" + rightRaw);
                addNodes(leftRaw.toString(), rightRaw.toString(), "+");

                partialEqSA = new StringBuilder(partialEqSA.substring(0, i - leftRaw.length()));
                skip = rightRaw.length();
                found = true;
                partialEqSA.append("~").append(tree.size() - 1).append("~"); // the last node is the one that we just made, so we can assign the node at this location to be that head node
            } else if (input.charAt(i) == '-' && !found) {
                // go left till reach operator
                StringBuilder leftRaw = new StringBuilder();
                for (int j = i-1; j > -1 && isNotOperator(input.charAt(j)); j--){
                    leftRaw.insert(0, input.charAt(j));
                }
                // right until operator
                StringBuilder rightRaw = new StringBuilder();
                for (int j = i+1; j < input.length() && isNotOperator(input.charAt(j)); j++){
                    rightRaw.append(input.charAt(j));
                }
                // I turn these two "raw" values to new nodes or recognize that they are already nodes

                addNodes(leftRaw.toString(), rightRaw.toString(), "-");

                partialEqSA = new StringBuilder(partialEqSA.substring(0, i - leftRaw.length()));
                skip = rightRaw.length();
                found = true;
                partialEqSA.append("~").append(tree.size() - 1).append("~"); // the last node is the one that we just made, so we can assign the node at this location to be that head node
            } else {
                if (skip < 1){
                    partialEqSA.append(input.charAt(i));
                }
                else {
                    skip--;
                }
            }
        }
//        System.out.println(input + "_SA_" +partialEqSA);
        if (found){
            return createTreeAddSub(partialEqSA.toString());
        } else {
            if(!partialEqSA.toString().contains("~")){
                tree.add(new Node("", new ComplexNumber(partialEqSA.toString())));
            }
            return tree.size()-1;
        }
    }

    public void addNodes(String leftStr, String rightStr, String operation){
        // I check for each left and right to be a reference and then create new nodes accordingly and repurpose the nodes that have already been created to be child nodes of the new node that I create
        if (leftStr.contains("~") && rightStr.contains("~")){ // both nodes have already been created
            tree.add(new Node(operation, tree.get(Integer.parseInt(leftStr.substring(1,leftStr.length()-1))), tree.get(Integer.parseInt(rightStr.substring(1,rightStr.length()-1))) ));
        } else if (leftStr.contains("~")){ // left node exists
            tree.add(new Node("", new ComplexNumber(rightStr)));
            tree.add(new Node(operation, tree.get(Integer.parseInt(leftStr.substring(1,leftStr.length()-1))), tree.get(tree.size()-1)));
        } else if (rightStr.contains("~")){ // right node exists
            tree.add(new Node("", new ComplexNumber(leftStr)));
            tree.add(new Node(operation, tree.get(tree.size()-1), tree.get(Integer.parseInt(rightStr.substring(1,rightStr.length()-1)))));
        } else { // neither node exists
            tree.add(new Node("", new ComplexNumber(leftStr)));
            tree.add(new Node("", new ComplexNumber(rightStr)));
            tree.add(new Node(operation, tree.get(tree.size()-2), tree.get(tree.size()-1)));
        }
    }

    public boolean isNotOperator(char c){
        // you may be yelling at me here for forgetting log or sin, etc.
        // I did consider these at first, but they will all be gone by the time that this function is used
        // if you end up using this function at a different point in your own code you may want to adjust it
        for (char operator : operators) {
            if (c == operator) {
                return false;
            }
        }
        return true;
    }

    public ComplexNumber evaluateNode(ComplexNumber myX, Node head) {
        if (head.isNumber()) { // node type = 0
            return head.data; // simple base case saying that given a number return that number
        }
        if (head.isX()) { // node type == 1
            return head.data.mul(myX); // head data might include some value because 2x can be stored as (2,0) with "isX" flagged as true
        }
        // trig its sub categories are fast, so I will do those first
        if (head.nodeType() == 7) {
            // I split it up like this to save time (for the computer (not very important))
            // trig
            if (head.sub.size() > 1) {
                System.out.println("Error: Wrong number of inputs found in evaluate node for trig");
                return new ComplexNumber(0, 0);
            }
            switch (head.operator) {
                case "sin" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).sin(myX); // this same method is used for each of the single input functions
                }
                case "cos" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).cos(myX);
                }
                case "tan" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).tan(myX);
                }
                case "sec" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).sec(myX);
                }
                case "csc" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).csc(myX);
                }
                case "cot" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).cot(myX);
                }
            }

        }
        if (head.nodeType() == 8) {
            // inverse trig
            if (head.sub.size() > 1) {
                System.out.println("Error: Wrong number of inputs found in evaluate node for inverse trig");
                return new ComplexNumber(0, 0);
            }
            switch (head.operator) {
                case "asin" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).asin(myX); // this same method is used for each of the single input functions
                }
                case "acos" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).acos(myX);
                }
                case "atan" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).atan(myX);
                }
                case "asec" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).asec(myX);
                }
                case "acsc" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).acsc(myX);
                }
                case "acot" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).acot(myX);
                }
            }
        }
        if (head.nodeType() == 9) {
            // hyperbolic trig
            if (head.sub.size() > 1){
                System.out.println("Error: Wrong number of inputs found in evaluate node for hyperbolic trig");
                return new ComplexNumber(0,0);
            }
            switch (head.operator) {
                case "sinh" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).sinh(myX); // this same method is used for each of the single input functions
                }
                case "cosh" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).cosh(myX);
                }
                case "tanh" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).tanh(myX);
                }
                case "sech" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).sech(myX);
                }
                case "csch" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).csch(myX);
                }
                case "coth" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).coth(myX);
                }
            }
        }
        if (head.nodeType() == 10) {
            // inverse hyperbolic trig
            if (head.sub.size() > 1){
                System.out.println("Error: Wrong number of inputs found in evaluate node for inverse hyperbolic trig");
                return new ComplexNumber(0,0);
            }
            switch (head.operator) {
                case "asinh" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).asinh(myX); // this same method is used for each of the single input functions
                }
                case "acosh" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).acosh(myX);
                }
                case "atanh" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).atanh(myX);
                }
                case "asech" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).asech(myX);
                }
                case "acsch" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).acsch(myX);
                }
                case "acoth" -> {
                    return this.evaluateNode(myX, head.sub.get(0)).acoth(myX);
                }
            }
        }
        // addition subtraction multiplication and division all require looping to combine all the sub-nodes
        if (head.nodeType() == 2){
            // addition
            ComplexNumber sum = new ComplexNumber(0,0);
            for (Node subNode : head.sub){
                sum = this.evaluateNode(myX, subNode).add(sum, myX);
            }
            return sum;
        }
        if (head.nodeType() == 3){
            // subtraction
            ComplexNumber sum = new ComplexNumber(0,0);
            int counter = 0;
            for (Node subNode : head.sub){
                if (counter == 0) {
                    sum = this.evaluateNode(myX, subNode).add(sum, myX);
                } else {
                    sum = this.evaluateNode(myX, subNode).sub(sum, myX);
                }
                counter++;
            }
            return sum;
        }
        if (head.nodeType() == 4){
            // multiplication
            ComplexNumber sum = new ComplexNumber(1,0);
            for (Node subNode : head.sub){
                sum = this.evaluateNode(myX, subNode).mul(sum, myX);
            }
            return sum;
        }
        if (head.nodeType() == 5){
            // subtraction
            ComplexNumber sum = new ComplexNumber(1,0);
            int counter = 0;
            for (Node subNode : head.sub){
                if (counter == 0) {
                    sum = this.evaluateNode(myX, subNode).mul(sum, myX);
                } else {
                    sum = this.evaluateNode(myX, subNode).div(sum, myX);
                }
                counter++;
            }
            return sum;
        }
        // exponentiation and logarithms
        if (head.nodeType() == 6){
            if (head.sub.size() != 2) {
                System.out.println("Error: Wrong number of inputs found in evaluate node for exponentiation");
                return new ComplexNumber(0, 0);
            }

            return this.evaluateNode(myX, head.sub.get(0)).pow(this.evaluateNode(myX, head.sub.get(1)), myX);
        }
        if (head.nodeType() == 12){
            if (head.sub.size() != 2) {
                System.out.println("Error: Wrong number of inputs found in evaluate node for logarithms");
                return new ComplexNumber(0, 0);
            }

            return this.evaluateNode(myX, head.sub.get(0)).log(this.evaluateNode(myX, head.sub.get(1)), myX);
        }
        // factorial
        if (head.nodeType() == 11){
            if (head.sub.size() != 1) {
                System.out.println("Error: Wrong number of inputs found in evaluate node for the gamma function");
                return new ComplexNumber(0, 0);
            }
            return evaluateNode(myX, head.sub.get(0)).gam(myX);
        }

        // something went wrong
        System.out.println("Error: Evaluate node was unable to find operator");
        return null;

    }

    public ComplexNumber riemannSumOfDefiniteIntegral(double min, double max, double stepSize){
        // pretty sure this is a left riemann sum but did not check recently (check for yourself)
        // this will be a function to return the definite integral of a given function over some size
        ComplexNumber total = new ComplexNumber(0,0);
        // step through the entire thing
        for (double i = min; i < max; i += stepSize){
            total = total.add(this.evaluateNode(new ComplexNumber(i,0), tree.get(length)), new ComplexNumber(i,0));
        }
        total = total.mul(new ComplexNumber(stepSize,0),new ComplexNumber());

        return total;
    }

    public Node createDerivativeNode(Node thisNode){

        if (thisNode.nodeType() < 2 && thisNode.nodeType() > -1){
            if (thisNode.isNumber()){
                return new Node(0,0);
            }
            else if (thisNode.isX()){
                return new Node(1,0);
            }
            else {
                System.out.println("Error: Derivative found an impossible node");
                return new Node(0,0); // how did we get here?
            }
        }
        if (thisNode.sub == null){ // in case of errors
            System.out.println("Error: Nodes require either data or operator");
            return new Node(0,0);
        }
        switch (thisNode.operator) {
            case "+" -> {
                Node returnNode = new Node("+");

                for (Node subNode : thisNode.sub){
                    returnNode.sub.add(createDerivativeNode(subNode));
                }

                return returnNode;
            } // sum rule
            case "-" -> {
                Node returnNode = new Node("-");

                for (Node subNode : thisNode.sub){
                    returnNode.sub.add(createDerivativeNode(subNode));
                }

                return returnNode;            } // inverse sum rule
            case "*" -> {
                // product rule is complicated ish, so it may seem very difficult for an arbitrary number of nodes
                // this is not true, there is a simple formula that you can use to solve it:
                // d/dx (abcdef) =
                // a'bcdef + ab'cdef + abc'def + abcd'ef + abcde'f + abcdef'
                Node returnNode = new Node("+");
                Node product = new Node("*");
                for (int i = 0; i < thisNode.sub.size(); i++){ // loop through each of the nodes because each sub node gets to be the derivative once
                    product.sub.clear();
                    int counter = 0;
                    for (Node subNode : thisNode.sub){ // nested loop to add all the nodes into the specific sub node
                        if (counter == i){
                            product.sub.add(createDerivativeNode(subNode)); // only one node gets to have its derivative included in the product
                        } else {
                            product.sub.add(subNode); // the rest of the nodes are just multiplied in
                        }
                    }
                    returnNode.sub.add(product);
                }
                return returnNode;
            } // product rule
            case "/" -> {
                // the generalized quotient rule is too complicated to code here and I have a better solution:
                // run clean node, it will remove all the division in the node
                System.out.println("Error: code does not accept division in derivative functions please use .clean() to remove it from the node");
                return new Node(0,0);
            } // quotient rule
            case "^" -> {
                // u^v * (v' * ln(u) + \frac{v*u'}{u})
                Node firstPart =  new Node("^", thisNode.sub.get(0), thisNode.sub.get(1));
                Node secondPart = new Node("*", createDerivativeNode(thisNode.sub.get(1)), new Node("log", new Node(new ComplexNumber(Math.E, 0)), thisNode.sub.get(0)));
                Node thirdPart =  new Node("/", new Node("*", thisNode.sub.get(1), createDerivativeNode(thisNode.sub.get(0))), thisNode.sub.get(0));
                Node fourthPart = new Node("+", secondPart, thirdPart);
                return            new Node("*", firstPart, fourthPart);
            } // generalized power rule
            case "ln" -> {
                // NOTE: ln should never be used in the code other than the derivative log function
                return new Node("/", createDerivativeNode(thisNode.sub.get(0)), thisNode.sub.get(0)); // so elegant
            }
            case "log" -> {
                // this one is really annoying
                // \frac{d/dx(ln(g))ln(f)-d/dx(ln(f))ln(g)}{(ln(f))^2}
                Node firstPart   = createDerivativeNode(new Node("ln", thisNode.sub.get(1), null));
                Node secondPart  = new Node("log", new Node(new ComplexNumber(Math.E, 0)), thisNode.sub.get(0));
                Node thirdPart   = createDerivativeNode(new Node("ln", thisNode.sub.get(0)));
                Node fourthPart  = new Node("log", new Node("", new ComplexNumber(Math.E, 0)), thisNode.sub.get(1));
                Node fifthPart   = new Node("^", new Node("log", new Node(new ComplexNumber(Math.E, 0)), thisNode.sub.get(0)), new Node(new ComplexNumber(2, 0)));
                // the first five parts are from the equation, next four are from combining those parts into the final equation
                Node sixthPart   = new Node("*", firstPart, secondPart);
                Node seventhPart = new Node("*", thirdPart, fourthPart);
                Node eighthPart  = new Node("-", sixthPart, seventhPart);
                return             new Node("/", eighthPart, fifthPart);
            } // generalized log rule
            case "sin" -> {
                // cos
                return new Node ("*", new Node("cos", thisNode.sub.get(0), thisNode.sub.get(1)), createDerivativeNode(thisNode.sub.get(0)));
            }
            case "cos" -> {
                // -sin
                return new Node ("*", new Node("*", new Node("sin", thisNode.sub.get(0), thisNode.sub.get(1)), new Node(new ComplexNumber(-1,0))), createDerivativeNode(thisNode.sub.get(0)));
            }
            case "tan" -> {
                // sec^2
                Node baseNode = new Node("sec", thisNode.sub.get(0), thisNode.sub.get(1));
                Node twoNode =  new Node(new ComplexNumber(2,0));
                return          new Node ("*", new Node("^", baseNode, twoNode), createDerivativeNode(thisNode.sub.get(0)));
            }
            case "cot" -> {
                // -csc^2
                Node baseNode = new Node("csc", thisNode.sub.get(0), thisNode.sub.get(1));
                Node twoNode =  new Node(new ComplexNumber(2,0));
                Node mOneNode = new Node(new ComplexNumber(-1,0));
                return          new Node("*", new Node("*", mOneNode, new Node("^", baseNode, twoNode)), createDerivativeNode(thisNode.sub.get(0)));

            }
            case "sec" -> {
                // sec*tan
                Node secNode = new Node("sec", thisNode.sub.get(0), thisNode.sub.get(1));
                Node tanNode = new Node("tan", thisNode.sub.get(0), thisNode.sub.get(1));
                return         new Node("*", new Node("*", secNode, tanNode), createDerivativeNode(thisNode.sub.get(0)));

            }
            case "csc" -> {
                // -csc*cot
                Node secNode  = new Node("csc", thisNode.sub.get(0), thisNode.sub.get(1));
                Node tanNode  = new Node("cot", thisNode.sub.get(0), thisNode.sub.get(1));
                Node stNode   = new Node("*",   secNode, tanNode);
                Node mOneNode = new Node(new ComplexNumber(-1,0));
                return          new Node("*", new Node("*", stNode, mOneNode), createDerivativeNode(thisNode.sub.get(0)));
            }
            case "sinh" -> {
                return new Node ("*", new Node("cosh", thisNode.sub.get(0), thisNode.sub.get(1)), createDerivativeNode(thisNode.sub.get(0)));
            }
            case "cosh" -> {
                return new Node ("*", new Node("sinh", thisNode.sub.get(0), thisNode.sub.get(1)), createDerivativeNode(thisNode.sub.get(0)));
            }
            case "tanh" -> {
                Node baseNode = new Node("sech", thisNode.sub.get(0), thisNode.sub.get(1));
                Node twoNode = new Node(new ComplexNumber(2,0));
                return new Node ("*", new Node("^", baseNode, twoNode), createDerivativeNode(thisNode.sub.get(0)));
            }
            case "coth" -> {
                Node baseNode = new Node("csch", thisNode.sub.get(0), thisNode.sub.get(1));
                Node twoNode =  new Node(new ComplexNumber(2,0));
                Node mOneNode = new Node(new ComplexNumber(-1,0));
                return          new Node("*", new Node("*", mOneNode, new Node("^", baseNode, twoNode)), createDerivativeNode(thisNode.sub.get(0)));
            }
            case "sech" -> {
                Node secNode  = new Node("sech", thisNode.sub.get(0), thisNode.sub.get(1));
                Node tanNode  = new Node("tanh", thisNode.sub.get(0), thisNode.sub.get(1));
                Node stNode   = new Node("*",     secNode, tanNode);
                Node mOneNode = new Node(new ComplexNumber(-1,0));
                return          new Node("*", new Node("*", stNode, mOneNode), createDerivativeNode(thisNode.sub.get(0)));
            }
            case "csch" -> {
                Node secNode  = new Node("csch", thisNode.sub.get(0), thisNode.sub.get(1));
                Node tanNode  = new Node("coth", thisNode.sub.get(0), thisNode.sub.get(1));
                Node stNode   = new Node("*", secNode, tanNode);
                Node mOneNode = new Node(new ComplexNumber(-1,0));
                return          new Node("*", new Node("*", stNode, mOneNode), createDerivativeNode(thisNode.sub.get(0)));
            }
            case "asin" -> {
                // \frac{1}{(1-x^2)^{1/2)}
                // numbers
                Node firstNode =  new Node(new ComplexNumber(1,0));
                Node secondNode = new Node(new ComplexNumber(2,0));
                Node thirdNode =  new Node(new ComplexNumber(-1,0));
                Node node31 =     new Node(new ComplexNumber(0.5,0));
                // calculations
                Node fourthNode = new Node("^",thisNode.sub.get(0), secondNode); // x^2
                Node fifthNode =  new Node("*", fourthNode, thirdNode);    // (..) * -1
                Node node51 =     new Node("+", firstNode, fifthNode);     // (..) + 1
                Node node52 =     new Node("^", node51, node31);           // (..) sqrt(..)
                Node sixthNode =  new Node("/", firstNode, node52);        // 1 / (..)
                return            new Node("*", sixthNode, createDerivativeNode(thisNode.sub.get(0))); // implement chain rule
            }
            case "acos" -> {
                // -\frac{1}{(1-x^2)^{1/2)}
                // values
                Node firstNode =   new Node(new ComplexNumber(1,0));
                Node secondNode =  new Node(new ComplexNumber(2,0));
                Node thirdNode =   new Node(new ComplexNumber(-1,0));
                Node node31 =      new Node(new ComplexNumber(0.5,0));
                // calculations
                Node fourthNode =  new Node("^", thisNode.sub.get(0), secondNode); // x^2
                Node fifthNode =   new Node("*", fourthNode, thirdNode);     // (..) * -1
                Node node51 =      new Node("+", firstNode, fifthNode);      // (..) + 1
                Node node52 =      new Node("^", node51, node31);            // sqrt(..)
                Node sixthNode =   new Node("/", firstNode, node52);         //  1 / (..)
                Node seventhNode = new Node("*", thirdNode, sixthNode);      // -1 * (..)
                return             new Node("*", seventhNode, createDerivativeNode(thisNode.sub.get(0))); // add in chain
            }
            case "atan" -> {
                // \frac{1}{(1+x^2)}
                // numbers
                Node firstNode =  new Node(new ComplexNumber(1,0));
                Node secondNode = new Node(new ComplexNumber(2,0));
                // calculations
                Node thirdNode =  new Node("^", thisNode.sub.get(0), secondNode); // x^2
                Node fourthNode = new Node("+", firstNode, thirdNode);      // (..) + 1
                Node fifthNode =  new Node("/", firstNode, fourthNode);     // 1 / (..)
                return            new Node("*", fifthNode, createDerivativeNode(thisNode.sub.get(0))); // and in chain
            }
            case "acot" -> {
                // \frac{1}{(1+x^2)}
                // numbers
                Node firstNode =   new Node(new ComplexNumber(1,0));
                Node secondNode =  new Node(new ComplexNumber(2,0));
                Node sixthNode =   new Node(new ComplexNumber(-1,0));
                // calculations
                Node thirdNode =   new Node("^", thisNode.sub.get(0), secondNode); // x^2
                Node fourthNode =  new Node("+", firstNode, thirdNode);      // (..) + 1
                Node fifthNode =   new Node("/", firstNode, fourthNode);     //  1 / (..)
                Node seventhNode = new Node("*", sixthNode, fifthNode);      // -1 * (..)
                return             new Node("*", seventhNode, createDerivativeNode(thisNode.sub.get(0))); // add in chain
            }
            case "acsc" -> {
                // -\frac{1}{|x|(1-x^2)^{1/2)}
                // numbers
                Node firstNode =   new Node(new ComplexNumber(1,0));
                Node secondNode =  new Node(new ComplexNumber(2,0));
                Node thirdNode =   new Node(new ComplexNumber(-1,0));
                Node node31 =      new Node(new ComplexNumber(0.5,0 ));
                // calculations
                Node fourthNode =  new Node("^",   thisNode.sub.get(0), secondNode); // x^2
                Node fifthNode =   new Node("*",   fourthNode, thirdNode);     // (..) * -1
                Node node51 =      new Node("+",   firstNode, fifthNode);      // (..) + 1
                Node node52 =      new Node("^",   node51, node31);            // sqrt(..)
                Node node53 =      new Node("abs", thisNode.sub.get(0), null);   // |x|
                Node node54 =      new Node("*",   node53, node52);            // (..) * (...)
                Node sixthNode =   new Node("/",   firstNode, node54);         // 1 / (..)
                Node seventhNode = new Node("*",   thirdNode, sixthNode);      // (..) * -1
                return             new Node("*",   seventhNode, createDerivativeNode(thisNode.sub.get(0)));
            }
            case "asinh" -> {
                // \frac{1}{(1+x^2)^{1/2)}
                // numbers
                Node firstNode =  new Node(new ComplexNumber(1,0));
                Node secondNode = new Node(new ComplexNumber(2,0));
                Node node31 =     new Node(new ComplexNumber(0.5,0));
                // calculations
                Node fourthNode = new Node("^",thisNode.sub.get(0), secondNode); // x^2
                Node node51 =     new Node("+", firstNode, fourthNode);    // (..) + 1
                Node node52 =     new Node("^", node51, node31);           // sqrt(..)
                Node sixthNode =  new Node("/", firstNode, node52);        // 1 / (..)
                return            new Node("*", sixthNode, createDerivativeNode(thisNode.sub.get(0))); // implement chain rule
            }
            case "acosh" -> {
                // \frac{1}{(x^2 - 1)^{1/2}}
                // numbers
                Node firstNode =  new Node(new ComplexNumber(1,0));
                Node secondNode = new Node(new ComplexNumber(2,0));
                Node node31 =     new Node(new ComplexNumber(0.5,0));
                // calculations
                Node fourthNode = new Node("^",thisNode.sub.get(0), secondNode); // x^2
                Node node51 =     new Node("-", fourthNode, firstNode);    // (..) - 1
                Node node52 =     new Node("^", node51, node31);           // sqrt(..)
                Node sixthNode =  new Node("/", firstNode, node52);        // 1 / (..)
                return            new Node("*", sixthNode, createDerivativeNode(thisNode.sub.get(0))); // implement chain rule
            }
            case "atanh", "acoth" -> {
                // \frac{1}{(1-x^2)}
                // numbers
                Node firstNode =  new Node(new ComplexNumber(1,0));
                Node secondNode = new Node(new ComplexNumber(2,0));
                // calculations
                Node thirdNode =  new Node("^", thisNode.sub.get(0), secondNode); // x^2
                Node fourthNode = new Node("-", firstNode, thirdNode);      // 1 - (..)
                Node fifthNode =  new Node("/", firstNode, fourthNode);     // 1 / (..)
                return            new Node("*", fifthNode, createDerivativeNode(thisNode.sub.get(0))); // and in chain
            }
            case "acsch" -> {
                // \frac{1}{|x|(1+x^2)^{1/2}}
                // numbers
                Node firstNode =  new Node(new ComplexNumber(1,0));
                Node secondNode = new Node(new ComplexNumber(2,0));
                Node node31 =     new Node(new ComplexNumber(0.5,0));
                // calculations
                Node fourthNode = new Node("^",   thisNode.sub.get(0), secondNode); // x^2
                Node node51 =     new Node("+",   firstNode, fourthNode);    // (..) + 1
                Node node52 =     new Node("^",   node51, node31);           // sqrt(..)
                Node node53 =     new Node("abs", thisNode.sub.get(0), null);  // abs(..)
                Node node54 =     new Node("*",   node53, node52);           // (..) * (...)
                Node sixthNode =  new Node("/",   firstNode, node54);        // 1 / (..)
                return            new Node("*",   sixthNode, createDerivativeNode(thisNode.sub.get(0))); // implement chain rule
            }
            case "asech" -> {
                // this is so much easier, but I forgot about it until now...
                // -\tanh(x)sech(x)
                Node node1 = new Node("tanh", thisNode.sub.get(0), null);
                Node node2 = new Node("sech", thisNode.sub.get(0), null);
                Node node3 = new Node(new ComplexNumber(-1,0));
                Node node4 = new Node("*", node1, node2);
                Node node5 = new Node("*", node3, node4);
                return       new Node("*", node5, createDerivativeNode(thisNode.sub.get(0))); // implement chain rule
            }
        }


//        ddxTree.add(createDerivativeTree(thisNode));
//        return createDerivativeTree(thisNode);
        System.out.println("Error: was not able to parse function to calculate derivative");
        return new Node(0,0); // something went wrong
    }

    public void printTree(){
        // little used function for debug
        for (Node n : tree){
            System.out.println("data:" + n.data + " operator:" + n.operator);
        }
    }

    @Override
    public String toString() {
        return "INPUT:\t" + eq + "\nOUTPUT:\t" + tree.get(length);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        if (!(obj instanceof Equation)) {
            return false;
        }
        return ((Equation) obj).tree.equals(this.tree) ;
    }
}
