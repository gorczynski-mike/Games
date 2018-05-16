package com.gorczynskimike.warmercolder;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.net.URL;

public class MusicClass extends Application implements Runnable {

//    public MusicClass() {
//
//        public class JavaFXInitializer extends Application {
//            @Override
//            public void start(Stage stage) throws Exception {
//                // JavaFX should be initialized
//                someGlobalVar.setInitialized(true);
//            }
//        }
//
//        URL musicURL = getClass().getClassLoader().getResource("bip.mp4");
//        Media hit = new Media(musicURL.toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(hit);
//        mediaPlayer.play();
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("start");
        URL musicURL = getClass().getClassLoader().getResource("bip.mp4");
        Media hit = new Media(musicURL.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
        System.out.println("end");
    }


    @Override
    public void run() {
        Application.launch();
    }
}
