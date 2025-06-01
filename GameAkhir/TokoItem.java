package GameAkhir;
import java.util.*;
public class TokoItem {
    private List<Item> daftarItem;
    private Item item;
    private Player pemain;
    private Rumah rumah;
    private Kendaraan kendaraan;

    public TokoItem(List<Item> daftarItem, Player pemain, Rumah rumah, Kendaraan kendaraan) {
        this.daftarItem = daftarItem;
        this.pemain = pemain;
        this.rumah = rumah;
        this.kendaraan = kendaraan;
        daftarItem.add(new Item("Hipnotis", "Hipnotis", 10000, 1, 0));
        daftarItem.add(new Item("Originium", "tertarik", 12000, 1, 0));
        daftarItem.add(new Item("Lucky Coin", "bonus", 5000, 1, 0));
    }
}
