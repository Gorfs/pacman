package gui;

import model.Direction;
import model.MazeState;
import model.PacMan;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PacmanController {
    private KeyCode[] k = {KeyCode.LEFT,KeyCode.RIGHT,KeyCode.UP,KeyCode.DOWN};
    public PacmanController(KeyCode[] k){this.k=k;}

    public void keyPressedHandler(KeyEvent event) {

        
        if (!MazeState.getGameEnded()){
            if(event.getCode()==k[0]){PacMan.INSTANCE.setNextDirection(Direction.WEST);}
            else if(event.getCode()==k[1]){PacMan.INSTANCE.setNextDirection(Direction.EAST);}
            else if(event.getCode()==k[2]){PacMan.INSTANCE.setNextDirection(Direction.NORTH);}
            else if(event.getCode()==k[3]){PacMan.INSTANCE.setNextDirection(Direction.SOUTH);}
            else {PacMan.INSTANCE.setNextDirection(PacMan.INSTANCE.getNextDirection());}}
        else{
            if (event.getCode() == KeyCode.ENTER){ //Si la partie est terminée mais que le joueur appuie sur ENTER alors on restart
                MazeState.restart();
                App.playBackgroundMusic();

            }
        }

    }
    public void keyReleasedHandler(KeyEvent event) {
        // Nothing to do?
    }
}
