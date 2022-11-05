package factory;

import crm.InternalCRM;
import interfaces.Proxy;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;

public class InternalCRMFactory implements ProxyFactory {
    @Override
    public Proxy createProxy() throws IOException, DatatypeConfigurationException {
        return InternalCRM.getInternalCRM();
    }
}
