package config;

import geometry.IntCoordinates;

import static config.Cell.*;
import static config.Cell.Content.*;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Class record that create the maze.
 * @param grid Array of cell that represent the maze
 * @param pacManPos used to initialize pacman spawn coordinates
 * @param blinkyPos used to initialize BLINKY spawn coordinates
 * @param pinkyPos used to initialize PINKY spawn coordinates
 * @param inkyPos used to initialize INKY spawn coordinates
 * @param clydePos used to initialize clyde spawn coordinates
 */
public record MazeConfig(Cell[][] grid, IntCoordinates pacManPos, IntCoordinates blinkyPos, IntCoordinates pinkyPos,
                         IntCoordinates inkyPos, IntCoordinates clydePos) {
    /**
     * Class record that create the maze.
     * @param grid Array of cell that represent the maze
     * @param pacManPos used to initialize PLAYER spawn coordinates
     * @param blinkyPos used to initialize BLINKY spawn coordinates
     * @param pinkyPos used to initialize PINKY spawn coordinates
     * @param inkyPos used to initialize INKY spawn coordinates
     * @param clydePos used to initialize CLYDE spawn coordinates
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
        Cell[][] maze = new Cell[21][21];
        InputStream is = MazeConfig.class.getResourceAsStream("/mazes/" + file + ".txt");
        Scanner myReader;
        assert is != null;
        myReader = new Scanner(is);
        int line = 0;
        // Init the spawn of the entities
        IntCoordinates player = new IntCoordinates(10, 15),
                blinky = new IntCoordinates(10, 7), inky = new IntCoordinates(10, 9),
                pinky = new IntCoordinates(11, 9), clyde = new IntCoordinates(9, 9);
        // while there is something to read
        while (myReader.hasNextLine()) {
            // Get the curent nextLine
            String nextLine = myReader.nextLine();
            // Split everything into a String array
            String[] data = nextLine.split(",");
            if (line < maze.length) {
                for (int i = 0; i < data.length; i++) {
                    // create a cell based on what there is inside(NOTHING, DOT, etc.)
                    maze[line][i] = switch (data[i]) {
                        case "E" -> slot(ENERGIZER);
                        case "W" -> slot(WALL);
                        case "D" -> slot(DOT);
                        default -> slot(NOTHING);
                    };
                }
            } else if (line == maze.length) {
                try {
                    // Init the spawn of the entities
                    player = new IntCoordinates(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                    blinky = new IntCoordinates(Integer.parseInt(data[2]), Integer.parseInt(data[3]));
                    inky = new IntCoordinates(Integer.parseInt(data[4]), Integer.parseInt(data[5]));
                    pinky = new IntCoordinates(Integer.parseInt(data[6]), Integer.parseInt(data[7]));
                    clyde = new IntCoordinates(Integer.parseInt(data[8]), Integer.parseInt(data[9]));
                } catch (Exception e) {
                    System.out.println("Erreur dans la lecture du fichier, impossible de lire la position de départ des entités");
                }
                Constants.PLAYER = player;
                Constants.BLINKY = blinky;
                Constants.INKY = inky;
                Constants.PINKY = pinky;
                Constants.CLYDE = clyde;
            } line++;
        } myReader.close();

        return new MazeConfig(maze, player, blinky, pinky, inky, clyde);
    }

}
