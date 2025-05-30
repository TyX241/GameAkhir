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
    JLabel gambarPembeliLabelDiLayar;
    JLabel gambarPenjual;
    JLabel gambarMC;
    JTextArea dialogPembeliLabelDiLayar;
    JLabel kesabaranPembeliLabelDiLayar;
    JTextArea infoBarangDitawarAreaDiLayar;
    JFormattedTextField inputHargaPlayerDiLayar;
    JButton btnTawarDiLayar, btnJualDiLayar, btnBatalJualDiLayar;
    JTextArea inventarisKendaraanAreaDiLayar;
    private JLabel backgroundLabel;
    private Barang barangYangDipilihPembeli = null;
    private Pembeli pembeliSaatIni = null;
    private String kotaTransaksiSaatIni = null;
    private int hargaTawaranAwalPembeli = 0;
    
    private JLabel labelDialogPembeli;
    private JFormattedTextField inputHargaPlayer;
    private JLabel labelKesabaranPembeli;
    private JButton btnTawarKePembeli, btnJualKePembeli; // Tambahkan btnJualKePembeli
    private JTextArea infoBarangKendaraanTextArea; // Untuk menampilkan barang di kendaraan
    String nama;
    JLayeredPane layeredPane;
    Player pemain = new Player("Abi");
    Rumah rumah = new Rumah();
    Kendaraan kendaraan = new Kendaraan("Trek", 5, 1);
     // Inisialisasi inventori kendaraan
    ArrayList<Barang> daftarBarang = new ArrayList<>();
    ArrayList<Item> daftarItem = new ArrayList<>();
    Peta peta = new Peta();
    TokoItem tokoItem = new TokoItem(daftarItem, pemain, rumah, kendaraan);
    Supplier supplier = new Supplier(daftarBarang, rumah, pemain, this);

    JFrame window;
    JLayeredPane con;
    JPanel namaPanel, judulPanel, tombolStartPanel, mainTextPanel, tombolPilihanPanel, panelPemain;
    JLabel judulLabel;
    JButton tombolStart, pil1, pil2, pil3, pil4, pil5;
    
     JTextField inputNamaPemainField;
    JPanel namaPanel_LayarAwal; // Direname dari namaPanel
    JPanel judulPanel_LayarAwal;
    JButton tombolStart_LayarAwal;
    JPanel tombolStartPanel_LayarAwal;
    JPanel panelPemain_GameNormal; // Direname dari panelPemain
    JTextArea infoAreaPemain_GameNormal;
    JLabel saldoPemainLabel_ModeJual; // Tambahan untuk saldo di layar jual
    
    // Komponen UI Panel Kota (di panelKotaSaatIni)
    JLabel namaKotaLabel_Kota;
    JButton btnCariPembeli_Kota;
    JButton btnKembaliKePeta_Kota;
    // Komponen UI Game Normal (di panelGameNormal yang sudah Anda deklarasikan)

    
    JPanel mainTextPanel_GameNormal; // Direname dari mainTextPanel
    JTextArea mainTextArea_GameNormal; // Direname dari mainTextArea
    JPanel tombolPilihanPanel_GameNormal; // Direname dari tombolPilihanPanel
    JButton pil1_GameNormal, pil2_GameNormal, pil3_GameNormal, pil4_GameNormal, pil5_GameNormal; // 
    Font fontJudul = new Font("Times New Roman", Font.PLAIN, 90);
    Font fontNormal = new Font("Times New Roman", Font.PLAIN, 20);
    JTextArea mainTextArea;
    String position;
    private ArrayList<JSpinner> spinners;

    public GamePanel() {
        kendaraan.tambahBarang(new Barang("Pensil", 10000, 10000), 5);
        pemain.setKendaraan(kendaraan);
        window = new JFrame();
        window.setSize(1280, 720);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);
        window.setLayout(null);
        window.setLocationRelativeTo(null);
        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        window.setContentPane(layeredPane);
        con = layeredPane;

        backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, 1280, 720);
        ImageIcon icon = new ImageIcon(getClass().getResource("/GameAkhir/Asset/Menu.jpeg")); // Ganti sesuai nama file default kamu
        Image img = icon.getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(img));
        con.add(backgroundLabel, Integer.valueOf(0));

        namaPanel = new JPanel();
        namaPanel.setBounds(550, 300, 200, 25);
        namaPanel.setBackground(Color.WHITE);
        JTextField textField = new JTextField(10);
        namaPanel.setVisible(false);
        namaPanel.add(new JLabel("Nama:"));
        namaPanel.add(textField);
        con.add(namaPanel, Integer.valueOf(1));
        textField.addActionListener(e -> {
            String nama = textField.getText();
            pemain.setNama(nama);
            namaPanel.setVisible(false);
        });

        judulPanel = new JPanel();
        judulPanel.setBounds(320, 100, 600, 150);
        judulPanel.setBackground(Color.BLACK);
        judulLabel = new JLabel("SHOP");
        judulLabel.setForeground(Color.WHITE);
        judulLabel.setFont(fontJudul);

        tombolStartPanel = new JPanel();
        tombolStartPanel.setBounds(520, 400, 200, 100);
        tombolStartPanel.setBackground(Color.BLACK);

        tombolStart = new JButton("START");
        tombolStart.setBackground(Color.black);
        tombolStart.setForeground(Color.white);
        tombolStart.setFont(fontNormal);
        tombolStart.addActionListener(new TitleScreenHandler());

        judulPanel.add(judulLabel);
        tombolStartPanel.add(tombolStart);
        con.add(judulPanel, Integer.valueOf(1));
        con.add(tombolStartPanel, Integer.valueOf(1));

        daftarItem.add(new Item("Hipnotis", "Meningkatkan peluang pembeli setuju", 10000, 1, 0));
        daftarItem.add(new Item("Promo", "Meningkatkan minat beli", 7500, 1, 0));
        

        window.setVisible(true);
    }

    public void GameScreen() {
        con.removeAll();
        setBackgroundImage("M");
        con.add(backgroundLabel, Integer.valueOf(0));

        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(100, 100, 600, 250);
        mainTextPanel.setBackground(Color.BLUE);
        mainTextPanel.setVisible(false);
        con.add(mainTextPanel, Integer.valueOf(2));

        gambarMC();

        mainTextArea = new JTextArea("Rumah");
        mainTextArea.setBackground(Color.BLACK);
        mainTextArea.setForeground(Color.WHITE);
        mainTextArea.setFont(fontNormal);
        mainTextArea.setEditable(false);
        mainTextArea.setFocusable(false);
        mainTextArea.setLineWrap(true);
        mainTextPanel.add(mainTextArea);

        tombolPilihanPanel = new JPanel();
        tombolPilihanPanel.setBounds(850, 100, 200, 550);
        tombolPilihanPanel.setBackground(Color.BLACK);
        tombolPilihanPanel.setLayout(new GridLayout(5, 1));
        con.add(tombolPilihanPanel, Integer.valueOf(2));

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
        con.add(panelPemain, Integer.valueOf(2));

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
    public void gambarMC(){
         gambarMC = new JLabel();
        gambarMC.setHorizontalAlignment(SwingConstants.CENTER);
        String pathMC = "Asset/MC.png";
        gambarMC.setBounds(-100, 100, 900, 900);
            ImageIcon iconMC = new ImageIcon(getClass().getResource("/GameAkhir/" + pathMC));
            Image imgMC = iconMC.getImage().getScaledInstance(gambarMC.getWidth(), gambarMC.getHeight(), Image.SCALE_SMOOTH);
            gambarMC.setIcon(new ImageIcon(imgMC));
            gambarMC.setText("");
        con.add(gambarMC, Integer.valueOf(1));
    }

private void showBuyBarangScreen() {
    con.removeAll();
    gambarPenjual = new JLabel();
    gambarPenjual.setHorizontalAlignment(SwingConstants.CENTER);
    String pathPenjual = "Asset/Trader.png";
    gambarPenjual.setBounds(650, 100, 720, 720);
    try {
        ImageIcon iconPenjual = new ImageIcon(getClass().getResource("/GameAkhir/" + pathPenjual));
        Image imgPenjual = iconPenjual.getImage().getScaledInstance(gambarPenjual.getWidth(), gambarPenjual.getHeight(), Image.SCALE_SMOOTH);
        gambarPenjual.setIcon(new ImageIcon(imgPenjual));
        gambarPenjual.setText("");
    } catch (Exception ex) {
        gambarPenjual.setIcon(null);
        gambarPenjual.setText("Gbr: " + pembeliSaatIni.getClass().getSimpleName().substring(0,Math.min(7,pembeliSaatIni.getClass().getSimpleName().length())));
        gambarPenjual.setForeground(Color.WHITE);
        System.err.println("Err gbr pembeli: " + "/GameAkhir/" + pathPenjual + " - " + ex.getMessage());
    }
    con.add(gambarPenjual, Integer.valueOf(1));
    setBackgroundImage("S");
    con.add(backgroundLabel, Integer.valueOf(0));

    JLabel title = new JLabel("Toko Barang / Supplier");
    title.setFont(fontJudul);
    title.setForeground(Color.WHITE);
    title.setBounds(220, 20, 600, 100);
    con.add(title, Integer.valueOf(1));

    JPanel barangPanel = new JPanel();
    barangPanel.setLayout(new BoxLayout(barangPanel, BoxLayout.Y_AXIS));
    barangPanel.setBackground(Color.WHITE);

    for (Barang barang : daftarBarang) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(450, 90));

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
    scroll.setBounds(100, 150, 600, 250);
    con.add(scroll, Integer.valueOf(2));

    JButton kembali = new JButton("Kembali");
    kembali.setFont(fontNormal);
    kembali.setBounds(800, 420, 200, 60);
    kembali.addActionListener(e -> kembali());
    con.add(kembali, Integer.valueOf(2));

    JTextArea infoArea = new JTextArea(pemain.tampilan());
    infoArea.setFont(fontNormal);
    infoArea.setEditable(false);
    infoArea.setBackground(Color.BLACK);
    infoArea.setForeground(Color.WHITE);
    infoArea.setLineWrap(true);
    JScrollPane infoScroll = new JScrollPane(infoArea);
    infoScroll.setBounds(50, 500, 950, 140);
    con.add(infoScroll, Integer.valueOf(2));

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
                pilihLokasi();
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
        if (panelPemain != null && panelPemain.getComponentCount() > 0) {
            Component firstChild = panelPemain.getComponent(0);
            if (firstChild instanceof JScrollPane) {
                Component view = ((JScrollPane) firstChild).getViewport().getView();
                if (view instanceof JTextArea) {
                    ((JTextArea) view).setText(pemain.tampilan());
                     if (((JTextArea) view).getDocument().getLength() > 0) {
                        ((JTextArea) view).setCaretPosition(0);
                    }
                }
            }
        } else {
            System.out.println("updatePlayerInfo: panelPemain belum siap untuk diupdate (mungkin karena layar GameScreen tidak aktif).");
        }
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
    public void pilihLokasi(){
        if (kendaraan.getInventori().isEmpty()) {
            JOptionPane.showMessageDialog(window, "Stok kendaraan kosong! Tidak bisa berjualan.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        con.removeAll();
        //setBackgroundImage("Peta.jpg"); // Atur background peta
        con.add(backgroundLabel, Integer.valueOf(0));

        // Tombol kembali ke GameScreen (base)
        JButton btnKembaliBase = new JButton("Kembali ke Base");
        btnKembaliBase.setFont(fontNormal.deriveFont(18f));
        btnKembaliBase.setBounds(50, 620, 220, 50); // Sesuaikan posisi
        btnKembaliBase.setBackground(new Color(128,128,128));
        btnKembaliBase.setForeground(Color.WHITE);
        btnKembaliBase.addActionListener(e -> GameScreen()); // Kembali ke GameScreen
        con.add(btnKembaliBase, Integer.valueOf(1));


        JLabel titleLokasi = new JLabel("Pilih Lokasi Berjualan");
        titleLokasi.setFont(fontJudul.deriveFont(70f));
        titleLokasi.setForeground(new Color(255, 200, 0));
        titleLokasi.setBounds(0, 80, 1280, 100);
        titleLokasi.setHorizontalAlignment(SwingConstants.CENTER);
        con.add(titleLokasi, Integer.valueOf(1));

        JButton kA = new JButton("Kota A");
        kA.setFont(fontNormal.deriveFont(Font.BOLD, 26f)); // Font lebih besar
        kA.setBounds(440, 250, 400, 80); // Ukuran lebih besar
        kA.setBackground(new Color(135, 206, 250)); // Warna berbeda
        kA.addActionListener(e -> pindahLokasi(1)); // Memanggil method pindahLokasi Anda
        con.add(kA, Integer.valueOf(1));

        JButton kB = new JButton("Kota B");
        kB.setFont(fontNormal.deriveFont(Font.BOLD, 26f));
        kB.setBounds(440, 360, 400, 80);
        kB.setBackground(new Color(255, 182, 193)); // Warna berbeda
        kB.addActionListener(e -> pindahLokasi(2)); // Memanggil method pindahLokasi Anda
        con.add(kB, Integer.valueOf(1));
        
        // Hapus buttonKembali() dari sini karena sudah ada btnKembaliBase
        // buttonKembali(); 

        window.revalidate();
        window.repaint();
    }
    public void pindahLokasi(int pil){
        switch(pil){
            case 1:
                kotaA();
                break;
            case 2:   
                kotaB();
                break; 
        }
    }
    public void buttonKembali(){
        JButton kembali = new JButton("Kembali");
        kembali.setFont(fontNormal);
        kembali.setBounds(800, 250, 200, 60);
        kembali.addActionListener(e -> kembali());
        con.add(kembali, Integer.valueOf(1));
    }
    public void buttonKembaliKota(){
        JButton kembali = new JButton("Kembali");
        kembali.setFont(fontNormal);
        kembali.setBounds(800, 250, 200, 60);
        kembali.addActionListener(e -> kembaliMenu());
        con.add(kembali, Integer.valueOf(1));
    }
    public void kembaliMenu(){
        con.removeAll();
        pilihLokasi();
    }
    public void AturBuff(){

    }

   public void kotaB(){
        this.kotaTransaksiSaatIni = "Kota B"; // SET KOTA TRANSAKSI
        con.removeAll();
        setBackgroundImage("B");
        con.add(backgroundLabel, Integer.valueOf(0));
        buttonKembaliKota();

        JLabel judulKotaB = new JLabel("Distrik Bisnis Kota B");
        // ... styling judulKotaB ...
        judulKotaB.setFont(fontJudul.deriveFont(60f));
        judulKotaB.setForeground(Color.LIGHT_GRAY);
        judulKotaB.setHorizontalAlignment(SwingConstants.CENTER);
        judulKotaB.setBounds(0, 50, 1280, 100);
        con.add(judulKotaB, Integer.valueOf(1));


        JButton btnCariPembeliDiKotaB = new JButton("Mulai Berjualan di Sini");
        // ... styling btnCariPembeliDiKotaB ...
        btnCariPembeliDiKotaB.setFont(fontNormal.deriveFont(Font.BOLD, 22f));
        btnCariPembeliDiKotaB.setBounds(490, 250, 300, 70);
        btnCariPembeliDiKotaB.setBackground(new Color(50,50,150));
        btnCariPembeliDiKotaB.setForeground(Color.WHITE);
        btnCariPembeliDiKotaB.addActionListener(e -> {
            mulaiSesiBerjualan();
        });
        con.add(btnCariPembeliDiKotaB, Integer.valueOf(1));

        window.revalidate();
        window.repaint();
    }
    public void setBackgroundImage(String namaKota) {
    String path = "";
    switch (namaKota) {
        case "A":
            path = "Asset/kotaA.png";
            break;
        case "B":
            path = "Asset/kotaB.jpeg";
            break;
        case "M":
            path = "Asset/Menu.jpeg";
            break;
        case "S":
            path = "Asset/Supplier.png";
            break;
        default:
            path = "Asset/default.jpg";
    }
    ImageIcon icon = new ImageIcon(getClass().getResource(path));
    Image img = icon.getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
    backgroundLabel.setIcon(new ImageIcon(img));
    backgroundLabel.setBounds(0, 0, 1280, 720);
    window.repaint();
}

 private void mulaiSesiBerjualan() {
    if (kendaraan.getInventori().isEmpty()) {
        JOptionPane.showMessageDialog(window, "Kendaraan Anda kosong! Tidak ada yang bisa dijual.", "Info", JOptionPane.INFORMATION_MESSAGE);
        return; // Tetap di layar kota saat ini
    }

    pembeliSaatIni = generatePembeliAcak("Pembeli dari " + this.kotaTransaksiSaatIni); // Lebih deskriptif
    pembeliSaatIni.resetKesabaran();

    List<Barang> barangDiKendaraanList = new ArrayList<>(pemain.getKendaraan().getInventori().keySet());
    if (barangDiKendaraanList.isEmpty()) {
        JOptionPane.showMessageDialog(window, "Aneh, kendaraan kosong padahal sudah dicek.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    barangYangDipilihPembeli = pembeliSaatIni.pilihBarang(barangDiKendaraanList);

    if (barangYangDipilihPembeli == null) {
        JOptionPane.showMessageDialog(window, pembeliSaatIni.getNama() + " tidak menemukan barang yang dicarinya dan pergi.");
        // Tetap di layar kota, tidak perlu JDialog terpisah untuk ini
        return;
    }

    int hargaJualBarangOlehPlayer = (int) barangYangDipilihPembeli.getHargaJual();
    this.hargaTawaranAwalPembeli = pembeliSaatIni.tawarHarga(barangYangDipilihPembeli, hargaJualBarangOlehPlayer);

    // TIDAK LAGI MEMANGGIL showBerjualanScreen() (JDialog)
    // Panggil method baru untuk menampilkan UI negosiasi di `con`
    tampilkanLayarNegosiasiDiCon(this.kotaTransaksiSaatIni);
}

// Anda perlu memodifikasi method seperti kotaA(), kotaB() atau pilihLokasi()
// untuk memanggil mulaiSesiBerjualan()
// Contoh di kotaA():
 public void kotaA(){
    this.kotaTransaksiSaatIni = "Kota A";
    con.removeAll();
    setBackgroundImage("A");
    con.add(backgroundLabel, Integer.valueOf(0));
    buttonKembaliKota();

    JLabel judulKotaA = new JLabel("Pasar Rakyat Kota A");
    judulKotaA.setFont(fontJudul.deriveFont(60f));
    judulKotaA.setForeground(Color.ORANGE);
    judulKotaA.setHorizontalAlignment(SwingConstants.CENTER);
    judulKotaA.setBounds(0, 50, 1280, 100);
    con.add(judulKotaA, Integer.valueOf(1));

    JButton btnCariPembeliDiKotaA = new JButton("Mulai Berjualan di Sini");
    btnCariPembeliDiKotaA.setFont(fontNormal.deriveFont(Font.BOLD, 22f));
    btnCariPembeliDiKotaA.setBounds(490, 250, 300, 70);
    btnCariPembeliDiKotaA.setBackground(new Color(0, 150, 50));
    btnCariPembeliDiKotaA.setForeground(Color.WHITE);
    btnCariPembeliDiKotaA.addActionListener(e -> {
        mulaiSesiBerjualan(); // Panggil method Anda yang sudah ada
    });
    con.add(btnCariPembeliDiKotaA, Integer.valueOf(1));

    window.revalidate();
    window.repaint();
}

private void tampilkanLayarNegosiasiDiCon(String namaLokasiAsal) {
    gambarPembeliLabelDiLayar      = new JLabel();
    kesabaranPembeliLabelDiLayar   = new JLabel();
    dialogPembeliLabelDiLayar      = new JTextArea();
    infoBarangDitawarAreaDiLayar   = new JTextArea();
    inputHargaPlayerDiLayar        = new JFormattedTextField();
    btnTawarDiLayar                = new JButton();
    btnJualDiLayar                 = new JButton();
    btnBatalJualDiLayar            = new JButton();
    inventarisKendaraanAreaDiLayar = new JTextArea();
    saldoPemainLabel_ModeJual      = new JLabel();
    con.removeAll();
    //setBackgroundImage("Transaksi.jpeg"); // Atau background lain untuk negosiasi
    con.add(backgroundLabel, Integer.valueOf(0));

    // --- Setup Komponen UI Negosiasi Langsung di con ---
    // Menggunakan field instance yang sudah diinisialisasi di konstruktor

    // Saldo Pemain
    saldoPemainLabel_ModeJual.setText("Saldo: Rp" + String.format("%,.0f", pemain.getSaldo()));
    saldoPemainLabel_ModeJual.setBounds(1000, 20, 250, 30); // Sesuaikan bounds
    saldoPemainLabel_ModeJual.setFont(fontNormal.deriveFont(Font.BOLD, 18f));
    saldoPemainLabel_ModeJual.setForeground(Color.ORANGE);
    saldoPemainLabel_ModeJual.setHorizontalAlignment(SwingConstants.RIGHT);
    con.add(saldoPemainLabel_ModeJual, Integer.valueOf(1));

    // Gambar Pembeli
    gambarPembeliLabelDiLayar.setHorizontalAlignment(SwingConstants.CENTER);
    String pathGambarPembeli = "Asset/default_pembeli.png";
    if (pembeliSaatIni instanceof PembeliMiskin) {
        pathGambarPembeli = "Asset/PembeliMiskin.png";
         gambarPembeliLabelDiLayar.setBounds(650, 100, 720, 720);

    }
    else if (pembeliSaatIni instanceof PembeliStandard){
        pathGambarPembeli = "Asset/PembeliStandard.png";
        gambarPembeliLabelDiLayar.setBounds(650, 100, 720, 720);
    }
    else if (pembeliSaatIni instanceof PembeliTajir){ 
        pathGambarPembeli = "Asset/PembeliTajir.png";
        gambarPembeliLabelDiLayar.setBounds(650, 100, 680, 800);
    }
    try {
        ImageIcon iconPembeli = new ImageIcon(getClass().getResource("/GameAkhir/" + pathGambarPembeli));
        Image imgPembeli = iconPembeli.getImage().getScaledInstance(gambarPembeliLabelDiLayar.getWidth(), gambarPembeliLabelDiLayar.getHeight(), Image.SCALE_SMOOTH);
        gambarPembeliLabelDiLayar.setIcon(new ImageIcon(imgPembeli));
        gambarPembeliLabelDiLayar.setText("");
    } catch (Exception ex) {
        gambarPembeliLabelDiLayar.setIcon(null);
        gambarPembeliLabelDiLayar.setText("Gbr: " + pembeliSaatIni.getClass().getSimpleName().substring(0,Math.min(7,pembeliSaatIni.getClass().getSimpleName().length())));
        gambarPembeliLabelDiLayar.setForeground(Color.WHITE);
        System.err.println("Err gbr pembeli: " + "/GameAkhir/" + pathGambarPembeli + " - " + ex.getMessage());
    }
    con.add(gambarPembeliLabelDiLayar, Integer.valueOf(1));

    // Nama Pembeli (buat label baru di sini untuk ditampilkan di con)
    JLabel namaPembeliNegosiasi = new JLabel(pembeliSaatIni.getNama());
    namaPembeliNegosiasi.setBounds(880, 330, 240, 30);
    namaPembeliNegosiasi.setFont(fontNormal.deriveFont(Font.BOLD, 20f));
    namaPembeliNegosiasi.setForeground(Color.CYAN);
    namaPembeliNegosiasi.setHorizontalAlignment(SwingConstants.CENTER);
    con.add(namaPembeliNegosiasi, Integer.valueOf(4));


    // Kesabaran Pembeli
    kesabaranPembeliLabelDiLayar.setText("Kesabaran: " + pembeliSaatIni.getKesabaranSaatIni() + "/" + pembeliSaatIni.getTingkatKesabaranDefault());
    kesabaranPembeliLabelDiLayar.setBounds(880, 360, 220, 30);
    kesabaranPembeliLabelDiLayar.setFont(fontNormal.deriveFont(16f));
    kesabaranPembeliLabelDiLayar.setForeground(Color.PINK);
    kesabaranPembeliLabelDiLayar.setHorizontalAlignment(SwingConstants.CENTER);
    con.add(kesabaranPembeliLabelDiLayar, Integer.valueOf(4));

    //Border Info Pembeli
    JLabel BorderInfoPembeli = new JLabel();
    BorderInfoPembeli.setBounds(870, 315, 260, 90);
    BorderInfoPembeli.setBackground(Color.BLACK);
    BorderInfoPembeli.setOpaque(true);
    con.add(BorderInfoPembeli, Integer.valueOf(3));

    // Dialog Pembeli (menggunakan JTextArea dialogPembeliLabelDiLayar Anda)
    dialogPembeliLabelDiLayar.setText(pembeliSaatIni.getNama() + ": \"Saya tertarik dengan " + barangYangDipilihPembeli.getNama() + ". Saya tawar Rp" + String.format("%,d", this.hargaTawaranAwalPembeli) + ".\"");
    dialogPembeliLabelDiLayar.setFont(fontNormal.deriveFont(18f));
    dialogPembeliLabelDiLayar.setForeground(Color.WHITE);
    dialogPembeliLabelDiLayar.setBackground(Color.BLACK); // Transparan
    dialogPembeliLabelDiLayar.setOpaque(true);
    dialogPembeliLabelDiLayar.setWrapStyleWord(true);
    dialogPembeliLabelDiLayar.setLineWrap(true);
    dialogPembeliLabelDiLayar.setEditable(false);
    dialogPembeliLabelDiLayar.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    JScrollPane scrollDialog = new JScrollPane(dialogPembeliLabelDiLayar);
    scrollDialog.setBounds(630, 100, 330, 90);
    scrollDialog.setOpaque(false); scrollDialog.getViewport().setOpaque(false); scrollDialog.setBorder(null);
    scrollDialog.getViewport().setBackground(new Color(0,0,0,190)); // Background untuk viewport
    con.add(scrollDialog, Integer.valueOf(1));

    // Info Barang Ditawar
    JLabel infoBarangTitleLabel = new JLabel("Barang yang Ditawar:");
    infoBarangTitleLabel.setBounds(380, 210, 250, 30);
    infoBarangTitleLabel.setFont(fontNormal.deriveFont(Font.BOLD, 18f));
    infoBarangTitleLabel.setForeground(Color.YELLOW);
    con.add(infoBarangTitleLabel, Integer.valueOf(1));

    infoBarangDitawarAreaDiLayar.setText(
        "Barang: " + barangYangDipilihPembeli.getNama() + "\n" +
        "Harga Jual: Rp" + String.format("%,.0f", barangYangDipilihPembeli.getHargaJual()) + "\n" +
        "Stok: " + pemain.getKendaraan().getJumlah(barangYangDipilihPembeli) + "\n" +
        "Modal: Rp" + String.format("%,.0f", barangYangDipilihPembeli.getHargaBeli())
    );
    infoBarangDitawarAreaDiLayar.setFont(fontNormal.deriveFont(16f));
    infoBarangDitawarAreaDiLayar.setForeground(Color.WHITE);
    infoBarangDitawarAreaDiLayar.setBackground(new Color(50,50,50,190));
    infoBarangDitawarAreaDiLayar.setEditable(false);
    infoBarangDitawarAreaDiLayar.setEnabled(false);
    infoBarangDitawarAreaDiLayar.setFocusable(false);
    infoBarangDitawarAreaDiLayar.setHighlighter(null);
    infoBarangDitawarAreaDiLayar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); // Cursor default
    infoBarangDitawarAreaDiLayar.setLineWrap(true); infoBarangDitawarAreaDiLayar.setWrapStyleWord(true);
    infoBarangDitawarAreaDiLayar.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    JScrollPane scrollInfoBarang = new JScrollPane(infoBarangDitawarAreaDiLayar);
    scrollInfoBarang.setBounds(370, 245, 400, 120);
    con.add(scrollInfoBarang, Integer.valueOf(1));

    // Input Harga Player
    JLabel labelInputHargaCon = new JLabel("Tawarkan Harga Anda (Rp):"); // _Con untuk membedakan dari JDialog
    labelInputHargaCon.setBounds(100, 290, 300, 30);
    labelInputHargaCon.setFont(fontNormal.deriveFont(Font.BOLD, 18f));
    labelInputHargaCon.setForeground(new Color(144, 238, 144));
    con.add(labelInputHargaCon, Integer.valueOf(1));

    // Menggunakan inputHargaPlayerDiLayar Anda
    inputHargaPlayerDiLayar.setValue((int) barangYangDipilihPembeli.getHargaJual());
    inputHargaPlayerDiLayar.setBounds(100, 320, 220, 35);
    inputHargaPlayerDiLayar.setFont(fontNormal.deriveFont(18f));
    inputHargaPlayerDiLayar.setHorizontalAlignment(JTextField.RIGHT);
    inputHargaPlayerDiLayar.setEnabled(true);
    con.add(inputHargaPlayerDiLayar, Integer.valueOf(1));

    // Tombol Aksi Player
    // Menggunakan btnTawarDiLayar, btnJualDiLayar, btnBatalJualDiLayar Anda
    btnTawarDiLayar.setText("Tawar Balik");
    btnTawarDiLayar.setBounds(100, 125, 220, 45);
    btnTawarDiLayar.setFont(fontNormal.deriveFont(Font.BOLD,18f));
    btnTawarDiLayar.setBackground(new Color(255, 165, 0));
    btnTawarDiLayar.setEnabled(true);
    for(ActionListener al : btnTawarDiLayar.getActionListeners()) btnTawarDiLayar.removeActionListener(al);
    btnTawarDiLayar.addActionListener(e -> handleTawarBalikKePembeliCon());
    con.add(btnTawarDiLayar, Integer.valueOf(1));

    btnJualDiLayar.setText("Jual dengan Harga Ini");
    btnJualDiLayar.setBounds(100, 180, 220, 45);
    btnJualDiLayar.setFont(fontNormal.deriveFont(Font.BOLD,18f));
    btnJualDiLayar.setBackground(new Color(60, 179, 113));
    btnJualDiLayar.setForeground(Color.WHITE);
    btnJualDiLayar.setEnabled(true);
    for(ActionListener al : btnJualDiLayar.getActionListeners()) btnJualDiLayar.removeActionListener(al);
    btnJualDiLayar.addActionListener(e -> handleJualDenganHargaInputCon());
    con.add(btnJualDiLayar, Integer.valueOf(1));

    btnBatalJualDiLayar.setText("Batalkan & Akhiri Sesi");
    btnBatalJualDiLayar.setBounds(100, 235, 220, 45);
    btnBatalJualDiLayar.setFont(fontNormal.deriveFont(Font.BOLD,16f));
    btnBatalJualDiLayar.setBackground(new Color(220, 20, 60));
    btnBatalJualDiLayar.setForeground(Color.WHITE);
    btnBatalJualDiLayar.setEnabled(true);
    for(ActionListener al : btnBatalJualDiLayar.getActionListeners()) btnBatalJualDiLayar.removeActionListener(al);
    btnBatalJualDiLayar.addActionListener(e -> handleAkhiriSesiDenganPembeliCon(true, namaLokasiAsal));
    con.add(btnBatalJualDiLayar, Integer.valueOf(1));
    
    // Inventaris Kendaraan
    JLabel labelInvKendaraan = new JLabel("Stok Kendaraan Anda:");
    labelInvKendaraan.setBounds(80, 380, 300, 30);
    labelInvKendaraan.setFont(fontNormal.deriveFont(Font.BOLD, 18f));
    labelInvKendaraan.setForeground(Color.ORANGE);
    con.add(labelInvKendaraan, Integer.valueOf(1));

    // Menggunakan inventarisKendaraanAreaDiLayar Anda
    updateInventarisKendaraanDiLayarJualCon();
    inventarisKendaraanAreaDiLayar.setFont(fontNormal.deriveFont(16f));
    inventarisKendaraanAreaDiLayar.setForeground(new Color(200,200,200));
    inventarisKendaraanAreaDiLayar.setBackground(new Color(30,30,30,210));
    inventarisKendaraanAreaDiLayar.setEditable(false);
    inventarisKendaraanAreaDiLayar.setLineWrap(true); inventarisKendaraanAreaDiLayar.setWrapStyleWord(true);
    inventarisKendaraanAreaDiLayar.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    JScrollPane scrollInv = new JScrollPane(inventarisKendaraanAreaDiLayar);
    scrollInv.setBounds(80, 415, 1100, 220);
    con.add(scrollInv, Integer.valueOf(2));

    window.revalidate();
    window.repaint();
}


// --- HANDLER AKSI UNTUK MODE BERJUALAN (versi internal) ---
private void handleTawarBalikKePembeliCon() {
    if (inputHargaPlayerDiLayar.getValue() == null) {
        JOptionPane.showMessageDialog(window, "Masukkan harga tawaran Anda!", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    int hargaTawaranPlayer = (Integer) inputHargaPlayerDiLayar.getValue();
    pembeliSaatIni.kurangiKesabaran();
    kesabaranPembeliLabelDiLayar.setText("Kesabaran: " + pembeliSaatIni.getKesabaranSaatIni() + "/" + pembeliSaatIni.getTingkatKesabaranDefault());

    if (!pembeliSaatIni.masihSabar()) {
        dialogPembeliLabelDiLayar.setText(
        pembeliSaatIni.getNama() + ": \"Sudah cukup! Saya tidak tertarik lagi.\"\n(Kesabaran habis, pergi)"
        );
        nonaktifkanKontrolJualCon("Kesabaran Habis");
        return;
    }

    boolean terimaTawaranPlayer = pembeliSaatIni.putuskanBeli(hargaTawaranPlayer, this.hargaTawaranAwalPembeli);

    if (terimaTawaranPlayer) {
        prosesPenjualanBerhasilCon(hargaTawaranPlayer);
    } else {
        int hargaTawaranBaruPembeli = pembeliSaatIni.tawarHarga(barangYangDipilihPembeli, hargaTawaranPlayer);
        if (hargaTawaranBaruPembeli >= hargaTawaranPlayer && !(pembeliSaatIni instanceof PembeliTajir && Math.random() < 0.15)) { 
            dialogPembeliLabelDiLayar.setText(
                pembeliSaatIni.getNama() + ": \"Hmm, harga Rp" + String.format("%,d", hargaTawaranPlayer) + " masih terlalu tinggi untuk saya.\"\n(Tidak menawar lagi)"
            );
        } else {
            this.hargaTawaranAwalPembeli = hargaTawaranBaruPembeli;
            dialogPembeliLabelDiLayar.setText(
                pembeliSaatIni.getNama() + ": \"Bagaimana kalau Rp" + String.format("%,d", this.hargaTawaranAwalPembeli) + "?\""
            );
        }
    }
}
private void handleJualDenganHargaInputCon() {
    if (inputHargaPlayerDiLayar.getValue() == null) {
        JOptionPane.showMessageDialog(window, "Masukkan harga jual Anda!", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    int hargaFinalPlayer = (Integer) inputHargaPlayerDiLayar.getValue();
    boolean keputusanFinalPembeli = pembeliSaatIni.putuskanBeli(hargaFinalPlayer, this.hargaTawaranAwalPembeli);

    if (keputusanFinalPembeli) {
        prosesPenjualanBerhasilCon(hargaFinalPlayer);
    } else {
        pembeliSaatIni.kurangiKesabaran();
        kesabaranPembeliLabelDiLayar.setText("Kesabaran: " + pembeliSaatIni.getKesabaranSaatIni() + "/" + pembeliSaatIni.getTingkatKesabaranDefault());
        if (!pembeliSaatIni.masihSabar()) {
            dialogPembeliLabelDiLayar.setText(
                pembeliSaatIni.getNama() + ": \"Saya rasa kita tidak akan sepakat.\"\n(Kesabaran habis, pergi)"
            );
            nonaktifkanKontrolJualCon("Kesabaran Habis");
        } else {
            dialogPembeliLabelDiLayar.setText(
                pembeliSaatIni.getNama() + ": \"Maaf, harga Rp" + String.format("%,d", hargaFinalPlayer) + " masih terlalu tinggi buat saya.\""
            );
        }
    }
}
private void prosesPenjualanBerhasilCon(int hargaTerjual) {
    pemain.tambahSaldo(hargaTerjual);
    pemain.getKendaraan().kurangiBarang(barangYangDipilihPembeli, 1);

   dialogPembeliLabelDiLayar.setText(
    pembeliSaatIni.getNama() + ": \"Setuju! Terima kasih banyak.\"\n(Barang terjual seharga Rp" + String.format("%,d", hargaTerjual) + ")");
    nonaktifkanKontrolJualCon("Transaksi Selesai");
    updateInventarisKendaraanDiLayarJualCon();
    updateSaldoDiLayarJualCon();

    JOptionPane.showMessageDialog(window,
            "Berhasil menjual " + barangYangDipilihPembeli.getNama() + " kepada " + pembeliSaatIni.getNama() + " seharga Rp" + String.format("%,d", hargaTerjual) + "!",
            "Transaksi Berhasil", JOptionPane.INFORMATION_MESSAGE);
    // Tombol btnBatalJualDiLayar sekarang berfungsi sebagai tombol "Lanjut"
}

private void nonaktifkanKontrolJualCon(String alasanSelesai) {
    btnTawarDiLayar.setEnabled(false);
    btnJualDiLayar.setEnabled(false);
    inputHargaPlayerDiLayar.setEnabled(false);
    btnBatalJualDiLayar.setText("Lanjut (" + alasanSelesai.substring(0, Math.min(alasanSelesai.length(), 10))+"..)");
    btnBatalJualDiLayar.setBackground(new Color(0,100,0)); // Warna hijau untuk lanjut
}
private void handleAkhiriSesiDenganPembeliCon(boolean dibatalkanOlehPemain, String kotaKembali) {
    if (dibatalkanOlehPemain && pembeliSaatIni != null && barangYangDipilihPembeli != null && btnTawarDiLayar.isEnabled()) {
        int konfirmasi = JOptionPane.showConfirmDialog(window,
                "Yakin ingin mengakhiri sesi dengan " + pembeliSaatIni.getNama() + "?",
                "Akhiri Sesi", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (konfirmasi == JOptionPane.NO_OPTION) {
            return;
        }
       if(dialogPembeliLabelDiLayar != null) {
            dialogPembeliLabelDiLayar.setText(
            pembeliSaatIni.getNama() + ": \"Baiklah kalau begitu...\" (Kecewa dan pergi)"
            );
        }
    }
    // Kembali ke layar kota tempat transaksi terjadi
    if ("Kota A".equals(kotaKembali)) kotaA();
    else if ("Kota B".equals(kotaKembali)) kotaB(); // Jika Anda implementasi kotaB
    else pilihLokasi(); // Fallback
}

private void updateInventarisKendaraanDiLayarJualCon() {
    // Menggunakan inventarisKendaraanAreaDiLayar Anda
    if (inventarisKendaraanAreaDiLayar == null) return;
    StringBuilder sb = new StringBuilder();
    Map<Barang, Integer> inventori = pemain.getKendaraan().getInventori();
    if (inventori.isEmpty()) {
        sb.append("- Kendaraan Kosong -");
    } else {
        for (Map.Entry<Barang, Integer> entry : inventori.entrySet()) {
            Barang b = entry.getKey(); int jumlah = entry.getValue();
            sb.append(String.format("- %s (Stok: %d, Harga Jual: Rp%,.0f)\n", b.getNama(), jumlah, b.getHargaJual()));
        }
    }
    inventarisKendaraanAreaDiLayar.setText(sb.toString());
    inventarisKendaraanAreaDiLayar.setCaretPosition(0);
}

private void updateSaldoDiLayarJualCon(){
    // Menggunakan saldoPemainLabel_ModeJual Anda
    if (saldoPemainLabel_ModeJual != null)
        saldoPemainLabel_ModeJual.setText("Saldo: Rp" + String.format("%,.0f", pemain.getSaldo()));
}



// Method generatePembeliAcak() Anda yang sudah ada
private Pembeli generatePembeliAcak(String namaTambahan) { // namaTambahan diubah jadi nama
    String[] daftarNama = {"Nel", "Budi", "Mike", "Dodi", "Brody", "Tzy", "Gilang", "Yuanwu", "Joko", "Mara", "John"};
    String namaAcak = daftarNama[new Random().nextInt(daftarNama.length)] + " " + namaTambahan; // Menggunakan namaTambahan
    double chance = Math.random();
    if (chance < 0.25) return new PembeliMiskin(namaAcak); // Peluang disesuaikan
    else if (chance < 0.75) return new PembeliStandard(namaAcak);
    else return new PembeliTajir(namaAcak);
}
}
