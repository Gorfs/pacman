package model;

import geometry.RealCoordinates;

public enum Ghost implements Critter {
    BLINKY, INKY, PINKY, CLYDE;

    private RealCoordinates pos;
    private Direction direction = Direction.NONE;
    private Direction nextDirection = Direction.NONE;
    private boolean scatterMode = true;
    private boolean scaredMode = false;

    private float timerAni = 0;
    private final float[] checkpointAni = {0.25F,0.5F};
    private double speed = 2;

    @Override
    public float[] getCheckpointAni() {
        return checkpointAni;
    }

    @Override
    public void setTimerAni(float timerAni) {
        this.timerAni = timerAni;
    }

    @Override
    public float getTimerAni() {
        return timerAni;
    }

    @Override
    public RealCoordinates getPos() {
        return pos;
    }

    @Override
    public void setPos(RealCoordinates newPos) {
        pos = newPos;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public Direction getNextDirection() {
        return nextDirection;
    }

    @Override
    public void setNextDirection(Direction nextDirection) {
        this.nextDirection = nextDirection;
    }

    @Override
    public double getSpeed() {
        // speed is constant, we should probably get this from a file such as a CONSTANT.json
        return speed;
    }

    @Override
    public void setSpeed(double speed){
        this.speed = speed;
    }

    public boolean isScatterMode() {
        return scatterMode;
    }

    public void changeScatterMode() {
        this.scatterMode = !this.scatterMode;

    }

    public boolean isScaredMode() {
        return scaredMode;
    }

    public void setScaredMode(boolean scaredMode) {
        this.scaredMode = scaredMode;
    }
}
