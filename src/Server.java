

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


/**
     * Created by Tanner on 12/1/2016.
     */
    public class Server {
        private static final String APPLICATION_NAME = "ServerForSWD";

        private static final String TABLE_ID = "1yjG0nIuuzsE83rqoWLkFrAvWwoQVMgLmWvhdg5ML";

        private static DataStoreFactory dataStoreFactory;

        private static final java.io.File CREDSTORE =
                new java.io.File("Credentials/");

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
                try {
                    Fusiontables.Query.Sql sql = fusiontables.query().sql("SELECT ROWID FROM " + TABLE_ID + " WHERE  GEO_ID = '1003'");
                    Sqlresponse sqlresponse= sql.execute();

                    Fusiontables.Query.Sql sql1 = fusiontables.query().sql("UPDATE " + TABLE_ID + " SET  Democrat = 1 WHERE ROWID = ''");
                    System.out.println("UPDATE " + TABLE_ID + " SET  Democrat = 1 WHERE ROWID = '"+sqlresponse.toString()+"'");
                    sql1.execute();
                }
                catch (IOException f){
                    throw f;
                }
                Fusiontables.Table.List listTables = fusiontables.table().list();
                TableList tablelist = listTables.execute();
                if (tablelist.getItems() == null || tablelist.getItems().isEmpty()) {
                    System.out.println("No tables found!");
                    return;
                }
                for (Table table : tablelist.getItems()){
                    System.out.println(table.getTableId());
                }


            }
            catch (Exception e){
            }

        }
    }

