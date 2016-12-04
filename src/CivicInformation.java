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
        shortToLongStates.put("AL","Alabama");
        shortToLongStates.put('B',"-..");
        shortToLongStates.put('C',"-.-.");
        shortToLongStates.put('D',"-..");
        shortToLongStates.put('E',".");
        shortToLongStates.put('F',"..-.");
        shortToLongStates.put('G',"--.");
        shortToLongStates.put('H',"....");
        shortToLongStates.put('I',"..");
        shortToLongStates.put('J',".---");
        shortToLongStates.put('K',"-.-");
        shortToLongStates.put('L',".-..");
        shortToLongStates.put('M',"--");
        shortToLongStates.put('N',"-.");
        shortToLongStates.put('O',"---");
        shortToLongStates.put('P',".--.");
        shortToLongStates.put('Q',"--.-");
        shortToLongStates.put('R',".-.");
        shortToLongStates.put('S',"...");
        shortToLongStates.put('T',"-");
        shortToLongStates.put('U',"..-");
        shortToLongStates.put('V',"...-");
        shortToLongStates.put('W',".--");
        shortToLongStates.put('X',"-..-");
        shortToLongStates.put('Y',"-.--");
        shortToLongStates.put('Z',"--..");
        shortToLongStates.put('0',"-----");
        shortToLongStates.put('1',".----");
        shortToLongStates.put('2',"..---");
        shortToLongStates.put('3',"...--");
        shortToLongStates.put('4',"....-");
        shortToLongStates.put('5',".....");
        shortToLongStates.put('6',"-....");
        shortToLongStates.put('7',"--...");
        shortToLongStates.put('8',"---..");
        shortToLongStates.put('9',"----.");

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
