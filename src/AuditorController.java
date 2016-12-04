import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * Created by tbrunscheon on 12/3/16.
 */
public class AuditorController {
    @FXML
    private ChoiceBox raceDropDown;
    @FXML
    private TextField districtIDBox;
    @FXML
    private TextField repubBox;
    @FXML
    private TextField demBox;
    @FXML
    private TextField stateBox;
    @FXML
    private Button addButton;

    @FXML
    private void initialize() {
        String
        raceDropDown.setItems(FXCollections.observableArrayList("President","Senate","House"));
        raceDropDown.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue != null){
                    switch (newValue){
                        case "President":
                            districtIDBox.setDisable(true);
                            stateBox.setDisable(true);
                            break;

                        case "Senate":
                            districtIDBox.setDisable(true);
                            stateBox.setDisable(false);
                            break;

                        case "House":

                    }

                }
            }
        });

    }

}
