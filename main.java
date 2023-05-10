import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class main extends Application {

  int window_width = 600;
  int grid_count_width = 20;
  int grid_count_height = grid_count_width; // square grid
  int grid_width = window_width / grid_count_width; 
  double mine_density = 0.1; // 10% of the grid is mines
  int num_mines = (int) (grid_count_width * grid_count_height * mine_density);

  boolean haveMinesBeenGenerated = false; 

  Point mines[] = new Point[num_mines];

  

  @Override
  public void start(Stage primaryStage) {

    MinesweeperTile[][] grid = new MinesweeperTile[grid_count_width][grid_count_height];
    for (int i = 0; i < grid_count_width; i++) {
      for (int j = 0; j < grid_count_height; j++) {

        grid[i][j] = new MinesweeperTile(grid_width, grid_width);
      }
    }

    VBox root = new VBox();

    for (int i = 0; i < grid_count_width; i++) {
      HBox row = new HBox();
      for (int j = 0; j < grid_count_height; j++) {
        row.getChildren().add(grid[i][j]);
      }
      root.getChildren().add(row);
    }

    // we want to highlight the grid when the mouse is over it
    highlightMechanic(grid);


    // reveal tile
    revealAndFlag(grid);






    Scene scene = new Scene(root, window_width, window_width);
    


    
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
          r.setStyleFromBooleanFlags(); 
        });
      }
    }
  }



  public void revealAndFlag(MinesweeperTile[][] grid) {

    // on right click reveal the tile or ctrl + primary click
    for (int i = 0; i < grid_count_width; i++) {
      for (int j = 0; j < grid_count_height; j++) {
        MinesweeperTile r = grid[i][j];
        r.setOnMouseClicked(e -> {
          if (e.getButton().toString().equals("SECONDARY") || (e.getButton().toString().equals("PRIMARY") && e.isControlDown())) {
            r.setRevealed(true);
            System.out.println("reveal");

            r.setStyleFromBooleanFlags();
          }
          if (e.getButton().toString().equals("PRIMARY") && !e.isControlDown()) {
            System.out.println("left");

            r.setFlagged(true);
            r.setStyleFromBooleanFlags();
          }

        });
      }
    }


  }

}
