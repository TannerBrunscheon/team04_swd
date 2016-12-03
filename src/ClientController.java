import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by tmiksch on 12/3/16.
 */
public class ClientController {

    @FXML
    private TextField streetTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField stateTextField;

    @FXML
    private Button submitButton;

    @FXML
    private void submitPressed(ActionEvent event) throws Exception{
        String address = streetTextField.getText() + ", " + cityTextField.getText() + ", " + stateTextField.getText();
        System.out.println(address);

        /*if (streetTextField.getText() == null || cityTextField.getText() == null || stateTextField.getText() == null){
            Alert error = new Alert(Alert.AlertType.ERROR);

            error.setTitle("Not all inputs entered!");
            error.setHeaderText("Please enter all variable fields");
            error.setContentText("Press OK to reenter yur address");

            error.showAndWait();
        }*/

        Stage thisStage = (Stage) submitButton.getScene().getWindow();
        thisStage.close();

        Stage stage;
        Parent root;

        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("SelectCandidateFX.fxml"));

        stage.setTitle("Select Candidates"); // displayed in window's title bar
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }


}
