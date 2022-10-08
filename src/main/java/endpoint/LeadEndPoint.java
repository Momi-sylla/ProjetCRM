package endpoint;

import gen.GetLeadsRequest;
import gen.GetleadsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import repositories.LeadRepo;

@Endpoint
public class LeadEndPoint {
    private static final String NAMESPACE_URI= "http://www.internalLead.com/springsoap/gen";
    private LeadRepo leadRepo;

    @Autowired
    public LeadEndPoint(LeadRepo leadRepo) {
        this.leadRepo = leadRepo;
    }

    @PayloadRoot(namespace = NAMESPACE_URI,localPart = "getLeadsRequest")
    @ResponsePayload
    public GetleadsResponse getLeads(@RequestPayload GetLeadsRequest request){
        GetleadsResponse response = new GetleadsResponse();
        response.setLead(leadRepo.findLeads(request.getLowAnnualRevenue(),request.getHighAnnualRevenue(),request.getState()));
        return response;
    }
}
