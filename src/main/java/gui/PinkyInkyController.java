package gui;

import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.Node;
import model.Critter;
import model.Direction;
import model.Ghost;
import model.PacMan;


public final class PinkyInkyController extends GhostsController {

    @Override
    public void startAI() {
        Ghost.PINKY.setNextDirection(Direction.NORTH);
    }

    @Override
    public Direction nextDirection(Critter critter, MazeConfig config) {
        Direction result = critter.getDirection();
        if (result == Direction.NONE) {return Direction.NORTH;}

        // Get the position behind pacman
        IntCoordinates pacPosInt = PacMan.INSTANCE.getPos().round();
        IntCoordinates[] pathing = findPathing(critter.getPos().round(), pacPosInt, config);


        if (pathing.length > 1) {
            if (critter.getPos().round().equals(pathing[0])) {
                if (pathing[0].x() < pathing[1].x()) result = Direction.EAST;
                else if (pathing[0].x() > pathing[1].x()) result = Direction.WEST;
                else if (pathing[0].y() < pathing[1].y()) result = Direction.SOUTH;
                else result = Direction.NORTH;
            }
        }
        return result;
    }

    public IntCoordinates[] findPathing(IntCoordinates pos, IntCoordinates pacPos, MazeConfig config) {
        Node objectif = new Node(pacPos, null);
        Node depart = new Node(pos, null);
        return revert(depart.cheminPlusCourt(objectif, config), pos);
    }

    public static IntCoordinates[] revert(IntCoordinates[] pathing, IntCoordinates pos) {
        IntCoordinates[] result = new IntCoordinates[getLength(pathing) + 1];
        result[0] = pos;
        IntCoordinates temp;
        for (int i = 1; i < result.length; i++) {
            temp = pathing[getLength(pathing) - i];
            result[i] = temp;
        } return result;
    }

    public static int getLength(IntCoordinates[] tab) {
        int n = 0;
        for (var value: tab) {
            if (value != null) n++;
        } return n;
    }
}
