package GameAkhir;

import java.util.Random;

public class PembeliMiskin extends Pembeli {
    private final Random random = new Random();

    public PembeliMiskin(String nama) {
        super(nama);
    }

    @Override
    public int tawarHarga(int hargaAwal) {
        int persen = 70 + random.nextInt(21); 
        return hargaAwal * persen / 100;
    }

    @Override
    public boolean putuskanBeli() {
        return random.nextDouble() < 0.65;
    }
}
