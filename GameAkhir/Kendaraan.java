package GameAkhir;

public class Kendaraan {
    private String nama = "Truk";
    private int kapasitas = 5;
    private int level = 1;

    public Kendaraan(String nama, int kapasitas, int level){
        this.nama = nama;
        this.kapasitas = kapasitas;
        this.level = level;
    }
    public Kendaraan(){
    }
    public String getNama(){
        return nama;
    }

    public int getlevel(){
        return level;
    }

    private void upgrade(){
        
    }
    public void infoKendaraan(){
        System.out.println("Kendaraan level " + level + " dengan kapasitas " + kapasitas);
    }
}
