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
    private List<Proxy> proxyList;

    private VirtualCRMServiceImpl() throws IOException, URISyntaxException, InterruptedException {
        this.proxyFactoryList = new Hashtable<String, ProxyFactory>();
        this.proxyList = new ArrayList<>();
        this.addProxy(this.createProxy("InternalCRM"));
        this.addProxy(this.createProxy("SalesForceCRM"));
    }

    public Proxy createProxy(String proxyName) throws IOException, URISyntaxException, InterruptedException {
        switch (proxyName) {
            case "SalesForceCRM":
                this.proxyFactoryList.put(proxyName, new SalesForceCRMFactory());
                break;
            case "InternalCRM":
                this.proxyFactoryList.put(proxyName, new InternalCRMFactory());
                break;
            default:
                this.proxyFactoryList.put(proxyName, null);
        }
        return this.proxyFactoryList.get(proxyName).createProxy();
    }

    public static VirtualCRMServiceImpl getVirtualCRMServiceImpl() throws IOException, URISyntaxException, InterruptedException {
        if (VirtualCRMServiceImpl.instance == null) {
            VirtualCRMServiceImpl.instance = new VirtualCRMServiceImpl();
        }
        return VirtualCRMServiceImpl.instance;
    }

    @Override
    public List<Lead> findLeads(double lowAnnualRevenue, double highAnnualRevenue, String state) throws Exception {
        ArrayList<Lead> myLeads = new ArrayList<>();

        // POUR RECUPERER LES LEADS DANS TOUS LES CRM EN MEME TEMPS
        for (Proxy proxy: this.proxyList) {
            for(Lead lead : proxy.getLeads(lowAnnualRevenue, highAnnualRevenue, state)) {
                myLeads.add(lead);
            }
        }

        return myLeads;
    }

    @Override
    public List<Lead> findLeadsByDate(Calendar StartDate, Calendar endDate) {
        return null;
    }

    public void addProxy (Proxy proxy) {
        this.proxyList.add(proxy);
    }

    public void deleteProxy(Proxy proxy) {
        this.proxyList.remove(proxy);
    }

    public Hashtable<String, ProxyFactory> getProxyFactoryList() {
        return proxyFactoryList;
    }

    public void setProxyFactoryList(Hashtable<String, ProxyFactory> proxyFactoryList) {
        this.proxyFactoryList = proxyFactoryList;
    }

    public List<Proxy> getProxyList() {
        return proxyList;
    }

    public void setProxyList(List<Proxy> proxyList) {
        this.proxyList = proxyList;
    }

    public static VirtualCRMServiceImpl getInstance() {
        return instance;
    }

    public static void setInstance(VirtualCRMServiceImpl instance) {
        VirtualCRMServiceImpl.instance = instance;
    }

}
