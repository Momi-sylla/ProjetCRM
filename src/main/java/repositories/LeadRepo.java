package repositories;

import gen.Lead;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LeadRepo {
    private static final   List<Lead>  leads = new ArrayList<>();

    @PostConstruct
    public void initData(){

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



}
