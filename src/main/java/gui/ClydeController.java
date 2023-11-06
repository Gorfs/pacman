package gui;

import config.Cell;
import config.MazeConfig;
import geometry.RealCoordinates;
import model.Critter;
import model.Direction;
import model.Ghost;

public final class ClydeController extends GhostsController {

    @Override
    public void startAI(){
        Ghost.CLYDE.setNextDirection(Direction.EAST);
    }

    @Override
    public Direction nextDirection(Critter clyde, MazeConfig config){
        RealCoordinates direction;
        var curPos = clyde.getPos();
        Direction result = Direction.NONE;
        switch(clyde.getDirection()){
            case NORTH -> direction = RealCoordinates.NORTH_UNIT;
            case EAST -> direction = RealCoordinates.EAST_UNIT;
            case SOUTH -> direction = RealCoordinates.SOUTH_UNIT;
            case WEST -> direction = RealCoordinates.WEST_UNIT;
            default -> {
                return Direction.EAST; // go right by default.
            }
        }
        // if the next cell has a wall, generate a valid direction.
        if (config.getCell((curPos.plus(direction).round())).initialContent() == Cell.Content.WALL){
            // setting the result to the current direction, so it forces at least 1 another call to the generate randomDirection function.
            result = clyde.getDirection();
            while(!isDirectionValid(clyde.getDirection(), result, clyde, config)){
                // outputs a random direction
                switch(rd.nextInt(0,4)){
                    case 0 -> result = Direction.NORTH;
                    case 1 -> result = Direction.EAST;
                    case 2 -> result = Direction.SOUTH;
                    case 3 -> result = Direction.WEST;
                }
            }
        }
        return result;
    }

    public boolean isDirectionValid(Direction dir1, Direction dir2, Critter critter, MazeConfig config){
        // the point of this function is to make sure the ghost doesn't turn back on itself
        if (dir1 == dir2){
            return false;
        }else{
            return switch(dir2){
                case NORTH -> ((dir1 != Direction.SOUTH) && (config.getCell(critter.getPos().plus(RealCoordinates.NORTH_UNIT).round()).initialContent()) != Cell.Content.WALL);
                case SOUTH -> ((dir1 != Direction.NORTH) && (config.getCell(critter.getPos().plus(RealCoordinates.SOUTH_UNIT).round()).initialContent()) != Cell.Content.WALL);
                case EAST -> ((dir1 != Direction.WEST ) && (config.getCell(critter.getPos().plus(RealCoordinates.EAST_UNIT ).round()).initialContent()) != Cell.Content.WALL);
                case WEST -> ((dir1 != Direction.EAST ) && (config.getCell(critter.getPos().plus(RealCoordinates.WEST_UNIT ).round()).initialContent()) != Cell.Content.WALL);
                // might need to be changed to false in the future
                default -> false;
            };
        }
    }
}
