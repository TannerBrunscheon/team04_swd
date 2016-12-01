import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
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

    private static FileDataStoreFactory dataStoreFactory;

    private static HttpTransport httpTransport;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static Fusiontables fusiontables;

    private Credential access()throws Exception{
            GoogleClientSecrets secrets = GoogleClientSecrets.load(
                    JSON_FACTORY, new InputStreamReader(
                            this.getClass().getResourceAsStream("/client_id.json")));

            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, JSON_FACTORY, secrets,
                    Collections.singleton(FusiontablesScopes.FUSIONTABLES)).setDataStoreFactory(
                    dataStoreFactory).build();

        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public Server() {

    }
}
