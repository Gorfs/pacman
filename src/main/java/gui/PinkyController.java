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
        Ghost.PINKY.setNextDirection(Direction.EAST);
    }

    @Override
    public IntCoordinates scatterDirection(Critter critter, MazeConfig config) {
        return findPathing(critter, new IntCoordinates(0, 0), config);
    }

    @Override
    public IntCoordinates nextDirection(Critter critter, MazeConfig config) {
        IntCoordinates goal = PacMan.INSTANCE.getPos().plus(getDirection(PacMan.INSTANCE).times(2)).round();
        return findPathing(critter, goal, config);
    }
}
