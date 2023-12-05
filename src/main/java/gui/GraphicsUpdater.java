package gui;

import javafx.scene.Node;

import java.io.IOException;

public interface GraphicsUpdater {
    void update(long deltaT) throws IOException;
    Node getNode();
}
