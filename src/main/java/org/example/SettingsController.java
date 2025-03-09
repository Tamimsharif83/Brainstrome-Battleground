package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.IOException;

public class SettingsController {

    @FXML
    private Slider volumeSlider;
    private MediaPlayer mediaPlayer;
    @FXML
    public void initialize() {
        if (Main.mainMusicPlayer != null) {
            volumeSlider.setValue(Main.mainMusicPlayer.getVolume() * 100); // Convert 0.0-1.0 to 0-100
            volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                Main.mainMusicPlayer.setVolume(newValue.doubleValue() / 100); // Convert back to 0.0-1.0
            });
        }
    }

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

    public void setMusicPlayer(MediaPlayer mainMusicPlayer) {
        if (mediaPlayer != null) {
            volumeSlider.setValue(mediaPlayer.getVolume() * 100);
            volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                mediaPlayer.setVolume(newValue.doubleValue() / 100);
            });
        }
    }
}
