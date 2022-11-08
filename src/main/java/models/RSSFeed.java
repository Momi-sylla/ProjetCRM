package models;

import gen.Lead;
import impl.VirtualCRMServiceImpl;
import mappers.VirtualCRMMappers;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Node;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
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
                clients.addContent(new Element("firstName").setText(lead.getFirstName()));
                clients.addContent(new Element("lastName").setText(lead.getLastName()));
                clients.addContent(new Element("company").setText(lead.getCompany()));
                clients.addContent(new Element("creationDate").setText(VirtualCRMMappers.mapCalendarToDateString(lead.getCreationDate().toGregorianCalendar())));
                clients.addContent(new Element("phone").setText(lead.getPhone()));
            }
            root.addContent(clients);

        }
        document.setContent(root);
        //XMLOutputter outputter = new XMLOutputter();
        //outputter.output(document, System.out);

        return document;
    }

    public static String toString(Document doc) {
       return new XMLOutputter(Format.getPrettyFormat() ).outputString(doc);
    }
    public void getPotentialClients() throws DatatypeConfigurationException, IOException, URISyntaxException, ParseException, JDOMException, InterruptedException {
        Document document = createFeedForClients();

    }

}
