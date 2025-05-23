package GameAkhir;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel {
    String nama;
    Player pemain = new Player();
    Rumah rumah = new Rumah();
    Kendaraan kendaraan = new Kendaraan("Trek", 5, 1);
    ArrayList<Barang> daftarBarang = new ArrayList<>();
    ArrayList<Item> daftarItem = new ArrayList<>();
    Peta peta = new Peta();
    TokoItem tokoItem = new TokoItem(daftarItem, pemain, rumah, kendaraan);
    Supplier supplier = new Supplier(daftarBarang, rumah, pemain, this);

    JFrame window;
    Container con;
    JPanel namaPanel, judulPanel, tombolStartPanel, mainTextPanel, tombolPilihanPanel, panelPemain;
    JLabel judulLabel;
    JButton tombolStart, pil1, pil2, pil3, pil4, pil5;
    Font fontJudul = new Font("Times New Roman", Font.PLAIN, 90);
    Font fontNormal = new Font("Times New Roman", Font.PLAIN, 20);
    JTextArea mainTextArea;
    String position;
    private ArrayList<JSpinner> spinners;

    public GamePanel() {
        pemain.setKendaraan(kendaraan);
        window = new JFrame();
        window.setSize(1080, 720);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);
        window.setLayout(null);

        con = window.getContentPane();

        namaPanel = new JPanel();
        namaPanel.setBounds(450, 300, 150, 25);
        namaPanel.setBackground(Color.WHITE);
        JTextField textField = new JTextField(10);
        namaPanel.add(new JLabel("Nama:"));
        namaPanel.add(textField);
        con.add(namaPanel);
        textField.addActionListener(e -> {
            String nama = textField.getText();
            pemain.setNama(nama);
            namaPanel.setVisible(false);
        });

        judulPanel = new JPanel();
        judulPanel.setBounds(220, 100, 600, 150);
        judulPanel.setBackground(Color.BLACK);
        judulLabel = new JLabel("SHOP");
        judulLabel.setForeground(Color.WHITE);
        judulLabel.setFont(fontJudul);

        tombolStartPanel = new JPanel();
        tombolStartPanel.setBounds(420, 400, 200, 100);
        tombolStartPanel.setBackground(Color.BLACK);

        tombolStart = new JButton("START");
        tombolStart.setBackground(Color.black);
        tombolStart.setForeground(Color.white);
        tombolStart.setFont(fontNormal);
        tombolStart.addActionListener(new TitleScreenHandler());

        judulPanel.add(judulLabel);
        tombolStartPanel.add(tombolStart);
        con.add(judulPanel);
        con.add(tombolStartPanel);

        daftarItem.add(new Item("Hipnotis", "Meningkatkan peluang pembeli setuju", 10000, 1, 0));
        daftarItem.add(new Item("Promo", "Meningkatkan minat beli", 7500, 1, 0));

        window.setVisible(true);
    }

    public void GameScreen() {
        judulPanel.setVisible(false);
        tombolStartPanel.setVisible(false);

        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(100, 100, 600, 250);
        mainTextPanel.setBackground(Color.BLUE);
        con.add(mainTextPanel);

        mainTextArea = new JTextArea(" ");
        mainTextArea.setBackground(Color.BLACK);
        mainTextArea.setForeground(Color.WHITE);
        mainTextArea.setFont(fontNormal);
        mainTextArea.setLineWrap(true);
        mainTextPanel.add(mainTextArea);

        tombolPilihanPanel = new JPanel();
        tombolPilihanPanel.setBounds(750, 100, 200, 550);
        tombolPilihanPanel.setBackground(Color.BLACK);
        tombolPilihanPanel.setLayout(new GridLayout(5, 1));
        con.add(tombolPilihanPanel);

        pil1 = new JButton("Pilihan 1");
        pil2 = new JButton("Pilihan 2");
        pil3 = new JButton("Pilihan 3");
        pil4 = new JButton("Pilihan 4");
        pil5 = new JButton("Pilihan 5");

        JButton[] buttons = {pil1, pil2, pil3, pil4, pil5};
        for (JButton b : buttons) {
            b.setBackground(Color.black);
            b.setForeground(Color.white);
            b.setFont(fontNormal);
            tombolPilihanPanel.add(b);
        }

        panelPemain = new JPanel();
        panelPemain.setBounds(100, 400, 600, 250);
        panelPemain.setBackground(Color.BLUE);
        panelPemain.setLayout(new BorderLayout());
        con.add(panelPemain);

        pil1.addActionListener(e -> handleMenu(1));
        pil2.addActionListener(e -> handleMenu(2));
        pil3.addActionListener(e -> handleMenu(3));
        pil4.addActionListener(e -> handleMenu(4));
        pil5.addActionListener(e -> handleMenu(5));

        JTextArea infoArea = new JTextArea(pemain.tampilan());
        infoArea.setFont(fontNormal);
        infoArea.setEditable(false);
        infoArea.setBackground(Color.BLACK);
        infoArea.setForeground(Color.WHITE);
        infoArea.setLineWrap(true);

        JScrollPane scroll = new JScrollPane(infoArea);
        panelPemain.add(scroll, BorderLayout.CENTER);
        homeChoice();
        window.revalidate();
        window.repaint();
    }

