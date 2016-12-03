import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Created by tmiksch on 12/3/16.
 */
public class SelectCandidateController {
    @FXML
    private ChoiceBox presidentSelect;
    @FXML
    private ChoiceBox senateSelect;
    @FXML
    private ChoiceBox houseSelect;
    @FXML
    private Button voteButton;

    @FXML
    private CheckBox demPresBox;

    @FXML
    private void initialize() {
        presidentSelect.setItems(FXCollections.observableArrayList("Bob Saget", "Other Guy"));
        senateSelect.setItems(FXCollections.observableArrayList("I guess", "Other Guy"));
        houseSelect.setItems(FXCollections.observableArrayList("for sure", "Other Guy"));
    }

    @FXML
    private void votePressed(ActionEvent event) throws Exception{
        String candidates = presidentSelect.getValue() + ", " + senateSelect.getValue() + ", " + houseSelect.getValue();

        Stage thisStage = (Stage) voteButton.getScene().getWindow();

        Alert confirmationBox = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationBox.setTitle("");
        confirmationBox.setHeaderText("Are you sure these are your candidates?");
        confirmationBox.setContentText("Press OK if you are sure");



        Optional<ButtonType> confirm = confirmationBox.showAndWait();
        if (confirm.get() == ButtonType.OK){
            thisStage.close();
        }

        System.out.println(candidates);
    }
}
