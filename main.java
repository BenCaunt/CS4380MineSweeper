import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;


public class main extends Application {

  int window_width = 600;
  int grid_count_width = 20;
  int grid_count_height = grid_count_width; // square grid
  int grid_width = window_width / grid_count_width; 
  double mine_density = 0.1; // 10% of the grid is mines
  int num_mines = (int) (grid_count_width * grid_count_height * mine_density);

  Point mines[] = new Point[num_mines];

  

  @Override
  public void start(Stage primaryStage) {

    Rectangle[][] grid = new Rectangle[grid_count_width][grid_count_height];
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
    for (int i = 0; i < grid_count_width; i++) {
      for (int j = 0; j < grid_count_height; j++) {
        Rectangle r = grid[i][j];
        r.setOnMouseEntered(e -> {
          r.setStyle("-fx-fill: red; -fx-stroke: black; -fx-stroke-width: 1;");
        });
        r.setOnMouseExited(e -> {
          r.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1;");
        });
      }
    }


    Scene scene = new Scene(root, window_width, window_width);
    


    
    primaryStage.setTitle("Hello World!");
    primaryStage.setScene(scene);

    primaryStage.show();





  }

  public static void main(String[] args) {
    launch(args);
  }
}
