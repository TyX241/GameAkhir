package GameAkhir;

public abstract class Pembeli {
    private final String nama;

    public Pembeli(String nama) {
        this.nama = nama;
    }

    public abstract int tawarHarga(int hargaAwal);
    public abstract boolean putuskanBeli();

    public String getNama() {
        return nama;
    }
}

     
