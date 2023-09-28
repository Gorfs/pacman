package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.nio.file.Paths;

import config.MazeConfig;
import model.MazeState;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        music();
        var root = new Pane();
        var gameScene = new Scene(root);
        var pacmanController = new PacmanController();
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);
        var maze = new MazeState(MazeConfig.mazeFromFile());
        var gameView = new GameView(maze, root, 100.0);
        
        primaryStage.setScene(gameScene);
        primaryStage.show();
        gameView.animate();
    }

    MediaPlayer mediaPlayer;
    public void music(){
        String s = "src\\main\\resources\\bgm.mp3";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }
        
    
}
