package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import java.io.IOException;

public class GamefaceController{

    public void handleSettingsButton(ActionEvent event) throws IOException {
        // Load the settings page FXML
        Parent settingsRoot = FXMLLoader.load(getClass().getResource("/fxml/settingspage.fxml"));


        // Get the current stage from the event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene
        stage.setScene(new Scene(settingsRoot));

        // Show the stage
        stage.show();
    }
}
