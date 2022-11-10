package fakedata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gen.Lead;
import mappers.VirtualCRMMappers;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public XMLGregorianCalendar generateRandomDate() throws DatatypeConfigurationException, ParseException {
        Random random = new Random();
        String begin = "2022-11-08";
        String end = "2022-11-09";
        Date d1 = VirtualCRMMappers.mapStringToDate(begin);
        Date d2 = VirtualCRMMappers.mapStringToDate(end);
        long longdate = d1.getTime() + Math.round(Math.random() * (d2.getTime() - d1.getTime()));
        Date randomDate = new Date(longdate);
        return  VirtualCRMMappers.mapDateToXMLGregorianCalendar(randomDate);
    }

    public void setDatasDate() throws DatatypeConfigurationException, ParseException {
        /*Iterator iterator = leadsData.listIterator();
        while(iterator.hasNext()){
            Lead l = (Lead) iterator.next();
            l.setCreationDate(generateRandomDate());
        }*/
        for(int i=0;i<leadsData.size()-1;i++){
            leadsData.get(i).setCreationDate(generateRandomDate());
        }
        int lastCLient = leadsData.size()-1;
        long todayMillis = System.currentTimeMillis();
        long dayToMillis = 86400000;
        Date today = new Date(todayMillis-dayToMillis);
        leadsData.get(lastCLient).setCreationDate(VirtualCRMMappers.mapDateToXMLGregorianCalendar(today));
    }

    public List<Lead>generateData() throws IOException, DatatypeConfigurationException, ParseException {
        setDatasDate();
        return leadsData;
    }

}
