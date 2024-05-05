package vut.ija2023;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import vut.ija2023.common.*;
import vut.ija2023.common.config.ConfigurationLoader;
import vut.ija2023.common.log.Log;
import vut.ija2023.common.log.Logger;
import vut.ija2023.enviroment.Position;
import vut.ija2023.room.AutonomusRobot;
import vut.ija2023.room.ControlledRobot;
import vut.ija2023.room.Room;
import javafx.scene.control.Alert;


import java.util.Random;

/**
 * Controller class for the Bots application.
 * Handles user interactions and updates the game state.
 */
public class BotsController {

    private List<NotifyMessage> messagesList = new ArrayList<NotifyMessage>();
    private List<AutonomusRobot> autoRobotList = new ArrayList<AutonomusRobot>();
    private List<ControlledRobot> controlledRobotList = new ArrayList<ControlledRobot>();

    private Timeline timeline;
    private boolean isPlaying = false;


    private final ImageView playIconView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/vut/ija2023/images/play.png"))));
    private final ImageView stopIconView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/vut/ija2023/images/stop.png"))));;
    Image bushImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/vut/ija2023/images/armchair.png")));
    private final Image robotImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/vut/ija2023/images/robot.png")));                                                // Create a Logger instance and add it as an observer to the robot
    public Logger logger = new Logger();


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addObstacleBtn;

    @FXML
    private Button addRobotBtn;

    @FXML
    private Button addAutoRobotBtn;

    @FXML
    private Pane gameField;

    double cellWidth;
    double cellHeight;

    @FXML
    private Pane obstacle;

    @FXML
    private Pane robot;

    @FXML
    private Button moveUp;

    @FXML
    private Button playBtn;

    @FXML
    private Button replayBtn;

    @FXML
    private Button changeAngle;
    @FXML
    private Button changeAngleReverse;

    @FXML
    private Button addConfiguration;

    @FXML
    private Button newGame;

    private Robot controlledRobotIndex;

    private Environment env = Room.create(8, 8);

    // Set the static size of the images
    double imageSize = 45.0;

    /**
     * Sets up the timeline for the simulation.
     */
    private void setupTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(.2), e -> updateSimulation()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Updates the simulation state.
     */
    private void updateSimulation() {
        for (AutonomusRobot robot : autoRobotList) {
            robot.move();
        }
        ViewPainter.paint(messagesList);
        logger.log(messagesList);
        messagesList.clear();
    }

