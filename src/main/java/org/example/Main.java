package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    public static MediaPlayer introMusicPlayer;
    public static MediaPlayer mainMusicPlayer;

    @Override
    public void start(Stage stage) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/login.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            // Set the title
            stage.setTitle("BrainStrome Battleground");

            // Set the scene
            stage.setScene(scene);

            // Disable maximize option
            stage.setResizable(false);

            // Play intro music
            playIntroMusic();

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void playIntroMusic() {
        String introMusicPath = "src/main/resources/music/intro_music.mp3"; // Path to the intro music file
        Media introMedia = new Media(new File(introMusicPath).toURI().toString());
        introMusicPlayer = new MediaPlayer(introMedia);
        introMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely
        introMusicPlayer.play();
    }

    public static void playMainMusic() {
        // Stop intro music
        if (introMusicPlayer != null) {
            introMusicPlayer.stop();
        }

        String mainMusicPath = "src/main/resources/music/main_music.mp3"; // Path to the main music file
        Media mainMedia = new Media(new File(mainMusicPath).toURI().toString());
        mainMusicPlayer = new MediaPlayer(mainMedia);
        mainMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely
        mainMusicPlayer.play();
    }

    public static void main(String[] args) {
        launch();
    }
}