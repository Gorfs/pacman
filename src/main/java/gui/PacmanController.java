package gui;

import model.Direction;
import model.MazeState;
import model.PacMan;

import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class PacmanController {
    //class qui gère les touches préssées in-game
    private KeyCode[] k = {KeyCode.LEFT,KeyCode.RIGHT,KeyCode.UP,KeyCode.DOWN};
    private String[] touches = {null,null,null,null};
    private MenuButton btncase1a;
    private MenuButton btncase2a;
    private MenuButton btncase3a;
    private MenuButton btncase4a;
    private GameMenu2 gameMenu1;
    private Pane root1;

    private Text LEFT;
    private Text RIGHT;
    private Text UP;
    private Text DOWN;
    public PacmanController(String[] touche, Text left, Text right, Text up, Text down, KeyCode[] k, GameMenu2 gameMenu2, Pane root, MenuButton2 button, 
    MenuButton btncase1, MenuButton btncase2, MenuButton btncase3, MenuButton btncase4){
        this.k=k; gameMenu1 = gameMenu2; root1 = root; btncase1a = btncase1;
        btncase2a = btncase2; btncase3a = btncase3; btncase4a = btncase4; LEFT=left; RIGHT=right; UP=up; DOWN=down; touches=touche;
        System.out.println("Les touches : "+touche[0]+touche[1]+touche[2]+touche[3]+"\n"+k[0]+k[1]+k[2]+k[3]);
    }
    
    public void keyPressedHandler(KeyEvent event) {

        
        if (!MazeState.getGameEnded()){
            if(touches[0]!=null || touches[1]!=null || touches[2]!=null || touches[3]!=null){
            if (event.getText().equals(touches[0])){PacMan.INSTANCE.setNextDirection(Direction.WEST);}
            else if(event.getText().equals(touches[1])){PacMan.INSTANCE.setNextDirection(Direction.EAST);}
            else if(event.getText().equals(touches[2])){PacMan.INSTANCE.setNextDirection(Direction.NORTH);}
            else if(event.getText().equals(touches[3])){PacMan.INSTANCE.setNextDirection(Direction.SOUTH);}
            }
            if(event.getCode()==KeyCode.ESCAPE){
                if(gameMenu1.isVisible()){//si quand on appuie sur options on est dans le menu
                    if(PacMan.getTimer2Marche()){//si le timer2 n'est en 'pause'
                        if(PacMan.isEnergized() && PacMan.getCompteur()>0){//si le pacman est energized et le compteur>0
                            PacMan.setTimer2Marche(false);//on remet le compteur de chrono en route
                            TimerTask t = new TimerTask() {
                                @Override
                                public void run() {
                                    PacMan.setAlmostNormal(true);
                                    Timer timer = new Timer();
                                     timer.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            PacMan.setEnergized(false);
                                            PacMan.setAlmostNormal(false);
                                        }
                                    }, 1000);
                                }
                            };
                            Timer tt = new Timer();
                            tt.schedule(t,PacMan.getCompteur());
                            //on lance un nouveau compteur, le dernier setEnergized etant fini
                        }
                    } 
                }
                //si le bouton echap est préssée, alors le menu options se lance
                System.out.println("ouvrir option");
                    if(!gameMenu1.isVisible()){
                        gameMenu1.setVisible(true);
                        root1.getChildren().addAll(gameMenu1);
                        //gameMenu1.base(); /*pas fini*/
                        //methode qui permet que quand on quitte les options in-game, et que on reouvre, on ne revienne pas dans menui, mais dans menu0
                    }
                    else if(gameMenu1.isVisible()){
                        root1.getChildren().removeAll(gameMenu1); 
                        gameMenu1.setVisible(false);
                    }
            }
            if(btncase1a.isVisible()){//même principe que dans App.java
                if(event.getCode()!=null){
                    if(App.keyName(event.getText())!=null){LEFT.setText(App.keyName(event.getText()));App.KeySpecial(touches, 0, event.getText());k[0]=null;}
                    else if(event.getCode()!=KeyCode.UNDEFINED){App.tab(k, 0, event.getCode()); LEFT.setText(App.KeyCodetoString(k[0])); touches[0]=null;}
                    btncase1a.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                }
            }
            if(btncase2a.isVisible()){
                if(event.getCode()!=null){
                    if(App.keyName(event.getText())!=null){RIGHT.setText(App.keyName(event.getText()));App.KeySpecial(touches, 1, event.getText());k[1]=null;}
                    else if(event.getCode()!=KeyCode.UNDEFINED){App.tab(k, 1, event.getCode()); RIGHT.setText(App.KeyCodetoString(k[1])); touches[1]=null;}
                    btncase2a.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                }
            }
            if(btncase3a.isVisible()){
                if(event.getCode()!=null){
                    if(App.keyName(event.getText())!=null){UP.setText(App.keyName(event.getText()));App.KeySpecial(touches, 2, event.getText());k[2]=null;}
                    else if(event.getCode()!=KeyCode.UNDEFINED){App.tab(k, 2, event.getCode()); UP.setText(App.KeyCodetoString(k[2])); touches[2]=null;}
                    btncase3a.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                }
            }
            if(btncase4a.isVisible()){
                if(event.getCode()!=null){
                    if(App.keyName(event.getText())!=null){DOWN.setText(App.keyName(event.getText()));App.KeySpecial(touches, 3, event.getText());k[3]=null;}
                    else if(event.getCode()!=KeyCode.UNDEFINED){App.tab(k, 3, event.getCode()); DOWN.setText(App.KeyCodetoString(k[3])); touches[3]=null;}
                    btncase4a.setVisible(false);//une fois que le bouton est appuyé, on l'enlève
                }
            }
        if(gameMenu1.isVisible()){
            if(event.getCode()==KeyCode.ESCAPE && !PacMan.getTimer2Marche()){
                if(PacMan.isEnergized() && PacMan.getCompteur()>0){
                    PacMan.setTimer2Marche(true);
                    PacMan.setTimerMarche(true);
                }
            } 
        }
            //grace à cette condition, si option est visible/activé, alors on ne peut pas déplacer pacman
            else if(event.getCode()==k[0]){PacMan.INSTANCE.setNextDirection(Direction.WEST);}
            else if(event.getCode()==k[1]){PacMan.INSTANCE.setNextDirection(Direction.EAST);}
            else if(event.getCode()==k[2]){PacMan.INSTANCE.setNextDirection(Direction.NORTH);}
            else if(event.getCode()==k[3]){PacMan.INSTANCE.setNextDirection(Direction.SOUTH);}
            else {PacMan.INSTANCE.setNextDirection(PacMan.INSTANCE.getNextDirection());}}
        else{
            if (event.getCode() == KeyCode.ENTER){ //Si la partie est terminée mais que le joueur appuie sur ENTER alors on restart
                MazeState.restart();
            }
        }

    }
    public void keyReleasedHandler(KeyEvent event) {
        // Nothing to do?
    }
}
