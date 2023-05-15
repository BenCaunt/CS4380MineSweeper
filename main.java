import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class Main extends Application {

  int window_width = 600;
  int grid_count_width = 20;
  int grid_count_height = grid_count_width; // square grid
  int grid_width = window_width / grid_count_width; 
  double mine_density = 0.1; // 10% of the grid is mines
  int num_mines = (int) (grid_count_width * grid_count_height * mine_density);

  boolean haveMinesBeenGenerated = false;
  
  boolean isDead = false; 

  Point mines[] = new Point[num_mines];

  

  @Override
  public void start(Stage primaryStage) {

    MinesweeperTile[][] grid = new MinesweeperTile[grid_count_width][grid_count_height];
    for (int i = 0; i < grid_count_width; i++) {
      for (int j = 0; j < grid_count_height; j++) {

        grid[i][j] = new MinesweeperTile(grid_width, grid_width, new Point(i, j));
      }
    }



    // labels for each tile, will start off blank, then when the associated tile is revealed, will show the number of mines around it
    Text[][] mine_labels = new Text[grid_count_width][grid_count_height];
    for (int i = 0; i < grid_count_width; i++) {
      for (int j = 0; j < grid_count_height; j++) {
        mine_labels[i][j] = new Text("");
      }
    }

    // stack plane so labels are on top of tiles
    StackPane[][] stack_panes = new StackPane[grid_count_width][grid_count_height];
    for (int i = 0; i < grid_count_width; i++) {
      for (int j = 0; j < grid_count_height; j++) {
        stack_panes[i][j] = new StackPane();
        // make it so the text is on top of the tile
        stack_panes[i][j].getChildren().add(grid[i][j]);
        stack_panes[i][j].getChildren().add(mine_labels[i][j]);
      }
    }


    

    VBox root = new VBox();

    for (int i = 0; i < grid_count_width; i++) {
      HBox row = new HBox();
      for (int j = 0; j < grid_count_height; j++) {
        row.getChildren().add(stack_panes[i][j]); // add the stack pane, not the tile and label separately
      }
      root.getChildren().add(row);
    }

    // we want to highlight the grid when the mouse is over it
    highlightMechanic(grid);

    // reveal tile
    revealAndFlag(grid,mine_labels);

    Scene scene = new Scene(root, window_width*2, window_width+10);
      
    primaryStage.setTitle("Hello World!");
    primaryStage.setScene(scene);

    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }


  public void highlightMechanic(MinesweeperTile[][] grid) {
    for (int i = 0; i < grid_count_width; i++) {
      for (int j = 0; j < grid_count_height; j++) {
        MinesweeperTile r = grid[i][j];
        r.setOnMouseEntered(e -> {
          r.setStyle("-fx-fill: red; -fx-stroke: black; -fx-stroke-width: 1;");
        });
        r.setOnMouseExited(e -> {
          if (!isDead) {
            r.setStyleFromBooleanFlags(); 
          }
        });
      }
    }
  }


  public void setMineLabelsIfTheyAreRevealed(MinesweeperTile[][] grid, Text[][] text) {
    for (int i = 0; i < grid_count_width; i++) {
      for (int j = 0; j < grid_count_height; j++) {
        MinesweeperTile r = grid[i][j];
        if (r.isRevealed) {
          int mines = r.getAdjacentMines(); 
          if (mines == 0) {
            text[i][j].setText("");
          } else {
            text[i][j].setText(r.getAdjacentMines() + "");
            String color = mineCountToColor(mines);
            text[i][j].setStyle("-fx-fill: " + color + ";");

          }
        }
      }
    }
  }

  public void revealAndFlag(MinesweeperTile[][] grid, Text[][] text) {

    // on right click reveal the tile or ctrl + primary click
    for (int i = 0; i < grid_count_width; i++) {
      for (int j = 0; j < grid_count_height; j++) {

        MinesweeperTile r = grid[i][j];
        r.setOnMouseClicked(e -> {

          if (!haveMinesBeenGenerated) {
            GameUtils.generateMines(grid, r.getPoint(), num_mines, grid_count_width, grid_count_height);
            haveMinesBeenGenerated = true;
            return; 
          }

          // reveal 
          if (e.getButton().toString().equals("SECONDARY") || (e.getButton().toString().equals("PRIMARY") && e.isControlDown())) {
            GameUtils.reveal(grid, r);
            if (r.isBomb) {
              GameUtils.failure(grid, grid_count_width);
              isDead = true; 
            } else { 
              // make the text on the tile the number of mines around it
              if(GameUtils.checkForWin(grid)) {
                GameUtils.success(grid, grid_count_width);
              }
              setMineLabelsIfTheyAreRevealed(grid, text);
            }
          }
          // flag
          if (e.getButton().toString().equals("PRIMARY") && !e.isControlDown()) {
            System.out.println("left");
            r.setFlagged(true);
            r.setStyleFromBooleanFlags();
          }

        });
      }
    }

  }

  public String mineCountToColor(int minecount) {
    switch (minecount) {
      case 0:
        return "white"; 
      case 1:
        return "DodgerBlue";
      case 2:
        return "Green"; 
      case 3:
        return "Red"; 
      case 4:
        return "DarkBlue";
      case 5:
        return "DarkRed";
      case 6:
        return "Cyan"; 
      case 7:
        return "Black"; 
      case 8: 
        return "Gray";
      default:
        break;
    }
    return "white";
  }

}
