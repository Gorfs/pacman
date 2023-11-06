package geometry;

import config.Cell;
import config.MazeConfig;

import java.util.HashSet;
import java.util.Set;

public class Node {
    private IntCoordinates pos;
    private final Node parent;
    private int cost, heuristic;

    public Node(IntCoordinates pos, Node parent) {
        this.pos = pos;
        this.cost = 0;
        this.heuristic = 0;
        this.parent = parent;
    }

    public Node compareNode(Node node) {
        int value1 = this.cost + this.heuristic;
        int value2 = node.cost + node.heuristic;
        if (value1 < value2) return this;
        return node;
    }

    public IntCoordinates[] cheminPlusCourt(Node objectif, MazeConfig config) {
        Node[] closedList = new Node[200];
        int n = 0;
        Set<Node> openList = new HashSet<>();
        openList.add(this);
        while (!(openList.isEmpty())) {
            Node u = filePrioritaire(openList);
            if (u.pos.x() == objectif.pos.x() && u.pos.y() == objectif.pos.y()) {
                return u.reconstituerChemin(closedList, this);
            }
            // Generate neighbour node
            Node[] voisins = new Node[4];
            voisins[0] = new Node(u.pos.toRealCoordinates(1.0).plus(RealCoordinates.NORTH_UNIT).round(), u);
            voisins[1] = new Node(u.pos.toRealCoordinates(1.0).plus(RealCoordinates.EAST_UNIT).round(), u);
            voisins[2] = new Node(u.pos.toRealCoordinates(1.0).plus(RealCoordinates.SOUTH_UNIT).round(), u);
            voisins[3] = new Node(u.pos.toRealCoordinates(1.0).plus(RealCoordinates.WEST_UNIT).round(), u);
            for (var v: voisins) {
                if (config.getCell(v.pos).initialContent() != Cell.Content.WALL && 0 < u.pos.x() && 0 < u.pos.y() && u.pos.x() < config.getHeight() && u.pos.y() < config.getWidth()) {
                    if (!(contain(closedList, v.pos) || v.existInferiorCost(openList))) {
                        v.cost = u.cost + 1;
                        v.heuristic = v.cost + (Math.abs(v.pos.x() - objectif.pos.x()) + Math.abs(v.pos.y() - objectif.pos.y()));
                        openList.add(v);
                    }
                }
            } closedList[n] = u; n++;

        } throw new RuntimeException("Aucun chemin possible trouvé.");

    }

    private IntCoordinates[] reconstituerChemin(Node[] closedList, Node depart) {
        IntCoordinates[] path = new IntCoordinates[getLength(closedList)];
        if (getLength(closedList) != 0) {
            path[0] = this.pos;
            Node temp = this;
            int n = 1;
            while (temp.parent != depart) {
                path[n] = temp.pos;
                        n++;
                        temp = temp.parent;
            }
        }
        return path;
    }

    public static Node filePrioritaire(Set<Node> openList) {
        Node u = null;
        for (var node: openList) {
            if (u == null) {
                u = node;
            } else {
                u = u.compareNode(node);
            }
        } openList.remove(u);
        return u;
    }

    public boolean existInferiorCost(Set<Node> openList) {
        for (var node: openList) {
            if (node.pos.x() == this.pos.x() && node.pos.y() == this.pos.y()) {
                if (node.cost < this.cost) return true;
            }
        } return false;
    }

    public static boolean contain(Node[] tab, IntCoordinates val) {
        for (var value: tab) if (value != null) if (value.pos.x() == val.x() && value.pos.y() == val.y()) return true;
        return false;
    }

    @Override
    public String toString() {
        return (this.pos.toString());
    }

    public IntCoordinates getPos() {
        return pos;
    }

    public void setPos(IntCoordinates pos) {
        this.pos = pos;
    }

    public static int getLength(Node[] tab) {
        int n = 0;
        for (var value: tab) {
            if (value != null) n++;
        } return n;
    }
}
