package interfaces;

import gen.Lead;

import java.util.Calendar;
import java.util.List;

public interface VirtualCRMService {
    public List<Lead> findLeads(double lowAnnualRevenue, double highAnnualRevenue, String state) throws Exception;
    public List<Lead> findLeadsByDate(Calendar StartDate, Calendar endDate);
}
