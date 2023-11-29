package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import config.MazeConfig;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import model.ClydeController;
import model.MazeState;


public class App extends Application {

    private static KeyCode[] k = {KeyCode.LEFT,KeyCode.RIGHT,KeyCode.UP,KeyCode.DOWN};//tableau qui permet de modifier les touches
    private static String[] touche = {null,null,null,null};//tableau qui permet de modifier les touches
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
    private Text LEFT;
    //texte qui affiche à côté du bouton LEFT la valeur
    private Text RIGHT;
    //texte qui affiche à côté du bouton RIGHT la valeur
    private Text UP;
    //texte qui affiche à côté du bouton UP la valeur
    private Text DOWN;
    //texte qui affiche à côté du bouton DOWN la valeur
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

        LEFT = new Text(KeyCodetoString(k[0]));//on lui assigne la valeur par default
        LEFT.setVisible(true);//on la met visible pour qu'elle s'affiche une fois dans les options de touches
        LEFT.setFill(Color.WHITE);//on met la couleur du texte en blanc
        LEFT.setStroke(Color.WHITE);//on met des bordure en blancs
        LEFT.setScaleX(2);//on augmente la taille
        LEFT.setScaleY(2);

        RIGHT = new Text(KeyCodetoString(k[1]));
        RIGHT.setVisible(true);
        RIGHT.setFill(Color.WHITE);
        RIGHT.setStroke(Color.WHITE);
        RIGHT.setScaleX(2);
        RIGHT.setScaleY(2);

        UP = new Text(KeyCodetoString(k[2]));
        UP.setVisible(true);
        UP.setFill(Color.WHITE);
        UP.setStroke(Color.WHITE);
        UP.setScaleX(2);
        UP.setScaleY(2);

