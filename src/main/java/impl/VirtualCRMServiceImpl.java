package impl;

import factory.InternalCRMFactory;
import factory.ProxyFactory;
import factory.SalesForceCRMFactory;
import interfaces.Proxy;
import gen.Lead;
import interfaces.VirtualCRMService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

public class VirtualCRMServiceImpl implements VirtualCRMService {
    private Hashtable<String, ProxyFactory> proxyFactoryList;
    private static VirtualCRMServiceImpl instance = null;

    private VirtualCRMServiceImpl() {
        this.proxyFactoryList = new Hashtable<String, ProxyFactory>();
    }

    public static VirtualCRMServiceImpl getVirtualCRMServiceImpl() {
        if (VirtualCRMServiceImpl.instance == null) {
            VirtualCRMServiceImpl.instance = new VirtualCRMServiceImpl();
        }
        return VirtualCRMServiceImpl.instance;
    }

    public Proxy createProxy(String proxyName) throws IOException, URISyntaxException, InterruptedException {
        switch (proxyName) {
            case "SalesForceCRM":
                this.proxyFactoryList.put(proxyName, new SalesForceCRMFactory());
            case "InternalCRM":
                this.proxyFactoryList.put(proxyName, new InternalCRMFactory());
            default:
                this.proxyFactoryList.put(proxyName, null);
        }
        return this.proxyFactoryList.get(proxyName).createProxy();
    }

    public Hashtable<String, ProxyFactory> getProxyFactoryList() {
        return proxyFactoryList;
    }

    public void setProxyFactoryList(Hashtable<String, ProxyFactory> proxyFactoryList) {
        this.proxyFactoryList = proxyFactoryList;
    }

    /*
    public void addProxy (Proxy proxy) {
        this.proxyList.add(proxy);
    }

    public void deleteProxy(Proxy proxy) {
        this.proxyList.remove(proxy);
    }
     */

    @Override
    public List<Lead> findLeads(double lowAnnualRevenue, double highAnnualRevenue, String state) throws Exception {
        return null;
    }

    @Override
    public List<Lead> findLeadsByDate(Calendar StartDate, Calendar endDate) {
        return null;
    }
}
