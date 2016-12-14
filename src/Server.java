import com.aldebaran.qi.Application;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import coppelia.remoteApi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // Server
        ServerSocket serverSocket = new ServerSocket(20000);
        System.out.println("Server started at port 20000");
        // Application
        String robotUrl = "tcp://127.0.0.1:9559";
        Application application = new Application(args, robotUrl);
        ALMotion alMotion = null;
        ALRobotPosture alRobotPosture = null;
        // Main loop
        while (true) {
            System.out.println("Waiting for connection...");
            // Obtain a socket connection
            Socket socket = serverSocket.accept();
            System.out.println("Connected!");
            // Data Streams
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            remoteApi vRep = null;
            int clientID = -1;
            Communication communication = null;

            while (true) {
                // Wait the reception of the command
                try {
                    int command = dataInputStream.readInt();
                    // Everything has worked fine, so make a switch based on the command
                    switch (command) {

                        //connect
                        case 0:
                            // Application start
                            application.start();
                            System.out.println("Connected to Choregraphe server");
                            // V-Rep connection request
                            vRep = new remoteApi();
                            vRep.simxFinish(clientID); // just in case, close all opened connections
                            clientID = vRep.simxStart("127.0.0.1", 19999, true, true, 5000, 5);
                            if (clientID != -1) {
                                System.out.println("Connected to remote V-REP server");
                                // get objects handles and streaming operation request
                                communication = new Communication(vRep, clientID, application);
                                try {
                                    // Initial configuration
                                    alMotion = new ALMotion(application.session());
                                    alMotion.setSmartStiffnessEnabled(false);
                                    alMotion.setFallManagerEnabled(true);
                                    alMotion.setMoveArmsEnabled(true, true);
                                    // go to posture "StandZero" (same as V-REP initial)
                                    alRobotPosture = new ALRobotPosture(application.session());
                                    alRobotPosture.goToPosture("StandZero", 0.5f);
                                    // start setJointAnglesVRepThread
                                    communication.setJointsAnglesVRepThreadStart();
                                    dataOutputStream.writeBoolean(true);
                                    break;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            dataOutputStream.writeBoolean(false);
                            break;

                        // get joint angles and vision sensor image
                        case 1:
                            if (communication != null) {
                                byte[] bytes = communication.getJointAnglesVisionSensorImage();
                                dataOutputStream.write(bytes);
                            }
                            break;

                        // walk and turn
                        case 2:
                            float x = dataInputStream.readFloat() / 2;
                            float y = dataInputStream.readFloat() / 2;
                            float theta = dataInputStream.readFloat();
                            // Do nothing
                            try {
                                if (alMotion != null && !alMotion.moveIsActive()) {
                                    alMotion.async().moveTo(x, y, theta);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            break;

                        // go to posture
                        case 3:
                            try {
                                int postureID = dataInputStream.readInt();
                                if (alMotion != null && !alMotion.moveIsActive()) {
                                    alMotion.stopMove();
                                }
                                if (alRobotPosture != null) {
                                    switch (postureID) {
                                        case 0:
                                            alRobotPosture.async().goToPosture("Crouch", 0.2f);
                                            break;

                                        case 1:
                                            alRobotPosture.async().goToPosture("LyingBack", 0.2f);
                                            break;

                                        case 2:
                                            alRobotPosture.async().goToPosture("LyingBelly", 0.2f);
                                            break;

                                        case 3:
                                            alRobotPosture.async().goToPosture("Sit", 0.2f);
                                            break;

                                        case 4:
                                            alRobotPosture.async().goToPosture("SitRelax", 0.2f);
                                            break;

                                        case 5:
                                            alRobotPosture.async().goToPosture("Stand", 0.2f);
                                            break;

                                        case 6:
                                            alRobotPosture.async().goToPosture("StandInit", 0.2f);
                                            break;

                                        case 7:
                                            alRobotPosture.async().goToPosture("StandZero", 0.2f);
                                            break;
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                break;
                            }
                            break;

                        // changeJointsAngles
                        case 4:
                            int jointID = dataInputStream.readInt();
                            float yaw = dataInputStream.readFloat();
                            float pitch = dataInputStream.readFloat();
                            float roll = dataInputStream.readFloat();
                            if (communication != null) {
                                communication.changeJointsAngles(jointID, yaw, pitch, roll);
                            }
                            break;
                    }
                } catch (IOException e) {
                    if (communication != null) {
                        communication.stopThread();
                    }
                    // Disconnect from V-Rep
                    if (vRep != null) {
                        vRep.simxFinish(clientID);
                    }
                    break;
                }
            }
        }
    }
}
