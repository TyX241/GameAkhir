package GameAkhir;

import java.io.*; // Untuk File, FileOutputStream, ObjectOutputStream, dll.
import java.util.List;
import java.util.Map;

public class SaveFile {
    private static final String SAVE_FILE_NAME_TXT = "gamestate.txt"; // Nama file teks

    // Penanda untuk memisahkan data objek dalam file
    private static final String PLAYER_START_TAG = "[PLAYER_DATA_START]";
    private static final String PLAYER_END_TAG = "[PLAYER_DATA_END]";
    private static final String KENDARAAN_START_TAG = "[KENDARAAN_DATA_START]";
    private static final String KENDARAAN_END_TAG = "[KENDARAAN_DATA_END]";
    private static final String RUMAH_START_TAG = "[RUMAH_DATA_START]";
    private static final String RUMAH_END_TAG = "[RUMAH_DATA_END]";
    private static final String INVENTORY_ITEM_TAG = "INV_ITEM:"; // Untuk item di Player
    private static final String KENDARAAN_INV_BARANG_TAG = "K_INV_BARANG:"; // Untuk barang di Kendaraan
    private static final String RUMAH_STOK_BARANG_TAG = "R_STOK_BARANG:"; // Untuk stok barang di Rumah
    private static final String RUMAH_HARGA_JUAL_TAG = "R_HARGA_JUAL:"; // Untuk harga jual di Rumah


