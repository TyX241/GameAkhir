package GameAkhir;
import java.util.*;
public class Rumah {
    private Map<Barang, Integer> stokBarang = new HashMap<>();
    private Map<Barang, Double> hargaJual = new HashMap<>();

    public Rumah() {
        this.stokBarang = new HashMap<>();
        this.hargaJual = new HashMap<>();
    }
    public void aturStok(){

    }
    public void aturHargaJual(){
        
    }
    public Map<Barang, Integer> getStokBarang() {
        return stokBarang;
    }
    public void infoStok() {
        Scanner in = new Scanner(System.in);
        int i, urutan[], jumlah, harga;
        System.out.println("1. Lihat Stok Barang\n2. Lihat Harga Jual Barang");
        System.out.print("Pilihan: ");
        i = in.nextInt();
        if(i == 1){
            System.out.println("Stok barang di rumah:");
            urutan = new int[stokBarang.size()];
            for (Map.Entry<Barang, Integer> entry : stokBarang.entrySet()) {
                System.out.println(entry.getKey().getNama() + ": " + entry.getValue());
            }
        }else if(i == 2){
            System.out.println("Harga jual barang:");
            for (Map.Entry<Barang, Double> entry : hargaJual.entrySet()) {
                System.out.println(entry.getKey().getNama() + ": " + entry.getValue());
            }
        }
    }
    public void addBarang(Barang barang, int jumlah) {
    if (stokBarang == null) {
        stokBarang = new HashMap<Barang, Integer>();
    }
    if (hargaJual == null) {
        hargaJual = new HashMap<Barang, Double>();
    }
    stokBarang.put(barang, stokBarang.getOrDefault(barang, 0) + jumlah);
    hargaJual.put(barang, barang.getHargaJual());
}
}
