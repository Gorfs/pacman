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
            if(event.getCode()==k[0]){PacMan.INSTANCE.setDirection(Direction.WEST);}
            else if(event.getCode()==k[1]){PacMan.INSTANCE.setDirection(Direction.EAST);}
            else if(event.getCode()==k[2]){PacMan.INSTANCE.setDirection(Direction.NORTH);}
            else if(event.getCode()==k[3]){PacMan.INSTANCE.setDirection(Direction.SOUTH);}
            else {PacMan.INSTANCE.getDirection();}
    }
    public void keyReleasedHandler(KeyEvent event) {
        // Nothing to do?
    }
}
