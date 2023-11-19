package controllers;

import config.MazeConfig;
import geometry.IntCoordinates;
import model.Critter;
import model.Direction;
import model.Ghost;

public final class BlinkyController extends GhostsController {
    @Override
    public void startAI() {
        Ghost.BLINKY.setDirection(Direction.NORTH);
        started = true;
        timer = 0;
    }

    @Override
    public IntCoordinates scatterDirection(Critter critter, MazeConfig config) {
        return findPathing(critter, new IntCoordinates(config.getWidth() - 1, 0), config);
    }

    @Override
    public IntCoordinates nextDirection(Critter critter, MazeConfig config) {
        return findPathing(critter, null, config);
    }

    @Override
    public boolean conditionOut() {
        return true;
    }
}
