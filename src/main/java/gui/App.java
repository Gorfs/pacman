package gui;

import java.io.InputStream;

import config.Constants;
import controllers.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import config.MazeConfig;
import model.MazeState;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;


/**
 * Main Class of the game, it starts everything (game window, ghosts controller, pacman controller etc...).
 */
public class App extends Application {
    // Array of keycode used to move pacman
    private static final KeyCode[] keyCodes = {KeyCode.LEFT,KeyCode.RIGHT,KeyCode.UP,KeyCode.DOWN};
    // Button that allows to confirm username
    private static final SubmitButton nameSubmit = new SubmitButton("Submit");
    private static final MenuButton btnWest = new MenuButton("Left : Press a Key");
    private static final MenuButton btnEast = new MenuButton("Right : Press a Key");
    private static final MenuButton btnNorth = new MenuButton("Up : Press a Key");
    private static final MenuButton btnSouth = new MenuButton("Down : Press a Key");
    public static AnimationTimer animationTimer = null;
    // Register buttons
    private static final String[] keyBind = {null,null,null,null};
    private static Text LEFT_TEXT;
    private static Text RIGHT_TEXT;
    private static Text UP_TEXT;
    private static Text DOWN_TEXT;

    /**
     * Method used to bind a new key in the array of keycode
     * @param k array of keycode used to move pacman
     * @param n id of the key we change in keycode 'k'
     * @param key new value of the keycode
     */
    public static void bindKey(KeyCode[] k, int n, KeyCode key) {
            k[n]=key;
    }

