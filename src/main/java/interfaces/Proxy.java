package interfaces;

import gen.Lead;

import java.util.Calendar;
import java.util.List;

public interface Proxy {
    public List<Lead> getLeads(double lowAnnualRevenue, double highANnualRevenue, String state) throws Exception;
    public List<Lead> getLeadsByDate(Calendar StartDate, Calendar endDate);
}
