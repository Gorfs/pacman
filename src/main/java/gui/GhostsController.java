package gui;

import config.Cell;
import config.MazeConfig;
import geometry.RealCoordinates;
import model.Critter;
import model.Direction;

import java.util.Random;

public sealed abstract class GhostsController permits ClydeController {
    public static final Random rd = new Random();

    public abstract void startAI();

    public void setDirection(MazeConfig config, Critter critter) {
        var result = critter.getDirection();
        RealCoordinates direction;
        var curPos = critter.getPos();



        switch(critter.getDirection()){
            case EAST -> direction = RealCoordinates.EAST_UNIT;
            case SOUTH -> direction = RealCoordinates.SOUTH_UNIT;
            case WEST -> direction = RealCoordinates.WEST_UNIT;
            case NORTH -> direction = RealCoordinates.NORTH_UNIT;
            default -> direction = RealCoordinates.EAST_UNIT; // go right by default.
        }
        // if the next cell has a wall, generate a valid direction.
        if(config.getCell((curPos.plus(direction).round())).initialContent() == Cell.Content.WALL){
            // setting the result to the current direction, so it forces at least 1 another call to the generate randomDirection function.
            result = critter.getDirection();
            while(!isDirectionValid(critter.getDirection(), result, critter, config)){
                result = nextDirection(critter);
            } critter.setNextDirection(result);
        }
    }

    public abstract Direction nextDirection(Critter critter);

    public boolean isDirectionValid(Direction dir1, Direction dir2, Critter clyde, MazeConfig config){
        // the point of this function is to make sure the ghost doesn't turn back on itself
        boolean cellValid;
        if (dir1 == dir2){
            return false;
        }else{
            return switch(dir2){
                case NORTH -> ((dir1 != Direction.SOUTH) && (config.getCell(clyde.getPos().plus(RealCoordinates.NORTH_UNIT).round()).initialContent()) != Cell.Content.WALL);
                case SOUTH -> ((dir1 != Direction.NORTH) && (config.getCell(clyde.getPos().plus(RealCoordinates.SOUTH_UNIT).round()).initialContent()) != Cell.Content.WALL);
                case EAST -> ((dir1 != Direction.WEST ) && (config.getCell(clyde.getPos().plus(RealCoordinates.EAST_UNIT ).round()).initialContent()) != Cell.Content.WALL);
                case WEST -> ((dir1 != Direction.EAST ) && (config.getCell(clyde.getPos().plus(RealCoordinates.WEST_UNIT ).round()).initialContent()) != Cell.Content.WALL);
                // might need to be changed to false in the future
                default -> false;
            };
        }
    }

}
