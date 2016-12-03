import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.services.civicinfo.CivicInfo;
import com.google.api.services.civicinfo.CivicInfoRequestInitializer;
import com.google.api.services.civicinfo.model.GeographicDivision;
import com.google.api.services.civicinfo.model.RepresentativeInfoResponse;

import java.util.Map;

/**
 * Created by Tanner on 12/3/2016.
 */
public class CivicInformation {
    //public static String  getRepInfo(String address){
    public static void main(String[] args) {
        try {
            CivicInfo civicInfo = new CivicInfo.Builder(GoogleNetHttpTransport.newTrustedTransport(),toFusionTableTest.JSON_FACTORY,null)
                .setApplicationName("ServerForSWD").setGoogleClientRequestInitializer(new CivicInfoRequestInitializer("AIzaSyA-ZVQaE0mN6lEsE5Z1hgppGa0J6oSoDA8")).build();

        String address ="607 Park View Drive Readlyn Iowa 50668";


            RepresentativeInfoResponse response=civicInfo.representatives().representativeInfoByAddress().setAddress(address).execute();
            Map<String,GeographicDivision> map=response.getDivisions();
            map.toString();
        }
        catch (Exception e){

        }
    }
}
