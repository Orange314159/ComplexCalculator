public class ComplexNumber {
    public double a;
    public boolean isX = false; // some people may want to use something like x or xi in their eq and this code supports that
    public double b;

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
    public ComplexNumber(String str){
        this.a = 0;
        this.b = 0;

        if (str.isEmpty()){
            return;
        }
        if (str.contains("x")){
            this.isX = true;
        } else{
            if (str.equals("i")){
                this.a = 0;
                this.b = 1;
            } else {
                this.a = Double.parseDouble(str);
                this.b = 0;
            }
        }
    }

    public ComplexNumber mul(ComplexNumber c, ComplexNumber xValue){
        if (isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        if(c.isX){
            c.a = xValue.a;
            c.b = xValue.b;
        }
        return new ComplexNumber((this.a*c.a - this.b*c.b), (this.a*c.b + this.b*c.a));
    }
    public ComplexNumber div(ComplexNumber c, ComplexNumber xValue){
//        System.out.println(this + " / " + c);
        if (isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        if(c.isX){
            c.a = xValue.a;
            c.b = xValue.b;
        }
        double realPart = (this.a*c.a + this.b*c.b)/(c.a*c.a + c.b*c.b);
        double imaginaryPart = (this.b*c.a - this.a*c.b)/(c.a*c.a + c.b*c.b);
        return new ComplexNumber(realPart, imaginaryPart); // this one is complicated, so I split it up (there is no real reason to do this other than readability)
    }

    public ComplexNumber add(ComplexNumber c, ComplexNumber xValue){
        if (isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        if(c.isX){
            c.a = xValue.a;
            c.b = xValue.b;
        }
        return new ComplexNumber((this.a + c.a), (this.b + c.b));
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
        return new ComplexNumber((this.a - c.a), (this.b - c.b));
    }

    public ComplexNumber log(ComplexNumber c, ComplexNumber xValue){
        if (isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        if(c.isX){
            c.a = xValue.a;
            c.b = xValue.b;
        }
        // you may be saying, is it possible to take a complex log, well of course
        // this function is saying log_c(this)
        // log_b(a) = ln(a)/ln(b)
        // Math.atan2(y, x)
        double r1 = Math.log(Math.sqrt(c.a * c.a + c.b * c.b)); // these are the logs of the magnitudes
        double r2 = Math.log(Math.sqrt(this.a * this.a + this.b * this.b));
        double t1 = Math.atan2(c.b, c.a); // these are the theta values
        double t2 = Math.atan2(this.b, this.a);
        double realPart      = (r1*r2 + t1*t2)/(r2*r2 + t2*t2);
        double imaginaryPart = (r2*t1 - r1*t2)/(r2*r2 + t2*t2);
        return new ComplexNumber(realPart, imaginaryPart);
    }
    public ComplexNumber pow(ComplexNumber c, ComplexNumber xValue){
        if (isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        if(c.isX){
            c.a = xValue.a;
            c.b = xValue.b;
        }
//        System.out.println(this + "_^_" + c);
        if(this.a == 0 && this.b == 0){
            return new ComplexNumber(0,0);
        }
        // this one is also complicated, im not going to explain it...
        // this method is saying this^c
        // I am aware that there are an infinite number of solutions but im just doing this one for now (10/08/24)
        double r = Math.sqrt(this.a * this.a + this.b * this.b);
        double t = Math.atan2(this.b, this.a);
        double realPart      = Math.pow(r, c.a) / (Math.pow(Math.E, (c.b*t))) * Math.cos(c.b*Math.log(r) + c.a*t);
        double imaginaryPart = Math.pow(r, c.a) / (Math.pow(Math.E, (c.b*t))) * Math.sin(c.b*Math.log(r) + c.a*t);
        return new ComplexNumber(realPart, imaginaryPart);
    }

    public ComplexNumber sinh(ComplexNumber xValue){
        // \frac{e^x-e^{-x}}{2}
        if (this.isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        ComplexNumber numerator = (new ComplexNumber(2.71828, 0)).pow(this, this).sub((new ComplexNumber(2.71828, 0)).pow(this.mul(new ComplexNumber(-1,0), xValue), xValue), xValue);
        double realPart = numerator.a / 2;
        double imaginaryPart = numerator.b/2;
        return new ComplexNumber(realPart, imaginaryPart);
    }
    public ComplexNumber cosh(ComplexNumber xValue){
        // \frac{e^+e^{-x}}{2}
        if (this.isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        ComplexNumber numerator = (new ComplexNumber(2.71828, 0)).pow(this, this).add((new ComplexNumber(2.71828, 0)).pow(this.mul(new ComplexNumber(-1,0), xValue), xValue), xValue);
        double realPart = numerator.a / 2;
        double imaginaryPart = numerator.b/2;
        return new ComplexNumber(realPart, imaginaryPart);
    }
    public ComplexNumber tanh(ComplexNumber xValue){
        // \frac{sinh}{cosh]
        ComplexNumber numerator = sinh(xValue);
        ComplexNumber denominator = cosh(xValue);
        return numerator.div(denominator, xValue);
    }
    public ComplexNumber coth(ComplexNumber xValue){
        // \frac{cosh}{sinh]
        ComplexNumber numerator = cosh(xValue);
        ComplexNumber denominator = sinh(xValue);
        return numerator.div(denominator, xValue);
    }
    public ComplexNumber sech(ComplexNumber xValue){
        // \frac{1}{cosh}
        ComplexNumber denominator = cosh(xValue);
        return new ComplexNumber(1/denominator.a, 1/denominator.b);
    }
    public ComplexNumber csch(ComplexNumber xValue){
        // \frac{1}{sinh}
        ComplexNumber denominator = sinh(xValue);
        return new ComplexNumber(1/denominator.a, 1/denominator.b);
    }

    public ComplexNumber sin(ComplexNumber xValue){
        // I have to use angle sum formula and angle difference formula, also some wierd stuff with sinh and cosh
        double bValue = this.b;
        ComplexNumber  firstPart = (new ComplexNumber(Math.sin(a), 0)).mul((new ComplexNumber(bValue, 0).cosh(xValue)), xValue);
        ComplexNumber secondPart = (new ComplexNumber(Math.cos(a), 0)).mul((new ComplexNumber(bValue, 0).sinh(xValue)), xValue);
        return new ComplexNumber(firstPart.a, secondPart.a);
    }
    public ComplexNumber cos(ComplexNumber xValue){
        double bValue = this.b;
        ComplexNumber firstPart = (new ComplexNumber(Math.cos(a), 0)).mul((new ComplexNumber(bValue, 0).cosh(xValue)), xValue);
        ComplexNumber secondPart = (new ComplexNumber(Math.sin(a), 0)).mul((new ComplexNumber(bValue, 0).sinh(xValue)), xValue);
        return new ComplexNumber(firstPart.a, -1*secondPart.a);
    }
    public ComplexNumber tan(ComplexNumber xValue){
        ComplexNumber numerator = sin(xValue);
        ComplexNumber denominator = cos(xValue);
        return numerator.div(denominator, xValue);
    }
    public ComplexNumber cot(ComplexNumber xValue){
        ComplexNumber numerator = cos(xValue);
        ComplexNumber denominator = sin(xValue);
        return numerator.div(denominator, xValue);
    }
    public ComplexNumber sec(ComplexNumber xValue){
        ComplexNumber numerator = new ComplexNumber(1,0);
        ComplexNumber denominator = cos(xValue);
        return numerator.div(denominator, xValue);
    }
    public ComplexNumber csc(ComplexNumber xValue){
        ComplexNumber numerator = new ComplexNumber(1,0);
        ComplexNumber denominator = sin(xValue);
        return numerator.div(denominator, xValue);
    }

    public Double gammaIntegral(double detail, ComplexNumber input, boolean real){
        double total = 0;
        if (real){
            for (double i = 0.000000000000000001; i < 30; i+=detail) {
                // this converges quickly (we will go to 30 for a good approximation (this gets us to the order of E-6 or E-7 precision)
                // I cant take log of zero, so I will get very close to zero to start with
                total += Math.pow(i,(input.a)) * (1/Math.pow(Math.E, (i))) * Math.cos(input.b * Math.log(i));
            }
        } else {
            // imaginary (only difference is the sin)
            for (double i = 0.000000000000000001; i < 30; i+=detail) {
                total += Math.pow(i,(input.a)) * (1/Math.pow(Math.E, (i))) * Math.sin(input.b * Math.log(i));
            }
        }
        total *= detail;
        return total;
    }
    public ComplexNumber gam(ComplexNumber xValue){
        if (this.isX){
            this.a = xValue.a;
            this.b = xValue.b;
        }
        // the gamma function is the extension of the factorial to all real numbers, what I am doing here is
        // analytically continuing this to a general complex number (not 0 or any negative integer)
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

    @Override
    public String toString() {
        if (isX){
            return "(x.a) + (x.b)i";
        }
        return this.a + " + " + this.b + "i";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ComplexNumber)){
            return false;
        }
        return ((ComplexNumber) obj).a == this.a && ((ComplexNumber) obj).b == this.b || this.isX == ((ComplexNumber) obj).isX;
    }
}
