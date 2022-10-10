package impl;

import interfaces.Proxy;
import gen.Lead;
import interfaces.VirtualCRMService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class VirtualCRMServiceImpl implements VirtualCRMService {
    private List<Proxy> proxyList;
    private static VirtualCRMServiceImpl instance = null;

    private VirtualCRMServiceImpl() {
        this.proxyList = new ArrayList<>();
    }

    public static VirtualCRMServiceImpl getVirtualCRMServiceImpl() {
        if (VirtualCRMServiceImpl.instance == null) {
            VirtualCRMServiceImpl.instance = new VirtualCRMServiceImpl();
        }
        return VirtualCRMServiceImpl.instance;
    }

    @Override
    public List<Lead> findLeads(double lowAnnualRevenue, double highANnualRevenue, String state) throws Exception {
        return null;
    }

    @Override
    public List<Lead> findLeadsByDate(Calendar StartDate, Calendar endDate) {
        return null;
    }
}
