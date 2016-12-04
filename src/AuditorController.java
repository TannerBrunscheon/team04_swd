import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.awt.*;
import java.net.URL;

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
    private ChoiceBox stateBox;
    @FXML
    private Button addButton;
    @FXML
    private Button pushFus;

    @FXML
    private void initialize() {
        stateBox.setItems(FXCollections.observableArrayList("AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL",
                "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO",
                "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
                "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"));
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
    private void addPressed(ActionEvent event) throws Exception{
        switch (raceDropDown.getValue().toString()){
            case "President":
            if (repubBox.getText()!=null&&demBox.getText()!=null){
                DatabaseManagement.setDemocraticPresidentialCandidate(demBox.getText());
                DatabaseManagement.setRepublicanPresidentialCandidate(repubBox.getText());

            }
            else  {
                Alert error = new Alert(Alert.AlertType.ERROR);

                error.setTitle("Error!");
                error.setHeaderText("Fill all canidates");

                error.showAndWait();
            }
            break;

            case "Senate":
                if (repubBox.getText()!=null&&demBox.getText()!=null&&
                        stateBox.getSelectionModel().getSelectedItem().toString() != null){
                    DatabaseManagement.setSenateCandidates(stateBox.getSelectionModel().getSelectedItem().toString(),demBox.getText(),repubBox.getText());
                    System.out.println(stateBox.getSelectionModel().getSelectedItem().toString() + demBox.getText()+repubBox.getText());
                }
                else  {
                    Alert error = new Alert(Alert.AlertType.ERROR);

                    error.setTitle("Error!");
                    error.setHeaderText("Fill all canidates");
                    error.showAndWait();
                }
                break;

            case "House":
                if (repubBox.getText()!=null&&demBox.getText()!=null&&
                        stateBox.getSelectionModel().getSelectedItem().toString() != null&&districtIDBox.getText()!=null){
                    DatabaseManagement.setHouseCandidates(stateBox.getSelectionModel().getSelectedItem().toString()+"-"+districtIDBox.getText(),demBox.getText(),repubBox.getText());
                }
                else  {
                    Alert error = new Alert(Alert.AlertType.ERROR);

                    error.setTitle("Error!");
                    error.setHeaderText("Fill all canidates");

                    error.showAndWait();
                }
        }


    }

    @FXML
    private void fusPressed(ActionEvent event) throws Exception{
        DatabaseManagement.toFusionTable();
        try {
            Desktop.getDesktop().browse(new URL("http://user.engineering.uiowa.edu/~tbrunscheon/WebsiteFusion.html").toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
