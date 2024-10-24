import java.util.ArrayList;
public class Equation {
    // important notes:
    // 1. When creating the binary tree I use "~" as a delimiter for pointers DO NOT USE "~" IN YOUR EQUATIONS
    // 2. Spaces do not matter, I will remove all the spaces at the beginning so don't worry about that

    public String eq;
    public int length = -1;
    private static final char[] operators = {'+', '-', '*', '/', '^'};
    ArrayList<Node> tree = new ArrayList<>();

    public Equation (String in){
        eq = in;
        removeSpaces();
        includeOmittedMultiplication();
//        System.out.println(eq + "CLEAN");
        length = createTreeSpecialFunctions(eq);
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
        // this is similar to that of createTreeParenthesis but is about \frac or \log
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
                        tree.add(new Node(functionName, new ComplexNumber(), tree.get(subTree), new Node()));
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
                        tree.add(new Node(functionName, new ComplexNumber(), tree.get(subTree), new Node()));
                        partialEqSP.append("~").append(tree.size() - 1).append("~");
                    } else {
                        System.out.println("Error");
                    }
                    skip = findCloseBrace(i+5, input) - i;
                } else if ((input.startsWith("sin", i+1) || input.startsWith("cos", i+1) || input.startsWith("tan", i+1)|| input.startsWith("cot", i+1)|| input.startsWith("sec", i+1)|| input.startsWith("csc", i+1) || input.startsWith("gam", i+1))&& skip <1) {
                    // any three letter function (I attempt to make all of my functions like this)
                    String functionName = input.substring(i+1,i+4);
//                    System.out.println(input + "  " + input.substring(i+6,findCloseBrace(i+5, input)));
                    if (input.charAt(i+4) == '{'){
                        int subTree = createTreeSpecialFunctions(input.substring(i+5,findCloseBrace(i+4, input)));
                        tree.add(new Node(functionName, new ComplexNumber(), tree.get(subTree), new Node()));
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
            tree.add(new Node(operation, new ComplexNumber(), tree.get(Integer.parseInt(leftStr.substring(1,leftStr.length()-1))), tree.get(Integer.parseInt(rightStr.substring(1,rightStr.length()-1))) ));
        } else if (leftStr.contains("~")){ // left node exists
            tree.add(new Node("", new ComplexNumber(rightStr)));
            tree.add(new Node(operation, new ComplexNumber(), tree.get(Integer.parseInt(leftStr.substring(1,leftStr.length()-1))), tree.get(tree.size()-1)));
        } else if (rightStr.contains("~")){ // right node exists
            tree.add(new Node("", new ComplexNumber(leftStr)));
            tree.add(new Node(operation, new ComplexNumber(), tree.get(tree.size()-1), tree.get(Integer.parseInt(rightStr.substring(1,rightStr.length()-1)))));
        } else { // neither node exists
            tree.add(new Node("", new ComplexNumber(leftStr)));
            tree.add(new Node("", new ComplexNumber(rightStr)));
            tree.add(new Node(operation, new ComplexNumber(), tree.get(tree.size()-2), tree.get(tree.size()-1)));
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

    public ComplexNumber evaluateEquation(ComplexNumber myX, int startNode){
        if (startNode == -1){
            System.out.println("ERROR: Tree Has Not Been Created Properly Yet or There is a Problem With The Start Node");
            return new ComplexNumber();
        }
        if(startNode == 0){ // there is only one node (this would look like 2.4 or x or 10
            if(tree.get(0).data.isX){
                return myX;
            }
            return tree.get(0).data;
        }
        String nodeOperator = tree.get(startNode).operator;
        ComplexNumber nodeData = tree.get(startNode).data;
        if (tree.get(startNode).left == null){ // base case
            // there are no child nodes
//            System.out.println(nodeData + "NODE " + startNode +"START" );
            return nodeData;

        }
        // big switch statement to check for every possible operator (that I include in my code)
        return switch (nodeOperator) {
            case "*" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).mul(this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).right)), myX);
            case "+" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).add(this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).right)), myX);
            case "/" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).div(this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).right)), myX);
            case "-" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).sub(this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).right)), myX);
            case "^" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).pow(this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).right)), myX);
            case "log" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).log(this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).right)), myX);
            case "sinh" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).sinh(myX);
            case "cosh" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).cosh(myX);
            case "tanh" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).tanh(myX);
            case "coth" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).coth(myX);
            case "sech" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).sech(myX);
            case "csch" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).csch(myX);
            case "sin" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).sin(myX);
            case "cos" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).cos(myX);
            case "tan" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).tan(myX);
            case "sec" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).sec(myX);
            case "csc" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).csc(myX);
            case "cot" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).cot(myX);
            case "asinh" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).asinh(myX);
            case "acosh" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).acosh(myX);
            case "atanh" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).atanh(myX);
            case "acoth" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).acoth(myX);
            case "asech" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).asech(myX);
            case "acsch" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).acsch(myX);
            case "asin" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).asin(myX);
            case "acos" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).acos(myX);
            case "atan" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).atan(myX);
            case "asec" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).asec(myX);
            case "acsc" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).acsc(myX);
            case "acot" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).acot(myX);
            case "gam" ->
                    this.evaluateEquation(myX, tree.indexOf(tree.get(startNode).left)).gam(myX); // this might be slow because it has to solve a Riemann sum (especially for values of x that follow 0 + bi)
            default -> new ComplexNumber();
        };
    }

    public ComplexNumber riemannSumOfDefiniteIntegral(double min, double max, double stepSize){
        // this will be a function to return the definite integral of a given function over some size
        ComplexNumber total = new ComplexNumber(0,0);
        // step through the entire thing
        for (double i = min; i < max; i += stepSize){
            total = total.add(this.evaluateEquation(new ComplexNumber(i,0), this.length), new ComplexNumber(i,0));
        }
        total = total.mul(new ComplexNumber(stepSize,0),new ComplexNumber());

        return total;
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
