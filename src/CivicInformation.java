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
    /**
     * This is the primary access for the google civics api.
     * @param address Addess for lookup
     * @return County and Congressional District of adress
     */
    public static String[] getCounty(String address) {
        String[] strings = new String[2];
        try {
            CivicInfo civicInfo = new CivicInfo.Builder(GoogleNetHttpTransport.newTrustedTransport(), DatabaseManagement.JSON_FACTORY, null)
                    .setApplicationName("ServerForSWD").setGoogleClientRequestInitializer(new CivicInfoRequestInitializer("AIzaSyA-ZVQaE0mN6lEsE5Z1hgppGa0J6oSoDA8")).build();
            //Initiallize the civic info grabber
            RepresentativeInfoResponse response = civicInfo.representatives().representativeInfoByAddress().setAddress(address).execute();
            //Get the response for a query using the address given
            Map<String, GeographicDivision> map = response.getDivisions();
            //Get the response to the query
            Object[] key = map.keySet().toArray();
            //Grab the string part of the query which are keys
            for (int i = 0; i < key.length; i++) {
                String string = key[i].toString();
                if (string.contains("cd:")) {//Look for congressional district
                    String[] strtemp = string.split("/");//Split to grab the county and congressional district
                    for (int j = 0; j < strtemp.length; j++) {
                        if (strtemp[j].contains("cd:")) ;//Grab everything after the cd for the district
                        {
                            strings[0] = strtemp[j].substring(3);
                        }
                    }
                } else if (string.contains("county")) {
                    String[] strtemp = string.split("/");
                    for (int j = 0; j < strtemp.length; j++) {//Grab everything after county
                        if (strtemp[j].contains("county")) ;
                        {
                            strings[1] = strtemp[j].substring(7);
                        }
                    }
                }
            }

        } catch (Exception e) {

        }

        if (strings[0] == null) {//Send back at large if there is no congressional district
            strings[0] = "AL";
        } else if (Integer.parseInt(strings[0]) < 10) {
            strings[0] = "0"+strings[0];
            }
        return strings;
    }
}
