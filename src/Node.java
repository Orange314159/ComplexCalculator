public class Node {
    // each node includes EITHER an operator or data, NOT both
    public String operator;
    public ComplexNumber data;
    public Node left, right; // if the node does not have left and right nodes then it will have data

    public Node(){
        data = new ComplexNumber();
        operator = "";
    }
    public Node(String o, ComplexNumber complexNumber) {
        data     = complexNumber;
        operator = o;
        left     = null;
        right    = null;
    }
    public Node(String o, ComplexNumber complexNumber, Node n1, Node n2){
        data     = complexNumber;
        operator = o;
        left     = n1;
        right    = n2;
    }
    public Node(String o, Node n1, Node n2){
        data     = new ComplexNumber();
        operator = o;
        left     = n1;
        right    = n2;
    }
    public Node(ComplexNumber complexNumber){
        data     = complexNumber;
        operator = "";
        left     =  null;
        right    = null;
    }
    public Node(double a, double b){
        data     = new ComplexNumber(a,b);
        operator = "";
        left     =  null;
        right    = null;
    }
    public boolean isNumber(){
        if (this.left != null || this.right != null){
            return false;
        }
        if (this.data == null || this.data.isX)
            return false;

        return this.operator.isEmpty();
    }
    public boolean isX(){
        if (this.left != null || this.right != null){
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
        if (this.isNumber()){
            return 0;
        }
        if (this.isX()){
            return 1;
        }
        if (this.left == null){
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
            default -> -1;
        };

    }

    public Node clean(){
        // the function of this method is to take a node that may include easily simplified values and then simplify them
        // for example if the node includes 0 * x + 4 it would simplify it to 4
        // of something like 2 * 90 + 14 - x would become 166 - x

                if (this.left == null){
            return this; // base case
        }
        if (this.operator.equals("+")){
            // cleaning in addition
            if (this.left.equals(new Node(0,0))){
                return this.right.clean(); // zero is the additive identity
            }
            if (this.right.equals(new Node(0,0))){
                return this.left.clean(); // zero is the additive identity
            }

            if (this.left.nodeType() < 2 && this.right.nodeType() < 2){

                if (this.left.isNumber() && this.right.isNumber()){
                    return new Node(this.left.data.add(this.right.data)).clean();
                }

                if (this.right.equals(this.left)){
                    return new Node("*", this.left.clean(), new Node(2,0)).clean(); // adding anything to itself results in 2 time itself
                }

                if (!this.right.data.isX && !this.left.data.isX){
                    // (some num) + (some other num) = some third num
                    return new Node(this.right.data.add(this.left.data, null)).clean(); // I know that these both are real numbers, not x
                    // here I am using the constructor of node that takes and input of a complex number
                }
            }
            if(this.left.equals(this.right)){
                // (some expression) + (some expression) = 2 * (some expression)
                return new Node("*", new Node(new ComplexNumber(2.0)), this.left).clean();
            }
        }
        if (this.operator.equals("-")){
            // cleaning in subtraction
            if (this.left.left == null && this.right.left == null){
                // the subtracting two numbers or x (not two expressions)
                // ex 4 - 7
                // not (4 * 2) - 18

                if (this.right.data.equals(new ComplexNumber(0,0)) && !this.right.data.isX){
                    return this.left.clean(); // zero is the additive identity
                }
                if (this.right.data.isX && this.left.data.isX){
                    // x - x = 0
                    return new Node(new ComplexNumber(0, 0)).clean();
                }
                if (!this.right.data.isX && !this.left.data.isX){
                    // (some num) + (some other num) = some third num
                    return new Node(this.right.data.sub(this.left.data, null)).clean(); // I know that these both are real numbers, not x
                    // here I am using the constructor of node that takes and input of a complex number
                }
            }
            if(this.left.equals(this.right)){
                // (some expression) - (some expression) = 0
                return new Node(new ComplexNumber(0,0)).clean();
            }
        }
        if (this.operator.equals("*")){
            // cleaning in mult
            if ( this.left.equals(new Node(0,0)) || this.right.equals(new Node(0,0))){
                return new Node(0,0); // multiplying anything by zero results in zero
            }
            if ( this.left.equals(new Node(1,0))){
                return this.right.clean(); // multiplying anything by 1 results in itself
            }
            if ( this.right.equals(new Node(1,0))){
                return this.left.clean(); // multiplying anything by 1 results in itself
            }
            if (this.left.left == null && this.right.left == null){
                // multiplying numbers like:
                // 4 * 2
                // or 8 * x
                if (this.left.equals(this.right)){
                    return new Node("^", this.left, new Node(2, 0)).clean();
                }
                if (this.left.equals(new Node(1,0))){
                    return this.right.clean(); // multiplying anything by one results in itself (multiplicative identity)
                }
                if (this.right.equals(new Node(1,0))){
                    return this.left.clean(); // multiplying anything by one results in itself (multiplicative identity)
                }
                if (!this.left.data.isX && !this.right.data.isX){
                    return new Node(this.left.data.mul(this.right.data, null)).clean(); // I am sure that neither of these values is "x" because I just checked in the above line
                }
            }
            if (this.left.nodeType() == 2 && this.right.nodeType() < 2){ // if this.left is addition and this.right is either "x" or some number
                // (a + b) * c = ab + bc
                Node leftPart  = new Node("*", this.left.left,  this.right);
                Node rightPart = new Node("*", this.left.right, this.right);
                return new Node("+", leftPart.clean(), rightPart.clean()).clean(); // order here does not matter because addition is commutative (just convention)
            }
            if (this.right.nodeType() == 2 && this.left.nodeType() < 2){
                // a * (b + c) = ab + ac
                Node leftPart =  new Node("*", this.right.left,  this.left);
                Node rightPart = new Node("*", this.right.right, this.left);
                return new Node("+", leftPart, rightPart).clean();
            }
            if (this.left.left != null && this.left.operator.equals("^") && this.right.operator.equals("^") && this.left.left.equals(this.right.left)){
                // I include the not null part to stop the possible warnings
                Node topPart = new Node("+", this.left.right, this.right.right);
                return new Node("^", this.left.left, topPart).clean();
            }


        }
        if (this.operator.equals("/")){
            // cleaning in division
            if (this.left.data.equals(new ComplexNumber(0,0)) && this.left.operator.isEmpty()){
                return new Node(0,0); // dividing zero by anything results in zero
            }

            if (this.right.data.equals(new ComplexNumber(0,0)) && this.right.operator.isEmpty()){
                System.out.println("Error: division by zero found in equation cleaning");
                return new Node(0,0); // dividing anything by zero results in undefined
            }
            if (this.left.left == null && this.right.left == null){
                // multiplying numbers like:
                // 4 * 2
                // or 8 * x
                if (this.left.equals(this.right)){
                    return new Node(1,0);
                }
                if (this.right.data.equals(new ComplexNumber(1,0))){
                    return this.left.clean(); // dividing anything by one results in itself
                }
                if (!this.left.data.isX && !this.right.data.isX){
                    return new Node(this.left.data.div(this.right.data, null)).clean(); // I am sure that neither of these values is "x" because I just checked in the above line
                }
            }
            if (this.left.operator.equals("+")){
                // (a + b) * c = ab + bc
                Node leftPart  = new Node("/", this.left.left,  this.right);
                Node rightPart = new Node("/", this.left.right, this.right);
                return new Node("+", leftPart, rightPart).clean(); // order here does not matter because addition is commutative (just convention)
            }
            if (this.left.left != null && this.left.operator.equals("^") && this.right.operator.equals("^") && this.left.left.equals(this.right.left)){
                // I include the not null part to stop the possible warnings
                Node topPart = new Node("-", this.left.right, this.right.right);
                return new Node("^", this.left.left, topPart).clean();
            }
        }
        if (this.operator.equals("^")){
            if (this.right.equals(new Node(0,0))){
                return new Node(1,0); // anything to the zero is 1
            }
            if (this.right.equals(new Node(1,0))){
                return this.left.clean(); // anything to the 1 is itself
            }
            if (this.right.isNumber() && this.left.isNumber()){
                return new Node(this.left.data.pow(this.right.data)); // do the exponent if they are both numbers
            }
        }
        if (this.operator.equals("log")){
            if (this.left.left == null && this.left.data != null && this.right.data != null){
                return new Node(left.data.log(right.data, new ComplexNumber(0, 0)));
            }
        }
        if (this.operator.equals("sin")){
            if (this.left.left == null && this.left.data != null && this.right == null){
                return new Node(left.data.sin(new ComplexNumber(0, 0)));
            }
        }
        if (this.operator.equals("cos")){
            if (this.left.left == null && this.left.data != null && this.right == null){
                return new Node(left.data.cos(new ComplexNumber(0, 0)));
            }
        }
        if (this.operator.equals("tan")){
            if (this.left.left == null && this.left.data != null && this.right == null){
                return new Node(left.data.tan());
            }
        }
        if (this.operator.equals("sec")){
            if (this.left.left == null && this.left.data != null && this.right == null){
                return new Node(left.data.sec());
            }
        }
        if (this.operator.equals("csc")){
            if (this.left.left == null && this.left.data != null && this.right == null){
                return new Node(left.data.csc());
            }
        }
        if (this.operator.equals("cot")){
            if (this.left.left == null && this.left.data != null && this.right == null){
                return new Node(left.data.cot());
            }
        }

        if (this.right == null) // for functions like trig, it is required to only clean the left node and leave the right node as null
            return new Node(this.operator, this.left.clean(), null);

        Node clean = new Node(this.operator, this.left.clean(), this.right.clean());

        if (this.equals(clean)){
            return clean;
        } else {
            return clean.clean();
        }
    }

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Node object)){
            return false;
        }

        if (this.right == null && this.left == null && object.left == null && object.right == null) {

            if(this.data != null && object.data != null) {
                // just a complex number
                return this.operator.equals(object.operator) && this.data.equals(object.data);
            }

            if((this.data == null && object.data == null)){
                // just an operator (this should not exist, but idk)
                return this.operator.equals(object.operator);
            }

            // this.data does not equal object.data
            return false;
        }

        if (this.right != null && this.left == null && object.left == null && object.right != null) {

            if (!this.right.equals(object.right)){
                return false;
            }

            if(this.data != null && object.data != null) {
                // just a complex number
                return this.operator.equals(object.operator) && this.data.equals(object.data);
            }

            if((this.data == null && object.data == null)){
                // just an operator (this should not exist, but idk)
                return this.operator.equals(object.operator);
            }

            // this.data does not equal object.data
            return false;
        }

        if (this.right == null && this.left != null && object.left != null && object.right == null) {

            if (!this.left.equals(object.left)){
                return false;
            }

            if(this.data != null && object.data != null) {
                // just a complex number
                return this.operator.equals(object.operator) && this.data.equals(object.data);
            }

            if((this.data == null && object.data == null)){
                // just an operator (this should not exist, but idk)
                return this.operator.equals(object.operator);
            }

            // this.data does not equal object.data
            return false;
        }

        if (this.right != null && this.left != null && object.left != null && object.right != null) {

            if (!this.left.equals(object.left)){
                return false;
            }

            if (!this.right.equals(object.right)){
                return false;
            }

            if(this.data != null && object.data != null) {
                // just a complex number
                return this.operator.equals(object.operator) && this.data.equals(object.data);
            }

            if((this.data == null && object.data == null)){
                // just an operator (this should not exist, but idk)
                return this.operator.equals(object.operator);
            }

            // this.data does not equal object.data
            return false;
        }

        // this.right does not equal object.right or this.left does not equal object.left
        return false;
    }

    @Override
    public String toString() {
        if (left == null){
            return data.toString();
        }
        if (operator.equals("/") || operator.equals("\\frac")){
            return "\\frac{" + left + "}{" + right + "}";
        }
        if (operator.isEmpty()){
            return operator;
        }
        if (operator.contains("sin") ||operator.contains("cos") || operator.contains("tan") || operator.contains("sec") || operator.contains("csc") || operator.contains("cot")){
            return "\\" + operator + "{" + left + "}";
        }
        return "(" + left + ")" + operator + "(" + right + ")";
    }
}