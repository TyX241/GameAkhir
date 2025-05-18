package GameAkhir;
import java.util.*;
public class TokoItem {
    private List<Item> daftarItem;
    private List<Item> itemDefault = Arrays.asList(
        new Item("Buku", "Intelegent + 100", 10000, 1, 1),
        new Item("Kunci", "Kendaraan + 1", 10000, 2, 2),
        new Item("Obat", "Kesehatan + 100", 10000, 3, 1)
    );
    private Player pemain;
    private Rumah rumah;
    private Kendaraan kendaraan;

    public TokoItem(List<Item> daftarItem, Player pemain, Rumah rumah, Kendaraan kendaraan) {
        this.daftarItem = daftarItem;
        this.pemain = pemain;
        this.rumah = rumah;
        this.kendaraan = kendaraan;
    }

    public void beliItem(Kendaraan kendaraan) {
        Scanner in = new Scanner(System.in);
        List<Item> daftarItem = new ArrayList<>();
        
        for(Item item : itemDefault){
            if(item.getPerluLevel()<= kendaraan.getlevel()){
                daftarItem.add(item);
            }
        }

        System.out.println("Daftar Item yang tersedia:");
        for (int i = 0; i < daftarItem.size(); i++) {
            Item item = daftarItem.get(i);
            System.out.println((i + 1) + ". " + item.getNama() + " - Efek: " + item.getEfek() + " - Harga: " + item.getHarga());
        }
        System.out.print("Pilih nomor item yang ingin dibeli: ");
        int pilihan = in.nextInt();
        if (pilihan < 1 || pilihan > daftarItem.size()) {
            System.out.println("Pilihan tidak valid.");
            return;
        }
        Item itemTerpilih = daftarItem.get(pilihan - 1);
        if (pemain.getSaldo() < itemTerpilih.getHarga()) {
            System.out.println("Saldo tidak cukup untuk membeli item ini.");
            return;
        }
        pemain.setSaldo(pemain.getSaldo() - itemTerpilih.getHarga());
        pemain.tambahItem(itemTerpilih);
        System.out.println("Item " + itemTerpilih.getNama() + " berhasil dibeli dan ditambahkan ke inventaris pemain.");
    }
}
