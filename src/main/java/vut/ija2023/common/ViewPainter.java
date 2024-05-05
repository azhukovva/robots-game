package vut.ija2023.common;

import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import vut.ija2023.enviroment.Position;
import vut.ija2023.common.AbstractRobot;

import java.util.List;

/**
 * Handles the painting of the game view.
 * This includes updating the positions and angles of robots on the game field.
 */
public class ViewPainter {

    private static double cellWidth;  // width of each grid cell in the pane
    private static double cellHeight; // height of each grid cell in the pane
    private static Pane gameField;    // the game field pane to draw onto

    /**
     * Sets the game field pane and calculates the dimensions of each grid cell.
     *
     * @param pane the game field pane
     * @param gridSize the size of the grid
     */
    public static void setGameField(Pane pane, int gridSize) {
        gameField = pane;
        cellWidth = gameField.getWidth() / gridSize;
        cellHeight = gameField.getHeight() / gridSize;
    }

    /**
     * Paints the game view based on a list of notify messages.
     *
     * @param messages the list of notify messages
     */
    public static void paint(List<NotifyMessage> messages) {
        if(messages == null) {
            return;
        }
        for (NotifyMessage msg : messages) {
            switch (msg.getMessage()) {
                case MOVE:
                    updateRobotPosition(msg.getRobot(), msg.getRobot().getPosition());
                    break;
                case TURN:
                    updateRobotAngle(msg.getRobot());
                    break;

            }
        }
    }

    /**
     * Updates the angle of a robot on the game field.
     *
     * @param robot the robot to update
     */
    private static void updateRobotAngle(Robot robot) {
        robot.getImageView().setRotate(robot.angle());
        robot.toggleFlag();
    }

    /**
     * Updates the position of a robot on the game field.
     *
     * @param robot the robot to update
     * @param pos the new position of the robot
     */
    private static void updateRobotPosition(Robot robot, Position pos) {
        ImageView view = robot.getImageView();

        if (view != null) {
            double x = pos.getRow() * cellWidth + (cellWidth - view.getFitWidth()) / 2;
            double y = pos.getCol() * cellHeight + (cellHeight - view.getFitHeight()) / 2;
            view.setLayoutX(x);
            view.setLayoutY(y);
            robot.toggleFlag();
            return;
        }
        System.err.println("Problem in updateRobotPosition function");
    }
}
