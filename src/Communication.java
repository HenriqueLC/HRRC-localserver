import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Tuple2;
import com.aldebaran.qi.Tuple3;
import com.aldebaran.qi.helper.proxies.ALMotion;
import coppelia.*;

import java.nio.ByteBuffer;
import java.util.List;

import static coppelia.remoteApi.*;

class Communication {

    private remoteApi vRep;
    private int clientID;
    private Application application;
    private IntW body[], visionSensorHandle;
    private FloatW jointsAngles[];
    private FloatWA orientation, leftPosition, rightPosition;
    private IntWA resolution;
    private CharWA visionSensor;
    private class VRepCommunication {

        synchronized void setJointAnglesVRep() {
            try {
                ALMotion alMotion = new ALMotion(application.session());
                List<Float> commandAngles = alMotion.getAngles("Body", false);
                // Head
                vRep.simxSetJointTargetPosition(clientID, body[0].getValue(), commandAngles.get(0), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[1].getValue(), commandAngles.get(1), simx_opmode_streaming);
                //Left Leg
                vRep.simxSetJointTargetPosition(clientID, body[2].getValue(), commandAngles.get(8), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[3].getValue(), commandAngles.get(9), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[4].getValue(), commandAngles.get(10), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[5].getValue(), commandAngles.get(11), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[6].getValue(), commandAngles.get(12), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[7].getValue(), commandAngles.get(13), simx_opmode_streaming);
                // Right Leg
                vRep.simxSetJointTargetPosition(clientID, body[8].getValue(), commandAngles.get(14), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[9].getValue(), commandAngles.get(15), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[10].getValue(), commandAngles.get(16), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[11].getValue(), commandAngles.get(17), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[12].getValue(), commandAngles.get(18), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[13].getValue(), commandAngles.get(19), simx_opmode_streaming);
                // Left Arm
                vRep.simxSetJointTargetPosition(clientID, body[14].getValue(), commandAngles.get(2), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[15].getValue(), commandAngles.get(3), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[16].getValue(), commandAngles.get(4), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[17].getValue(), commandAngles.get(5), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[18].getValue(), commandAngles.get(6), simx_opmode_streaming);
                // Right Arm
                vRep.simxSetJointTargetPosition(clientID, body[19].getValue(), commandAngles.get(20), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[20].getValue(), commandAngles.get(21), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[21].getValue(), commandAngles.get(22), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[22].getValue(), commandAngles.get(23), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[23].getValue(), commandAngles.get(24), simx_opmode_streaming);
                // Left Fingers
                vRep.simxSetJointTargetPosition(clientID, body[24].getValue(), 1.0f - commandAngles.get(7), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[25].getValue(), 1.0f - commandAngles.get(7), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[26].getValue(), 1.0f - commandAngles.get(7), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[27].getValue(), 1.0f - commandAngles.get(7), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[28].getValue(), 1.0f - commandAngles.get(7), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[29].getValue(), 1.0f - commandAngles.get(7), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[30].getValue(), 1.0f - commandAngles.get(7), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[31].getValue(), 1.0f - commandAngles.get(7), simx_opmode_streaming);
                // Right Fingers
                vRep.simxSetJointTargetPosition(clientID, body[32].getValue(), 1.0f - commandAngles.get(25), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[33].getValue(), 1.0f - commandAngles.get(25), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[34].getValue(), 1.0f - commandAngles.get(25), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[35].getValue(), 1.0f - commandAngles.get(25), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[36].getValue(), 1.0f - commandAngles.get(25), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[37].getValue(), 1.0f - commandAngles.get(25), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[38].getValue(), 1.0f - commandAngles.get(25), simx_opmode_streaming);
                vRep.simxSetJointTargetPosition(clientID, body[39].getValue(), 1.0f - commandAngles.get(25), simx_opmode_streaming);
            } catch (Exception callError) {
                callError.printStackTrace();
            }
        }

