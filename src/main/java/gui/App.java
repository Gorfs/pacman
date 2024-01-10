package gui;

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

import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;


/**
 * Main Class of the game, it starts everything (game window, ghosts controller, pacman controller etc...).
 */
public class App extends Application {
    // array of keycode used to move pacman
    private static final KeyCode[] keyCodes = {KeyCode.LEFT,KeyCode.RIGHT,KeyCode.UP,KeyCode.DOWN};
    //bouton qui permet de confirmer le pseudo
    private static final SubmitButton nameSubmit = new SubmitButton("Submit");
    //message qui s'affiche une fois qu'on appuie sur le bouton LEFT dans les options
    private static final MenuButton btnWest = new MenuButton("Left : Press a Key");
    //message qui s'affiche une fois qu'on appuie sur le bouton RIGHT dans les options
    private static final MenuButton btnEast = new MenuButton("Right : Press a Key");
    //message qui s'affiche une fois qu'on appuie sur le bouton dans UP options
    private static final MenuButton btnNorth = new MenuButton("Up : Press a Key");
    //message qui s'affiche une fois qu'on appuie sur le bouton dans DOWN options
    private static final MenuButton btnSouth = new MenuButton("Down : Press a Key");
    public static AnimationTimer animationTimer = null;
    //tableau qui permet de modifier les touches
    private static final String[] touches = {null,null,null,null};
    private static Text LEFT;
    //texte qui affiche à côté du bouton LEFT la valeur
    private static Text RIGHT;
    //texte qui affiche à côté du bouton RIGHT la valeur
    private static Text UP;
    //texte qui affiche à côté du bouton UP la valeur
    private static Text DOWN;
    //texte qui affiche à côté du bouton DOWN la valeur

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
        InputStream is = getClass().getResourceAsStream("/pac.jpg");
        assert is != null;
        Image img = new Image(is);
        is.close();

        // on met tous les boutons non visibles au debut sauf options, exit et play
        btnWest.setVisible(false);
        btnEast.setVisible(false);
        btnNorth.setVisible(false);
        btnSouth.setVisible(false);
        nameSubmit.setVisible(false);

        if(keyCodes[0]!=null){
            LEFT = new Text(KeyCodetoString(keyCodes[0]));//on lui assigne la valeur par default
        }
        LEFT.setVisible(true);//on la met visible pour qu'elle s'affiche une fois dans les options de touches
        LEFT.setFill(Color.WHITE);//on met la couleur du texte en blanc
        LEFT.setStroke(Color.WHITE);//on met des bordure en blancs
        LEFT.setScaleX(2);//on augmente la taille
        LEFT.setScaleY(2);

        if(keyCodes[1]!=null){
            RIGHT = new Text(KeyCodetoString(keyCodes[1]));
        }
        RIGHT.setVisible(true);
        RIGHT.setFill(Color.WHITE);
        RIGHT.setStroke(Color.WHITE);
        RIGHT.setScaleX(2);
        RIGHT.setScaleY(2);

        if(keyCodes[2]!=null){
            UP = new Text(KeyCodetoString(keyCodes[2]));
        }
        UP.setVisible(true);
        UP.setFill(Color.WHITE);
        UP.setStroke(Color.WHITE);
        UP.setScaleX(2);
        UP.setScaleY(2);

