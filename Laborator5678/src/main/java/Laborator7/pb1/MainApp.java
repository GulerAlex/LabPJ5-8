package Laborator7.pb1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MainApp {

    private static final String FILE_PATH = "src/main/resources/carti.json";

    public static void scriere(Map<Integer, Carte> carti) {
        try {
            ObjectMapper mapper = createObjectMapper();
            File file = new File(FILE_PATH);
            mapper.writeValue(file, carti);
        } catch (IOException e) {
            throw new RuntimeException("Eroare la salvarea in fisier", e);
        }
    }

    public static Map<Integer, Carte> citire() {
        try {
            ObjectMapper mapper = createObjectMapper();
            File file = new File(FILE_PATH);
            return mapper.readValue(file, new TypeReference<Map<Integer, Carte>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Eroare la citirea din fisier", e);
        }
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Înregistrăm JavaTimeModule pentru LocalDate
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Dezactivăm timestamp-urile
        return mapper;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<Integer, Carte> carti = citire();

        while (true) {
            afisareMeniu();
            int opt = sc.nextInt();

            switch (opt) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    afisareColectie(carti);
                    break;
                case 2:
                    stergereCarte(carti, sc);
                    break;
                case 3:
                    adaugareCarte(carti, sc);
                    break;
                case 4:
                    scriere(carti);
                    System.out.println("Carti salvate cu succes in carti.json");
                    break;
                case 5:
                    afisareCartiAutor(carti, "Yuval Noah Harari");
                    break;
                case 6:
                    afisareCartiOrdinate(carti);
                    break;
                case 7:
                    afisareCarteVeche(carti);
                    break;
                default:
                    System.out.println("Optiune invalida!");
            }
        }
    }

    private static void afisareMeniu() {
        System.out.println("\nMeniu:");
        System.out.println("0. Iesire");
        System.out.println("1. Afisare colectie");
        System.out.println("2. Stergere carte din colectie");
        System.out.println("3. Adaugare carte la colectie");
        System.out.println("4. Salvare in fisier JSON");
        System.out.println("5. Creare colectie carti autor (Yuval Noah Harari)");
        System.out.println("6. Afisare carti ordonate dupa titlu");
        System.out.println("7. Afisare cea mai veche carte");
        System.out.print("Introduceti optiunea dorita: ");
    }

    private static void afisareColectie(Map<Integer, Carte> carti) {
        carti.forEach((cheie, carte) ->
                System.out.println("Cheia: " + cheie + " Valoare: " + carte));
    }

    private static void stergereCarte(Map<Integer, Carte> carti, Scanner sc) {
        System.out.print("Introduceti a cata carte sa fie stearsa: ");
        int carteIndex = sc.nextInt();
        carti.entrySet().removeIf(entry -> entry.getKey() == carteIndex);
    }

    private static void adaugareCarte(Map<Integer, Carte> carti, Scanner sc) {
        System.out.print("Introduceti titlul cartii noi: ");
        sc.nextLine(); // consume newline left by nextInt
        String titlul = sc.nextLine();

        System.out.print("Introduceti autorul cartii noi: ");
        String autorul = sc.nextLine();

        System.out.print("Introduceti anul lansarii cartii noi: ");
        int anul = sc.nextInt();

        // Găsește cheia maximă existentă
        int newKey = carti.keySet().stream().max(Integer::compareTo).orElse(0) + 1;

        // Adaugă cartea la colecție
        carti.put(newKey, new Carte(titlul, autorul, anul));
    }


    private static void afisareCartiAutor(Map<Integer, Carte> carti, String autor) {
        Set<Carte> colectie = carti.values().stream()
                .filter(carte -> carte.autorul().equals(autor))
                .collect(Collectors.toSet());

        System.out.println("Cartile autorului " + autor + " sunt:");
        colectie.forEach(System.out::println);
    }

    private static void afisareCartiOrdinate(Map<Integer, Carte> carti) {
        carti.values().stream()
                .sorted(Comparator.comparing(Carte::titlul))
                .forEach(System.out::println);
    }

    private static void afisareCarteVeche(Map<Integer, Carte> carti) {
        Optional<Carte> carteVeche = carti.values().stream()
                .min(Comparator.comparing(Carte::anul));

        carteVeche.ifPresent(System.out::println);
    }
}
