//Kelompok 10
//Noval Abimanyu Sufyan Pratama - 245150207111075
//Rusdiansyah Alief Prasetya - 245150207111073
//Anindhita Faiza Aulia - 245150201111042
//Monika Miriwanop Kejok - 245150220111002

package GameAkhir;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player implements Interface{
    private String nama;
    private double saldo = 70000;
    private Kendaraan kendaraan;
    private Rumah rumah;
    private List<Perk> perks;
    private List<Item> items;
    private Map<String, Integer> itemMap = new HashMap<>();
    private List<Perk> semuaPerkDimiliki = new ArrayList<>();
    private List<Perk> perkAktif = new ArrayList<>();    

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
    
    public List<Perk> getPerkDimiliki() {
        List<Perk> perkDimiliki = null;
        return perkDimiliki;
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
        return Collections.unmodifiableList(semuaPerkDimiliki); 
    }

    public List<Perk> getPerkAktif() {
        return Collections.unmodifiableList(perkAktif);
    }
    
    private Map<Item, Integer> daftarItemPlayer = new HashMap<>();

    public void tambahPerkDimiliki(Perk perkBaru) {
        if (perkBaru != null) {
            boolean sudahAdaDenganNamaSama = false;
            for(Perk p : semuaPerkDimiliki) {
                if (p.getNama().equals(perkBaru.getNama()) && p.getClass().equals(perkBaru.getClass())) {
                }
            }
            this.semuaPerkDimiliki.add(perkBaru);
        }
    }
    
    public void hapusPerkDimiliki(Perk perk){
        if(perk != null){
            semuaPerkDimiliki.remove(perk);
            perkAktif.remove(perk);
        }
    }

    public boolean setPerkAktif(Perk perk, int slot) { 
        if (perk == null || !semuaPerkDimiliki.contains(perk) || slot < 0 || slot > 1) {
            return false; 
        }
        if (perkAktif.size() > slot) { 
            if(perkAktif.get(slot) != null && perkAktif.get(slot).equals(perk)) return true; 
            for(int i=0; i < perkAktif.size(); i++){
                if(i != slot && perkAktif.get(i) != null && perkAktif.get(i).equals(perk)){
                    System.out.println("Perk " + perk.getNama() + " sudah aktif di slot lain.");
                    return false;
                }
            }
            perkAktif.set(slot, perk);
        } else if (perkAktif.size() == slot) { 
            for(Perk pAktif : perkAktif){
                if(pAktif != null && pAktif.equals(perk)){
                     System.out.println("Perk " + perk.getNama() + " sudah aktif di slot lain.");
                    return false;
                }
            }
            perkAktif.add(slot, perk);
        } else {
            return false;
        }

        while (perkAktif.size() > 2) {
            perkAktif.remove(perkAktif.size() - 1); 
        }
        perkAktif.removeAll(Collections.singleton(null));
        System.out.println("Perk " + perk.getNama() + " diaktifkan di slot " + (slot + 1));
        return true;
    }

    public void lepasPerkAktif(int slot) {
        if (slot >= 0 && slot < perkAktif.size()) {
            Perk dilepas = perkAktif.get(slot);
            if (dilepas != null) {
                System.out.println("Perk " + dilepas.getNama() + " dilepas dari slot " + (slot+1));
                perkAktif.set(slot, null); 
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

    String daftarItem = "";
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
   
   public boolean cobaTransaksi() {
    double peluangBerhasil = 0.5;
        Iterable<Item> itemAktif = null;

    for (Item item : itemAktif) {
        String nama = item.getNama().toLowerCase();

        switch (nama) {
            case "penampilan maksimal":
                if (Math.random() < item.getBonusEfek()) {
                    System.out.println("💃 Pembeli langsung setuju beli karena penampilan maksimal!");
                    return true;
                }
                break;

            case "diskon mantap":
                double bonusDiskon = item.getBonusEfek();
                System.out.printf("💸 Diskon promo aktif! Keberhasilan meningkat sebesar %.0f%%%n", bonusDiskon * 100);
                peluangBerhasil += bonusDiskon;
                break;

            case "ahli tawar":
                double bonusTawar = item.getBonusEfek();
                System.out.printf("🧠 Skill negosiasi meningkat sebesar %.0f%%%n", bonusTawar * 100);
                peluangBerhasil += bonusTawar;
                break;

            case "hipnotis":
                if (Math.random() < item.getBonusEfek()) {
                    System.out.println("🌀 Pembeli terhipnotis dan langsung setuju beli!");
                    return true;
                }
                break;

            case "sentuhan ajaib":
                double bonusSentuhan = item.getBonusEfek();
                System.out.printf("✨ Sentuhan Ajaib aktif! Peluang meningkat sebesar %.0f%%%n", bonusSentuhan * 100);
                peluangBerhasil += bonusSentuhan;
                break;
        }
    }

    if (Math.random() < peluangBerhasil) {
        System.out.println("✅ Transaksi berhasil!");
        return true;
    } else {
        System.out.println("❌ Transaksi gagal.");
        return false;
    }
}

}
