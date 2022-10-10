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
        this.proxyList = new ArrayList<Proxy>();
    }

    public static VirtualCRMServiceImpl getVirtualCRMServiceImpl() {
        if (VirtualCRMServiceImpl.instance == null) {
            VirtualCRMServiceImpl.instance = new VirtualCRMServiceImpl();
        }
        return VirtualCRMServiceImpl.instance;
    }

    public void addProxy (Proxy proxy) {
        this.proxyList.add(proxy);
    }

    public void deleteProxy(Proxy proxy) {
        this.proxyList.remove(proxy);
    }

    @Override
    public List<Lead> findLeads(double lowAnnualRevenue, double highAnnualRevenue, String state) throws Exception {
        return null;
    }

    @Override
    public List<Lead> findLeadsByDate(Calendar StartDate, Calendar endDate) {
        return null;
    }
}
