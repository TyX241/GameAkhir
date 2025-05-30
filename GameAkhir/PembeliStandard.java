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
        // Menawar di bawah harga jual player, tapi di atas harga beli barang
        // Target sekitar 70% - 90% dari harga jual player
        double faktorPengali = 0.70 + (random.nextDouble() * 0.20); // Antara 0.70 dan 0.90
        int tawaran = (int) (hargaJualPlayer * faktorPengali);

        // Pastikan tawaran lebih tinggi dari harga beli barang + margin wajar
        double hargaBeliBarang = barangDitawar.getHargaBeli();
        int batasBawahTawaran = (int) (hargaBeliBarang * 1.15); // Minimal 15% di atas harga beli
        tawaran = Math.max(tawaran, batasBawahTawaran);

        // Jangan sampai menawar lebih tinggi dari harga jual player (kecuali sangat jarang)
        if (tawaran > hargaJualPlayer && random.nextDouble() > 0.1) { // 90% tidak akan nawar lebih
            tawaran = (int) (hargaJualPlayer * (0.65 + random.nextDouble() * 0.2)); // 65-85% dari harga player
        }
        return Math.max(1, tawaran); // Minimal 1
    }

    @Override
    public boolean putuskanBeli(int hargaFinalDariPlayer, int hargaTawaranTerakhirPembeli) {
        // Peluang beli bagus jika harga player dekat atau di bawah tawaran terakhirnya.
        if (hargaFinalDariPlayer <= hargaTawaranTerakhirPembeli * 1.10) { // Max 10% di atas tawarannya
            return random.nextDouble() < 0.85; // 85% peluang beli
        } else if (hargaFinalDariPlayer <= hargaTawaranTerakhirPembeli * 1.25) { // Max 25% di atas tawarannya
            return random.nextDouble() < 0.50; // 50% peluang beli
        }
        return random.nextDouble() < 0.15; // 15% peluang jika harga jauh lebih tinggi
    }
}