

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
import com.google.api.services.fusiontables.model.Table;
import com.google.api.services.fusiontables.model.TableList;
import javafx.scene.control.Tab;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;


/**
     * Created by Tanner on 12/1/2016.
     */
    public class Server {
    private static final String APPLICATION_NAME = "ServerForSWD";

    private static final String SENATE_ID = "1yjG0nIuuzsE83rqoWLkFrAvWwoQVMgLmWvhdg5ML";
    private static final String HOUSE_ID = "1uCVZ7lJXJZC-W7_XyBZEaoSoa9cp1q7OFFkEuQyt";
    private static final String STATES_ID ="13EUNnd4lN-yrhEc0QfRBMvzaK1QujUYns5m2BcKX";

    private String current;

    private static DataStoreFactory dataStoreFactory;

    private static final java.io.File CREDSTORE =
            new java.io.File("Credentials/");

    private static HttpTransport httpTransport;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static Fusiontables fusiontables;

    private static Credential access() throws Exception {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JSON_FACTORY, new InputStreamReader(Server.class.getResourceAsStream("/client_id.json")));

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
                Fusiontables.Query.Sql sql = fusiontables.query().sql("SELECT ROWID FROM " + SENATE_ID + " WHERE State_County = 'IA-Bremer'");
                Sqlresponse sqlresponse = sql.execute();
                List<List<Object>> list = sqlresponse.getRows();
                String number = list.get(0).toString().replaceAll("[^0-9]", "");
                Fusiontables.Query.Sql sql1 = fusiontables.query().sql("UPDATE " + SENATE_ID + " SET  Democrat = 1 WHERE ROWID = '" + number + "'");
                System.out.println("UPDATE " + SENATE_ID + " SET  Democrat = 1 WHERE ROWID = '" + number + "'");
                sql1.execute();

            } catch (IOException f) {
                throw f;
            }

        } catch (Exception e) {
        }

    }

}


