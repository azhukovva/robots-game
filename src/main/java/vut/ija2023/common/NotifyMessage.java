package vut.ija2023.common;

import vut.ija2023.enviroment.Position;
import vut.ija2023.common.Log.MovementType;

public class NotifyMessage {
    private Position pos;
    private Robot robot;
    private MovementType message;
    public NotifyMessage(Position pos, Robot robot, MovementType message) {
        this.pos = pos;
        this.robot = robot;
        this.message = message;
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
}
