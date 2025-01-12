package Laborator7.pb2;

public class Chitara extends InstrumentMuzical{
    private TipChitara tip_chitara;
    private int nr_corzi;

    public Chitara() {}

    public Chitara(String producator, double pret, TipChitara tip_chitara, int nr_corzi) {
        super(producator, pret);
        this.tip_chitara = tip_chitara;
        this.nr_corzi = nr_corzi;
    }

    public TipChitara getTipchitara() {
        return tip_chitara;
    }

    public void setTipchitara(TipChitara tip_chitara) {
        this.tip_chitara = tip_chitara;
    }

    public int getNr_corzi() {
        return nr_corzi;
    }

    public void setNr_corzi(int nr_corzi) {
        this.nr_corzi = nr_corzi;
    }

    @Override
    public String toString() {
        return "Chitara{" +
                "tip_chitara=" + tip_chitara +
                ", nr_corzi=" + nr_corzi +
                ", producator='" + producator + '\'' +
                ", pret=" + pret +
                '}';
    }

    @Override
    public void afisare_Detalii() {
        System.out.println(toString());;
    }
    int compare(Chitara chitara) {
        if(this.nr_corzi>chitara.getNr_corzi())
            return 1;
        return -1;
    }
}

