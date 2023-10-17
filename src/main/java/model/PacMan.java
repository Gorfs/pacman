package model;

import geometry.RealCoordinates;

/**
 * Implements Pac-Man character using singleton pattern. FIXME: check whether singleton is really a good idea.
 */
public final class PacMan implements Critter {
    private Direction direction = Direction.NONE;
    private RealCoordinates pos;
    private boolean energized;

    public PacMan() {
    }

    public static PacMan INSTANCE = new PacMan();
    public String name;
    private PacMan(String name){
        this.name=name;
    }
    public PacMan getInstance(String name){
        if(INSTANCE.name==null){
            INSTANCE=new PacMan(name);
        }
        return INSTANCE;
    }
    
    public static String getName(){return INSTANCE.name;}


    @Override
    public RealCoordinates getPos() {
        return pos;
    }

    @Override
    public double getSpeed() {
        return isEnergized() ? 6 : 4;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void setPos(RealCoordinates pos) {
        this.pos = pos;
    }

    /**
     *
     * @return whether Pac-Man just ate an energizer
     */
    public boolean isEnergized() {
        // TODO handle timeout!
        return energized;
    }

    public void setEnergized(boolean energized) {
        this.energized = energized;
    }
}
