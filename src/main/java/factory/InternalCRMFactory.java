package factory;

import crm.InternalCRM;
import interfaces.Proxy;
import factory.ProxyFactory;

import java.io.IOException;

public class InternalCRMFactory implements ProxyFactory {
    @Override
    public Proxy createProxy() throws IOException {
        return InternalCRM.getInternalCRM();
    }
}
