public class Vector {
    public double x;
    public double y;
    public double z;
    public double w;

    public Vector(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 0;
    }
    public Vector(double x, double y, double z, double w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector multiplyByMatrix(Matrix matrix){
        Vector vector = new Vector();
        vector.x = matrix.mat[0][0] * this.x + matrix.mat[1][0] * this.y + matrix.mat[2][0] * this.z + matrix.mat[3][0] * this.w;
        vector.y = matrix.mat[0][1] * this.x + matrix.mat[1][1] * this.y + matrix.mat[2][1] * this.z + matrix.mat[3][1] * this.w;
        vector.z = matrix.mat[0][2] * this.x + matrix.mat[1][2] * this.y + matrix.mat[2][2] * this.z + matrix.mat[3][2] * this.w;
        vector.w = matrix.mat[0][3] * this.x + matrix.mat[1][3] * this.y + matrix.mat[2][3] * this.z + matrix.mat[3][3] * this.w;
        return vector;
    }
    public Vector add(Vector vector){
        Vector v = new Vector();
        v.x = this.x + vector.x;
        v.y = this.y + vector.y;
        v.z = this.z + vector.z;
        v.w = this.w + vector.w;
        return v;
    }
    public Vector sub(Vector vector){
        Vector v = new Vector();
        v.x = this.x - vector.x;
        v.y = this.y - vector.y;
        v.z = this.z - vector.z;
        v.w = this.w - vector.w;
        return v;
    }
    public Vector mul(double scale){
        Vector vector = new Vector();
        vector.x = this.x * scale;
        vector.y = this.y * scale;
        vector.z = this.z * scale;
        vector.w = this.w * scale;
        return vector;
    }
    public Vector div(double scale){
        Vector vector = new Vector();
        vector.x = this.x / scale;
        vector.y = this.y / scale;
        vector.z = this.z / scale;
        vector.w = this.w / scale;
        return vector;
    }
    public double dotProduct(Vector vector){
        double total = 0;
        total += this.x * vector.x;
        total += this.y * vector.y;
        total += this.z * vector.z;
        total += this.w * vector.w;
        return total;
    }
    public double vectorLength(){
        return Math.sqrt(this.dotProduct(this));
    }
    public void normalize(){
        double r = this.vectorLength();
        this.x /= r;
        this.y /= r;
        this.z /= r;
        this.w /= r;
    }
    public Vector crossProduct(Vector vector){
        Vector v = new Vector();
        v.x = this.y * vector.z - this.z * vector.y;
        v.y = this.z * vector.x - this.x * vector.z;
        v.z = this.x * vector.y - this.y * vector.x;
        return v;
    }

}
