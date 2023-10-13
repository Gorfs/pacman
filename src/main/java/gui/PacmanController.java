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
        if (!MazeState.getGameEnded()){ //Lorsque c'est GameOver, on veut que le joueur ne bouge plus. Donc on vérifie que la partie est terminée.
            PacMan.INSTANCE.setNextDirection(
                    // Store the key pressed into the next move
                    switch (event.getCode()) {
                        case k[0] -> Direction.WEST;
                        case k[1] -> Direction.EAST;
                        case k[2] -> Direction.NORTH;
                        case k[3] -> Direction.SOUTH;
                        default -> PacMan.INSTANCE.getNextDirection(); // do nothing
                    }
            );
        }
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
