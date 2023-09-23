package config;

import geometry.IntCoordinates;

import static config.Cell.*;
import static config.Cell.Content.*;

// Import the File class
import java.io.File;
// Import this class to handle errors
import java.io.FileNotFoundException;
// Import the Scanner class to read text files
import java.util.Scanner;

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

    public static MazeConfig mazeFromFile(){
        // New class Cell to store the map
        Cell[][] map = new Cell[6][6];

        // Open the file maze.txt
        File maze = new File("src/main/java/config/maze.txt");
        Scanner myReader;
        // Try if the file exist
        try {
            // Read the file maze.txt
            myReader = new Scanner(maze);
            int n = 0;
            // while there is something to read
            while (myReader.hasNextLine()) {
                // Get the curent line
                String line = myReader.nextLine();
                // Split everything into a String array
                String[] data = line.split(",");
                // For every 2 string
                for (int i = 0; i < data.length; i += 2) {
                    // create a cell based on if there is something(NOTHING, DOT, etc.) or not and place wall(data[i])
                    switch (data[i + 1]) {
                        case "ENERGIZER" -> map[n][i/2] = slot(data[i], ENERGIZER);
                        case "NOTHING" -> map[n][i/2] = slot(data[i], NOTHING);
                        case "DOT" -> map[n][i/2] = slot(data[i], DOT);
                    }
                }
                n++;
            }
            // close file
            myReader.close();
        } catch (FileNotFoundException e) {
            // If it doesn't found the file
            throw new RuntimeException(e);
        }
        // Init the spawn of the entities
        IntCoordinates player = new IntCoordinates(3, 1),
                blinky = new IntCoordinates(0, 3), inky = new IntCoordinates(3, 5),
                pinky = new IntCoordinates(5, 5), clyde = new IntCoordinates(5, 1);
        // return everything
        return new MazeConfig(map, player, blinky, inky, pinky, clyde);
    }
}
