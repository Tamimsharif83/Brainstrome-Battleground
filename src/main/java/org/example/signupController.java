package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.io.IOException;

public class signupController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button signbtn;

    private MediaPlayer introMusicPlayer;

    @FXML
    public void initialize() {
        try {
            // Play the intro music when the login screen loads
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
    private void switchToGameface(ActionEvent event) throws IOException {
        String userInput = username.getText().trim();
        String passwordInput = password.getText().trim();

        // Validate input
        if (userInput.isEmpty() || passwordInput.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Registration Error", "Please enter both username and password.");
            return;
        }

        // Register user in database
        boolean registrationSuccess = DatabaseController.registerUser(userInput, passwordInput);

        if (registrationSuccess) {
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

                showAlert(Alert.AlertType.INFORMATION, "Registration Successful",
                        "Account created successfully! Welcome " + userInput + "!");
            } catch (IOException e) {
                e.printStackTrace(); // Prints the error if loading fails
                showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load game interface.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Registration Failed",
                    "Username may already exist or there was a database error. Please try again.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void switchToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent loginPage = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginPage));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FXML file loading failed! Check the file path.");
        }
    }
}