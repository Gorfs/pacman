package controllers;

import config.Cell;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import model.Critter;
import model.Direction;
import model.Ghost;
import model.MazeState;
import model.PacMan;

import java.util.Random;

public sealed abstract class GhostsController permits BlinkyController, ClydeController, InkyController, PinkyController {
    // Variable that is used to get a random Direction
    private static final Random rd = new Random();
    // Stock previous pos so that we just check one time if the ghost can turn
    private IntCoordinates previousPos = new IntCoordinates(0, 0);
    // timer to update scatter mode
    float timer = 0;
    // If it can start chasing/scatter
    boolean started = false;

    /**
     * Method that start the ghost AI.
     */
    public abstract void startAI();

    /**
     * This method get the next direction based on the three mode a ghost can be in.
     * @param config current maze
     * @param critter variable that represent a ghost
     * @param deltaTns time passed between two frame
     */
    public void setDirection(Ghost critter, MazeConfig config, long deltaTns) {

        if(MazeState.getGameEnded()){
            return;
        }

        // Update timer for scatter mode
        if (!critter.isScaredMode()) timer += (float) (deltaTns * 1E-9);
        if ((timer >= 7 && !critter.isScatterMode()) || (timer >= 5 && critter.isScatterMode())) {
            timer = 0; critter.changeScatterMode();
        }
        // Next cell the ghost should go
        IntCoordinates result = critter.getPos().round();
        if (!(critter.getPos().round().x() == previousPos.x() && critter.getPos().round().y() == previousPos.y())) {
            if (!started) {
                critter.setNextDirection(waiting(critter));
                previousPos = critter.getPos().round();
                return;
            // if the ghost is scared, it goes in random direction mode
            } else if (critter.isScaredMode()) {
                if (canTurn(critter, config)) {
                    Direction rdDir = randomDirection(critter, critter.getDirection(), config);
                    critter.setNextDirection(rdDir);
                    previousPos = critter.getPos().round();
                    return;
                }
            // if the ghost is in scatter mode, it goes in scatter direction mode
            } else if (critter.isScatterMode()) result = scatterDirection(critter, config);
            else result = nextDirection(critter, config);
            previousPos = critter.getPos().round();
        }
        // get the direction from result
        if (result.x() > critter.getPos().round().x()) critter.setNextDirection(Direction.EAST);
        else if (result.x() < critter.getPos().round().x()) critter.setNextDirection(Direction.WEST);
        else if (result.y() > critter.getPos().round().y()) critter.setNextDirection(Direction.SOUTH);
        else if (result.y() < critter.getPos().round().y()) critter.setNextDirection(Direction.NORTH);
    }

    /**
     * This method is used to get the direction when in scatter mode.
     * @param critter variable that represent a ghost
     * @param config current maze
     * @return direction the ghost should go
     */
    public abstract IntCoordinates scatterDirection(Critter critter, MazeConfig config);

    /**
     * This method is used when ghosts in chasing mode.
     * @param critter variable that represent a ghost
     * @param config current maze
     * @return position the critter should go
     */
    public abstract IntCoordinates nextDirection(Critter critter, MazeConfig config);

    /**
     * This method is used to find a path from the ghost to the goal (for exemple the position of pacman).
     * @param critter variable that represent a ghost
     * @param goal position where the pathfinding ends
     * @param config current maze
     * @return Path from position pos to position goal, coordinates by coordinates
     */
    public IntCoordinates findPathing(Critter critter, IntCoordinates goal, MazeConfig config) {
        if (goal == null) goal = PacMan.INSTANCE.getPos().round();

        IntCoordinates pos = critter.getPos().round();

        // Initialise variable that we will use to find pathing
        double[] distances = {-1.0,-1.0,-1.0,-1.0}; int n = 0;
        IntCoordinates[] voisins = new IntCoordinates[4];
        voisins[0] = pos.toRealCoordinates(1.0).plus(RealCoordinates.NORTH_UNIT).round();
        voisins[1] = pos.toRealCoordinates(1.0).plus(RealCoordinates.EAST_UNIT).round();
        voisins[2] = pos.toRealCoordinates(1.0).plus(RealCoordinates.SOUTH_UNIT).round();
        voisins[3] = pos.toRealCoordinates(1.0).plus(RealCoordinates.WEST_UNIT).round();

        for (var v: voisins) {
            if (config.getCell(v).initialContent() != Cell.Content.WALL)
                if (0 <= pos.x() && 0 <= pos.y() && pos.x() < config.getHeight() && pos.y() < config.getWidth()) {
                    distances[n] = Math.sqrt(Math.pow(v.x() - goal.x(), 2) + Math.pow(v.y() - goal.y(), 2));
                }
            n++;
        } n = 0;
        for (int i = 0; i < distances.length; i++) {
            if (distances[n] == -1.0) n = i;
            if (isDirectionValid(critter.getDirection(), Direction.values()[i + 1], critter, config)) {
                if (distances[i] != -1.0)
                    if (!isDirectionValid(critter.getDirection(), Direction.values()[n + 1], critter, config)) {
                        n = i;
                    } else if (distances[n] >= distances[i]) n = i;
            }
        }
        // Debug.out(n + " " + Arrays.toString(voisins) + " " + Arrays.toString(distances) + " " + critter.getPos().round());
        return voisins[n];
    }

