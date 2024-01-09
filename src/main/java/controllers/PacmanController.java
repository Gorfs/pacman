package controllers;

import gui.OptionInGame;
import gui.MenuButton;
import gui.App;
import gui.Music;
import model.Direction;
import model.MazeState;
import model.PacMan;

import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import javafx.scene.text.Text;

/**
 * Class PacmanController is used to check all the keyboard input while in the game.
 */
public class PacmanController {
    //class qui gère les touches préssées in-game
    private static KeyCode lastKeyCode;
    private static KeyCode[] keyCodes = {KeyCode.LEFT,KeyCode.RIGHT,KeyCode.UP,KeyCode.DOWN};
    private final MenuButton btnWest;
    private final MenuButton btnEast;
    private final MenuButton btnNorth;
    private final MenuButton btnSouth;
    private final OptionInGame optionMenu;
    private final MazeState state;
    private final Pane root;
    private static String[] touches = {null,null,null,null};
    private static Text LEFT;
    private static Text RIGHT;
    private static Text UP;
    private static Text DOWN;

    /**
     * Constructor method that initialize PacmanController.
     * @param keyCodes KeyCode used to move pacman
     * @param optionMenu variable used to display option menu in the game
     * @param root window of the game
     * @param btnWest button used to change keycode to go west in the option
     * @param btnEast button used to change keycode to go east in the option
     * @param btnNorth button used to change keycode to go north in the option
     * @param btnSouth button used to change keycode to go south in the option
    */
    public PacmanController(String[] touche, KeyCode[] keyCodes, Text left, Text right, Text up, Text down, OptionInGame optionMenu, Pane root, MenuButton btnWest,
                            MenuButton btnEast, MenuButton btnNorth, MenuButton btnSouth, MazeState state){
        PacmanController.keyCodes = keyCodes; this.optionMenu = optionMenu; this.root = root; this.btnWest = btnWest;
        this.btnEast = btnEast; this.btnNorth = btnNorth; this.btnSouth = btnSouth; this.state = state;
        touches=touche; LEFT=left; RIGHT=right; UP=up; DOWN=down;
    }

    /**
     * Method that check when a key is pressed
     * @param event get all the event that can happened in the game
    */
    public void keyPressedHandler(KeyEvent event) {
        if (!MazeState.getGameEnded()){
            if(touches[0]!=null || touches[1]!=null || touches[2]!=null || touches[3]!=null){
                if (event.getText().equals(touches[0])){PacMan.INSTANCE.setNextDirection(Direction.WEST);}
                else if(event.getText().equals(touches[1])){PacMan.INSTANCE.setNextDirection(Direction.EAST);}
                else if(event.getText().equals(touches[2])){PacMan.INSTANCE.setNextDirection(Direction.NORTH);}
                else if(event.getText().equals(touches[3])){PacMan.INSTANCE.setNextDirection(Direction.SOUTH);}
            }

            // If the button escape is pressed, if the option menu is showed then close it else open it
            if(event.getCode()==KeyCode.ESCAPE){
                //si le bouton echap est pressé, alors le menu options se lance
                System.out.println("ouvrir option");
                    if(!optionMenu.isVisible()){
                        optionMenu.setVisible(true);
                        root.getChildren().addAll(optionMenu);
                        // optionMenu.base(); /*pas fini*/
                        // methode qui permet lorsqu'on quitte les options in-game et qu'on le rouvre,
                        // on ne retourne pas dans menu1, mais dans menu0
                    }
                    else if(optionMenu.isVisible()){
                        root.getChildren().removeAll(optionMenu);
                        optionMenu.setVisible(false);
                    }
            }
            
            // Thanks to this condition, if the options are open, we can't move pacman
            if(optionMenu.isVisible()) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    if (PacMan.INSTANCE.isEnergized()) {
                        PacMan.INSTANCE.setEnergizerPaused(true);
                    }
                } if(btnWest.isVisible()) {//même principe que dans App.java
                    if(event.getCode()!=null && App.KeyForbidden(event.getCode())){
                        if(App.keyName(event.getText())!=null){LEFT.setText(App.keyName(event.getText()));App.KeySpecial(touches, 0, event.getText());keyCodes[0]=null;}
                        else if(event.getCode()!=KeyCode.UNDEFINED){App.bindKey(keyCodes, 0, event.getCode()); LEFT.setText(App.KeyCodetoString(keyCodes[0])); touches[0]=null;}
                        btnWest.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                    }
                } if(btnEast.isVisible()){
                    if(event.getCode()!=null && App.KeyForbidden(event.getCode())){
                        if(App.keyName(event.getText())!=null){RIGHT.setText(App.keyName(event.getText()));App.KeySpecial(touches, 1, event.getText());keyCodes[1]=null;}
                        else if(event.getCode()!=KeyCode.UNDEFINED){App.bindKey(keyCodes, 1, event.getCode()); RIGHT.setText(App.KeyCodetoString(keyCodes[1])); touches[1]=null;}
                        btnEast.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                    }
                } if(btnNorth.isVisible()){
                    if(event.getCode()!=null && App.KeyForbidden(event.getCode())){
                        if(App.keyName(event.getText())!=null){UP.setText(App.keyName(event.getText()));App.KeySpecial(touches, 2, event.getText());keyCodes[2]=null;}
                        else if(event.getCode()!=KeyCode.UNDEFINED){App.bindKey(keyCodes, 2, event.getCode()); UP.setText(App.KeyCodetoString(keyCodes[2])); touches[2]=null;}
                        btnNorth.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                    }
                } if(btnSouth.isVisible()){
                    if(event.getCode()!=null  && App.KeyForbidden(event.getCode())){
                        if(App.keyName(event.getText())!=null){DOWN.setText(App.keyName(event.getText()));App.KeySpecial(touches, 3, event.getText());keyCodes[3]=null;}
                        else if(event.getCode()!=KeyCode.UNDEFINED){App.bindKey(keyCodes, 3, event.getCode()); DOWN.setText(App.KeyCodetoString(keyCodes[3])); touches[3]=null;}
                        btnSouth.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                    }
                } else {
                    if (event.getCode() == keyCodes[0] || event.getCode() == keyCodes[1] || event.getCode() == keyCodes[2] || event.getCode() == keyCodes[3]){
                        if (lastKeyCode == null || lastKeyCode != event.getCode()) {
                            lastKeyCode = event.getCode();
                        }
                    }
                }
            } else {
                PacMan.INSTANCE.setEnergizerPaused(false);
                if (event.getCode() == keyCodes[0]) {
                    PacMan.INSTANCE.setNextDirection(Direction.WEST);
                } else if (event.getCode() == keyCodes[1]) {
                    PacMan.INSTANCE.setNextDirection(Direction.EAST);
                } else if (event.getCode() == keyCodes[2]) {
                    PacMan.INSTANCE.setNextDirection(Direction.NORTH);
                } else if (event.getCode() == keyCodes[3]) {
                    PacMan.INSTANCE.setNextDirection(Direction.SOUTH);
                } else {
                    PacMan.INSTANCE.setNextDirection(PacMan.INSTANCE.getNextDirection());
                }
            }
            
        } else {
            // If the game has ended and the PLAYER press enter, the game restart
            if (event.getCode() == KeyCode.ENTER){
                state.restart();
                Music.playBackgroundMusic();
            }
        }
    }

    public static void resetLastKeyCode() {
        lastKeyCode = null;
    }
    /* Currently unused, so I commented it.
     * Method that check when a key is released
     * @param event get all the event that can happened in the game
    */
    // public void keyReleasedHandler(KeyEvent event) {}
}