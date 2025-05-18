package GameAkhir;
import java.util.*;
public class Player implements Interface{
    private String nama;
    private double saldo = 69420;
    private Kendaraan kendaraan;
    private Rumah rumah;
    private List<Perk> perks;
    private List<Item> items;

    public Player(String nama, double saldo, Kendaraan kendaraan, Rumah rumah, List<Perk> perks, List<Item> items) {
        this.nama = nama;
        this.saldo = saldo;
        this.kendaraan = kendaraan;
        this.rumah = rumah;
        this.perks = perks;
        this.items = new ArrayList<>();
    }

    public Player() {
        this.items = new ArrayList<>();
        this.perks = new ArrayList<>();
    }
    
    public void tambahItem(Item item){
        items.add(item);
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
    
    @Override
    public String tampilan() {
    String daftarPerk = "Tidak ada";
    if (!perks.isEmpty()) {
        daftarPerk = "";
        for (Perk p : perks) {
            daftarPerk += p.getNama() + ", ";
        }
        daftarPerk = daftarPerk.substring(0, daftarPerk.length() - 2); // Hapus ", " terakhir
    }

    String daftarItem = "Tidak ada";
    if (!items.isEmpty()) {
        daftarItem = "";
        for (Item i : items) {
            daftarItem += i.getNama() + " x" + i.getJumlah() + ", ";
        }
        daftarItem = daftarItem.substring(0, daftarItem.length() - 2);
    }

    return String.format(
        "Nama: %s%nSaldo: %.0f%nKendaraan: %s%nPerks: %s%nItems: %s",
        nama, saldo, kendaraan.getNama(), daftarPerk, daftarItem
    );
}
}
