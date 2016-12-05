import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;

/**
 * Created by tbrunscheon on 12/3/16.
 */
public class AuditorController {
    @FXML   //Choice box to select the race to be audited
    private ChoiceBox raceDropDown;
    @FXML   //Enter your district if necessary
    private TextField districtIDBox;
    @FXML   //Enter your Republican candidate
    private TextField repubBox;
    @FXML   //Enter your Democratic candidate
    private TextField demBox;
    @FXML   //Choice box for your state
    private ChoiceBox stateBox;
    @FXML   //Add candidate information to database
    private Button addButton;
    @FXML   //Push the databases to the fusion tables
    private Button pushFus;

    @FXML   //Set states, disable the fields not needed to be changed
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
    @FXML   //If the "add" button is pressed
    private void addPressed(ActionEvent event) throws Exception{
        try
        {
        switch (raceDropDown.getValue().toString()) {
            case "President":   //Send president info to the database
                if (repubBox.getText() != null && demBox.getText() != null) {
                    DatabaseManagement.setPresidentialCandidate(demBox.getText(), repubBox.getText());

                } else {
                    Alert error = new Alert(Alert.AlertType.ERROR);

                    error.setTitle("Error!");
                    error.setHeaderText("Fill all canidates");

                    error.showAndWait();
                }
                break;

            case "Senate":  //Send senate info to the database
                if (repubBox.getText() != null && demBox.getText() != null &&
                        stateBox.getSelectionModel().getSelectedItem().toString() != null) {
                    DatabaseManagement.setSenateCandidates(stateBox.getSelectionModel().getSelectedItem().toString(), demBox.getText(), repubBox.getText());
                } else {
                    Alert error = new Alert(Alert.AlertType.ERROR);

                    error.setTitle("Error!");
                    error.setHeaderText("Fill all canidates");
                    error.showAndWait();
                }
                break;

            case "House":   //Send house info to the database
                if (repubBox.getText() != null && demBox.getText() != null &&
                        stateBox.getSelectionModel().getSelectedItem().toString() != null && districtIDBox.getText() != null) {
                    DatabaseManagement.setHouseCandidates(stateBox.getSelectionModel().getSelectedItem().toString() + "-" + districtIDBox.getText(), demBox.getText(), repubBox.getText());
                } else {
                    Alert error = new Alert(Alert.AlertType.ERROR);

                    error.setTitle("Error!");
                    error.setHeaderText("Fill all canidates");

                    error.showAndWait();
                }
                break;
        }


        } catch (NullPointerException e){
            Alert error = new Alert(Alert.AlertType.ERROR);

            error.setTitle("Error!");
            error.setHeaderText("Pick a race");

            error.showAndWait();
        }
    }



    @FXML   //When the fusion button is pressed, it sends the database to the Fusion Tables
    private void fusPressed(ActionEvent event) throws Exception{
        Stage thisStage = (Stage) pushFus.getScene().getWindow();
        URL url = new URL("http://user.engineering.uiowa.edu/~tbrunscheon/WebsiteFusion.html");
        DatabaseManagement.toFusionTable();
        thisStage.close();
    }
}
