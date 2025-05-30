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

    return String.format(
        "Nama: %s%nSaldo: %.0f%nKendaraan: %s%nPerks: %s%nItems: %s",
        nama, saldo, kendaraan.getNama(), daftarPerk, daftarItem
    );
    

}
}
