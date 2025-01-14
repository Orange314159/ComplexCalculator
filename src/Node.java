import java.util.ArrayList;

public class Node {
    // each node includes EITHER an operator or data, NOT both
    public String operator;
    public ComplexNumber data;
    public ArrayList<Node> sub;

    public Node(){
        data = new ComplexNumber();
        operator = "";
    }
    public Node(String o, ComplexNumber complexNumber) {
        data     = complexNumber;
        operator = o;
        sub = null;
    }
    public Node(String o, Node n1, Node n2){
        data     = new ComplexNumber();
        operator = o;
        sub = new ArrayList<Node>();
        sub.add(n1);
        sub.add(n2);
    }
    public Node(String o, Node n1){
        data     = new ComplexNumber();
        operator = o;
        sub = new ArrayList<Node>();
        sub.add(n1);
    }
    public Node(String o){
        data     = new ComplexNumber();
        operator = o;
        sub = new ArrayList<Node>();
    }
    public Node(ComplexNumber complexNumber){
        data     = complexNumber;
        operator = "";
        sub = null;
    }
    public Node(double a, double b){
        data     = new ComplexNumber(a,b);
        operator = "";
        sub = null;
    }
    public boolean isNumber(){
        if (this.sub != null){
            return false;
        }
        if (this.data == null || this.data.isX)
            return false;

        return this.operator.isEmpty();
    }
    public boolean isX(){
        if (this.sub != null){
            return false;
        }
        if (this.data == null)
            return false;

        return this.data.isX;
    }
    public int nodeType(){
        // Here is a list for the types of nodes
        // -1   unknown
        // 0    regular number
        // 1    x
        // 2    addition
        // 3    subtraction
        // 4    multiplication
        // 5    division
        // 6    exponent
        // 7    trig
        // 8    inverse trig
        // 9    hyperbolic trig
        // 10   hyperbolic inverse trig
        // 11   factorial (gamma function)
        // 12   log
        if (this.isNumber()){
            return 0;
        }
        if (this.isX()){
            return 1;
        }
        if (this.sub == null){
            return -1; // there should be no functions that have no left input
        }
        if (this.operator.isEmpty()){
            return -1; // all functions should have an operator
        }
        return switch (this.operator) {
            case "+" -> 2;
            case "-" -> 3;
            case "*" -> 4;
            case "/" -> 5;
            case "^" -> 6;
            case "sin", "cos", "tan", "sec", "csc", "cot" -> 7;
            case "asin", "acos", "atan", "asec", "acsc", "acot" -> 8;
            case "sinh", "cosh", "tanh", "sech", "csch", "coth" -> 9;
            case "asinh", "acosh", "atanh", "asech", "acsch", "acoth" -> 10;
            case "gam" -> 11;
            case "log" -> 12;
            default -> -1;
        };

    }