    /**
     * This method is used when ghosts are frightened. When in scared mode, ghosts move randomly.
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
     * This method is used in the ghost home so that can move in a loop instead of not moving.
     * @param critter variable that represent a ghost
     * @return direction the ghost should go
     */
    public Direction waiting(Critter critter) {
        IntCoordinates pos = critter.getPos().round();
        if (conditionOut() && pos.x() == 10 && pos.y() == 9) {
            started = true;return Direction.NORTH;
        } else if (pos.x() == 9 && pos.y() == 9) return Direction.EAST;
        else if (pos.x() == 11 && pos.y() == 9) return Direction.WEST;
        return critter.getDirection();
    }

    /**
     * Blinky -> Always true since it is the first one out
     * Pinky -> Always true, it goes out of the ghost home almost instantly after the game starts
     * Inky -> Goes out after pacman has eaten 30 dots
     * Clyde -> Goes out once one third of the dots are eaten
     * @return true if the condition for each ghost is reached
     */
    public abstract boolean conditionOut();

    /**
     * Check if a direction is valid
     * @param dir1 current direction of critter
     * @param dir2 if critter can turn this way
     * @param critter variable that represent a ghost
     * @param config current map
     * @return true if the ghost can turn dir2 way else false
     */
    public boolean isDirectionValid(Direction dir1, Direction dir2, Critter critter, MazeConfig config) {
        // Can't go back
        if (critter.getPos().plus(getDirection(dir2)).round().x() == previousPos.x() &&
                critter.getPos().plus(getDirection(dir2)).round().y() == previousPos.y()) {
            return false;
        }
        // Can't return in spawn
        IntCoordinates unavailable = critter.getPos().plus(getDirection(dir2)).round();
        if (unavailable.x() == 10 && (unavailable.y() == 8 || unavailable.y() == 9)) return false;

        return switch(dir2){
            case NORTH -> ((dir1 != Direction.SOUTH) && (config.getCell(critter.getPos().plus(RealCoordinates.NORTH_UNIT).round()).initialContent()) != Cell.Content.WALL);
            case SOUTH -> ((dir1 != Direction.NORTH) && (config.getCell(critter.getPos().plus(RealCoordinates.SOUTH_UNIT).round()).initialContent()) != Cell.Content.WALL);
            case EAST -> ((dir1 != Direction.WEST) && (config.getCell(critter.getPos().plus(RealCoordinates.EAST_UNIT).round()).initialContent()) != Cell.Content.WALL);
            case WEST -> ((dir1 != Direction.EAST) && (config.getCell(critter.getPos().plus(RealCoordinates.WEST_UNIT).round()).initialContent()) != Cell.Content.WALL);
            // might need to be changed to false in the future
            default -> false;
        };
    }

    /**
     * Check if the ghost can turn without having to be in front of a wall
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
     * Used to transform a direction into a RealCoordinates unit
     * @param direction variable that represent a direction
     * @return RealCoordinates unit for each direction to add to the ghost pos for the neighbouring cells
     */
    public static RealCoordinates getDirection(Direction direction) {
        return switch (direction) {
            case NORTH -> RealCoordinates.NORTH_UNIT;
            case EAST -> RealCoordinates.EAST_UNIT;
            case SOUTH -> RealCoordinates.SOUTH_UNIT;
            case WEST -> RealCoordinates.WEST_UNIT;
            default -> RealCoordinates.ZERO;
        };
    }
}
