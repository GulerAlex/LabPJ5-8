package Laborator6;

import Laborator5.pb3.Mobilier;
import Laborator5.pb3.Placa;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class MainApp {

    public static void scriere(List<Angajat> angajati) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // Înregistrăm JavaTimeModule pentru LocalDate
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Dezactivăm timestamp-urile
            File file = new File("src/main/resources/angajati.json");
            mapper.writeValue(file, angajati);
        } catch (StreamWriteException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Angajat> citire() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // Înregistrăm JavaTimeModule pentru LocalDate
            File file = new File("src/main/resources/angajati.json");
            return mapper.readValue(file, new TypeReference<List<Angajat>>() {
            });
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
        Scanner sc = new Scanner(System.in);
        List<Angajat> angajati;
        angajati = citire();
        //scriere(angajati);
        System.out.println("Meniul este de forma:");
        System.out.println("0. Ieșire");
        System.out.println("1. Afișarea listei de angajați");
        System.out.println("2. Afișarea angajaților cu salariul peste 2500 RON");
        System.out.println("3. Crearea unei liste cu angajații din aprilie anul trecut, cu funcție de conducere");
        System.out.println("4. Afișarea angajaților fără funcție de conducere, în ordine descrescătoare a salariilor");
        System.out.println("5. Lista de nume ale angajaților în majuscule");
        System.out.println("6. Afișarea salariilor mai mici de 3000 RON");
        System.out.println("7. Afișarea datelor primului angajat al firmei");
        System.out.println("8. Statistici despre salariul angajaților");
        System.out.println("9. Verificare existență angajat „Ion”");
        System.out.println("10. Numărul angajaților angajați vara anului precedent");
        int anul_precedent = LocalDate.now().getYear() - 1;
        do {

            System.out.print("Introduceti optiunea: ");
            int opt = sc.nextInt();
            sc.nextLine();
            switch (opt) {
                case 0:
                    exit(0);
                    break;
                case 1:
                    angajati.forEach(System.out::println);
                    break;
                case 2:
                    angajati.stream().filter((a) -> a.getSalar() > 2500).forEach(System.out::println);
                    break;
                case 3:
                    List<Angajat> angajati_sefi=
                            angajati.
                                    stream().
                                    filter((a)->(a.getPost().equals("Sef")||a.getPost().equals("Director"))
                                            &&a.getData_angajarii().getYear()==anul_precedent &&a.getData_angajarii().getMonthValue()==4).
                                    collect(Collectors.toList());
                    angajati_sefi.forEach(System.out::println);
                    break;
                case 4:
                    angajati.
                            stream().
                            filter((a) ->
                                    !(a.getPost().equals("Sef") || a.getPost().equals("Director"))).
                            sorted((a, b) -> a.compareTo(b)).forEach(System.out::println);
                    break;
                case 5:
                    List<String> angajati_majuscule=angajati.stream().
                            map(Angajat::getNume).collect(Collectors.toList());
                    for(String s:angajati_majuscule){
                        System.out.println(s.toUpperCase());
                    }
                    break;
                case 6:
                    angajati.stream().map(Angajat::getSalar).filter(salar -> salar<3000).
                            forEach(System.out::println);
                    break;
                case 7:
                    Optional<Angajat> angajat=angajati.stream().
                            min(Comparator.comparing(Angajat::getData_angajarii));
                    if(angajat.isPresent())
                        System.out.println("Primul angajat este: "+angajat.get());
                    else
                        System.out.println("Firma nu are angajati");
                    break;
                case 8:
                    DoubleSummaryStatistics statistics=angajati.stream().
                            collect(Collectors.summarizingDouble(Angajat::getSalar));
                    System.out.println("Salariul mediu: " + statistics.getAverage());
                    System.out.println("Salariul maxim: " + statistics.getMax());
                    System.out.println("Salariul minim: " + statistics.getMin());
                    break;
                case 9:
                    Optional<Angajat>angajatIon=angajati.stream().
                            filter((a)->a.getNume().contains("Ion")).findAny();
                    angajatIon.ifPresentOrElse(a -> System.out.println("Firma are cel putin un angajat Ion"),
                            ()-> System.out.println("Firma nu are niciun Ion angajat"));
                    break;
                case 10:
                    System.out.println(
                            angajati.stream().
                                    filter((a)->(a.getData_angajarii().getMonthValue()>=6&&
                                            a.getData_angajarii().getMonthValue()<=8)&&
                                            anul_precedent==a.getData_angajarii().getYear()).count()
                    );
                    break;
                default:
                    System.out.print("Optiune invalida!");
                    break;
            }
        }while(true);
    }
}