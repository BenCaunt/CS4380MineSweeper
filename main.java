import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;  
import java.io.FileNotFoundException; 
import java.io.IOException;

public class Main extends Application {

  int window_width = 600;
  int grid_count_width = 10;
  int grid_count_height = grid_count_width; // square grid
  int grid_width = window_width / grid_count_width; 
  double mine_density = 0.10; // 10% of the grid is mines
  int num_mines = (int) (grid_count_width * grid_count_height * mine_density);

  boolean haveMinesBeenGenerated = false;
  
  boolean isDead = false; 

  Point mines[] = new Point[num_mines];

  @Override
  public void start(Stage primaryStage) throws FileNotFoundException {

    String startingSound = "RoleReveal.wav";
    Media sound = new Media(new File(startingSound).toURI().toString());

    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.play();



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
        String fileName = "default.png";
        Image image = new Image(fileName);
        ImageView imageView = new ImageView(image);  
        imageView.setFitHeight(grid_width); 
        imageView.setFitWidth(grid_width); 
        // make it so the text is on top of the tile
        stack_panes[i][j].getChildren().addAll(grid[i][j],imageView, mine_labels[i][j]); 
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
    // highlightMechanic(grid);

    // reveal tile
    revealAndFlag(grid, stack_panes, mine_labels);

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
          if (!r.isRevealed && !isDead && !r.isFlagged)
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

  public void setMineLabelsIfTheyAreRevealed(MinesweeperTile[][] grid, StackPane[][] stack_panes, Text[][] text) {
    for (int i = 0; i < grid_count_width; i++) {
      for (int j = 0; j < grid_count_height; j++) {
        StackPane p = stack_panes[i][j];
        MinesweeperTile r = grid[i][j];
        if (r.isRevealed) {
          // safely remove the ImageView node
          p.getChildren().removeIf(node -> node instanceof ImageView);
  
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

  public void revealAndFlag(MinesweeperTile[][] grid, StackPane[][] stack_panes, Text[][] text) throws FileNotFoundException {

    // on right click reveal the tile or ctrl + primary click
    for (int i = 0; i < grid_count_width; i++) {
      for (int j = 0; j < grid_count_height; j++) {
        StackPane p = stack_panes[i][j];
        MinesweeperTile r = (MinesweeperTile) p.getChildren().get(0);
        p.setOnMouseClicked(e -> {
          if (!haveMinesBeenGenerated) {
            GameUtils.generateMines(grid, r.getPoint(), num_mines, grid_count_width, grid_count_height);
            haveMinesBeenGenerated = true;
            
            return; 
          }
          
          // reveal 
          if (e.getButton().toString().equals("SECONDARY") || (e.getButton().toString().equals("PRIMARY") && e.isControlDown() &&!r.isFlagged)) {
            GameUtils.reveal(grid, r);
            if (r.isBomb) {

              String soundFile = "DefeatSound.wav";

              Media sound = new Media(new File(soundFile).toURI().toString());
              MediaPlayer mediaPlayer = new MediaPlayer(sound);
              mediaPlayer.play();
              

              String fileName = "death.png";
              Image image = new Image(fileName);
              ImageView imageView = new ImageView(image);      
              imageView.setFitHeight(grid_width); 
              imageView.setFitWidth(grid_width); 
              GameUtils.failure(grid, grid_count_width);
              p.getChildren().add(imageView);
              // go through and reveal all the mines by adding the mine image to the stack plane
              for (int k = 0; k < grid_count_width; k++) {
                for (int l = 0; l < grid_count_height; l++) {
                  MinesweeperTile tile = grid[k][l];
                  if (tile.isBomb) {
                    // calculate width and height based on the size of each rectangular tile 
                    int width_height = (int) (window_width / grid_count_width);

                    imageView.setFitHeight(grid_width);
                    imageView.setFitWidth(grid_width);
                    stack_panes[k][l].getChildren().add(imageView);
                  }
                }
              }
              isDead = true; 
            } else { 
              // make the text on the tile the number of mines around it
              if(GameUtils.checkForWin(grid)) {
                String winSound = "victorySound.wav";
                Media sound = new Media(new File(winSound).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();

                GameUtils.success(grid, grid_count_width);
              }
              setMineLabelsIfTheyAreRevealed(grid, stack_panes, text);
            }
          }
          // flag
          if (e.getButton().toString().equals("PRIMARY") && !e.isControlDown() && !r.isRevealed) {
            System.out.println("left");
            //Creating an image 
            if (!r.isFlagged) {
              r.setFlagged(true);
              r.setStyleFromBooleanFlags();  
              String fileName = "flag.png";
              Image image = new Image(fileName);
              ImageView imageView = new ImageView(image);  
              imageView.setFitHeight(grid_width); 
              imageView.setFitWidth(grid_width); 
              p.getChildren().remove(1);
              p.getChildren().addAll(imageView);
            } else {
              r.setFlagged(false);
              p.getChildren().remove(p.getChildren().size()-1);
              String fileName = "default.png";
              Image image = new Image(fileName);
              ImageView imageView = new ImageView(image);  
              imageView.setFitHeight(grid_width); 
              imageView.setFitWidth(grid_width); 
              p.getChildren().addAll(imageView);
            }
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
        return "e9d8a6";
      case 2:
        return "ca6702"; 
      case 3:
        return "ee9b00"; 
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
        return "white";
    }
  }
  //picturehandler(r, i, j, stack_panes, grid);          
  public void picturehandler(MinesweeperTile r, int i, int j, StackPane[][] stack_panes, MinesweeperTile[][] grid) throws FileNotFoundException {
      //Creating an image 
      Image image = new Image(new FileInputStream("flag.png"));  
      
      //Setting the image view 
      ImageView imageView = new ImageView(image); 

      
      //setting the fit height and width of the image view 
      imageView.setFitHeight(39); 
      imageView.setFitWidth(39); 
      
  }
}