    /**
     * @param primaryStage the primary stage for this application, onto which the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be primary stages.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialise the window content
        Pane root = new Pane();
        root.setPrefSize(Constants.WINDOW_X,Constants.WINDOW_Y);
        root.setStyle("-fx-background-color: #000000");

        // Initialise the background image
        InputStream is = getClass().getResourceAsStream("/pac.jpg");
        assert is != null;
        Image img = new Image(is);
        is.close();
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(Constants.WINDOW_X);
        imgView.setFitHeight(Constants.WINDOW_Y);
        imgView.setTranslateY(100);

        // Setup all the buttons
        btnWest.setVisible(false);
        btnEast.setVisible(false);
        btnNorth.setVisible(false);
        btnSouth.setVisible(false);
        nameSubmit.setVisible(false);

        if (keyCodes[0]!=null) LEFT_TEXT = new Text(KeyCodetoString(keyCodes[0]));
        LEFT_TEXT.setVisible(true);
        LEFT_TEXT.setFill(Color.WHITE);
        LEFT_TEXT.setStroke(Color.WHITE);
        LEFT_TEXT.setScaleX(2);
        LEFT_TEXT.setScaleY(2);

        if(keyCodes[1]!=null)RIGHT_TEXT = new Text(KeyCodetoString(keyCodes[1]));
        RIGHT_TEXT.setVisible(true);
        RIGHT_TEXT.setFill(Color.WHITE);
        RIGHT_TEXT.setStroke(Color.WHITE);
        RIGHT_TEXT.setScaleX(2);
        RIGHT_TEXT.setScaleY(2);

        if(keyCodes[2]!=null) UP_TEXT = new Text(KeyCodetoString(keyCodes[2]));
        UP_TEXT.setVisible(true);
        UP_TEXT.setFill(Color.WHITE);
        UP_TEXT.setStroke(Color.WHITE);
        UP_TEXT.setScaleX(2);
        UP_TEXT.setScaleY(2);

        if(keyCodes[3]!=null) DOWN_TEXT = new Text(KeyCodetoString(keyCodes[3]));
        DOWN_TEXT.setVisible(true);
        DOWN_TEXT.setFill(Color.WHITE);
        DOWN_TEXT.setStroke(Color.WHITE);
        DOWN_TEXT.setScaleX(2);
        DOWN_TEXT.setScaleY(2);

        OptionInGame gameMenu2 = new OptionInGame(LEFT_TEXT, RIGHT_TEXT, UP_TEXT, DOWN_TEXT,
                primaryStage, btnWest, btnEast, btnNorth, btnSouth);
        gameMenu2.setVisible(false);
        Menu menu = new Menu(LEFT_TEXT, RIGHT_TEXT, UP_TEXT, DOWN_TEXT, gameMenu2,
                primaryStage, keyCodes, nameSubmit, btnWest, btnEast, btnNorth, btnSouth);

        // Initialise menu buttons
        menu.setVisible(true);
        root.getChildren().addAll(imgView, btnWest, btnEast, btnNorth, btnSouth, menu, nameSubmit);
        Scene scene = getScene(root);

        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }

    /**
     * Setting up a new Scene on the root
     * @param root Window screen
     * @return a new Scene
     */
    private static Scene getScene(Pane root) {
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            if(btnWest.isVisible()){
                // If the button left is visible, get the next key pressed and bind it if it isn't forbidden
                if (event.getCode() != null && KeyForbidden(event.getCode())) {
                    if (keyName(event.getText()) != null) {
                        LEFT_TEXT.setText(keyName(event.getText()));
                        KeySpecial(keyBind, 0, event.getText());
                        keyCodes[0] = null;
                    } else if (event.getCode() != KeyCode.UNDEFINED) {
                        bindKey(keyCodes, 0, event.getCode());
                        LEFT_TEXT.setText(KeyCodetoString(keyCodes[0]));
                        keyBind[0] = null;
                    } btnWest.setVisible(false);
                }
            }
            if(btnEast.isVisible()){
                // If the button right is visible, get the next key pressed and bind it if it isn't forbidden
                if(event.getCode()!=null && KeyForbidden(event.getCode())){
                    if (keyName(event.getText()) != null) {
                        RIGHT_TEXT.setText(keyName(event.getText()));
                        KeySpecial(keyBind, 1, event.getText());
                        keyCodes[1] = null;
                    } else if (event.getCode() != KeyCode.UNDEFINED) {
                        bindKey(keyCodes, 1, event.getCode());
                        RIGHT_TEXT.setText(KeyCodetoString(keyCodes[1]));
                        keyBind[1] = null;
                    } btnEast.setVisible(false);
                }
            }
            if(btnNorth.isVisible()){
                // If the button up is visible, get the next key pressed and bind it if it isn't forbidden
                if (event.getCode() != null && KeyForbidden(event.getCode())) {
                    if (keyName(event.getText()) != null) {
                        UP_TEXT.setText(keyName(event.getText()));
                        KeySpecial(keyBind, 2, event.getText());
                        keyCodes[2] = null;
                    } else if (event.getCode() != KeyCode.UNDEFINED) {
                        bindKey(keyCodes, 2, event.getCode());
                        UP_TEXT.setText(KeyCodetoString(keyCodes[2]));
                        keyBind[2] = null;
                    } btnNorth.setVisible(false);
                }
            }
            if(btnSouth.isVisible()){
                // If the button down is visible, get the next key pressed and bind it if it isn't forbidden
                if (event.getCode() != null && KeyForbidden(event.getCode())) {
                    if (keyName(event.getText()) != null) {
                        DOWN_TEXT.setText(keyName(event.getText()));
                        KeySpecial(keyBind, 3, event.getText());
                        keyCodes[3]=null;
                    } else if (event.getCode()!=KeyCode.UNDEFINED) {
                        bindKey(keyCodes, 3, event.getCode());
                        DOWN_TEXT.setText(KeyCodetoString(keyCodes[3]));
                        keyBind[3] = null;
                    } btnSouth.setVisible(false);
                }
            }
        });
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }


    /**
     * @param key key to return as a string
     * @return the name of the key
     */
    public static String KeyCodetoString(KeyCode key){
        return key.toString();
    }

    /**
     * @param tab Where it stocks the name of the key
     * @param n index where it stocks
     * @param s string to stocks
     */
    public static void KeySpecial(String[] tab, int n, String s){
        switch (s) {
            case "é" -> tab[n] = "é";
            case "^" -> tab[n] = "^";
            case "è" -> tab[n] = "è";
            case "ç" -> tab[n] = "ç";
            case "à" -> tab[n] = "à";
            case "ù" -> tab[n] = "ù";
            case "&" -> tab[n] = "&";
            case "\"" -> tab[n] = "\"";
            case "'" -> tab[n] = "'";
            case "(" -> tab[n] = "(";
            case "-" -> tab[n] = "-";
            case "_" -> tab[n] = "_";
            case "1" -> tab[n] = "1";
            case "2" -> tab[n] = "2";
            case "3" -> tab[n] = "3";
            case "4" -> tab[n] = "4";
            case "5" -> tab[n] = "5";
            case "6" -> tab[n] = "6";
            case "7" -> tab[n] = "7";
            case "8" -> tab[n] = "8";
            case "9" -> tab[n] = "9";
            case "0" -> tab[n] = "0";
        }
    }

    public static String keyName(String s){
        return switch (s) {
            case "&", "1" -> "1";
            case "é", "2" -> "2";
            case "\"", "3" -> "3";
            case "'", "4" -> "4";
            case "(", "5" -> "5";
            case "-", "6" -> "6";
            case "è", "7" -> "7";
            case "_", "8" -> "8";
            case "ç", "9" -> "9";
            case "à", "0" -> "0";
            case "ù" -> "ù";
            default -> null;
        };
    }

    /**
     * @param key check if it is a forbidden key
     * @return boolean
     */
    public static boolean KeyForbidden(KeyCode key){
        return key != KeyCode.ACCEPT && key != KeyCode.ALT && key != KeyCode.F1 && key != KeyCode.F2
                && key != KeyCode.F3 && key != KeyCode.F4 && key != KeyCode.F5 && key != KeyCode.F6
                && key != KeyCode.F7 && key != KeyCode.F8 && key != KeyCode.F9 && key != KeyCode.F10
                && key != KeyCode.F11 && key != KeyCode.F12 && key != KeyCode.F13 && key != KeyCode.F14
                && key != KeyCode.F15 && key != KeyCode.F16 && key != KeyCode.F17 && key != KeyCode.F18
                && key != KeyCode.F19 && key != KeyCode.F20 && key != KeyCode.F21 && key != KeyCode.F22
                && key != KeyCode.F23 && key != KeyCode.F24 && key != KeyCode.SHIFT && key != KeyCode.CAPS
                && key != KeyCode.ALT_GRAPH && key != KeyCode.BACK_SPACE && key != KeyCode.BEGIN
                && key != KeyCode.CANCEL && key != KeyCode.CLEAR && key != KeyCode.DELETE && key != KeyCode.ENTER
                && key != KeyCode.ESCAPE && key != KeyCode.NUM_LOCK && key != KeyCode.TAB && key != KeyCode.CONTROL;
    }

    /**
     * @param primaryStage the primary stage for this application, onto which the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be primary stages.
     * @param live number of lives
     * @param keyCodes array of keycode used to move pacman
     */
    public static void start(Stage primaryStage, int live, KeyCode[] keyCodes, String mapChoice) throws Exception {
        var root = new Pane();
        var gameScene = new Scene(root);

        OptionInGame optionInGame = new OptionInGame(LEFT_TEXT, RIGHT_TEXT, UP_TEXT,
                DOWN_TEXT, primaryStage, btnWest, btnEast, btnNorth, btnSouth);
        // Initialise in game's options.
        optionInGame.setVisible(false);

        // Controllers for ghosts
        GhostsController[] ghostsController = {new ClydeController(), new PinkyController(),
                new BlinkyController(), new InkyController()};
        for (var ghost: ghostsController) {ghost.startAI();}

        // Generate selected map from file
        var maze = new MazeState(ghostsController, MazeConfig.originalMaze(mapChoice), optionInGame);
        Constants.WINDOW_X = (int) (maze.getWidth() * Constants.SCALE);
        Constants.WINDOW_Y = maze.getHeight() * Constants.SCALE;
        root.setPrefSize(Constants.WINDOW_X, Constants.WINDOW_Y);
        maze.setLives(live);

        // Controller for PacMan
        var pacmanController = new PacmanController(keyBind, keyCodes, LEFT_TEXT, RIGHT_TEXT, UP_TEXT,
                DOWN_TEXT, optionInGame, root, btnWest, btnEast, btnNorth, btnSouth, maze);

        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);

        // Set up game window
        var gameView = new GameView(maze, root, Constants.SCALE);
        Music.playBackgroundMusic();
        primaryStage.setScene(gameScene);
        primaryStage.show();
        animationTimer = gameView.animate();
        animationTimer.start();
    }
}
