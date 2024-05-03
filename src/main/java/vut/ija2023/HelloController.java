package vut.ija2023;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;

import java.io.InputStream;
import java.util.Random;

public class HelloController {

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

    // Set the static size of the images
    double imageSize = 45.0;

    @FXML
    void onAddRobot(ActionEvent event) {
        // Calculate the width and height of each cell in the grid
        double cellWidth = gameField.getWidth() / 8;
        double cellHeight = gameField.getHeight() / 8;

        // Check if the cell is already occupied
        boolean[][] occupiedCells = new boolean[8][8];
        for (Node node : gameField.getChildren()) {
            if (node instanceof ImageView) {
                ImageView imageView = (ImageView) node;
                double nodeX = imageView.getLayoutX();
                double nodeY = imageView.getLayoutY();
                int row = (int) (nodeY / cellHeight);
                int col = (int) (nodeX / cellWidth);
                occupiedCells[row][col] = true;
            }
        }

        InputStream stream = getClass().getResourceAsStream("/vut/ija2023/images/valli.png");
        if (stream == null) {
            System.err.println("Could not load robot image");
            return;
        }

        Image robotImage = new Image(stream);
        ImageView robotImageView = new ImageView(robotImage);
        robotImageView.setFitWidth(imageSize);
        robotImageView.setFitHeight(imageSize);

        Random random = new Random();

        int maxAttempts = 64; // Maximum number of attempts to find a free cell
        int attempt = 0;
        int row, col;
        do {
            // Generate random grid position
            row = random.nextInt(8);
            col = random.nextInt(8);
            attempt++;
        } while (occupiedCells[row][col] && attempt < maxAttempts);

        // Check if all cells are occupied
        boolean allCellsOccupied = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!occupiedCells[i][j]) {
                    allCellsOccupied = false;
                    break;
                }
            }
        }

        if (attempt >= maxAttempts || allCellsOccupied) {
            System.err.println("Unable to find a free cell to place the robot");
            return;
        }

        // Calculate actual position (x, y) within the gameField pane
        double x = col * cellWidth + (cellWidth - imageSize) / 2;
        double y = row * cellHeight + (cellHeight - imageSize) / 2;

        robotImageView.setLayoutX(x);
        robotImageView.setLayoutY(y);

        gameField.getChildren().add(robotImageView);
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

        Random random = new Random();

        // Generate random grid position
        int row = random.nextInt(8);
        int col = random.nextInt(8);

        // Calculate actual position (x, y) within the gameField pane
        double x = col * cellWidth + (cellWidth - imageSize) / 2;
        double y = row * cellHeight + (cellHeight - imageSize) / 2;

        bushImageView.setLayoutX(x);
        bushImageView.setLayoutY(y);

        gameField.getChildren().add(bushImageView);
    }




    @FXML
    void initialize() {
        assert addObstacleBtn != null : "fx:id=\"addObstacleBtn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert addRobotBtn != null : "fx:id=\"addRobotBtn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert gameField != null : "fx:id=\"gameField\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert obstacle != null : "fx:id=\"obstacle\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert robot != null : "fx:id=\"robot\" was not injected: check your FXML file 'hello-view.fxml'.";

    }

}
