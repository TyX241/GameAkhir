package GameAkhir;
import java.util.*;

public class Main {
    public static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        //testGame();
        GamePanel Game = new GamePanel();
    }
    public void GameScreen(){
        
    }
    public static void testGame(){
        String nama;
        /*Player pemain = new Player();
        Rumah rumah = new Rumah();
        
        Kendaraan kendaraan = new Kendaraan(5,1);
        ArrayList<Barang> daftarBarang = new ArrayList<>();
        ArrayList<Item> daftarItem = new ArrayList<>();
        Peta peta = new Peta();
        TokoItem tokoItem = new TokoItem(daftarItem, pemain, rumah, kendaraan);
        Supplier supplier = new Supplier(daftarBarang, rumah, pemain);
       /*ystem.out.print("Masukan nama pemain:");
        nama = in.next();
        in.nextLine();
        pemain.setNama("Abi");
        do{
            System.out.println("1. Beli Barang");
            System.out.println("2. Beli Item");
            System.out.println("3. Tampilkan Info Pemain");
            System.out.println("4. Tampilkan Info Rumah");
            System.out.println("5. Tampilkan Info Kendaraan");
            System.out.println("6. Berjualan");
            System.out.print("Pilih menu: ");
            int pilihan = in.nextInt();
            switch(pilihan){
                case 1:
                    supplier.beliBarang();
                    break;
                case 2:
                    tokoItem.beliItem(kendaraan);
                    break;
                case 3:
                    System.out.println(pemain.tampilan());
                    break;
                case 4:
                    rumah.infoStok();
                    break;
                case 5:
                    kendaraan.infoKendaraan();
                    break;
                case 6:
                    int lokasi = 0;
                    do{
                    peta.tampilkanLokasi();
                    lokasi = in.nextInt();
                    peta.pindahLokasi(lokasi-1);
                    }while(!peta.getLokasiSekarang().equals("Rumah"));
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }

        }while(true);*/
        
    }
}
     
