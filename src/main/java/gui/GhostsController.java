package gui;

import config.MazeConfig;
import model.Critter;
import model.Direction;
import model.Ghost;

import java.util.Random;

public sealed abstract class GhostsController permits ClydeController, PinkyController {
    public static final Random rd = new Random();
    public String filename = "maze2";

    public abstract void startAI(String filename);

    public void setDirection(MazeConfig config, Ghost critter) {
        Direction result;
        if (critter.isScatterMode()) result = scatterPathing(critter, config, filename);
        else do {
            result = nextDirection(critter, config);
        } while (critter.getPos().round().x() == 10 && critter.getPos().round().y() == 7 && result == Direction.SOUTH);
        critter.setNextDirection(result);
    }

    public abstract Direction scatterPathing(Critter critter, MazeConfig config, String filename);

    public abstract Direction nextDirection(Critter critter, MazeConfig config);
}