        if(keyCodes[3]!=null){
            DOWN = new Text(KeyCodetoString(keyCodes[3]));
        }
        DOWN.setVisible(true);
        DOWN.setFill(Color.WHITE);
        DOWN.setStroke(Color.WHITE);
        DOWN.setScaleX(2);
        DOWN.setScaleY(2);

        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(Constants.WINDOW_X); //image aux dimensions de l'écran
        imgView.setFitHeight(Constants.WINDOW_Y);
        imgView.setTranslateY(100);
        OptionInGame gameMenu2 = new OptionInGame(LEFT, RIGHT, UP, DOWN, primaryStage, root, keyCodes, btnWest, btnEast, btnNorth, btnSouth);
        gameMenu2.setVisible(false);
        Menu menu = new Menu(LEFT, RIGHT, UP, DOWN, gameMenu2, root, primaryStage,keyCodes,nameSubmit,btnWest,btnEast,btnNorth,btnSouth);
        //on initialise les boutons dans le menu
        menu.setVisible(true);
        root.getChildren().addAll(imgView, btnWest, btnEast, btnNorth, btnSouth, menu, nameSubmit);
        // on met tout dans l'affichage de la fenêtre
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            if(btnWest.isVisible()){
                //si le bouton est visible, donc qu'on a cliqué sur LEFT dans les options, alors la prochaine touche sera incrémenter dans k si elle n'est pas interdite
                if(event.getCode()!=null && KeyForbidden(event.getCode())){
                    System.out.println(event.getText());
                    if(keyName(event.getText())!=null){LEFT.setText(keyName(event.getText()));KeySpecial(touches, 0, event.getText());keyCodes[0]=null;}
                    //si la touche fait partie des touches non lisible par KeyCode, alors on le transforme en texte pour apres l'afficher et le mettre dans le tableau touche
                    else if(event.getCode()!=KeyCode.UNDEFINED){bindKey(keyCodes, 0, event.getCode()); LEFT.setText(KeyCodetoString(keyCodes[0])); touches[0]=null;}
                    //sinon on met la valeur KeyCode dans k, et on l'affiche
                    btnWest.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                }
            }
            if(btnEast.isVisible()){
                //si le bouton est visible, donc que on a cliqué sur RIGHT dans options, alors la prochaine touche sera incrémenter dans k
                if(event.getCode()!=null && KeyForbidden(event.getCode())){
                    System.out.println(event.getText());
                    if(keyName(event.getText())!=null){RIGHT.setText(keyName(event.getText()));KeySpecial(touches, 1, event.getText());keyCodes[1]=null;}
                    else if(event.getCode()!=KeyCode.UNDEFINED){bindKey(keyCodes, 1, event.getCode()); RIGHT.setText(KeyCodetoString(keyCodes[1])); touches[1]=null;}
                    btnEast.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                }
            }
            if(btnNorth.isVisible()){
                //si le bouton est visible, donc que on a cliqué sur RIGHT dans options, alors la prochaine touche sera incrémenter dans k
                if(event.getCode()!=null && KeyForbidden(event.getCode())){
                    System.out.println(event.getText());
                    if(keyName(event.getText())!=null){UP.setText(keyName(event.getText()));KeySpecial(touches, 2, event.getText());keyCodes[2]=null;}
                    else if(event.getCode()!=KeyCode.UNDEFINED){bindKey(keyCodes, 2, event.getCode()); UP.setText(KeyCodetoString(keyCodes[2])); touches[2]=null;}
                    btnNorth.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                }
            }
            if(btnSouth.isVisible()){
                //si le bouton est visible, donc que on a cliqué sur RIGHT dans options, alors la prochaine touche sera incrémenter dans k
                if(event.getCode()!=null && KeyForbidden(event.getCode())){
                    System.out.println(event.getText());
                    if(keyName(event.getText())!=null){DOWN.setText(keyName(event.getText()));KeySpecial(touches, 3, event.getText());keyCodes[3]=null;}
                    else  if(event.getCode()!=KeyCode.UNDEFINED){bindKey(keyCodes, 3, event.getCode()); DOWN.setText(KeyCodetoString(keyCodes[3])); touches[3]=null;}
                    btnSouth.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                }
            }
        });

        primaryStage.setScene(scene);//on met la scene sur le stage
        primaryStage.show();//on affiche le menu
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static String KeyCodetoString(KeyCode key){//on recupere le String pour ensuite afficher la valeur de key
        String letter;
        letter = key.toString();
        return letter;
    }

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
            case "é", "2" -> "2";
            case "è", "7" -> "7";
            case "ç", "9" -> "9";
            case "à", "0" -> "0";
            case "ù" -> "ù";
            case "&", "1" -> "1";
            case "'", "4" -> "4";
            case "(", "5" -> "5";
            case "-", "6" -> "6";
            case "_", "8" -> "8";
            case "\"", "3" -> "3";
            default -> null;
        };
    }

    public static boolean KeyForbidden(KeyCode key){
        return key != KeyCode.ACCEPT && key != KeyCode.ALT && key != KeyCode.F1 && key != KeyCode.F2 && key != KeyCode.F3 && key != KeyCode.F4 && key != KeyCode.F5
                && key != KeyCode.F6 && key != KeyCode.F7 && key != KeyCode.F8 && key != KeyCode.F9 && key != KeyCode.F10 && key != KeyCode.F11 && key != KeyCode.F12
                && key != KeyCode.F13 && key != KeyCode.F14 && key != KeyCode.F15 && key != KeyCode.F16 && key != KeyCode.F17 && key != KeyCode.F18 && key != KeyCode.F19
                && key != KeyCode.F20 && key != KeyCode.F21 && key != KeyCode.F22 && key != KeyCode.F23 && key != KeyCode.F24 && key != KeyCode.SHIFT && key != KeyCode.CAPS
                && key != KeyCode.ALT_GRAPH && key != KeyCode.BACK_SPACE && key != KeyCode.BEGIN && key != KeyCode.CANCEL && key != KeyCode.CLEAR && key != KeyCode.DELETE
                && key != KeyCode.ESCAPE && key != KeyCode.NUM_LOCK && key != KeyCode.TAB && key != KeyCode.CONTROL && key != KeyCode.ENTER;
    }

    /**
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @param live number of lives
     * @param keyCodes array of keycode used to move pacman
     */
    public static void start(Stage primaryStage, int live, KeyCode[] keyCodes, String choix_map) throws Exception {
        var root = new Pane();
        var gameScene = new Scene(root);
        OptionInGame gameMenu2 = new OptionInGame(LEFT, RIGHT, UP, DOWN, primaryStage, root, keyCodes, btnWest, btnEast, btnNorth, btnSouth);
        // Initialise in game's options.
        gameMenu2.setVisible(false);

        // Controllers for ghosts
        GhostsController[] ghostsController = {new ClydeController(), new PinkyController(),
                new BlinkyController(), new InkyController()};
        for (var ghost: ghostsController) {ghost.startAI();}

        // Generate map from file
        //choix des maps
        var maze = new MazeState(ghostsController, MazeConfig.originalMaze(choix_map), gameMenu2);
        Constants.WINDOW_X = (int) (maze.getWidth() * Constants.SCALE);
        Constants.WINDOW_Y = maze.getHeight() * Constants.SCALE;
        root.setPrefSize(Constants.WINDOW_X, Constants.WINDOW_Y);
        maze.setLives(live);

        // Controller for PacMan
        var pacmanController = new PacmanController(touches, keyCodes, LEFT, RIGHT, UP, DOWN, gameMenu2, root, btnWest, btnEast, btnNorth, btnSouth, maze);
        //on initialise la classe qui gère les touches pressées in-game
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        // Currently doing nothing, so I commented it.
        // gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);

        // Set up game window
        var gameView = new GameView(maze, root, Constants.SCALE);
        Music.playBackgroundMusic();
        primaryStage.setScene(gameScene);
        primaryStage.show();
        animationTimer = gameView.animate();
        animationTimer.start();
    }
}
