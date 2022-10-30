package interfaces;

import gen.Lead;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

public interface Proxy {
    public List<Lead> getLeads(double lowAnnualRevenue, double highANnualRevenue, String state) throws Exception;
    public List<Lead> getLeadsByDate(Calendar StartDate, Calendar endDate) throws IOException, InterruptedException, URISyntaxException, DatatypeConfigurationException, ParseException;
}
