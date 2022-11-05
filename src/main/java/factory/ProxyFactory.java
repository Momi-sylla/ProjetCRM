package factory;

import interfaces.Proxy;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;

public interface ProxyFactory {
    public Proxy createProxy() throws IOException, URISyntaxException, InterruptedException, DatatypeConfigurationException;
}
