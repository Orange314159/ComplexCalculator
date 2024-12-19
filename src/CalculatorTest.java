import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    // addition subtraction
    @Test
    public void testAddition(){
        // integers
        ComplexNumber c0 = new ComplexNumber(0,0);
        ComplexNumber c1 = new ComplexNumber(1,5);
        ComplexNumber c2 = new ComplexNumber(-3, -4);
        // add zero to positive
//        System.out.println("0 plus 1+5i");
        ComplexNumber result = c0.add(c1, new ComplexNumber());
        assertEquals(result, new ComplexNumber(1, 5));
        // add zero to negative
//        System.out.println("0 plus -3+-4i");
        result = c0.add(c2, new ComplexNumber());
        assertEquals(result, new ComplexNumber(-3, -4));
        // add pos and neg numbers
//        System.out.println("-3+-4i plus 1+5i");
        result = c1.add(c2, new ComplexNumber());
        assertEquals(result, new ComplexNumber(-2, 1));
        // doubles
        ComplexNumber c0d = new ComplexNumber(0.5,0.2);
        ComplexNumber c1d = new ComplexNumber(1.2,5.9);
        ComplexNumber c2d = new ComplexNumber(-3.0, -4.8);
//        System.out.println("0.5+0.5i plus 1.2+5.9i");
        ComplexNumber resultDouble = c0d.add(c1d, new ComplexNumber());
        assertEquals(resultDouble, new ComplexNumber(1.7, 6.1));
//        System.out.println("0.5+0.5i plus -3+-4.8i");
        resultDouble = c0d.add(c2d, new ComplexNumber());
        assertEquals(resultDouble, new ComplexNumber(-2.5, -4.6));
//        System.out.println("1.2+5.9i plus -3+-4.8i");
        resultDouble = c1d.add(c2d, new ComplexNumber());
        assertEquals(resultDouble, new ComplexNumber(-1.8, 1.1));
    }
    @Test
    public void testSubtraction(){
        ComplexNumber c0 = new ComplexNumber(0,0);
        ComplexNumber c1 = new ComplexNumber(1,5);
        ComplexNumber c2 = new ComplexNumber(-3, -4);
        // add zero to positive
//        System.out.println("0 plus 1+5i");
        ComplexNumber result = c0.sub(c1, new ComplexNumber());
        assertEquals(result, new ComplexNumber(-1, -5));
        // add zero to negative
//        System.out.println("0 plus -3+-4i");
        result = c0.sub(c2, new ComplexNumber());
        assertEquals(result, new ComplexNumber(-3, 4));
        // add pos and neg numbers
//        System.out.println("-3+-4i plus 1+5i");
        result = c1.sub(c2, new ComplexNumber());
        assertEquals(result, new ComplexNumber(4, 9));
    }
    // multiplication division
    @Test
    public void testMultiplication(){
        ComplexNumber c0 = new ComplexNumber(0,0);
        ComplexNumber c1 = new ComplexNumber(1,5);
        ComplexNumber c2 = new ComplexNumber(-3, -4);

        ComplexNumber result = c0.mul(c1, new ComplexNumber());
        assertEquals(result, new ComplexNumber(0, 0));

        result = c0.mul(c2, new ComplexNumber());
        assertEquals(result, new ComplexNumber(0, 0));

        result = c1.mul(c2, new ComplexNumber());
        assertEquals(result, new ComplexNumber(17, -19));
    }
    @Test
    public void testDivision(){
        ComplexNumber c0 = new ComplexNumber(0, 0);
        ComplexNumber c1 = new ComplexNumber(1.0, 5.0);
        ComplexNumber c2 = new ComplexNumber(-3.0, -4.0);

        ComplexNumber result = c0.div(c1, new ComplexNumber());
        assertEquals(result, new ComplexNumber(0, 0));

        result = c0.div(c2, new ComplexNumber());
        assertEquals(result, new ComplexNumber(0, 0));

        result = c1.div(c2, new ComplexNumber());
        assertEquals(result, new ComplexNumber(-0.92, -0.44));
    }
    // log and exponents
    @Test
    public void testLog(){
        ComplexNumber c0 = new ComplexNumber(5,7);
        ComplexNumber c1 = new ComplexNumber(1.0,5.0);
        ComplexNumber c2 = new ComplexNumber(-3.0, -4.0);

        ComplexNumber result = c0.log(c1, new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.869274608, 0.254231534));

        result = c0.log(c2, new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.245496388, -1.13736814));

        result = c1.log(c2, new ComplexNumber());
        assertEquals(result, new ComplexNumber(-0.0923487193, - 1.28140195));
    }
    @Test
    public void testPow(){
        ComplexNumber c0 = new ComplexNumber(5,7);
        ComplexNumber c1 = new ComplexNumber(1.0,5.0);
        ComplexNumber c2 = new ComplexNumber(-3.0, -4.0);

        ComplexNumber result = c0.pow(c1, new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.0486684777723081, -0.0560375556593289));

        result = c0.pow(c2, new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.0315074151063000, 0.0629284667170977));

        result = c1.pow(c2, new ComplexNumber());
        assertEquals(result, new ComplexNumber(-0.6446348764729833, 1.7168940896479310));
    }
    // hyperbolic trig
    @Test
    public void testSinh(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.sinh(new ComplexNumber());
        assertEquals(result, new ComplexNumber(-1.0442677385989620, -5.1824865415641153));

        result = c1.sinh(new ComplexNumber());
        assertEquals(result, new ComplexNumber(-2.1412914141657269, -2.9904717625658672));
    }
    @Test
    public void testCosh(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.cosh(new ComplexNumber());
        assertEquals(result, new ComplexNumber(-1.0634379054806748, -5.0890639436371510));

        result = c1.cosh(new ComplexNumber());
        assertEquals(result, new ComplexNumber(-2.2228379617064800, -2.8807639692150002));
    }
    @Test
    public void testTanh(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.tanh(new ComplexNumber());
        assertEquals(result, new ComplexNumber(1.0168352268403071 , 0.0072849124439503));

        result = c1.tanh(new ComplexNumber());
        assertEquals(result, new ComplexNumber(1.0101797155901111, 0.0361620760697776));
    }
    @Test
    public void testSech(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.sech(new ComplexNumber());
        assertEquals(result, new ComplexNumber(-0.0393436433176296, 0.1882783334947693));

        result = c1.sech(new ComplexNumber());
        assertEquals(result, new ComplexNumber(-0.1678904773114276, 0.2175834883806678));
    }
    @Test
    public void testCsch(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.csch(new ComplexNumber());
        assertEquals(result, new ComplexNumber(-0.0373637830570891, 0.1854287896464926));

        result = c1.csch(new ComplexNumber());
        assertEquals(result, new ComplexNumber(-0.1582852938418875, 0.2210571146608868));
    }
    @Test
    public void testCoth(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.coth(new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.9833930306973088, -0.0070453225237700));

        result = c1.coth(new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.9886559309853240, -0.0353915748172028));
    }
    // regular trig
    @Test
    public void testSin(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.sin(new ComplexNumber());
        assertEquals(result, new ComplexNumber(32.3481380844319588, -31.9421110347954738));

        result = c1.sin(new ComplexNumber());
        assertEquals(result, new ComplexNumber(4.2133095948510597, 1.8328737338588796));
    }
    @Test
    public void testCos(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.cos(new ComplexNumber());
        assertEquals(result, new ComplexNumber(-31.9498397965854011, -32.3403129730951306));

        result = c1.cos(new ComplexNumber());
        assertEquals(result, new ComplexNumber(-1.8775255405967493, 4.1131075567491946));
    }
    @Test
    public void testTan(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.tan(new ComplexNumber());
        assertEquals(result, new ComplexNumber(-0.0002419144075377, 1.0000029679605014));

        result = c1.tan(new ComplexNumber());
        assertEquals(result, new ComplexNumber(-0.0181868614370665, -1.0160598137391490));
    }
    @Test
    public void testSec(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.sec(new ComplexNumber());
        assertEquals(result, new ComplexNumber(-0.0154594395256580, 0.0156483761994278));

        result = c1.sec(new ComplexNumber());
        assertEquals(result, new ComplexNumber(-0.0918430737577362, -0.0918430737577362));
    }
    @Test
    public void testCsc(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.csc(new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.0156520686787634,  0.0154556071931052));

        result = c1.csc(new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.1995750553363244, -0.0868191308102324));
    }
    @Test
    public void testCot(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.cot(new ComplexNumber());
        assertEquals(result, new ComplexNumber(-0.0002419129574020,  -0.9999969735262514));

        result = c1.cot(new ComplexNumber());
        assertEquals(result, new ComplexNumber(-0.0176108406539949, -0.0176108406539949));
    }
    // inverse hyperbolic trig
    @Test
    public void testAsinh(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.asinh(new ComplexNumber());
        assertEquals(result, new ComplexNumber(2.3140589852532747, 1.0823846347595283));

        result = c1.asinh(new ComplexNumber());
        assertEquals(result, new ComplexNumber(1.7812652417741113, -0.8094432641810320));
    }
    @Test
    public void testAcosh(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.acosh(new ComplexNumber());
        assertEquals(result, new ComplexNumber(2.3251296898986475, 1.0982314597675402));

        result = c1.acosh(new ComplexNumber());
        assertEquals(result, new ComplexNumber(1.7871256117380554, -0.8655256754501864));
    }
    @Test
    public void testAtanh(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.atanh(new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.0883916729577262 , 1.3967829556994327));

        result = c1.atanh(new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.2143886522837231, -1.3142319371083135));
    }
    @Test
    public void testAsech(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.asech(new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.1742033642115091, -1.4811731013307252));

        result = c1.asech(new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.2533198666567144, 1.3510573235572823));
    }
    @Test
    public void testAcsch(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.acsch(new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.0921360796238691, -0.1745302276259973));

        result = c1.acsch(new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.2299519750135477, 0.2299519750135477));
    }
    @Test
    public void testAcoth(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.acoth(new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.0883916729577262, -0.1740133710954640));

        result = c1.acoth(new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.2143886522837231, 0.2565643896865832));
    }
    // inverse regular trig
    @Test
    public void testAsin(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.asin(new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.4725648670273800,  2.3251296898986542));

        result = c1.asin(new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.7052706513447102, -1.7871256117380554));
    }
    @Test
    public void testAcos(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.acos(new ComplexNumber());
        assertEquals(result, new ComplexNumber(1.0982314597675167, -2.3251296898986542));

        result = c1.acos(new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.8655256754501863, 1.7871256117380554));
    }
    @Test
    public void testAtan(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.atan(new ComplexNumber());
        assertEquals(result, new ComplexNumber(1.4773792136917188, 0.1746659104725478));

        result = c1.atan(new ComplexNumber());
        assertEquals(result, new ComplexNumber(1.3360287479662243, -0.2417206196650745));
    }
    @Test
    public void testAsec(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.asec(new ComplexNumber());
        assertEquals(result, new ComplexNumber(1.4811731013307254, 0.1742033642115098));

        result = c1.asec(new ComplexNumber());
        assertEquals(result, new ComplexNumber(1.3510573235572823, -0.2533198666567142));
    }
    @Test
    public void testAcsc(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.acsc(new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.0896232254641711, -0.1742033642115098));

        result = c1.acsc(new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.2197390032376142, 0.2533198666567142));
    }
    @Test
    public void testAcot(){
        ComplexNumber c0 = new ComplexNumber(2.35, 4.51);
        ComplexNumber c1 = new ComplexNumber(1.99, -2.21);

        ComplexNumber result = c0.acot(new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.0934171131031779,  -0.1746659104725478));

        result = c1.acot(new ComplexNumber());
        assertEquals(result, new ComplexNumber(0.2347675788286723, 0.2417206196650745));
    }
    // node functions
    @Test
    public void testCleanNodeMultiplication(){
        // less complicated
        // x * 0 = 0
        Node n0 = new Node(0,0);
        Node n1 = new Node(new ComplexNumber("x"));
        Node n2 = new Node("*", n0, n1);

        assertEquals(n2.clean(), n0);

        // more complicated
        // a * b = c where a b and c are all numbers
        Node n3 = new Node(2,3);
        Node n4 = new Node(3,5);
        Node n5 = new Node("*", n3, n4);

        assertEquals(n5.clean(), new Node(-9, 19));

        // more complicated
        // x * x = x^2
        Node n6 = new Node("*", n3, n3);

        assertEquals(n6.clean(), new Node("^", n3, new Node(2, 0)));

        // less complicated
        // 1 * x = x
        Node n7 = new Node(1, 0);
        Node n8 = new Node("*", n7, n3);

        assertEquals(n8.clean(), n3);
    }
    @Test
    public void testCleanNodeAddition(){
        // less complicated
        // x * 0 = 0
        Node n0 = new Node(0,0);
        Node n1 = new Node(new ComplexNumber("x"));
        Node n2 = new Node("+", n0, n1);

        assertEquals(n2.clean(), n1);

        // more complicated
        // a * b = c where a b and c are all numbers
        Node n3 = new Node(2,3);
        Node n4 = new Node(3,5);
        Node n5 = new Node("+", n3, n4);

        assertEquals(n5.clean(), new Node(5, 8));

        // more complicated
        // x * x = x^2
        Node n6 = new Node("+", n3, n3);

        assertEquals(n6.clean(), (new Node("*", n3, new Node(2, 0))).clean());
    }
}