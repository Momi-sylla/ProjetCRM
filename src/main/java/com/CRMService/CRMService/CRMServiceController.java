package com.CRMService.CRMService;

import CRM.CRMService;
import CRM.LeadTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CRMServiceController {
    private List<LeadTo> leads;

    @GetMapping("/lead")
    public List<LeadTo> getMyLeads() throws Exception {
        leads = (ArrayList<LeadTo>) new CRMService().findLeads(50000,1000000000,"");
       return leads;


    }
}
