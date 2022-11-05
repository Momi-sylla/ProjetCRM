package crm;

import gen.Lead;
import interfaces.Proxy;
import mappers.VirtualCRMMappers;
import models.LeadTo;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SalesForceCRM implements Proxy {
    private final String CLIENT_ID = "3MVG9t0sl2P.pByp0SnSA6Bzh2XDVY0n37pe_gz.hrStcxxQSVIBhVP20m71vfl92KK7.whRIvdhvbrVbIw.v";
    private final String CLIENT_SECRET = "89DAE17221F8CDA71D4BFD665060A1033A902B603B9615A823ED18D7DD4A0A1E";
    private final String USERNAME = "msylla@univ-angers.fr";
    private final String PASSWORD = "Projet2022";
    private final String URI = "https://universityofangers2-dev-ed.my.salesforce.com/services/data/v45.0/query?";
    private static String TOKEN;
    private static String URL;
    private static SalesForceCRM instance = null;

    private SalesForceCRM() throws URISyntaxException, IOException, InterruptedException {
        ArrayList<String> data = getUrlAndToken();
        SalesForceCRM.URL = data.get(0);
        SalesForceCRM.TOKEN = data.get(1);
    }

    public static SalesForceCRM getSalesForceCRM() throws URISyntaxException, IOException, InterruptedException {
        if (SalesForceCRM.instance == null) {
            SalesForceCRM.instance = new SalesForceCRM();
        }
        return SalesForceCRM.instance;
    }

    @Override
    public List<Lead> getLeads(double lowAnnualRevenue, double highANnualRevenue, String state) throws Exception {
        ArrayList<Lead> leads= new ArrayList<>();

        //encodage des caractères speciaux
        String sup = URLEncoder.encode(">","UTF-8");
        String inf = URLEncoder.encode("<","UTF-8");

        //conversion des doubles pour eviter la notation scientifique
        String low = new BigDecimal(lowAnnualRevenue).toPlainString();
        String high = new BigDecimal(highANnualRevenue).toPlainString();

        //requête SOQL pour saleforce
        String sqlRequest = "q=SELECT+FirstName,LastName,phone,street,postalcode,city,CreatedDate,country,AnnualRevenue+FROM+Lead+where+AnnualRevenue+"+sup+"+"+low+"+and+"+"AnnualRevenue+"+inf+"+"+high;
        System.out.println("SQL Request : " + sqlRequest);
        leads = recupLeads(getSaleforceResponses(sqlRequest));


        return leads;
    }

    public ArrayList<Lead> recupLeads(JSONArray records) throws URISyntaxException, IOException, InterruptedException, ParseException, DatatypeConfigurationException {
        ArrayList<Lead> leads= new ArrayList<>();
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
            String dateTimeZone = ((JSONObject) record).getString("CreatedDate");
            dateTimeZone=dateTimeZone.substring(0,dateTimeZone.indexOf("T"));
            Date date = toDate(dateTimeZone);
            lead.setCreationDate(date);
            leads.add(VirtualCRMMappers.mapLeadToFromLead(lead));

        }
        return leads;
    }

    public JSONArray getSaleforceResponses(String sqlRequest) throws URISyntaxException, IOException, InterruptedException {
        //requete get pour la réupération des informations
        HttpRequest getReq = (HttpRequest) HttpRequest.newBuilder()
                .uri(new URI(URI + sqlRequest))
                .headers("Content-Type", "application/x-www-form-urlencoded","Authorization","Bearer " + SalesForceCRM.TOKEN,"Accept","application/json")
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> getResponses = client.send(getReq, HttpResponse.BodyHandlers.ofString());

        System.out.println("Body from Salesforce : " + getResponses.body());
        JSONObject results = new JSONObject(getResponses.body());
        JSONArray records = results.getJSONArray("records");
        return records;
    }
    public static Date toDate(String dateStr) throws ParseException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate dateTime = LocalDate.parse(dateStr, formatter);
        Date date = Date.from(dateTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
            return date;
    }
    @Override
    public List<Lead> getLeadsByDate(Calendar StartDate, Calendar endDate) throws IOException, InterruptedException, URISyntaxException, DatatypeConfigurationException, ParseException {
        ArrayList<Lead> leads= new ArrayList<>();
        //encodage des caractères speciaux
        String sup = URLEncoder.encode(">","UTF-8");
        String inf = URLEncoder.encode("<","UTF-8");
        String startDateStr =StartDate.toString();
        String endDateStr =endDate.toString();
        String sqlRequest = "q=SELECT+FirstName,LastName,phone,street,postalcode,city,CreatedDate,country,AnnualRevenue+FROM+Lead+where+CreatedDate+"+sup+"+"+startDateStr+"+and+"+"AnnualRevenue+"+inf+"+"+endDateStr;
        leads=recupLeads(getSaleforceResponses(sqlRequest));
        return leads;
    }

    public ArrayList<String> getUrlAndToken() throws URISyntaxException, IOException, InterruptedException {
        ArrayList<String> urlAndToken = new ArrayList<>();

        HttpRequest postReq = (HttpRequest) HttpRequest.newBuilder()
                .uri(new URI("https://login.salesforce.com/services/oauth2/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=password " + "&client_id="+ CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&username=" + USERNAME + "&password=" + PASSWORD))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String>postResponses = client.send(postReq, HttpResponse.BodyHandlers.ofString());
        JSONObject myObject = new JSONObject(postResponses.body());

        urlAndToken.add(myObject.getString("instance_url"));
        urlAndToken.add(myObject.getString("access_token"));

        return urlAndToken;
    }

}
