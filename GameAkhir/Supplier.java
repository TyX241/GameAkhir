package GameAkhir;
import javax.swing.*;

import java.awt.GridLayout;
import java.util.*;

public class Supplier {
    private List<Barang> daftarBarang;
    private Rumah rumah;
    private Player pemain;
    private GamePanel game;
    public Supplier(List<Barang> daftarBarang, Rumah rumah, Player pemain, GamePanel game) {
        this.daftarBarang = daftarBarang;
        this.rumah = rumah;
        this.pemain = pemain;
        this.game = game;
        daftarBarang.add(new Barang("Ester",1000, 1200));
        daftarBarang.add(new Barang("Orirock",2000, 2200));
        daftarBarang.add(new Barang("Oriron",3000, 3200));
        daftarBarang.add(new Barang("Grindstone",4000, 4200));
        daftarBarang.add(new Barang("Crystaline",5000, 5200));
        daftarBarang.add(new Barang("D32 Steel",6000, 6200));
    }
    public List<Barang> getDaftarBarang() {
        return this.daftarBarang;
    }
    public void beliBarang(){
        Scanner in = new Scanner(System.in);
        System.out.println("Daftar Barang:");
        for (int i = 0; i < daftarBarang.size(); i++) {
            System.out.println((i+1) + ". " + daftarBarang.get(i).getNama() + " - Harga Beli: " + daftarBarang.get(i).getHargaBeli());
        }
        System.out.print("Pilih barang yang ingin dibeli (nomor): ");
        int pilihan = in.nextInt();
        if (pilihan > 0 && pilihan <= daftarBarang.size()) {
            Barang barang = daftarBarang.get(pilihan - 1);
            System.out.print("Masukkan jumlah barang yang ingin dibeli: ");
            int jumlah = in.nextInt();
            if (jumlah > 0) {
                double totalHarga = (barang.getHargaBeli() * jumlah);
                if (totalHarga > pemain.getSaldo()) {
                    System.out.println("Saldo tidak cukup.");
                    return;
                }
                pemain.setSaldo(pemain.getSaldo() - totalHarga);
                rumah.addBarang(barang, jumlah);
                System.out.println("Anda membeli " + jumlah + " " + barang.getNama() + " seharga " + totalHarga);
            } else {
                System.out.println("Jumlah tidak valid.");
            }
            
        } else {
            System.out.println("Pilihan tidak valid.");
        }
    }
      public void processPurchase(int index, int jumlah) {
        Barang barang = daftarBarang.get(index);
        double total = barang.getHargaBeli() * jumlah;
        if (jumlah <= 0) {
            return;
        }
        if (total > pemain.getSaldo()) {
            // Gunakan game.window sebagai parent untuk dialog
            JOptionPane.showMessageDialog(
                game.window,
                "Saldo tidak cukup untuk " + barang.getNama(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        } else {
            pemain.setSaldo(pemain.getSaldo() - total);
            rumah.addBarang(barang, jumlah);
            JOptionPane.showMessageDialog(
                game.window,
                "Berhasil beli " + jumlah + " x " + barang.getNama() +
                "\nSisa Saldo: Rp" + pemain.getSaldo(),
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}
