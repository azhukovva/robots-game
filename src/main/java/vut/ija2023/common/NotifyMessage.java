package vut.ija2023.common;

import vut.ija2023.enviroment.Position;

public class NotifyMessage {
    private Position pos;
    private Robot robot;
    private String message;
    public NotifyMessage(Position pos, Robot robot, String message) {
        this.pos = pos;
        this.robot = robot;
        this.message = message;
    }
    public Position getPos() {
        return pos;
    }
    public String getMessage() {
        return message;
    }
    public Robot getRobot() {
        return robot;
    }
}
