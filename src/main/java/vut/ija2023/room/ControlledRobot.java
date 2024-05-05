package vut.ija2023.room;

import javafx.scene.image.ImageView;
import vut.ija2023.BotsController;
import vut.ija2023.common.AbstractRobot;
import vut.ija2023.common.Environment;
import vut.ija2023.common.log.Log;
import vut.ija2023.common.Robot;
import vut.ija2023.enviroment.Position;

/**
 * Checks if the robot can move forward in the current direction.
 */
public class ControlledRobot extends AbstractRobot implements Robot {
    private Position position;
    private Environment environment;
    private int angle = 0;
    private boolean selected = false;
    /**
     * Checks if the robot is selected.
     *
     * @return true if the robot is selected, false otherwise
     */
    public boolean isSelected() {
        return selected;
    }
    /**
     * Sets the selected state of the robot.
     *
     * @param selected the new selected state
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Private constructor for a ControlledRobot.
     * This constructor is private to enforce the use of the create method for creating ControlledRobot instances.
     *
     * @param env the environment in which the robot exists
     * @param pos the initial position of the robot
     * @param controller the controller that controls the robot
     * @param view the ImageView representing the robot
     */
    private ControlledRobot(Environment env, Position pos, BotsController controller, ImageView view) {
        super(controller, view);
        this.environment = env;
        this.position = pos;
    }

    /**
     * Creates a new ControlledRobot.
     * This method validates the input parameters and returns a new ControlledRobot if they are valid.
     *
     * @param env the environment in which the robot will exist
     * @param pos the initial position of the robot
     * @param controller the controller that will control the robot
     * @param view the ImageView that will represent the robot
     * @return a new ControlledRobot if the input parameters are valid, null otherwise
     */
    public static ControlledRobot create(Environment env, Position pos, BotsController controller, ImageView view) {
        if (env == null || pos == null) {
            return null;
        }
        if (!env.containsPosition(pos)) {
            return null;
        }
        if (env.obstacleAt(pos)) {
            return null;
        }
        if (env.robotAt(pos)) {
            return null;
        }
        ControlledRobot thisBot = new ControlledRobot(env, pos, controller, view);
        env.addRobot(thisBot);
        return thisBot;
    }

    @Override
    public int angle() {
        return this.angle*45;
    }



    @Override
    public void turn() {
        super.notifyController(position, Log.MovementType.TURN, angle);
        angle = (angle + 1) % 8;
    }

    @Override
    public void turnReverse() {
        super.notifyController(position, Log.MovementType.TURN, angle);
        angle = (angle - 1 + 8) % 8; // Add 8 before taking modulus to handle negative values
    }

    /**
     * Checks if the robot can move to the next cell in its current direction.
     *
     * @return true if the robot can move to the next cell, false otherwise
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
            return !(environment.obstacleAt(newpos)) && !(environment.robotAt(newpos));
        }

        return false;
    }

    /**
     * Moves the robot to the next cell in its current direction if possible.
     *
     * @return true if the robot moved to the next cell, false otherwise
     */
    @Override
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
            super.notifyController(position, Log.MovementType.MOVE, angle);
            this.environment.moveObject(position, newpos);
            this.position = newpos;
            return true;
        }
        return false;
    }
    /**
     * Gets the current position of the robot.
     *
     * @return the current position of the robot
     */
    @Override
    public Position getPosition() {
        return this.position;
    }

}
