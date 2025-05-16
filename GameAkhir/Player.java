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
        this.items = items;
    }

    public Player() {
    }
    
    public void tambahItem(Item item){
        items.add(item);
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
    
    @Override
    public void tampilan(){
        System.out.println("Nama: "+nama);
        System.out.println("saldo: "+saldo);
        System.out.println("Kendaraan: "+kendaraan);
        System.out.println("perks: "+perks);
        System.out.println("items: "+items);
    }
}