    /**
     * Shows an alert if the simulation is not running.
     */
    private void showSimulationAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Simulation Error");
        alert.setHeaderText(null);
        alert.setContentText("You must start the simulation first by clicking the play button.");
        alert.showAndWait();
    }

    /**
     * Finds a free cell in the environment.
     *
     * @return the position of the free cell, or null if no free cell is found
     */
    Position findFreeCell() {
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
        } while ((env.obstacleAt(pos) || env.robotAt(pos)) && attempt < maxAttempts);
        if (attempt >= maxAttempts) {
            System.err.println("Unable to find a free cell to place the robot");
            return null;
        }
        return pos;
    }

    /**
     * Creates an ImageView for a robot.
     *
     * @return the created ImageView
     */
    ImageView createRobotImageView() {
        ImageView robotImageView = new ImageView(robotImage);
        robotImageView.getProperties().put("type", "robot");
        robotImageView.setFitWidth(imageSize);
        robotImageView.setFitHeight(imageSize);
        return robotImageView;
    }

    /**
     * Creates a robot and adds it to the game field.
     *
     * @param new_robot the robot to be added
     */
    void createRobot(Robot new_robot){
        env.addRobot(new_robot);

        // Calculate actual position (x, y) within the gameField pane
        double x = new_robot.getPosition().getRow() * cellWidth + (cellWidth - imageSize) / 2;
        double y = new_robot.getPosition().getCol() * cellHeight + (cellHeight - imageSize) / 2;

        new_robot.getImageView().getStyleClass().add("robot");

        new_robot.getImageView().setLayoutX(x);
        new_robot.getImageView().setLayoutY(y);

        gameField.getChildren().add(new_robot.getImageView());
    }

    /**
     * Handles the event of adding a robot.
     *
     * @param event the event that triggered this method
     */
    @FXML
    void onAddRobot(ActionEvent event) {
        // Calculate the width and height of each cell in the grid

        ImageView robotImageView = createRobotImageView();


        Position pos;
        if ((pos = findFreeCell()) != null) {
            ControlledRobot new_robot = ControlledRobot.create(env, pos, this, robotImageView);

            createRobot(new_robot);

            controlledRobotIndex = new_robot;

            for (ControlledRobot otherRobot : controlledRobotList) {
                otherRobot.setSelected(false);
            }
            new_robot.setSelected(true);
            controlledRobotList.add(new_robot);

            robotImageView.setOnMouseClicked(mouseEvent -> {
                for (ControlledRobot otherRobot : controlledRobotList) {
                    otherRobot.setSelected(false);
                }
                new_robot.setSelected(true);
                controlledRobotIndex = new_robot; // update the selected robot
            });

            this.addMessage(new_robot.getPosition(), new_robot, Log.MovementType.ADD, new_robot.angle());

        }
    }


    @FXML
    void onAddObstacle(ActionEvent event) {

        ImageView bushImageView = new ImageView(bushImage);
        bushImageView.getProperties().put("type", "obstacle");
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
    void onAddAutoRobot(ActionEvent event) {
        ImageView robotImageView = createRobotImageView();

        Position pos;
        if ((pos = findFreeCell()) != null) {
            AutonomusRobot new_robot = AutonomusRobot.create(env, pos, this, robotImageView);

            createRobot(new_robot);

            autoRobotList.add(new_robot);
            this.addMessage(new_robot.getPosition(), new_robot, Log.MovementType.ADD, new_robot.angle());
        }
    }

    /**
     * Initializes the controller.
     */
    @FXML
    void initialize() {
        assert addObstacleBtn != null : "fx:id=\"addObstacleBtn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert addRobotBtn != null : "fx:id=\"addRobotBtn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert addAutoRobotBtn != null : "fx:id=\"addAutoRobotBtn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert gameField != null : "fx:id=\"gameField\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert obstacle != null : "fx:id=\"obstacle\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert robot != null : "fx:id=\"robot\" was not injected: check your FXML file 'hello-view.fxml'.";

        playIconView.setFitHeight(20.0);
        playIconView.setFitWidth(33.0);
        playIconView.setPickOnBounds(true);
        playIconView.setPreserveRatio(true);

        stopIconView.setFitHeight(20.0);
        stopIconView.setFitWidth(33.0);
        stopIconView.setPickOnBounds(true);
        stopIconView.setPreserveRatio(true);

        Platform.runLater(() -> {
            ViewPainter.setGameField(gameField, 8);
            cellWidth = gameField.getWidth() / 8;
            cellHeight = gameField.getHeight() / 8;
            setupTimeline();
        });
    }

    /**
     * Handles the event of moving the selected robot up.
     * If the simulation is not running, it shows an alert to the user.
     * If a robot is selected, it moves the robot up.
     *
     * @param actionEvent the event that triggered this method
     */
    @FXML
    public void onMoveUp(ActionEvent actionEvent) {
        if (!isPlaying) {
            // Display an error message to the user
            showSimulationAlert();
            return;
        }
        for (ControlledRobot robot : controlledRobotList) {
            if (robot.isSelected()) {
                if(controlledRobotIndex!=null){
                    controlledRobotIndex.move();
                }
            }
        }
    }

    /**
     * Changes the angle of the selected robot.
     *
     * @param actionEvent the event that triggered this method
     */
    @FXML
    public void onChangeAngle(ActionEvent actionEvent) {
        if (!isPlaying) {
            // Display an error message to the user
            showSimulationAlert();
            return;
        }
        for (ControlledRobot robot : controlledRobotList) {
            if (robot.isSelected()) {
                controlledRobotIndex.turn();
            }
        }
    }

    /**
     * Changes the angle of the selected robot in the reverse direction(anti-clockwise).
     *
     * @param actionEvent the event that triggered this method
     */
    @FXML
    public void onChangeAngleReverse(ActionEvent actionEvent) {
        if (!isPlaying) {
            // Display an error message to the user
            showSimulationAlert();
            return;
        }
        for (ControlledRobot robot : controlledRobotList) {
            if (robot.isSelected()) {
                controlledRobotIndex.turnReverse();
            }
        }
    }

    /**
     * Adds a message to the messages list.
     *
     * @param pos the position of the robot
     * @param abstractRobot the robot that moved
     * @param type the type of movement
     * @param angle the angle of movement
     */
    public void addMessage(Position pos, AbstractRobot abstractRobot, Log.MovementType type, int angle) {
        messagesList.add(new NotifyMessage(pos, abstractRobot, type, angle));
    }


    /**
     * Starts or stops the game.
     *
     * @param actionEvent the event that triggered this method
     */
    @FXML
    public void onPlay(ActionEvent actionEvent) {
        togglePlayButton();
    }

    @FXML
    public void onReplay(ActionEvent actionEvent) {

    }

    /**
     * Toggles the play button state.
     */
    private void togglePlayButton() {

        if (isPlaying) {
            playBtn.setGraphic(playIconView);
            playBtn.getStyleClass().remove("play-btn-active");
            isPlaying = false;
            timeline.stop();
        } else {
            playBtn.setGraphic(stopIconView);
            playBtn.getStyleClass().add("play-btn-active");
            isPlaying = true;
            timeline.play();
        }
    }

    /**
     * Resets the game to its initial state.
     *
     * @param actionEvent the event that triggered this method
     */
    @FXML
    public void onNewGame(ActionEvent actionEvent) {
        // Clear the list of autonomous robots
        autoRobotList.clear();
        messagesList.clear();
        // Remove all robot and obstacle images from the gameField pane
        gameField.getChildren().removeIf(node -> node instanceof ImageView &&
                ("robot".equals(((ImageView) node).getProperties().get("type")) ||
                        "obstacle".equals(((ImageView) node).getProperties().get("type"))));

        // Reset the isPlaying state and the play button icon
        timeline.stop();
        isPlaying = false;
        playBtn.setGraphic(playIconView);
    }

    /**
     * Loads a new game configuration from a file.
     *
     * @param actionEvent the event that triggered this method
     */
    @FXML
    public void onAddConfig(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Configuration File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON Files", "*.json"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(null);


        if (selectedFile != null) {
            try {
                ConfigurationLoader.Configuration config = ConfigurationLoader.loadConfiguration(selectedFile.getPath());
                System.out.println(config);

                env.clear();

                for (ConfigurationLoader.Robot robot : config.getRobots()) {
                    ImageView robotImageView = createRobotImageView();
                        if (robot.getType().equals("controlled")) {
                            ControlledRobot new_robot = ControlledRobot.create(env, robot.getPosition(), this, robotImageView);
                            if (new_robot == null) {
                                System.err.println("Configuration is not valid. Robot cannot be created.");
                                return;
                            }
                            robotImageView.setOnMouseClicked(mouseEvent -> {
                                for (ControlledRobot otherRobot : controlledRobotList) {
                                    otherRobot.setSelected(false);
                                }
                                new_robot.setSelected(true);
                                controlledRobotIndex = new_robot; // update the selected robot
                            });
                            controlledRobotList.add((ControlledRobot) new_robot);
                            createRobot(new_robot);

                        } else {
                            AutonomusRobot new_robot = AutonomusRobot.create(env, robot.getPosition(), this, robotImageView);
                            if (new_robot == null) {
                                System.err.println("Configuration is not valid. Robot cannot be created.");
                                return;
                            }
                            autoRobotList.add((AutonomusRobot) new_robot);
                            createRobot(new_robot);
                        }

                }
                for (ConfigurationLoader.Obstacle obstacle : config.getObstacles()) {
                    ImageView bushImageView = new ImageView(bushImage);
                    bushImageView.getProperties().put("type", "obstacle");
                    bushImageView.setFitWidth(imageSize);
                    bushImageView.setFitHeight(imageSize);

                    Position pos = obstacle.getPosition();
                    env.createObstacleAt(pos.getRow(), pos.getCol());

                    double x = pos.getRow() * cellWidth + (cellWidth - imageSize) / 2;
                    double y = pos.getCol() * cellHeight + (cellHeight - imageSize) / 2;

                    bushImageView.setLayoutX(x);
                    bushImageView.setLayoutY(y);

                    gameField.getChildren().add(bushImageView);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File selection cancelled.");
        }
    }
}
