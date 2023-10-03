package model;

import java.util.Random;
import config.MazeConfig;
import javax.swing.text.Position;
import java.util.List;


import misc.Debug;

public class ClydeController {

    Random rd = new Random();
    public void startAI(){
        Debug.out("CLYDE AI started");
        Ghost.CLYDE.setDirection(Direction.EAST);
    }
    public static void setNextPosition(){
        // should return the next positon of the ghost
        List<Critter> critters = MazeState.getCritters();
        Critter clyde;
        for (var critter: critters){
            if (critter.toString() == "CLYDE"){
                critter.setDirection(Direction.EAST);
            }
        }
        
    }
}
