package config;

import geometry.IntCoordinates;

import static config.Cell.Content.DOT;
import static config.Cell.*;
import static config.Cell.Content.NOTHING;

public class MazeConfig {
    public MazeConfig(Cell[][] grid, IntCoordinates pacManPos, IntCoordinates blinkyPos, IntCoordinates pinkyPos,
                      IntCoordinates inkyPos, IntCoordinates clydePos) {
        this.grid = new Cell[grid.length][grid[0].length];
        for (int i = 0; i < getHeight(); i++) {
            if (getWidth() >= 0) System.arraycopy(grid[i], 0, this.grid[i], 0, getHeight());
        }
        this.pacManPos = pacManPos;
        this.blinkyPos = blinkyPos;
        this.inkyPos = inkyPos;
        this.pinkyPos = pinkyPos;
        this.clydePos = clydePos;
    }

    private final Cell[][] grid;
    private final IntCoordinates pacManPos, blinkyPos, pinkyPos, inkyPos, clydePos;

    public IntCoordinates getPacManPos() {
        return pacManPos;
    }

    public IntCoordinates getBlinkyPos() {
        return blinkyPos;
    }

    public IntCoordinates getPinkyPos() {
        return pinkyPos;
    }

    public IntCoordinates getInkyPos() {
        return inkyPos;
    }

    public IntCoordinates getClydePos() {
        return clydePos;
    }

    public int getWidth() {
        return grid[0].length;
    }

    public int getHeight() {
        return grid.length;
    }

    public Cell getCell(IntCoordinates pos) {
        return grid[Math.floorMod(pos.y(), getHeight())][Math.floorMod(pos.x(), getWidth())];
    }


    // simple example with a square shape
    // TODO: mazes should be loaded from a text file
    public static MazeConfig makeExample1() {
        return new MazeConfig(new Cell[][]{
                {slot("n", DOT), slot("ns", DOT), slot("ns", DOT), slot("ns", DOT), slot("ns", DOT), slot("n", DOT)},
                {slot("we", DOT), slot("nw", NOTHING), slot("n", NOTHING), slot("n", NOTHING), slot("ne", NOTHING), slot("we", DOT)},
                {slot("we", DOT), slot("w", NOTHING), slot("", NOTHING), slot("", NOTHING), slot("e", NOTHING), slot("we", DOT)},
                {slot("we", DOT), slot("w", NOTHING), slot("", NOTHING), slot("", NOTHING), slot("e", NOTHING), slot("we", DOT)},
                {slot("we", DOT), slot("ws", NOTHING), slot("s", NOTHING), slot("s", NOTHING), slot("se", NOTHING), slot("we", DOT)},
                {slot("ws", DOT), slot("ns", DOT), slot("ns", DOT), slot("ns", DOT), slot("ns", DOT), slot("se", DOT)},
        },
                new IntCoordinates(3, 0),
                new IntCoordinates(0, 3),
                new IntCoordinates(3, 5),
                new IntCoordinates(5, 5),
                new IntCoordinates(5, 1)
        );
    }
}
