public class Scene {

    //---------------------------- Camera ----------------------------//
    public Vector camera;
    public Vector lookDirection;
    public double FOV;
    public double near;
    public double far;
    private double yaw;
    private double pitch;
    private Matrix viewMatrix;
    //---------------------------- Camera ----------------------------//

    //---------------------------- Screen ----------------------------//
    private int WIDTH;
    private int HEIGHT;
    private double aspectRatio;
    //---------------------------- Screen ----------------------------//

    //---------------------------- Points ----------------------------//
    public Vector[] points;
    //---------------------------- Points ----------------------------//

    public Scene(){
        this.camera        = new Vector();
        this.lookDirection = new Vector();
        this.FOV           = 90;
        this.yaw           = 0;
        this.pitch         = 0;
        this.near          = 0.1;
        this.far           = 1000;
        this.points        = new Vector[0];
        this.HEIGHT        = 100;
        this.WIDTH         = 100;
        this.aspectRatio   = (double) HEIGHT /WIDTH;
    }
    public Scene(Vector camera, Vector lookDirection, double FOV, double yaw, double pitch, double near, double far, Vector[] points, int HEIGHT, int WIDTH){
        this.camera        = camera;
        this.lookDirection = lookDirection;
        this.FOV           = FOV;
        this.yaw           = yaw;
        this.pitch         = pitch;
        this.near          = near;
        this.far           = far;
        this.points        = points;
        this.HEIGHT        = HEIGHT;
        this.WIDTH         = WIDTH;
        this.aspectRatio   = (double) HEIGHT /WIDTH;
    }

    public void pointAt(){
        Vector upVector        = new Vector(0, 1, 0, 1);
        Vector targetVector    = new Vector(0, 0, 1, 1);
        Matrix cameraRotationY = new Matrix(4,4);
        cameraRotationY.makeRotationY(this.yaw);
        Matrix cameraRotationZ = new Matrix(4,4);
        cameraRotationZ.makeRotationZ(this.pitch);
        this.lookDirection = targetVector.multiplyByMatrix(cameraRotationZ);
        this.lookDirection = this.lookDirection.multiplyByMatrix(cameraRotationY);
        targetVector = this.camera.add(this.lookDirection); // does not change camera vector
        Matrix cameraMatrix = new Matrix();
        cameraMatrix.pointAt(this.camera, targetVector, upVector);
        this.viewMatrix = cameraMatrix.quickInverse();
    }

    public void drawFrame(){

    }
}
