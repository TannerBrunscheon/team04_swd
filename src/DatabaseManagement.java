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
    private static final String HOUSE_ID = "1uCVZ7lJXJZC-W7_XyBZEaoSoa9cp1q7OFFkEuQyt";
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

    public static String getDemocraticPresidentialCandidate() {
        return democraticPresidentialCandidate;
    }

    public static void setDemocraticPresidentialCandidate(String democraticPresidentialCandidate) {
        DatabaseManagement.democraticPresidentialCandidate = democraticPresidentialCandidate;
    }

    public static String getRepublicanPresidentialCandidate() {
        return republicanPresidentialCandidate;
    }

    public static void setRepublicanPresidentialCandidate(String republicanPresidentialCandidate) {
        DatabaseManagement.republicanPresidentialCandidate = republicanPresidentialCandidate;
    }

    public static void presidentialRaceVote(String state_county, String vote){
        try {
            ResultSet resultSet = null;
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String statement = "SELECT * FROM presidentrace WHERE id = " + state_county;
            selectAllValue = connection.prepareStatement(statement);
            resultSet = selectAllValue.executeQuery();
            resultSet.updateInt(vote, resultSet.getInt(vote) + 1);
        } catch (SQLException e) {
            e.printStackTrace();
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
                    if (resultSet.getString("ss-nn").contains("IA-")) {
                        Fusiontables.Query.Sql sql = fusiontables.query().sql("SELECT ROWID FROM " + HOUSE_ID + " WHERE ss-nn = '"+ resultSet.getString("ss-nn") +"'");
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
                JSON_FACTORY, new InputStreamReader(toFusionTableTest.class.getResourceAsStream("/client_id.json")));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, Collections.singleton(FusiontablesScopes.FUSIONTABLES)).setDataStoreFactory(dataStoreFactory).build();


        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }
}