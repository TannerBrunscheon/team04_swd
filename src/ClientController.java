import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
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
    private Button submitButton;

    @FXML
    private ChoiceBox stateSelect;

    @FXML
    private void initialize() {
        stateSelect.setItems(FXCollections.observableArrayList("AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL",
                "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO",
                "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
                "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"));
    }

    @FXML
    private void submitPressed(ActionEvent event) throws Exception{

        Stage thisStage = (Stage) submitButton.getScene().getWindow();

        if (!streetTextField.getText().equals("") && !cityTextField.getText().equals("") &&
                stateSelect.getValue() != null) {

            String street = streetTextField.getText();
            String city = cityTextField.getText();
            String state = stateSelect.getValue().toString();

            String[] information = CivicInformation.getCounty(street + city + state);

            thisStage.close();

            Stage stage;
            Parent root;

            stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SelectCandidateFX.fxml"));

            information[1] = information[1].substring(0, 1).toUpperCase() + information[1].substring(1);
            root = loader.load();
            SelectCandidateController controller = (SelectCandidateController) loader.getController();

            controller.setId(state);
            controller.setSsnn(state + "-" + information[0]);
            controller.setState_county(state + "-" + information[1]);

            stage.setTitle("Select Candidates");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }

        else {
            Alert error = new Alert(Alert.AlertType.ERROR);

            error.setTitle("Error!");
            error.setHeaderText("Not all address components entered");
            error.setContentText("Press OK to re-enter your address");
            error.showAndWait();
        }
    }

}
