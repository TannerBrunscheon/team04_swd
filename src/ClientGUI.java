import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by tbrunscheon on 12/3/16.
 */
public class ClientGUI extends Application {
    /**
     * Created by tmiksch on 12/3/16.
     */
    public static void main(String[] args) {
            launch(args);
        }

        @Override   //Creates the client GUI
        public void start(Stage primaryStage) throws Exception{
            Parent root =
                    FXMLLoader.load(getClass().getResource("ClientFX.fxml"));   //Loads the fxml file
            Scene scene = new Scene(root);  //Attach scene graph to scene
            primaryStage.setTitle("Voter Address"); //Gives it a title
            primaryStage.setScene(scene);   //Attach scene to stage
            primaryStage.show();    //Display the stage
        }
    }

