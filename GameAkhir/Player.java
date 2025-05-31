package GameAkhir;
import java.util.*;
public class Player implements Interface{
    private String nama;
    private double saldo = 1000069420;
    private Kendaraan kendaraan;
    private Rumah rumah;
    private List<Perk> perks;
    private List<Item> items;
    private Map<String, Integer> itemMap = new HashMap<>();
    private List<Perk> semuaPerkDimiliki = new ArrayList<>();
    private List<Perk> perkAktif = new ArrayList<>();       // Maksimal 2 perk yang aktif untuk berjualan

    public Player(String nama, double saldo, Kendaraan kendaraan, Rumah rumah, List<Perk> perks, List<Item> items) {
        this.nama = nama;
        this.saldo = saldo;
        this.kendaraan = kendaraan;
        this.rumah = rumah;
        this.perks = perks;
        this.items = new ArrayList<>();
        this.itemMap = new HashMap<>();
    }

    public Player() {
        this.items = new ArrayList<>();
        this.perks = new ArrayList<>();
        this.itemMap = new HashMap<>();
    }
    public Player(String nama) {
        this.nama = nama;
        this.items = new ArrayList<>();
        this.perks = new ArrayList<>();
        this.itemMap = new HashMap<>();
    }
    
    public Map<String, Integer> getItemMap(){
        return this.itemMap;
    };
        
    public void tambahItem(Item item, int jumlah) {
        itemMap.put(item.getNama(), itemMap.getOrDefault(item.getNama(), 0) + jumlah);
    }

    public int getJumlahItem(String namaItem) {
        return itemMap.getOrDefault(namaItem, 0);
    }
    
