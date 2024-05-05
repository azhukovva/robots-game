package vut.ija2023;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BotsApplication extends Application {
    private BotsController botsController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BotsApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 534, 600);

        botsController = fxmlLoader.getController();
        stage.setTitle("Robots:3");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        // This method is called when the application is about to close
        botsController.logger.printLogs();  // Method in Logger to print all accumulated logs
        System.out.println("Application is closing.");
    }

    public static void main(String[] args) {
        launch();
    }
}