import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

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
    private void initialize() {
        presidentSelect.setItems(FXCollections.observableArrayList("Bob Saget", "Other Guy"));
        senateSelect.setItems(FXCollections.observableArrayList("I guess", "Other Guy"));
        houseSelect.setItems(FXCollections.observableArrayList("for sure", "Other Guy"));
    }

    @FXML
    private void votePressed(ActionEvent event){
        String candidates = presidentSelect.getValue() + ", " + senateSelect.getValue() + ", " + houseSelect.getValue();

        if (presidentSelect.getValue() == null || senateSelect.getValue() == null || houseSelect.getValue() == null){

        }
        System.out.println(candidates);
    }
}
