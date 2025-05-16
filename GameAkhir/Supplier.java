package GameAkhir;
import java.util.*;
public class Supplier {
    private List<Barang> daftarBarang;
    private Rumah rumah;
    private Player pemain;
    public Supplier(List<Barang> daftarBarang, Rumah rumah, Player pemain) {
        this.daftarBarang = daftarBarang;
        this.rumah = rumah;
        this.pemain = pemain;
        daftarBarang.add(new Barang("Pensil",1000, 1200));
        daftarBarang.add(new Barang("Pena",2000, 2200));
        daftarBarang.add(new Barang("Buku",3000, 3200));
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
}
