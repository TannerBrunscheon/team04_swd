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

        Stage thisStage = (Stage) submitButton.getScene().getWindow();

        if (!streetTextField.getText().equals("") && !cityTextField.getText().equals("") &&
                !stateTextField.getText().equals("")){
            thisStage.close();

            Stage stage;
            Parent root;

            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("SelectCandidateFX.fxml"));

            stage.setTitle("Select Candidates");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }

        else {
            Alert error = new Alert(Alert.AlertType.ERROR);

            error.setTitle("Error!");
            error.setHeaderText("Not all address components entered");
            error.setContentText("Press OK to reenter your address");

            error.showAndWait();
        }
    }

}
