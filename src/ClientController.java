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


    @FXML   //Text field that holds the voter street
    private TextField streetTextField;

    @FXML   //Text field that holds the voter city
    private TextField cityTextField;

    @FXML   //Submits the voter's address
    private Button submitButton;

    @FXML   //Choice box to pick your state
    private ChoiceBox stateSelect;

    @FXML   //Set the options for states
    private void initialize() {
        stateSelect.setItems(FXCollections.observableArrayList("AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL",
                "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO",
                "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
                "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"));
    }

    @FXML   //Actions performed when submit is hit
    private void submitPressed(ActionEvent event) throws Exception{

        Stage thisStage = (Stage) submitButton.getScene().getWindow();  //Declare this stage so it can be closed later

        if (!streetTextField.getText().equals("") && !cityTextField.getText().equals("") &&
                stateSelect.getValue() != null) {   //Makes sure all fields are filled out

            String street = streetTextField.getText();
            String city = cityTextField.getText();
            String state = stateSelect.getValue().toString();

            String[] information = CivicInformation.getCounty(street + city + state);   //Send information to Civics

            thisStage.close();  //Close the address window

            Stage stage;
            Parent root;

            stage = new Stage();    //Load the voting window
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

        else {  //Error message for not all fields being entered
            Alert error = new Alert(Alert.AlertType.ERROR);

            error.setTitle("Error!");
            error.setHeaderText("Not all address components entered");
            error.setContentText("Press OK to re-enter your address");
            error.showAndWait();
        }
    }

}
