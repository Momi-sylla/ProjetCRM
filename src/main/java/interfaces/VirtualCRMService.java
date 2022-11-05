package interfaces;

import gen.Lead;
import org.jdom2.JDOMException;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

public interface VirtualCRMService {
    public List<Lead> findLeads(double lowAnnualRevenue, double highAnnualRevenue, String state) throws Exception;
    public List<Lead> findLeadsByDate(Calendar StartDate, Calendar endDate) throws DatatypeConfigurationException, IOException, URISyntaxException, ParseException, JDOMException, InterruptedException;
}
