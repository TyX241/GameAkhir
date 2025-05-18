package GameAkhir;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Peta {
    private List<String> lokasiList;
    private int posisiSekarang;
    private Random rand;

    public Peta() {
        lokasiList = Arrays.asList("Rumah", "Toko", "Pasar", "Sekolah", "Kantor");
        posisiSekarang = 0; // mulai dari Rumah
        rand = new Random();
    }
    public int getPosisiSekarang() {
        return posisiSekarang;
    }
    public List<String> getLokasiList() {
        return lokasiList;
    }
    public String getLokasiSekarang() {
        return lokasiList.get(posisiSekarang);
    }

    public void pindahLokasi(int indexTujuan) {
        if (indexTujuan < 0 || indexTujuan >= lokasiList.size()) {
            System.out.println("Lokasi tidak valid.");
            return;
        }
        if(posisiSekarang == indexTujuan){
            indexTujuan += 1;
        }
        posisiSekarang = indexTujuan;
        System.out.println("Pindah ke: " + getLokasiSekarang());
        if(indexTujuan == 0){
            return;
        }
        // Cek kemungkinan bertemu pembeli (misal 80%)
       if (rand.nextDouble() < 0.8) {
        Pembeli pembeli;
        int tipe = rand.nextInt(3);
        if (tipe == 0) {
            pembeli = new PembeliTajir();
        } else if (tipe == 1) {
            pembeli = new PembeliStandard();
        } else {
            pembeli = new PembeliMiskin();
        }
        System.out.println("Kamu bertemu dengan pembeli: " + pembeli.getClass().getSimpleName());
            // Bisa tambahkan logika interaksi dengan pembeli di sini
        } else {
            System.out.println("Tidak ada pembeli di lokasi ini.");
        }
    }

    public void tampilkanLokasi() {
        int k = 0;
        System.out.println("Daftar Lokasi:");
        for (int i = 0; i < lokasiList.size(); i++) {
            
            if(lokasiList.get(i).equals(getLokasiSekarang())){
                k -= 1;
            }
            else{
                System.out.println((k + 1) + ". " + lokasiList.get(i));
            }
            k++;
        }
    }
}
