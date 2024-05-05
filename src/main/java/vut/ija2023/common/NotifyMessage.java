package vut.ija2023.common;

import vut.ija2023.enviroment.Position;
import vut.ija2023.common.log.Log.MovementType;

/**
 * Represents a notification message in the system.
 * A notification message includes a position, a robot, a movement type, and an angle.
 */
public class NotifyMessage {
    private Position pos;
    private Robot robot;
    private MovementType message;
    private int angle;

    /**
     * Constructs a new NotifyMessage with the specified position, robot, movement type, and angle.
     *
     * @param pos the position of the notification
     * @param robot the robot involved in the notification
     * @param message the movement type of the notification
     * @param angle the angle of the notification
     */
    public NotifyMessage(Position pos, Robot robot, MovementType message, int angle) {
        this.pos = pos;
        this.robot = robot;
        this.message = message;
        this.angle = angle;
    }

    /**
     * Returns the position of the notification.
     *
     * @return the position of the notification
     */
    public Position getPos() {
        return pos;
    }

    /**
     * Returns the movement type of the notification.
     *
     * @return the movement type of the notification
     */
    public MovementType getMessage() {
        return message;
    }

    /**
     * Returns the robot involved in the notification.
     *
     * @return the robot involved in the notification
     */
    public Robot getRobot() {
        return robot;
    }

    /**
     * Returns the angle of the notification.
     *
     * @return the angle of the notification
     */
    public int getAngle(){
        return angle;
    }
}
