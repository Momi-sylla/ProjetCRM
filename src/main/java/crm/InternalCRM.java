package crm;

import fakedata.Fakedata;
import gen.Lead;
import interfaces.Proxy;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class InternalCRM implements Proxy {
    private List<Lead> leads;
    private static InternalCRM instance = null;

    private InternalCRM() throws IOException {
        this.leads = new ArrayList<Lead>();
        this.genereDataBase();
    }

    public static InternalCRM getInternalCRM() throws IOException {
        if (InternalCRM.instance == null) {
            InternalCRM.instance = new InternalCRM();
        }
        return InternalCRM.instance;
    }

    @PostConstruct
    public void genereDataBase() throws IOException {
       this.leads = (ArrayList<Lead>) Fakedata.getFakeData().generateData();
    }

    public List<Lead> findLeads(double lowAnnualRevenue, double highANnualRevenue, String state){
        List<Lead> matchedLeads = new ArrayList<>();
       for(Lead lead : leads){
           if(lead.getAnnualRevenue()>lowAnnualRevenue || lead.getAnnualRevenue()<highANnualRevenue){
               matchedLeads.add(lead);
           }
       }
        return matchedLeads;
    }

    @Override
    public List<Lead> findLeadsByDate(Calendar StartDate, Calendar endDate) {
        return null;
    }


}
