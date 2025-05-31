package GameAkhir;

public abstract class Perk implements Upgradable{
    protected String nama;
    protected int tingkatKesaktian;
    protected int ability;
    protected int hargaJual = 10000;

    public Perk(String nama, int tingkatKesaktian) {
        this.nama = nama;
        this.tingkatKesaktian = tingkatKesaktian;
    }


    public abstract void upgrade()throws GameException;
    public String getNama(){
        return nama;
    }
    public int getTingkatKesaktian(){
        return tingkatKesaktian;
    }
    public int getHarga(){
        return hargaJual * tingkatKesaktian;
    }
    public int getHargaUpgrade(){
        return 0;
    }
    public double getEfek() {
            return 0.05 * tingkatKesaktian;
        }
    public abstract Perk ubahAbility(String namaPerkBaru) throws GameException;
    public abstract String getDeskripsiEfek();
    public abstract int getBiayaUbahAbility();
}
    class PerkElegan extends Perk{
        public PerkElegan(String nama, int tingkatKesaktian){
            super(nama, tingkatKesaktian);
        }
        @Override
        public void upgrade() throws GameException {
            if(tingkatKesaktian < 5){
                tingkatKesaktian++;
            }else{
                throw new GameException("Tingkat kesaktian sudah maksimal");
            }
        }
       @Override
        public Perk ubahAbility(String namaPerkBaru) throws GameException {
            // Elegan bisa jadi Charming
            return new PerkCharming(namaPerkBaru, 1); // Reset level ke 1
        }
        @Override
        public int getHargaUpgrade(){
            return 1000 * (int) Math.pow(1.3,tingkatKesaktian);
        }

        @Override
        public int getBiayaUbahAbility() {
            return 15000; // Biaya tetap untuk ubah dari Elegan
        }
        @Override
        public String getDeskripsiEfek() {
            return String.format("Meningkatkan %+.0f%% peluang bertemu Pembeli Tajir.", getEfek() * 100);
        }


    }
    class PerkCharming extends Perk{
        public PerkCharming(String nama, int tingkatKesaktian){
            super(nama, tingkatKesaktian);
        }
        @Override
        public void upgrade() throws GameException {
            if(tingkatKesaktian < 5){
                tingkatKesaktian++;
            }else{
                throw new GameException("Tingkat kesaktian sudah maksimal");
            }
        }
        @Override
        public Perk ubahAbility(String namaPerkBaru) throws GameException {
            // Charming bisa jadi Active
            return new PerkActive(namaPerkBaru, 1);
        }
        @Override
        public int getHargaUpgrade(){
            return 1000 * (int) Math.pow(1.3,tingkatKesaktian);
        }
        
        @Override
        public int getBiayaUbahAbility() {
            return 15000; // Biaya tetap untuk ubah dari Elegan
        }
        @Override
        public String getDeskripsiEfek() {
            return String.format("Meningkatkan %+.0f%% keberhasilan tawar menawar.", getEfek() * 100);
        }
    }
    class PerkActive extends Perk{
        public PerkActive(String nama, int tingkatKesaktian){
            super(nama, tingkatKesaktian);
        }
        @Override
        public void upgrade() throws GameException {
            if(tingkatKesaktian < 5){
                tingkatKesaktian++;
            }else{
                throw new GameException("Tingkat kesaktian sudah maksimal");
            }
        }
         @Override
        public Perk ubahAbility(String namaPerkBaru) throws GameException {
            // Active bisa jadi Elegan
            return new PerkElegan(namaPerkBaru, 1);
        }
        @Override
        public int getHargaUpgrade(){
            return 1000 * (int) Math.pow(1.3,tingkatKesaktian);
        }

        @Override
        public int getBiayaUbahAbility() {
            return 15000; // Biaya tetap untuk ubah dari Elegan
        }
        @Override
        public String getDeskripsiEfek() {
            // Deskripsi bisa lebih naratif
            if (tingkatKesaktian == 1) {
                return "Meningkatkan sedikit aktivitas pembeli di sekitar.";
            }
            return String.format("Meningkatkan hingga %+.0f%% peluang bertemu pembeli tambahan.", getEfek() * 100);
        }
    }
