import java.util.ArrayList;


public class SweepXValues {

    public ArrayList<ArrayList<ComplexNumber>> xValues = new ArrayList<>();
    public ArrayList<ComplexNumber> yValues = new ArrayList<>();
    public Equation equation;


    public SweepXValues(double minXReal, double maxXReal, double minXImaginary, double maxXImaginary, int detail){
        // x values will be calculated at min, max and detail number of times in between
        int counter = 0;
        for (double i = minXImaginary; i <= maxXImaginary; i += (maxXImaginary-minXImaginary)/(detail+1)) {
            xValues.add(new ArrayList<ComplexNumber>());
            for (double j = minXReal; j <= maxXReal ; j += (maxXReal-minXReal)/(detail+1)) {
                xValues.get(counter).add(new ComplexNumber(j,i));
            }
            counter++;
        }
        // if they do not include equation here they will have to set it later
    }

    public SweepXValues(double minXReal, double maxXReal, double minXImaginary, double maxXImaginary, int detail, Equation equation1){
        // x values will be calculated at min, max and detail number of times in between
        int counter = 0;
        for (double i = minXImaginary; i <= maxXImaginary; i += (maxXImaginary-minXImaginary)/(detail+1)) {
            xValues.add(new ArrayList<ComplexNumber>());
            for (double j = minXReal; j <= maxXReal ; j += (maxXReal-minXReal)/(detail+1)) {
                xValues.get(counter).add(new ComplexNumber(j,i));
            }
            counter++;
        }
        this.equation = equation1;
    }

    public ArrayList<ComplexNumber> calculateYValues(int whichBValue){
        yValues.clear();
        int counter = 0;
        for (ComplexNumber complexNumber : xValues.get(whichBValue)){
//            System.out.println(counter);
            yValues.add(equation.evaluateEquation(complexNumber, equation.length));
            counter++;
        }
        return yValues;
    }

    public Vector[] calculateYValuesVector(int whichBValue){
        calculateYValues(whichBValue);
        Vector[] ret = new Vector[yValues.size()];

        double maxA = 0.0;
        double maxB = 0.0;
        double maxX = 0.0;
        maxX = xValues.get(whichBValue).get(xValues.size()-1).a;
        for (ComplexNumber complexNumber : yValues){
            if (complexNumber.a > maxA){
                maxA = complexNumber.a;
            }
            if (complexNumber.b > maxB){
                maxB = complexNumber.b;
            }
        }

        int counter = 0;
        for (ComplexNumber complexNumber : yValues){
//            System.out.println(counter);
            ret[counter] = new Vector(xValues.get(whichBValue).get(counter).a/maxX, complexNumber.a/maxA, complexNumber.b/maxB, 1);
            counter++;
        }
        return ret;
    }

    @Override
    public String toString() {
        return yValues.toString();
    }
}
