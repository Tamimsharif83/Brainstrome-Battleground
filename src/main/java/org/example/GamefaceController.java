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

public class GamefaceController {

    private static MediaPlayer mainMusicPlayer;

    @FXML
    public void initialize() {
        startMainMusic();
    }

    public void startMainMusic() {
        if (mainMusicPlayer == null) {
            String mainMusicPath = "src/main/resources/music/main_music.mp3";
            Media mainMedia = new Media(new File(mainMusicPath).toURI().toString());
            mainMusicPlayer = new MediaPlayer(mainMedia);
            mainMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }
        mainMusicPlayer.play();
    }

    public static MediaPlayer getMainMusicPlayer() {
        return mainMusicPlayer;
    }

    @FXML
    private void handleSettingsButton(ActionEvent event) throws IOException {
        // Load the settings page FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/settingspage.fxml"));
        Parent settingsRoot = loader.load();

        // Pass the music player to settings controller
        SettingsController settingsController = loader.getController();
        settingsController.setMusicPlayer(mainMusicPlayer);

        // Get the current stage from the event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene
        stage.setScene(new Scene(settingsRoot));
        stage.show();
    }

    @FXML
    private void handleExit(ActionEvent event) {
        // Stop music before exiting
        if (mainMusicPlayer != null) {
            mainMusicPlayer.stop();
        }

        // Get the current stage and close it
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        // Exit the application
        System.exit(0);
    }

    @FXML
    private void handlePlayButton(ActionEvent event) throws IOException {
        // Load the game screen FXML
        Parent gameRoot = FXMLLoader.load(getClass().getResource("/fxml/game.fxml"));

        // Get the current stage from the event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene
        stage.setScene(new Scene(gameRoot));
        stage.show();
    }
}