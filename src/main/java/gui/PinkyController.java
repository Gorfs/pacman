package gui;

import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.Node;
import geometry.RealCoordinates;
import model.Critter;
import model.Direction;
import model.Ghost;
import model.PacMan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public final class PinkyController extends GhostsController {
    private int scatterCheckPoint = 1;

    @Override
    public void startAI(String filename) {
        this.filename = filename;
        Ghost.PINKY.setScatterMode(true);
        Ghost.PINKY.setNextDirection(Direction.NORTH);
    }

    @Override
    public Direction scatterPathing(Critter critter, MazeConfig config, String filename) {
        File scatterPath = new File("src/main/resources/ghosts/scatterPath/" + filename + ".txt");
        Scanner myReader;
        try {
            // Open file
            myReader = new Scanner(scatterPath);
            // Get to the line of pinky
            myReader.nextLine();
            String line = myReader.nextLine();
            String[] data = line.split(",");
            IntCoordinates pos = critter.getPos().round();
            IntCoordinates[] pathing;
            if ((Integer.parseInt(data[0]) < pos.x() && (pos.y() == 1 || pos.y() == 2)) || scatterCheckPoint == 1) {
                pathing = findPathing(pos, new IntCoordinates(Integer.parseInt(data[0]), Integer.parseInt(data[1])), config);
                if (Integer.parseInt(data[0]) == pos.x() && Integer.parseInt(data[1]) == pos.y()) {
                    scatterCheckPoint = 2;
                }
            } else if (pos.y() == 1 || pos.y() == 2 || scatterCheckPoint == 2) {
                pathing = findPathing(pos, new IntCoordinates(Integer.parseInt(data[2]), Integer.parseInt(data[3])), config);
                if (Integer.parseInt(data[2]) == pos.x() && Integer.parseInt(data[3]) == pos.y()) {
                    scatterCheckPoint = 3;
                }
            } else {
                pathing = findPathing(pos, new IntCoordinates(Integer.parseInt(data[4]), Integer.parseInt(data[5])), config);
                if (Integer.parseInt(data[4]) == pos.x() && Integer.parseInt(data[5]) == pos.y()) {
                    scatterCheckPoint = 1;
                }
            }

            if (pathing.length > 1) {
                if (critter.getPos().round().equals(pathing[0])) {
                    if (pathing[0].x() < pathing[1].x()) return Direction.EAST;
                    else if (pathing[0].x() > pathing[1].x()) return Direction.WEST;
                    else if (pathing[0].y() < pathing[1].y()) return Direction.SOUTH;
                    else return Direction.NORTH;
                }
            } myReader.close();
            return critter.getDirection();

        } catch (FileNotFoundException e) {
            // If it doesn't find the file
            throw new RuntimeException(e);
        }
    }

    @Override
    public Direction nextDirection(Critter critter, MazeConfig config) {
        Direction result = critter.getDirection();
        if (result == Direction.NONE) {return Direction.NORTH;}

        IntCoordinates[] pathing = findPathing(critter.getPos().round(), null, config);

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

    public IntCoordinates[] findPathing(IntCoordinates pos, IntCoordinates goal, MazeConfig config) {
        // Get the position of pacman
        Node objectif;
        Node depart = new Node(pos, null);
        IntCoordinates[] pathing;
        if (goal != null) objectif = new Node(goal, null);
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
        for (int i = 1; i <= getLength(pathing); i++) {
            temp = pathing[getLength(pathing) - i];
            result[i] = temp;
        } System.out.println(Arrays.toString(pathing)); System.out.println(Arrays.toString(result));
        return result;
    }

    public static int getLength(IntCoordinates[] tab) {
        int n = 0;
        for (var value: tab) {
            if (value != null) n++;
        } return n;
    }
}
