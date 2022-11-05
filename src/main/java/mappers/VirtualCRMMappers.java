package mappers;

import models.LeadTo;
import gen.Geographic;
import gen.Lead;
import models.GeographicPointTo;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class VirtualCRMMappers {

    public static Geographic mapGeographicPointToFromGeographic(GeographicPointTo geographicPointTo) {
        if (geographicPointTo != null) {
            Geographic geographic = new Geographic();
            geographic.setLatitude(geographicPointTo.getLatitude());
            geographic.setLongitude(geographicPointTo.getLongitude());
            return geographic;
        }
        return null;
    }

    public static GeographicPointTo mapGeographicFromGeographicPointTo(Geographic geographic) {
        if (geographic != null) {
            GeographicPointTo geographicPointTo = new GeographicPointTo(geographic.getLatitude(), geographic.getLongitude());
            return geographicPointTo;
        }
        return null;
    }

    public static LeadTo mapLeadFromLeadTo(Lead lead) {
        if (lead != null) {
            LeadTo leadTo = new LeadTo();
            leadTo.setFirstName(lead.getFirstName());
            leadTo.setLastName(lead.getLastName());
            leadTo.setAnnualRevenue(lead.getAnnualRevenue());
            leadTo.setPhone(lead.getPhone());
            leadTo.setStreet(lead.getStreet());
            leadTo.setPostalCode(lead.getPostalCode());
            leadTo.setCity(lead.getCity());
            leadTo.setCountry(lead.getCountry());
            leadTo.setGeoGraphicPointTo(mapGeographicFromGeographicPointTo(lead.getGeographic()));
            return leadTo;
        }
        return null;
    }

    public static Lead mapLeadToFromLead(LeadTo leadTo) throws URISyntaxException, IOException, InterruptedException, DatatypeConfigurationException {
        if (leadTo != null) {
            Lead lead = new Lead();
            lead.setFirstName(leadTo.getFirstName());
            lead.setLastName(leadTo.getLastName());
            lead.setAnnualRevenue(leadTo.getAnnualRevenue());
            lead.setPhone(leadTo.getPhone());
            lead.setStreet(leadTo.getStreet());
            lead.setPostalCode(leadTo.getPostalCode());
            lead.setCity(leadTo.getCity());
            lead.setCountry(leadTo.getCountry());
            lead.setCreationDate(mapDateToXMLGregorianCalendar(leadTo.getCreationDate()));
            lead.setGeographic(mapGeographicPointToFromGeographic(leadTo.getGeoGraphicPointTo()));
            return lead;
        }
        return null;
    }

    public static XMLGregorianCalendar mapDateToXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
        if(date != null) {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(date);
            XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory
                    .newInstance()
                    .newXMLGregorianCalendar(gregorianCalendar);
            return xmlGregorianCalendar;
        }
        return null;
    }
    public static Date toDate(String dateStr) throws ParseException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate dateTime = LocalDate.parse(dateStr, formatter);
        Date date = Date.from(dateTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }
}
