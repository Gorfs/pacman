package model;

import config.Cell;
import geometry.IntCoordinates;
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

    public static final PacMan INSTANCE = new PacMan();

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


    public void update(long deltaTns){
        // FIXME Pac-Man rules should somehow be in Pacman class
        var pacPos = INSTANCE.getPos().round();
        // Debug.out(config.getCell(new IntCoordinates(pacPos.y(), pacPos.x())).toString());
        if (!MazeState.getGridState()[pacPos.y()][pacPos.x()] && !MazeState.allPointsCollected()) {
            if (MazeState.getConfig().getCell(new IntCoordinates(pacPos.y(), pacPos.x())).initialContent() == Cell.Content.DOT) {
                MazeState.addScore(1);
            }else if (MazeState.getConfig().getCell(pacPos).initialContent() == Cell.Content.ENERGIZER){
                // make the pacman energized -->
                MazeState.addScore(15);
            }
            MazeState.getGridState()[pacPos.y()][pacPos.x()] = true;
        }
    }

}
