package com.CRMService.CRMService;

import crm.InternalCRM;
import gen.GetLeadsByDateRequest;
import gen.GetLeadsRequest;
import gen.GetleadsByDateResponse;
import gen.GetleadsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;

@Endpoint
public class LeadEndPoint {
    private static final String NAMESPACE_URI= "http://www.internalLead.com/springsoap/gen";
    public InternalCRM internalCRM;

    @Autowired
    public LeadEndPoint() throws IOException, DatatypeConfigurationException {
        this.internalCRM = InternalCRM.getInternalCRM();
    }

    @PayloadRoot(namespace = NAMESPACE_URI,localPart = "getLeadsRequest")
    @ResponsePayload
    public GetleadsResponse getLeads(@RequestPayload GetLeadsRequest request){
        GetleadsResponse response = new GetleadsResponse();
        response.setLead(this.internalCRM.getLeadsInFakeData(request.getLowAnnualRevenue(),request.getHighAnnualRevenue(),request.getState()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI,localPart = "getLeadsByDateRequest")
    @ResponsePayload
    public GetleadsByDateResponse getleadsByDateResponse(@RequestPayload GetLeadsByDateRequest request){
        GetleadsByDateResponse response = new GetleadsByDateResponse();
        response.setLead(this.internalCRM.getLeadsByDateInFakeData(request.getStartDate().toGregorianCalendar(), request.getEndDate().toGregorianCalendar()));
        return response;
    }
}
