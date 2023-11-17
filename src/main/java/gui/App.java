package gui;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.io.File;

import model.MazeState.EffetSonoreManager;

//inspiré grandement par
//https://github.com/AlmasB/FXTutorials/blob/
//master/src/main/java/com/almasb/tutorial4/GameMenuDemo.java
import model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import config.MazeConfig;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import model.MazeState;


public class App extends Application {

    private static Clip bgmClip;

    // final à enlever lorsqu'on pourra choisir plusieurs niveaux différents.
    private final static String filename = "maze2";

    public static void playBackgroundMusic() { // fonction pour lancer le bgm
        try {
            File audioFile = new File("src/main/resources/music/bgm.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(BGMManager.getVolume()));
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopBackgroundMusic() { // fonction pour arrêter le bgm
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
        }
    }

    public class BGMManager { //class pour la réglage du bgm et de l'effet sonore
        private static float volume = 0.6f;

        public static void setVolume(float volumeLevel) {
            if(volumeLevel < 0.0f) volume = 0.0f;
            else if(volumeLevel > 1.0f) volume = 1.0f;
            else volume = volumeLevel;
        }

        public static float getVolume() {
            return volume;
        }
    }

    private static KeyCode[] k = {KeyCode.LEFT,KeyCode.RIGHT,KeyCode.UP,KeyCode.DOWN};
    private GameMenu gameMenu;
    private GameMenu1 gameMenu1;
    private Stage primaryS;
    private TextField text;
    private TextField t;
    private MenuButton2 button = new MenuButton2("Submit");
    private MenuButton2 button1 = new MenuButton2("Submit");
    private MenuButton btncase1 = new MenuButton("Press a Key");
    private MenuButton btncase2 = new MenuButton("Press a Key");
    private MenuButton btncase3 = new MenuButton("Press a Key");
    private MenuButton btncase4 = new MenuButton("Press a Key");

    public static void K(KeyCode [] k, int n, KeyCode key){
            k[n]=key;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryS=primaryStage;
        Pane root = new Pane();
        root.setPrefSize(860, 600);

        InputStream is = Files.newInputStream(Paths.get("src/main/resources/pac.jpg"));
        Image img = new Image(is);
        is.close();
        TextField te = new TextField();
        TextField tex = new TextField();
        text=te;
        t=tex;
        button1.setVisible(false);
        btncase1.setVisible(false);
        btncase2.setVisible(false);
        btncase3.setVisible(false);
        btncase4.setVisible(false);
        button.setVisible(false);
        text.setVisible((false));
        t.setVisible(false);
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(860);
        imgView.setFitHeight(600);

        gameMenu = new GameMenu();
        gameMenu1 = new GameMenu1();
        gameMenu.setVisible(true);
        gameMenu1.setVisible(false);
        root.getChildren().addAll(imgView, btncase1, btncase2, btncase3, btncase4, gameMenu, gameMenu1, text, button, t, button1 );
        Scene scene = new Scene(root);
        
        scene.setOnKeyPressed(event -> {
            if(btncase1.isVisible()){
                if(event.getCode()!=null){
                    K(k, 0, event.getCode());
                    btncase1.setVisible(false);
                }
            }
            if(btncase2.isVisible()){
                if(event.getCode()!=null){
                    K(k, 1, event.getCode());
                    btncase2.setVisible(false);
                }
            }
            if(btncase3.isVisible()){
                if(event.getCode()!=null){
                    K(k, 2, event.getCode());
                    btncase3.setVisible(false);
                }
            }
            if(btncase4.isVisible()){
                if(event.getCode()!=null){
                    K(k, 3, event.getCode());
                    btncase4.setVisible(false);
                }
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                if (gameMenu.isVisible()) {
                    FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
                    ft.setFromValue(1);
                    ft.setToValue(0);

                    gameMenu.setVisible(false);
                    text.setVisible(false);
                    button.setVisible(false);
                    t.setVisible(false);
                    button1.setVisible(false);
                    gameMenu1.setVisible(true);
                    ft.play();

                }
                else if(button.isVisible()){
                    FadeTransition ft = new FadeTransition(Duration.seconds(0.5), button);
                    ft.setFromValue(1);
                    ft.setToValue(0);
                }
                else {
                    FadeTransition ft = new FadeTransition(Duration.seconds(0.1), gameMenu);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.setOnFinished(evt -> gameMenu.setVisible(true));
                    gameMenu1.setVisible(false);
                    t.setVisible(false);
                    button1.setVisible(false);
                    ft.play();
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private class GameMenu1 extends Parent {
        public GameMenu1() {
            VBox menu0 = new VBox(10);
            VBox menu1 = new VBox(10);
            VBox menu2 = new VBox(10);
            VBox menu3 = new VBox(10);
            VBox menu4 = new VBox(10);
            VBox menu5 = new VBox(10);

            menu0.setTranslateX(100);
            menu0.setTranslateY(200);

            menu1.setTranslateX(100);
            menu1.setTranslateY(200);

            menu2.setTranslateX(100);
            menu2.setTranslateY(200);

            menu3.setTranslateX(100);
            menu3.setTranslateY(200);

            menu4.setTranslateX(100);
            menu4.setTranslateY(200);

            menu5.setTranslateX(100);
            menu5.setTranslateY(200);

            final int offset = 400;

            menu1.setTranslateX(offset);

            Rectangle bg = new Rectangle(800, 600);
            bg.setFill(Color.GREY);
            bg.setOpacity(0.4);
        

            MenuButton btnOptions = new MenuButton("OPTIONS");
            btnOptions.setOnMouseClicked(event -> {
                getChildren().add(menu1);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
                tt.setToX(menu0.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
                tt1.setToX(menu0.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu0);
                });
            });

            MenuButton btnExit = new MenuButton("EXIT");
            btnExit.setOnMouseClicked(event -> {
                System.exit(0);
            });

            MenuButton btnBack = new MenuButton("BACK");
            btnBack.setOnMouseClicked(event -> {
                getChildren().add(menu0);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
                tt.setToX(menu1.getTranslateX() + offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
                tt1.setToX(menu1.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu1);
                });
            });

            MenuButton btnBack1 = new MenuButton("BACK");
            btnBack1.setOnMouseClicked(event -> {
                getChildren().add(menu1);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu2);
                tt.setToX(menu2.getTranslateX() + offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
                tt1.setToX(menu2.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu2);
                });
            });

            MenuButton btnBack2 = new MenuButton("BACK");
            btnBack2.setOnMouseClicked(event -> {
                getChildren().add(menu1);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu3);
                tt.setToX(menu3.getTranslateX() + offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
                tt1.setToX(menu3.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu3);
                });
            });

            MenuButton btnSound = new MenuButton("SOUND");
            btnSound.setOnMouseClicked(event -> {
                getChildren().add(menu3);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
                tt.setToX(menu1.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu3);
                tt1.setToX(menu1.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu1);
                });
            });

            MenuButton btnKey = new MenuButton("KEY");
            btnKey.setOnMouseClicked(event -> {
                getChildren().add(menu2);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
                tt.setToX(menu1.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu2);
                tt1.setToX(menu1.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu1);
                });
            });
            
            MenuButton btncaseLeft = new MenuButton("LEFT");
            btncaseLeft.setOnMouseClicked(event -> {
                btncase1.setVisible(true);
            });
            MenuButton btncaseRight = new MenuButton("RIGHT");
            btncaseRight.setOnMouseClicked(event1 -> {
                btncase2.setVisible(true);
            });
            
            MenuButton btncaseUp = new MenuButton("UP");
            btncaseUp.setOnMouseClicked(event1 -> {
                btncase3.setVisible(true);
            });

            MenuButton btncaseDown = new MenuButton("DOWN");
            btncaseDown.setOnMouseClicked(event1 -> {
                btncase4.setVisible(true);
            });

            MenuButton btnBack3 = new MenuButton("BACK");
            btnBack3.setOnMouseClicked(event -> {
                getChildren().add(menu3);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu4);
                tt.setToX(menu4.getTranslateX() + offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu3);
                tt1.setToX(menu4.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu4);
                });
            });

            MenuButton btns1 = new MenuButton("Background music");
            btns1.setOnMouseClicked(event -> {
                getChildren().add(menu4);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu3);
                tt.setToX(menu3.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu4);
                tt1.setToX(menu3.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu3);
                });
                });
            
            MenuButton btnBack4 = new MenuButton("BACK");
            btnBack4.setOnMouseClicked(event -> {
                getChildren().add(menu3);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu5);
                tt.setToX(menu5.getTranslateX() + offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu3);
                tt1.setToX(menu5.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu5);
                });
            });

            MenuButton btns2 = new MenuButton("Sound effects");
            btns2.setOnMouseClicked(event -> {
                getChildren().add(menu5);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu3);
                tt.setToX(menu3.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu5);
                tt1.setToX(menu3.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu3);
                });
                });

            // bouton de niveau de son (entre 0 et 5)
            MenuButton btn_bgm0 = new MenuButton("0");
            btn_bgm0.setOnMouseClicked(event -> BGMManager.setVolume(0.0f));

            MenuButton btn_bgm1 = new MenuButton("1");
            btn_bgm1.setOnMouseClicked(event -> BGMManager.setVolume(0.2f));

            MenuButton btn_bgm2 = new MenuButton("2");
            btn_bgm2.setOnMouseClicked(event -> BGMManager.setVolume(0.4f));

            MenuButton btn_bgm3 = new MenuButton("3");
            btn_bgm3.setOnMouseClicked(event -> BGMManager.setVolume(0.6f));

            MenuButton btn_bgm4 = new MenuButton("4");
            btn_bgm4.setOnMouseClicked(event -> BGMManager.setVolume(0.8f));

            MenuButton btn_bgm5 = new MenuButton("5");
            btn_bgm5.setOnMouseClicked(event -> BGMManager.setVolume(1.0f));

            MenuButton btn_eff0 = new MenuButton("0");
            btn_eff0.setOnMouseClicked(event -> EffetSonoreManager.setVolume(0.0f));

            MenuButton btn_eff1 = new MenuButton("1");
            btn_eff1.setOnMouseClicked(event -> EffetSonoreManager.setVolume(0.2f));

            MenuButton btn_eff2 = new MenuButton("2");
            btn_eff2.setOnMouseClicked(event -> EffetSonoreManager.setVolume(0.4f));

            MenuButton btn_eff3 = new MenuButton("3");
            btn_eff3.setOnMouseClicked(event -> EffetSonoreManager.setVolume(0.6f));

            MenuButton btn_eff4 = new MenuButton("4");
            btn_eff4.setOnMouseClicked(event -> EffetSonoreManager.setVolume(0.8f));

            MenuButton btn_eff5 = new MenuButton("5");
            btn_eff5.setOnMouseClicked(event -> EffetSonoreManager.setVolume(1.0f));


            menu2.getChildren().addAll(btnBack1, btncaseLeft, btncaseRight, btncaseUp, btncaseDown);
            menu3.getChildren().addAll(btnBack2, btns1, btns2);
            menu0.getChildren().addAll(btnOptions, btnExit);
            menu1.getChildren().addAll(btnBack, btnSound, btnKey);
            menu4.getChildren().addAll(btnBack3, btn_bgm0, btn_bgm1, btn_bgm2, btn_bgm3, btn_bgm4, btn_bgm5);
            //creation des menus qui suivent apres avoir cliqué
            //sur btns1 et respectivement btns2
            menu5.getChildren().addAll(btnBack4, btn_eff0, btn_eff1, btn_eff2, btn_eff3, btn_eff4, btn_eff5);
            getChildren().addAll(bg,menu0);
        }
    }
    private class GameMenu extends Parent {
        public GameMenu() {
            VBox menu2 = new VBox(10);
            VBox menu3 = new VBox(10);
            VBox menu4 = new VBox(10);

            menu2.setTranslateX(276);
            menu2.setTranslateY(100);

            menu3.setTranslateX(100);
            menu3.setTranslateY(200);

            menu4.setTranslateX(100);
            menu4.setTranslateY(200);

            final int offset = 400;


            Rectangle bg = new Rectangle(800, 600);
            bg.setFill(Color.GREY);
            bg.setOpacity(0);

            MenuButton btnName = new MenuButton("NAME");
            btnName.setOnMouseClicked(event -> {
                text.setTranslateY(320);
                text.setTranslateX(300);
                text.setText("Écrivez votre nom");
                text.setVisible((true));
                button.setVisible(true);
                button.setTranslateY(322);
                button.setTranslateX(432);
                button.setOnMouseClicked(event1 -> {
                    text.setVisible(false);
                    button.setVisible(false);
                    String a="";
                    a = text.getText();
                    System.out.println(a);
                    PacMan.INSTANCE = new PacMan();
                    PacMan.INSTANCE.getInstance(a);
                    getChildren().add(menu3);
                    TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu4);
                    tt.setToX(menu4.getTranslateX() + offset);

                    TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu3);
                    tt1.setToX(menu4.getTranslateX());

                    tt.play();
                    tt1.play();

                    tt.setOnFinished(evt -> {
                    getChildren().remove(menu4);
                    });
                });
            });

            MenuButton btnBack1 = new MenuButton("BACK");
            btnBack1.setOnMouseClicked(event -> {
                getChildren().add(menu2);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu3);
                tt.setToX(menu3.getTranslateX() + offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu2);
                tt1.setToX(menu3.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu3);
                });
            });

            MenuButton1 btnPlay = new MenuButton1("PLAY");
            btnPlay.setOnMouseClicked(event -> {
                getChildren().add(menu4);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu2);
                tt.setToX(menu2.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu4);
                tt1.setToX(menu2.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu2);
                });
            });

            MenuButton btnf = new MenuButton("FACILE");
            btnf.setOnMouseClicked(event -> {
                start(primaryS,4,k);
            });

            MenuButton btnm = new MenuButton("MEDIUM");
            btnm.setOnMouseClicked(event -> {
                start(primaryS,3,k);
            });

            MenuButton btnh = new MenuButton("HARD");
            btnh.setOnMouseClicked(event -> {
                start(primaryS,2,k);
            });

            MenuButton btne = new MenuButton("EXPERT");
            btne.setOnMouseClicked(event -> {
                start(primaryS,1,k);
            });
            menu4.getChildren().addAll(btnName);
            menu3.getChildren().addAll(btnBack1,btnf,btnm,btnh,btne);
            menu2.getChildren().addAll(btnPlay);
            getChildren().addAll(bg,menu2);
        }
    }
    private static class MenuButton2 extends StackPane {
        private Text text;

        public MenuButton2(String name) {
            text = new Text(name);
            text.setFont(Font.font(10));
            text.setStrokeWidth(0.5);
            text.setFill(Color.BLACK);

            Rectangle bg = new Rectangle(30,20);
            bg.setFill(Color.WHITE);
            bg.setOpacity(1);
            bg.setEffect(new GaussianBlur(3.5));

            setAlignment(Pos.CENTER);
            setRotate(-0.5);
            getChildren().addAll(bg, text);

            setOnMouseEntered(event -> {
                text.setFill(Color.PURPLE);
            });

            setOnMouseExited(event -> {
                text.setFill(Color.BLACK);
            });
            DropShadow drop = new DropShadow(50, Color.WHITE);
            drop.setInput(new Glow());

            setOnMousePressed(event -> setEffect(drop));
            setOnMouseReleased(event -> setEffect(null));
        }
    }
    private static class MenuButton1 extends StackPane {
        private Text text;

        public MenuButton1(String name) {
            text = new Text(name);
            text.setFont(Font.loadFont("file:src/main/resources/slkscrb.ttf", 100));
            text.setFill(Color.BROWN);
            text.setStroke(Color.BLUEVIOLET);
            text.setStrokeWidth(0.5);
            text.setFill(Color.WHITE);

            Rectangle bg = new Rectangle(332, 100);
            bg.setOpacity(0.6);
            bg.setFill(Color.BLACK);
            bg.setEffect(new GaussianBlur(3.5));

            setAlignment(Pos.CENTER);
            setRotate(-0.5);
            getChildren().addAll(bg, text);

            setOnMouseEntered(event -> {
                bg.setTranslateX(10);
                text.setTranslateX(10);
                bg.setFill(Color.WHITE);
                text.setFill(Color.BLACK);
            });

            setOnMouseExited(event -> {
                bg.setTranslateX(0);
                text.setTranslateX(0);
                bg.setFill(Color.BLACK);
                text.setFill(Color.WHITE);
            });
            DropShadow drop = new DropShadow(50, Color.WHITE);
            drop.setInput(new Glow());

            setOnMousePressed(event -> setEffect(drop));
            setOnMouseReleased(event -> setEffect(null));
        }
    }
    private static class MenuButton extends StackPane {
        private Text text;

        public MenuButton(String name) {
            text = new Text(name);
            text.setFont(Font.font(20));
            text.setFill(Color.WHITE);

            Rectangle bg = new Rectangle(250, 30);
            bg.setOpacity(0.6);
            bg.setFill(Color.BLACK);
            bg.setEffect(new GaussianBlur(3.5));

            setAlignment(Pos.CENTER_LEFT);
            setRotate(-0.5);
            getChildren().addAll(bg, text);

            setOnMouseEntered(event -> {
                bg.setTranslateX(10);
                text.setTranslateX(10);
                bg.setFill(Color.WHITE);
                text.setFill(Color.BLACK);
            });

            setOnMouseExited(event -> {
                bg.setTranslateX(0);
                text.setTranslateX(0);
                bg.setFill(Color.BLACK);
                text.setFill(Color.WHITE);
            });
            DropShadow drop = new DropShadow(50, Color.WHITE);
            drop.setInput(new Glow());

            setOnMousePressed(event -> setEffect(drop));
            setOnMouseReleased(event -> setEffect(null));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage, int l, KeyCode[] k) {
        var root = new Pane();
        var gameScene = new Scene(root);
        // Controllers for Pacman and ghosts
        var pacmanController = new PacmanController(k);
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);
        GhostsController[] ghostsController = {new ClydeController(), new PinkyController(),
                new BlinkyController()};
        for (var ghost: ghostsController) {ghost.startAI();}
        // Generate map from file
        var maze = new MazeState(ghostsController, MazeConfig.originalMaze(filename));
        maze.setLives(l);
        // Set up game window
        var gameView = new GameView(maze, root, 30.0);
        playBackgroundMusic();
        primaryStage.setScene(gameScene);
        primaryStage.show();
        gameView.animate();
    }
}