import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.fusiontables.Fusiontables;
import com.google.api.services.fusiontables.FusiontablesScopes;
import com.google.api.services.fusiontables.model.Sqlresponse;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Collections;
import java.util.List;

/**
 * This class contains all the static methods required to interact with the postgreSQL database
 * It also contains methods to send the data to the google fusion table.
 */
public class DatabaseManagement {
    private static final String APPLICATION_NAME = "ServerForSWD";

    private static final String SENATE_ID = "1yjG0nIuuzsE83rqoWLkFrAvWwoQVMgLmWvhdg5ML";
    private static final String HOUSE_ID = "1OVLmrwJtr8ftuSwPKeSSod4iyP9nHY_Bk1uKEO4R";
    private static final String STATES_ID = "13EUNnd4lN-yrhEc0QfRBMvzaK1QujUYns5m2BcKX";

    protected static DataStoreFactory dataStoreFactory;

    private static final java.io.File CREDSTORE = new java.io.File("Credentials/");

    protected static HttpTransport httpTransport;

    protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static Fusiontables fusiontables;

    // stuff for database:
    //private static final String URL = "jdbc:derby:/scratch/myDB";
    private static final String URL = "jdbc:postgresql://s-l112.engr.uiowa.edu:5432/postgres";
    private static final String USERNAME = "student2";
    private static final String PASSWORD = "hawkeys04";

    private static Connection connection; // manages DB connection
    private static PreparedStatement selectAllValue; // Silly statement for finding values in DB


    private static String democraticPresidentialCandidate;
    private static String republicanPresidentialCandidate;

    /**
     * This is the getter method for the democratic presidential candidate
     * @return Returns a string containing the name of the democratic presidential candidate.
     */
    public static String getDemocraticPresidentialCandidate() {
        return democraticPresidentialCandidate;
    }

    /**
     * This is the setter method for the democratic presidential candidate
     * @param democraticPresidentialCandidate A string containing the desired name of the democratic candidate.
     */
    public static void setDemocraticPresidentialCandidate(String democraticPresidentialCandidate) {
        DatabaseManagement.democraticPresidentialCandidate = democraticPresidentialCandidate;
    }

    /**
     * This is the getter method for the republican presidential candidate
     * @return Returns a string containing the name of the republican presidential candidate.
     */
    public static String getRepublicanPresidentialCandidate() {
        return republicanPresidentialCandidate;
    }

    /**
     * This is the setter method for the republican presidential candidate
     * @param republicanPresidentialCandidate A string containing the desired name of the republican candidate.
     */
    public static void setRepublicanPresidentialCandidate(String republicanPresidentialCandidate) {
        DatabaseManagement.republicanPresidentialCandidate = republicanPresidentialCandidate;
    }

