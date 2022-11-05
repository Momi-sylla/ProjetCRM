package fakedata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gen.Lead;
import mappers.VirtualCRMMappers;
import org.jdom2.Element;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Fakedata {
    private List<Lead> leadsData;
    private static Fakedata instance = null;

    private Fakedata () throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        this.leadsData = new ArrayList<Lead>();
        this.leadsData = mapper.readValue(new File("src/main/resources/leadsFakeData.json"), new TypeReference<ArrayList<Lead>>() {});
    }

    public static Fakedata getFakeData() throws IOException {
        if (Fakedata.instance == null) {
            Fakedata.instance = new Fakedata();
        }
        return Fakedata.instance;
    }


    public XMLGregorianCalendar generateRandomDate() throws DatatypeConfigurationException {
        Random random = new Random();
        long milliseconds = -946771200000L + (Math.abs(random.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));
        return  VirtualCRMMappers.mapDateToXMLGregorianCalendar(new Date(milliseconds));
    }

    public void setDatasDate() throws DatatypeConfigurationException {
        Iterator iterator = leadsData.listIterator();
        while(iterator.hasNext()){
            Lead l = (Lead) iterator.next();
            l.setCreationDate(generateRandomDate());
        }
    }
    public List<Lead>generateData() throws IOException, DatatypeConfigurationException {
        setDatasDate();
        return leadsData;
    }
}
