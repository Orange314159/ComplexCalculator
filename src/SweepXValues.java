import java.util.ArrayList;


public class SweepXValues {

    public ArrayList<ArrayList<ComplexNumber>> xValues = new ArrayList<>();
    public int imaginaryValues;
    public ArrayList<ComplexNumber> yValues = new ArrayList<>();
    public Equation equation;

    public SweepXValues(double minXReal, double maxXReal, double minXImaginary, double maxXImaginary, int detail){
        // x values will be calculated at min, max and detail number of times in between
        int counter = 0;
        for (double i = minXImaginary; i <= maxXImaginary; i += (maxXImaginary-minXImaginary)/(detail+1)) {
            xValues.add(new ArrayList<>());
            for (double j = minXReal; j <= maxXReal ; j += (maxXReal-minXReal)/(detail+1)) {
                xValues.get(counter).add(new ComplexNumber(j,i));
            }
            counter++;
        }
        this.imaginaryValues = counter;
        // if they do not include equation here they will have to set it later
    }
    public SweepXValues(double minXReal, double maxXReal, double minXImaginary, double maxXImaginary, int detail, int detail2, Equation equation1){
        // x values will be calculated at min, max and detail number of times in between
        int counter = 0;
        for (double i = minXImaginary; i <= maxXImaginary; i += (maxXImaginary-minXImaginary)/(detail2+1)) {
            xValues.add(new ArrayList<>());
            for (double j = minXReal; j <= maxXReal ; j += (maxXReal-minXReal)/(detail+1)) {
                xValues.get(counter).add(new ComplexNumber(j,i));
            }
            counter++;
        }
        this.equation = equation1;
        this.imaginaryValues = counter;
    }

    public ArrayList<ComplexNumber> calculateYValues(int whichBValue){
        yValues.clear();
        // loop through all the x-values and calculate the corresponding y-values
        for (ComplexNumber complexNumber : xValues.get(whichBValue)){
            yValues.add(equation.evaluateEquation(complexNumber, equation.length));
        }
        return yValues;
    }

    public Vector[] calculateYValuesVector(int whichBValue){
        yValues = calculateYValues(whichBValue);
        Vector[] ret = new Vector[yValues.size()];

        // I want to scale all the values to fit within 1x1x1 cube, so I have to find the max values and divide byt the max (at some point I hope to include a log scale option)
        double maxA = 0.0;
        double maxB = 0.0;
        double maxX = 0.0;
        for (ComplexNumber complexNumber : xValues.get(whichBValue)){
            if (Math.abs(complexNumber.a) > maxA){
                maxX = Math.abs(complexNumber.a);
            }
        }
        for (ComplexNumber complexNumber : yValues){
            if (Math.abs(complexNumber.a) > maxA){
                maxA = Math.abs(complexNumber.a);
            }
            if (Math.abs(complexNumber.b)> maxB){
                maxB = Math.abs(complexNumber.b);
            }
        }
        // if the max does not exist I should set it to one that way I am not dividing by zero
        if (maxA == 0.0){
            maxA = 1;
        } else if (maxB == 0.0) {
            maxB = 1;
        } else if (maxX == 0.0){
            maxX =1;
        }

        int counter = 0;
        for (ComplexNumber complexNumber : yValues){
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
