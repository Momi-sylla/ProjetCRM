package com.CRMService.CRMService;

import gen.Lead;
import impl.VirtualCRMServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CRMServiceController {
    private List<Lead> leads;

    @GetMapping("/lead")
    public List<Lead> getMyLeads() throws Exception {
        VirtualCRMServiceImpl virtualCRMService = VirtualCRMServiceImpl.getVirtualCRMServiceImpl();
        return virtualCRMService.findLeads(50000, 1000000000, null);
    }

}