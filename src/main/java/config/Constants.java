package config;

import geometry.IntCoordinates;

public class Constants {
    // Initialise lives for each difficulty
    public final static int EASY_LIVES = 4;
    public final static int NORMAL_LIVES = 3;
    public final static int HARD_LIVES = 2;
    public final static int EXPERT_LIVES = 1;

    // Initialise Critters spawn coordinates.
    public final static IntCoordinates PLAYER = new IntCoordinates(10, 15);
    public final static IntCoordinates BLINKY = new IntCoordinates(10, 8);
    public final static IntCoordinates INKY = new IntCoordinates(11, 9);
    public final static IntCoordinates PINKY = new IntCoordinates(10, 9);
    public final static IntCoordinates CLYDE = new IntCoordinates(9, 9);

    // Initialise Window size
    public static int SCALE = 30;

    public static int WINDOW_X = 630;
    public static int WINDOW_Y = 630;

    // String name of file
    public final static String PACMAN_PNG = "/pacman.png";
    public final static String BLINKY_PNG = "/ghosts/ghost_blinky.png";
    public final static String INKY_PNG = "/ghosts/ghost_inky.png";
    public final static String PINKY_PNG = "/ghosts/ghost_pinky.png";
    public final static String CLYDE_PNG = "/ghosts/ghost_clyde.png";

    // Score given by each possible input
    public final static int DOT_SCORE = 10;
    public final static int ENERGIZER_SCORE = 50;
    public final static int GHOST_SCORE = 100;

    // Index of the current map
    public static int MAP_INDEX = 0;
}
