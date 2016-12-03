import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxListCell;

import javax.annotation.Resource;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;

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
    private void submitPressed(ActionEvent event){
        String address = streetTextField.getText() + ", " + cityTextField.getText() + ", " + stateTextField.getText();
        System.out.println(address);
    }
}
