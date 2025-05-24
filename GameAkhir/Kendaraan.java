package GameAkhir;

import java.util.HashMap;
import java.util.Map;

public class Kendaraan {
    private String nama = "Truk";
    private int kapasitas = 5;
    private int level = 1;

    public Kendaraan(String nama, int kapasitas, int level){
        this.nama = nama;
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

    public int getJumlah(Barang barang) {
        return inventori.getOrDefault(barang, 0);
    }

    public Map<Barang, Integer> getInventori() {
        return inventori;
    }

    public void upgrade(){
        if (level < 5) {
            level++;
            kapasitas += 20;
            System.out.println("Kendaraan berhasil diupgrade ke level " + level);
        } else {
            System.out.println("Kendaraan sudah mencapai level maksimum.");
        }
    }
    public void infoKendaraan(){
        System.out.println("Kendaraan level " + level + " dengan kapasitas " + kapasitas);
    }
}
