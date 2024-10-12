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
        mat = new double[x][x];
        for (int i = 0; i < x; i++) {
            mat[i][i] = 1;
        }
    }
    public void makeRotationX(double angle){
        // angle should be in rad
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
        double fovRad = 1.0f / Math.tan(fovDeg * 0.5f / 180.0f * 3.14159f);
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


}
