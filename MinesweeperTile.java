import javafx.scene.shape.Rectangle;

public class MinesweeperTile extends Rectangle {

    boolean isBomb;
    boolean isRevealed;
    boolean isFlagged;
    private Point p;
    int adjacentMines;


    public static final String STYLE_HIDDEN = "-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1;";
    public static final String STYLE_REVEALED = "-fx-fill: #001219; -fx-stroke: black; -fx-stroke-width: 1;";
    public static final String STYLE_FLAGGED = "-fx-fill: blue; -fx-stroke: black; -fx-stroke-width: 1;";
    public static final String STYLE_BOMB = "-fx-fill: black; -fx-stroke: black; -fx-stroke-width: 1;";
    public static final String STYLE_HIGHLIGHT = "-fx-fill: red; -fx-stroke: black; -fx-stroke-width: 1;";
    
    public MinesweeperTile(int grid_width, int grid_height, Point p) {
        super(grid_width, grid_height);
        this.isBomb = false;
        this.isRevealed = false;
        this.isFlagged = false;
        this.setStyle(STYLE_HIDDEN);
        this.p = p; 
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

    public Point getPoint() {
        return p;
    }

    public void setHighlightWhileMouseOver() {
        this.setStyle(STYLE_HIGHLIGHT);
    }

    public void setStyleFromBooleanFlags() {
        if (isRevealed && isBomb) {
            this.setStyle(STYLE_BOMB);
        } else if (isRevealed) {
            this.setStyle(STYLE_REVEALED);
        } else if (isFlagged) {
            this.setStyle(STYLE_FLAGGED);
        } else {
            this.setStyle(STYLE_HIDDEN);
        }
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }
}