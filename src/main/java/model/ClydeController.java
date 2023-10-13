package model;

import java.util.Random;

import javax.swing.text.Position;

import config.MazeConfig;
import geometry.RealCoordinates;

import java.util.List;
import config.Cell;

import misc.Debug;

public class ClydeController {

    Random rd = new Random();
    public void startAI(){
        Debug.out("CLYDE AI started");
        Ghost.CLYDE.setDirection(Direction.EAST);
    }
    public static void setDirection(MazeConfig config){
        // should return the next positon of the ghost
        Random rd = new Random();
        // We get the list from mazestate to get the critter object.
        // This give us access to current position as well as current neighbours.
        List<Critter> critters = MazeState.getCritters();
        Critter clyde = null;
        for (var critter: critters){
            if (critter.toString() == "CLYDE"){
                clyde = critter;
                
            }
        }
        clyde.setDirection(Direction.EAST);
        Direction result = clyde.getDirection();

        if (clyde != null){
            var curPos = clyde.getPos();
            // Debug.out(curPos.toString());
            var curNeighbours = curPos.intNeighbours();
            RealCoordinates direction;
            switch(clyde.getDirection()){
                case EAST -> direction = RealCoordinates.EAST_UNIT;
                case SOUTH -> direction = RealCoordinates.SOUTH_UNIT;
                case WEST -> direction = RealCoordinates.WEST_UNIT;
                case NORTH -> direction = RealCoordinates.NORTH_UNIT;
                default -> direction = RealCoordinates.EAST_UNIT; // go right by default.
            }
            if(config.getCell((curPos.plus(direction).round())).initialContent() == Cell.Content.WALL){
                switch(rd.nextInt(0,4)){
                    case 0 -> result = Direction.NORTH;
                    case 1 -> result = Direction.EAST;
                    case 2 -> result = Direction.SOUTH;
                    case 3 -> result = Direction.WEST;
                }
            }
            clyde.setDirection(result);
        }
    }

}
