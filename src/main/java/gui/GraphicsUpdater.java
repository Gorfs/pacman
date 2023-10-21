package gui;

import javafx.scene.Node;

public interface GraphicsUpdater {
    void update(long deltaT);
    Node getNode();
}
