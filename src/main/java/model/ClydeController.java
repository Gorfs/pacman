package model;

import java.util.Random;

import javax.swing.text.Position;

import config.MazeConfig;
import java.util.List;


import misc.Debug;

public class ClydeController {

    Random rd = new Random();
    public void startAI(){
        Debug.out("CLYDE AI started");
        Ghost.CLYDE.setDirection(Direction.EAST);
    }
    public static void setDirection(){
        // should return the next positon of the ghost
        

        // We get the list from mazestate to get the critter object.
        // This give us access to current position as well as current neighbours.
        List<Critter> critters = MazeState.getCritters();
        Critter clyde = null;
        for (var critter: critters){
            if (critter.toString() == "CLYDE"){
                clyde = critter;
                
            }
        }
        if (clyde != null){
            var curPos = clyde.getPos();
            // Debug.out(curPos.toString());
            var curNeighbours = curPos.intNeighbours();
            for(var x : curNeighbours){
                // Debug.out(x.toString());
                // Debug.out("current neighbours length is " + curNeighbours.size());
            }
            
        }

        
    }
}
