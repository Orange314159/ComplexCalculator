import java.util.ArrayList;
public class Equation {

    public String eq;
    public int length = -1;
    char[] operators = {'+', '-', '*', '/', '^'};
    ArrayList<Node> tree = new ArrayList<>();

    public Equation (String in){
        eq = in;
        removeSpaces();
        includeOmittedMultiplication();
//        System.out.println(eq + "CLEAN");
        length = createTreeParenthesis(eq);
    }

    public int findCloseParenthesis(int start){
        int counter = 1; // this signifies that there is one open parenthesis (the one that we start with)
        // once we find a close parenthesis we will decrement this, once we find an open parenthesis we increment
        for (int i = start+1; i < eq.length(); i++){
            if(eq.charAt(i) == '('){
                counter++;
            }
            else if(eq.charAt(i) == ')'){
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

            }
            // Debug
//            System.out.println("i:" + i + " - " + eq.charAt(i) + " eq:" + eq + " j:"+j + " - " + eq.charAt(j));
        }
    }

    public int createTreeParenthesis(String input){
        StringBuilder partialEqP = new StringBuilder(); // idk what to call this
        // start with parenthesis
        int skip = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '('){
                partialEqP.append("{");
//                System.out.println(input.substring(i+1,findCloseParenthesis(i)));
                partialEqP.append(createTreeParenthesis(input.substring(i+1,findCloseParenthesis(i))));
                skip = findCloseParenthesis(i)-i;
                partialEqP.append("}");
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
        StringBuilder partialEqE = new StringBuilder();
        int skip = 0;
        boolean foundOne = false;
        for (int i = 0; i < input.length(); i++) {
            if(input.charAt(i) == '^' && !foundOne){
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
                // I turn these two "raw" values to new nodes or recognize that they are already nodes

                addNodes(leftRaw.toString(), rightRaw.toString(), "^");

                partialEqE = new StringBuilder(partialEqE.substring(0, i - leftRaw.length()));
                skip = rightRaw.length();
                foundOne = true;
                partialEqE.append("{").append(tree.size() - 1).append("}"); // the last node is the one that we just made, so we can assign the node at this location to be that head node
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
            return createTreeExponents(partialEqE.toString());
        } else {
            return createTreeMultDiv(partialEqE.toString());
        }
    }

    public int createTreeMultDiv(String input){
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

//                System.out.println(leftRaw + " <-> " + rightRaw);
                addNodes(leftRaw.toString(), rightRaw.toString(), "*");

                partialEqMD = new StringBuilder(partialEqMD.substring(0, i - leftRaw.length()));
                skip = rightRaw.length();
                found = true;
                partialEqMD.append("{").append(tree.size() - 1).append("}"); // the last node is the one that we just made, so we can assign the node at this location to be that head node
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
                partialEqMD.append("{").append(tree.size() - 1).append("}"); // the last node is the one that we just made, so we can assign the node at this location to be that head node
            } else{
                if (skip < 1){
                    partialEqMD.append(input.charAt(i));
                }else {
                    skip--;
                }
            }
        }

        if(found){

            return createTreeMultDiv(partialEqMD.toString());
        } else {
//            System.out.println(input + "_MD_" +partialEqMD);
            return createTreeAddSub(partialEqMD.toString());
        }

    }

    public int createTreeAddSub(String input){
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
                partialEqSA.append("{").append(tree.size() - 1).append("}"); // the last node is the one that we just made, so we can assign the node at this location to be that head node
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
                partialEqSA.append("{").append(tree.size() - 1).append("}"); // the last node is the one that we just made, so we can assign the node at this location to be that head node
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
            return tree.size()-1;
        }
    }

    public void addNodes(String leftStr, String rightStr, String operation){

        if (leftStr.contains("{") && rightStr.contains("{")){ // both nodes have already been created
            tree.add(new Node(operation, new ComplexNumber(), tree.get(Integer.parseInt(leftStr.substring(1,leftStr.length()-1))), tree.get(Integer.parseInt(rightStr.substring(1,rightStr.length()-1))) ));

        } else if (leftStr.contains("{")){ // left node exists

            tree.add(new Node("", new ComplexNumber(rightStr)));
            tree.add(new Node(operation, new ComplexNumber(), tree.get(Integer.parseInt(leftStr.substring(1,leftStr.length()-1))), tree.get(tree.size()-1)));
        } else if (rightStr.contains("{")){ // right node exists
            tree.add(new Node("", new ComplexNumber(leftStr)));
            tree.add(new Node(operation, new ComplexNumber(), tree.get(tree.size()-1), tree.get(Integer.parseInt(rightStr.substring(1,rightStr.length()-1)))));
        } else { // neither node exists
            tree.add(new Node("", new ComplexNumber(leftStr)));
            tree.add(new Node("", new ComplexNumber(rightStr)));
            tree.add(new Node(operation, new ComplexNumber(), tree.get(tree.size()-2), tree.get(tree.size()-1)));
        }
//        System.out.println(tree.get(tree.size()-1));
    }

    public boolean isNotOperator(char c){
        for (char operator : this.operators) {
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
        String nodeOperator = tree.get(startNode).operator;
        ComplexNumber nodeData = tree.get(startNode).data;
        if (tree.get(startNode).left == null){ // base case
            // there are no child nodes
//            System.out.println(nodeData + "NODE " + startNode +"START" );
            return nodeData;

        }
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
            default -> new ComplexNumber();
        };
    }

    @Override
    public String toString() {
        return eq + "\n" + tree.get(length);
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
