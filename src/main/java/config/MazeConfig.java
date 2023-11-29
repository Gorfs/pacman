package config;

import geometry.IntCoordinates;

import static config.Cell.*;
import static config.Cell.Content.*;

import java.io.InputStream;
// Import the Scanner class to read text files
import java.util.Scanner;
import java.util.Random;

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

    public Cell[][] getGrid(){
        return grid;
    }

    public static MazeConfig originalMaze(String file) {
        // New class Cell to store the map
        Cell[][] map = new Cell[21][21];
        // Open the file maze.txt
        InputStream is = MazeConfig.class.getResourceAsStream("/" + file + ".txt");
        Scanner myReader;
        // Read the file maze.txt
        myReader = new Scanner(is);
        int n = 0;
        // while there is something to read
        while (myReader.hasNextLine()) {
            // Get the curent line
            String line = myReader.nextLine();
            // Split everything into a String array
            String[] data = line.split(",");

            // For every 2 string
            for (int i = 0; i < data.length; i ++) {
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

        Random rand = new Random();
        for(int i=0; i<map.length; i++){
            for(int j=0; j<map[i].length; j++){
                if(map[i][j].initialContent() == Cell.Content.DOT && rand.nextDouble() < 0.05){
                    map[i][j] = new Cell(Cell.Content.CHERRY);
                }
            }
        }
        // Init the spawn of the entities
        IntCoordinates player = new IntCoordinates(10, 15),
                blinky = new IntCoordinates(10, 8), inky = new IntCoordinates(11, 9),
                pinky = new IntCoordinates(10, 9), clyde = new IntCoordinates(9, 9);
        // return everything
        return new MazeConfig(map, player, blinky, pinky, inky, clyde);
    }

}
 