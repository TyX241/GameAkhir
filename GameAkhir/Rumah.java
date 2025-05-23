package GameAkhir;

import java.util.*;

public class Rumah {
    private Map<Barang, Integer> stokBarang = new HashMap<>();
    private Map<Barang, Double> hargaJual = new HashMap<>();

    public Rumah() {
        this.stokBarang = new HashMap<>();
        this.hargaJual = new HashMap<>();
    }

    public void aturStok() {
    }

    public void aturHargaJual() {
    }

    public Map<Barang, Integer> getStokBarangMap() {
        return stokBarang;
    }

    public void infoStok() {
        Scanner in = new Scanner(System.in);
        System.out.println("1. Lihat Stok Barang\n2. Lihat Harga Jual Barang");
        System.out.print("Pilihan: ");
        int i = in.nextInt();

        if (i == 1) {
            System.out.println("Stok barang di rumah:");
            for (Map.Entry<Barang, Integer> entry : stokBarang.entrySet()) {
                System.out.println(entry.getKey().getNama() + ": " + entry.getValue());
            }
        } else if (i == 2) {
            System.out.println("Harga jual barang:");
            for (Map.Entry<Barang, Double> entry : hargaJual.entrySet()) {
                System.out.println(entry.getKey().getNama() + ": Rp" + entry.getValue());
            }
        }
    }

    public void addBarang(Barang barang, int jumlah) {
        stokBarang.put(barang, stokBarang.getOrDefault(barang, 0) + jumlah);
        hargaJual.put(barang, barang.getHargaJual());
    }

    public ArrayList<Barang> getStokBarangList() {
        return new ArrayList<>(stokBarang.keySet());
    }

    public int getJumlah(Barang barang) {
        return stokBarang.getOrDefault(barang, 0);
    }

    public double getHargaJual(Barang barang) {
        return hargaJual.getOrDefault(barang, barang.getHargaJual());
    }

    public void kurangiStok(Barang barang, int jumlah) {
        int sisa = getJumlah(barang) - jumlah;
        stokBarang.put(barang, Math.max(sisa, 0));
    }

}
