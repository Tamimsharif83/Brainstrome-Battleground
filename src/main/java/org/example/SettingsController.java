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

    @FXML
    public void initialize() {
        if (Main.mainMusicPlayer != null) {
            // স্লাইডারের মান সেট করা হচ্ছে (0-100 স্কেলে)
            volumeSlider.setValue(Main.mainMusicPlayer.getVolume() * 100);

            // যখন স্লাইডার পরিবর্তন হবে, তখন মিডিয়া প্লেয়ারের ভলিউম আপডেট হবে
            volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                double newVolume = newValue.doubleValue() / 100; // 0.0 - 1.0 এ রূপান্তর
                Main.mainMusicPlayer.setVolume(newVolume);
                System.out.println("Updated Volume: " + newVolume); // Debugging log
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
        if (mainMusicPlayer != null) {
            // স্লাইডারের মান সেট করা হচ্ছে
            volumeSlider.setValue(mainMusicPlayer.getVolume() * 100);

            // স্লাইডারের মান পরিবর্তন হলে মিডিয়া প্লেয়ারের ভলিউম আপডেট হবে
            volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                double newVolume = newValue.doubleValue() / 100;
                mainMusicPlayer.setVolume(newVolume);
                System.out.println("Updated Volume from setMusicPlayer: " + newVolume); // Debugging log
            });
        }
    }
}
