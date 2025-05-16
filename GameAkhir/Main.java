package GameAkhir;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String nama;
        Player pemain = new Player();
        Rumah rumah = new Rumah();
        ArrayList<Barang> daftarBarang = new ArrayList<>();
        Supplier supplier = new Supplier(daftarBarang, rumah, pemain);
        System.out.println("Masukan nama pemain:");
        nama = in.next();
        pemain.setNama(nama);
        supplier.beliBarang();
        pemain.tampilan();
        rumah.infoStok();
    }
}
