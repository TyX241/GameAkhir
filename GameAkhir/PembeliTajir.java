package GameAkhir;

import java.util.Random;

public class PembeliTajir extends Pembeli {
    private final Random random = new Random();

    public PembeliTajir(String nama) {
        super(nama);
    }

    @Override
    public int tawarHarga(int hargaAwal) {
        if (random.nextDouble() < 0.8) return hargaAwal;
        int diskon = 5 + random.nextInt(6); 
        return hargaAwal * (100 - diskon) / 100;
    }

    @Override
    public boolean putuskanBeli() {
        return random.nextDouble() < 0.95;
    }
}
