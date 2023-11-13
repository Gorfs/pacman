package gui;

import config.MazeConfig;
import model.Critter;
import model.Direction;
import model.Ghost;

import java.util.Random;

public sealed abstract class GhostsController permits ClydeController, PinkyController {
    public static final Random rd = new Random();

    public abstract void startAI();

    public void setDirection(MazeConfig config, Ghost critter) {
        Direction result;
        if (critter.isScatterMode()) result = scatterPathing(critter, config);
        else result = nextDirection(critter, config);
        critter.setNextDirection(result);
    }

    public abstract Direction scatterPathing(Critter critter, MazeConfig config);

    public abstract Direction nextDirection(Critter critter, MazeConfig config);
}
