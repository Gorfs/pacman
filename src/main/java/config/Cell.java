package config;

public record Cell(boolean northWall, boolean eastWall, boolean southWall, boolean westWall, Cell.Content initialContent) {
    public enum Content {NOTHING, ENERGIZER, DOT}
    // Create a cell shaped as we want
    public static Cell slot(String wall, Content c) {
        // n = north, e = east, s = south, w = west. By default, there is no wall (false).
        boolean n = false, e = false, s = false, w = false;
        // For each char in String wall
        for (int i = 0; i < wall.length(); i++) {
            // If a letter is written in String wall, a wall will be added
            switch (wall.charAt(i)) {
                case 'n' -> n = true;
                case 'e' -> e = true;
                case 'w' -> w = true;
                case 's' -> s = true;
            }
        }
        // Return cell once created
        return new Cell(n, e, s, w, c);
    }
}
