package org.example;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import java.io.IOException;
public class GamefaceController {
    @FXML // Add this annotation to allow FXML to recognize the method
    private void handleSettingsButton(ActionEvent event) throws IOException {
        // Load the settings page FXML
        Parent settingsRoot = FXMLLoader.load(getClass().getResource("/fxml/settingspage.fxml"));

        // Get the current stage from the event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene
        stage.setScene(new Scene(settingsRoot));

        // Show the stage
        stage.show();
    }

    @FXML
    private void handleExit(ActionEvent event) {
        // Get the current stage and close it
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        // OR, if you want to exit the entire application
        System.exit(0);
    }
    @FXML

    private void handlePlayButton(ActionEvent event) throws IOException {
        // Load the settings page FXML
        Parent settingsRoot = FXMLLoader.load(getClass().getResource("/fxml/game.fxml"));

        // Get the current stage from the event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene
        stage.setScene(new Scene(settingsRoot));

        // Show the stage
        stage.show();
    }

}


