package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label signupLabel;

    private MediaPlayer introMusicPlayer;

    @FXML
    public void initialize() {
        // Play the intro music when the login screen loads
        try {
            String introMusicPath = "src/main/resources/music/intro_music.mp3";
            Media introMedia = new Media(new File(introMusicPath).toURI().toString());
            introMusicPlayer = new MediaPlayer(introMedia);
            introMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely
            introMusicPlayer.play();
        } catch (Exception e) {
            System.err.println("Error loading intro music: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSignupClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
            Parent signupPage = loader.load();

            Stage stage = (Stage) signupLabel.getScene().getWindow();
            stage.setScene(new Scene(signupPage));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FXML file loading failed! Check the file path.");
        }
    }

    @FXML
    private void switchToGameface(ActionEvent event) throws IOException {
        String userInput = username.getText().trim();
        String passwordInput = password.getText().trim();

        // Validate input
        if (userInput.isEmpty() || passwordInput.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Login Error", "Please enter both username and password.");
            return;
        }

        // Verify credentials with database
        if (DatabaseController.verifyUser(userInput, passwordInput)) {
            try {
                // Stop the intro music and start main music
                if (introMusicPlayer != null) {
                    introMusicPlayer.stop();
                }
                Main.playMainMusic();

                // Load the gameface.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gameface.fxml"));
                Parent gamefaceRoot = loader.load();

                // Get the current stage
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set the new scene
                stage.setScene(new Scene(gamefaceRoot));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace(); // Prints the error if loading fails
                showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load game interface.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password. Please try again.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}