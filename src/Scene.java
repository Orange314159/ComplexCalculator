public class Scene {

    //---------------------------- Camera ----------------------------//
    public Vector camera;
    public Vector lookDirection;
    public double FOV;
    public double near;
    public double far;
    public double yaw;
    public double pitch;
    private Matrix viewMatrix;
    private double relativeCameraYaw; // idk what to call this, but it is the angle from the origin that the camera is at
    private double relativeCameraPitch; // it is only used for move camera in circle
    //---------------------------- Camera ----------------------------//

    //---------------------------- Screen ----------------------------//
    public int WIDTH;
    public int HEIGHT;
    public double aspectRatio;
    //---------------------------- Screen ----------------------------//

    //---------------------------- Points ----------------------------//
    public Vector[]  points;
    public Vector[] pointsToDraw;
    //---------------------------- Points ----------------------------//

    public Scene(){
        this.camera        = new Vector();
        this.lookDirection = new Vector();
        this.FOV           = 90.0;
        this.yaw           = 0;
        this.pitch         = 0;
        this.near          = 0.1;
        this.far           = 1000;
        this.points        = new Vector[0];
        this.pointsToDraw  = new Vector[0];
        this.HEIGHT        = 100;
        this.WIDTH         = 100;
        this.aspectRatio   = (double) HEIGHT /WIDTH;
    }
    public Scene(Vector camera, double FOV, double yaw, double pitch, double near, double far, Vector[] points, int HEIGHT, int WIDTH){
        this.camera        = camera;
        this.lookDirection = new Vector();
        this.FOV           = FOV;
        this.yaw           = yaw;
        this.pitch         = pitch;
        this.near          = near;
        this.far           = far;
        this.points        = points;
        this.pointsToDraw  = new Vector[points.length];
        this.HEIGHT        = HEIGHT;
        this.WIDTH         = WIDTH;
        this.aspectRatio   = (double) HEIGHT /WIDTH;
    }

    public void moveCameraInCircle(double deltaYaw, double deltaPitch){
        this.relativeCameraYaw   += deltaYaw;

        double circleRadiusXZ = 2;
        if (pitch - deltaPitch > (1.3)){
            this.pitch = (1.3);
            this.camera.y = -2.0;
        } else if (pitch - deltaPitch < -(1.3)) {
            this.pitch = -(1.3);
            this.camera.y = 2.0;
        } else {
            this.relativeCameraPitch += deltaPitch;
            this.pitch    -= deltaPitch;
            this.camera.y  = 2.2 * Math.sin(relativeCameraPitch) + 1;
            circleRadiusXZ = 2.2 * Math.cos(relativeCameraPitch);
        }

        this.camera.x = circleRadiusXZ * Math.cos(relativeCameraYaw);
        this.camera.z = circleRadiusXZ * Math.sin(relativeCameraYaw);

        this.yaw   += deltaYaw;
    }

    public void pointAt(){
        Vector upVector        = new Vector(0, 1.0, 0, 1.0);
        Vector targetVector    = new Vector(0, 0, 1.0, 1.0);
        Matrix cameraRotationY = new Matrix(4,4);
        cameraRotationY.makeRotationY(this.yaw);
        Matrix cameraRotationX = new Matrix(4,4);
        cameraRotationX.makeRotationX(this.pitch);
        this.lookDirection =       targetVector.multiplyByMatrix(cameraRotationX);
        this.lookDirection = this.lookDirection.multiplyByMatrix(cameraRotationY);
        targetVector = this.camera.add(this.lookDirection); // does not change camera vector
        Matrix cameraMatrix = new Matrix();
        cameraMatrix.pointAt(this.camera, targetVector, upVector);
        this.viewMatrix = cameraMatrix.quickInverse();
    }

    private Matrix getWorldMatrix() {
        Matrix rotationZMatrix = new Matrix();
        Matrix rotationXMatrix = new Matrix();
        rotationZMatrix.makeRotationZ(0);
        rotationXMatrix.makeRotationX(0);
        // general translation mat, there is no movement to the object
        Matrix translationMatrix = new Matrix();
        translationMatrix.makeTranslation(0.0f, 0.0f, 0.0f);

        Matrix worldMatrix = new Matrix(4,4);
        worldMatrix.makeIdentityMatrix(4);
        worldMatrix = rotationZMatrix.multiplyMatrix(rotationXMatrix);
        worldMatrix = worldMatrix.multiplyMatrix(translationMatrix);
        return worldMatrix;
    }

    public Vector[] drawFrame(){
        // limit pitch to no more than pi/2 and no less than -pi/2
        this.pointAt();
        Matrix worldMatrix = getWorldMatrix();
        Matrix projectionMatrix = new Matrix();
        projectionMatrix.makeProjection(this.FOV, this.aspectRatio, this.near, this.far);
        int counter = 0;
        for(Vector point : this.points){
            Vector transformedVector;
            transformedVector = point.multiplyByMatrix(worldMatrix);
            // scale
            Vector projectedVector = transformedVector.multiplyByMatrix(this.viewMatrix);
            projectedVector = projectedVector.multiplyByMatrix(projectionMatrix);

            projectedVector = projectedVector.div(projectedVector.w);
            // fix xy inversion
//            projectedVector.x *= -1.0;
//            projectedVector.y *= -1.0;
            // fit into normalized space
            Vector offsetVector = new Vector(1.0,1.0,0,1.0);
            projectedVector = projectedVector.add(offsetVector);
            projectedVector.x *= 0.5 * (double) this.WIDTH;
            projectedVector.y *= 0.5 * (double) this.HEIGHT;
            pointsToDraw[counter] = projectedVector;
            counter++;
        }
        return pointsToDraw;
    }

    public Vector[] drawAxis(){
        Vector[] pointsOfAxis = new Vector[6];
        // x axis
        pointsOfAxis[0] = new Vector(0,0,0,1.0);
        pointsOfAxis[1] = new Vector(-1.0,0,0,1.0);
        // y axis
        pointsOfAxis[2] = new Vector(0,0,0,1.0);
        pointsOfAxis[3] = new Vector(0,1.0,0,1.0);
        // z axis
        pointsOfAxis[4] = new Vector(0,0,0,1.0);
        pointsOfAxis[5] = new Vector(0,0,1.0,1.0);

        Vector[] returnPoints = new Vector[6];
        this.pointAt();
        Matrix worldMatrix = getWorldMatrix();
        Matrix projectionMatrix = new Matrix();
        projectionMatrix.makeProjection(this.FOV, this.aspectRatio, this.near, this.far);

        int counter = 0;
        for(Vector point : pointsOfAxis){
            Vector transformedVector;
            transformedVector = point.multiplyByMatrix(worldMatrix);
            // scale
            Vector projectedVector = transformedVector.multiplyByMatrix(this.viewMatrix);
            projectedVector = projectedVector.multiplyByMatrix(projectionMatrix);

            projectedVector = projectedVector.div(projectedVector.w);
            // fix xy inversion
//            projectedVector.x *= -1.0;
//            projectedVector.y *= -1.0;
            // fit into normalized space
            Vector offsetVector = new Vector(1.0,1.0,0,1.0);
            projectedVector = projectedVector.add(offsetVector);
            projectedVector.x *= 0.5 * (double) this.WIDTH;
            projectedVector.y *= 0.5 * (double) this.HEIGHT;
            returnPoints[counter] = projectedVector;
            counter++;
        }


        return returnPoints;
    }


}
