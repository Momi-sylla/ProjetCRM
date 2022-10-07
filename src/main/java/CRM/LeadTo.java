package CRM;

import geolocation.GeographicPointTo;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Calendar;

public class LeadTo {
    private String firstName;
    private String lastName;
    private double annualRevenue;
    private String phone;
    private String street;
    private String postalCode;
    private String city;
    private String country;
    private Calendar creationDate;
    private GeographicPointTo geoGraphicPointTo;
    private String company;
    private String state;
    private final String URI = "https://nominatim.openstreetmap.org/search?";

    public LeadTo() {}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getAnnualRevenue() {
        return annualRevenue;
    }

    public void setAnnualRevenue(double annualRevenue) {
        this.annualRevenue = annualRevenue;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public GeographicPointTo getGeoGraphicPointTo() throws URISyntaxException, IOException, InterruptedException, IOException, URISyntaxException {
        street = street.replace(" " , "+");
        HttpRequest getReq = (HttpRequest) HttpRequest.newBuilder()
                .uri(new URI(URI + "city=" + this.city + "&country=" + this.country + "&postalcode=" + this.postalCode + "&street=" + this.street + "&format=json&limit=1"))
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> getResponses = client.send(getReq, HttpResponse.BodyHandlers.ofString());
        JSONArray results = new JSONArray(getResponses.body());

        if (results.length() > 0) {
            JSONObject locations = (JSONObject) results.get(0);
            this.geoGraphicPointTo = new GeographicPointTo((locations.getDouble("lat")), locations.getDouble("lon"));
            return geoGraphicPointTo;
        } else {
            System.out.println("Address '" + this.street.replace("+", " ") + ", " + this.postalCode + " " + this.city + "' not found!");
            return null;
        }
    }

    public void setGeoGraphicPointTo(GeographicPointTo geoGraphicPointTO) {
        this.geoGraphicPointTo = geoGraphicPointTO;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String toString() {
        return "FirstName : " + this.firstName + ", LastName : " + this.lastName + ", Phone : " + this.phone + ", Street : " + this.street +
                ", Postal Code : " + this.postalCode + ", City : " + this.city + ", Country : " + this.country + ", CreationDate : " + creationDate +
                ", Company : " + this.company + ", State : " + this.state + "\n" +
                "GeographicPointTo : " + this.geoGraphicPointTo;
    }

}
