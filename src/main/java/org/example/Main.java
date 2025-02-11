package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // Load the FXML file from the correct resource folder
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/gameface.fxml"));

            // Create a scene using the FXML file
            // Scene scene = new Scene(fxmlLoader.load(), 824, 530);
            Parent root = fxmlLoader.load();// Adjusted size according to FXML
            Scene scene = new Scene(root);
            stage.setTitle("BrainStrome Battleground");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Print error information for debugging purposes
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(); // Launch the JavaFX application

        // significant change
    }
}