

public class GameUtils {

    /**
     * get the neighbors of a tile, including diagonals
     * @param grid the grid of tiles
     * @param p the point of the tile
     * @return the neighbors of the tile
     */
    
    public static MinesweeperTile[] getNeighbors(MinesweeperTile[][] grid, Point p) {
        MinesweeperTile[] neighbors = new MinesweeperTile[8]; 
        int max = grid.length-1; 
        // RIGHT wall and corners        
        if (p.getX() == 0) {

            // top corner 
            if (p.getY() == 0) {
                neighbors[0] = grid[0][1]; 
                neighbors[1] = grid[1][0]; 
                neighbors[2] = grid[1][1]; 
            }
            // bottom corner 
            else if (p.getY() == max){
                neighbors[0] = grid[0][max-1]; 
                neighbors[1] = grid[1][max]; 
                neighbors[2] = grid[1][max-1]; 
            } else {
                neighbors[0] = grid[0][p.getY()-1]; 
                neighbors[1] = grid[1][p.getY()-1]; 
                neighbors[2] = grid[1][p.getY()]; 
                neighbors[3] = grid[1][p.getY()+1]; 
                neighbors[4] = grid[0][p.getY()+1]; 
            }
        }
        // LEFT wall and corners        
        else if (p.getX() == max) {
            // top corner 
            if (p.getY() == 0) {
                neighbors[0] = grid[max-1][0]; 
                neighbors[1] = grid[max-1][1]; 
                neighbors[2] = grid[max][1]; 
            }
            // bottom corner 
            else if (p.getY() == max){
                neighbors[0] = grid[max-1][max]; 
                neighbors[1] = grid[max-1][max-1]; 
                neighbors[2] = grid[max][max-1]; 
            } else {
                neighbors[0] = grid[max][p.getY()-1]; 
                neighbors[1] = grid[max-1][p.getY()-1]; 
                neighbors[2] = grid[max-1][p.getY()]; 
                neighbors[3] = grid[max-1][p.getY()+1]; 
                neighbors[4] = grid[max][p.getY()+1]; 
            }
        }
        // TOP wall
        else if (p.getY() == 0) {
            neighbors[0] = grid[0][p.getX()-1]; 
            neighbors[1] = grid[1][p.getX()-1]; 
            neighbors[2] = grid[1][p.getX()]; 
            neighbors[3] = grid[1][p.getX()+1]; 
            neighbors[4] = grid[0][p.getX()+1]; 
        }
        // BOTTOM wall 
        else if (p.getY() == max) {
            neighbors[0] = grid[p.getX()-1][max]; 
            neighbors[1] = grid[p.getX()-1][max-1]; 
            neighbors[2] = grid[p.getX()][max-1]; 
            neighbors[3] = grid[p.getX()+1][max-1];  
            neighbors[4] = grid[p.getX()+1][max]; 
        } else {
            neighbors[0] = grid[p.getX()-1][p.getY()-1]; 
            neighbors[1] = grid[p.getX()-1][p.getY()]; 
            neighbors[2] = grid[p.getX()-1][p.getY()+1]; 
            neighbors[3] = grid[p.getX()][p.getY()-1]; 

            neighbors[4] = grid[p.getX()][p.getY()+1]; 
            neighbors[5] = grid[p.getX()+1][p.getY()-1]; 
            neighbors[6] = grid[p.getX()+1][p.getY()]; 
            neighbors[7] = grid[p.getX()+1][p.getY()+1]; 
        }
        return neighbors;
    }





    
}
