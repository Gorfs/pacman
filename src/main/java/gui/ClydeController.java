package gui;

import model.Critter;
import model.Direction;
import model.Ghost;

public final class ClydeController extends GhostsController {

    @Override
    public void startAI(){
        Ghost.CLYDE.setNextDirection(Direction.EAST);
    }

    @Override
    public Direction nextDirection(Critter clyde){
        // outputs a random direction
        Direction result;
        result = clyde.getDirection();
        switch(rd.nextInt(0,4)){
            case 0 -> result = Direction.NORTH;
            case 1 -> result = Direction.EAST;
            case 2 -> result = Direction.SOUTH;
            case 3 -> result = Direction.WEST;
            }
        return result;
    }

}
