package Laborator5.pb2;

public class PerecheNumere {
    private int a, b;

    public PerecheNumere(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public PerecheNumere() {}

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return a + " " + b + "\n";
    }

    public boolean consecutive() {
        int x = 0, y = 1, z = 1;
        while (y < a) {
            x = y;
            y = z;
            z = x + y;
        }
        return (y == a && z == b);
    }

    public int cmmdc() {
        int x = a, y = b;
        while (x != y) {
            if (x > y) {
                x -= y;
            } else {
                y -= x;
            }
        }
        return x;
    }

    public boolean sumaCifre() {
        int sumaA = sumaCifreNumar(a);
        int sumaB = sumaCifreNumar(b);
        return sumaA == sumaB;
    }

    private int sumaCifreNumar(int numar) {
        int suma = 0;
        while (numar != 0) {
            suma += numar % 10;
            numar /= 10;
        }
        return suma;
    }

    public boolean paritate() {
        int pareA = numarCifrePare(a);
        int pareB = numarCifrePare(b);
        return pareA == pareB;
    }

    private int numarCifrePare(int numar) {
        int count = 0;
        while (numar != 0) {
            if ((numar % 10) % 2 == 0) {
                count++;
            }
            numar /= 10;
        }
        return count;
    }
}

