package gui;

import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.Node;
import geometry.RealCoordinates;
import model.Critter;
import model.Direction;
import model.Ghost;
import model.PacMan;


public final class PinkyController extends GhostsController {

    @Override
    public void startAI() {
        Ghost.PINKY.setNextDirection(Direction.NORTH);
    }

    @Override
    public Direction scatterPathing(Critter critter, MazeConfig config) {
        return null;
    }

    @Override
    public Direction nextDirection(Critter critter, MazeConfig config) {
        Direction result = critter.getDirection();
        if (result == Direction.NONE) {return Direction.NORTH;}

        IntCoordinates[] pathing = findPathing(critter.getPos().round(), config);


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

    public IntCoordinates[] findPathing(IntCoordinates pos, MazeConfig config) {
        // Get the position of pacman
        Node objectif;
        Node depart = new Node(pos, null);
        IntCoordinates[] pathing;
        if (PacMan.INSTANCE.getDirection() == Direction.NONE) objectif = new Node(PacMan.INSTANCE.getPos().round(), null);
        else {
            int n = 3;
            RealCoordinates direction = RealCoordinates.ZERO;
            switch (PacMan.INSTANCE.getDirection()) {
                case NORTH -> direction = RealCoordinates.NORTH_UNIT;
                case EAST -> direction = RealCoordinates.EAST_UNIT;
                case SOUTH -> direction = RealCoordinates.SOUTH_UNIT;
                case WEST -> direction = RealCoordinates.WEST_UNIT;
            }

            RealCoordinates pacPos;
            do {
                pacPos = PacMan.INSTANCE.getPos().plus(direction.times(n));
                n--;
                IntCoordinates unavailable = pacPos.plus(direction.times(-1)).round();
                pathing = depart.cheminPlusCourt(new Node(pacPos.round(), null), unavailable, config);
            } while (pathing.length == 0 || !(n <= 0));
            return revert(pathing, pos);
        }
        pathing = depart.cheminPlusCourt(objectif, new IntCoordinates(0, 0), config);
        return revert(pathing, pos);
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
