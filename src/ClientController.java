import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxListCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.annotation.Resource;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

/**
 * Created by tmiksch on 12/3/16.
 */
public class ClientController {
    /*@FXML
    private ChoiceBox stateChoiceBox;
    @FXML
    private ChoiceBox countyChoiceBox;
    @FXML
    private ChoiceBox partyChoiceBox;
    @FXML
    private TextField addressTextField;

    @FXML
    private void initialize() {
        stateChoiceBox.setItems(FXCollections.observableArrayList("New Document", "Open "));
    }*/

    @FXML
    private TextField streetTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField stateTextField;

    @FXML
    private Button submitButton;

    @FXML
    private void submitPressed(ActionEvent event) throws Exception{
        String address = streetTextField.getText() + ", " + cityTextField.getText() + ", " + stateTextField.getText();
        System.out.println(address);

        while (streetTextField.getText() == null || cityTextField.getText() == null || stateTextField.getText() == null){
            Alert error = new Alert(Alert.AlertType.ERROR);

            //error.setTitle
        }

        Stage thisStage = (Stage) submitButton.getScene().getWindow();
        thisStage.close();

        Stage stage;
        Parent root;

        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("SelectCandidateFX.fxml"));

        stage.setTitle("Select Candidates"); // displayed in window's title bar
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }


}
