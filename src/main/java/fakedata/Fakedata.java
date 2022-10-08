package fakedata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gen.Lead;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Fakedata {

    public ArrayList<Lead>generateData() throws IOException {
        ArrayList<Lead>leadsData = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
      leadsData=  mapper.readValue(new File("src/main/resources/leadsFakeData.json"), new TypeReference<ArrayList<Lead>>() {
      });
    System.out.println(leadsData.get(0).getFirstName());
        return leadsData;
    }
}
