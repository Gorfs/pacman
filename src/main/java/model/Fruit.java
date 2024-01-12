package model;


/**
 * @param points Number of points the fruit gives
 * @param thresholds the thresholds before spawning a new fruit
 */
public record Fruit(String name, int points, int thresholds) {}
