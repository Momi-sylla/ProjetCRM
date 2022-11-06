package factory;

import crm.InternalCRM;
import interfaces.Proxy;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.text.ParseException;

public class InternalCRMFactory implements ProxyFactory {
    @Override
    public Proxy createProxy() throws IOException, DatatypeConfigurationException, ParseException {
        return InternalCRM.getInternalCRM();
    }
}
