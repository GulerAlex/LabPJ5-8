package Laborator5.pb3;

import java.util.List;

public class Mobilier {
    private String nume;
    private List<Placa> placi;

    public Mobilier(List<Placa> placi, String nume) {
        this.placi = placi;
        this.nume = nume;
    }

    public Mobilier() {}

    public String getNume() { return nume; }

    public List<Placa> getPlaci() { return placi; }

    public void setNume(String nume) { this.nume = nume; }

    public void setPlaci(List<Placa> placi) { this.placi = placi; }

    @Override
    public String toString() {
        return "Mobilier{" +
                "nume='" + nume + '\'' +
                ", placi=" + placi +
                '}';
    }
}

