package misc;

import config.Cell;
public class Debug {
    public static void out(String message) {
        // comment this out if you do not want to see the debug messages
        System.out.println(">> DEBUG >> " + message);
    }
    public static void gridOut(Cell[][] grid){
        System.out.println("Grid currently looks like : ");
        for(int i = 0 ; i < grid.length; i ++){
            if (i == 15){
                System.out.print( " [");
                for(int j = 0; j < grid[0].length; j++){
                    System.out.print(", " + grid[i][j].toString());
                }
            }
            System.out.println(" ]");
        }
    }
}
