import javafx.collections.FXCollections;
import javafx.fxml.FXML;
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
    private void initialize() {
        presidentSelect.setItems(FXCollections.observableArrayList("Bob Saget", "Other Guy"));
    }
}
