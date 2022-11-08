package models;

import gen.Lead;
import impl.VirtualCRMServiceImpl;
import mappers.VirtualCRMMappers;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RSSFeed {

    private String title;
    private String description;
    private String pubDate;

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

    public void getPotentialClients() throws DatatypeConfigurationException, IOException, URISyntaxException, ParseException, JDOMException, InterruptedException {
        Document document = createFeedForClients();

    }

}
