package models;

import gen.Lead;
import impl.VirtualCRMServiceImpl;
import mappers.VirtualCRMMappers;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RSSFeed {

    List<Lead> leads = new ArrayList<>();
    public Document createFeedForClients() throws DatatypeConfigurationException, IOException, URISyntaxException, ParseException, InterruptedException, JDOMException {
        Document document = new Document();
        Element root = new Element("feed");
        Element feedName = new Element("name");
        feedName.setText("Last Potential Clients");
        Element feedDescription = new Element("description");
        feedDescription.setText("Clients created within 24 hours");
        root.addContent(feedName);
        root.addContent(feedDescription);
        VirtualCRMServiceImpl virtualCRMService = VirtualCRMServiceImpl.getVirtualCRMServiceImpl();
        long todayMillis = System.currentTimeMillis();
        long dayToMillis = 86400000;
        Date today = new Date(todayMillis);
        Date yesterday = new Date(todayMillis-dayToMillis);
        Calendar yest = VirtualCRMMappers.mapDateToXMLGregorianCalendar(yesterday).toGregorianCalendar();
        Calendar tod = VirtualCRMMappers.mapDateToXMLGregorianCalendar(today).toGregorianCalendar();
        leads=virtualCRMService.findLeadsByDate(yest,tod);

        if(leads.size()>0) {
            Element clients = new Element("clients");

            for (Lead lead : leads) {
                Element client = new Element("client");
                Element title = new Element("title");
                title.addContent(new Element("firstName").setText(lead.getFirstName()));
                title.addContent(new Element("lastName").setText(lead.getLastName()));
                title.addContent(new Element("company").setText(lead.getCompany()));

                Element desc= new Element("description");
                desc.addContent(new Element("phone").setText(lead.getPhone()));
                Element pubDate= new Element("pubDate");
                pubDate.addContent(new Element("creationDate").setText(VirtualCRMMappers.mapCalendarToDateString(lead.getCreationDate().toGregorianCalendar())));
                client.addContent(title);
                client.addContent(desc);
                client.addContent(pubDate);
                clients.addContent(client);
            }
            root.addContent(clients);
        }

        document.setContent(root);
        return document;
    }

    public static String toString(Document doc) throws IOException {
        XMLOutputter xmlOutput = new XMLOutputter();
        String xmlString = null;
        xmlOutput.setFormat(Format.getPrettyFormat());
        xmlString=  xmlOutput.outputString(doc);
        return  xmlString;
    }

    public List<RSSFeedText> getPotentialClients(Document document) throws DatatypeConfigurationException, IOException, URISyntaxException, ParseException, JDOMException, InterruptedException {
    List<RSSFeedText> feedTexts = new ArrayList<>();
        String docString = toString(document);
        JSONObject feed= (JSONObject) XML.toJSONObject(docString).get("feed");
        if(feed.has("clients")){
            JSONObject clients = (JSONObject) feed.get("clients");
            System.out.println(clients.get("client").getClass().getName());
            if(clients.get("client").getClass().getName().equals("org.json.JSONArray")){
                JSONArray client= (JSONArray) clients.get("client");
                for(int i=0;i<client.length();i++){
                    RSSFeedText rssFeedText = new RSSFeedText();
                    JSONObject cli = client.getJSONObject(i);
                   JSONObject description = cli.getJSONObject("description");
                    rssFeedText.setDescription(description.getString("phone"));
                    JSONObject titleNode = cli.getJSONObject("title");
                    rssFeedText.setTitle(titleNode.getString("firstName")+" "+titleNode.getString("lastName")+ " - "+titleNode.getString("company"));
                    JSONObject pubDateNode = cli.getJSONObject("pubDate");
                    rssFeedText.setPubDate(pubDateNode.getString("creationDate"));
                    System.out.println(pubDateNode.getString("creationDate"));

                    feedTexts.add(rssFeedText);
                }
            }
            else if(clients.get("client").getClass().getName().equals("org.json.JSONObject")){
                RSSFeedText rssFeedText = new RSSFeedText();
                JSONObject cli = (JSONObject) clients.get("client");
                JSONObject description = cli.getJSONObject("description");
                rssFeedText.setDescription(description.getString("phone"));
                JSONObject titleNode = cli.getJSONObject("title");
                rssFeedText.setTitle(titleNode.getString("firstName")+" "+titleNode.getString("lastName")+ " - "+titleNode.getString("company"));
                JSONObject pubDateNode = cli.getJSONObject("pubDate");
                rssFeedText.setPubDate(pubDateNode.getString("creationDate"));
                System.out.println(pubDateNode.getString("creationDate"));

                feedTexts.add(rssFeedText);
            }
        }

       return feedTexts;
    }

}
