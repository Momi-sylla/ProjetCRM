package CRM;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CRMService implements VirtualCRMService{

    private final String CLIENT_ID = "3MVG9t0sl2P.pByp0SnSA6Bzh2XDVY0n37pe_gz.hrStcxxQSVIBhVP20m71vfl92KK7.whRIvdhvbrVbIw.v";
    private final String CLIENT_SECRET = "89DAE17221F8CDA71D4BFD665060A1033A902B603B9615A823ED18D7DD4A0A1E";
    private final String USERNAME = "msylla@univ-angers.fr";
    private final String PASSWORD = "Projet2022";
    private final String URI = "https://universityofangers2-dev-ed.my.salesforce.com/services/data/v45.0/query?";
    private static String TOKEN;
    private static String URL;

    public CRMService() throws URISyntaxException, IOException, InterruptedException {
        ArrayList<String> data = getUrlAndToken();
        CRMService.URL = data.get(0);
        CRMService.TOKEN = data.get(1);
    }

    @Override
    public List<LeadTo> findLeads(double lowAnnualRevenue, double highANnualRevenue, String state) throws Exception {
        ArrayList<LeadTo> leads= new ArrayList<>();

        //encodage des caractères speciaux
        String sup= URLEncoder.encode(">","UTF-8");
        String inf = URLEncoder.encode("<","UTF-8");

        //conversion des doubles pour eviter la notation scientifique
        String low =new BigDecimal(lowAnnualRevenue).toPlainString();
        String high =new BigDecimal(highANnualRevenue).toPlainString();

        //requête SOQL pour saleforce
        String sqlRequest = "q=SELECT+FirstName,LastName,phone,street,postalcode,city,country,AnnualRevenue+FROM+Lead+where+AnnualRevenue+"+sup+"+"+low+"+and+"+"AnnualRevenue+"+inf+"+"+high;
//		String sqlRequest = "q=SELECT+FirstName,LastName,ConvertedAccountId,AnnualRevenue+FROM+Lead+where+AnnualRevenue+=+350000000";

        //requete get pour la réupération des informations
        HttpRequest getReq = (HttpRequest) HttpRequest.newBuilder()
                .uri(new URI(URI+sqlRequest))
                .headers("Content-Type", "application/x-www-form-urlencoded","Authorization","Bearer " + CRMService.TOKEN,"Accept","application/json")
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> getResponses = client.send(getReq, HttpResponse.BodyHandlers.ofString());

        JSONObject results = new JSONObject(getResponses.body());
        JSONArray records = results.getJSONArray("records");

        for(Object record : records) {
            LeadTo lead = new LeadTo();
            lead.setFirstName(((JSONObject) record).getString("FirstName"));
            lead.setLastName(((JSONObject) record).getString("LastName"));
            lead.setAnnualRevenue(((JSONObject) record).getDouble("AnnualRevenue"));
            lead.setPhone(((JSONObject) record).getString("Phone"));
            lead.setStreet(((JSONObject) record).getString("Street"));
            lead.setPostalCode(((JSONObject) record).getString("PostalCode"));
            lead.setCity(((JSONObject) record).getString("City"));
            lead.setCountry(((JSONObject) record).getString("Country"));
            lead.getGeoGraphicPointTo();
            leads.add(lead);
        }

        return leads;
    }

    @Override
    public List<LeadTo> findLeadsByDate(Calendar StartDate, Calendar endDate) {
        // TODO Auto-generated method stub
        return null;
    }

    public ArrayList<String> getUrlAndToken() throws URISyntaxException, IOException, InterruptedException {
        ArrayList<String> urlAndToken = new ArrayList<>();

        HttpRequest postReq = (HttpRequest) HttpRequest.newBuilder()
                .uri(new URI("https://login.salesforce.com/services/oauth2/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=password "+"&client_id="+CLIENT_ID +
                        "&client_secret="+CLIENT_SECRET + "&username="+USERNAME + "&password="+PASSWORD))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String>postResponses = client.send(postReq, HttpResponse.BodyHandlers.ofString());
        JSONObject myObject = new JSONObject(postResponses.body());

        urlAndToken.add(myObject.getString("instance_url"));
        urlAndToken.add(myObject.getString("access_token"));

        return urlAndToken;
    }
}
