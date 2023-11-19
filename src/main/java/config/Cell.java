package config;

// public record Cell(boolean northWall, boolean eastWall, boolean southWall, boolean westWall, Cell.Content initialContent) {
/**
 * Représente une cellule dans une grille de jeu, telle qu'un labyrinthe.
 * Chaque cellule peut contenir différents types de contenu définis par
 * l'énumération 'Content'.
 */
public record Cell(Cell.Content initialContent) {
    /**
     * Énumération des différents contenus possibles d'une cellule.
     * Les options incluent rien, energizer, mur, et point.
     */
    public enum Content {
        NOTHING, ENERGIZER, WALL, DOT
    }

    // Create a cell shaped as we want
    /**
     * Crée une représentation en chaîne de caractères de la cellule.
     * Utile pour l'affichage ou le débogage.
     *
     * @return La représentation en chaîne de la cellule.
     */
    public String toString() {
        return String.valueOf(this.initialContent);
    }

    /**
     * Crée une nouvelle cellule avec un contenu spécifique.
     *
     * @param c Le contenu de la cellule à créer.
     * @return La cellule nouvellement créée.
     */
    
    public static Cell slot(Content c) {
        /*
         * // n = north, e = east, s = south, w = west. By default, there is no wall
         * (false).
         * boolean n = false, e = false, s = false, w = false;
         * // For each char in String wall
         * for (int i = 0; i < wall.length(); i++) {
         * // If a letter is written in String wall, a wall will be added
         * switch (wall.charAt(i)) {
         * case 'n' -> n = true;
         * case 'e' -> e = true;
         * case 'w' -> w = true;
         * case 's' -> s = true;
         * }
         * }
         */
        // Return cell once created
        return new Cell(c);
    }

}
