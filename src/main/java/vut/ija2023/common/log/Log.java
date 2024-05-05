package vut.ija2023.common.log;
import vut.ija2023.enviroment.Position;

/**
 * Represents a log entry in the system.
 * A log entry includes a step count, a starting position, a movement type, and an angle.
 */
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

    /**
     * Constructs a new Log with the specified step count, starting position, movement type, and angle.
     *
     * @param stepCount the step count of the log
     * @param startingPosition the starting position of the log
     * @param movementType the movement type of the log
     * @param angle the angle of the log
     */
    public Log(int stepCount, Position startingPosition, MovementType movementType, int angle) {
        this.stepCount = stepCount;
        this.startingPosition = startingPosition;
        this.movementType = movementType;
        this.angle = angle;
    }

    /**
     * Returns the step count of the log.
     *
     * @return the step count of the log
     */
    public int getStepCount() {
        return stepCount;
    }

    /**
     * Returns the starting position of the log.
     *
     * @return the starting position of the log
     */
    public Position getStartingPosition() {
        return startingPosition;
    }

    /**
     * Returns the starting position of the log.
     *
     * @return the starting position of the log
     */
    public MovementType getMovementType() {
        return movementType;
    }

    /**
     * Returns the angle of the log.
     *
     * @return the angle of the log
     */
    public int getAngle() {
        return angle;
    }

}
