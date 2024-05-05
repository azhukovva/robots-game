package vut.ija2023.common;

import javafx.scene.image.ImageView;
import vut.ija2023.BotsController;
import vut.ija2023.common.log.Log;
import vut.ija2023.enviroment.Position;

/**
 * Represents an abstract robot in the system.
 * An abstract robot includes a controller, a message flag, and an image view.
 */
public abstract class AbstractRobot implements Robot {
    private BotsController controller;
    public boolean messageFlag = false;
    private ImageView view;

    /**
     * Constructs a new AbstractRobot with the specified controller and image view.
     *
     * @param controller the controller of the robot
     * @param view the image view of the robot
     */
    public AbstractRobot(BotsController controller, ImageView view) {
        this.controller = controller;
        this.view = view;
    }

    /**
     * Returns the image view of the robot.
     *
     * @return the image view of the robot
     */
    public ImageView getImageView(){
        return view;
    }

    /**
     * Toggles the message flag of the robot.
     */
    public void toggleFlag(){
        messageFlag = !(messageFlag);
    }

    /**
     * Notifies the controller with the specified position, movement type, and angle.
     *
     * @param pos the position of the robot
     * @param type the movement type of the robot
     * @param angle the angle of the robot
     */
    public void notifyController(Position pos, Log.MovementType type, int angle ) {
        if (!messageFlag){
            controller.addMessage(pos, this,  type, angle );
            messageFlag = true;
        }
        // If it's true, it means a message has already been sent and no further action is taken
    }

}