    public Node clean(){
        // the function of this method is to take a node that may include easily simplified values and then simplify them
        // for example if the node includes 0 * x + 4 it would simplify it to 4
        // of something like 2 * 90 + 14 - x would become 166 - x

        if (this.sub == null){
            return this; // base case
        }

        if (this.operator.equals("+")){
            // we first want to clean all the smaller parts before combining
            ArrayList<Node> newSubNodes = new ArrayList<>();

            for (Node subNode: sub){
                newSubNodes.add(subNode.clean()); // this should leave no subtraction left, so our next step does not have to worry about that
            }
            this.sub = newSubNodes;

            // collapse all addition into one if possible
            int counter = 0;
            for (Node subNode : sub){ // loop through all the sub nodes
                if (subNode.nodeType() == 2){ // if the node is addition
                    this.sub.addAll(subNode.sub); // add all the nodes into our current "main" node
                    this.sub.remove(counter); // remove the sub node
                    counter--; // this exists to fix the weird counting problem of removing a part of a list that you are still editing
                }
                counter++; // iterate through all nodes and keep track of their number
            }

            // remove all nodes that are zeros

            Node zero = new Node(0,0); // instantiate a zero node at the start that will be reused
            newSubNodes = new ArrayList<>();
            for (Node subNode : sub){ // loop through all the sub nodes
                if (!subNode.equals(zero)){ // check equality with zero
                    newSubNodes.add(subNode);
                }
            }
            this.sub = newSubNodes;

            // combine like number and x terms
            Node numberSum = new Node(0,0); // this will be the sum of all the numbers found in the sub node list
            Node xSum      = new Node(0,0); // like above this represents the number of "x"s found in the list
            newSubNodes = new ArrayList<>();
            for (Node subNode : sub){ // loop through all the sub nodes
                if (subNode.nodeType() == 0){ // check for the node being a number
                    numberSum.data = numberSum.data.add(subNode.data); // add the number to the sum
                } else if (subNode.nodeType() == 1){ // check for the node being x
                    xSum.data = xSum.data.add(subNode.data, subNode.data); // add it to the xSum
                } else {
                    newSubNodes.add(subNode);
                }
            }
            this.sub = newSubNodes;

            if(!numberSum.equals(zero)){
                this.sub.add(numberSum); // add in the number and x sum finally
            }
            if (!xSum.equals(zero)){
                xSum.data.isX = true;
                this.sub.add(xSum);
            }
            // Todo: combine other like terms
            //  ex: (2x+1) + (2x+1) + 2 + 5 + 8 + x = 2(2x+1) + 15 + x
            //  we should be able to combine the terms like (2x+1)
            if (this.sub.isEmpty()){
                this.data = zero.data;
                this.operator = "";
                this.sub = null;
            } else if ( this.sub.size() == 1){ // if there is only one node in the multiply then I just replace myself with that node
                this.data     = sub.get(0).data;
                this.operator = sub.get(0).operator;
                if (this.sub.get(0).sub == null){
                    this.sub = null;
                } else {
                    this.sub      = sub.get(0).sub;
                }

            }

            return this;
        }
        if (this.operator.equals("-")){
            // I hate subtraction
            // not really, subtraction is just adding a negative number, so I will simplify it as such

            // we treat subtraction as: a - b - c - d - e - f - g...
            //                          - (a,b,c,d,e,f,g)
            // simplified this becomes: a + -b + -c + -d + -e + -f + -g... (it doesn't look simplified, but it is)
            //                          + (a,-b,-c,-d,-e,-f,-g)

            // we first want to clean all the smaller parts before combining
            // we first want to clean all the smaller parts before combining
            ArrayList<Node> newSubNodes = new ArrayList<>();
            for (Node subNode: sub){
                newSubNodes.add(subNode.clean()); // this should leave no subtraction left, so our next step does not have to worry about that
            }
            this.sub = newSubNodes;

            ArrayList<Node> newSubList = new ArrayList<Node>(); // this will be our new list of sub nodes once we are finished
            int counter = 0;
            for (Node subNode : sub){
                if (counter == 0){ // skip first node
                    newSubList.add(subNode);
                } else if (subNode.nodeType() == 0 || subNode.nodeType() == 1){ // check if the node is a number or x
                    Node correctedNode = new Node(subNode.data.mul(new ComplexNumber(-1,0))); // multiply the node by -1
                    newSubList.add(correctedNode); // add to the list
                } else {
                    Node correctedNode = new Node("*", new Node(-1,0), subNode); // multiply the node by -1
                    newSubList.add(correctedNode); // add to the list
                }
                counter++;
            }
            sub = newSubList; // replace old list with new list
            this.operator = "+"; // change the sign


            return this.clean();
        }
        if (this.operator.equals("*")){
            // we first want to clean all the smaller parts before combining
            // we first want to clean all the smaller parts before combining
            ArrayList<Node> newSubNodes = new ArrayList<>();
            for (Node subNode: sub){
                newSubNodes.add(subNode.clean()); // this should leave no subtraction left, so our next step does not have to worry about that
            }
            this.sub = newSubNodes;

            // clean up all numbers and stuff
            Node zero        = new Node(0,0); // some reused vars for the loop
            Node one         = new Node(1,0);
            Node productNode = new Node(1,0);
            Node xs          = new Node(0,0);
            ArrayList<Node> newNodes = new ArrayList<>();

            for (Node subNode : sub){ // loop through each node
                if (subNode.equals(zero)){ // check if the node is zero
                    return zero; // if the node is zero it does not matter what anything else is, so it returns zero
                } else if (subNode.nodeType() == 0){ // if the node is a number it multiplies it to the product
                    productNode.data = productNode.data.mul(subNode.data);
                } else if (subNode.nodeType() == 1){ // if the number is an x then it multiplies it and keeps track of the number of x's found
                    xs.data = xs.data.add(one.data);
                    productNode.data =productNode.data.mul(subNode.data, new ComplexNumber(1,0)); // include the 1,0 complex number because it would break otherwise
                } else {
                    newNodes.add(subNode);
                }
            }


            sub = newNodes;
            if (!productNode.equals(one)){
                this.sub.add(productNode);
            }


            if (!xs.equals(zero)){
                this.sub.add(new Node("^", new Node(new ComplexNumber("x")), xs));
            }

            // TODO: similar to addition, need to add in a better combination of like terms

            if (this.sub.isEmpty()){
                this.data = zero.data;
                this.operator = "";
                this.sub = null;
            } else if (this.sub.size() == 1){ // if there is only one node in the multiply then I just replace myself with that node
                this.data     = sub.get(0).data;
                this.operator = sub.get(0).operator;
                if (this.sub.get(0).sub == null){
                    this.sub = null;
                } else {
                    this.sub      = sub.get(0).sub;
                }
            }

            return this;
        }
        if (this.operator.equals("/")){
            // we first want to clean all the smaller parts before combining
            // we first want to clean all the smaller parts before combining
            ArrayList<Node> newSubNodes = new ArrayList<>();
            for (Node subNode: sub){
                newSubNodes.add(subNode.clean()); // this should leave no subtraction left, so our next step does not have to worry about that
            }
            this.sub = newSubNodes;

            int counter = 0;
            Node zero = new Node(0,0);
            Node one  = new Node(1,0);
            Node returnNode = new Node("*");
            for (Node subNode : sub){
                if (counter == 0){
                    if (subNode.equals(zero)){
                        return zero; // if the first node is zero that means the entire thing will evaluate to zero
                    } else {
                        returnNode.sub.add(subNode);
                    }
                } else {
                    if (subNode.equals(zero)){
                        System.out.println("Error: Found divide by zero while cleaning node");
                        return zero;
                    } else if (subNode.equals(one)){
                        // 1/1 = 1 so skip
                        continue;
                    } else {
                        returnNode.sub.add(subNode);
                    }
                }
                counter++;
            }

            boolean allNumbers = true;
            for (Node sn : sub){
                if(!sn.isNumber()){
                    allNumbers=false;
                    break;
                }
            }

            if (allNumbers){
                Node quotient = new Node(1,0);
                counter = 0;
                for (Node subNode : sub){
                    if (counter == 0){
                        quotient.data = quotient.data.mul(subNode.data);
                    } else {
                        quotient.data = quotient.data.div(subNode.data);
                    }
                    counter++;
                }
                this.sub = null;
                this.data = quotient.data;
                this.operator = "";
                return this;
            }

            this.operator = returnNode.operator;
            this.sub      = returnNode.sub;
            this.data     = returnNode.data;

            // Todo : there might be more exceptions to look at here (to speed it up)

            return this.clean();
        }
        if (this.operator.equals("^")){
            // as of (12/23/2024) I will only allow two node exponents, in the future I plan on allowing more, but not for now
            if (sub.size() != 2){
                System.out.println("Error: Found incorrect number of sub-nodes in an exponent while cleaning");
                return new Node(0,0);
            }

            if (this.sub.get(1).equals(new Node(0,0))){
                return new Node(1,0); // anything to the zero is 1
            }
            if (this.sub.get(1).equals(new Node(1,0))){
                return this.sub.get(0).clean(); // anything to the 1 is itself
            }
            if (this.sub.get(1).isNumber() && this.sub.get(0).isNumber()){
                return new Node(this.sub.get(0).data.pow(this.sub.get(1).data)); // do the exponent if they are both numbers
            }
        }
        if (this.operator.equals("log")){
            if (sub.size() != 2){
                System.out.println("Error: Found incorrect number of sub-nodes in log while cleaning");
                return new Node(0,0);
            }

            if (this.sub.get(0).sub.get(0) == null && this.sub.get(0).data != null && this.sub.get(1).data != null){
                return new Node(sub.get(0).data.log(sub.get(1).data, new ComplexNumber(0, 0)));
            }
        }
        if (this.operator.equals("sin")){
            if (sub.size() != 1){
                System.out.println("Error: Found incorrect number of sub-nodes in sin while cleaning");
                return new Node(0,0);
            }

            if (this.sub.get(0).isNumber()){
                return new Node(sub.get(0).data.sin());
            }
        }
        if (this.operator.equals("cos")){
            if (sub.size() != 1){
                System.out.println("Error: Found incorrect number of sub-nodes in cos while cleaning");
                return new Node(0,0);
            }

            if (this.sub.get(0).isNumber()){
                return new Node(sub.get(0).data.cos());
            }
        }
        if (this.operator.equals("tan")){
            if (sub.size() != 1){
                System.out.println("Error: Found incorrect number of sub-nodes in tan while cleaning");
                return new Node(0,0);
            }

            if (this.sub.get(0).isNumber()){
                return new Node(sub.get(0).data.tan());
            }
        }
        if (this.operator.equals("sec")){
            if (sub.size() != 1){
                System.out.println("Error: Found incorrect number of sub-nodes in sec while cleaning");
                return new Node(0,0);
            }

            if (this.sub.get(0).isNumber()){
                return new Node(sub.get(0).data.sec());
            }
        }
        if (this.operator.equals("csc")){
            if (sub.size() != 1){
                System.out.println("Error: Found incorrect number of sub-nodes in csc while cleaning");
                return new Node(0,0);
            }

            if (this.sub.get(0).isNumber()){
                return new Node(sub.get(0).data.csc());
            }
        }
        if (this.operator.equals("cot")){
            if (sub.size() != 1){
                System.out.println("Error: Found incorrect number of sub-nodes in cot while cleaning");
                return new Node(0,0);
            }

            if (this.sub.get(0).isNumber()){
                return new Node(sub.get(0).data.cot());
            }
        }

        if (this.sub.size() < 2) { // for functions like trig, it is required to only clean the left node and leave the right node as null
            Node clean =  new Node(this.operator, this.sub.get(0).clean());
            if (this.equals(clean)){
                return clean;
            } else {
                return clean.clean();
            }
        }

        Node clean = new Node(this.operator);
        for (Node subNode : sub){
            clean.sub.add(subNode.clean());
        }

        if (this.equals(clean)){
            return clean;
        } else {
            return clean.clean();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node object)) {
            return false;
        }

