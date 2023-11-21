package controllers;

import gui.GameMenu2;
import gui.MenuButton;
import gui.MenuButton2;
import gui.Music;
import model.Direction;
import model.MazeState;
import model.PacMan;

import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * Class PacmanController is used to check all the keyboard input while in the game.
 */
public class PacmanController {
    private final KeyCode[] keyCodes;
    private final MenuButton btnWest;
    private final MenuButton btnEast;
    private final MenuButton btnNorth;
    private final MenuButton btnSouth;
    private final GameMenu2 optionMenu;
    private final Pane root;

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
    public PacmanController(KeyCode[] keyCodes, GameMenu2 optionMenu, Pane root, MenuButton btnWest,
                            MenuButton btnEast, MenuButton btnNorth, MenuButton btnSouth){
        this.keyCodes = keyCodes; this.optionMenu = optionMenu; this.root = root; this.btnWest = btnWest;
        this.btnEast = btnEast; this.btnNorth = btnNorth; this.btnSouth = btnSouth;
    }

    /**
     * Method that check when a key is pressed
     * @param event get all the event that can happened in the game
     */
    public void keyPressedHandler(KeyEvent event) {
        if (!MazeState.getGameEnded()){
            // If the button escape is pressed, if the option menu is showed then close it else open it
            if(event.getCode()==KeyCode.ESCAPE){
                if(optionMenu.isVisible()){
                    if(PacMan.getTimer2Marche()){
                        if(PacMan.INSTANCE.isEnergized() && PacMan.getCompteur()>0){//si le pacman est energized et le compteur>0
                            PacMan.setTimer2Marche(false);//on remet le compteur de chrono en route
                            TimerTask t = new TimerTask() {
                                @Override
                                public void run() {
                                    PacMan.setAlmostNormal(true);
                                    Timer timer = new Timer();
                                     timer.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            PacMan.INSTANCE.setEnergized(false);
                                            PacMan.setAlmostNormal(false);
                                        }
                                    }, 1000);
                                }
                            };
                            Timer tt = new Timer();
                            tt.schedule(t,PacMan.getCompteur());
                            //on lance un nouveau compteur, le dernier setEnergized étant fini
                        }
                    } 
                }
                //si le bouton échap est pressé, alors le menu options se lance
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
            if(btnWest.isVisible()){ //même principe que dans App.java
                if(event.getCode()!=null){
                    keyCodes[0]=event.getCode();
                    btnWest.setVisible(false);
                }
            }
            if(btnEast.isVisible()){
                if(event.getCode()!=null){
                    keyCodes[1]=event.getCode();
                    btnEast.setVisible(false);
                }
            }
            if(btnNorth.isVisible()){
                if(event.getCode()!=null){
                    keyCodes[2]=event.getCode();
                    btnNorth.setVisible(false);
                }
            }
            if(btnSouth.isVisible()){
                if(event.getCode()!=null){
                    keyCodes[3]=event.getCode();
                    btnSouth.setVisible(false);
                }
            }
        if(optionMenu.isVisible()){
            if(event.getCode()==KeyCode.ESCAPE && !PacMan.getTimer2Marche()){
                if(PacMan.INSTANCE.isEnergized() && PacMan.getCompteur()>0){
                    PacMan.setTimer2Marche(true);
                    PacMan.setTimerMarche(true);
                }
            } 
        }
            //grâce à cette condition, si option est visible/activé, alors on ne peut pas déplacer pacman
            else if(event.getCode()== keyCodes[0]){PacMan.INSTANCE.setNextDirection(Direction.WEST);}
            else if(event.getCode()== keyCodes[1]){PacMan.INSTANCE.setNextDirection(Direction.EAST);}
            else if(event.getCode()== keyCodes[2]){PacMan.INSTANCE.setNextDirection(Direction.NORTH);}
            else if(event.getCode()== keyCodes[3]){PacMan.INSTANCE.setNextDirection(Direction.SOUTH);}
            else {PacMan.INSTANCE.setNextDirection(PacMan.INSTANCE.getNextDirection());}}
        else{
            // Si la partie est terminée et que le joueur appuie sur entrer, alors on recommence la partie
            if (event.getCode() == KeyCode.ENTER){
                MazeState.restart();
                Music.playBackgroundMusic();
            }
        }
    }

    /**
     * Method that check when a key is released
     * @param event get all the event that can happened in the game
     */
    public void keyReleasedHandler(KeyEvent event) {
        // Nothing to do?
    }
}
