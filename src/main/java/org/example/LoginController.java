package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.io.IOException;

public class LoginController {

    private MediaPlayer introMusicPlayer;
    private MediaPlayer mainMusicPlayer;

    @FXML
    public void initialize() {
        // Play the intro music when the login screen loads
        String introMusicPath = "src/main/resources/music/intro_music.mp3";
        Media introMedia = new Media(new File(introMusicPath).toURI().toString());
        introMusicPlayer = new MediaPlayer(introMedia);
        introMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely
        introMusicPlayer.play();
    }

    @FXML

    private void switchToGameface(ActionEvent event) throws IOException {
        try {
            // Stop the intro music and start main music
            introMusicPlayer.stop();
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
        }
    }

}
