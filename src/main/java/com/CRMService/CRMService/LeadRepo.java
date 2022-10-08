package com.CRMService.CRMService;

import fakedata.Fakedata;
import gen.Lead;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LeadRepo {
    private final static List<Lead>  leads = new ArrayList<>();

    public LeadRepo() throws IOException {
        initData();
    }

    @PostConstruct
    public void initData() throws IOException {
       ArrayList<Lead> myleads = new Fakedata().generateData();
       for(Lead lead : myleads){
           leads.add(lead);
       }
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
