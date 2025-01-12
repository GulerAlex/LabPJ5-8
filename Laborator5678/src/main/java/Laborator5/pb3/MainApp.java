package Laborator5.pb3;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainApp {
    public static List<Mobilier> citire() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/main/resources/mobilier.json");
            return mapper.readValue(file, new TypeReference<List<Mobilier>>() {});
        } catch (StreamReadException | DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        List<Mobilier> mobiliere=citire();
        for(Mobilier mobilier:mobiliere) {
            System.out.println(mobilier.getNume());
            for(Placa placa:mobilier.getPlaci()){
                System.out.println(placa.toString());
                System.out.println("Numar coli estimativ: "+(placa.getArieTotala()/5796000 + 1));
            }
        }
    }
}
