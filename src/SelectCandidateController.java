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
    private String id;
    private String ssnn;
    private String state_county;
    @FXML
    private Button voteButton;

    @FXML
    private CheckBox demPresBox;

    @FXML
    private CheckBox repPresBox;

    @FXML
    private CheckBox demSCBox;

    @FXML
    private CheckBox repSCBox;

    @FXML
    private CheckBox demHRBox;

    @FXML
    private CheckBox repHRBox;

    @FXML
    private void initialize() {
        String demPres = DatabaseManagement.getDemocraticPresidentialCandidate();
        String repPres = DatabaseManagement.getRepublicanPresidentialCandidate();
        String[] senators = DatabaseManagement.getSenateCandidates(ssnn);
        String[] house = DatabaseManagement.getHouseCandidates(state_county);

        demPresBox.setText(demPres);
        demSCBox.setText(null);
        demHRBox.setText(null);
        repPresBox.setText(repPres);
        repSCBox.setText(null);
        repHRBox.setText(null);
    }

    @FXML
    private void votePressed(ActionEvent event) throws Exception{
        Stage voteStage = (Stage) voteButton.getScene().getWindow();

        Alert voteTwice = new Alert(Alert.AlertType.ERROR);
        Alert confirmationBox = new Alert(Alert.AlertType.CONFIRMATION);

        if (demPresBox.isSelected() && repPresBox.isSelected()){
            voteTwice.setTitle("Error!");
            voteTwice.setHeaderText("Can't vote for two candidates in one race!");
            voteTwice.setContentText("Please select only one president");

            voteTwice.showAndWait();
        }

        else if (demSCBox.isSelected() && repSCBox.isSelected()){
            voteTwice.setTitle("Error!");
            voteTwice.setHeaderText("Can't vote for two candidates in one race!");
            voteTwice.setContentText("Please select only one Senate member");

            voteTwice.showAndWait();
        }

        else if (demHRBox.isSelected() && repHRBox.isSelected()){
            voteTwice.setTitle("Error!");
            voteTwice.setHeaderText("Can't vote for two candidates in one race!");
            voteTwice.setContentText("Please select only one House Representative");

            voteTwice.showAndWait();
        }

        else {

            confirmationBox.setTitle("");
            confirmationBox.setHeaderText("Are you sure these are your candidates?");
            confirmationBox.setContentText("Press OK if you are sure");


            Optional<ButtonType> confirm = confirmationBox.showAndWait();
            if (confirm.get() == ButtonType.OK){
                if (demPresBox.isSelected()){
                    DatabaseManagement.presidentialRaceVote(id, "democrat");
                }
                else if (repPresBox.isSelected()){
                    DatabaseManagement.presidentialRaceVote(id, "republican");
                }

                if (demSCBox.isSelected()){
                    DatabaseManagement.senateRaceVote(state_county, "democrat");
                }
                else if (repSCBox.isSelected()){
                    DatabaseManagement.senateRaceVote(state_county, "republican");
                }

                if (demHRBox.isSelected()){
                    DatabaseManagement.houseRaceVote(ssnn, "democrat");
                }
                else if(repHRBox.isSelected()){
                    DatabaseManagement.houseRaceVote(ssnn, "republican");
                }

                voteStage.close();
            }
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSsnn(String ssnn) {
        this.ssnn = ssnn;
    }

    public void setState_county(String state_county) {
        this.state_county = state_county;
    }
}
