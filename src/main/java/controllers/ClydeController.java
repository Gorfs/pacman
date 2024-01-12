package controllers;

import config.Constants;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import model.*;

public final class ClydeController extends GhostsController {
    @Override
    public void startAI(){
        Ghost.CLYDE.setNextDirection(Direction.EAST);
        started = false;
        scatterTimer = 0;
    }

    @Override
    public IntCoordinates scatterDirection(Critter critter, MazeConfig config) {
        return findPathing(critter, new IntCoordinates(0, config.getHeight() + 1), config);
    }

    @Override
    public IntCoordinates nextDirection(Critter critter, MazeConfig config){
        RealCoordinates clydePos = Ghost.CLYDE.getPos().times(-1);
        IntCoordinates distance = PacMan.INSTANCE.getPos().plus(clydePos).round();
        if (Math.abs(distance.x()) + Math.abs(distance.y()) <= 8) return findPathing(critter, null, config);
        return scatterDirection(critter, config);
    }

    @Override
    public boolean conditionOut() {
        return (MazeState.getScore() >= 146/3 * Constants.DOT_SCORE);
    }
}