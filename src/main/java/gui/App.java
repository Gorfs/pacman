package gui;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.awt.Dimension;
import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import config.MazeConfig;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.control.*;
import model.ClydeController;
import model.MazeState;


public class App extends Application {

    private static KeyCode[] k = {KeyCode.LEFT,KeyCode.RIGHT,KeyCode.UP,KeyCode.DOWN};
    private static GameMenu gameMenu;
    private static TextField text;
    private static float son_effect=1;
    private static MenuButton2 button = new MenuButton2("Submit");
    private static MenuButton2 button1 = new MenuButton2("Submit");
    private static MenuButton btncase1 = new MenuButton("Left : Press a Key");
    private static MenuButton btncase2 = new MenuButton("Right : Press a Key");
    private static MenuButton btncase3 = new MenuButton("Up : Press a Key");
    private static MenuButton btncase4 = new MenuButton("Down : Press a Key");

    public static void K(KeyCode [] k, int n, KeyCode key){
            k[n]=key;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane root = new Pane();
        Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        double height = d.getHeight();
        double width = d.getWidth();
        root.setPrefSize(width, height);
        InputStream is = Files.newInputStream(Paths.get("src/main/resources/pac.jpg"));
        Image img = new Image(is);
        is.close();
        button1.setVisible(false);
        btncase1.setVisible(false);
        btncase2.setVisible(false);
        btncase3.setVisible(false);
        btncase4.setVisible(false);
        button.setVisible(false);
        text = new TextField("pseudo");
        text.setVisible(false);
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(1920);
        imgView.setFitHeight(1080);

        gameMenu = new GameMenu(root, primaryStage,k,text,button,btncase1,btncase2,btncase3,btncase4,son_effect);
        gameMenu.setVisible(true);
        root.getChildren().addAll(imgView, btncase1, btncase2, btncase3, btncase4, gameMenu, text, button, button1 );
        Scene scene = new Scene(root);
        
        scene.setOnKeyPressed(event -> {
            if(btncase1.isVisible()){//LEFT
                if(event.getCode()!=null){
                    K(k, 0, event.getCode());
                    btncase1.setVisible(false);
                }
            }
            if(btncase2.isVisible()){//RIGHT
                if(event.getCode()!=null){
                    K(k, 1, event.getCode());
                    btncase2.setVisible(false);
                }
            }
            if(btncase3.isVisible()){//UP
                if(event.getCode()!=null){
                    K(k, 2, event.getCode());
                    btncase3.setVisible(false);
                }
            }
            if(btncase4.isVisible()){//DOWN
                if(event.getCode()!=null){
                    K(k, 3, event.getCode());
                    btncase4.setVisible(false);
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    public void playBackgroundMusic(float l) {
        try {
            File audioFile = new File("src/main/resources/bgm.wav"); 
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(son_effect); 
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void start(Stage primaryStage, int l, KeyCode[] k, float son_effect) {
        var root = new Pane();
        double d1 = root.maxHeight(root.getMaxHeight());
        double d2 = root.maxWidth(root.getMaxWidth());
        root.setPrefSize(d2, d1);
        var gameScene = new Scene(root);
        GameMenu2 gameMenu2 = new GameMenu2(root, k, button, btncase1, btncase2, btncase3, btncase4, son_effect);
        gameMenu2.setVisible(false);
        var pacmanController = new PacmanController(k, gameMenu2, root, button, btncase1, btncase2, btncase3, btncase4, son_effect);
        var clydeController = new ClydeController();
        clydeController.startAI();
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);
        var maze = new MazeState(MazeConfig.originalMaze("maze2"));
        maze.setLives(l);
        var gameView = new GameView(maze, root, 50);
        Music.playBackgroundMusic();
        primaryStage.setScene(gameScene);
        primaryStage.show();
        gameView.animate();
    }
        
    
}
      


