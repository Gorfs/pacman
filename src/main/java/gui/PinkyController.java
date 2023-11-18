package gui;

import config.MazeConfig;
import geometry.IntCoordinates;
import model.Critter;
import model.Direction;
import model.Ghost;
import model.PacMan;


public final class PinkyController extends GhostsController {

    @Override
    public void startAI() {
        Ghost.PINKY.setNextDirection(Direction.NORTH);
        started = false;
        timer = 0;
    }

    @Override
    public IntCoordinates scatterDirection(Critter critter, MazeConfig config) {
        return findPathing(critter, new IntCoordinates(0, 0), config);
    }

    @Override
    public IntCoordinates nextDirection(Critter critter, MazeConfig config) {
        IntCoordinates goal = PacMan.INSTANCE.getPos().plus(getDirection(PacMan.INSTANCE.getDirection()).times(2)).round();
        return findPathing(critter, goal, config);
    }

    @Override
    public boolean conditionOut() {
        return true;
    }
}
