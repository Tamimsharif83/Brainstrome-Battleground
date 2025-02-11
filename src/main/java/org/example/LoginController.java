package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import java.io.IOException;

public class LoginController {

    @FXML
    private void switchToGameface(ActionEvent event) throws IOException {
        try {
            // Load the gameface.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gameface.fxml"));
            Parent settingsRoot = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(settingsRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Prints the error if loading fails
        }
    }
}
