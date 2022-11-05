package com.CRMService.CRMService;

import gen.Lead;
import impl.VirtualCRMServiceImpl;
import mappers.VirtualCRMMappers;
import models.LeadInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//@RestController
@Controller
public class CRMServiceController {
    private List<Lead> leads = new ArrayList<>();

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String getMyForm(Model model) throws Exception {
        model.addAttribute("leadInfo", new LeadInfo());
        System.out.println("Form");
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
        model.addAttribute("customers", leads);
        model.addAttribute("nbCustomers", leads.size());
        return leads;
    }

    @RequestMapping(value = "/leadsByFormWithDate", method = RequestMethod.POST)
    public String postMyLeadsByFormWithDate(@ModelAttribute LeadInfo leadInfo) throws Exception {
        VirtualCRMServiceImpl virtualCRMService = VirtualCRMServiceImpl.getVirtualCRMServiceImpl();
        Calendar dateStart = VirtualCRMMappers.mapDateToXMLGregorianCalendar(VirtualCRMMappers.toDate(leadInfo.getDateStart())).toGregorianCalendar();
        Calendar dateEnd = VirtualCRMMappers.mapDateToXMLGregorianCalendar(VirtualCRMMappers.toDate(leadInfo.getDateEnd())).toGregorianCalendar();

        System.out.println("Lead Form With Date");
        leads = virtualCRMService.findLeadsByDate(dateStart, dateEnd);
        return "redirect:/leadsByDate";
    }

    @GetMapping("/leadByDate")
    public String getMyLeadsByDate(Model model) throws Exception {
        VirtualCRMServiceImpl virtualCRMService = VirtualCRMServiceImpl.getVirtualCRMServiceImpl();
        model.addAttribute("nbCustomers", leads.size());
        model.addAttribute("customers", leads);
        System.out.println("LeadPage With Date");
        return "leadsByDate";
    }

}