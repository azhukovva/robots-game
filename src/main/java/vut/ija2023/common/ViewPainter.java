package vut.ija2023.common;

import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import vut.ija2023.enviroment.Position;
import vut.ija2023.common.AbstractRobot;

import java.util.List;

public class ViewPainter {

    private static double cellWidth;  // width of each grid cell in the pane
    private static double cellHeight; // height of each grid cell in the pane
    private static Pane gameField;    // the game field pane to draw onto

    public static void setGameField(Pane pane, int gridSize) {
        gameField = pane;
        cellWidth = gameField.getWidth() / gridSize;
        cellHeight = gameField.getHeight() / gridSize;
    }

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

    private static void updateRobotAngle(Robot robot) {
        robot.getImageView().setRotate(robot.angle());
        robot.toggleFlag();
    }

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

    private static void addNewRobot(Robot robot, Position pos) {
        ImageView view = robot.getImageView();
        if (view == null) {
            System.err.println("Problem in addNewRobt function");
            // Log error or throw an exception
            return;
        }
        double x = pos.getRow() * cellWidth + (cellWidth - view.getFitWidth()) / 2;
        double y = pos.getCol() * cellHeight + (cellHeight - view.getFitHeight()) / 2;
        view.setLayoutX(x);
        view.setLayoutY(y);
        gameField.getChildren().add(view);
    }
}
