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
import config.MazeConfig;
import model.ClydeController;
import model.MazeState;

public class App extends Application {

    public void playBackgroundMusic() {
        try {
            File audioFile = new File("src/main/resources/bgm.wav"); 
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(0.2f); 
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
        var clydeController = new ClydeController();
        clydeController.startAI();
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);
        var maze = new MazeState(MazeConfig.originalMaze("maze2"));
        var gameView = new GameView(maze, root, 30.0);
        playBackgroundMusic();
        primaryStage.setScene(gameScene);
        primaryStage.show();
        gameView.animate();
    }
}
