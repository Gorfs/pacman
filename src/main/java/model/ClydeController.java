package model;

import java.util.Random;
import config.MazeConfig;
import geometry.RealCoordinates;
import misc.Debug;

import java.util.List;
import config.Cell;

// import misc.Debug;

public class ClydeController {

    private static Random rd = new Random();
    public void startAI(){
        // Debug.out("CLYDE AI started");
        Ghost.CLYDE.setDirection(Direction.EAST);
    }
    public static void setDirection(MazeConfig config){
        // should set the next direction of the ghost
        
        // We get the list from mazestate to get the critter object.
        // This give us access to current position as well as current neighbours.
        List<Critter> critters = MazeState.getCritters();
        Critter clyde = null;
        for (var critter: critters){
            if (critter.toString() == "CLYDE"){
                clyde = critter;
                
            }
        }
        // settings a default direction, mostly just for starting the movment when the game starts
        if (clyde.getDirection() == Direction.NONE){
            clyde.setDirection(Direction.EAST);
        }

        var result = clyde.getDirection();
        var direction = RealCoordinates.NORTH_UNIT;
        var curPos = clyde.getPos();
        // used to calculate the next cell that clyde is going to walk into
        switch(clyde.getDirection()){
            case EAST -> direction = RealCoordinates.EAST_UNIT;
            case SOUTH -> direction = RealCoordinates.SOUTH_UNIT;
            case WEST -> direction = RealCoordinates.WEST_UNIT;
            case NORTH -> direction = RealCoordinates.NORTH_UNIT;
            default -> direction = RealCoordinates.EAST_UNIT; // go right by default.
        }

        // if the next cell has a wall, generate a valid direction.
        if(config.getCell((curPos.plus(direction).round())).initialContent() == Cell.Content.WALL){
            // setting the result to the current direction so it forces at least 1 another call to the generate randomDirection function.
            result = clyde.getDirection();
            while(!isDirectionValid(clyde.getDirection(), result, clyde,config)){
                result = randomDir(clyde);
                // Debug.out(String.valueOf(result));

            }
            clyde.setDirection(result);

        // set the direction to the valid direction given previously
        
        }
    }

    private static boolean isDirectionValid(Direction dir1, Direction dir2, Critter clyde, MazeConfig config){
        // the point of this function is to make sure the ghost doesn't turn back on itself
        Debug.out("isDirectionValid has been called " + dir1 + " " + dir2);
        boolean cellValid = true;
        if (dir1 == dir2){
            return false;
        }else{
            // Debug.out("The current direction being checked is :" + dir2);
            switch(dir2){
                case NORTH -> {cellValid =  ((dir1 != Direction.SOUTH) && (config.getCell(clyde.getPos().plus(RealCoordinates.NORTH_UNIT).round()).initialContent()) != Cell.Content.WALL);}
                case SOUTH -> {cellValid =  ((dir1 != Direction.NORTH) && (config.getCell(clyde.getPos().plus(RealCoordinates.SOUTH_UNIT).round()).initialContent()) != Cell.Content.WALL);}
                case EAST ->  {cellValid =  ((dir1 != Direction.WEST ) && (config.getCell(clyde.getPos().plus(RealCoordinates.EAST_UNIT ).round()).initialContent()) != Cell.Content.WALL);}
                case WEST ->  {cellValid =  ((dir1 != Direction.EAST ) && (config.getCell(clyde.getPos().plus(RealCoordinates.WEST_UNIT ).round()).initialContent()) != Cell.Content.WALL);}
                // might need to be changed to false in the future
                default -> {cellValid = false;}
            };
        }
        // Debug.out(String.valueOf(cellValid) + String.valueOf(cellValid));
        return cellValid;

    }

    private static Direction randomDir(Critter clyde){
        // outputs a random direction
        Direction result;
        result = clyde.getDirection();
        switch(rd.nextInt(0,4)){
            case 0 -> result =  Direction.NORTH;
            case 1 -> result = Direction.EAST;
            case 2 -> result = Direction.SOUTH;
            case 3 -> result = Direction.WEST;
            }
        // Debug.out("randomDir function input : " + input + " output : " + result );
        return result;
        
    }

}
