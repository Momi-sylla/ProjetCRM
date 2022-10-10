package com.CRMService.CRMService;

import crm.SalesForceCRM;
import gen.Lead;
import impl.VirtualCRMServiceImpl;
import interfaces.Proxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CRMServiceController {
    private List<Lead> leads;

    @GetMapping("/lead")
    public List<Lead> getMyLeads() throws Exception {
        leads = SalesForceCRM.getSalesForceCRM().findLeads(50000,1000000000,"");
        return leads;

     /*
          // POUR RECUPERERLES LEADS DANS TOUS LES CRM EN MEME TEMPS
        VirtualCRMServiceImpl virtualCRMService = VirtualCRMServiceImpl.getVirtualCRMServiceImpl();
        List<Lead> myLeads = new ArrayList<Lead>();
        for(Proxy proxy : virtualCRMService.getProxyList()) {
            for(Lead lead : proxy.findLeads(50000,1000000000,""))
                myLeads.add(lead);
        }
        myLeads = SalesForceCRM.getSalesForceCRM().findLeads(50000,1000000000,"");
        return myLeads;
    */
    }

}
