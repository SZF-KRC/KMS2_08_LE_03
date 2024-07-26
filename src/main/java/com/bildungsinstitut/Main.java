package com.bildungsinstitut;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bildungsinstitut/bildungsinstitut/MainWindow.fxml"));
            AnchorPane mainPane = fxmlLoader.load();

            // Create and set the scene
            Scene scene = new Scene(mainPane, 750, 400);
            stage.setTitle("Bildungsinstitute");

            // Set the logo on the window's title bar
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/bildungsinstitut/bildungsinstitut/pictures/logo.png")));
            stage.getIcons().add(icon);

            // Disable resizing
            stage.setResizable(false);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("The image could not be loaded. Check the file path.");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
