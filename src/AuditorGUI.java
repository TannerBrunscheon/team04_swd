/**
 * Created by tbrunscheon on 12/3/16.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AuditorGUI extends Application {

    /**
     * Launch Args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Create auditor GUI
     * @param primaryStage  primary stage from JavaFX
     * @throws Exception    allows the FXMLLoader to run properly
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root =
                FXMLLoader.load(getClass().getResource("AuditorFX.fxml"));  //Load the fxml file
        Scene scene = new Scene(root); //Attach scene graph to scene
        primaryStage.setTitle("Auditor");   //Displayed in window's title bar
        primaryStage.setScene(scene);   //Attach scene to stage
        primaryStage.show();    //Display the stage
    }
}
