package GameAkhir;

import java.io.*;
import java.util.List;
import java.util.Map;

public class SaveFile {
    private static final String SAVE_FILE_NAME_TXT = "gamestate.txt";

    private static final String SECTION_PLAYER = "[PLAYER]";
    private static final String SECTION_KENDARAAN = "[KENDARAAN]";
    private static final String SECTION_RUMAH = "[RUMAH]";
    private static final String END_SECTION = "[END_SECTION]";

    private static final String PLAYER_ITEM_PREFIX = "player_item:";
    private static final String PLAYER_PERK_DIMILIKI_PREFIX = "perk_dimiliki:"; // Diubah dari "PERK:"
    private static final String PLAYER_PERK_AKTIF_PREFIX = "perk_aktif:";   // Diubah dari "PERK_AKTIF:"
    private static final String KENDARAAN_INV_PREFIX = "kendaraan_inv:";
    private static final String RUMAH_STOK_PREFIX = "rumah_stok:";

    public boolean simpanProgress(Player pemain, Kendaraan kendaraan, Rumah rumah) {
        if (pemain == null || kendaraan == null || rumah == null) {
            System.err.println("Error: Data pemain, kendaraan, atau rumah tidak boleh null saat menyimpan ke TXT.");
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE_NAME_TXT))) {
            // --- Simpan Data Player ---
            writer.write(SECTION_PLAYER); writer.newLine();
            writer.write("nama=" + (pemain.getNama() != null ? pemain.getNama() : "PemainDefault")); writer.newLine();
            writer.write("saldo=" + pemain.getSaldo()); writer.newLine();
            // Simpan itemMap pemain (HANYA SEKALI)
            if (pemain.getItemMap() != null) {
                for (Map.Entry<String, Integer> entry : pemain.getItemMap().entrySet()) {
                    writer.write(PLAYER_ITEM_PREFIX + entry.getKey() + "," + entry.getValue());
                    writer.newLine();
                }
            }
            // Simpan perks yang dimiliki
            if (pemain.getSemuaPerkDimiliki() != null) {
                for (Perk perk : pemain.getSemuaPerkDimiliki()) {
                    if (perk != null) { // Pastikan perk tidak null
                        writer.write(PLAYER_PERK_DIMILIKI_PREFIX + perk.getClass().getSimpleName() + "," + perk.getNama() + "," + perk.getTingkatKesaktian());
                        writer.newLine();
                    }
                }
            }
            // Simpan perks aktif (slot 0 dan 1)
            if (pemain.getPerkAktif() != null) {
                int slotIndex = 0;
                for (Perk perk : pemain.getPerkAktif()) {
                    if (perk != null) { // Hanya simpan jika slot terisi
                        writer.write(PLAYER_PERK_AKTIF_PREFIX + slotIndex + "," + perk.getClass().getSimpleName() + "," + perk.getNama());
                        writer.newLine();
                    }
                    slotIndex++;
                }
            }
            writer.write(END_SECTION); writer.newLine();
            writer.newLine();

            // --- Simpan Data Kendaraan ---
            writer.write(SECTION_KENDARAAN); writer.newLine();
            writer.write("nama=" + (kendaraan.getNama() != null ? kendaraan.getNama() : "TrukDefault")); writer.newLine();
            writer.write("kapasitas=" + kendaraan.getKapasitas()); writer.newLine();
            writer.write("level=" + kendaraan.getlevel()); writer.newLine();
            if (kendaraan.getInventori() != null) {
                for (Map.Entry<Barang, Integer> entry : kendaraan.getInventori().entrySet()) {
                    Barang barang = entry.getKey();
                    if (barang != null) {
                        writer.write(KENDARAAN_INV_PREFIX + barang.getNama() + "," +
                                     barang.getHargaBeli() + "," + barang.getHargaJual() + "," +
                                     entry.getValue());
                        writer.newLine();
                    }
                }
            }
            writer.write(END_SECTION); writer.newLine();
            writer.newLine();

            // --- Simpan Data Rumah ---
            writer.write(SECTION_RUMAH); writer.newLine();
            if (rumah.getStokBarangMap() != null) {
                for (Map.Entry<Barang, Integer> entry : rumah.getStokBarangMap().entrySet()) {
                    Barang barang = entry.getKey();
                    if (barang != null) {
                        writer.write(RUMAH_STOK_PREFIX + barang.getNama() + "," +
                                     barang.getHargaBeli() + "," + barang.getHargaJual() + "," +
                                     entry.getValue());
                        writer.newLine();
                    }
                }
            }
            writer.write(END_SECTION); writer.newLine();

            System.out.println("Progress game berhasil disimpan ke file teks: " + new File(SAVE_FILE_NAME_TXT).getAbsolutePath());
            return true;

        } catch (IOException e) {
            System.err.println("Gagal menyimpan progress game ke file teks: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Object[] muatProgress(List<Barang> masterDaftarBarang, List<Item> masterDaftarItem) {
        File fileSimpanan = new File(SAVE_FILE_NAME_TXT);
        if (!fileSimpanan.exists() || !fileSimpanan.isFile()) {
            System.out.println("File simpanan teks '" + SAVE_FILE_NAME_TXT + "' tidak ditemukan.");
            return null;
        }

        Player loadedPemain = new Player();
        Kendaraan loadedKendaraan = new Kendaraan();
        Rumah loadedRumah = new Rumah();
        String currentSection = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileSimpanan))) {
            // Di dalam kelas SaveFile, method muatProgress

// ... (bagian awal muatProgress tetap sama) ...

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.equals(SECTION_PLAYER)) { currentSection = "PLAYER"; continue; }
                if (line.equals(SECTION_KENDARAAN)) { currentSection = "KENDARAAN"; continue; }
                if (line.equals(SECTION_RUMAH)) { currentSection = "RUMAH"; continue; }
                if (line.equals(END_SECTION)) { currentSection = ""; continue; }

                String keyOrPrefix = null; // Bisa jadi key dari "key=value" atau prefix
                String valueData = null;

                // Cek dulu apakah ini format prefix:data
                int firstColon = line.indexOf(':');
                if (firstColon != -1) {
                    keyOrPrefix = line.substring(0, firstColon + 1); // Include colon as part of prefix
                    valueData = line.substring(firstColon + 1);
                } else { // Jika bukan prefix, coba format key=value
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) {
                        keyOrPrefix = parts[0].trim();
                        valueData = parts[1].trim();
                    } else {
                        System.err.println("Format baris tidak dikenal (bukan prefix:data atau key=value): " + line);
                        continue;
                    }
                }

                if ("PLAYER".equals(currentSection)) {
                    if ("nama".equals(keyOrPrefix)) {
                        loadedPemain.setNama(valueData);
                    } else if ("saldo".equals(keyOrPrefix)) {
                        loadedPemain.setSaldo(Double.parseDouble(valueData));
                    } else if (PLAYER_ITEM_PREFIX.equals(keyOrPrefix)) { // INV_ITEM:
                        String[] itemData = valueData.split(",");
                        if (itemData.length == 2) {
                            String namaItem = itemData[0].trim();
                            int jumlah = Integer.parseInt(itemData[1].trim());
                            Item itemMaster = findItemByName(masterDaftarItem, namaItem);
                            if (itemMaster != null) {
                                loadedPemain.tambahItem(itemMaster, jumlah);
                            } else {
                                System.err.println("Peringatan (Load Player Item): Item master '" + namaItem + "' tidak ditemukan.");
                            }
                        }
                    } else if (PLAYER_PERK_DIMILIKI_PREFIX.equals(keyOrPrefix)) { // perk_dimiliki:
                        String[] perkData = valueData.split(",");
                        if (perkData.length == 3) {
                            String className = perkData[0].trim();
                            String namaPerk = perkData[1].trim();
                            int tingkat = Integer.parseInt(perkData[2].trim());
                            Perk perk = null;
                            // Asumsi konstruktor Perk menerima nama dan tingkat akhir
                            if ("PerkElegan".equals(className)) perk = new PerkElegan(namaPerk, tingkat);
                            else if ("PerkCharming".equals(className)) perk = new PerkCharming(namaPerk, tingkat);
                            else if ("PerkActive".equals(className)) perk = new PerkActive(namaPerk, tingkat);
                            // Tambahkan else if untuk tipe Perk lain jika ada

                            if (perk != null) {
                                loadedPemain.tambahPerkDimiliki(perk);
                            } else {
                                System.err.println("Peringatan (Load Perk Dimiliki): Tipe Perk '" + className + "' tidak dikenal.");
                            }
                        }
                    } else if (PLAYER_PERK_AKTIF_PREFIX.equals(keyOrPrefix)) { // perk_aktif:
                        String[] perkData = valueData.split(",");
                        // Format file Anda: className,namaPerk
                        if (perkData.length == 2) { // Disesuaikan dengan format file Anda
                            String className = perkData[0].trim();
                            String namaPerk = perkData[1].trim();

                            Perk perkToActivate = null;
                            if (loadedPemain.getSemuaPerkDimiliki() != null) {
                                for (Perk p : loadedPemain.getSemuaPerkDimiliki()) {
                                    // Cocokkan berdasarkan nama DAN tipe kelas
                                    if (p.getNama().equals(namaPerk) && p.getClass().getSimpleName().equals(className)) {
                                        perkToActivate = p;
                                        break;
                                    }
                                }
                            }

                            if (perkToActivate != null) {
                                // Coba aktifkan ke slot yang tersedia (0 lalu 1)
                                // Asumsi Player.setPerkAktif akan return false jika slot sudah terisi atau gagal
                                if (!loadedPemain.setPerkAktif(perkToActivate, 0)) { // Coba slot 0
                                    loadedPemain.setPerkAktif(perkToActivate, 1);   // Jika slot 0 gagal, coba slot 1
                                }
                            } else {
                                System.err.println("Peringatan (Load Perk Aktif): Perk '" + namaPerk + "' (" + className + ") tidak ditemukan di daftar perk dimiliki untuk diaktifkan.");
                            }
                        }
                    }
                } else if ("KENDARAAN".equals(currentSection)) {
                    if ("nama".equals(keyOrPrefix)) {
                        loadedKendaraan.setNama(valueData); // Memanggil setter yang sudah ada
                    } else if ("kapasitas".equals(keyOrPrefix)) {
                        loadedKendaraan.setKapasitas(Integer.parseInt(valueData)); // Perlu setter
                    } else if ("level".equals(keyOrPrefix)) {
                        loadedKendaraan.setLevel(Integer.parseInt(valueData)); // Perlu setter
                        // Panggil setNamaByLevel() jika nama kendaraan selalu berdasarkan level
                        // dan tidak di-override oleh 'nama=' dari file.
                        // Jika 'nama=' selalu ada dan itu yang utama, jangan panggil ini.
                        // loadedKendaraan.setNamaByLevel();
                    } else if (KENDARAAN_INV_PREFIX.equals(keyOrPrefix)) { // K_INV_BARANG:
                        String[] barangData = valueData.split(",");
                        if (barangData.length == 4) {
                            String namaBarang = barangData[0].trim();
                            double hargaBeli = Double.parseDouble(barangData[1].trim());
                            double hargaJual = Double.parseDouble(barangData[2].trim());
                            int jumlah = Integer.parseInt(barangData[3].trim());
                            Barang barang = new Barang(namaBarang, hargaBeli, hargaJual);
                            loadedKendaraan.tambahBarang(barang, jumlah);
                        }
                    }
                } else if ("RUMAH".equals(currentSection)) {
                    if (RUMAH_STOK_PREFIX.equals(keyOrPrefix)) { // R_STOK_BARANG:
                        String[] barangData = valueData.split(",");
                        if (barangData.length == 4) {
                            String namaBrg = barangData[0].trim();
                            double hb = Double.parseDouble(barangData[1].trim());
                            double hj = Double.parseDouble(barangData[2].trim());
                            int jumlah = Integer.parseInt(barangData[3].trim());
                            Barang barangMaster = findBarangByName(masterDaftarBarang, namaBrg);
                            Barang barangUntukRumah;
                            if (barangMaster != null && barangMaster.getHargaBeli() == hb && barangMaster.getHargaJual() == hj) {
                                barangUntukRumah = barangMaster;
                            } else {
                                barangUntukRumah = new Barang(namaBrg, hb, hj);
                            }
                            loadedRumah.addBarang(barangUntukRumah, jumlah);
                        }
                    }
                }
            } // End while loop

            if (loadedPemain != null && loadedKendaraan != null) {
                loadedPemain.setKendaraan(loadedKendaraan);
            }

            System.out.println("Progress game berhasil dimuat dari file teks (sesuai file contoh): " + fileSimpanan.getAbsolutePath());
            return new Object[]{loadedPemain, loadedKendaraan, loadedRumah};