private void showBuyBarangScreen() {
    con.removeAll();

    JLabel title = new JLabel("Toko Barang / Supplier");
    title.setFont(fontJudul);
    title.setForeground(Color.WHITE);
    title.setBounds(220, 20, 600, 100);
    con.add(title);

    JPanel barangPanel = new JPanel();
    barangPanel.setLayout(new BoxLayout(barangPanel, BoxLayout.Y_AXIS));
    barangPanel.setBackground(Color.WHITE);

    for (Barang barang : daftarBarang) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(750, 90));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel namaLabel = new JLabel(barang.getNama());
        namaLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel infoLabel = new JLabel("Harga: Rp" + barang.getHargaBeli());
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        infoPanel.add(namaLabel);
        infoPanel.add(infoLabel);

        JPanel actionPanel = new JPanel();
        actionPanel.setBackground(Color.WHITE);

        SpinnerModel model = new SpinnerNumberModel(1, 1, 99, 1);
        JSpinner jumlahSpinner = new JSpinner(model);
        jumlahSpinner.setPreferredSize(new Dimension(50, 30));
        actionPanel.add(jumlahSpinner);

        JButton beliBtn = new JButton("Beli");
        beliBtn.setFont(fontNormal);
        beliBtn.addActionListener(e -> {
            int jumlah = (int) jumlahSpinner.getValue();
            int total = (int) (barang.getHargaBeli() * jumlah);
            int konfirmasi = JOptionPane.showConfirmDialog(window,
                "Anda akan membeli " + jumlah + "x " + barang.getNama() + " seharga Rp" + total + "\nYakin ingin melanjutkan?",
                "Konfirmasi Pembelian", JOptionPane.YES_NO_OPTION);

            if (konfirmasi == JOptionPane.YES_OPTION) {
                if (pemain.getSaldo() >= total) {
                    pemain.kurangiSaldo(total);
                    rumah.addBarang(barang, jumlah);
                    JOptionPane.showMessageDialog(window, "Berhasil membeli " + jumlah + "x " + barang.getNama());
                    showBuyBarangScreen();
                } else {
                    JOptionPane.showMessageDialog(window, "Saldo tidak cukup!");
                }
            }

        });

        actionPanel.add(beliBtn);

        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.EAST);
        barangPanel.add(panel);
    }

    JScrollPane scroll = new JScrollPane(barangPanel);
    scroll.setBounds(100, 150, 800, 250);
    con.add(scroll);

    JButton kembali = new JButton("Kembali");
    kembali.setFont(fontNormal);
    kembali.setBounds(800, 420, 200, 60);
    kembali.addActionListener(e -> kembali());
    con.add(kembali);

    JTextArea infoArea = new JTextArea(pemain.tampilan());
    infoArea.setFont(fontNormal);
    infoArea.setEditable(false);
    infoArea.setBackground(Color.BLACK);
    infoArea.setForeground(Color.WHITE);
    infoArea.setLineWrap(true);
    JScrollPane infoScroll = new JScrollPane(infoArea);
    infoScroll.setBounds(50, 500, 950, 140);
    con.add(infoScroll);

    window.revalidate();
    window.repaint();
}


    private void tampilkanStok(Rumah rumah) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }


    public class TitleScreenHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GameScreen();
        }
    }

    public void homeChoice() {
        position = "rumah";
        pil1.setText("Beli Barang");
        pil2.setText("Beli Item");
        pil3.setText("Info Pemain");
        pil4.setText("Info Stok");
        pil5.setText("Berjualan");
    }

    private void handleMenu(int pilihan) {
        switch (pilihan) {
            case 1:
                showBuyBarangScreen();
                updatePlayerInfo();
                break;
            case 2:
                showBeliItemScreen();
                break;
            case 3:
                pemain.tampilan();
                break;
            case 4:
                tampilkanStok(rumah);
                break;
            case 5:
                showPilihLokasiScreen();
                break;
        }
    }

    public void showBeliItemScreen() {
        con.removeAll();

        JLabel title = new JLabel("Toko Item");
        title.setFont(fontJudul);
        title.setForeground(Color.WHITE);
        title.setBounds(220, 20, 600, 100);
        con.add(title);

        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setBackground(Color.WHITE);

        for (Item item : daftarItem) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            panel.setBackground(Color.WHITE);
            panel.setPreferredSize(new Dimension(750, 90));

            JPanel infoPanel = new JPanel(new GridLayout(2, 1));
            infoPanel.setBackground(Color.WHITE);
            infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel namaLabel = new JLabel(item.getNama());
            namaLabel.setFont(new Font("Arial", Font.BOLD, 18));

            JLabel infoLabel = new JLabel("Efek: " + item.getEfek() + "    Harga: Rp" + item.getHarga());
            infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            infoPanel.add(namaLabel);
            infoPanel.add(infoLabel);

            JButton beli = new JButton("Beli");
            beli.setFont(new Font("Arial", Font.BOLD, 16));
            beli.setPreferredSize(new Dimension(120, 50)); 
            beli.addActionListener(e -> {
                if (pemain.getSaldo() >= item.getHarga()) {
                    pemain.kurangiSaldo(item.getHarga());
                    pemain.tambahItem(item);
                    JOptionPane.showMessageDialog(window, "Berhasil membeli: " + item.getNama());
                    showBeliItemScreen();
                } else {
                    JOptionPane.showMessageDialog(window, "Saldo tidak cukup!");
                }
            });

            JPanel tombolPanel = new JPanel();
            tombolPanel.setBackground(Color.WHITE);
            tombolPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            tombolPanel.add(beli);

            panel.add(infoPanel, BorderLayout.CENTER);
            panel.add(tombolPanel, BorderLayout.EAST);
            itemPanel.add(panel);
        }

        JScrollPane scroll = new JScrollPane(itemPanel);
        scroll.setBounds(100, 150, 800, 250);
        con.add(scroll);

        JButton kembali = new JButton("Kembali");
        kembali.setFont(fontNormal);
        kembali.setBounds(800, 420, 200, 60);
        kembali.addActionListener(e -> kembali());
        con.add(kembali);

        JTextArea infoArea = new JTextArea(pemain.tampilan());
        infoArea.setFont(fontNormal);
        infoArea.setEditable(false);
        infoArea.setBackground(Color.BLACK);
        infoArea.setForeground(Color.WHITE);
        infoArea.setLineWrap(true);
        JScrollPane infoScroll = new JScrollPane(infoArea);
        infoScroll.setBounds(50, 500, 950, 140);
        con.add(infoScroll);

        window.revalidate();
        window.repaint();
    }

    private void kembali() {
        con.removeAll();
        GameScreen();
    }

    private void updatePlayerInfo() {
        String info = pemain.tampilan();
        JTextArea infoArea = (JTextArea) ((JScrollPane) panelPemain.getComponent(0)).getViewport().getView();
        infoArea.setText(info);
    }
    
    private void showPilihLokasiScreen() {
        ArrayList<Barang> stokBarang = rumah.getStokBarangList();

        if (stokBarang.isEmpty()) {
            JOptionPane.showMessageDialog(window, "Stok kosong! Tidak bisa berjualan.");
            return;
        }

        String[] pilihanBarang = stokBarang.stream()
            .map(b -> b.getNama() + " (Stok: " + rumah.getJumlah(b) + ")")
            .toArray(String[]::new);

        int pilihan = JOptionPane.showOptionDialog(window,
                "Pilih barang yang akan dijual:",
                "Pilih Barang",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                pilihanBarang,
                pilihanBarang[0]);

        if (pilihan == -1) return;

        Barang barangDipilih = stokBarang.get(pilihan);
        int stokTersedia = rumah.getJumlah(barangDipilih);
        int hargaSatuan = (int) rumah.getHargaJual(barangDipilih);

        String jumlahInput = JOptionPane.showInputDialog(window, "Pembeli ingin beli berapa? (Stok: " + stokTersedia + ")");
        if (jumlahInput == null) return;

        int jumlahBeli;
        try {
            jumlahBeli = Integer.parseInt(jumlahInput);
            if (jumlahBeli <= 0 || jumlahBeli > stokTersedia) {
                JOptionPane.showMessageDialog(window, "Jumlah tidak valid atau melebihi stok!");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(window, "Masukan tidak valid.");
            return;
        }

        String namaPembeli = JOptionPane.showInputDialog(window, "Masukkan nama pembeli:");
        if (namaPembeli == null || namaPembeli.trim().isEmpty()) return;

        Pembeli pembeli = generatePembeliAcak(namaPembeli);
        int hargaTawaranSatuan = pembeli.tawarHarga(hargaSatuan);
        int totalTawaran = hargaTawaranSatuan * jumlahBeli;

        int hasil = JOptionPane.showConfirmDialog(window,
            "Pembeli: " + pembeli.getNama() +
            "\nBarang: " + barangDipilih.getNama() +
            "\nJumlah: " + jumlahBeli +
            "\nHarga Satuan: Rp" + hargaSatuan +
            "\nTawaran per unit: Rp" + hargaTawaranSatuan +
            "\nTotal: Rp" + totalTawaran +
            "\n\nTerima tawaran dan jual barang ini?",
            "Tawar-Menawar", JOptionPane.YES_NO_OPTION);

        if (hasil == JOptionPane.YES_OPTION) {
            rumah.kurangiStok(barangDipilih, jumlahBeli);
            pemain.tambahSaldo(totalTawaran);
            JOptionPane.showMessageDialog(window, "Berhasil menjual " + jumlahBeli + "x " + barangDipilih.getNama());
        } else {
            JOptionPane.showMessageDialog(window, "Transaksi dibatalkan.");
        }

        updatePlayerInfo();
    }




private Pembeli generatePembeliAcak(String nama) {
    double chance = Math.random();
    if (chance < 0.1) {
        return new PembeliTajir(nama);
    } else if (chance < 0.6) {
        return new PembeliStandard(nama);
    } else {
        return new PembeliMiskin(nama);
    }
}



}
