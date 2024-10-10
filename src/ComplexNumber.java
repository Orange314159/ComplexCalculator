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
        double r1 = Math.log(Math.sqrt(this.a * this.a + this.b * this.b)); // these are the logs of the magnitudes
        double r2 = Math.log(Math.sqrt(c.a * c.a + c.b * c.b));
        double t1 = Math.atan2(this.b, this.a); // these are the theta values
        double t2 = Math.atan2(c.b, c.a);
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
