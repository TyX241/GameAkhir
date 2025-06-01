package GameAkhir;

import java.util.List;

public class PembeliStandard extends Pembeli {

    public PembeliStandard(String nama) {
        super(nama, 3); // Tingkat kesabaran sedang (misal: 3 kali negosiasi)
    }

    @Override
    public Barang pilihBarang(List<Barang> daftarBarangTersediaDiKendaraan) {
        if (daftarBarangTersediaDiKendaraan == null || daftarBarangTersediaDiKendaraan.isEmpty()) {
            return null;
        }
        // Memilih secara acak
        return daftarBarangTersediaDiKendaraan.get(random.nextInt(daftarBarangTersediaDiKendaraan.size()));
    }

    @Override
public int tawarHarga(Barang barangDitawar, int hargaJualPlayer) {
    // Dasar tawaran: harga beli asli barang (dari Supplier)
    double hargaBeliDasar = barangDitawar.getHargaBeli();

    // Target tawaran: 120% - 150% dari harga beli barang
    double faktorPengali = 1.20 + (random.nextDouble() * 0.30); // Antara 1.20 dan 1.50
    int tawaran = (int) (hargaBeliDasar * faktorPengali);

    // Batas atas: tidak lebih dari 90% harga jual player
    int batasAtasTawaran = (int) (hargaJualPlayer * 0.90);
    tawaran = Math.min(tawaran, batasAtasTawaran);

    // Minimal tawaran: 15% di atas harga beli
    int batasBawahTawaran = (int) (hargaBeliDasar * 1.15);
    tawaran = Math.max(tawaran, batasBawahTawaran);

    // Jangan sampai menawar lebih tinggi dari harga jual player (kecuali sangat jarang)
    if (tawaran > hargaJualPlayer && random.nextDouble() > 0.1) { // 90% tidak akan nawar lebih
        tawaran = (int) (hargaJualPlayer * (0.65 + random.nextDouble() * 0.2)); // 65-85% dari harga player
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
        // Peluang beli bagus jika harga player dekat atau di bawah tawaran terakhirnya.
        if (hargaFinalDariPlayer <= hargaTawaranTerakhirPembeli * 1.10) { // Max 10% di atas tawarannya
            return random.nextDouble() < 0.85 + buff + hipno; // 85% peluang beli
        } else if (hargaFinalDariPlayer <= hargaTawaranTerakhirPembeli * 1.25) { // Max 25% di atas tawarannya
            return random.nextDouble() < 0.50 + buff + hipno; // 50% peluang beli
        }
        return random.nextDouble() < 0.001 + buff + hipno; // 15% peluang jika harga jauh lebih tinggi
    }
}