        synchronized byte[] getJointAnglesVisionSensorImage() {
            byte[] message = new byte[112 + 38400]; // 26 floats + 19200 chars
            // get angles, convert than and compose the message
            for (int i = 0; i < jointsAngles.length; i++) {
                vRep.simxGetJointPosition(clientID, body[i].getValue(), jointsAngles[i], simx_opmode_buffer);
                jointsAngles[i].toDegrees();
                byte[] jointsAnglesBytes = ByteBuffer.allocate(4).putFloat(jointsAngles[i].getValue()).array();
                System.arraycopy(jointsAnglesBytes, 0, message, 4 * i, 4);
            }
            // Robot orientation
            vRep.simxGetObjectOrientation(clientID, body[40].getValue(), -1, orientation, simx_opmode_buffer);
            float x = orientation.getArray()[0] * (float) Math.cos(orientation.getArray()[2]) + orientation.getArray()[1] * (float) Math.sin(orientation.getArray()[2]);
            byte[] xBytes = ByteBuffer.allocate(4).putFloat((float) Math.toDegrees(x)).array();
            System.arraycopy(xBytes, 0, message, 96, 4);
            float y = - orientation.getArray()[0] * (float) Math.sin(orientation.getArray()[2]) + orientation.getArray()[1] * (float) Math.cos(orientation.getArray()[2]);
            byte[] yBytes = ByteBuffer.allocate(4).putFloat((float) Math.toDegrees(y)).array();
            System.arraycopy(yBytes, 0, message, 100, 4);
            // robot position
            vRep.simxGetObjectPosition(clientID, body[2].getValue(), -1, leftPosition, simx_opmode_buffer);
            float zLeft = leftPosition.getArray()[2];
            byte[] zLeftBytes = ByteBuffer.allocate(4).putFloat((float) Math.toDegrees(zLeft)).array();
            System.arraycopy(zLeftBytes, 0, message, 104, 4);
            vRep.simxGetObjectPosition(clientID, body[8].getValue(), -1, rightPosition, simx_opmode_buffer);
            float zRight = rightPosition.getArray()[2];
            byte[] zRightBytes = ByteBuffer.allocate(4).putFloat((float) Math.toDegrees(zRight)).array();
            System.arraycopy(zRightBytes, 0, message, 108, 4);
            // get vision data
            vRep.simxGetVisionSensorImage(clientID, visionSensorHandle.getValue(), resolution, visionSensor, sim_object_camera_type, simx_opmode_buffer);
            // adjust resolution
            char[] pixels = visionSensor.getArray();
            int count = 0;
            for (int i = 0; i < 480; i += 4) {
                for (int j = 0; j < 640; j += 4) {
                    byte[] pixel = ByteBuffer.allocate(2).putChar(pixels[640 * i + j]).array();
                    System.arraycopy(pixel, 0, message, 112 + 2 * count, 2);
                    count++;
                }
            }
            return message;
        }
    }
    private VRepCommunication vRepCommunication;
    private volatile boolean stopThread;

