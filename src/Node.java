class Node {
    // each node includes EITHER an operator or data, NOT both
    String operator;
    ComplexNumber data;
    Node left, right; // if the node does not have left and right nodes then it will have data
    Node(){
        data = new ComplexNumber();
        operator = "";
    }

    Node(String o, ComplexNumber complexNumber) {
        data = complexNumber;
        operator = o;
        left = null;
        right = null;
    }
    Node (String o, ComplexNumber complexNumber, Node n1, Node n2){
        data = complexNumber;
        operator = o;
        left = n1;
        right = n2;
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