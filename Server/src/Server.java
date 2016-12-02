
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.fusiontables.Fusiontables;
import com.google.api.services.fusiontables.FusiontablesScopes;
import java.io.InputStreamReader;
import java.util.Collections;



    /**
     * Created by Tanner on 12/1/2016.
     */
    public class Server {
        private static final String APPLICATION_NAME = "ServerForSWD";

        private static final String TABLE_ID = "1jigM5G7EHZlGhAWETMo76ZU2V54oWEniS0T28RoM&";

        private static FileDataStoreFactory dataStoreFactory;

        private static final java.io.File CREDSTORE =
                new java.io.File("team04_swd/Credentials/");

        private static HttpTransport httpTransport;

        private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

        private static Fusiontables fusiontables;

        private static Credential access()throws Exception{
            GoogleClientSecrets secrets = GoogleClientSecrets.load(
                    JSON_FACTORY, new InputStreamReader(
                            Server.class.getResourceAsStream("/client_id.json")));

            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, JSON_FACTORY, secrets,
                    Collections.singleton(FusiontablesScopes.FUSIONTABLES)).setDataStoreFactory(
                    dataStoreFactory).build();

            return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        }

        public static void main(String[] args) {
            try {

                httpTransport = GoogleNetHttpTransport.newTrustedTransport();
                dataStoreFactory = new FileDataStoreFactory(CREDSTORE);
                Credential credential = access();
                fusiontables = new Fusiontables.Builder(httpTransport,JSON_FACTORY,credential).setApplicationName(APPLICATION_NAME).build();
                Fusiontables.Query.Sql sql = fusiontables.query().sql("SELECT Text,Number,Location,Date FROM " + TABLE_ID);

            }
            catch (Exception e){

            }
        }
    }

