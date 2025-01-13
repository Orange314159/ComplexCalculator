// A complex number is a number that is a combination of a real and imaginary part
// An imaginary number is considered to be a number that is some real number times "i" the imaginary unit equal to sqrt(-1)
// As a convention of my code I will use the form (a + bi) for all complex numbers unless otherwise stated
public class ComplexNumber {
    public final static double CLOSEENOUGH = 0.00000001;
    public double a;
    public double b;
    public boolean isX = false; // some people may want to use something like x or xi in their eq and this code supports that

    public ComplexNumber(){
        this.a = 0;
        this.b = 0;
        this.isX = false;
    }
    public ComplexNumber(double a, double b){
        this.a = a;
        this.b = b;
        this.isX = false;
    }
    public ComplexNumber(double a){
        this.a = a;
        this.b = 0;
        this.isX = false;
    }
    public ComplexNumber(String str){
        // the expected input for "str" is some number such as 23.452 or i or x
        this.a = 1;
        this.b = 0;

        if (str.isEmpty()){
            this.a = 0;
            return;
        }
        if (str.contains("x")){
            this.isX = true;
        } else{
            if (str.equals("i")){
                this.a = 0;
                this.b = 1;
            } else {
                this.a = Double.parseDouble(str); // in Equation.java I will handle imaginary numbers such as 4i as 4 * i, this is why I only ever have to parse a double for the real component of x
                this.b = 0;
            }
        }
    }
    // multiplication and division
    public ComplexNumber mul(ComplexNumber c, ComplexNumber xValue){
        // if either complex number is x it is important to make sure it gets updated when calculating the result
        if (isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        if(c.isX){
            c.a = xValue.a;
            c.b = xValue.b;
        }
        // this equation is derived by solving (a + bi) * (c + di) using the FOIL method
        return new ComplexNumber((this.a*c.a - this.b*c.b), (this.a*c.b + this.b*c.a));
    }
    public ComplexNumber mul(ComplexNumber c){
        return mul(c, new ComplexNumber(0,0));
    }
    public ComplexNumber div(ComplexNumber c, ComplexNumber xValue){
        if (isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        if(c.isX){
            c.a = xValue.a;
            c.b = xValue.b;
        }
        // in order to split the number into both a real and imaginary part we multiply the denominator by its conjugate
        // this will result in a real number denominator which we can then use to solve for the real and imaginary parts
        double realPart = (this.a*c.a + this.b*c.b)/(c.a*c.a + c.b*c.b);
        double imaginaryPart = (this.b*c.a - this.a*c.b)/(c.a*c.a + c.b*c.b);
        return new ComplexNumber(realPart, imaginaryPart); // this one is kinda complicated, so I split it up (there is no real reason to do this other than readability)
    }
    public ComplexNumber div(ComplexNumber c){
        return div(c, new ComplexNumber(0,0));
    }
    // addition and subtraction
    public ComplexNumber add(ComplexNumber c, ComplexNumber xValue){
        if (isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        if(c.isX){
            c.a = xValue.a;
            c.b = xValue.b;
        }
        // this is quite trivial, it is just adding like terms
        return new ComplexNumber((this.a + c.a), (this.b + c.b));
    }
    public ComplexNumber add(ComplexNumber c){
        return add(c, new ComplexNumber(0,0));
    }
    public ComplexNumber sub(ComplexNumber c, ComplexNumber xValue){
        if (isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        if(c.isX){
            c.a = xValue.a;
            c.b = xValue.b;
        }
        // same as addition
        return new ComplexNumber((this.a - c.a), (this.b - c.b));
    }
    public ComplexNumber sub(ComplexNumber c){
        return sub(c, new ComplexNumber(0,0));
    }
    // exponents and logarithms
    public ComplexNumber log(ComplexNumber c, ComplexNumber xValue){
        if (isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        if(c.isX){
            c.a = xValue.a;
            c.b = xValue.b;
        }
        // you may be saying, is it possible to take a complex log? well of course
        // this function is saying log_this(c)
        // log_b(a) = ln(a)/ln(b)
        double r1 = Math.log(Math.sqrt(c.a * c.a + c.b * c.b)); // these are the logs of the magnitudes it would be more technically correct to not call them "r" but I only ever use the log of the magnitudes
        double r2 = Math.log(Math.sqrt(this.a * this.a + this.b * this.b));
        double t1 = Math.atan2(c.b, c.a); // these are the theta values
        double t2 = Math.atan2(this.b, this.a);
        double realPart      = (r1*r2 + t1*t2)/(r2*r2 + t2*t2);
        double imaginaryPart = (r2*t1 - r1*t2)/(r2*r2 + t2*t2);
        return new ComplexNumber(realPart, imaginaryPart);
    }
    public ComplexNumber log(ComplexNumber c){
        return log(c, new ComplexNumber(0,0));
    }
    public ComplexNumber pow(ComplexNumber c, ComplexNumber xValue){
//        System.out.println(c + "debug");
        if (this.isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        if(c.isX){
            c.a = xValue.a;
            c.b = xValue.b;
        }
        if(this.a == 0 && this.b == 0){
            return new ComplexNumber(0,0);
        }
        // this method is saying this^c
        // I am aware that there are an infinite number of solutions but im just doing this one (the principal power) for now (10/08/24)
        double r = Math.sqrt(this.a * this.a + this.b * this.b); // this time "r" is the proper magnitude
        double t = Math.atan2(this.b, this.a); // again this is theta
        double realPart      = Math.pow(r, c.a) / (Math.pow(Math.E, (c.b*t))) * Math.cos(c.b*Math.log(r) + c.a*t);
        double imaginaryPart = Math.pow(r, c.a) / (Math.pow(Math.E, (c.b*t))) * Math.sin(c.b*Math.log(r) + c.a*t);
        return new ComplexNumber(realPart, imaginaryPart);
    }
    public ComplexNumber pow(ComplexNumber c){
        return pow(c, new ComplexNumber(0,0));
    }
    // hyperbolic trig
    public ComplexNumber sinh(ComplexNumber xValue){
        // \frac{e^x-e^{-x}}{2}
        // or
        // -i * \sin{ix}
        if (this.isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        // this is just solving the above equation
        ComplexNumber numerator = (new ComplexNumber(Math.E, 0)).pow(this, this).sub((new ComplexNumber(Math.E, 0)).pow(this.mul(new ComplexNumber(-1,0), xValue), xValue), xValue);
        double realPart = numerator.a / 2;
        double imaginaryPart = numerator.b / 2;
        return new ComplexNumber(realPart, imaginaryPart);
    }
    public ComplexNumber sinh(){
        return sinh(new ComplexNumber(0,0));
    }
    public ComplexNumber cosh(ComplexNumber xValue){
        // trig functions become very repetitive so just assume that they all follow similar rules
        // \frac{e^+e^{-x}}{2}
        if (this.isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        ComplexNumber numerator = (new ComplexNumber(Math.E, 0)).pow(this, this).add((new ComplexNumber(Math.E, 0)).pow(this.mul(new ComplexNumber(-1,0), xValue), xValue), xValue);
        double realPart = numerator.a / 2;
        double imaginaryPart = numerator.b/2;
        return new ComplexNumber(realPart, imaginaryPart);
    }
    public ComplexNumber cosh(){
        return cosh(new ComplexNumber(0,0));
    }
    public ComplexNumber tanh(ComplexNumber xValue){
        // \frac{sinh}{cosh]
        ComplexNumber numerator = sinh(xValue);
        ComplexNumber denominator = cosh(xValue);
        return numerator.div(denominator, xValue);
    }
    public ComplexNumber tanh(){
        return tanh(new ComplexNumber(0,0));
    }
    public ComplexNumber coth(ComplexNumber xValue){
        // \frac{cosh}{sinh]
        ComplexNumber numerator = cosh(xValue);
        ComplexNumber denominator = sinh(xValue);
        return numerator.div(denominator, xValue);
    }
    public ComplexNumber coth(){
        return coth(new ComplexNumber(0,0));
    }
    public ComplexNumber sech(ComplexNumber xValue){
        // \frac{1}{cosh}
        ComplexNumber denominator = cosh(xValue);
        return new ComplexNumber(1, 0).div(denominator, new ComplexNumber());
    }
    public ComplexNumber sech(){
        return sech(new ComplexNumber(0,0));
    }
    public ComplexNumber csch(ComplexNumber xValue){
        // \frac{1}{sinh}
        ComplexNumber denominator = sinh(xValue);
        return new ComplexNumber(1, 0).div(denominator, new ComplexNumber());
    }
    public ComplexNumber csch(){
        return csch(new ComplexNumber(0,0));
    }
    // regular trig
    public ComplexNumber sin(ComplexNumber xValue){
        // I have to use angle sum formula and angle difference formula, also some wierd stuff with sinh and cosh
        // we can use the idea that sinh(x) = -isin(ix) to derive the formula that I use here to calculate sine(x)
        double bValue = this.b; // idk why I did this
        ComplexNumber  firstPart = (new ComplexNumber(Math.sin(a), 0)).mul((new ComplexNumber(bValue, 0).cosh(xValue)), xValue);
        ComplexNumber secondPart = (new ComplexNumber(Math.cos(a), 0)).mul((new ComplexNumber(bValue, 0).sinh(xValue)), xValue);
        return new ComplexNumber(firstPart.a, secondPart.a);
    }
    public ComplexNumber sin(){
        return sin(new ComplexNumber(0,0));
    }
    public ComplexNumber cos(ComplexNumber xValue){
        double bValue = this.b;
        ComplexNumber firstPart = (new ComplexNumber(Math.cos(a), 0)).mul((new ComplexNumber(bValue, 0).cosh(xValue)), xValue);
        ComplexNumber secondPart = (new ComplexNumber(Math.sin(a), 0)).mul((new ComplexNumber(bValue, 0).sinh(xValue)), xValue);
        return new ComplexNumber(firstPart.a, -1*secondPart.a);
    }
    public ComplexNumber cos(){
        return cos(new ComplexNumber(0,0));
    }
    public ComplexNumber tan(ComplexNumber xValue){
        ComplexNumber numerator = sin(xValue);
        ComplexNumber denominator = cos(xValue);
        return numerator.div(denominator, xValue);
    }
    public ComplexNumber tan(){
        return tan(new ComplexNumber(0,0));
    }
    public ComplexNumber cot(ComplexNumber xValue){
        ComplexNumber numerator = cos(xValue);
        ComplexNumber denominator = sin(xValue);
        return numerator.div(denominator, xValue);
    }
    public ComplexNumber cot(){
        return cot(new ComplexNumber(0,0));
    }
    public ComplexNumber sec(ComplexNumber xValue){
        ComplexNumber numerator = new ComplexNumber(1,0);
        ComplexNumber denominator = cos(xValue);
        return numerator.div(denominator, xValue);
    }
    public ComplexNumber sec(){
        return sec(new ComplexNumber(0,0));
    }
    public ComplexNumber csc(ComplexNumber xValue){
        ComplexNumber numerator = new ComplexNumber(1,0);
        ComplexNumber denominator = sin(xValue);
        return numerator.div(denominator, xValue);
    }
    public ComplexNumber csc(){
        return csc(new ComplexNumber(0,0));
    }
    // inverse hyperbolic trig
    public ComplexNumber asinh(ComplexNumber xValue){
        // \log{x + (x^2 + 1)^{1/2} }
        if (this.isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        return new ComplexNumber(Math.E ,0).log(((((this.pow(new ComplexNumber(2,0),xValue)).add(new ComplexNumber(1,0), xValue)).pow(new ComplexNumber(0.5,0),xValue)).add(this, xValue)),xValue);
    }
    public ComplexNumber asinh(){
        return asinh(new ComplexNumber(0,0));
    }
    public ComplexNumber acosh(ComplexNumber xValue){
        // \log{x + (x^2 - 1)^{1/2} }
        if (this.isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        return new ComplexNumber(Math.E ,0).log(((((this.pow(new ComplexNumber(2,0),xValue)).sub(new ComplexNumber(1,0), xValue)).pow(new ComplexNumber(0.5,0),xValue)).add(this, xValue)),xValue);
    }
    public ComplexNumber acosh(){
        return acosh(new ComplexNumber(0,0));
    }
    public ComplexNumber atanh(ComplexNumber xValue){
        // 1/2 * \log{\frac{1+x}{1-x}}
        if (this.isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        return (new ComplexNumber(Math.E ,0).log( (this.add(new ComplexNumber(1,0),xValue)).div((new ComplexNumber(1,0).sub(this,xValue)),xValue),xValue)).mul(new ComplexNumber(0.5,0),xValue);
    }
    public ComplexNumber atanh(){
        return atanh(new ComplexNumber(0,0));
    }
    public ComplexNumber acoth(ComplexNumber xValue){
        // 1/2 * \log{\frac{1+x}{1-x}}
        if (this.isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        return (new ComplexNumber(Math.E ,0).log( (this.add(new ComplexNumber(1,0),xValue)).div((this.sub(new ComplexNumber(1,0),xValue)),xValue),xValue)).mul(new ComplexNumber(0.5,0),xValue);
    }
    public ComplexNumber acoth(){
        return acoth(new ComplexNumber(0,0));
    }
    public ComplexNumber asech(ComplexNumber xValue){
        // 1/2 * \log{\frac{1+x}{1-x}}
        if (this.isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        return new ComplexNumber(Math.E, 0).log((new ComplexNumber(1,0).add((new ComplexNumber(1,0).sub(this.pow(new ComplexNumber(2,0),xValue),xValue)).pow(new ComplexNumber(0.5,0),xValue), xValue)).div(this,xValue), xValue);
    }
    public ComplexNumber asech(){
        return asech(new ComplexNumber(0,0));
    }
    public ComplexNumber acsch(ComplexNumber xValue){
        // 1/2 * \log{\frac{1+x}{1-x}}
        if (this.isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        return new ComplexNumber(Math.E, 0).log((new ComplexNumber(1,0).add((new ComplexNumber(1,0).add(this.pow(new ComplexNumber(2,0),xValue),xValue)).pow(new ComplexNumber(0.5,0),xValue), xValue)).div(this,xValue), xValue);
    }
    public ComplexNumber acsch(){
        return acsch(new ComplexNumber(0,0));
    }
    // inverse regular trig
    public ComplexNumber asin(ComplexNumber xValue){
        // -i * \log{(1-x^2)^{1/2} + ix}
        if (this.isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        return (new ComplexNumber(Math.E, 0).log((((new ComplexNumber(1,0).sub(this.pow(new ComplexNumber(2,0),xValue),xValue)).pow(new ComplexNumber(0.5,0),xValue)).add(this.mul(new ComplexNumber(0,1),xValue),xValue)),xValue)).mul(new ComplexNumber(0,-1),xValue);
    }
    public ComplexNumber asin(){
        return asin(new ComplexNumber(0,0));
    }
    public ComplexNumber acos(ComplexNumber xValue){
        // -i * \log{(1-x^2)^{1/2}i + x}
        if (this.isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        return (new ComplexNumber(Math.E, 0).log((((new ComplexNumber(1,0).sub(this.pow(new ComplexNumber(2,0),xValue),xValue)).pow(new ComplexNumber(0.5,0),xValue)).mul(new ComplexNumber(0,1),xValue).add(this,xValue)),xValue)).mul(new ComplexNumber(0,-1),xValue);
    }
    public ComplexNumber acos(){
        return acos(new ComplexNumber(0,0));
    }
    public ComplexNumber atan(ComplexNumber xValue){
        // -i/2 * \log{\frac{i-x}{1+x}}
        if (this.isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        return ((new ComplexNumber(Math.E, 0).log(((new ComplexNumber(0,1)).sub(this,xValue)).div(((new ComplexNumber(0,1)).add(this,xValue)),xValue),xValue)).mul(new ComplexNumber(0,-1),xValue)).div(new ComplexNumber(2,0),xValue);
    }
    public ComplexNumber atan(){
        return atan(new ComplexNumber(0,0));
    }
    public ComplexNumber acot(ComplexNumber xValue){
        // \atan(1/x)
        if (this.isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        return (new ComplexNumber(1,0).div(this,xValue)).atan(xValue);
    }
    public ComplexNumber acot(){
        return acot(new ComplexNumber(0,0));
    }
    public ComplexNumber asec(ComplexNumber xValue){
        // \acos(1/x)
        if (this.isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        return (new ComplexNumber(1,0).div(this,xValue)).acos(xValue);
    }
    public ComplexNumber asec(){
        return asec(new ComplexNumber(0,0));
    }
    public ComplexNumber acsc(ComplexNumber xValue){
        // \asin(1/x)
        if (this.isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        return (new ComplexNumber(1,0).div(this,xValue)).asin(xValue);
    }
    public ComplexNumber acsc(){
        return acsc(new ComplexNumber(0,0));
    }
    // factorials
    public Double gammaIntegral(double detail, ComplexNumber input, boolean real){
        // this is truly solving the Riemann sum of the gamma function over some small set
        double total = 0;
        if (real){
            for (double i = 0.000000000000000001; i < 30; i+=detail) {
                // this converges quickly (we will go to 30 for a good approximation (this gets us to the order of E-6 or E-7 precision)
                // I cant take log of zero, so I will get very close to zero to start with
                total += Math.pow(i,(input.a)) * (1/Math.pow(Math.E, (i))) * Math.cos(input.b * Math.log(i));
            }
        } else {
            // imaginary (only difference is the sin here where cos was used in the real part)
            for (double i = 0.000000000000000001; i < 30; i+=detail) {
                total += Math.pow(i,(input.a)) * (1/Math.pow(Math.E, (i))) * Math.sin(input.b * Math.log(i));
            }
        }
        total *= detail;
        return total;
    }
    public ComplexNumber gam(ComplexNumber xValue){
        // yes I dislike the abbreviation as well, but I want all of my functions to have a three or four letter abbreviation that is used
        if (this.isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        // the gamma function is the extension of the factorial to all real numbers, what I am doing here is
        // analytically continuing this to a general complex number (not 0 or any negative integer which are out of the domain of the function)
        if((this.a == ((int)this.a) && this.a < 0)){
            return new ComplexNumber();
        } else if (this.a == 0 && this.b == 0) {
            return new ComplexNumber(1,0);
        } else if(this.a == 0){
            // when a == 0 the graph moves rapidly at the beginning so a smaller detail is required
            double realPart      = gammaIntegral(0.0005,this,true);
            // imaginary part = integral from 0 to infinity {t^{a-1}*e^{-t}*sin(b*ln{t})dt}
            double imaginaryPart = gammaIntegral(0.0005,this,false);
            return new ComplexNumber(realPart, imaginaryPart);
        }
        // real part = integral from 0 to infinity {t^{a-1}*e^{-t}*cos(b*ln{t})dt}
        // to evaluate the integral I will use a left Riemann sum
        double realPart      = gammaIntegral(0.2,this,true);
        // imaginary part = integral from 0 to infinity {t^{a-1}*e^{-t}*sin(b*ln{t})dt}
        double imaginaryPart = gammaIntegral(0.2,this,false);
        return new ComplexNumber(realPart, imaginaryPart);
    }
    // absolute value
    public ComplexNumber abs(ComplexNumber xValue){
        if (this.isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        // the absolute value of a complex number is just the distance from the origin, use pythagoras to solve sqrt(z.a^2 + z.b^2) = |z|
        return new ComplexNumber(Math.sqrt(this.a*this.a + this.b*this.b), 0);
    }
    public ComplexNumber abs(){
        return abs(new ComplexNumber(0,0));
    }

    @Override
    public String toString() {
        if (isX){
            return "x";
        }
        if (this.a == 0){
            return this.b + "i";
        }
        if (this.b == 0){
            return this.a + "";
        }
        return this.a + " + " + this.b + "i";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ComplexNumber)){
            return false;
        }
        if(((ComplexNumber) obj).a != this.a){
            return Math.abs(((((ComplexNumber) obj).a)) - (this.a)) <= CLOSEENOUGH;
        }
        if(((ComplexNumber) obj).b != this.b){
            return Math.abs(((((ComplexNumber) obj).b)) - (this.b)) <= CLOSEENOUGH;
        }
        return ((ComplexNumber) obj).isX == this.isX;
    }
}
