package crm;

import fakedata.Fakedata;
import gen.Lead;
import interfaces.Proxy;
import mappers.VirtualCRMMappers;
import models.LeadTo;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class InternalCRM implements Proxy {
    private List<Lead> fakeDataOfLeads;
    private String apiRequest;
    private static InternalCRM instance = null;

    private InternalCRM() throws IOException, DatatypeConfigurationException {
        this.fakeDataOfLeads = (ArrayList<Lead>) Fakedata.getFakeData().generateData();
        this.apiRequest = "http://localhost:8080/ws/leads";
    }

    public static InternalCRM getInternalCRM() throws IOException, DatatypeConfigurationException {
        if (InternalCRM.instance == null) {
            InternalCRM.instance = new InternalCRM();
        }
        return InternalCRM.instance;
    }

    public List<Lead> getLeadsInFakeData(double lowAnnualRevenue, double highAnnualRevenue, String state){
        List<Lead> matchedLeads = new ArrayList<>();
        for(Lead lead : this.fakeDataOfLeads){
            if(lead.getAnnualRevenue()>lowAnnualRevenue && lead.getAnnualRevenue()<highAnnualRevenue){
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


      /*  InputStream stream = new ByteArrayInputStream(responses.body().getBytes("UTF-8"));
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(stream);
        Element rootElement = document.getRootElement();

        List<Element> envs = rootElement.getChildren();
        List<Element> body = envs.get(1).getChildren();
        Element e = body.get(0);

        List<Element> leadsElement= e.getChildren();
       for(int i=0;i<leadsElement.size();i++){
           List<Element> el = leadsElement.get(i).getChildren();
           Iterator elIterator = el.iterator();
           LeadTo leadTo = new LeadTo();
           while (elIterator.hasNext()){
               Element  lead = (Element) elIterator.next();
            //   System.out.println(lead.getName());
               if(lead.getName().equals("firstName")){
                   leadTo.setFirstName(lead.getText());
               }
               if(lead.getName().equals("lastName")){
                   leadTo.setLastName(lead.getText());
               }
               if(lead.getName().equals("annualRevenue")){
                   leadTo.setAnnualRevenue(Double. valueOf(lead.getText()));
               }
               if(lead.getName().equals("phone")){
                   leadTo.setPhone(lead.getText());
               }
               if(lead.getName().equals("street")){
                   leadTo.setStreet(lead.getText());
               }
               if(lead.getName().equals("postalCode")){
                   leadTo.setPostalCode(lead.getText());
               }
               if(lead.getName().equals("city")){
                   leadTo.setCity(lead.getText());
               }
               if(lead.getName().equals("country")){
                   leadTo.setCountry(lead.getText());
               }
           }
           leadTo.getGeoGraphicPointTo();
           leads.add(VirtualCRMMappers.mapLeadToFromLead(leadTo));

       }

        return leads;*/
        leads=recupInternalLead(responses);
        return leads;
    }

    public  ArrayList<Lead> recupInternalLead( HttpResponse<String> responses ) throws URISyntaxException, IOException, InterruptedException, DatatypeConfigurationException, JDOMException {
        ArrayList<Lead> leads= new ArrayList<>();
        InputStream stream = new ByteArrayInputStream(responses.body().getBytes("UTF-8"));
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(stream);
        Element rootElement = document.getRootElement();

        List<Element> envs = rootElement.getChildren();
        List<Element> body = envs.get(1).getChildren();
        Element e = body.get(0);

        List<Element> leadsElement= e.getChildren();
        for(int i=0;i<leadsElement.size();i++){
            List<Element> el = leadsElement.get(i).getChildren();
            Iterator elIterator = el.iterator();
            LeadTo leadTo = new LeadTo();
            while (elIterator.hasNext()){
                Element  lead = (Element) elIterator.next();
                //   System.out.println(lead.getName());
                if(lead.getName().equals("firstName")){
                    leadTo.setFirstName(lead.getText());
                }
                if(lead.getName().equals("lastName")){
                    leadTo.setLastName(lead.getText());
                }
                if(lead.getName().equals("annualRevenue")){
                    leadTo.setAnnualRevenue(Double. valueOf(lead.getText()));
                }
                if(lead.getName().equals("phone")){
                    leadTo.setPhone(lead.getText());
                }
                if(lead.getName().equals("street")){
                    leadTo.setStreet(lead.getText());
                }
                if(lead.getName().equals("postalCode")){
                    leadTo.setPostalCode(lead.getText());
                }
                if(lead.getName().equals("city")){
                    leadTo.setCity(lead.getText());
                }
                if(lead.getName().equals("country")){
                    leadTo.setCountry(lead.getText());
                }
            }
            leadTo.getGeoGraphicPointTo();
            leads.add(VirtualCRMMappers.mapLeadToFromLead(leadTo));

        }
        return leads;
    }

    @Override
    public List<Lead> getLeadsByDate(Calendar StartDate, Calendar endDate) throws IOException, InterruptedException, URISyntaxException, DatatypeConfigurationException, JDOMException {

        ArrayList<Lead> leads= new ArrayList<>();
        String bodyOfSOAPData =
                "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'\n" +
                        "                  xmlns:gs='http://www.internalLead.com/springsoap/gen'>\n" +
                        "    <soapenv:Header/>\n" +
                        "    <soapenv:Body>\n" +
                        "        <gs:getLeadsByDateRequest>\n" +
                        "            <gs:startDate>"+StartDate+"</gs:startDate>\n" +
                        "            <gs:endDate>"+endDate+"</gs:endDate>\n" +
                        "        </gs:getLeadsByDateRequest>\n" +
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
        leads=recupInternalLead(responses);
        return leads;

    }

    public List<Lead> getLeadsByDateInFakeData(Calendar StartDate, Calendar endDate) {
        List<Lead> matchedLeads = new ArrayList<>();
        for(Lead lead : this.fakeDataOfLeads){
            long diff1 = getMillisBetween(StartDate,lead.getCreationDate().toGregorianCalendar());
            long diff2 = getMillisBetween(endDate,lead.getCreationDate().toGregorianCalendar());
            if((diff1>0) &&(diff2<0)){
                matchedLeads.add(lead);
            }
        }
        return matchedLeads;
    }
    public static long getMillisBetween(Calendar startDate, Calendar endDate) {
        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();
        return end - start;
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
