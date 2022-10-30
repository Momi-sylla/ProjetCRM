package com.CRMService.CRMService;

import gen.Lead;
import impl.VirtualCRMServiceImpl;
import models.LeadInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@Controller
public class CRMServiceController {
    private List<Lead> leads;

    @RequestMapping(value = "/leadsByForm", method = RequestMethod.POST)
    public String postMyLeadsByForm(@ModelAttribute LeadInfo leadInfo) throws Exception {
        VirtualCRMServiceImpl virtualCRMService = VirtualCRMServiceImpl.getVirtualCRMServiceImpl();
        leads = virtualCRMService.findLeads(leadInfo.getSalaireMinimum(), leadInfo.getSalaireMaximum(), leadInfo.getEtat());

        return "redirect:/lead";
    }

    @RequestMapping(value = "/lead", method = RequestMethod.GET)
    public List<Lead> getMyLeads(Model model) throws Exception {
        model.addAttribute("leadInfo", new LeadInfo());
        model.addAttribute("customers", leads);

        return leads;
    }

    @GetMapping("/leadByDate")
    public String getMyLeadsByDate(Model model) throws Exception {
        VirtualCRMServiceImpl virtualCRMService = VirtualCRMServiceImpl.getVirtualCRMServiceImpl();
        // model.addAttribute("customers", virtualCRMService.findLeadsByDate("2000-01-01", "2020-01-01"));
        return "leadsByDate";
    }

}