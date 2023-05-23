import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameUtils {

    /**
     * get the neighbors of a tile, including diagonals
     * @param grid the grid of tiles
     * @param p the point of the tile
     * @return the neighbors of the tile
     */
    
    // gets neighbors; accounting for the walls and corners
    public static List<MinesweeperTile> getNeighbors(MinesweeperTile[][] grid, MinesweeperTile t) {

        List<MinesweeperTile> neighbors = new ArrayList<MinesweeperTile>();
        System.out.println(t.getPoint() + " 911");

        int x = t.getPoint().getX();
        int y = t.getPoint().getY();
        System.out.println("x: " + x + " y: " + y);

        // top left
        if (x - 1 >= 0 && y - 1 >= 0) {
            neighbors.add(grid[x - 1][y - 1]);
            System.out.println("top left");
        }
        // top
        if (y - 1 >= 0) {
            neighbors.add(grid[x][y - 1]);
            System.out.println("top");
        }
        // top right
        if (x + 1 < grid.length && y - 1 >= 0) {
            neighbors.add(grid[x + 1][y - 1]);
            System.out.println("top right");
        }
        // right
        if (x + 1 < grid.length) {
            neighbors.add(grid[x + 1][y]);
            System.out.println("right");
        }
        // bottom right
        if (x + 1 < grid.length && y + 1 < grid[0].length) {
            neighbors.add(grid[x + 1][y + 1]);
            System.out.println("bottom right");
        }
        // bottom
        if (y + 1 < grid[0].length) {
            neighbors.add(grid[x][y + 1]);
            System.out.println("bottom");
        }
        // bottom left
        if (x - 1 >= 0 && y + 1 < grid[0].length) {
            neighbors.add(grid[x - 1][y + 1]);
            System.out.println("bottom left");
        }
        // left
        if (x - 1 >= 0) {
            neighbors.add(grid[x - 1][y]);
            System.out.println("left");
        }
        return neighbors; 
     
    }

    /**
     * generate the mines of the game, excluding the first click
     * @param grid the grid of tiles
     * @param firstclick the first click of the game
     */
    public static void generateMines(MinesweeperTile[][] grid, Point firstclick, int num_mines, int grid_count_width, int grid_count_height) {
        for (int i = 0; i < num_mines; i++) {
            Point p = new Point((int) (Math.random() * grid_count_width), (int) (Math.random() * grid_count_height));
            // make sure the first click is not a bomb
            while (p.equals(firstclick) || grid[p.getX()][p.getY()].isBomb()) {

                p = new Point((int) (Math.random() * grid_count_width), (int) (Math.random() * grid_count_height));
            }
            // set the bomb
            grid[p.getX()][p.getY()].setBomb(true);
        }

    }

    public static void reveal(MinesweeperTile[][] grid, MinesweeperTile r) {
        Stack<MinesweeperTile> tileStack = new Stack<>();
        tileStack.push(r);
    
        while (!tileStack.isEmpty()) {
            MinesweeperTile currentTile = tileStack.pop();
    
            if (currentTile.isRevealed()) {
                continue;
            }
    
            currentTile.setRevealed(true);
            currentTile.setStyleFromBooleanFlags();
    
            if (currentTile.isBomb()) {
                return;
            }
    
            int adjacentMines = countAdjacentMines(grid, currentTile);
            currentTile.setAdjacentMines(adjacentMines);
    
            if (adjacentMines == 0) {
                List<MinesweeperTile> neighbors = getNeighbors(grid, currentTile);
                for (MinesweeperTile neighbor : neighbors) {
                    if (neighbor != null && !neighbor.isRevealed()) {
                        tileStack.push(neighbor);
                    }
                }
            }
        }
    }

    public static int countAdjacentMines(MinesweeperTile[][] grid, MinesweeperTile t) {
        List<MinesweeperTile> neighbors = getNeighbors(grid, t);
        int adjacentMines = 0;
    
        for (MinesweeperTile neighbor : neighbors) {
            if (neighbor != null && neighbor.isBomb()) {
                adjacentMines++;
            }
        }
    
        return adjacentMines;
    }
    // on loss, red board
    public static void failure(MinesweeperTile[][] grid, int dim) {
        
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (grid[i][j].isBomb) {
                    grid[i][j].setStyle("-fx-fill: black; -fx-stroke: black; -fx-stroke-width: 1;");

                }
                else if (grid[i][j].isRevealed) {
                    grid[i][j].setStyle("-fx-fill: Darkred; -fx-stroke: black; -fx-stroke-width: 1;");
                } else if (!grid[i][j].isRevealed) {
                    grid[i][j].setStyle("-fx-fill: red; -fx-stroke: black; -fx-stroke-width: 1;");
                }
            }
        }
    }    
    
    // when no tile that are not bombs are unrevealed, you win
    public static boolean checkForWin(MinesweeperTile[][] grid) {
        for (MinesweeperTile[] row : grid) {
            for (MinesweeperTile tile : row) {
                if (!tile.isRevealed() && !tile.isBomb()) {
                    return false;
                }
            }
        }
        return true;
    }
    // on win, green board
    public static void success(MinesweeperTile[][] grid, int dim) {
        
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (grid[i][j].isBomb) {
                    grid[i][j].setStyle("-fx-fill: black; -fx-stroke: black; -fx-stroke-width: 1;");
                }
                else if (grid[i][j].isRevealed) {
                    grid[i][j].setStyle("-fx-fill: green; -fx-stroke: black; -fx-stroke-width: 1;");
                } else if (!grid[i][j].isRevealed) {
                    grid[i][j].setStyle("-fx-fill: green; -fx-stroke: black; -fx-stroke-width: 1;");
                }
            }
        }
    }
    
}
