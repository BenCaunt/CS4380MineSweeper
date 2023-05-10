import javafx.scene.shape.Rectangle;

public class MinesweeperTile extends Rectangle {

    boolean isBomb;
    boolean isRevealed;
    boolean isFlagged;

    public static final String STYLE_HIDDEN = "-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1;";
    public static final String STYLE_REVEALED = "-fx-fill: gray; -fx-stroke: black; -fx-stroke-width: 1;";
    public static final String STYLE_FLAGGED = "-fx-fill: red; -fx-stroke: black; -fx-stroke-width: 1;";
    public static final String STYLE_BOMB = "-fx-fill: black; -fx-stroke: black; -fx-stroke-width: 1;";
    public MinesweeperTile(int grid_width, int grid_height) {
        super(grid_width, grid_height);
        this.isBomb = false;
        this.isRevealed = false;
        this.isFlagged = false;
        this.setStyle(STYLE_HIDDEN);
    }
    

    public boolean isBomb() {
        return isBomb;
    }
    public boolean isRevealed() {
        return isRevealed;
    }
    public boolean isFlagged() {
        return isFlagged;
    }
    public void setBomb(boolean isBomb) {
        this.isBomb = isBomb;
    }
    public void setRevealed(boolean isRevealed) {
        this.isRevealed = isRevealed;
    }
    public void setFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
    }



    
}