    public void tambahPerk(Perk perk){
        if(perks.size() >= 2){
            throw new IllegalStateException("Batas perk tercapai");
        }
            perks.add(perk);
    }
    public void jualBarang(){

    }
    public void gunakanItem(){

    }
    public String getNama(){
        return nama;
    }
    public void setNama(String nama){
        this.nama = nama;
    }
    public void aturHarga(){
        
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    public double getSaldo() {
        return saldo;
    }
    public void setKendaraan(Kendaraan kendaraan) {
        this.kendaraan = kendaraan;
    }
    public void getperks(){
        for (Perk perk : perks) {
            System.out.println(perk.getNama());
        }
    }
    public void tambahSaldo(double jumlah) {
        this.saldo += jumlah;
    }
    
    public void kurangiSaldo(double jumlah) {
        if (saldo >= jumlah) {
            this.saldo -= jumlah;
        }
    }
    public Kendaraan getKendaraan() {
        return kendaraan;
    }

    
    public List<Perk> getSemuaPerkDimiliki() {
        return Collections.unmodifiableList(semuaPerkDimiliki); // Kembalikan versi unmodifiable
    }

    public List<Perk> getPerkAktif() {
        return Collections.unmodifiableList(perkAktif);
    }

    public void tambahPerkDimiliki(Perk perkBaru) {
        if (perkBaru != null) {
            // Cek apakah sudah punya perk dengan nama yang sama (mungkin beda tipe atau level)
            // Untuk simplicity, kita izinkan duplikat nama jika tipenya beda, tapi idealnya nama unik.
            // Atau, jika nama sama dan tipe sama, mungkin ini adalah hasil upgrade.
            boolean sudahAdaDenganNamaSama = false;
            for(Perk p : semuaPerkDimiliki) {
                if (p.getNama().equals(perkBaru.getNama()) && p.getClass().equals(perkBaru.getClass())) {
                    // Jika sudah ada perk dengan nama dan tipe yang sama, mungkin ganti dengan yang baru (jika level lebih tinggi)
                    // Atau, ini bisa jadi logika yang lebih kompleks. Untuk sekarang, kita replace saja.
                    // Ini perlu dipikirkan: apakah pemain bisa punya >1 perk "Elegan Awal"?
                    // Jika tidak, maka logika ini perlu disesuaikan.
                    // Untuk sekarang, kita anggap bisa ada beberapa instance perk berbeda.
                    // Jika nama adalah ID unik, maka harus ada pengecekan lebih lanjut.
                    // Jika Anda ingin nama unik, maka jangan tambahkan jika sudah ada nama yang sama.
                }
            }
            this.semuaPerkDimiliki.add(perkBaru);
        }
    }
    
    public void hapusPerkDimiliki(Perk perk){
        if(perk != null){
            semuaPerkDimiliki.remove(perk);
            // Juga hapus dari perk aktif jika ada di sana
            perkAktif.remove(perk);
        }
    }


    public boolean setPerkAktif(Perk perk, int slot) { // slot 0 atau 1
        if (perk == null || !semuaPerkDimiliki.contains(perk) || slot < 0 || slot > 1) {
            return false; // Perk tidak dimiliki atau slot tidak valid
        }

        // Jika slot sudah terisi oleh perk lain, kembalikan perk lama ke daftar dimiliki (jika perlu)
        // Untuk sekarang, kita asumsikan perk aktif bisa diganti langsung.
        
        // Pastikan tidak ada perk yang sama di kedua slot aktif
        if (perkAktif.size() > slot) { // Jika slot sudah ada isinya
            if(perkAktif.get(slot) != null && perkAktif.get(slot).equals(perk)) return true; // Sudah aktif di slot itu
            // Cek apakah perk ini sudah aktif di slot lain
            for(int i=0; i < perkAktif.size(); i++){
                if(i != slot && perkAktif.get(i) != null && perkAktif.get(i).equals(perk)){
                    // Perk sudah aktif di slot lain, tidak bisa double
                    System.out.println("Perk " + perk.getNama() + " sudah aktif di slot lain.");
                    return false;
                }
            }
            perkAktif.set(slot, perk);
        } else if (perkAktif.size() == slot) { // Jika menambah ke slot berikutnya yang kosong
             // Cek apakah perk ini sudah aktif di slot lain
            for(Perk pAktif : perkAktif){
                if(pAktif != null && pAktif.equals(perk)){
                     System.out.println("Perk " + perk.getNama() + " sudah aktif di slot lain.");
                    return false;
                }
            }
            perkAktif.add(slot, perk);
        } else { // Slot yang dituju terlalu jauh (misal, coba set slot 1 padahal slot 0 kosong)
            return false;
        }
        
        // Pastikan tidak lebih dari 2 perk aktif
        while (perkAktif.size() > 2) {
            perkAktif.remove(perkAktif.size() - 1); // Hapus yang terakhir jika kelebihan
        }
        // Hapus null jika ada dari pengisian slot yang tidak berurutan
        perkAktif.removeAll(Collections.singleton(null));


        System.out.println("Perk " + perk.getNama() + " diaktifkan di slot " + (slot + 1));
        return true;
    }

    public void lepasPerkAktif(int slot) {
        if (slot >= 0 && slot < perkAktif.size()) {
            Perk dilepas = perkAktif.get(slot);
            if (dilepas != null) {
                System.out.println("Perk " + dilepas.getNama() + " dilepas dari slot " + (slot+1));
                perkAktif.set(slot, null); // Kosongkan slot
                // Atau perkAktif.remove(slot) jika ingin listnya mengecil, tapi lebih baik set null untuk menjaga 2 slot
                // Kemudian bersihkan null
                perkAktif.removeAll(Collections.singleton(null));
            }
        }
    }
    
   public String tampilan() {
    String daftarPerk = "Tidak ada";
    if (!perks.isEmpty()) {
        daftarPerk = "";
        for (Perk p : perks) {
            daftarPerk += p.getNama() + ", ";
        }
        daftarPerk = daftarPerk.substring(0, daftarPerk.length() - 2); 
    }

    String daftarItem = "Tidak ada";
    if (!itemMap.isEmpty()) {
        daftarItem = "";
        for (Map.Entry<String, Integer> entry : itemMap.entrySet()) {
            daftarItem += entry.getKey() + " x" + entry.getValue() + ", ";
        }
        daftarItem = daftarItem.substring(0, daftarItem.length() - 2);
    }
     String daftarPerkAktif = "Tidak ada";
    if (!perkAktif.isEmpty()) {
        daftarPerkAktif = "";
        for (Perk p : perkAktif) {
            if (p != null) {
                daftarPerkAktif += p.getNama() + ", ";
            }
        }
        if (!daftarPerkAktif.isEmpty()) {
            daftarPerkAktif = daftarPerkAktif.substring(0, daftarPerkAktif.length() - 2);
        } else {
            daftarPerkAktif = "Tidak ada";
        }
    }

    return String.format(
        "Nama: %s%nSaldo: %.0f%nKendaraan: %s%nPerks: %s%nItems: %s",
        nama, saldo, kendaraan.getNama(), daftarPerkAktif, daftarItem
    );
    

}
}
