package CRM;

import java.util.Calendar;
import java.util.List;

public interface VirtualCRMService {
    public List<LeadTo> findLeads(double lowAnnualRevenue, double highANnualRevenue, String state) throws Exception;
    public List<LeadTo>findLeadsByDate(Calendar StartDate, Calendar endDate);
}
