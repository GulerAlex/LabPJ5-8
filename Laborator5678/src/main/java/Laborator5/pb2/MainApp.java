package Laborator5.pb2;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainApp {
    public static void scriere(List<PerecheNumere>numere){
        try{
            ObjectMapper mapper = new ObjectMapper();
            File file=new File("src/main/resources/numere.json");
            mapper.writeValue(file,numere);
        }
        catch (StreamWriteException e) {
            throw new RuntimeException(e);
        }
        catch (DatabindException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<PerecheNumere> citire(){
        try{
            ObjectMapper mapper = new ObjectMapper();
            File file=new File("src/main/resources/numere.json");
            return mapper.readValue(file,new TypeReference<List<PerecheNumere>>(){});
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        List<PerecheNumere> numere=citire();
        numere.add(new PerecheNumere(5,8));
        scriere(numere);

        System.out.println(numere);
    }
}