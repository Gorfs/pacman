package model;

import geometry.RealCoordinates;

// Critter seems to be the main class used for any object that moves around in the maze with the pac-man
public sealed interface Critter permits Ghost, PacMan {
    float timerAni = 0;
    float[] checkpointAni = new float[2];

    void setTimerAni(float timerAni);
    float getTimerAni();
    float[] getCheckpointAni();

    double getSpeed();

    /**
     * @param deltaTNanoSeconds time since the last update in nanoseconds
     * @return the next position if there is no wall
     */
    default RealCoordinates nextPos(long deltaTNanoSeconds) {
        return getPos().plus((switch (getDirection()) {
            case NONE -> RealCoordinates.ZERO;
            case NORTH -> RealCoordinates.NORTH_UNIT;
            case EAST -> RealCoordinates.EAST_UNIT;
            case SOUTH -> RealCoordinates.SOUTH_UNIT;
            case WEST -> RealCoordinates.WEST_UNIT;
        }).times(getSpeed()*deltaTNanoSeconds * 1E-9));
    }

    /**
    * @param deltaTNanoSeconds time since the last update in nanoseconds
    * @return the next position after the new one if there is no wall
    * */
    default RealCoordinates nextNextPos(long deltaTNanoSeconds) {
        return getPos().plus((switch (getNextDirection()) {
            case NONE -> RealCoordinates.ZERO;
            case NORTH -> RealCoordinates.NORTH_UNIT;
            case EAST -> RealCoordinates.EAST_UNIT;
            case SOUTH -> RealCoordinates.SOUTH_UNIT;
            case WEST -> RealCoordinates.WEST_UNIT;
        }).times(getSpeed()*deltaTNanoSeconds * 1E-9));
    }

    RealCoordinates getPos();
    Direction getDirection();
    Direction getNextDirection();
    void setPos(RealCoordinates realCoordinates);
    void setDirection(Direction direction);
    void setNextDirection(Direction direction);
}
