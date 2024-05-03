package vut.ija2023;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.util.Duration;
import vut.ija2023.common.Environment;
import vut.ija2023.common.Robot;
import vut.ija2023.enviroment.Position;
import vut.ija2023.room.ControlledRobot;
import vut.ija2023.room.Room;

import java.io.InputStream;
import java.util.Random;

public class HelloController {

    private Timeline timeline;

    private void setupTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateSimulation()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void updateSimulation() {
        return;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private Button addObstacleBtn;

    @FXML
    private Button addRobotBtn;

    @FXML
    private Pane gameField;

    @FXML
    private Pane obstacle;

    @FXML
    private Pane robot;

    @FXML
    private Button moveDown;

    @FXML
    private Button moveUp;

    @FXML
    private Button moveLeft;

    @FXML
    private Button moveRight;



    private Robot controlledRobotIndex;

    private Environment env = Room.create(8,8);

    // Set the static size of the images
    double imageSize = 45.0;

    Position findFreeCell () {
        Random random = new Random();
        Position pos;
        int maxAttempts = 64; // Maximum number of attempts to find a free cell
        int attempt = 0;
        int row, col;
        do {
            // Generate random grid position
            row = random.nextInt(8);
            col = random.nextInt(8);
            pos = new Position(row, col);
            attempt++;
        } while ( (env.obstacleAt(pos) || env.robotAt(pos) ) && attempt < maxAttempts);
        if (attempt >= maxAttempts ) {
            System.err.println("Unable to find a free cell to place the robot");
            return null;
        }
        return pos;
    }

    @FXML
    void onAddRobot(ActionEvent event) {


        // Calculate the width and height of each cell in the grid
        double cellWidth = gameField.getWidth() / 8;
        double cellHeight = gameField.getHeight() / 8;



        InputStream stream = getClass().getResourceAsStream("/vut/ija2023/images/valli.png");
        if (stream == null) {
            System.err.println("Could not load robot image");
            return;
        }

        Image robotImage = new Image(stream);
        ImageView robotImageView = new ImageView(robotImage);
        robotImageView.setFitWidth(imageSize);
        robotImageView.setFitHeight(imageSize);

        Position pos;
        if ((pos= findFreeCell()) != null) {
            Robot new_robot = ControlledRobot.create(env, pos);
            env.addRobot(new_robot);

            // Calculate actual position (x, y) within the gameField pane
            double x = pos.getRow() * cellWidth + (cellWidth - imageSize) / 2;
            double y = pos.getCol() * cellHeight + (cellHeight - imageSize) / 2;

            robotImageView.setLayoutX(x);
            robotImageView.setLayoutY(y);

            gameField.getChildren().add(robotImageView);

            controlledRobotIndex = new_robot;
        }
    }


    @FXML
    void onAddObstacle(ActionEvent event) {

        // Calculate the width and height of each cell in the grid
        double cellWidth = gameField.getWidth() / 8;
        double cellHeight = gameField.getHeight() / 8;

        InputStream stream = getClass().getResourceAsStream("/vut/ija2023/images/bush.png");
        if (stream == null) {
            System.err.println("Could not load bush image");
            return;
        }
        Image bushImage = new Image(stream);

        ImageView bushImageView = new ImageView(bushImage);
        bushImageView.setFitWidth(imageSize);
        bushImageView.setFitHeight(imageSize);

        Position pos;
        if ((pos = findFreeCell()) != null) {
            env.createObstacleAt(pos.getRow(), pos.getCol());

            double x = pos.getRow() * cellWidth + (cellWidth - imageSize) / 2;
            double y = pos.getCol() * cellHeight + (cellHeight - imageSize) / 2;

            bushImageView.setLayoutX(x);
            bushImageView.setLayoutY(y);

            gameField.getChildren().add(bushImageView);
        }
    }

    @FXML
    void initialize() {
        assert addObstacleBtn != null : "fx:id=\"addObstacleBtn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert addRobotBtn != null : "fx:id=\"addRobotBtn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert gameField != null : "fx:id=\"gameField\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert obstacle != null : "fx:id=\"obstacle\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert robot != null : "fx:id=\"robot\" was not injected: check your FXML file 'hello-view.fxml'.";

    }
    @FXML
    public void onMoveUp(ActionEvent actionEvent) {
        controlledRobotIndex.move();
    }
    @FXML
    public void onMoveRight(ActionEvent actionEvent) {
        controlledRobotIndex.turn();
    }
    @FXML
    public void onMoveLeft(ActionEvent actionEvent) {
    }
}
