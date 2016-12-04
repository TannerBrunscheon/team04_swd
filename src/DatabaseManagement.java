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


    public static void setPresidentialCandidate(String demCandidate, String repCandidate) {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String statement = "UPDATE presidentrace SET demcandidate = '" + demCandidate + "'";
            connection.prepareStatement(statement).executeQuery();
        } catch (SQLException e) {

        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String statement = "UPDATE presidentrace SET repCandidate = '" + repCandidate + "'";
            connection.prepareStatement(statement).executeQuery();
        } catch (SQLException e) {

        }
    }

    public static String[] getPresidentialCandidate() {
        String[] candidates = new String[2];
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            selectAllValue = connection.prepareStatement("SELECT * FROM presidentrace");
            ResultSet resultSet = selectAllValue.executeQuery();
            while(resultSet.next()){
                candidates[0] = resultSet.getString("demcandidate");
                candidates[1] = resultSet.getString("repcandidate");

            }
        }catch (SQLException e){

        }
        return candidates;
    }

    public static void setRepublicanPresidentialCandidate(String republicanPresidentialCandidate) {
        DatabaseManagement.republicanPresidentialCandidate = republicanPresidentialCandidate;
    }

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

    public static void presidentialRaceVote(String state_county, String vote){
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String statement = "UPDATE presidentrace SET " + vote + " = " + vote + " + 1 WHERE id = '" + state_county +"'";
            connection.prepareStatement(statement).executeQuery();

        } catch (SQLException e) {

        }

    }

    public static void houseRaceVote(String ss_nn, String vote){
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String statement = "UPDATE houserace SET " + vote + " = " + vote + " + 1 WHERE ssnn = '" + ss_nn +"'";
            connection.prepareStatement(statement).executeQuery();

        } catch (SQLException e) {

        }

    }

    public static void senateRaceVote(String state_county, String vote){
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String statement = "UPDATE senaterace SET " + vote + " = " + vote + " + 1 WHERE state_county = '" + state_county +"'";
            connection.prepareStatement(statement).executeQuery();

        } catch (SQLException e) {

        }
    }

    public static void toFusionTable() {
            try {
                httpTransport = GoogleNetHttpTransport.newTrustedTransport();
                dataStoreFactory = new FileDataStoreFactory(CREDSTORE);
                Credential credential = access();

                fusiontables = new Fusiontables.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();


                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

                ResultSet resultSet = null;
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