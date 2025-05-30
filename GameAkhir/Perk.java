package GameAkhir;

public abstract class Perk implements Upgradable{
    protected String nama;
    protected int tingkatKesaktian;
    protected int ability;

    public Perk(String nama, int tingkatKesaktian) {
        this.nama = nama;
        this.tingkatKesaktian = tingkatKesaktian;
    }

    public void upgrade(){
    
    }
    public String getNama(){
        return nama;
    }
    public int getHargaUpgrade(){
        return 0;
    }
    public Perk ubahAbility() throws GameException{
        return null;
    }
    public double getEfek(){
        return 0;
    }
}
    class PerkElegan extends Perk{
        public PerkElegan(String nama, int tingkatKesaktian){
            super(nama, tingkatKesaktian);
        }
        @Override
        public void upgrade(){
            if(tingkatKesaktian < 5){
                tingkatKesaktian++;
            }else{
                throw new IllegalArgumentException("Tingkat kesaktian sudah maksimal");
            }
        }
        @Override
        public Perk ubahAbility() throws GameException{
           return new PerkCharming(this.nama, 1);
        }
        @Override
        public int getHargaUpgrade(){
            return 1000 * (int) Math.pow(1.3,tingkatKesaktian);
        }

        @Override
        public double getEfek() {
            return 0.1 * tingkatKesaktian;
        }


    }
    class PerkCharming extends Perk{
        public PerkCharming(String nama, int tingkatKesaktian){
            super(nama, tingkatKesaktian);
        }
        @Override
        public void upgrade(){
            if(tingkatKesaktian < 5){
                tingkatKesaktian++;
            }else{
                throw new IllegalArgumentException("Tingkat kesaktian sudah maksimal");
            }
        }
        @Override
        public Perk ubahAbility() throws GameException{
           return new PerkActive(this.nama, 1);
        }
        @Override
        public int getHargaUpgrade(){
            return 1000 * (int) Math.pow(1.3,tingkatKesaktian);
        }

        @Override
        public double getEfek() {
            return 0.1 * tingkatKesaktian;
        }
    }
    class PerkActive extends Perk{
        public PerkActive(String nama, int tingkatKesaktian){
            super(nama, tingkatKesaktian);
        }
        @Override
        public void upgrade(){
            if(tingkatKesaktian < 5){
                tingkatKesaktian++;
            }else{
                throw new IllegalArgumentException("Tingkat kesaktian sudah maksimal");
            }
        }
        @Override
        public Perk ubahAbility() throws GameException{
           return new PerkElegan(this.nama, 1);
        }
        @Override
        public int getHargaUpgrade(){
            return 1000 * (int) Math.pow(1.3,tingkatKesaktian);
        }

        @Override
        public double getEfek() {
            return 0.1 * tingkatKesaktian;
        }
    }
