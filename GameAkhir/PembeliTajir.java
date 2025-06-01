package GameAkhir;

import java.util.List;

public class PembeliTajir extends Pembeli {

    public PembeliTajir(String nama) {
        super(nama, 4); // Tingkat kesabaran tinggi (misal: 4 kali negosiasi)
    }

    @Override
    public Barang pilihBarang(List<Barang> daftarBarangTersediaDiKendaraan) {
        if (daftarBarangTersediaDiKendaraan == null || daftarBarangTersediaDiKendaraan.isEmpty()) {
            return null;
        }
        // Tidak terlalu peduli harga, bisa memilih acak atau barang yang "terlihat mahal"
        // Untuk sederhana, kita buat acak saja.
        return daftarBarangTersediaDiKendaraan.get(random.nextInt(daftarBarangTersediaDiKendaraan.size()));
    }

    @Override
public int tawarHarga(Barang barangDitawar, int hargaJualPlayer) {
    // Dasar tawaran: harga beli asli barang (dari Supplier)
    double hargaBeliDasar = barangDitawar.getHargaBeli();

    // Cenderung menawar tinggi: 140% - 180% dari harga beli barang
    double faktorPengali = 1.40 + (random.nextDouble() * 0.40); // Antara 1.40 dan 1.80
    int tawaran = (int) (hargaBeliDasar * faktorPengali);

    // Batas atas: tidak lebih dari 100% harga jual player
    int batasAtasTawaran = (int) (hargaJualPlayer * 1.00);
    tawaran = Math.min(tawaran, batasAtasTawaran);

    // Minimal tawaran: 30% di atas harga beli
    int batasBawahTawaran = (int) (hargaBeliDasar * 1.30);
    tawaran = Math.max(tawaran, batasBawahTawaran);

    // 5% kemungkinan menawar lebih tinggi dari harga jual player
    if (tawaran > hargaJualPlayer && random.nextDouble() < 0.05) {
        tawaran = (int) (hargaJualPlayer * (1.0 + (random.nextDouble() * 0.05))); // 100% - 105%
    }

    return Math.max(1, tawaran); // Minimal 1
}

    @Override
    public boolean putuskanBeli(int hargaFinalDariPlayer, int hargaTawaranTerakhirPembeli, List<Perk> perks, double hipno) {
        double buff = 0;
        if (perks != null) {
            for (Perk p : perks) {
                if (p instanceof PerkElegan) {
                    buff += p.getEfek();
                }
            }
            if(buff > 0.5){
                buff = 0.5; // Batasi buff maksimum
            }
        }
        // Pembeli Tajir tidak terlalu sensitif harga jika sudah dekat dengan tawarannya (yang biasanya sudah tinggi).
        // Mereka lebih melihat apakah harga final dari player itu "masuk akal" relatif terhadap apa yang mereka anggap fair value (tawaran terakhir mereka).
        if (hargaFinalDariPlayer <= hargaTawaranTerakhirPembeli * 1.02) { // Sangat dekat atau sama dengan tawarannya (max 2% di atas)
            return random.nextDouble() < 0.98 + buff + hipno; // Hampir pasti beli (98% peluang)
        } else if (hargaFinalDariPlayer <= hargaTawaranTerakhirPembeli * 1.10) { // Sedikit di atas tawarannya (max 10% di atas)
            return random.nextDouble() < 0.85 + buff + hipno; // Masih sangat mungkin beli (85% peluang)
        } else if (hargaFinalDariPlayer <= hargaTawaranTerakhirPembeli * 1.20) { // Agak jauh (max 20% di atas)
            return random.nextDouble() < 0.50 + buff + hipno; // Peluang 50/50
        }
        // Jika harga yang ditawarkan pemain jauh lebih tinggi dari tawaran terakhir pembeli tajir
        return random.nextDouble() < 0.005 + buff + hipno; // Peluang kecil untuk beli (20%)
    }
}