// ... (catch blocks tetap sama) ...
        } catch (FileNotFoundException e) {
             System.err.println("File save tidak ditemukan: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Gagal memuat progress (IOException): " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Gagal memuat progress (Format Angka Salah): " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) { // Catchall untuk error tak terduga saat parsing
            System.err.println("Gagal memuat progress (Error Umum): " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Gagal load
    }
    private Barang findBarangByName(List<Barang> masterList, String name) {
        if (masterList == null || name == null || name.trim().isEmpty()) {
            return null;
        }
        for (Barang b : masterList) {
            if (name.trim().equals(b.getNama())) { // Tambahkan trim() untuk konsistensi
                return b;
            }
        }
        System.err.println("Peringatan (SaveFile - findBarangByName): Barang master '" + name + "' tidak ditemukan.");
        return null; // Atau bisa juga membuat Barang default jika tidak ditemukan, tapi null lebih aman
    }

    /**
     * Mencari objek Item dari daftar master berdasarkan nama.
     * @param masterList Daftar semua Item yang mungkin ada di game.
     * @param name Nama Item yang dicari.
     * @return Objek Item jika ditemukan, atau null.
     */
    private Item findItemByName(List<Item> masterList, String name) {
        if (masterList == null || name == null || name.trim().isEmpty()) {
            return null;
        }
        for (Item item : masterList) {
            if (name.trim().equals(item.getNama())) { // Tambahkan trim()
                return item;
            }
        }
        System.err.println("Peringatan (SaveFile - findItemByName): Item master '" + name + "' tidak ditemukan.");
        return null;
    }
// ... (sisa method find...ByName, apakahAdaFileSave, hapusFileSave) ...
}