    public boolean simpanProgress(Player pemain, Kendaraan kendaraan, Rumah rumah) {
        if (pemain == null || kendaraan == null || rumah == null) {
            System.err.println("Error: Data pemain, kendaraan, atau rumah tidak boleh null saat menyimpan ke TXT.");
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE_NAME_TXT))) {
            // --- Simpan Data Player ---
            writer.write(PLAYER_START_TAG); writer.newLine();
            writer.write("nama=" + pemain.getNama()); writer.newLine();
            writer.write("saldo=" + pemain.getSaldo()); writer.newLine();
            for (Map.Entry<String, Integer> entry : pemain.getItemMap().entrySet()) {
                writer.write(INVENTORY_ITEM_TAG + entry.getKey() + "," + entry.getValue()); writer.newLine();
            }
            // Simpan perks yang dimiliki
            for (Perk perk : pemain.getSemuaPerkDimiliki()) {
                writer.write("PERK:" + perk.getClass().getSimpleName() + "," + perk.getNama() + "," + perk.getTingkatKesaktian());
                writer.newLine();
            }
            // Simpan perks aktif (slot 0 dan 1)
            for (Perk perk : pemain.getPerkAktif()) {
                writer.write("PERK_AKTIF:" + perk.getClass().getSimpleName() + "," + perk.getNama());
                writer.newLine();
            }
            // Simpan itemMap pemain
            for (Map.Entry<String, Integer> entry : pemain.getItemMap().entrySet()) { // Asumsi ada getItemMap()
                // Kita hanya simpan nama dan jumlah. Untuk load, kita perlu referensi ke Item master
                writer.write(INVENTORY_ITEM_TAG + entry.getKey() + "," + entry.getValue()); writer.newLine();
            }
            // Simpan perks jika ada (lebih kompleks, mungkin hanya nama perk dan levelnya)
            // writer.write("perks_count=" + pemain.getPerks().size()); writer.newLine();
            // for (Perk perk : pemain.getPerks()) {
            //     writer.write("perk_nama=" + perk.getNama()); writer.newLine();
            //     writer.write("perk_level=" + perk.getTingkatKesaktian()); writer.newLine(); // Asumsi ada getTingkatKesaktian()
            // }
            writer.write(PLAYER_END_TAG); writer.newLine();
            writer.newLine(); // Baris kosong sebagai pemisah

            // --- Simpan Data Kendaraan ---
            writer.write(KENDARAAN_START_TAG); writer.newLine();
            writer.write("nama=" + kendaraan.getNama()); writer.newLine();
            writer.write("kapasitas=" + kendaraan.getKapasitas()); writer.newLine();
            writer.write("level=" + kendaraan.getlevel()); writer.newLine();
            // Simpan inventori kendaraan
            for (Map.Entry<Barang, Integer> entry : kendaraan.getInventori().entrySet()) {
                Barang barang = entry.getKey();
                // Simpan nama barang, harga beli, harga jual (dari objek Barang di kendaraan), dan jumlah
                writer.write(KENDARAAN_INV_BARANG_TAG + barang.getNama() + "," +
                             barang.getHargaBeli() + "," + barang.getHargaJual() + "," +
                             entry.getValue());
                writer.newLine();
            }
            writer.write(KENDARAAN_END_TAG); writer.newLine();
            writer.newLine();

            // --- Simpan Data Rumah ---
            writer.write(RUMAH_START_TAG); writer.newLine();
            // Simpan stok barang rumah
            for (Map.Entry<Barang, Integer> entry : rumah.getStokBarangMap().entrySet()) {
                Barang barang = entry.getKey();
                // Simpan nama barang, harga beli, harga jual (dari objek Barang di rumah), dan jumlah
                writer.write(RUMAH_STOK_BARANG_TAG + barang.getNama() + "," +
                             barang.getHargaBeli() + "," + barang.getHargaJual() + "," +
                             entry.getValue());
                writer.newLine();
            }
            // Simpan harga jual custom di rumah (jika berbeda dari default barang)
            // Ini sudah implisit tersimpan di atas jika objek Barang di rumah memiliki harga jualnya sendiri.
            // Jika hargaJual di Rumah adalah Map<Barang, Double> yang terpisah dan key-nya adalah objek Barang master,
            // maka kita perlu menyimpan nama barang dan harga jualnya.
            // Untuk kesederhanaan, kita asumsikan Map<Barang, Integer> di Rumah
            // sudah cukup jika objek Barang di dalamnya memiliki harga jual yang benar.
            writer.write(RUMAH_END_TAG); writer.newLine();

            System.out.println("Progress game berhasil disimpan ke file teks: " + new File(SAVE_FILE_NAME_TXT).getAbsolutePath());
            return true;

        } catch (IOException e) {
            System.err.println("Gagal menyimpan progress game ke file teks: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Memuat progress game dari file teks.
     * @param masterDaftarBarang List semua Barang master yang ada di game (dari Supplier)
     * @param masterDaftarItem List semua Item master yang ada di game
     * @return Object[] array berisi [Player, Kendaraan, Rumah] jika berhasil, atau null.
     */
    public Object[] muatProgress(List<Barang> masterDaftarBarang, List<Item> masterDaftarItem) {
        File fileSimpanan = new File(SAVE_FILE_NAME_TXT);
        if (!fileSimpanan.exists() || !fileSimpanan.isFile()) {
            System.out.println("File simpanan teks '" + SAVE_FILE_NAME_TXT + "' tidak ditemukan.");
            return null;
        }

        Player loadedPemain = new Player(); // Buat instance baru untuk diisi
        Kendaraan loadedKendaraan = new Kendaraan();
        Rumah loadedRumah = new Rumah();

        String currentSection = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileSimpanan))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.equals(PLAYER_START_TAG)) { currentSection = "PLAYER"; continue; }
                if (line.equals(PLAYER_END_TAG)) { currentSection = null; continue; }
                if (line.equals(KENDARAAN_START_TAG)) { currentSection = "KENDARAAN"; continue; }
                if (line.equals(KENDARAAN_END_TAG)) { currentSection = null; continue; }
                if (line.equals(RUMAH_START_TAG)) { currentSection = "RUMAH"; continue; }
                if (line.equals(RUMAH_END_TAG)) { currentSection = null; continue; }

                if (currentSection != null) {
                    String[] parts = line.split("=", 2);
                    if (parts.length < 2 && !(line.startsWith(INVENTORY_ITEM_TAG) || line.startsWith(KENDARAAN_INV_BARANG_TAG) || line.startsWith(RUMAH_STOK_BARANG_TAG))) {
                        System.err.println("Format baris tidak valid (kurang dari 2 bagian setelah split '='): " + line);
                        continue; // Skip baris yang tidak valid
                    }
                    String key = "";
                    String value = "";

                    if (line.startsWith(INVENTORY_ITEM_TAG)) {
                        key = INVENTORY_ITEM_TAG;
                        value = line.substring(INVENTORY_ITEM_TAG.length());
                    } else if (line.startsWith(KENDARAAN_INV_BARANG_TAG)) {
                        key = KENDARAAN_INV_BARANG_TAG;
                        value = line.substring(KENDARAAN_INV_BARANG_TAG.length());
                    } else if (line.startsWith(RUMAH_STOK_BARANG_TAG)) {
                        key = RUMAH_STOK_BARANG_TAG;
                        value = line.substring(RUMAH_STOK_BARANG_TAG.length());
                    } else if (parts.length == 2) {
                        key = parts[0].trim();
                        value = parts[1].trim();
                    } else {
                        System.err.println("Format baris tidak dikenali: " + line);
                        continue;
                    }


                    switch (currentSection) {
                        case "PLAYER":
                            if (key.equals("nama")) loadedPemain.setNama(value);
                            else if (key.equals("saldo")) loadedPemain.setSaldo(Double.parseDouble(value));
                            else if (key.equals(INVENTORY_ITEM_TAG)) {
                                String[] itemData = value.split(",");
                                if (itemData.length == 2) {
                                    String namaItem = itemData[0].trim();
                                    int jumlahItem = Integer.parseInt(itemData[1].trim());
                                    Item itemMaster = findItemByName(masterDaftarItem, namaItem);
                                    if (itemMaster != null) {
                                        loadedPemain.tambahItem(itemMaster, jumlahItem);
                                    } else {
                                        System.err.println("Item master tidak ditemukan untuk: " + namaItem);
                                    }
                                }
                            }
                          else if (line.startsWith("PERK:")) {
                                String valuePerk = line.substring("PERK:".length());
                                String[] perkData = valuePerk.split(",");
                                if (perkData.length == 3) {
                                    String className = perkData[0].trim();
                                    String namaPerk = perkData[1].trim();
                                    int tingkat = Integer.parseInt(perkData[2].trim());
                                    Perk perk = null;
                                    if (className.equals("PerkElegan")) perk = new PerkElegan(namaPerk, tingkat);
                                    else if (className.equals("PerkCharming")) perk = new PerkCharming(namaPerk, tingkat);
                                    else if (className.equals("PerkActive")) perk = new PerkActive(namaPerk, tingkat);
                                    if (perk != null) loadedPemain.tambahPerkDimiliki(perk);
                                }
                            }
                            else if (line.startsWith("PERK_AKTIF:")) {
                                String valuePerk = line.substring("PERK_AKTIF:".length());
                                String[] perkData = valuePerk.split(",");
                                if (perkData.length == 2) {
                                    String className = perkData[0].trim();
                                    String namaPerk = perkData[1].trim();
                                    for (Perk p : loadedPemain.getSemuaPerkDimiliki()) {
                                        if (p.getNama().equals(namaPerk) && p.getClass().getSimpleName().equals(className)) {
                                            loadedPemain.setPerkAktif(p, loadedPemain.getPerkAktif().size());
                                            break;
                                        }
                                    }
                                }
                            }
                            break;
                        case "KENDARAAN":
                            if (key.equals("nama")) loadedKendaraan.setNama(value); // Perlu setter di Kendaraan
                            else if (key.equals("kapasitas")) loadedKendaraan.setKapasitas(Integer.parseInt(value)); // Perlu setter
                            else if (key.equals("level")) loadedKendaraan.setLevel(Integer.parseInt(value)); // Perlu setter
                            else if (key.equals(KENDARAAN_INV_BARANG_TAG)) {
                                String[] barangData = value.split(",");
                                if (barangData.length == 4) {
                                    String namaBarang = barangData[0].trim();
                                    double hargaBeli = Double.parseDouble(barangData[1].trim());
                                    double hargaJual = Double.parseDouble(barangData[2].trim());
                                    int jumlah = Integer.parseInt(barangData[3].trim());
                                    // Buat objek Barang baru karena harga jual bisa custom di kendaraan
                                    Barang barangDiKendaraan = new Barang(namaBarang, hargaBeli, hargaJual);
                                    loadedKendaraan.tambahBarang(barangDiKendaraan, jumlah);
                                }
                            }
                            break;
                        case "RUMAH":
                            if (key.equals(RUMAH_STOK_BARANG_TAG)) {
                                String[] barangData = value.split(",");
                                if (barangData.length == 4) {
                                    String namaBarang = barangData[0].trim();
                                    double hargaBeli = Double.parseDouble(barangData[1].trim());
                                    double hargaJual = Double.parseDouble(barangData[2].trim());
                                    int jumlah = Integer.parseInt(barangData[3].trim());
                                    // Cari barang master untuk konsistensi, atau buat baru jika harga jual di rumah bisa custom
                                    Barang barangMaster = findBarangByName(masterDaftarBarang, namaBarang);
                                    Barang barangUntukRumah;
                                    if (barangMaster != null && barangMaster.getHargaBeli() == hargaBeli && barangMaster.getHargaJual() == hargaJual) {
                                        barangUntukRumah = barangMaster;
                                    } else {
                                        // Jika harga berbeda dari master, atau tidak ada di master, buat objek baru
                                        barangUntukRumah = new Barang(namaBarang, hargaBeli, hargaJual);
                                    }
                                    loadedRumah.addBarang(barangUntukRumah, jumlah);
                                }
                            }
                            break;
                    }
                }
            }
            System.out.println("Progress game berhasil dimuat dari file teks: " + fileSimpanan.getAbsolutePath());
            // Penting: setelah load, set kendaraan ke pemain
            loadedPemain.setKendaraan(loadedKendaraan);
            return new Object[]{loadedPemain, loadedKendaraan, loadedRumah};

        } catch (IOException e) {
            System.err.println("Gagal memuat progress game dari file teks (I/O): " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Gagal memuat progress game dari file teks (Format Angka Salah): " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Helper method untuk mencari Barang master berdasarkan nama
    private Barang findBarangByName(List<Barang> masterList, String name) {
        for (Barang b : masterList) {
            if (b.getNama().equals(name)) {
                return b;
            }
        }
        return null; // Atau buat barang default jika tidak ditemukan
    }
    // Helper method untuk mencari Item master berdasarkan nama
    private Item findItemByName(List<Item> masterList, String name) {
        if (masterList == null) return null;
        for (Item item : masterList) {
            if (item.getNama().equals(name)) {
                return item;
            }
        }
        return null;
    }


    // apakahAdaFileSave() dan hapusFileSave() bisa menggunakan SAVE_FILE_NAME_TXT
    public boolean apakahAdaFileSave() {
        File fileSimpanan = new File(SAVE_FILE_NAME_TXT);
        return fileSimpanan.exists() && fileSimpanan.isFile();
    }
     public boolean hapusFileSave() {
        File fileSimpanan = new File(SAVE_FILE_NAME_TXT);
        if (fileSimpanan.exists()) {
            if (fileSimpanan.delete()) {
                System.out.println("File simpanan teks '" + SAVE_FILE_NAME_TXT + "' berhasil dihapus.");
                return true;
            } else {
                System.err.println("Gagal menghapus file simpanan teks '" + SAVE_FILE_NAME_TXT + "'.");
                return false;
            }
        }
        return true;
    }
}
