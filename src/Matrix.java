// this class attempts to be a kinda general matrix class but is almost exclusively used for 4x4 matrices
public class Matrix {
    double[][] mat;

    public Matrix(){
        mat = new double[0][0];
    }
    public Matrix(int x, int y){
        mat = new double[y][x];
    }
    public Matrix(double[][] input){
        mat = input;
    }

    public void makeIdentityMatrix(int x){
        // this is a matrix that is all zeros but has ones going through the diagonal
        mat = new double[x][x];
        for (int i = 0; i < x; i++) {
            mat[i][i] = 1;
        }
    }
    public void makeRotationX(double angle){
        // angle should be in rad
        // you can find any of these matrices on wikipedia
        mat = new double[4][4];
        mat[0][0] = 1;
        mat[1][1] = Math.cos(angle);
        mat[1][2] = Math.sin(angle);
        mat[2][1] = -Math.sin(angle);
        mat[2][2] = Math.cos(angle);
        mat[3][3] = 1;
    }
    public void makeRotationY(double angle){
        mat = new double[4][4];
        mat[0][0] = Math.cos(angle);
        mat[0][2] = Math.sin(angle);
        mat[2][0] = -Math.sin(angle);
        mat[1][1] = 1.0f;
        mat[2][2] = Math.cos(angle);
        mat[3][3] = 1.0f;
    }
    public void makeRotationZ(double angle){
        mat = new double[4][4];
        mat[0][0] = Math.cos(angle);
        mat[0][1] = Math.sin(angle);
        mat[1][0] = -Math.sin(angle);
        mat[1][1] = Math.cos(angle);
        mat[2][2] = 1.0f;
        mat[3][3] = 1.0f;
    }
    public void makeTranslation(double x, double y, double z){
        mat = new double[4][4];
        mat[0][0] = 1.0f;
        mat[1][1] = 1.0f;
        mat[2][2] = 1.0f;
        mat[3][3] = 1.0f;
        mat[3][0] = x;
        mat[3][1] = y;
        mat[3][2] = z;
    }
    public void makeProjection(double fovDeg, double aspectRatio, double fNear, double fFar){
        // this matrix is hard to explain, but the general idea is that you are able to multiply a 3d point by this matrix, and it will return a point that compresses it into two dimensions
        double fovRad = 1.0f / Math.tan(fovDeg * 0.5f / 180.0f * Math.PI);
        mat = new double[4][4];
        mat[0][0] = aspectRatio * fovRad;
        mat[1][1] = fovRad;
        mat[2][2] = fFar / (fFar - fNear);
        mat[3][2] = (-fFar * fNear) / (fFar - fNear);
        mat[2][3] = 1.0f;
        mat[3][3] = 0.0f;
    }
    public Matrix multiplyMatrix(Matrix matrix){
        Matrix m = new Matrix(4,4);
        for (int c = 0; c < 4; c++) {
            for (int r = 0; r < 4; r++) {
                m.mat[r][c] = this.mat[r][0]*matrix.mat[0][c] + this.mat[r][1] * matrix.mat[1][c] + this.mat[r][2] * matrix.mat[2][c] + this.mat[r][3] * matrix.mat[3][c];
            }
        }
        return m;
    }
    public Matrix quickInverse(){
        // this inverse is not correct for any matrix but is a quick method that I can use for the matrices that I am dealing with here
        Matrix matrix = new Matrix(4,4);
        matrix.mat[0][0] = this.mat[0][0];    matrix.mat[0][1] = this.mat[1][0];  matrix.mat[0][2] = this.mat[2][0];  matrix.mat[0][3] = 0.0f;
        matrix.mat[1][0] = this.mat[0][1];    matrix.mat[1][1] = this.mat[1][1];  matrix.mat[1][2] = this.mat[2][1];  matrix.mat[1][3] = 0.0f;
        matrix.mat[2][0] = this.mat[0][2];    matrix.mat[2][1] = this.mat[1][2];  matrix.mat[2][2] = this.mat[2][2];  matrix.mat[2][3] = 0.0f;
        matrix.mat[3][0] = -(this.mat[3][0] * matrix.mat[0][0] + this.mat[3][1] * matrix.mat[1][0] + this.mat[3][2] * matrix.mat[2][0]);
        matrix.mat[3][1] = -(this.mat[3][0] * matrix.mat[0][1] + this.mat[3][1] * matrix.mat[1][1] + this.mat[3][2] * matrix.mat[2][1]);
        matrix.mat[3][2] = -(this.mat[3][0] * matrix.mat[0][2] + this.mat[3][1] * matrix.mat[1][2] + this.mat[3][2] * matrix.mat[2][2]);
        matrix.mat[3][3] = 1.0f;
        return matrix;
    }
    public void add(Matrix matrix){
        this.mat[0][0] = this.mat[0][0] + matrix.mat[0][0];
        this.mat[0][1] = this.mat[0][1] + matrix.mat[0][1];
        this.mat[0][2] = this.mat[0][2] + matrix.mat[0][2];
        this.mat[0][3] = this.mat[0][3] + matrix.mat[0][3];

        this.mat[1][0] = this.mat[1][0] + matrix.mat[1][0];
        this.mat[1][1] = this.mat[1][1] + matrix.mat[1][1];
        this.mat[1][2] = this.mat[1][2] + matrix.mat[1][2];
        this.mat[1][3] = this.mat[1][3] + matrix.mat[1][3];

        this.mat[2][0] = this.mat[2][0] + matrix.mat[2][0];
        this.mat[2][1] = this.mat[2][1] + matrix.mat[2][1];
        this.mat[2][2] = this.mat[2][2] + matrix.mat[2][2];
        this.mat[2][3] = this.mat[2][3] + matrix.mat[2][3];

        this.mat[3][0] = this.mat[3][0] + matrix.mat[3][0];
        this.mat[3][1] = this.mat[3][1] + matrix.mat[3][1];
        this.mat[3][2] = this.mat[3][2] + matrix.mat[3][2];
        this.mat[3][3] = this.mat[3][3] + matrix.mat[3][3];
    }
    public void pointAt(Vector position, Vector target, Vector up){
        // I understood this at one point, but I don't anymore (10/18/24)
        // Vector Stuff
        Vector newForward;
        newForward = target.sub(position);
        newForward.normalize();

        Vector a;
        a = newForward.mul(up.dotProduct(newForward));
        Vector newUp = up.sub(a);
        newUp.normalize();

        Vector newRight;
        newRight = newUp.crossProduct(newForward);

        // Construct Matrix
        Matrix matrix = new Matrix(4,4);
        matrix.mat[0][0] = newRight.x;	    matrix.mat[0][1] = newRight.y;	    matrix.mat[0][2] = newRight.z;	    matrix.mat[0][3] = 0.0f;
        matrix.mat[1][0] = newUp.x;		    matrix.mat[1][1] = newUp.y;		    matrix.mat[1][2] = newUp.z;		    matrix.mat[1][3] = 0.0f;
        matrix.mat[2][0] = newForward.x;	matrix.mat[2][1] = newForward.y;	matrix.mat[2][2] = newForward.z;	matrix.mat[2][3] = 0.0f;
        matrix.mat[3][0] = position.x;		matrix.mat[3][1] = position.y;		matrix.mat[3][2] = position.z;		matrix.mat[3][3] = 1.0f;
        this.mat = matrix.mat;
    }
}
