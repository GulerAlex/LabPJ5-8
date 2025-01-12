package Laborator7.pb2;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.exit;

enum TipChitara{
    ELECTRICA, ACUSTICA, CLASICA;
}
enum TipTobe{
    ELECTRONICE, ACUSTICE;
}
enum Tip{
    Chitara, Tobe;
}
public class MainApp {
    public static void scriere(Set<InstrumentMuzical> instrumente) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator());
            File file=new File("src/main/resources/instrumente.json");
            mapper.writeValue(file,instrumente);
        } catch (StreamWriteException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Set<InstrumentMuzical> citire() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator());
            File file =new File("src/main/resources/instrumente.json");

            return mapper.readValue(file, new TypeReference<HashSet<InstrumentMuzical>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        Set<InstrumentMuzical> instrumente = new HashSet<InstrumentMuzical>();
        Scanner scanner=new Scanner(System.in);
        instrumente.add(new Chitara("Fender",3500,TipChitara.ELECTRICA,5));
        instrumente.add(new Chitara("Harley",1800,TipChitara.ACUSTICA,4));
        instrumente.add(new Chitara("Timberline",2500,TipChitara.CLASICA,5));
        instrumente.add(new SetTobe("Yamaha",4000,TipTobe.ACUSTICE,4,7));
        instrumente.add(new SetTobe("Startone",5000,TipTobe.ELECTRONICE,3,9));
        instrumente.add(new SetTobe("Tama",2500,TipTobe.ACUSTICE,4,6));
        scriere(instrumente);
        System.out.println("\nMeniu:");
        System.out.println("1. Incarcare instrumente din instrumente.json");
        System.out.println("2. Afisare implementare utilizată pentru interfața Set de către ObjectMapper in urma citirii");
        System.out.println("3. Verificare dacă colecția Set permite sau nu duplicate");
        System.out.println("4. Să se șteargă instrumentele din Set al căror preț este mai mare de 3000 de RON");
        System.out.println("5. Să se afișeze toate datele chitărilor, utilizând Stream API şi operatorul instanceof");
        System.out.println("6. Să se afișeze toate datele tobelor, utilizând Stream API şi metoda getClass()");
        System.out.println("7. Să se afișeze datele chitării care are cele mai multe corzi");
        System.out.println("8. Să se afișeze datele tobelor acustice, ordonat după numărul de tobe din set");
        do {
            System.out.print("Alegeti optiunea: ");
            int opt= scanner.nextInt();
            scanner.nextLine();
            switch(opt){
                default:
                    System.out.println("Optiune invalida!");
                    break;
                case 0:
                    System.out.println("Lista finala: ");
                    System.out.println(instrumente);
                    exit(0);
                    break;
                case 1:
                    instrumente=citire();
                    System.out.println("Adaugare efectuata cu succes!");
                    break;
                case 2:
                    System.out.println("Set implementation class: " + instrumente.getClass().getName());
                    break;
                case 3:
                    InstrumentMuzical verif=instrumente.iterator().next(); // luam un instrument din lista
                    boolean ok=instrumente.add(verif);
                    if(ok) System.out.println("Permite duplicate");
                    else System.out.println("NU Permite duplicate");
                    break;
                case 4:
                    instrumente.removeIf((instrumentMuzical -> instrumentMuzical.getPret()>3000));
                    System.out.println(instrumente);
                    break;
                case 5:
                    instrumente.stream()
                            .filter(instr -> instr instanceof Chitara)
                            .forEach(System.out::println);
                    break;
                case 6:
                    instrumente.stream()
                            .filter(instr -> instr.getClass().equals(SetTobe.class))
                            .forEach(System.out::println);
                    break;
                case 7:
                    Optional chitara_cautata=instrumente.stream().
                            filter(c-> c instanceof Chitara).
                            map(c->(Chitara)c).
                            max(Comparator.comparing(Chitara::getPret));
                    System.out.println(chitara_cautata);
                    break;
                case 8:
                    instrumente.stream().
                            filter(t->t instanceof SetTobe && ((SetTobe) t).getTip_tobe()==TipTobe.ACUSTICE).
                            map(t->(SetTobe)t).
                            sorted((t1,t2)->Integer.compare(t1.nr_tobe,t2.nr_tobe)).
                            forEach(System.out::println);
                    break;
            }
        }while(true);
    }
}

