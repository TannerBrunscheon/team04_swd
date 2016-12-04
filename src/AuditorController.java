import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
    private Button pushPres;
    @FXML
    private Button pushSen;
    @FXML
    private Button pushHouse;

    @FXML
    private void initialize() {
        raceDropDown.setItems(FXCollections.observableArrayList("President","Senate","House"));
        raceDropDown.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String raceToAudit = raceDropDown.getValue().toString();
                if (raceToAudit != null){
                    switch (raceToAudit){
                        case "President":
                            districtIDBox.setDisable(true);
                            stateBox.setDisable(true);
                            break;

                        case "Senate":
                            districtIDBox.setDisable(true);
                            stateBox.setDisable(false);
                            break;

                        case "House":
                            districtIDBox.setDisable(false);
                            stateBox.setDisable(false);
                            break;

                    }

                }
            }
        });

    }

    @FXML
    private void presPressed(ActionEvent event) throws Exception{

    }

    @FXML
    private void senPressed(ActionEvent event) throws Exception{

    }

    @FXML
    private void housePressed(ActionEvent event) throws Exception{

    }

}
