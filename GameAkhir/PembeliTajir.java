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
        // Cenderung menawar dekat atau sama dengan harga jual player.
        double faktorPengali;
        if (random.nextDouble() < 0.7) { // 70% kemungkinan menawar harga jual player atau sedikit di bawah
            faktorPengali = 0.90 + (random.nextDouble() * 0.10); // Antara 0.90 dan 1.00
        } else { // 30% kemungkinan menawar sedikit lebih rendah untuk formalitas
            faktorPengali = 0.85 + (random.nextDouble() * 0.10); // Antara 0.85 dan 0.95
        }
        int tawaran = (int) (hargaJualPlayer * faktorPengali);

        if (random.nextDouble() < 0.05) { // 5% kemungkinan menawar lebih
             tawaran = (int) (hargaJualPlayer * (1.0 + (random.nextDouble() * 0.05))); // 100% - 105%
        }

        double hargaBeliBarang = barangDitawar.getHargaBeli();
        int batasBawahTawaran = (int) (hargaBeliBarang * 1.30); // Minimal 30% di atas harga beli
        tawaran = Math.max(tawaran, batasBawahTawaran);

        return Math.max(1, tawaran); // Minimal 1
    }

    @Override
    public boolean putuskanBeli(int hargaFinalDariPlayer, int hargaTawaranTerakhirPembeli, List<Perk> perks) {
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
            return random.nextDouble() < 0.98 + buff; // Hampir pasti beli (98% peluang)
        } else if (hargaFinalDariPlayer <= hargaTawaranTerakhirPembeli * 1.10) { // Sedikit di atas tawarannya (max 10% di atas)
            return random.nextDouble() < 0.85 + buff; // Masih sangat mungkin beli (85% peluang)
        } else if (hargaFinalDariPlayer <= hargaTawaranTerakhirPembeli * 1.20) { // Agak jauh (max 20% di atas)
            return random.nextDouble() < 0.50 + buff; // Peluang 50/50
        }
        // Jika harga yang ditawarkan pemain jauh lebih tinggi dari tawaran terakhir pembeli tajir
        return random.nextDouble() < 0.20; // Peluang kecil untuk beli (20%)
    }
}