import javafx.application.Application;
import javafx.scene.layout.StackPane; 
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.text.Text; 

public class texttest extends Application{
    
    @Override
    public void start(Stage primaryStage) {

        Text text = new Text("PENIS");
        MinesweeperTile tile = new MinesweeperTile(40, 40, new Point(40, 40));
        tile.setStyle("-fx-fill: gray; -fx-stroke: black; -fx-stroke-width: 1;");
        StackPane stack = new StackPane();
        stack.getChildren().addAll(tile, text);
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(stack);

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setTitle("RAAW!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
      }
}
