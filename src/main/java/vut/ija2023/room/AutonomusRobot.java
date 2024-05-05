package vut.ija2023.room;

import javafx.scene.image.ImageView;
import vut.ija2023.BotsController;
import vut.ija2023.common.AbstractRobot;
import vut.ija2023.common.Environment;
import vut.ija2023.common.log.Log.MovementType;
import vut.ija2023.common.Robot;
import vut.ija2023.enviroment.Position;


/**
 * Represents an autonomous robot that can move within an environment.
 */
public class AutonomusRobot extends AbstractRobot implements Robot {
    private Position position;
    private Environment environment;
    private int angle = 0;

    /**
     * Constructs an autonomous robot with the specified environment, position, controller, and view.
     *
     * @param env        the environment in which the robot operates
     * @param pos        the initial position of the robot
     * @param controller the controller for the robot
     * @param view       the image view representing the robot
     */
    private AutonomusRobot(Environment env, Position pos, BotsController controller, ImageView view) {
        super(controller, view);
        this.environment = env;
        this.position = pos;
    }

    /**
     * Creates an autonomous robot with the specified environment, position, controller, and view.
     *
     * @param env        the environment in which the robot operates
     * @param pos        the initial position of the robot
     * @param controller the controller for the robot
     * @param view       the image view representing the robot
     * @return the created autonomous robot, or null if creation fails
     */
    public static AutonomusRobot create(Environment env, Position pos, BotsController controller, ImageView view) {
        if (env == null || pos == null || env.obstacleAt(pos) || env.robotAt(pos)) {
            return null;
        }
        AutonomusRobot thisBot = new AutonomusRobot(env, pos, controller, view);
        env.addRobot(thisBot);
        return thisBot;
    }

    /**
     * Moves the robot forward in the current direction.
     *
     * @return true if the movement was successful, false otherwise
     */
    public boolean move() {
        if(canMove()){
            int x=0, y=0;
            switch (angle){
                case 7: y = -1; x = -1; break;
                case 0: y = -1; break;
                case 1: y = -1; x = 1; break;
                case 2: x = 1; break;
                case 3: x = 1; y = 1; break;
                case 4: y = 1; break;
                case 5: x = -1; y = 1; break;
                case 6: x = -1; break;
            }
            Position newpos = new Position(position.getRow() + x, position.getCol() + y);
            super.notifyController(position, MovementType.MOVE, angle);
            this.environment.moveObject(position, newpos);
            this.position = newpos;
            return true;
        }
        else {
            turn();
        }
        return false;
    }

    /**
     * Retrieves the current position of the robot.
     *
     * @return the current position of the robot
     */
    @Override
    public Position getPosition() {
        return this.position;
    }

    /**
     * Retrieves the current angle of the robot.
     *
     * @return the current angle of the robot
     */
    @Override
    public int angle() {
        return this.angle*45;
    }

    /**
     * Turns the robot to the right by 45 degrees.
     */
    public void turn() {
        super.notifyController(position, MovementType.TURN, angle);
        angle = (angle + 1) % 8;
    }
    /**
     * Turns the robot to the left by 45 degrees.
     */
    public void turnReverse() {
        super.notifyController(position, MovementType.TURN, angle);
        angle = (angle - 1) % 8;
    }

    /**
     * Checks if the robot can move forward in the current direction.
     *
     * @return true if the robot can move forward, false otherwise
     */
    @Override
    public boolean canMove() {
        int x=0, y=0;
        switch (angle){
            case 7: y = -1; x = -1; break;
            case 0: y = -1; break;
            case 1: y = -1; x = 1; break;
            case 2: x = 1; break;
            case 3: x = 1; y = 1; break;
            case 4: y = 1; break;
            case 5: x = -1; y = 1; break;
            case 6: x = -1; break;

        }
        Position newpos = new Position(position.getRow() + x,position.getCol() + y);
        if (environment.containsPosition(newpos)){
            return !(environment.obstacleAt(newpos)) && !(environment.robotAt(newpos) );
        }

        return false;
    }

}
