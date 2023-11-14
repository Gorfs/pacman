package gui;

import model.Direction;
import model.MazeState;
import model.PacMan;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class PacmanController {
    private KeyCode[] k = {KeyCode.LEFT,KeyCode.RIGHT,KeyCode.UP,KeyCode.DOWN};
    private MenuButton btncase1a;
    private MenuButton btncase2a;
    private MenuButton btncase3a;
    private MenuButton btncase4a;
    private GameMenu2 gameMenu1;
    private float b;
    private Pane root1;
    public PacmanController(KeyCode[] k, GameMenu2 gameMenu2, Pane root, MenuButton2 button, 
    MenuButton btncase1, MenuButton btncase2, MenuButton btncase3, MenuButton btncase4, float a){
        this.k=k; gameMenu1 = gameMenu2; root1 = root; btncase1a = btncase1;
        btncase2a = btncase2; btncase3a = btncase3; btncase4a = btncase4; b = a;
    }

    public void keyPressedHandler(KeyEvent event) {

        
        if (!MazeState.getGameEnded()){
            if(event.getCode()==KeyCode.ESCAPE){
                System.out.println("ouvrir option");
                    if(!gameMenu1.isVisible()){
                        gameMenu1.setVisible(true);
                        root1.getChildren().addAll(gameMenu1);
                    }
                    else if(gameMenu1.isVisible()){root1.getChildren().removeAll(gameMenu1); gameMenu1.setVisible(false);}
            }
            if(btncase1a.isVisible()){//LEFT
                if(event.getCode()!=null){
                    k[0]=event.getCode();
                    btncase1a.setVisible(false);
                }
            }
            if(btncase2a.isVisible()){//RIGHT
                if(event.getCode()!=null){
                    k[1]=event.getCode();        
                    btncase2a.setVisible(false);
                }
            }
            if(btncase3a.isVisible()){//UP
                if(event.getCode()!=null){
                    k[2]=event.getCode();
                    btncase3a.setVisible(false);
                }
            }
            if(btncase4a.isVisible()){//DOWN
                if(event.getCode()!=null){
                    k[3]=event.getCode();
                    btncase4a.setVisible(false);
                }
            }
            if(gameMenu1.isVisible()){}
            else if(event.getCode()==k[0]){PacMan.INSTANCE.setNextDirection(Direction.WEST);}
            else if(event.getCode()==k[1]){PacMan.INSTANCE.setNextDirection(Direction.EAST);}
            else if(event.getCode()==k[2]){PacMan.INSTANCE.setNextDirection(Direction.NORTH);}
            else if(event.getCode()==k[3]){PacMan.INSTANCE.setNextDirection(Direction.SOUTH);}
            else {PacMan.INSTANCE.setNextDirection(PacMan.INSTANCE.getNextDirection());}}
        else{
            if (event.getCode() == KeyCode.ENTER){ //Si la partie est terminée mais que le joueur appuie sur ENTER alors on restart
                MazeState.restart();
                Music.playBackgroundMusic();

            }
        }

    }
    public void keyReleasedHandler(KeyEvent event) {
        // Nothing to do?
    }
}
