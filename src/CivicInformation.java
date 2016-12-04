import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.services.civicinfo.CivicInfo;
import com.google.api.services.civicinfo.CivicInfoRequestInitializer;
import com.google.api.services.civicinfo.model.GeographicDivision;
import com.google.api.services.civicinfo.model.RepresentativeInfoResponse;

import java.util.*;

/**
 * Created by Tanner on 12/3/2016.
 */
public class CivicInformation {
    public static HashMap<String,String> shortToLongStates = new HashMap<String,String>();
    static {

    }
    public static String[] getCounty(String address) {
        String[] strings = new String[2];
        try {
            CivicInfo civicInfo = new CivicInfo.Builder(GoogleNetHttpTransport.newTrustedTransport(), toFusionTableTest.JSON_FACTORY, null)
                    .setApplicationName("ServerForSWD").setGoogleClientRequestInitializer(new CivicInfoRequestInitializer("AIzaSyA-ZVQaE0mN6lEsE5Z1hgppGa0J6oSoDA8")).build();
            RepresentativeInfoResponse response = civicInfo.representatives().representativeInfoByAddress().setAddress(address).execute();
            Map<String, GeographicDivision> map = response.getDivisions();
            Object[] key = map.keySet().toArray();

            for (int i = 0; i < key.length; i++) {
                String string = key[i].toString();
                if (string.contains("cd:")) {
                    String[] strtemp = string.split("/");
                    for (int j = 0; j < strtemp.length; j++) {
                        if (strtemp[j].contains("cd:")) ;
                        {
                            strings[0] = strtemp[j].substring(3);
                        }
                    }
                } else if (string.contains("county")) {
                    String[] strtemp = string.split("/");
                    for (int j = 0; j < strtemp.length; j++) {
                        if (strtemp[j].contains("county")) ;
                        {
                            strings[1] = strtemp[j].substring(7);
                        }
                    }
                }
            }

        } catch (Exception e) {

        }
        if (strings[0] == null)
        {
            strings[0] = "AL";
        }
        return strings;
    }
}
