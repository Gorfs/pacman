package gui;

import config.Constants;
import controllers.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import config.MazeConfig;

import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import model.MazeState;


/**
 * Main Class of the game, it starts everything (game window, ghosts controller, pacman controller etc...).
 */
public class App extends Application {
    // array of keycode used to move pacman
    private static final KeyCode[] keyCodes = {KeyCode.LEFT,KeyCode.RIGHT,KeyCode.UP,KeyCode.DOWN};
    //bouton qui permet de confirmer le pseudo
    private static final MenuButton2 nameSubmit = new MenuButton2("Submit");
    //message qui s'affiche une fois qu'on appuie sur le bouton LEFT dans les options
    private static final MenuButton btnWest = new MenuButton("Left : Press a Key");
    //message qui s'affiche une fois qu'on appuie sur le bouton RIGHT dans les options
    private static final MenuButton btnEast = new MenuButton("Right : Press a Key");
    //message qui s'affiche une fois qu'on appuie sur le bouton dans UP options
    private static final MenuButton btnNorth = new MenuButton("Up : Press a Key");
    //message qui s'affiche une fois qu'on appuie sur le bouton dans DOWN options
    private static final MenuButton btnSouth = new MenuButton("Down : Press a Key");

    /**
     * Method used to bind a new key in the array of keycode
     * @param k array of keycode used to move pacman
     * @param n id of the key we change in keycode 'k'
     * @param key new value of the keycode
     */
    public static void bindKey(KeyCode[] k, int n, KeyCode key){//fonction qui permet d'intégrer un KeyCode dans le tableau keyCodes
            k[n]=key;
    }

    /**
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // on initialise la fenêtre
        Pane root = new Pane();
        // on incrémente les dimensions de l'écran dans la fenêtre
        root.setPrefSize(Constants.WINDOW_X,Constants.WINDOW_Y);
        root.setStyle("-fx-background-color: #000000");

        // on prend une image située dans les ressources
        InputStream is = getClass().getResourceAsStream("/pac.jpg");//on prend une image située dans les ressources
        assert is != null;
        Image img = new Image(is);
        is.close();

        // on met tous les boutons non visibles au debut sauf options, exit et play
        btnWest.setVisible(false);
        btnEast.setVisible(false);
        btnNorth.setVisible(false);
        btnSouth.setVisible(false);
        nameSubmit.setVisible(false);

        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(Constants.WINDOW_X); //image aux dimensions de l'écran
        imgView.setFitHeight(Constants.WINDOW_Y);
        imgView.setTranslateY(100);
        GameMenu2 gameMenu2 = new GameMenu2(root, keyCodes, nameSubmit, btnWest, btnEast, btnNorth, btnSouth);
        gameMenu2.setVisible(false);
        // Les boutons dans le menu, option et pour jouer
        GameMenu gameMenu = new GameMenu(gameMenu2, root, primaryStage, keyCodes, nameSubmit, btnWest, btnEast, btnNorth, btnSouth);
        //on initiale les boutons dans le menu
        gameMenu.setVisible(true);
        root.getChildren().addAll(imgView, btnWest, btnEast, btnNorth, btnSouth, gameMenu, nameSubmit);
        // on met tout dans l'affichage de la fenêtre
        Scene scene = getScene(root);

        primaryStage.setScene(scene);//on met la scene sur le stage
        primaryStage.show();//on affiche le menu
    }

    /**
     * @param root the scene we need to update
     * @return the scene once created
     */
    private Scene getScene(Pane root) {
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            if(btnWest.isVisible()){
                // si le bouton est visible, donc qu'on a cliqué sur WEST dans les options,
                // alors la prochaine touche sera incrémenter dans 'keyCodes'
                if(event.getCode()!=null){
                    bindKey(keyCodes, 0, event.getCode());
                    btnWest.setVisible(false);// une fois que le bouton est appuyé, on l'enlève
                }
            }
            if(btnEast.isVisible()){
                // si le bouton est visible, donc qu'on a cliqué sur EAST dans les options,
                // alors la prochaine touche sera incrémenté dans 'keyCodes'
                if(event.getCode()!=null){
                    bindKey(keyCodes, 1, event.getCode());
                    btnEast.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                }
            }
            if(btnNorth.isVisible()){
                // si le bouton est visible, donc qu'on a cliqué sur NORTH dans les options,
                // alors la prochaine touche sera incrémenter dans 'keyCodes'
                if(event.getCode()!=null){
                    bindKey(keyCodes, 2, event.getCode());
                    btnNorth.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                }
            }
            if(btnSouth.isVisible()){
                // si le bouton est visible, donc qu'on a cliqué sur SOUTH dans les options,
                // alors la prochaine touche sera incrémenter dans 'keyCodes'
                if(event.getCode()!=null){
                    bindKey(keyCodes, 3, event.getCode());
                    btnSouth.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                }
            }
        });
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @param live number of lives
     * @param keyCodes array of keycode used to move pacman
     */
    public static void start(Stage primaryStage, int live, KeyCode[] keyCodes) throws Exception {
        var root = new Pane();
        var gameScene = new Scene(root);
        GameMenu2 gameMenu2 = new GameMenu2(root, keyCodes, nameSubmit, btnWest, btnEast, btnNorth, btnSouth);

        // Initialise in game's options.
        gameMenu2.setVisible(false);

        // Controllers for ghosts
        GhostsController[] ghostsController = {new ClydeController(), new PinkyController(),
                new BlinkyController(), new InkyController()};
        for (var ghost: ghostsController) {ghost.startAI();}

        // Generate map from file
        var maze = new MazeState(ghostsController, MazeConfig.originalMaze("maze2"), gameMenu2);
        Constants.WINDOW_X = (int) (maze.getWidth() * Constants.SCALE);
        Constants.WINDOW_Y = maze.getHeight() * Constants.SCALE;
        root.setPrefSize(Constants.WINDOW_X, Constants.WINDOW_Y);
        maze.setLives(live);

        // Controller for PacMan
        var pacmanController = new PacmanController(keyCodes, gameMenu2, root, btnWest, btnEast, btnNorth, btnSouth, maze);
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        // Currently doing nothing, so I commented it.
        // gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);

        // Set up game window
        var gameView = new GameView(maze, root, Constants.SCALE);
        Music.playBackgroundMusic();
        primaryStage.setScene(gameScene);
        primaryStage.show();
        gameView.animate();
    }
}