package fakedata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gen.Lead;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Lead>generateData() throws IOException {
        return leadsData;
    }
}
