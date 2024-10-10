class Node {
    String operator;
    ComplexNumber data;
    Node left, right;
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
        return "Data: " + operator + " Left: " + left.toString() + " Right: " + right.toString();
    }
}