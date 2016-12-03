

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
     * Created by Tanner on 12/1/2016.
     */
    public class toFusionTableTest {
    private static final String APPLICATION_NAME = "ServerForSWD";

    private static final String SENATE_ID = "1yjG0nIuuzsE83rqoWLkFrAvWwoQVMgLmWvhdg5ML";
    private static final String HOUSE_ID = "1uCVZ7lJXJZC-W7_XyBZEaoSoa9cp1q7OFFkEuQyt";
    private static final String STATES_ID ="13EUNnd4lN-yrhEc0QfRBMvzaK1QujUYns5m2BcKX";

    private String current;

    protected static DataStoreFactory dataStoreFactory;

    private static final java.io.File CREDSTORE =
            new java.io.File("Credentials/");

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



    private static Credential access() throws Exception {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JSON_FACTORY, new InputStreamReader(toFusionTableTest.class.getResourceAsStream("/client_id.json")));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, Collections.singleton(FusiontablesScopes.FUSIONTABLES)).setDataStoreFactory(dataStoreFactory).build();


        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public static void main(String[] args) {

        try {

            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(CREDSTORE);
            Credential credential = access();

            fusiontables = new Fusiontables.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
            try {
                Fusiontables.Query.Sql sql = fusiontables.query().sql("SELECT ROWID FROM " + SENATE_ID + " WHERE State_County = 'AL-Baldwin'");
                Sqlresponse sqlresponse = sql.execute();
                List<List<Object>> list = sqlresponse.getRows();
                String number = list.get(0).toString().replaceAll("[^0-9]", "");


                // database access
                try {
                    connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    selectAllValue = connection.prepareStatement("SELECT * FROM votinginformation");
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                    System.exit(-1);
                }
                ResultSet resultSet = null;
                try {
                    resultSet = selectAllValue.executeQuery();
                    while (resultSet.next()) {
                        if(resultSet.getString("state_county").equals("AL-Baldwin")){
                            Fusiontables.Query.Sql sql1 = fusiontables.query().sql("UPDATE " + SENATE_ID + " SET  Democrat = " + resultSet.getInt("democrat") + " WHERE ROWID = '" + number + "'");
                            System.out.println("UPDATE " + SENATE_ID + " SET  Democrat = " + resultSet.getInt("democrat") + " WHERE ROWID = '" + number + "'");
                            sql1.execute();
                        }
                        //results.add(new Names(resultSet.getString("id"),
                               // resultSet.getInt("democrat"), resultSet.getInt("republican")
                        //));
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                } finally {
                    try {
                        resultSet.close();
                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                        //close();
                    }
                }

            } catch (IOException f) {
                throw f;
            }

        } catch (Exception e) {
        }

    }

}