    Communication(remoteApi vRep, int clientID, Application application) {
        this.vRep = vRep;
        this.clientID = clientID;
        this.application = application;
        // Joints Handle
        body = new IntW[]{
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0),
                new IntW(0)
        };
        visionSensorHandle = new IntW(0);
        // Get Joints Handle
        vRep.simxGetObjectHandle(clientID, "HeadYaw#", body[0], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "HeadPitch#", body[1], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "LHipYawPitch3#", body[2], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "LHipRoll3#", body[3], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "LHipPitch3#", body[4], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "LKneePitch3#", body[5], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "LAnklePitch3#", body[6], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "LAnkleRoll3#", body[7], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "RHipYawPitch3#", body[8], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "RHipRoll3#", body[9], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "RHipPitch3#", body[10], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "RKneePitch3#", body[11], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "RAnklePitch3#", body[12], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "RAnkleRoll3#", body[13], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "LShoulderPitch3#", body[14], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "LShoulderRoll3#", body[15], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "LElbowYaw3#", body[16], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "LElbowRoll3#", body[17], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "LWristYaw3#", body[18], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "RShoulderPitch3#", body[19], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "RShoulderRoll3#", body[20], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "RElbowYaw3#", body[21], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "RElbowRoll3#", body[22], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "RWristYaw3#", body[23], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "NAO_LThumbBase#", body[24], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "Revolute_joint8#", body[25], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "NAO_LLFingerBase#", body[26], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "Revolute_joint12#", body[27], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "Revolute_joint14#", body[28], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "NAO_LRFinger_Base#", body[29], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "Revolute_joint11#", body[30], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "Revolute_joint13#", body[31], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "NAO_RThumbBase#", body[32], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "Revolute_joint0#", body[33], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "NAO_RLFingerBase#", body[34], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "Revolute_joint5#", body[35], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "Revolute_joint6#", body[36], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "NAO_RRFinger_Base#", body[37], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "Revolute_joint2#", body[38], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "Revolute_joint3#", body[39], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "NAO#", body[40], simx_opmode_oneshot_wait);
        vRep.simxGetObjectHandle(clientID, "NAO_vision1", visionSensorHandle, simx_opmode_oneshot_wait);
        // Angles of the joints
        jointsAngles = new FloatW[]{
                new FloatW(0),
                new FloatW(0),
                new FloatW(0),
                new FloatW(0),
                new FloatW(0),
                new FloatW(0),
                new FloatW(0),
                new FloatW(0),
                new FloatW(0),
                new FloatW(0),
                new FloatW(0),
                new FloatW(0),
                new FloatW(0),
                new FloatW(0),
                new FloatW(0),
                new FloatW(0),
                new FloatW(0),
                new FloatW(0),
                new FloatW(0),
                new FloatW(0),
                new FloatW(0),
                new FloatW(0),
                new FloatW(0),
                new FloatW(0)
        };
        orientation = new FloatWA(3);
        leftPosition = new FloatWA(3);
        rightPosition = new FloatWA(3);
        // vision sensor
        resolution = new IntWA(2);
        visionSensor = new CharWA(640 * 480);
        // VRep communication
        vRepCommunication = new VRepCommunication();
        // set streaming op mode
        for (int i = 0; i < jointsAngles.length; i++) {
            vRep.simxGetJointPosition(clientID, body[i].getValue(), jointsAngles[i], simx_opmode_streaming);
        }
        vRep.simxGetObjectOrientation(clientID, body[40].getValue(), -1, orientation, simx_opmode_streaming);
        vRep.simxGetObjectPosition(clientID, body[2].getValue(), -1, leftPosition, simx_opmode_streaming);
        vRep.simxGetObjectPosition(clientID, body[8].getValue(), -1, rightPosition, simx_opmode_streaming);
        vRep.simxGetVisionSensorImage(clientID, visionSensorHandle.getValue(), resolution, visionSensor, sim_object_camera_type, simx_opmode_streaming);
    }

    void setJointsAnglesVRepThreadStart() {
        stopThread = false;
        new Thread(() -> {
            while (!stopThread) {
                vRepCommunication.setJointAnglesVRep();
            }
        }).start();
    }

    byte[] getJointAnglesVisionSensorImage() {
        return vRepCommunication.getJointAnglesVisionSensorImage();
    }

    void changeJointsAngles(int jointID, float yaw, float pitch, float roll) {
        try {
            ALMotion alMotion = new ALMotion(application.session());
            switch (jointID) {
                case 0:
                    try {
                        alMotion.changeAngles(new Tuple2<>("HeadYaw", "HeadPitch"), new Tuple2<>(Math.toRadians(-yaw), Math.toRadians(-pitch)), 1.0f);
                    } catch (CallError | InterruptedException callError) {
                        callError.printStackTrace();
                    }
                    break;

                case 1:
                    try {
                        alMotion.changeAngles(new Tuple3<>("LHipYawPitch", "LHipRoll", "LHipPitch"), new Tuple3<>(Math.toRadians(yaw), Math.toRadians(roll), Math.toRadians(-pitch)), 1.0f);
                    } catch (CallError | InterruptedException callError) {
                        callError.printStackTrace();
                    }
                    break;

                case 2:
                    try {
                        alMotion.changeAngles("LKneePitch", Math.toRadians(-pitch), 1.0f);
                    } catch (CallError | InterruptedException callError) {
                        callError.printStackTrace();
                    }
                    break;

                case 3:
                    try {
                        alMotion.changeAngles(new Tuple2<>("LAnklePitch", "LAnkleRoll"), new Tuple2<>(Math.toRadians(-pitch), Math.toRadians(roll)), 1.0f);
                    } catch (CallError | InterruptedException callError) {
                        callError.printStackTrace();
                    }
                    break;

                case 4:
                    try {
                        alMotion.changeAngles(new Tuple3<>("RHipYawPitch", "RHipRoll", "RHipPitch"), new Tuple3<>(Math.toRadians(-yaw), Math.toRadians(-roll), Math.toRadians(-pitch)), 1.0f);
                    } catch (CallError | InterruptedException callError) {
                        callError.printStackTrace();
                    }
                    break;

                case 5:
                    try {
                        alMotion.changeAngles("RKneePitch", Math.toRadians(-pitch), 1.0f);
                    } catch (CallError | InterruptedException callError) {
                        callError.printStackTrace();
                    }
                    break;

                case 6:
                    try {
                        alMotion.changeAngles(new Tuple2<>("RAnklePitch", "RAnkleRoll"), new Tuple2<>(Math.toRadians(-pitch), Math.toRadians(-roll)), 1.0f);
                    } catch (CallError | InterruptedException callError) {
                        callError.printStackTrace();
                    }
                    break;

                case 7:
                    try {
                        alMotion.changeAngles(new Tuple2<>("LShoulderPitch", "LShoulderRoll"), new Tuple2<>(Math.toRadians(-pitch), Math.toRadians(-yaw)), 1.0f);
                    } catch (CallError | InterruptedException callError) {
                        callError.printStackTrace();
                    }
                    break;

                case 8:
                    try {
                        alMotion.changeAngles(new Tuple2<>("LElbowYaw", "LElbowRoll"), new Tuple2<>(Math.toRadians(-roll), Math.toRadians(-yaw)), 1.0f);
                    } catch (CallError | InterruptedException callError) {
                        callError.printStackTrace();
                    }
                    break;

                case 9:
                    try {
                        alMotion.changeAngles("LWristYaw", Math.toRadians(-roll), 1.0f);
                    } catch (CallError | InterruptedException callError) {
                        callError.printStackTrace();
                    }
                    break;

                case 10:
                    try {
                        alMotion.changeAngles(new Tuple2<>("RShoulderPitch", "RShoulderRoll"), new Tuple2<>(Math.toRadians(-pitch), Math.toRadians(-yaw)), 1.0f);
                    } catch (CallError | InterruptedException callError) {
                        callError.printStackTrace();
                    }
                    break;

                case 11:
                    try {
                        alMotion.changeAngles(new Tuple2<>("RElbowYaw", "RElbowRoll"), new Tuple2<>(Math.toRadians(roll), Math.toRadians(-yaw)), 1.0f);
                    } catch (CallError | InterruptedException callError) {
                        callError.printStackTrace();
                    }
                    break;

                case 12:
                    try {
                        alMotion.changeAngles("RWristYaw", Math.toRadians(roll), 1.0f);
                    } catch (CallError | InterruptedException callError) {
                        callError.printStackTrace();
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void stopThread() {
        stopThread = true;
    }
}