package factory;

import crm.SalesForceCRM;
import interfaces.Proxy;
import factory.ProxyFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class SalesForceCRMFactory implements ProxyFactory {
    @Override
    public Proxy createProxy() throws IOException, URISyntaxException, InterruptedException {
        return SalesForceCRM.getSalesForceCRM();
    }
}
