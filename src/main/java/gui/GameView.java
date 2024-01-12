package gui;

import geometry.IntCoordinates;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import model.MazeState;
import model.PacMan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameView {
    // class parameters
    private final MazeState maze;
    private static Pane gameRoot; // main node of the game

    private final List<GraphicsUpdater> graphicsUpdaters;

    private void addGraphics(GraphicsUpdater updater) {
        gameRoot.getChildren().add(updater.getNode());
        graphicsUpdaters.add(updater);
    }

    /**
     * @param maze  the "model" of the view (the labyrinth and everything there is)
     * @param root  the root in the scene JavaFX in which the game will be displayed
     * @param scale scaling of one cell of the labyrinth
     */
    public GameView(MazeState maze, Pane root, double scale) throws Exception {
        this.maze = maze;
        gameRoot = root;
        // pixels per cell
        root.setMinWidth(maze.getWidth() * scale);
        // adding 80 pixels at the end for the display in game
        root.setMinHeight(maze.getHeight() * scale + 80);
        root.setStyle("-fx-background-color: #000000");
        var critterFactory = new CritterGraphicsFactory(scale);
        var cellFactory = new CellGraphicsFactory(scale);
        // Initialise GameOver
        var gameover = new GameOver();
        // Initialise the Menu
        var menu = new InGameDisplay(scale * 1);
        graphicsUpdaters = new ArrayList<>();

        for (int x = 0; x < maze.getWidth(); x++)
            for (int y = 0; y < maze.getHeight(); y++)
                addGraphics(cellFactory.makeGraphics(maze, new IntCoordinates(x, y)));
        for (var critter : MazeState.getCritters()) addGraphics(critterFactory.makeGraphics(critter));
        addGraphics(gameover.makeGraphics());
        addGraphics(menu.makeGraphics());
    }

    public AnimationTimer animate() {
        return new AnimationTimer() {
            long last = 0;
            int count = 0;
            long fpsCount = 0;

            @Override
            public void handle(long now) {
                if (last == 0) { // ignore the first tick, just compute the first deltaT
                    last = now;
                    fpsCount = now;
                    return;
                }

                var deltaT = now - last;

                count++;
                if (now - fpsCount >= 1) {
                    fpsCount = now - fpsCount;
                    System.out.println(((int) (count/((int)deltaT*1E-9))) + " FPS");
                    count = 0;
                }

                if (!PacMan.INSTANCE.getIsDying())
                    maze.update(deltaT);

                for (var updater : graphicsUpdaters) {
                    try {
                        updater.update(deltaT);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                // Removed this update from loop for because we just need to call it once.
                PacMan.INSTANCE.update(deltaT);
                if (PacMan.INSTANCE.isEnergized()) PacMan.INSTANCE.updateEnergizer(deltaT);
                last = now;
            }
        };
    }
}
