/*
package com.bildungsinstitut;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bildungsinstitut/bildungsinstitut/MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 400);
        stage.setTitle("Bildungsinstitute");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
*/
package com.bildungsinstitut;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try {
            // Load image
            Image image = new Image(getClass().getResourceAsStream("/com/bildungsinstitut/bildungsinstitut/pictures/logo.png"));
            ImageView imageView = new ImageView(image);

            // Set ImageView properties
            imageView.setFitHeight(98);
            imageView.setFitWidth(118);
            imageView.setPreserveRatio(true);

            // Create AnchorPane and add ImageView
            AnchorPane root = new AnchorPane();
            root.getChildren().add(imageView);

            // Set specific position in AnchorPane
            AnchorPane.setTopAnchor(imageView, 10.0); // pixels from the top
            AnchorPane.setLeftAnchor(imageView, 20.0); // pixels from the left

            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bildungsinstitut/bildungsinstitut/MainWindow.fxml"));
            AnchorPane mainPane = fxmlLoader.load();

            // Add the image pane to the main pane (or replace it depending on your layout)
            mainPane.getChildren().add(root);

            // Create and set the scene
            Scene scene = new Scene(mainPane, 750, 400);
            stage.setTitle("Bildungsinstitute");
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
