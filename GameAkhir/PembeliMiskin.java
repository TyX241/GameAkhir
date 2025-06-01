package GameAkhir;

import java.util.List;

public class PembeliMiskin extends Pembeli {

    public PembeliMiskin(String nama) {
        super(nama, 2); // Tingkat kesabaran rendah (misal: 2 kali negosiasi)
    }

    @Override
    public Barang pilihBarang(List<Barang> daftarBarangTersediaDiKendaraan) {
        if (daftarBarangTersediaDiKendaraan == null || daftarBarangTersediaDiKendaraan.isEmpty()) {
            return null;
        }
        // Cenderung memilih barang dengan harga beli terendah (karena mereka miskin, mereka tahu harga modal)
        Barang barangPilihan = null;
        double hargaTerendah = Double.MAX_VALUE;

        for (Barang b : daftarBarangTersediaDiKendaraan) {
            if (b.getHargaBeli() < hargaTerendah) {
                hargaTerendah = b.getHargaBeli();
                barangPilihan = b;
            } else if (b.getHargaBeli() == hargaTerendah) {
                // Jika ada beberapa barang dengan harga beli sama, pilih acak di antaranya
                if (random.nextBoolean()) {
                    barangPilihan = b;
                }
            }
        }
        // Ada kemungkinan kecil (misal 20%) memilih acak meskipun ada yang murah,
        // atau jika tidak ada barang (barangPilihan masih null)
        if (barangPilihan == null || random.nextDouble() < 0.2) {
            return daftarBarangTersediaDiKendaraan.get(random.nextInt(daftarBarangTersediaDiKendaraan.size()));
        }
        return barangPilihan;
    }

    @Override
    public int tawarHarga(Barang barangDitawar, int hargaJualPlayer) {
        // Menawar sangat rendah, sedikit di atas harga beli barang, atau persentase rendah dari harga jual player
        double hargaBeliBarang = barangDitawar.getHargaBeli();
        // Tawaran antara 105% hingga 120% dari harga beli barang
        double faktorPengali = 1.05 + (random.nextDouble() * 0.15); // Antara 1.05 dan 1.20
        int tawaran = (int) (hargaBeliBarang * faktorPengali);

        // Pastikan tawaran tidak lebih tinggi dari 60% harga jual player (batas atas)
        int batasAtasTawaran = (int) (hargaJualPlayer * 0.60);
        tawaran = Math.min(tawaran, batasAtasTawaran);
        
        // Pastikan tawaran minimal harga beli + sedikit margin
        tawaran = Math.max(tawaran, (int) (hargaBeliBarang * 1.02)); // Minimal 2% di atas harga beli

        // Jangan sampai menawar lebih tinggi dari harga jual player (kecuali sangat jarang)
        if (tawaran > hargaJualPlayer && random.nextDouble() > 0.05) { // 95% tidak akan nawar lebih
            tawaran = (int) (hargaJualPlayer * (0.5 + random.nextDouble() * 0.1)); // 50-60% dari harga player
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
        // Sangat sensitif harga. Cenderung beli jika harga player SAMA atau sedikit di atas tawaran terakhirnya.
        if (hargaFinalDariPlayer <= hargaTawaranTerakhirPembeli * 1.05) { // Max 5% di atas tawarannya
            return random.nextDouble() < 0.75 + buff + hipno; // 75% peluang beli
        } else if (hargaFinalDariPlayer <= hargaTawaranTerakhirPembeli * 1.15) { // Max 15% di atas tawarannya
            return random.nextDouble() < 0.30 + buff + hipno; // 30% peluang beli
        }
        return random.nextDouble() < 0.0005 + buff + hipno; // 5% peluang jika harga jauh lebih tinggi
    }
}