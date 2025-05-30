package GameAkhir;
public class Berjualan implements Interface {
    private Barang barang;
    private Pembeli pembeli;

    public Berjualan(Barang barang, Pembeli pembeli) {
        this.barang = barang;
        this.pembeli = pembeli;
    }

    @Override
    public String tampilan() {
        StringBuilder sb = new StringBuilder();
        sb.append("Barang: ").append(barang.getNama()).append("\n");
        sb.append("Harga Beli: ").append(barang.getHargaBeli()).append("\n");
        sb.append("Harga Jual: ").append(barang.getHargaJual()).append("\n");
        sb.append("Pembeli: ").append(pembeli.getNama()).append("\n");
        return sb.toString();
    }
}