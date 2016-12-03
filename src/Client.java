import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by tmiksch on 12/3/16.
 */
public class Client {
    public static void main(String args[]) {
        Client application;
    }

    //@Override
    public void start(Stage stage) throws Exception {
        // construct scene graph
        Parent root =
                FXMLLoader.load(getClass().getResource("TipCalculator.fxml"));
        Scene scene = new Scene(root); // attach scene graph to scene
        stage.setTitle("Tip Calculator"); // displayed in window's title bar
        stage.setScene(scene); // attach scene to stage
        stage.show(); // display the stage
        }
}
