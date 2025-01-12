package Laborator6;

import java.time.LocalDate;

public class Angajat {
    private String nume;
    private String post;
    private LocalDate data_angajarii;
    private double salar;
    public Angajat() {}
    public Angajat(String nume, String post, LocalDate data_angajarii, double salar) {
        this.nume = nume;
        this.post = post;
        this.data_angajarii = data_angajarii;
        this.salar = salar;
    }
    public String getNume() {
        return nume;
    }

    public String getPost() {
        return post;
    }

    public LocalDate getData_angajarii() {
        return data_angajarii;
    }

    public double getSalar() {
        return salar;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setData_angajarii(LocalDate data_angajarii) {
        this.data_angajarii = data_angajarii;
    }

    public void setSalar(double salar) {
        this.salar = salar;
    }

    @Override
    public String toString() {
        return "Angajat{" +
                "nume='" + nume +
                ", post='" + post +
                ", data_angajarii=" + data_angajarii +
                ", salar=" + salar +
                '}';
    }
    public int compareTo(Angajat o) {
        if(this.salar <=o.getSalar())
            return 1;
        return -1;
    }

}

