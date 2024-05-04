package vut.ija2023.common;

import vut.ija2023.enviroment.Position;
import vut.ija2023.common.Log.MovementType;

public class NotifyMessage {
    private Position pos;
    private Robot robot;
    private MovementType message;
    private int angle;
    public NotifyMessage(Position pos, Robot robot, MovementType message, int angle) {
        this.pos = pos;
        this.robot = robot;
        this.message = message;
        this.angle = angle;
    }
    public Position getPos() {
        return pos;
    }
    public MovementType getMessage() {
        return message;
    }
    public Robot getRobot() {
        return robot;
    }

    public int getAngle(){
        return angle;
    }
}
