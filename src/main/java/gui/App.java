package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import config.MazeConfig;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import model.MazeState;


public class App extends Application {

    private static KeyCode[] k = {KeyCode.LEFT,KeyCode.RIGHT,KeyCode.UP,KeyCode.DOWN};//tableau qui permet de modifier les touches
    private static GameMenu gameMenu;//Les boutons dans le menu, option et pour jouer
    private static float son_effect=1;//pour regler le son
    private static MenuButton2 button = new MenuButton2("Submit");//bouton qui permet de confirmer le pseudo
    private static MenuButton btncase1 = new MenuButton("Left : Press a Key");
    //message qui s'affiche une fois qu'on appuie sur le bouton LEFT dans options
    private static MenuButton btncase2 = new MenuButton("Right : Press a Key");
    //message qui s'affiche une fois qu'on appuie sur le bouton RIGHT dans options
    private static MenuButton btncase3 = new MenuButton("Up : Press a Key");
    //message qui s'affiche une fois qu'on appuie sur le bouton dans UP options
    private static MenuButton btncase4 = new MenuButton("Down : Press a Key");
    //message qui s'affiche une fois qu'on appuie sur le bouton dans DOWN options

    public static void tab(KeyCode [] k, int n, KeyCode key){//fonction qui permet d'intégrer un KeyCode dans le tableau k
            k[n]=key;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane root = new Pane();//on initialise la fenêtre
        root.setPrefSize(630,630);//on incrémente les dimensions de l'écran dans la fenêtre
        root.setStyle("-fx-background-color: #000000");
        InputStream is = Files.newInputStream(Paths.get("src/main/resources/pac.jpg"));//on prends une image situé dans ressources
        Image img = new Image(is);
        is.close();
        btncase1.setVisible(false);//on met tout les boutons non visible au debut sauf options, exit et play
        btncase2.setVisible(false);
        btncase3.setVisible(false);
        btncase4.setVisible(false);
        button.setVisible(false);
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(630);//image au dimensions de l'écran
        imgView.setFitHeight(350);
        imgView.setTranslateY(100);
        GameMenu2 gameMenu2 = new GameMenu2(root, k, button, btncase1, btncase2, btncase3, btncase4, son_effect);
        gameMenu2.setVisible(false);
        gameMenu = new GameMenu(gameMenu2, root, primaryStage,k,button,btncase1,btncase2,btncase3,btncase4,son_effect, 800, 600);
        //on initialse les boutons dans le menu
        gameMenu.setVisible(true);
        root.getChildren().addAll(imgView, btncase1, btncase2, btncase3, btncase4, gameMenu, button);
        //on met tout dans l'affichage de la fenêtre
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            if(btncase1.isVisible()){
                //si le bouton est visible, donc que on a cliqué sur LEFT dans options, alors la prochaine touche sera incrémenter dans k
                if(event.getCode()!=null){
                    tab(k, 0, event.getCode());
                    btncase1.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                }
            }
            if(btncase2.isVisible()){
                //si le bouton est visible, donc que on a cliqué sur RIGHT dans options, alors la prochaine touche sera incrémenter dans k
                if(event.getCode()!=null){
                    tab(k, 1, event.getCode());
                    btncase2.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                }
            }
            if(btncase3.isVisible()){
                //si le bouton est visible, donc que on a cliqué sur RIGHT dans options, alors la prochaine touche sera incrémenter dans k
                if(event.getCode()!=null){
                    tab(k, 2, event.getCode());
                    btncase3.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                }
            }
            if(btncase4.isVisible()){
                //si le bouton est visible, donc que on a cliqué sur RIGHT dans options, alors la prochaine touche sera incrémenter dans k
                if(event.getCode()!=null){
                    tab(k, 3, event.getCode());
                    btncase4.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                }
            }
        });

        primaryStage.setScene(scene);//on met la scene sur le stage
        primaryStage.show();//on affiche le menu
    }

    public static void main(String[] args) {
        launch(args);
    }
    public static void start(Stage primaryStage, int l, KeyCode[] k, float son_effect) {
        var root = new Pane();
        root.setPrefSize(630,630);//on incrémente les dimensions de l'écran dans la fenêtre
        var gameScene = new Scene(root);
        GameMenu2 gameMenu2 = new GameMenu2(root, k, button, btncase1, btncase2, btncase3, btncase4, son_effect);
        //on initialise les options in-game
        gameMenu2.setVisible(false);
        // Controllers for Pacman and ghosts
        var pacmanController = new PacmanController(k, gameMenu2, root, button, btncase1, btncase2, btncase3, btncase4);
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);
        GhostsController[] ghostsController = {new ClydeController(), new PinkyController(),
                new BlinkyController(), new InkyController()};
        for (var ghost: ghostsController) {ghost.startAI();}
        var maze = new MazeState(ghostsController, MazeConfig.originalMaze("maze2"), gameMenu2);
        // Generate map from file
        maze.setLives(l);
        // Set up game window
        var gameView = new GameView(maze, root, 30.0);
        Music.playBackgroundMusic();
        primaryStage.setScene(gameScene);
        primaryStage.show();
        gameView.animate();
    }
}