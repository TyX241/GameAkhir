package GameAkhir;

public class Item {
    private String nama;
    private String efek;
    private double harga;
    private int durasi;

    public Item(String nama, String efek, double harga, int durasi) {
        this.nama = nama;
        this.efek = efek;
        this.harga = harga;
        this.durasi = durasi;
    }
    public Item(String nama, String efek, double harga) {
        this.nama = nama;
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEfek() {
        return efek;
    }

    public void setEfek(String efek) {
        this.efek = efek;
    }

    public double getHarga() {
        return harga;
    }

    public int getDurasi() {
        return durasi;
    }

    public void setDurasi(int durasi) {
        this.durasi = durasi;
    }

    public void aktifkan(){
        
    }
}
