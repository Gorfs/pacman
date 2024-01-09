package config;

/**
 * Class record Cell : Create a cell that contains initialContent
 * @param initialContent variable that represent the content in the cell.
 */
public record Cell(Cell.Content initialContent) {
    public enum Content {NOTHING, ENERGIZER, WALL, DOT}

    /**
     * @return the content of the cell
     */
   public String toString(){
    return String.valueOf(this.initialContent); 
   }

    /**
     * Method that create a new cell.
     * @param c variable that represent the content in the cell.
     * @return a new cell
     */
    public static Cell slot(Content c) {
        return new Cell(c);
    }
}
