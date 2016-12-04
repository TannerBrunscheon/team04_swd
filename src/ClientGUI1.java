import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by tbrunscheon on 12/3/16.
 */
public class ClientGUI1 extends Application {
    /**
     * Created by tmiksch on 12/3/16.
     */


        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception{
            Parent root =
                    FXMLLoader.load(getClass().getResource("ClientFX.fxml"));
            Scene scene = new Scene(root); // attach scene graph to scene
            primaryStage.setTitle("Voter Address"); // displayed in window's title bar
            primaryStage.setScene(scene); // attach scene to stage
            primaryStage.show(); // display the stage
        }
    }

