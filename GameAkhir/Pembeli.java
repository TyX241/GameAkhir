package GameAkhir;

import java.util.List;
import java.util.Random;

public abstract class Pembeli {
    protected final String nama;
    protected int tingkatKesabaranDefault;
    protected int kesabaranSaatIni;
    protected static Random random = new Random();

    public Pembeli(String nama, int tingkatKesabaranDefault) {
        this.nama = nama;
        this.tingkatKesabaranDefault = tingkatKesabaranDefault;
        this.kesabaranSaatIni = tingkatKesabaranDefault; // Inisialisasi kesabaran saat ini
    }

    /**
     * Pembeli menawar harga untuk sebuah barang.
     * @param barangDitawar Barang yang sedang ditawar.
     * @param hargaJualPlayer Harga yang ditetapkan pemain untuk barang tersebut.
     * @return Harga yang ditawarkan oleh pembeli.
     */
    public abstract int tawarHarga(Barang barangDitawar, int hargaJualPlayer);

    /**
     * Pembeli memutuskan apakah akan membeli barang pada harga final yang ditawarkan pemain.
     * @param hargaFinalDariPlayer Harga terakhir yang ditawarkan oleh pemain.
     * @param hargaTawaranTerakhirPembeli Tawaran terakhir dari pembeli sendiri (sebagai referensi).
     * @return true jika pembeli setuju membeli, false jika tidak.
     */
    public abstract boolean putuskanBeli(int hargaFinalDariPlayer, int hargaTawaranTerakhirPembeli, List<Perk> perks);

    /**
     * Pembeli memilih satu barang dari daftar barang yang tersedia di kendaraan pemain.
     * @param daftarBarangTersediaDiKendaraan List barang yang ada di kendaraan.
     * @return Barang yang dipilih, atau null jika tidak ada yang menarik/tersedia.
     */
    public abstract Barang pilihBarang(List<Barang> daftarBarangTersediaDiKendaraan);

    public String getNama() {
        return nama;
    }

    public void kurangiKesabaran() {
        if (this.kesabaranSaatIni > 0) {
            this.kesabaranSaatIni--;
        }
    }

    public int getKesabaranSaatIni() {
        return kesabaranSaatIni;
    }

    public boolean masihSabar() {
        return this.kesabaranSaatIni > 0;
    }

    public void resetKesabaran() {
        this.kesabaranSaatIni = this.tingkatKesabaranDefault;
    }

    public int getTingkatKesabaranDefault() {
        return tingkatKesabaranDefault;
    }
    public static boolean peluangBertemu(List<Perk> perk){
        double buff = 0.0;
        if (perk != null) {
        for (Perk p : perk) {
            if (p instanceof PerkActive) {
                buff += p.getEfek();
            }
            double peluangDasar = 0.5;
            double peluang = peluangDasar + (1.0 - peluangDasar) * buff;
            return random.nextDouble() < peluang;
        }
        if(buff > 0.5){
            buff = 0.5;
        }
    }
        return random.nextInt(100) < 50;
    }
}