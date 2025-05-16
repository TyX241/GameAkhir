package GameAkhir;

public class Barang {
    private String nama;
    private double hargaBeli;
    private double hargaJual;

    public Barang(String nama, double hargaBeli, double hargaJual) {
        this.nama = nama;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
    }

    public String getNama() {
        return nama;
    }

    public double getHargaBeli() {
        return hargaBeli;
    }

    public double getHargaJual() {
        return hargaJual;
    }
    
}
