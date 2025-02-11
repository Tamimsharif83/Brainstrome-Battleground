package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SettingsController {

    public void handleSettingsBackBtn(ActionEvent event) throws IOException {
        // Load the gameface.fxml file
        Parent gamefaceRoot = FXMLLoader.load(getClass().getResource("/fxml/gameface.fxml"));

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene
        stage.setScene(new Scene(gamefaceRoot));

        // Show the stage
        stage.show();
    }
}

