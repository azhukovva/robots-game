package vut.ija2023.common.log;
import vut.ija2023.enviroment.Position;

public class Log {
    public enum MovementType {
        WAIT,
        MOVE,
        TURN,
        TURN_RIGHT,
        ADD,
    }
    private int stepCount;
    private Position startingPosition;
    private MovementType movementType;
    private int angle;

    public Log(int stepCount, Position startingPosition, MovementType movementType, int angle) {
        this.stepCount = stepCount;
        this.startingPosition = startingPosition;
        this.movementType = movementType;
        this.angle = angle;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public Position getStartingPosition() {
        return startingPosition;
    }

    public void setStartingPosition(Position startingPosition) {
        this.startingPosition = startingPosition;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public int getAngle() {
        return angle;
    }

}