        DOWN = new Text(KeyCodetoString(k[3]));
        DOWN.setVisible(true);
        DOWN.setFill(Color.WHITE);
        DOWN.setStroke(Color.WHITE);
        DOWN.setScaleX(2);
        DOWN.setScaleY(2);

        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(630);//image au dimensions de l'écran
        imgView.setFitHeight(350);
        imgView.setTranslateY(100);
        GameMenu2 gameMenu2 = new GameMenu2(LEFT, RIGHT, UP, DOWN, primaryStage, root, k, button, btncase1, btncase2, btncase3, btncase4, son_effect);
        gameMenu2.setVisible(false);
        gameMenu = new GameMenu(LEFT, RIGHT, UP, DOWN, gameMenu2, root, primaryStage,k,button,btncase1,btncase2,btncase3,btncase4,son_effect, 800, 600);
        //on initialse les boutons dans le menu
        gameMenu.setVisible(true);
        root.getChildren().addAll(imgView, btncase1, btncase2, btncase3, btncase4, gameMenu, button);
        //on met tout dans l'affichage de la fenêtre
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            if(btncase1.isVisible()){
                //si le bouton est visible, donc que on a cliqué sur LEFT dans options, alors la prochaine touche sera incrémenter dans k si elle n'est pas interdite
                if(event.getCode()!=null && KeyForbidden(event.getCode())){
                    System.out.println(event.getText());
                    if(keyName(event.getText())!=null){LEFT.setText(keyName(event.getText()));KeySpecial(touche, 0, event.getText());k[0]=null;}
                    //si la touche fait partie des touches non lisible par KeyCode, alors on le transforme en texte pour apres l'afficher et le mettre dans le tableau touche
                    else if(event.getCode()!=KeyCode.UNDEFINED){tab(k, 0, event.getCode()); LEFT.setText(KeyCodetoString(k[0])); touche[0]=null;}
                    //sinon on met la valeur KeyCode dans k, et on l'affiche
                    btncase1.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                }
            }
            if(btncase2.isVisible()){
                //si le bouton est visible, donc que on a cliqué sur RIGHT dans options, alors la prochaine touche sera incrémenter dans k
                if(event.getCode()!=null && KeyForbidden(event.getCode())){
                    System.out.println(event.getText());
                    if(keyName(event.getText())!=null){RIGHT.setText(keyName(event.getText()));KeySpecial(touche, 1, event.getText());k[1]=null;}
                    else if(event.getCode()!=KeyCode.UNDEFINED){tab(k, 1, event.getCode()); RIGHT.setText(KeyCodetoString(k[1])); touche[1]=null;}
                    btncase2.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                }
            }
            if(btncase3.isVisible()){
                //si le bouton est visible, donc que on a cliqué sur RIGHT dans options, alors la prochaine touche sera incrémenter dans k
                if(event.getCode()!=null && KeyForbidden(event.getCode())){
                    System.out.println(event.getText());
                    if(keyName(event.getText())!=null){UP.setText(keyName(event.getText()));KeySpecial(touche, 2, event.getText());k[2]=null;}
                    else if(event.getCode()!=KeyCode.UNDEFINED){tab(k, 2, event.getCode()); UP.setText(KeyCodetoString(k[2])); touche[2]=null;}
                    btncase3.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                }
            }
            if(btncase4.isVisible()){
                //si le bouton est visible, donc que on a cliqué sur RIGHT dans options, alors la prochaine touche sera incrémenter dans k
                if(event.getCode()!=null && KeyForbidden(event.getCode())){
                    System.out.println(event.getText());
                    if(keyName(event.getText())!=null){DOWN.setText(keyName(event.getText()));KeySpecial(touche, 3, event.getText());k[3]=null;}
                    else  if(event.getCode()!=KeyCode.UNDEFINED){tab(k, 3, event.getCode()); DOWN.setText(KeyCodetoString(k[3])); touche[3]=null;}
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

    public static String KeyCodetoString(KeyCode key){//on recupere le String pour ensuite afficher la valeur de key
        String letter;
        letter = key.toString();
        return letter;
    }

    public static void KeySpecial(String[] tab, int n, String s){
        if(s.equals("é")){tab[n]="é";}
        else if(s.equals("^")){tab[n]="^";}
        else if(s.equals("è")){tab[n]="è";}
        else if(s.equals("ç")){tab[n]="ç";}
        else if(s.equals("à")){tab[n]="à";}
        else if(s.equals("ù")){tab[n]="ù";}
        else if(s.equals("&")){tab[n]="&";}
        else if(s.equals("\"")){tab[n]="\"";}
        else if(s.equals("'")){tab[n]="'";}
        else if(s.equals("(")){tab[n]="(";}
        else if(s.equals("-")){tab[n]="-";}
        else if(s.equals("_")){tab[n]="_";}
    }

    public static String keyName(String s){
        if(s.equals("é")){return "2";}
        else if(s.equals("è")){return "7";}
        else if(s.equals("ç")){return "9";}
        else if(s.equals("à")){return "0";}
        else if(s.equals("ù")){return "ù";}
        else if(s.equals("&")){return "1";}
        else if(s.equals("'")){return "4";}
        else if(s.equals("(")){return "5";}
        else if(s.equals("-")){return "6";}
        else if(s.equals("_")){return "8";}
        else if(s.equals("\"")){return "3";}
        else return null;
    }

    public static String KeySpecial(KeyCode key){
        if(key==KeyCode.AMPERSAND)return "1";
        if(key==KeyCode.QUOTEDBL)return "3";
        if(key==KeyCode.QUOTE)return "4";
        if(key==KeyCode.LEFT_PARENTHESIS)return "5";
        if(key==KeyCode.MINUS)return "6";
        if(key==KeyCode.UNDERSCORE)return "8";
        //if(key==KeyCode.UNDEFINED)return "2, 7, 9, 0" a discuter avec le prof
        else return null;
    }

    public boolean KeyForbidden(KeyCode key){
        if(key==KeyCode.ACCEPT || key==KeyCode.ALT || key==KeyCode.F1 || key==KeyCode.F2 || key==KeyCode.F3 || key==KeyCode.F4 || key==KeyCode.F5
        || key==KeyCode.F6 || key==KeyCode.F7 || key==KeyCode.F8 || key==KeyCode.F9 || key==KeyCode.F10 || key==KeyCode.F11 || key==KeyCode.F12
        || key==KeyCode.F13 || key==KeyCode.F14 || key==KeyCode.F15 || key==KeyCode.F16 || key==KeyCode.F17 || key==KeyCode.F18 || key==KeyCode.F19
        || key==KeyCode.F20 || key==KeyCode.F21 || key==KeyCode.F22 || key==KeyCode.F23 || key==KeyCode.F24 || key==KeyCode.SHIFT || key==KeyCode.CAPS
        || key==KeyCode.ALT_GRAPH || key==KeyCode.BACK_SPACE || key==KeyCode.BEGIN || key==KeyCode.CANCEL || key==KeyCode.CLEAR || key==KeyCode.DELETE
        || key==KeyCode.ESCAPE || key==KeyCode.NUM_LOCK || key==KeyCode.TAB || key==KeyCode.CONTROL || key==KeyCode.ENTER
        ){
            //les touches typiquement francaise du style é, ç etc... ne sont pas reconnues par la classe KeyCode
            return false;
        }
        return true;
    }

    public static void start(Stage primaryStage, int l, KeyCode[] k, float son_effect, Text LEFT, Text RIGHT, Text UP, Text DOWN) {
        var root = new Pane();
        root.setPrefSize(630,630);//on incrémente les dimensions de l'écran dans la fenêtre
        var gameScene = new Scene(root);
        GameMenu2 gameMenu2 = new GameMenu2(LEFT, RIGHT, UP, DOWN, primaryStage, root, k, button, btncase1, btncase2, btncase3, btncase4, son_effect);
        //on initialise les options in-game
        gameMenu2.setVisible(false);
        var pacmanController = new PacmanController(touche, LEFT, RIGHT, UP, DOWN, k, gameMenu2, root, button, btncase1, btncase2, btncase3, btncase4);
        //on initialise la classe qui gère les touches pressées in-game
        var clydeController = new ClydeController();
        clydeController.startAI();
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);
        var maze = new MazeState(MazeConfig.originalMaze("maze2"), gameMenu2);
        maze.setLives(l);
        var gameView = new GameView(maze, root, 30.0);
        Music.playBackgroundMusic();
        primaryStage.setScene(gameScene);
        primaryStage.show();
        gameView.animate();
    }
        
    
}
      


