package controllers;

import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import model.*;

public final class ClydeController extends GhostsController {
    @Override
    public void startAI(){
        Ghost.CLYDE.setNextDirection(Direction.EAST);
        started = false;
        timer = 0;
    }

    @Override
    public IntCoordinates scatterDirection(Critter critter, MazeConfig config) {
        return findPathing(critter, new IntCoordinates(0, config.getHeight()-1), config);
    }

    @Override
    public IntCoordinates nextDirection(Critter critter, MazeConfig config){
        RealCoordinates blinkyPos = Ghost.BLINKY.getPos().times(-1);
        RealCoordinates distance = PacMan.INSTANCE.getPos().plus(blinkyPos);
        IntCoordinates goal = Ghost.BLINKY.getPos().plus(distance.times(2)).round();
        return findPathing(critter, goal, config);
    }

    @Override
    public boolean conditionOut() {
        return (MazeState.getScore() >= 146/3);
    }
}