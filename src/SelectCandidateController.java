import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private void votePressed(ActionEvent event) throws Exception{
        String candidates = presidentSelect.getValue() + ", " + senateSelect.getValue() + ", " + houseSelect.getValue();

        Stage stage;
        Parent root;

        if (presidentSelect.getValue() == null || senateSelect.getValue() == null || houseSelect.getValue() == null){
            if (event.getSource() == voteButton){

            }
        }
        System.out.println(candidates);
    }
}
