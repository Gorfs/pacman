package gui;

import model.Direction;
import model.MazeState;
import model.PacMan;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PacmanController {
    public void keyPressedHandler(KeyEvent event) {
        if (!MazeState.getGameEnded()){ //Lorsque c'est GameOver, on veut que le joueur ne bouge plus. Donc on vérifie que la partie est terminée.
            PacMan.INSTANCE.setNextDirection(
                    // Store the key pressed into the next move
                    switch (event.getCode()) {
                        case LEFT -> Direction.WEST;
                        case RIGHT -> Direction.EAST;
                        case UP -> Direction.NORTH;
                        case DOWN -> Direction.SOUTH;
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