    /**
     * This method uses the SQL database to set the house candidates within a specific house district
     * @param ss_nn The house district where the candidates are to be named
     * @param demCandidate The democratic candidate
     * @param repCandidate The republican candidate
     */
    public static void setHouseCandidates(String ss_nn, String demCandidate, String repCandidate){
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String statement = "UPDATE houserace SET demcandidate = '" + demCandidate + "' WHERE ssnn = '" + ss_nn +"'";
            connection.prepareStatement(statement).executeQuery();
        } catch (SQLException e) {

        }
        try {
            String statement = "UPDATE houserace SET repcandidate = '" + repCandidate + "' WHERE ssnn = '" + ss_nn +"'";
            connection.prepareStatement(statement).executeQuery();
        } catch (SQLException e) {

        }
    }

    /**
     * This method accesses the SQL database to retrieve the house dem and rep candidates within the specific district
     * @param ss_nn The district where the user wants to know the candidates
     * @return An array of strings. Index 0 contains the dem candidate, index 1 contains the rep candidate
     */
    public static String[] getHouseCandidates(String ss_nn){
        String[] candidates = new String[2];
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            selectAllValue = connection.prepareStatement("SELECT * FROM houserace");
            ResultSet resultSet = selectAllValue.executeQuery();
            while(resultSet.next()){
                if(resultSet.getString("ssnn").equals(ss_nn)){
                    candidates[0] = resultSet.getString("demcandidate");
                    candidates[1] = resultSet.getString("repcandidate");
                }

            }
            resultSet.close();
        }catch (SQLException e){

        }
        return candidates;
    }

    /**
     * This method uses the SQL database to set the senate candidates within a specific state and county
     * @param state_county The county where the candidates are to be named
     * @param demCandidate The democratic candidate
     * @param repCandidate The republican candidate
     */
    public static void setSenateCandidates(String state_county, String demCandidate, String repCandidate){
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String statement = "UPDATE senaterace SET demcandidate = '" + demCandidate + "' WHERE strpos(state_county, '" + state_county + "') > 0";
            connection.prepareStatement(statement).executeQuery();

        } catch (SQLException e) {

        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String statement = "UPDATE senaterace SET repcandidate = '" + repCandidate + "' WHERE strpos(state_county, '" + state_county + "') > 0" ;
            connection.prepareStatement(statement).executeQuery();

        } catch (SQLException e) {

        }
    }

    /**
     * This method accesses the SQL database to retrieve the house dem and rep candidates within the specific state
     * @param state_county The state where the user wants to know the candidates
     * @return An array of strings. Index 0 contains the dem candidate, index 1 contains the rep candidate
     */
    public static String[] getSenateCandidates(String state_county){
        String[] candidates = new String[2];
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            selectAllValue = connection.prepareStatement("SELECT * FROM senaterace");
            ResultSet resultSet = selectAllValue.executeQuery();
            while(resultSet.next()){
                if(resultSet.getString("state_county").equals(state_county)){
                    candidates[0] = resultSet.getString("demcandidate");
                    candidates[1] = resultSet.getString("repcandidate");
                }

            }
        }catch (SQLException e){

        }
        return candidates;
    }

    /**
     * This method is used to vote for a candidate at the presidential level. It increments a value in the sql database
     * by 1
     * @param state_county The state where the user is voting
     * @param vote The vote the user is selecting either dem or rep
     */
    public static void presidentialRaceVote(String state_county, String vote){
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String statement = "UPDATE presidentrace SET " + vote + " = " + vote + " + 1 WHERE id = '" + state_county +"'";
            connection.prepareStatement(statement).executeQuery();

        } catch (SQLException e) {

        }

    }

    /**
     * This casts a single vote in a house race, incrementing a specific sql value by 1
     * @param ss_nn The house district where the user is voting
     * @param vote The vote, either democrat or republican
     */
    public static void houseRaceVote(String ss_nn, String vote){
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String statement = "UPDATE houserace SET " + vote + " = " + vote + " + 1 WHERE ssnn = '" + ss_nn +"'";
            connection.prepareStatement(statement).executeQuery();

        } catch (SQLException e) {

        }

    }

    /**
     * This casts a single vote in the senate race.
     * @param state_county This is the state and county where the person is voting from
     * @param vote This is the choice that the user is selecting, either democrat or republican
     */
    public static void senateRaceVote(String state_county, String vote){
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String statement = "UPDATE senaterace SET " + vote + " = " + vote + " + 1 WHERE ssnn = '" + state_county +"'";
            connection.prepareStatement(statement).executeQuery();

        } catch (SQLException e) {

        }
    }

    /**
     * This method is used by the autditor to send some of the data to the Google Fusion table where it can be
     * displayed as a map containing red and blue within specific regions depending on the votes recieved
     */
    public static void toFusionTable() {
            try {
                httpTransport = GoogleNetHttpTransport.newTrustedTransport();
                dataStoreFactory = new FileDataStoreFactory(CREDSTORE);
                Credential credential = access();

                fusiontables = new Fusiontables.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();


                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

                ResultSet resultSet = null;

                // This block sends president race data to the fusion table
                selectAllValue = connection.prepareStatement("SELECT * FROM presidentrace");
                resultSet = selectAllValue.executeQuery();
                int i = 1;
                while (resultSet.next() && i <= 14) {
                    Fusiontables.Query.Sql sql = fusiontables.query().sql("SELECT ROWID FROM " + STATES_ID + " WHERE id = '"+ resultSet.getString("id") +"'");
                    Sqlresponse sqlresponse = sql.execute();
                    List<List<Object>> list = sqlresponse.getRows();
                    String number = list.get(0).toString().replaceAll("[^0-9]", "");

                    sql = fusiontables.query().sql("UPDATE " + STATES_ID + " SET  Democrat = " + resultSet.getInt("democrat") + " WHERE ROWID = '" + number + "'");
                    sql.execute();
                    sql = fusiontables.query().sql("UPDATE " + STATES_ID + " SET  Republican = " + resultSet.getInt("republican") + " WHERE ROWID = '" + number + "'");
                    sql.execute();
                    i++;

                }

                // This block sends senate race data to the fusion table
                selectAllValue = connection.prepareStatement("SELECT * FROM senaterace");
                resultSet = selectAllValue.executeQuery();
                i = 1;
                while (resultSet.next() && i <= 14) {
                    if (resultSet.getString("state_county").contains("IA-")) {
                        Fusiontables.Query.Sql sql = fusiontables.query().sql("SELECT ROWID FROM " + SENATE_ID + " WHERE State_County = '"+ resultSet.getString("state_county") +"'");
                        Sqlresponse sqlresponse = sql.execute();
                        List<List<Object>> list = sqlresponse.getRows();
                        String number = list.get(0).toString().replaceAll("[^0-9]", "");

                        sql = fusiontables.query().sql("UPDATE " + SENATE_ID + " SET  Democrat = " + resultSet.getInt("democrat") + " WHERE ROWID = '" + number + "'");
                        sql.execute();
                        sql = fusiontables.query().sql("UPDATE " + SENATE_ID + " SET  Republican = " + resultSet.getInt("republican") + " WHERE ROWID = '" + number + "'");
                        sql.execute();
                        i++;
                    }
                }

                // This block sends house race data to the fusion table
                selectAllValue = connection.prepareStatement("SELECT * FROM houserace");
                resultSet = selectAllValue.executeQuery();
                i = 1;
                while (resultSet.next() && i <= 14) {
                    if (resultSet.getString("ssnn").contains("IA-")) {
                        Fusiontables.Query.Sql sql = fusiontables.query().sql("SELECT ROWID FROM " + HOUSE_ID + " WHERE SSNN = '"+ resultSet.getString("ssnn") +"'");
                        Sqlresponse sqlresponse = sql.execute();
                        List<List<Object>> list = sqlresponse.getRows();
                        String number = list.get(0).toString().replaceAll("[^0-9]", "");

                        sql = fusiontables.query().sql("UPDATE " + HOUSE_ID + " SET  Democrat = " + resultSet.getInt("democrat") + " WHERE ROWID = '" + number + "'");
                        sql.execute();
                        sql = fusiontables.query().sql("UPDATE " + HOUSE_ID + " SET  Republican = " + resultSet.getInt("republican") + " WHERE ROWID = '" + number + "'");
                        sql.execute();
                        i++;
                    }
                }



                resultSet.close();
            }catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }catch (IOException f){
                f.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }


    }


    /**
     * This method............................................
     * @return
     * @throws Exception
     */
    private static Credential access() throws Exception {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JSON_FACTORY, new InputStreamReader(DatabaseManagement.class.getResourceAsStream("/client_id.json")));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, Collections.singleton(FusiontablesScopes.FUSIONTABLES)).setDataStoreFactory(dataStoreFactory).build();


        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }
}