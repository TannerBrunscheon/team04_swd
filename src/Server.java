

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
import com.google.api.services.fusiontables.model.Column;
import com.google.api.services.fusiontables.model.Table;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;


/**
     * Created by Tanner on 12/1/2016.
     */
    public class Server {
        private static final String APPLICATION_NAME = "ServerForSWD";

        private static final String TABLE_ID = "1jigM5G7EHZlGhAWETMo76ZU2V54oWEniS0T28RoM&";

        private static DataStoreFactory dataStoreFactory;

        private static final java.io.File CREDSTORE =
                new java.io.File("team04_swd/Credentials/");

        private static HttpTransport httpTransport;

        private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

        private static Fusiontables fusiontables;

        private static Credential access()throws Exception{
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                    JSON_FACTORY,new InputStreamReader(Server.class.getResourceAsStream("/client_id.json")));

            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, JSON_FACTORY, clientSecrets,Collections.singleton(FusiontablesScopes.FUSIONTABLES)).setDataStoreFactory(dataStoreFactory).build();



            return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        }

        public static void main(String[] args) {
            try {

                httpTransport = GoogleNetHttpTransport.newTrustedTransport();
                dataStoreFactory = new FileDataStoreFactory(CREDSTORE);
                Credential credential = access();

                fusiontables = new Fusiontables.Builder(httpTransport,JSON_FACTORY,credential).setApplicationName(APPLICATION_NAME).build();
                Table table = new Table();
                table.setName(UUID.randomUUID().toString());
                table.setIsExportable(false);
                table.setDescription("Sample Table");

                // Set columns for new table
                table.setColumns(Arrays.asList(new Column().setName("Text").setType("STRING"),
                        new Column().setName("Number").setType("NUMBER"),
                        new Column().setName("Location").setType("LOCATION"),
                        new Column().setName("Date").setType("DATETIME")));

                // Adds a new column to the table.
                Fusiontables.Table.Insert t = fusiontables.table().insert(table);
                Table r = t.execute();
            }
            catch (Exception e){

            }
        }
    }

