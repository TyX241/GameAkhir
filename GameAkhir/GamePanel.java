package GameAkhir;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;

public class GamePanel {
    private DefaultTableModel invModel, cartModel;
    private JTable invTable, cartTable;
    private JSpinner qtySpinner;
    private JTextField priceField;
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
        namaPanel.setBounds(450, 300, 200, 25);
        namaPanel.setBackground(Color.WHITE);
        JTextField textField = new JTextField(10);
        namaPanel.setVisible(false);
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

        mainTextArea = new JTextArea("Rumah");
        mainTextArea.setBackground(Color.BLACK);
        mainTextArea.setForeground(Color.WHITE);
        mainTextArea.setFont(fontNormal);
        mainTextArea.setEditable(false);
        mainTextArea.setFocusable(false);
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




    public class TitleScreenHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (pemain.getNama() == null || pemain.getNama().isEmpty()) {
                namaPanel.setVisible(true);
                return;
            }
            GameScreen();
        }
    }

    public void homeChoice() {
        position = "rumah";
        pil1.setText("Beli Barang");
        pil2.setText("Beli Item");
        pil3.setText("Info Pemain");
        pil4.setText("Atur Stok");
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
                showManageInventoryDialog();
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
            JPanel tombolPanel = new JPanel();
            tombolPanel.setBackground(Color.WHITE);
            tombolPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            

        SpinnerModel model = new SpinnerNumberModel(1, 1, 99, 1);
        JSpinner jumlahSpinner = new JSpinner(model);
        jumlahSpinner.setPreferredSize(new Dimension(50, 30));
        tombolPanel.add(jumlahSpinner);

        JButton beli = new JButton("Beli");
        beli.setFont(new Font("Arial", Font.BOLD, 16));
        beli.setPreferredSize(new Dimension(120, 50)); 
        beli.addActionListener(e -> {
            int jumlahBeli = (int) jumlahSpinner.getValue();
            double totalHarga = item.getHarga() * jumlahBeli;
            if (pemain.getSaldo() >= totalHarga) {
                pemain.kurangiSaldo(totalHarga);
                pemain.tambahItem(item, jumlahBeli); // Tambah ke player
                JOptionPane.showMessageDialog(window, "Berhasil membeli: " + jumlahBeli + "x " + item.getNama());
                showBeliItemScreen();
            } else {
                JOptionPane.showMessageDialog(window, "Saldo tidak cukup!");
            }
        });
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

        JTextArea textArea = new JTextArea("Pilih barang yang akan dijual:");
        textArea.setPreferredSize(new Dimension(300, 100));  // Set desired size

        int pilihan = JOptionPane.showOptionDialog(
    window,
    textArea,                      // Use the larger component
    "Pilih Barang",
    JOptionPane.DEFAULT_OPTION,
    JOptionPane.QUESTION_MESSAGE,
    null,
    pilihanBarang,
    pilihanBarang[0]
);

        if (pilihan == -1) return;

        Barang barangDipilih = stokBarang.get(pilihan);
        int stokTersedia = rumah.getJumlah(barangDipilih);
        int hargaSatuan = (int) rumah.getHargaJual(barangDipilih);
        textArea.setText("Harga Satuan: Rp" + hargaSatuan + "\nStok Tersedia: " + stokTersedia);
        String jumlahInput = JOptionPane.showInputDialog(window,textArea, "Pembeli ingin beli berapa? (Stok: " + stokTersedia + ")");
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
    private void tampilkanStok(Rumah rumah) {
    ArrayList<Barang> stokRumah = rumah.getStokBarangList();
    if (stokRumah.isEmpty()) {
        JOptionPane.showMessageDialog(window, "Stok rumah kosong!");
        return;
    }

    String[] pilihanBarang = stokRumah.stream()
        .map(b -> b.getNama() + " (Stok: " + rumah.getJumlah(b) + ")")
        .toArray(String[]::new);

    int pilihan = JOptionPane.showOptionDialog(
        window,
        "Pilih barang yang ingin dipindahkan ke kendaraan:",
        "Pindahkan ke Kendaraan",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        pilihanBarang,
        pilihanBarang[0]
    );

    if (pilihan == -1) return;

    Barang barangDipilih = stokRumah.get(pilihan);
    int stokTersedia = rumah.getJumlah(barangDipilih);

    String jumlahInput = JOptionPane.showInputDialog(window,
        "Masukkan jumlah yang ingin dipindahkan (Stok: " + stokTersedia + "):");
    if (jumlahInput == null) return;

    int jumlahPindah;
    try {
        jumlahPindah = Integer.parseInt(jumlahInput);
        if (jumlahPindah <= 0 || jumlahPindah > stokTersedia) {
            JOptionPane.showMessageDialog(window, "Jumlah tidak valid atau melebihi stok!");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(window, "Masukan tidak valid.");
        return;
    }

    // Kapasitas kendaraan
    int totalDiKendaraan = kendaraan.getInventori().values().stream().mapToInt(Integer::intValue).sum();
    if (totalDiKendaraan + jumlahPindah > kendaraan.getKapasitas()) {
        JOptionPane.showMessageDialog(window, "Kapasitas kendaraan tidak cukup!");
        return;
    }

    rumah.kurangiStok(barangDipilih, jumlahPindah);
    kendaraan.tambahBarang(barangDipilih, jumlahPindah);

    JOptionPane.showMessageDialog(window, "Berhasil memindahkan " + jumlahPindah + "x " + barangDipilih.getNama() + " ke kendaraan.");
}
private void showManageInventoryDialog() {
        JDialog inventoryDialog = new JDialog(window, "Manajemen Inventaris", true); // Modal dialog
        inventoryDialog.setSize(900, 600);
        inventoryDialog.setLocationRelativeTo(window);
        inventoryDialog.setLayout(new BorderLayout(10, 10));

        // Panel Atas: Info Pemain & Kendaraan
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel saldoLabel = new JLabel("Saldo: Rp" + String.format("%,.0f", pemain.getSaldo()));
        // Hitung kapasitas terpakai langsung saat membuat label
        JLabel kapasitasLabel = new JLabel("Kapasitas Kendaraan: " + 
                                         pemain.getKendaraan().getInventori().values().stream().mapToInt(Integer::intValue).sum() + 
                                         "/" + pemain.getKendaraan().getKapasitas());
        topPanel.add(saldoLabel);
        topPanel.add(new JLabel("   |   ")); // Separator
        topPanel.add(kapasitasLabel);
        inventoryDialog.add(topPanel, BorderLayout.NORTH);

        // Panel Utama: Tiga Kolom
        JPanel mainContentPanel = new JPanel(new GridLayout(1, 3, 10, 0)); // 1 baris, 3 kolom

        // --- KOLOM 1: STOK RUMAH ---
        JPanel panelStokRumah = new JPanel(new BorderLayout(0, 5));
        panelStokRumah.setBorder(BorderFactory.createTitledBorder("Stok di Rumah"));

        DefaultTableModel modelStokRumah = new DefaultTableModel(new String[]{"Nama Barang", "Jumlah", "Harga Beli"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabel tidak bisa diedit langsung
            }
        };
        JTable tabelStokRumah = new JTable(modelStokRumah);
        tabelStokRumah.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Isi tabel stok rumah
        Runnable populateRumahTable = () -> {
            modelStokRumah.setRowCount(0); // Bersihkan tabel
            for (Barang barang : rumah.getStokBarangList()) {
                modelStokRumah.addRow(new Object[]{
                        barang.getNama(),
                        rumah.getJumlah(barang),
                        String.format("%,.0f", barang.getHargaBeli())
                });
            }
        };
        populateRumahTable.run();
        panelStokRumah.add(new JScrollPane(tabelStokRumah), BorderLayout.CENTER);

        // --- KOLOM 2: KONTROL AKSI ---
        JPanel panelKontrol = new JPanel();
        panelKontrol.setLayout(new BoxLayout(panelKontrol, BoxLayout.Y_AXIS));
        panelKontrol.setBorder(BorderFactory.createTitledBorder("Aksi"));

        JLabel labelJumlah = new JLabel("Jumlah Pindah:");
        SpinnerModel spinnerModelJumlah = new SpinnerNumberModel(1, 1, 1, 1); // Min, current, max, step
        JSpinner spinnerJumlah = new JSpinner(spinnerModelJumlah);
        spinnerJumlah.setEnabled(false);

        JLabel labelHargaJual = new JLabel("Harga Jual Diinginkan (opsional):");

        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false); // Tidak pakai titik/koma pemisah ribuan saat input
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false); // Tidak izinkan input non-numerik
        formatter.setCommitsOnValidEdit(true); // Langsung update saat valid
        JFormattedTextField inputHargaJual = new JFormattedTextField(formatter);
        inputHargaJual.setColumns(10);
        inputHargaJual.setEnabled(false);


        JButton btnPindahKeKendaraan = new JButton("Pindah ke Kendaraan");
        btnPindahKeKendaraan.setEnabled(false);
        JButton btnUpgradeKendaraan = new JButton("Upgrade Kendaraan ("+kendaraan.getBiayaUpgrade()+")");

        panelKontrol.add(labelJumlah);
        panelKontrol.add(spinnerJumlah);
        panelKontrol.add(Box.createRigidArea(new Dimension(0, 10)));
        panelKontrol.add(labelHargaJual);
        panelKontrol.add(inputHargaJual);
        panelKontrol.add(Box.createRigidArea(new Dimension(0, 20)));
        panelKontrol.add(btnPindahKeKendaraan);
        panelKontrol.add(Box.createRigidArea(new Dimension(0, 10)));
        panelKontrol.add(btnUpgradeKendaraan);
        
        // --- KOLOM 3: STOK KENDARAAN ---
        JPanel panelStokKendaraan = new JPanel(new BorderLayout(0, 5));
        panelStokKendaraan.setBorder(BorderFactory.createTitledBorder("Stok di Kendaraan"));
        DefaultTableModel modelStokKendaraan = new DefaultTableModel(new String[]{"Nama Barang", "Jumlah", "Harga Jual"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabel tidak bisa diedit langsung
            }
        };
        JTable tabelStokKendaraan = new JTable(modelStokKendaraan);
        tabelStokKendaraan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Isi tabel stok kendaraan
        Runnable populateKendaraanTable = () -> {
            modelStokKendaraan.setRowCount(0);
            for (Map.Entry<Barang, Integer> entry : pemain.getKendaraan().getInventori().entrySet()) {
                Barang barangDiKendaraan = entry.getKey();
                modelStokKendaraan.addRow(new Object[]{
                        barangDiKendaraan.getNama(),
                        entry.getValue(),
                        String.format("%,.0f", barangDiKendaraan.getHargaJual()) // Menampilkan harga jual dari objek Barang itu sendiri
                });
            }
        };
        populateKendaraanTable.run();

        JButton btnKembalikanKeRumah = new JButton("Kembalikan ke Rumah");
        btnKembalikanKeRumah.setEnabled(false);
        panelStokKendaraan.add(new JScrollPane(tabelStokKendaraan), BorderLayout.CENTER);
        panelStokKendaraan.add(btnKembalikanKeRumah, BorderLayout.SOUTH);


        // Tambahkan kolom ke panel utama
        mainContentPanel.add(panelStokRumah);
        mainContentPanel.add(panelKontrol);
        mainContentPanel.add(panelStokKendaraan);
        inventoryDialog.add(mainContentPanel, BorderLayout.CENTER);

        // Panel Bawah: Tombol Tutup
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnTutup = new JButton("Tutup");
        btnTutup.addActionListener(e -> inventoryDialog.dispose());
        bottomPanel.add(btnTutup);
        inventoryDialog.add(bottomPanel, BorderLayout.SOUTH);

        // Listener untuk tabel stok rumah
        tabelStokRumah.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelStokRumah.getSelectedRow() != -1) {
                int selectedRow = tabelStokRumah.getSelectedRow();
                Barang barangTerpilih = rumah.getStokBarangList().get(selectedRow);
                int stokRumah = rumah.getJumlah(barangTerpilih);
                
                spinnerJumlah.setEnabled(true);
                ((SpinnerNumberModel) spinnerModelJumlah).setMaximum(stokRumah);
                ((SpinnerNumberModel) spinnerModelJumlah).setValue(Math.min(1, stokRumah)); // Set default 1 atau max stok jika < 1

                inputHargaJual.setEnabled(true);
                inputHargaJual.setValue((int) barangTerpilih.getHargaJual()); // Default ke harga jual standar
                
                btnPindahKeKendaraan.setEnabled(true);
            } else {
                spinnerJumlah.setEnabled(false);
                inputHargaJual.setEnabled(false);
                inputHargaJual.setValue(null);
                btnPindahKeKendaraan.setEnabled(false);
            }
        });

        // Listener untuk tabel stok kendaraan
        tabelStokKendaraan.getSelectionModel().addListSelectionListener(e -> {
            btnKembalikanKeRumah.setEnabled(tabelStokKendaraan.getSelectedRow() != -1);
        });


        // Aksi Tombol Pindah ke Kendaraan
        btnPindahKeKendaraan.addActionListener(e -> {
            int selectedRow = tabelStokRumah.getSelectedRow();
            if (selectedRow == -1) return;

            Barang barangAsliRumah = rumah.getStokBarangList().get(selectedRow);
            int jumlahPindah = (Integer) spinnerJumlah.getValue();
            
            Object hargaJualObj = inputHargaJual.getValue();
            double hargaJualKustom = (hargaJualObj != null) ? ((Number) hargaJualObj).doubleValue() : barangAsliRumah.getHargaJual();


            if (jumlahPindah <= 0) {
                JOptionPane.showMessageDialog(inventoryDialog, "Jumlah pindah harus lebih dari 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int kapasitasTerpakaiSekarang = pemain.getKendaraan().getInventori().values().stream().mapToInt(Integer::intValue).sum();
            if (kapasitasTerpakaiSekarang + jumlahPindah > pemain.getKendaraan().getKapasitas()) {
                JOptionPane.showMessageDialog(inventoryDialog, "Kapasitas kendaraan tidak cukup!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Buat objek Barang baru jika harga jualnya diubah, atau gunakan yg asli jika sama
            Barang barangUntukKendaraan;
            if (hargaJualKustom != barangAsliRumah.getHargaJual()) {
                barangUntukKendaraan = new Barang(barangAsliRumah.getNama(), barangAsliRumah.getHargaBeli(), hargaJualKustom);
            } else {
                // Jika harga jual tidak diubah, kita perlu memastikan bahwa objek Barang yang sama
                // dari daftarBarang utama (supplier) digunakan, atau setidaknya objek dengan harga jual default.
                // Untuk kesederhanaan, kita bisa tetap membuat objek baru, atau mencari referensi objek asli.
                // Mari kita buat objek baru untuk konsistensi, atau cari dari daftarBarang supplier jika ada.
                // Untuk contoh ini, kita asumsikan barangAsliRumah sudah memiliki harga jual default yang benar.
                barangUntukKendaraan = barangAsliRumah; 
                // Jika ingin lebih ketat, cari di daftarBarang global dan gunakan referensi itu
                // agar jika ada perubahan harga jual global, tercermin. Tapi ini jadi kompleks.
            }


             rumah.kurangiStok(barangAsliRumah, jumlahPindah);
            pemain.getKendaraan().tambahBarang(barangUntukKendaraan, jumlahPindah);

            // Refresh tampilan
            populateRumahTable.run();
            populateKendaraanTable.run();
            // Update kapasitasLabel dengan menghitung ulang
            kapasitasLabel.setText("Kapasitas Kendaraan: " + 
                                   pemain.getKendaraan().getInventori().values().stream().mapToInt(Integer::intValue).sum() + 
                                   "/" + pemain.getKendaraan().getKapasitas());
            saldoLabel.setText("Saldo: Rp" + String.format("%,.0f", pemain.getSaldo())); // Update saldo juga jika berubah
            
            // Reset Pilihan
            tabelStokRumah.clearSelection();
            if (inputHargaJual.isEnabled()) { // Cek dulu apakah enabled
                 inputHargaJual.setValue(null);
            }
        });

        // Aksi Tombol Kembalikan ke Rumah
        btnKembalikanKeRumah.addActionListener(e -> {
            int selectedRow = tabelStokKendaraan.getSelectedRow();
            if (selectedRow == -1) return;

            // Mendapatkan barang dari model tabel kendaraan
            String namaBarangDiKendaraan = (String) modelStokKendaraan.getValueAt(selectedRow, 0);
            int jumlahDiKendaraan = Integer.parseInt(modelStokKendaraan.getValueAt(selectedRow, 1).toString());
            // double hargaJualDiKendaraan = Double.parseDouble(modelStokKendaraan.getValueAt(selectedRow, 2).toString().replace(",", ""));

            Barang barangYangDikembalikan = null;
            // Cari objek Barang yang sesuai di inventori kendaraan
            for(Barang b : pemain.getKendaraan().getInventori().keySet()){
                if(b.getNama().equals(namaBarangDiKendaraan) && pemain.getKendaraan().getJumlah(b) == jumlahDiKendaraan){
                    // Perlu cara lebih baik untuk mencocokkan jika ada barang sama dengan harga jual beda
                    // Untuk saat ini, kita ambil yang pertama cocok berdasarkan nama dan jumlah (kurang ideal jika ada duplikat nama dengan harga jual beda)
                     barangYangDikembalikan = b;
                     break;
                }
            }
            // Fallback jika tidak ketemu dengan cara di atas (misal karena harga jual beda membuat objek beda)
            if (barangYangDikembalikan == null) {
                 for(Barang b : pemain.getKendaraan().getInventori().keySet()){
                    if(b.getNama().equals(namaBarangDiKendaraan)){
                         barangYangDikembalikan = b; // Ambil saja berdasarkan nama
                         break;
                    }
                }
            }


            if (barangYangDikembalikan == null) {
                 JOptionPane.showMessageDialog(inventoryDialog, "Error: Barang tidak ditemukan di kendaraan untuk dikembalikan.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int jumlahUntukDikembalikan = pemain.getKendaraan().getJumlah(barangYangDikembalikan); // Kembalikan semua stok barang tsb

            // Cari barang asli dari supplier atau daftarBarang utama untuk ditambahkan ke rumah
            // agar harga belinya konsisten dan harga jual defaultnya kembali.
            Barang barangAsliUntukRumah = null;
            for (Barang barangMaster : daftarBarang) { // Asumsi 'daftarBarang' adalah list dari Supplier
                if (barangMaster.getNama().equals(barangYangDikembalikan.getNama())) {
                    barangAsliUntukRumah = barangMaster;
                    break;
                }
            }
            // Jika tidak ada di daftar supplier (misal barang custom), buat objek baru dengan harga default
            if (barangAsliUntukRumah == null) {
                 // Mencari di rumah dulu, mungkin ada instance dengan harga jual default
                boolean foundInHouse = false;
                for(Barang br : rumah.getStokBarangList()){
                    if(br.getNama().equals(barangYangDikembalikan.getNama()) && br.getHargaJual() == new Barang(br.getNama(), 0,0).getHargaJual() /* Cek harga jual default */ ){
                        barangAsliUntukRumah = br;
                        foundInHouse = true;
                        break;
                    }
                }
                if(!foundInHouse){
                    // Jika benar-benar tidak ada, buat objek baru dengan harga beli dan jual yang "wajar"
                    // Atau, idealnya, kita harus punya cara untuk mendapatkan harga beli asli.
                    // Untuk sekarang, kita bisa gunakan harga beli dari barang di kendaraan (jika ada)
                    // atau default ke 0 jika tidak tahu. Ini bagian yang agak tricky tanpa referensi master.
                    double hargaBeliAsli = barangYangDikembalikan.getHargaBeli(); // Ambil dari barang di kendaraan
                     // Ambil harga jual standar dari konstruktor Barang baru untuk nama yg sama
                    Barang tempRefHargaJual = new Barang(barangYangDikembalikan.getNama(), 0, 0); 
                    // Cari dari daftarBarang (master list dari supplier) untuk harga jual default
                    for (Barang master : daftarBarang) {
                        if (master.getNama().equals(barangYangDikembalikan.getNama())) {
                            tempRefHargaJual = master;
                            break;
                        }
                    }

                    barangAsliUntukRumah = new Barang(barangYangDikembalikan.getNama(), hargaBeliAsli, tempRefHargaJual.getHargaJual());
                }
            }


            pemain.getKendaraan().kurangiBarang(barangYangDikembalikan, jumlahUntukDikembalikan);
            rumah.addBarang(barangAsliUntukRumah, jumlahUntukDikembalikan);

            // Refresh tampilan
            populateRumahTable.run();
            populateKendaraanTable.run();
            // Update kapasitasLabel dengan menghitung ulang
            kapasitasLabel.setText("Kapasitas Kendaraan: " + 
                                   pemain.getKendaraan().getInventori().values().stream().mapToInt(Integer::intValue).sum() + 
                                   "/" + pemain.getKendaraan().getKapasitas());
            saldoLabel.setText("Saldo: Rp" + String.format("%,.0f", pemain.getSaldo())); 

            // Reset Pilihan
            tabelStokKendaraan.clearSelection();
        });
        
        // Aksi Tombol Upgrade Kendaraan (Placeholder)
        btnUpgradeKendaraan.addActionListener(e -> {
            // Contoh implementasi (jika ada perubahan kapasitas atau saldo)
            
            if(kendaraan.getlevel() <= 5) {
            if (pemain.getSaldo() >= kendaraan.getBiayaUpgrade()) {
                pemain.kurangiSaldo(kendaraan.getBiayaUpgrade());
                kendaraan.upgrade();
                btnUpgradeKendaraan.setText("Upgrade Kendaraan (" + kendaraan.getBiayaUpgrade() + ")"); // Update tombol dengan biaya upgrade baru
                btnUpgradeKendaraan.repaint(); 
                // Anda perlu method setKapasitas di Kendaraan.java
                // Misal: kendaraan.upgradeKapasitas(tambahanKapasitas);
                // Untuk contoh ini, kita asumsikan ada setter:
                // pemain.getKendaraan().setKapasitas(pemain.getKendaraan().getKapasitas() + tambahanKapasitas); // Jika setKapasitas ada
                // Jika tidak ada, Anda mungkin perlu membuat method baru di Kendaraan
                // atau memodifikasi kapasitas langsung (tidak ideal)
                // Untuk sementara, kita tampilkan pesan saja dan update label secara manual jika ada setter.

                JOptionPane.showMessageDialog(inventoryDialog, "Kendaraan berhasil di-upgrade ke level "+kendaraan.getlevel());

                // Update label (jika kapasitas atau saldo benar-benar berubah)
                kapasitasLabel.setText("Kapasitas Kendaraan: " + 
                                       pemain.getKendaraan().getInventori().values().stream().mapToInt(Integer::intValue).sum() + 
                                       "/" + pemain.getKendaraan().getKapasitas()); // Asumsi kapasitas.getKapasitas() sudah terupdate
                saldoLabel.setText("Saldo: Rp" + String.format("%,.0f", pemain.getSaldo()));
            } else {
                JOptionPane.showMessageDialog(inventoryDialog, "Saldo tidak cukup untuk upgrade.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
            // JOptionPane.showMessageDialog(inventoryDialog, "Fitur upgrade kendaraan belum diimplementasikan sepenuhnya.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });


        inventoryDialog.pack(); // Sesuaikan ukuran dialog dengan isinya
        inventoryDialog.setSize(Math.max(inventoryDialog.getWidth(), 900), Math.max(inventoryDialog.getHeight(), 600)); // Ensure minimum size
        inventoryDialog.setVisible(true);
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
