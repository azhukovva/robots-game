package vut.ija2023;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main application class for the Robots:3 application.
 */
public class BotsApplication extends Application {
    private BotsController botsController;

    /**
     * The main entry point for the application.
     * @param args The command line arguments (not used).
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Initializes the application.
     * @param stage The primary stage for the application.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BotsApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 534, 600);

        botsController = fxmlLoader.getController();
        stage.setTitle("Robots:3");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Called when the application is about to close.
     */
    @Override
    public void stop() {
        botsController.logger.printLogs();  // Method in Logger to print all accumulated logs
        System.out.println("Application is closing.");
    }
}
