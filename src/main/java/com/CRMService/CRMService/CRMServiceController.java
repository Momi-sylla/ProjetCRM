package com.CRMService.CRMService;

import gen.Lead;
import impl.VirtualCRMServiceImpl;
import mappers.VirtualCRMMappers;
import models.LeadInfo;
import models.RSSFeed;
import org.jdom2.Document;
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
    private List<Lead> leadsByDate = new ArrayList<>();

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
    public String getMyLeads(Model model) throws Exception {
        model.addAttribute("customers", leads);
        model.addAttribute("nbCustomers", leads.size());
        return "lead";
    }

    @RequestMapping(value = "/leadsByFormWithDate", method = RequestMethod.POST)
    public String postMyLeadsByFormWithDate(@ModelAttribute LeadInfo leadInfo) throws Exception {
        VirtualCRMServiceImpl virtualCRMService = VirtualCRMServiceImpl.getVirtualCRMServiceImpl();
        Calendar dateStart = VirtualCRMMappers.mapDateToXMLGregorianCalendar(VirtualCRMMappers.mapStringToDate(leadInfo.getDateStart())).toGregorianCalendar();
        Calendar dateEnd = VirtualCRMMappers.mapDateToXMLGregorianCalendar(VirtualCRMMappers.mapStringToDate(leadInfo.getDateEnd())).toGregorianCalendar();
        leadsByDate = virtualCRMService.findLeadsByDate(dateStart, dateEnd);
        return "redirect:/leadsByDate";
    }

    @GetMapping("/leadsByDate")
    public String getMyLeadsByDate(Model model) throws Exception {
        model.addAttribute("nbCustomers", leadsByDate.size());
        model.addAttribute("customers", leadsByDate);
        return "leadsByDate";
    }

    @GetMapping("/rss")
    public String getLastPotentialClients(Model model) throws Exception {
        RSSFeed rssFeed = new RSSFeed();
        Document doc =rssFeed.createFeedForClients();
        rssFeed.getPotentialClients(doc);
        model.addAttribute("potentialClientXml", rssFeed.toString(doc));
        model.addAttribute("potentialClientText", rssFeed.getPotentialClients(doc));
        return "rss";
    }

}