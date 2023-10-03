package model;

import geometry.RealCoordinates;

/**
 * Implements Pac-Man character using singleton pattern. FIXME: check whether singleton is really a good idea.
 */
public final class PacMan implements Critter {
    private Direction direction = Direction.NONE;
    private RealCoordinates pos;
    private boolean energized;

    private PacMan() {
    }
    /**On pourra mettre dans le menu le choix du nom qui s'affichera au dessus du pacman,
     * en plus si on refait un round, le pacman garde quand meme le nom en l'initialisant
     */
    public static PacMan INSTANCE = new PacMan();
    public String name;
    private PacMan(String name){
        this.name=name;
    }
    public static PacMan getInstance(String name){
        if(INSTANCE==null){
            INSTANCE=new PacMan(name);
        }
        return INSTANCE;
    }

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
        // TODO handle timeout! le temps que ca dure
        return energized;
    }

    public void setEnergized(boolean energized) {
        this.energized = energized;
    }
}
