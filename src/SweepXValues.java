import java.util.ArrayList;


public class SweepXValues {

    public ArrayList<ComplexNumber> xValues = new ArrayList<>();
    public ArrayList<ComplexNumber> yValues = new ArrayList<>();
    public Equation equation;


    public SweepXValues(double minXReal, double maxXReal, double minXImaginary, double maxXImaginary, int detail){
        // x values will be calculated at min, max and detail number of times in between
        for (double i = minXReal; i <= maxXReal; i += (maxXReal-minXReal)/(detail+1)) {
            for (double j = minXImaginary; j <= maxXImaginary ; j += (maxXImaginary-minXImaginary)/(detail+1)) {
                xValues.add(new ComplexNumber(i,j));
            }
        }
        // if they do not include equation here they will have to set it later
    }

    public SweepXValues(double minXReal, double maxXReal, double minXImaginary, double maxXImaginary, int detail, Equation equation1){
        // x values will be calculated at min, max and detail number of times in between
        for (double i = minXReal; i <= maxXReal; i += (maxXReal-minXReal)/(detail+1)) {
            for (double j = minXImaginary; j <= maxXImaginary ; j += (maxXImaginary-minXImaginary)/(detail+1)) {
                xValues.add(new ComplexNumber(i,j));
            }
        }
        this.equation = equation1;
    }

    public ArrayList<ComplexNumber> calculateYValues(){
        yValues.clear();
        for (ComplexNumber complexNumber : xValues){
            yValues.add(equation.evaluateEquation(complexNumber, equation.length));
        }
        return yValues;
    }

    public Vector[] calculateYValuesVector(){
        ArrayList<ComplexNumber> complexNumbers = calculateYValues();
        Vector[] ret = new Vector[complexNumbers.size()];

        double maxA = 0.0;
        double maxB = 0.0;
        double maxX = 0.0;
        maxX = xValues.get(xValues.size()-1).a;
        for (ComplexNumber complexNumber : complexNumbers){
            if (complexNumber.a > maxA){
                maxA = complexNumber.a;
            }
            if (complexNumber.b > maxB){
                maxB = complexNumber.b;
            }
        }

        int counter = 0;
        for (ComplexNumber complexNumber : complexNumbers){
            System.out.println(counter);
            ret[counter] = new Vector(xValues.get(counter).a/maxX, complexNumber.a/maxA, complexNumber.b/maxB, 1);
            counter++;
        }
        return ret;
    }

    @Override
    public String toString() {
        return yValues.toString();
    }
}