        if (this.sub == null && object.sub != null) {
            return false;
        }

        if (this.sub != null && object.sub == null) {
            return false;
        }

        if (this.sub != null){
            if (this.sub.size() != object.sub.size()) {
                // different length node lists
                return false;
            }
            for (int i = 0; i < sub.size(); i++){
                if (!this.sub.get(i).equals(object.sub.get(i))){
                    return false;
                }
            }
        }


        if (!this.operator.equals(object.operator)){

            return false;
        }

        return this.data.equals(object.data);
    }

    @Override
    public String toString() {
        if (this.sub == null){
            return data.toString();
        }
        if (operator.equals("/") || operator.equals("\\frac")){
            StringBuilder ret = new StringBuilder();
            ret.append("\\frac{");
            ret.append(sub.get(0));
            ret.append("}{");
            int counter = 0;
            for (Node subNode : sub){
                if (counter > 0){
                    ret.append(" * ");
                    ret.append(subNode);
                }
                counter++;
            }
            ret.append("}");
            return ret.toString();
        }
        if (operator.isEmpty()){
            return operator;
        }
        if (operator.contains("sin") ||operator.contains("cos") || operator.contains("tan") || operator.contains("sec") || operator.contains("csc") || operator.contains("cot")){
            return "\\" + operator + "{" + this.sub.get(0) + "}";
        }

        StringBuilder ret = new StringBuilder();
        ret.append(sub.get(0));
        int counter = 0;
        for (Node subNode : sub){
            if (counter > 0){
                ret.append(operator);
                ret.append(subNode);
            }
            counter++;
        }

        return ret.toString();
    }
}