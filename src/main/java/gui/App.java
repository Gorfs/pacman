package gui;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.io.File;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.nio.file.Paths;

import config.MazeConfig;
import model.MazeState;

public class App extends Application {

    public void playBackgroundMusic() {
        try {
            File audioFile = new File("src/main/resources/bgm.wav"); 
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(0.2f); // 在这里设置初始音量
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        var root = new Pane();
        var gameScene = new Scene(root);
        var pacmanController = new PacmanController();
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);
        var maze = new MazeState(MazeConfig.mazeFromFile());
        var gameView = new GameView(maze, root, 100.0);

        playBackgroundMusic();
        
        primaryStage.setScene(gameScene);
        primaryStage.show();
        gameView.animate();
    }

    
        
    
}
