package model;

import geometry.RealCoordinates;

public enum Ghost implements Critter {
    BLINKY, INKY, PINKY, CLYDE;

    private RealCoordinates pos;
    private Direction direction = Direction.NONE;
    private Direction nextDirection = Direction.NONE;
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
        this.direction = this.nextDirection;
        this.nextDirection = direction;
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
        return 2;
    }

}