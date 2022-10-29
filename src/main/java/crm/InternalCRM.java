package crm;

import fakedata.Fakedata;
import gen.Lead;
import interfaces.Proxy;
import mappers.VirtualCRMMappers;
import models.LeadTo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class InternalCRM implements Proxy {
    private List<Lead> fakeDataOfLeads;
    private String apiRequest;
    private static InternalCRM instance = null;

    private InternalCRM() throws IOException {
        this.fakeDataOfLeads = (ArrayList<Lead>) Fakedata.getFakeData().generateData();
        this.apiRequest = "http://localhost:8080/ws/leads";
    }

    public static InternalCRM getInternalCRM() throws IOException {
        if (InternalCRM.instance == null) {
            InternalCRM.instance = new InternalCRM();
        }
        return InternalCRM.instance;
    }

    public List<Lead> getLeadsInFakeData(double lowAnnualRevenue, double highAnnualRevenue, String state){
        List<Lead> matchedLeads = new ArrayList<>();
        for(Lead lead : this.fakeDataOfLeads){
            if(lead.getAnnualRevenue()>lowAnnualRevenue || lead.getAnnualRevenue()<highAnnualRevenue){
                matchedLeads.add(lead);
            }
        }
        return matchedLeads;
    }


    @Override
    public List<Lead> getLeads(double lowAnnualRevenue, double highAnnualRevenue, String state) throws Exception {
        ArrayList<Lead> leads= new ArrayList<>();
        String bodyOfSOAPData =
                "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'\n" +
                "                  xmlns:gs='http://www.internalLead.com/springsoap/gen'>\n" +
                "    <soapenv:Header/>\n" +
                "    <soapenv:Body>\n" +
                "        <gs:getLeadsRequest>\n" +
                "            <gs:lowAnnualRevenue> " + lowAnnualRevenue + "</gs:lowAnnualRevenue>\n" +
                "            <gs:highAnnualRevenue> " + highAnnualRevenue + "</gs:highAnnualRevenue>\n" +
                "            <gs:State> " + state + "</gs:State>\n" +
                "        </gs:getLeadsRequest>\n" +
                "    </soapenv:Body>\n" +
                "</soapenv:Envelope>\n";

        //requete post pour la réupération des informations
        HttpRequest postReq = (HttpRequest) HttpRequest.newBuilder()
                .uri(new URI(this.apiRequest))
                .header("Content-Type", "text/xml")
                .POST(HttpRequest.BodyPublishers.ofString(bodyOfSOAPData))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> responses = client.send(postReq, HttpResponse.BodyHandlers.ofString());

        String res = responses.body().replace("xmlns:","");
        String res2= res.replace("SOAP-ENV:","ENV");//replaces all occurrences of "is" to "was"
        String res3= res2.replace("ns2:","");//replaces all occurrences of "is" to "was"


        JSONObject results = XML.toJSONObject(res3);
        System.out.println(results);
        JSONObject env = results.getJSONObject("ENVEnvelope");
        JSONObject env2 = env.getJSONObject("ENVBody");
        JSONObject env3= env2.getJSONObject("getleadsResponse");

        System.out.println(env3);

        JSONArray records = env3.getJSONArray("Lead");
        System.out.println(records);
/*
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
            leads.add(VirtualCRMMappers.mapLeadToFromLead(lead));
        }
*/
        return leads;
    }

    @Override
    public List<Lead> getLeadsByDate(Calendar StartDate, Calendar endDate) {
        // DO THIS FUNCTION
        return null;
    }

    public List<Lead> getFakeDataOfLeads() {
        return fakeDataOfLeads;
    }

    public void setFakeDataOfLeads(List<Lead> fakeDataOfLeads) {
        this.fakeDataOfLeads = fakeDataOfLeads;
    }

    public String getApiRequest() {
        return apiRequest;
    }

    public void setApiRequest(String apiRequest) {
        this.apiRequest = apiRequest;
    }

    public static InternalCRM getInstance() {
        return instance;
    }

    public static void setInstance(InternalCRM instance) {
        InternalCRM.instance = instance;
    }

}
