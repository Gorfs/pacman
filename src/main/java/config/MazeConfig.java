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

/**
 * Class record that create the maze.
 * @param grid Array of cell that represent the maze
 * @param pacManPos used to initialize pacman spawn coordinates
 * @param blinkyPos used to initialize blinky spawn coordinates
 * @param pinkyPos used to initialize pinky spawn coordinates
 * @param inkyPos used to initialize inky spawn coordinates
 * @param clydePos used to initialize clyde spawn coordinates
 */
public record MazeConfig(Cell[][] grid, IntCoordinates pacManPos, IntCoordinates blinkyPos, IntCoordinates pinkyPos,
                         IntCoordinates inkyPos, IntCoordinates clydePos) {
    /**
     * Class record that create the maze.
     * @param grid Array of cell that represent the maze
     * @param pacManPos used to initialize pacman spawn coordinates
     * @param blinkyPos used to initialize blinky spawn coordinates
     * @param pinkyPos used to initialize pinky spawn coordinates
     * @param inkyPos used to initialize inky spawn coordinates
     * @param clydePos used to initialize clyde spawn coordinates
     */
    public MazeConfig(Cell[][] grid, IntCoordinates pacManPos, IntCoordinates blinkyPos, IntCoordinates pinkyPos,
                      IntCoordinates inkyPos, IntCoordinates clydePos) {
        this.grid = new Cell[grid.length][grid[0].length];
        for (int i = 0; i < getHeight(); i++) {
            System.arraycopy(grid[i], 0, this.grid[i], 0, getHeight());
        }
        // Initialize all critter position
        this.pacManPos = pacManPos;
        this.blinkyPos = blinkyPos;
        this.inkyPos = inkyPos;
        this.pinkyPos = pinkyPos;
        this.clydePos = clydePos;
    }

    /**
     * @return width of the maze
     */
    public int getWidth() {
        return grid[0].length;
    }

    /**
     * @return height of the maze
     */
    public int getHeight() {
        return grid.length;
    }

    /**
     * Get the cell at the IntCoordinates position given in argument
     * @param pos position of the cell we want
     * @return the content of the cell
     */
    public Cell getCell(IntCoordinates pos) {
        return grid[Math.floorMod(pos.y(), getHeight())][Math.floorMod(pos.x(), getWidth())];
    }

    /**
     * Method that uses String file to generate the maze
     * @param file name of the file.
     * @return Array of cell that will represent the maze
     */
    public static MazeConfig originalMaze(String file) {
        // New class Cell to store the map
        Cell[][] map = new Cell[21][21];

        // Open the file maze2.txt
        File maze = new File("src/main/resources/" + file + ".txt");
        Scanner myReader;
        // Try if the file exist
        try {
            // Read the file maze2.txt
            myReader = new Scanner(maze);
            int n = 0;
            // while there is something to read
            while (myReader.hasNextLine()) {
                // Get the curent line
                String line = myReader.nextLine();
                // Split everything into a String array
                String[] data = line.split(",");

                // For every 2 string
                for (int i = 0; i < data.length; i++) {
                    // create a cell based on what there is inside(NOTHING, DOT, etc.)
                    switch (data[i]) {
                        case "ENERGIZER" -> map[n][i] = slot(ENERGIZER);
                        case "WALL" -> map[n][i] = slot(WALL);
                        case "DOT" -> map[n][i] = slot(DOT);
                        default -> map[n][i] = slot(NOTHING);
                    }
                }
                n++;
            }
            // close file
            myReader.close();
        } catch (FileNotFoundException e) {
            // If it doesn't find the file
            throw new RuntimeException(e);
        }
        // Init the spawn of the entities
        IntCoordinates player = new IntCoordinates(10, 15),
                blinky = new IntCoordinates(10, 8), inky = new IntCoordinates(11, 9),
                pinky = new IntCoordinates(10, 9), clyde = new IntCoordinates(9, 9);
        // return everything
        return new MazeConfig(map, player, blinky, pinky, inky, clyde);
    }

}
