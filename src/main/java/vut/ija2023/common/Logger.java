package vut.ija2023.common;
import vut.ija2023.enviroment.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import vut.ija2023.common.Log.MovementType;
import java.util.stream.Collectors;

public class Logger {

    // Map to store messages for each robot
    private Map<Robot, List<Log>> robotMessages = new HashMap<>();
    // Map to track the previous movement type for each robot
    private Map<Robot, MovementType> previousMovementType = new HashMap<>();
    private int stepCount = 0;

    public void log(List<NotifyMessage> messages) {
        for (NotifyMessage msg : messages) {
            handleMessage(msg);
        }
        stepCount++;
    }

    public void handleMessage(NotifyMessage notifyMessage) {
        Position pos = notifyMessage.getPos();
        Robot robot = notifyMessage.getRobot();
        MovementType message = notifyMessage.getMessage();
        int angle = notifyMessage.getAngle();

        // Check if the robot already has messages stored
        if (!robotMessages.containsKey(robot)) {
            List<Log> messages = new ArrayList<>(); // new list for the robot's messages
            messages.add(new Log(stepCount, pos, message, angle)); // Assuming null for the initial movement type
            robotMessages.put(robot, messages); // to messages array
            previousMovementType.put(robot, null);
        }
        // OLD ROBOT
        else {
            List<Log> messages = robotMessages.get(robot); // the list of messages for the robot

            MovementType prevType = previousMovementType.get(robot);
            if (message != prevType) {
                messages.add(new Log(stepCount, pos, message, angle ));
                previousMovementType.put(robot, message);
            }
        }
    }

    public void printLogs() {
        robotMessages.forEach((robot, logs) -> {
            System.out.println("Logs for Robot: " + robot);
            logs.forEach(log -> System.out.println("Step: " + log.getStepCount() + ", Position: " + log.getStartingPosition() + ", Angle: " + log.getAngle() + ", Movement: " + log.getMovementType()));
        });
    }
}

