package gui;

import config.Cell;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import model.Critter;
import model.Direction;
import model.Ghost;
import model.PacMan;

import java.util.Arrays;
import java.util.Random;

public sealed abstract class GhostsController permits BlinkyController, ClydeController, PinkyController {
    public static final Random rd = new Random();
    public IntCoordinates previousPos = new IntCoordinates(0, 0);

    /**
     * Method that start the ghost AI.
     */
    public abstract void startAI();

    /**
     * @param config current maze
     * @param critter variable that represent a ghost
     */
    public void setDirection(Ghost critter, MazeConfig config) {
        // Next cell the ghost should go
        IntCoordinates result = critter.getPos().round();
        if (!(critter.getPos().round().x() == previousPos.x() && critter.getPos().round().y() == previousPos.y())) {
            previousPos = critter.getPos().round();
            if (critter.isScaredMode()) {
                if (canTurn(critter, config)) {
                    System.out.println('a');
                    critter.setNextDirection(randomDirection(critter, critter.getDirection(), config));
                    return;
                }
            } else if (critter.isScatterMode()) result = scatterDirection(critter, config);
            else result = nextDirection(critter, config);
        }
        // get the direction from result
        if (result.x() > critter.getPos().round().x()) critter.setNextDirection(Direction.EAST);
        else if (result.x() < critter.getPos().round().x()) critter.setNextDirection(Direction.WEST);
        else if (result.y() > critter.getPos().round().y()) critter.setNextDirection(Direction.SOUTH);
        else if (result.y() > critter.getPos().round().y()) critter.setNextDirection(Direction.NORTH);
    }

    /**
     * @param critter variable that represent a ghost
     * @param config current maze
     * @return direction the ghost should go
     */
    public abstract IntCoordinates scatterDirection(Critter critter, MazeConfig config);

    /**
     * @param critter variable that represent a ghost
     * @param config current maze
     * @return direction the critter should go
     */
    public abstract IntCoordinates nextDirection(Critter critter, MazeConfig config);

    /**
     * @param critter variable that represent a ghost
     * @param goal position where the pathfinding ends
     * @param config current maze
     * @return Path from position pos to position goal, coordinates by coordinates
     */
    public IntCoordinates findPathing(Critter critter, IntCoordinates goal, MazeConfig config) {
        if (goal == null) goal = PacMan.INSTANCE.getPos().round();

        IntCoordinates pos = critter.getPos().round();

        IntCoordinates[] voisins = new IntCoordinates[4];
        double[] distances = {-1.0,-1.0,-1.0,-1.0}; int n = 0;
        voisins[0] = pos.toRealCoordinates(1.0).plus(RealCoordinates.NORTH_UNIT).round();
        voisins[1] = pos.toRealCoordinates(1.0).plus(RealCoordinates.EAST_UNIT).round();
        voisins[2] = pos.toRealCoordinates(1.0).plus(RealCoordinates.SOUTH_UNIT).round();
        voisins[3] = pos.toRealCoordinates(1.0).plus(RealCoordinates.WEST_UNIT).round();

        for (var v: voisins) {
            if (config.getCell(v).initialContent() != Cell.Content.WALL)
                if (0 < pos.x() && 0 < pos.y() && pos.x() < config.getHeight() && pos.y() < config.getWidth()) {
                    distances[n] = Math.sqrt(Math.pow(v.x() - goal.x(), 2) + Math.pow(v.y() - goal.y(), 2));
                }
            n++;
        } n = 0;
        for (int i = 0; i < distances.length; i++) {
            if (distances[n] == -1.0) n = i;
            if (isDirectionValid(critter.getDirection(), Direction.values()[i + 1], critter, config)) {
                if (distances[i] != -1.0 && distances[n] > distances[i]) {
                    n = i;
                }
            }
        }
        System.out.println(n + " " + Arrays.toString(voisins) + " " + Arrays.toString(distances) + " " + critter.getPos().round());
        return voisins[n];
    }

    /**
     * @param critter variable that represent a ghost
     * @param result direction it should go
     * @param config current maze
     * @return direction the ghost should go
     */
    public Direction randomDirection(Critter critter, Direction result, MazeConfig config) {
        while(!isDirectionValid(critter.getDirection(), result, critter, config)){
            // outputs a random direction
            result = switch(rd.nextInt(0,4)){
                case 0 -> Direction.NORTH;
                case 2 -> Direction.SOUTH;
                case 3 -> Direction.WEST;
                default -> Direction.EAST;
            };
        } return result;
    }

    /**
     * @param dir1 current direction of critter
     * @param dir2 if critter can turn this way
     * @param critter variable that represent a ghost
     * @param config current map
     * @return true if the ghost can turn dir2 way else false
     */
    public boolean isDirectionValid(Direction dir1, Direction dir2, Critter critter, MazeConfig config) {
        IntCoordinates unavailable = critter.getPos().plus(RealCoordinates.SOUTH_UNIT).round();
        if (unavailable.x() == 10 && unavailable.y() == 8) return false;
        // the point of this function is to make sure the ghost doesn't turn back on itself
        if (dir1 == dir2) return true;
        else{
            return switch(dir2){
                case NORTH -> ((dir1 != Direction.SOUTH) && (config.getCell(critter.getPos().plus(RealCoordinates.NORTH_UNIT).round()).initialContent()) != Cell.Content.WALL);
                case SOUTH -> ((dir1 != Direction.NORTH) && (config.getCell(critter.getPos().plus(RealCoordinates.SOUTH_UNIT).round()).initialContent()) != Cell.Content.WALL);
                case EAST -> ((dir1 != Direction.WEST) && (config.getCell(critter.getPos().plus(RealCoordinates.EAST_UNIT).round()).initialContent()) != Cell.Content.WALL);
                case WEST -> ((dir1 != Direction.EAST) && (config.getCell(critter.getPos().plus(RealCoordinates.WEST_UNIT).round()).initialContent()) != Cell.Content.WALL);
                // might need to be changed to false in the future
                default -> false;
            };
        }
    }

    /**
     * @param critter variable that represent a ghost
     * @param config current maze
     * @return true if the ghost can turn else false
     */
    public boolean canTurn(Critter critter, MazeConfig config) {
        Direction dir = critter.getDirection();
        switch (dir) {
            case NORTH, SOUTH -> {
                if (config.getCell(critter.getPos().plus(RealCoordinates.EAST_UNIT).round()).initialContent() != Cell.Content.WALL) {return true;}
                if (config.getCell(critter.getPos().plus(RealCoordinates.WEST_UNIT).round()).initialContent() != Cell.Content.WALL) {return true;}
            }
            case WEST, EAST -> {
                if (config.getCell(critter.getPos().plus(RealCoordinates.NORTH_UNIT).round()).initialContent() != Cell.Content.WALL) {return true;}
                if (config.getCell(critter.getPos().plus(RealCoordinates.SOUTH_UNIT).round()).initialContent() != Cell.Content.WALL) {return true;}
            }
        } return false;
    }

    /**
     * @param critter variable that represent a ghost
     * @return RealCoordinates unit for each direction to add to the ghost pos for the neighbouring cells
     */
    public static RealCoordinates getDirection(Critter critter) {
        return switch (critter.getDirection()) {
            case NORTH -> RealCoordinates.NORTH_UNIT;
            case EAST -> RealCoordinates.EAST_UNIT;
            case SOUTH -> RealCoordinates.SOUTH_UNIT;
            case WEST -> RealCoordinates.WEST_UNIT;
            default -> RealCoordinates.ZERO;
        };
    }
}
