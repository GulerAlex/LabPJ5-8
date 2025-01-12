package Laborator5.pb3;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class Placa {
    private String descriere;
    private int lungime;
    private int latime;
    private Orientare orientare;
    private boolean[] canturi;
    @JsonProperty("nr_bucati")
    private int nr_bucati;

    public Placa(String descriere, int lungime, int latime, String orientare, boolean[] canturi, int nr_bucati) {
        this.descriere = descriere;
        this.lungime = lungime;
        this.latime = latime;
        this.orientare = Orientare.valueOf(orientare);
        this.canturi = Arrays.copyOf(canturi,canturi.length);
        this.nr_bucati = nr_bucati;
    }

    public Placa() {}

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getLungime() {
        return lungime;
    }

    public void setLungime(int lungime) {
        this.lungime = lungime;
    }

    public int getLatime() {
        return latime;
    }

    public void setLatime(int latime) {
        this.latime = latime;
    }

    public Orientare getOrientare() {
        return orientare;
    }

    public void setOrientare(Orientare orientare) {
        this.orientare = orientare;
    }

    public boolean[] getCanturi() {
        return canturi;
    }

    public void setCanturi(boolean[] canturi) {
        this.canturi = Arrays.copyOf(canturi, canturi.length);
    }

    public int getNrBucati() {
        return nr_bucati;
    }

    public void setNrBucati(int nr_bucati) {
        this.nr_bucati = nr_bucati;
    }

    public int getArieTotala() {
        return lungime * latime * nr_bucati;
    }

    @Override
    public String toString() {
        return "Placa{" +
                "descriere='" + descriere + '\'' +
                ", lungime=" + lungime +
                " mm, latime=" + latime +
                " mm, orientare=" + orientare +
                ", canturi=" + Arrays.toString(canturi) +
                ", nr_bucati=" + nr_bucati +
                '}';
    }

}