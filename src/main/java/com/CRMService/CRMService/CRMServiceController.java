package com.CRMService.CRMService;

import crm.SalesForceCRM;
import gen.Lead;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class CRMServiceController {
    private List<Lead> leads;

    @GetMapping("/lead")
    public List<Lead> getMyLeads() throws Exception {
        leads = SalesForceCRM.getSalesForceCRM().findLeads(50000,1000000000,"");
        return leads;
    }
}
