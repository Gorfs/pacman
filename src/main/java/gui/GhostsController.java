package gui;

import config.MazeConfig;
import model.Critter;
import model.Direction;

import java.util.Random;

public sealed abstract class GhostsController permits ClydeController, PinkyInkyController {
    public static final Random rd = new Random();

    public abstract void startAI();

    public void setDirection(MazeConfig config, Critter critter) {
        Direction result = nextDirection(critter, config);
        critter.setNextDirection(result);
    }

    public abstract Direction nextDirection(Critter critter, MazeConfig config);
}
