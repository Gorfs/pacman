package gui;

import config.MazeConfig;
import geometry.IntCoordinates;
import model.Critter;
import model.Direction;
import model.Ghost;

public final class BlinkyController extends GhostsController{
    @Override
    public void startAI() {
        Ghost.BLINKY.setDirection(Direction.EAST);
    }

    @Override
    public IntCoordinates scatterDirection(Critter critter, MazeConfig config) {
        return findPathing(critter, new IntCoordinates(config.getWidth(), 0), config);
    }

    @Override
    public IntCoordinates nextDirection(Critter critter, MazeConfig config) {
        return findPathing(critter, null, config);
    }
}
