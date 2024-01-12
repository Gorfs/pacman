package config;

import geometry.IntCoordinates;

public class Constants {
    // Initialise lives for each difficulty
    public final static int EASY_LIVES = 4;
    public final static int NORMAL_LIVES = 3;
    public final static int HARD_LIVES = 2;
    public final static int EXPERT_LIVES = 1;

    // Initialise Critters spawn coordinates.
    public static IntCoordinates PLAYER = new IntCoordinates(10, 15);
    public static IntCoordinates BLINKY = new IntCoordinates(10, 8);
    public static IntCoordinates INKY = new IntCoordinates(11, 9);
    public static IntCoordinates PINKY = new IntCoordinates(10, 9);
    public static IntCoordinates CLYDE = new IntCoordinates(9, 9);

    // Initialise Window size
    public static int SCALE = 30;

    public static int WINDOW_X = 630;
    public static int WINDOW_Y = 630;

    // String name of file
    public final static String PACMAN_PNG = "/critters/pacman.png";
    public final static String BLINKY_PNG = "/critters/ghost_blinky.png";
    public final static String INKY_PNG = "/critters/ghost_inky.png";
    public final static String PINKY_PNG = "/critters/ghost_pinky.png";
    public final static String CLYDE_PNG = "/critters/ghost_clyde.png";

    // Score given by each possible input
    public final static int DOT_SCORE = 10;
    public final static int ENERGIZER_SCORE = 50;
    public final static int GHOST_SCORE = 100;
}
