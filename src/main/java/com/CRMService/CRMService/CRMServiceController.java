package com.CRMService.CRMService;

import gen.Lead;
import impl.VirtualCRMServiceImpl;
import models.LeadInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@RestController
@Controller
public class CRMServiceController {
    private List<Lead> leads = new ArrayList<>();

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String getMyForm(Model model) throws Exception {
        model.addAttribute("leadInfo", new LeadInfo());
        return "form";
    }

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
        model.addAttribute("nbCustomers", leads.size());
        return leads;
    }

    @GetMapping("/leadByDate")
    public String getMyLeadsByDate(Model model) throws Exception {
        VirtualCRMServiceImpl virtualCRMService = VirtualCRMServiceImpl.getVirtualCRMServiceImpl();
        // leads = virtualCRMService.findLeadsByDate("2000-01-01", "2020-01-01");
        model.addAttribute("nbCustomers", leads.size());
        // model.addAttribute("customers", leads);
        return "leadsByDate";
    }

}