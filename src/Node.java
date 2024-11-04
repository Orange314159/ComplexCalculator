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