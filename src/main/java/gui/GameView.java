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
     * @param maze  le "modèle" de cette vue (le labyrinthe et tout ce qui s'y trouve)
     * @param root  le nœud racine dans la scène JavaFX dans lequel le jeu sera affiché
     * @param scale le nombre de pixels représentant une unité du labyrinthe
     */
    public GameView(MazeState maze, Pane root, double scale) throws Exception {
        this.maze = maze;
        gameRoot = root;
        // pixels per cell
        // double w = maze.getWidth() * 1.5;
        // double h = maze.getHeight() * 1.5;
        root.setMinWidth(maze.getWidth()*scale);
        root.setMinHeight(maze.getHeight()*scale);    
        root.setStyle("-fx-background-color: #000000");
        var critterFactory = new CritterGraphicsFactory(scale);
        var cellFactory = new CellGraphicsFactory(scale);
        var gameover = new GameOver(scale * 1); //On initialise le GameOver
        var menu = new Menu(scale * 1); //On initialise le Menu
        graphicsUpdaters = new ArrayList<>();

        for (var critter : MazeState.getCritters()) addGraphics(critterFactory.makeGraphics(critter));
        for (int x = 0; x < maze.getWidth(); x++)
            for (int y = 0; y < maze.getHeight(); y++)
                addGraphics(cellFactory.makeGraphics(maze, new IntCoordinates(x, y)));
        addGraphics(gameover.makeGraphics(maze, new IntCoordinates(0,0))); //Pour pouvoir afficher le GameOver   
        addGraphics(menu.makeGraphics(maze, new IntCoordinates(0, 0))); //Pour pouvoir afficher le Menu
    }

    public void animate() {
        new AnimationTimer() {
            long last = 0;

            @Override
            public void handle(long now) {
                if (last == 0) { // ignore the first tick, just compute the first deltaT
                    last = now;
                    return;
                }

                var deltaT = now - last;
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
                last = now;
            }
        }.start();
    }
}
