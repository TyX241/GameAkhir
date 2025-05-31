package GameAkhir;

import java.util.HashMap;
import java.util.Map;

public class Kendaraan {
    private String nama = "Truk";
    private int kapasitas = 5;
    private int level = 1;

    public Kendaraan(int kapasitas, int level){
        this.nama = "Retrofitted Gastruck";
        this.kapasitas = kapasitas;
        this.level = level;
    }
    public Kendaraan(){
    }
    public String getNama(){
        return nama;
    }

    public int getlevel(){
        return level;
    }
    public int getKapasitas() {
        return kapasitas;
    }
    public double getBiayaUpgrade(){
        double biayaUpgrade = 20000 * level;
        return biayaUpgrade;
    }
    
    private Map<Barang, Integer> inventori = new HashMap<>();

    public void tambahBarang(Barang barang, int jumlah) {
        inventori.put(barang, inventori.getOrDefault(barang, 0) + jumlah);
    }

    public void kurangiBarang(Barang barang, int jumlah) {
        int sisa = inventori.getOrDefault(barang, 0) - jumlah;
        if (sisa > 0) {
            inventori.put(barang, sisa);
        } else {
            inventori.remove(barang);
        }
    }
    public void setNama(){
        if(this.level == 2){
            this.nama = "Supercharged Gastruck";
        } else if(this.level == 3){
            this.nama = "Mobile Powerhouse";
        }
    }
    public void setNama(String nama){
        this.nama = nama;
    }
    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }
    public int getJumlah(Barang barang) {
        return inventori.getOrDefault(barang, 0);
    }
    public void setLevel(int level) {
        this.level = level;
        // Setelah level di-set (misalnya dari file save),
        // update juga nama kendaraan agar konsisten dengan level tersebut,
        // KECUALI jika Anda ingin nama dari file save (yang mungkin kustom) menimpa ini.
        // Jika nama dari file save adalah yang utama, maka setNama(String) akan dipanggil setelah ini.
        // Jika nama harus selalu ikut level, panggil setNamaByLevel() di sini.
        // Untuk load, biasanya kita set field satu per satu dari file, jadi setNamaByLevel()
        // mungkin tidak perlu dipanggil di sini jika nama juga di-load dari file.
        // Namun, jika nama TIDAK disimpan/di-load dari file, maka ini penting:
        // this.setNamaByLevel();
    }

    public Map<Barang, Integer> getInventori() {
        return inventori;
    }

    public void upgrade(){
        if (level < 5) {
            level++;
            kapasitas += 20;
            setNama();
            System.out.println("Kendaraan berhasil diupgrade ke level " + level);
        } else {
            System.out.println("Kendaraan sudah mencapai level maksimum.");
        }
    }
    public void infoKendaraan(){
        System.out.println("Kendaraan level " + level + " dengan kapasitas " + kapasitas);
    }
}
