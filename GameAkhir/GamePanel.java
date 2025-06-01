//Kelompok 10
//Noval Abimanyu Sufyan Pratama - 245150207111075
//Rusdiansyah Alief Prasetya - 245150207111073
//Anindhita Faiza Aulia - 245150201111042
//Monika Miriwanop Kejok - 245150220111002

package GameAkhir;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;

public class GamePanel {

    private static Component PerkListCellRendererthis;
    JLabel gambarPembeliLabelDiLayar;
    JLabel gambarPenjual;
    JLabel gambarMC;
    JTextArea dialogNPC;
    JLabel kesabaranPembeliLabelDiLayar;
    JTextArea infoBarangDitawarAreaDiLayar;
    JFormattedTextField inputHargaPlayerDiLayar;
    JButton btnTawarDiLayar, btnJualDiLayar, btnBatalJualDiLayar,btnToko, btnToko2;
    JTextArea inventarisKendaraanAreaDiLayar;
    private JLabel backgroundLabel;
    private Clip bgmClip;
    private Barang barangYangDipilihPembeli = null;
    private Pembeli pembeliSaatIni = null;
    private String kotaTransaksiSaatIni = null;
    private int hargaTawaranAwalPembeli = 0;
    private int totalPembeliSesi = 0;
    private int pembeliKe = 0;
    private SaveFile saveFile;
    private boolean efekHipnotisAktif = false;
    private boolean efekBonusAktif = false;
    
    String nama;
    JLayeredPane layeredPane;
    Player pemain = new Player();
    Rumah rumah = new Rumah();
    Kendaraan kendaraan = new Kendaraan(5, 1);
    
    ArrayList<Barang> daftarBarang = new ArrayList<>();
    ArrayList<Item> daftarItem = new ArrayList<>();
    ArrayList<Perk> daftarPerk = new ArrayList<>();
    Item item = new Item();
    TokoItem tokoItem = new TokoItem(daftarItem, pemain, rumah, kendaraan);
    Supplier supplier = new Supplier(daftarBarang, rumah, pemain, this);
    Random rand = new Random();

    JFrame window;
    JLayeredPane con;
    JPanel namaPanel, judulPanel, tombolStartPanel, mainTextPanel, tombolPilihanPanel, panelPemain;
    JLabel judulLabel, judulLabel2;
    JButton tombolStart, tombolLoad, pil1, pil2, pil3, pil4, pil5;
    
    JTextField inputNamaPemainField;
    JPanel namaPanel_LayarAwal; 
    JPanel judulPanel_LayarAwal;
    JButton tombolStart_LayarAwal;
    JPanel tombolStartPanel_LayarAwal;
    JPanel panelPemain_GameNormal; 
    JTextArea infoAreaPemain_GameNormal;
    JLabel saldoPemainLabel_ModeJual; 
    
    // Komponen UI Panel Kota (di panelKotaSaatIni)
    JLabel namaKotaLabel_Kota;
    JButton btnCariPembeli_Kota;
    JButton btnKembaliKePeta_Kota;
    
    // Komponen UI Game Normal (di panelGameNormal yang sudah dideklarasikan)
    JPanel mainTextPanel_GameNormal; 
    JTextArea mainTextArea_GameNormal; 
    JPanel tombolPilihanPanel_GameNormal; 
    JButton pil1_GameNormal, pil2_GameNormal, pil3_GameNormal, pil4_GameNormal, pil5_GameNormal; 
    
    Font fontJudul, fontJudulBold;
    
    Font fontNormal = new Font("Times New Roman", Font.PLAIN, 20);
    JTextArea mainTextArea;
    String position;

    public GamePanel() {
        fontJudul = testFont("Serif", 90f);
        fontJudulBold = testFont("Serif", 90f);
        pemain.setKendaraan(kendaraan);
        pemain.tambahPerkDimiliki(new PerkElegan("Hipnotis", 1));
        pemain.tambahPerkDimiliki(new PerkDiskon("Diskon Mantap", 1));
        pemain.tambahPerkDimiliki(new PerkPintarTawar("Ahli Tawar", 1));
        pemain.tambahPerkDimiliki(new PerkPenampilMenarik("Penampil Menarik", 1));
        pemain.tambahPerkDimiliki(new PerkTanganDingin("Tangan Dingin", 1));
        
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
        putarBGM("/GameAkhir/Asset/Normal.wav"); 
        saveFile = new SaveFile();
        backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, 1280, 720);
        ImageIcon icon = new ImageIcon(getClass().getResource("/GameAkhir/Asset/Start.png")); 
        Image img = icon.getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(img));
        con.add(backgroundLabel, Integer.valueOf(0));

        namaPanel = new JPanel();
        namaPanel.setBounds(510, 250, 200, 25);
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

        judulLabel = new JLabel("POST-APOCALYPTRADE");
        judulLabel.setForeground(Color.WHITE);
        fontJudul = fontJudul.deriveFont(60f);
        judulLabel.setFont(fontJudul);
        judulLabel.setBounds(270, 100, 750, 130);
        judulLabel2 = new JLabel("POST-APOCALYPTRADE");
        judulLabel2.setForeground(Color.BLACK);
        judulLabel2.setFont(fontJudul);
        judulLabel2.setBounds(275, 100, 750, 130);

        tombolLoad = new JButton("LOAD");
        tombolLoad.setBackground(Color.black);
        tombolLoad.setForeground(Color.white);
        tombolLoad.setFont(fontNormal);
        tombolLoad.setBounds(510, 400, 200, 50);
        tombolLoad.addActionListener(e -> {
            Object[] hasilLoad = saveFile.muatProgress(daftarBarang, daftarItem);
            if (hasilLoad != null) {
                this.pemain = (Player) hasilLoad[0];
                this.kendaraan = (Kendaraan) hasilLoad[1];
                this.rumah = (Rumah) hasilLoad[2];
                GameScreen();
                JOptionPane.showMessageDialog(window, "Game berhasil dimuat!", "Load", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(window, "Gagal memuat game!", "Load Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        

        tombolStart = new JButton("START");
        tombolStart.setBackground(Color.black);
        tombolStart.setForeground(Color.white);
        tombolStart.setFont(fontNormal);
        tombolStart.addActionListener(new TitleScreenHandler());
        tombolStart.setBounds(510, 300, 200, 50);
        con.add(judulLabel2, Integer.valueOf(1));
        con.add(judulLabel, Integer.valueOf(2));
        con.add(tombolStart, Integer.valueOf(1));
        con.add(tombolLoad, Integer.valueOf(1));
        daftarPerk.add(new PerkElegan("Elegan",1));
        daftarPerk.add(new PerkActive("Aktif",1));
        daftarPerk.add(new PerkCharming("Charming",1));

        gambarPembeliLabelDiLayar      = new JLabel();
        kesabaranPembeliLabelDiLayar   = new JLabel();
        dialogNPC      = new JTextArea();
        infoBarangDitawarAreaDiLayar   = new JTextArea();
        inputHargaPlayerDiLayar        = new JFormattedTextField();
        btnTawarDiLayar                = new JButton();
        btnJualDiLayar                 = new JButton();
        btnBatalJualDiLayar            = new JButton();
        inventarisKendaraanAreaDiLayar = new JTextArea();
        saldoPemainLabel_ModeJual      = new JLabel();

         window.setVisible(true);
        }
        public void showTitleScreen() {
            con.removeAll();
            ImageIcon icon = new ImageIcon(getClass().getResource("/GameAkhir/Asset/Start.png")); 
            Image img = icon.getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
            backgroundLabel.setIcon(new ImageIcon(img));
            con.add(backgroundLabel, Integer.valueOf(0));
            con.add(judulLabel2, Integer.valueOf(1));
            con.add(judulLabel, Integer.valueOf(2));
            con.add(tombolStart, Integer.valueOf(1));
            con.add(tombolLoad, Integer.valueOf(1));
            namaPanel.setVisible(false);
            window.revalidate();
            window.repaint();
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

            tombolPilihanPanel = new JPanel();
            tombolPilihanPanel.setBounds(950, 100, 200, 550);
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
            panelPemain.setBounds(100, 500, 600, 150);
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
            public Font testFont(String fontName, float size) {
                String fontnama = "/GameAkhir/Asset/Font/"+fontName+".ttf";
                System.out.println("Coba load font: " + fontnama);
                InputStream is = getClass().getResourceAsStream(fontnama);
                try {
                    Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(fontnama))
                        .deriveFont(Font.PLAIN, size);
                    GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
                    return font;
                } catch (Exception e) {
                    System.err.println("Font custom gagal dimuat, pakai default.");
                    return new Font("Serif", Font.BOLD, (int) size);
                }
            }
        public void Stroke(JLabel label, Color color, int x, int y, int w, int h){
        }

        private void showBuyBarangScreen() {
            putarBGM("/GameAkhir/Asset/Shop.wav"); 
            con.removeAll();
            gambarPenjual = new JLabel();
            gambarPenjual.setHorizontalAlignment(SwingConstants.CENTER);
            String pathPenjual = "Asset/Amiya1.png";
            gambarPenjual.setBounds(580, 0, 1040, 1040);
            try {
                ImageIcon iconPenjual = new ImageIcon(getClass().getResource("/GameAkhir/" + pathPenjual));
                Image imgPenjual = iconPenjual.getImage().getScaledInstance(gambarPenjual.getWidth(), gambarPenjual.getHeight(), Image.SCALE_SMOOTH);
                gambarPenjual.setIcon(new ImageIcon(imgPenjual));
                gambarPenjual.setText("");
            } catch (Exception ex) {
                gambarPenjual.setIcon(null);
                gambarPenjual.setText("Gambar tidak ditemukan");
            }
            con.add(gambarPenjual, Integer.valueOf(1));
            setBackgroundImage("S");
            con.add(backgroundLabel, Integer.valueOf(0));

            dialogNPC.setText("Selamat datang di toko barang!");
            dialogNPC.setBounds(730, 200, 250, 30);
            dialogNPC.setFont(fontNormal);
            dialogNPC.setEditable(false);
            dialogNPC.setLineWrap(true);
            dialogNPC.setWrapStyleWord(true);
            dialogNPC.setBackground(Color.BLACK);
            dialogNPC.setForeground(Color.WHITE);
            dialogNPC.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            con.add(dialogNPC, Integer.valueOf(2));

            JLabel title = new JLabel("Toko Barang");
            fontJudul = fontJudul.deriveFont(90f);
            title.setFont(fontJudul);
            title.setForeground(Color.WHITE);
            title.setBounds(150, 40, 600, 110);
            con.add(title, Integer.valueOf(2));
            JLabel title2 = new JLabel("Toko Barang");
            fontJudulBold = fontJudul.deriveFont(90f);
            title2.setFont(fontJudulBold);
            title2.setForeground(Color.BLACK);
            title2.setBounds(155, 40, 600, 110);
            con.add(title2, Integer.valueOf(1));

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
                            updatePenjual(jumlah, pathPenjual);
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
            kembali.addActionListener(e -> kembalidariTokoB());
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

        private void putarBGM(String path) {
        try {
            if (bgmClip != null && bgmClip.isRunning()) {
                bgmClip.stop();
                bgmClip.close();
            }
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource(path));
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioIn);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();
        } catch (Exception e) {
            System.err.println("Gagal memutar BGM: " + e.getMessage());
        }
    }
        public void updatePenjual(int jumBarang, String path){
            if(jumBarang > 10){
                dialogNPC.setText("EHHH!!!??? Beneran kamu beli sebanyak ini ?");
                dialogNPC.setBounds(730, 200, 250, 60);
                path = "Asset/Amiya2.png";
            }else{
                path = "Asset/Amiya3.png";
                int angka = rand.nextInt(5) + 1;
                switch(angka){
                    case 1:
                        dialogNPC.setText("Terima kasih sudah belanja");
                        dialogNPC.setBounds(730, 200, 250, 30);
                        break;
                    case 2:
                        dialogNPC.setText("Semoga barang ini bermanfaat");
                        dialogNPC.setBounds(730, 200, 250, 30);
                        break;
                    case 3:
                        dialogNPC.setText("Silakan datang lagi kalau butuh sesuatu");
                        dialogNPC.setBounds(730, 200, 250, 60);
                        break;
                    case 4:
                        dialogNPC.setText("Barang ini sudah saya pilihkan khusus untuk kamu");
                        dialogNPC.setBounds(730, 200, 250, 60);
                        break;
                    case 5:
                        dialogNPC.setText("Semoga kamu suka dengan barang ini");
                        dialogNPC.setBounds(730, 200, 250, 60);
                        break;
                }

            }
            try {
            ImageIcon iconPenjual = new ImageIcon(getClass().getResource("/GameAkhir/" + path));
            Image imgPenjual = iconPenjual.getImage().getScaledInstance(gambarPenjual.getWidth(), gambarPenjual.getHeight(), Image.SCALE_SMOOTH);
            gambarPenjual.setIcon(new ImageIcon(imgPenjual));
            gambarPenjual.setText("");
            } catch (Exception ex) {
                gambarPenjual.setIcon(null);
                gambarPenjual.setText("Gambar tidak ditemukan");
            }
            window.revalidate();
            window.repaint();
        }

        private void removeAll() {
            throw new UnsupportedOperationException("Not supported yet."); 
        }

        private void setLayout(BorderLayout borderLayout) {
            throw new UnsupportedOperationException("Not supported yet."); 
        }

        private void showMainMenu() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        private void add(JLabel scrollPanel, String CENTER) {
            throw new UnsupportedOperationException("Not supported yet."); 
        }

        private void revalidate() {
            throw new UnsupportedOperationException("Not supported yet."); 
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
            pil1.setText("Title Screen");
            pil2.setText("Save Game");
            pil3.setText("Atur Perks & Item");
            pil4.setText("Atur Stok");
            pil5.setText("Berjualan");
        }

        private void handleMenu(int pilihan) {
            switch (pilihan) {
                case 1:
                    showTitleScreen();
                    break;
                case 2:
                    save();
                    break;
                case 3:
                    showAturPerk();
                    break;
                case 4:
                    showManageInventoryDialog();
                    updatePlayerInfo();
                    break;
                case 5:
                    pilihLokasi();
                    break;
            }
        }
    
        public void showAturPerk(){
            JDialog aturPerkDialog = new JDialog(window, "Atur Pasif Perks", true);
            aturPerkDialog.setSize(800, 600);
            aturPerkDialog.setLocationRelativeTo(window);
            aturPerkDialog.setLayout(new BorderLayout(10, 10));

             // --- Panel Kiri: Daftar Perk yang Dimiliki ---
            JPanel panelKiri = new JPanel(new BorderLayout(5,5));
            panelKiri.setBorder(BorderFactory.createTitledBorder("Perk & Item Dimiliki"));
            DefaultListModel<Perk> listModelDimiliki = new DefaultListModel<>();
            JList<Perk> listPerkDimiliki = new JList<>(listModelDimiliki);
            listPerkDimiliki.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listPerkDimiliki.setCellRenderer(new PerkListCellRenderer()); 

            Runnable refreshListDimiliki = () -> {
                listModelDimiliki.clear();
                for (Perk p : pemain.getSemuaPerkDimiliki()) {
                    listModelDimiliki.addElement(p);
                }
            };
            refreshListDimiliki.run();
            panelKiri.add(new JScrollPane(listPerkDimiliki), BorderLayout.CENTER);

            // --- Panel Tengah: Info Perk Terpilih & Aksi ---
            JPanel panelTengah = new JPanel();
            panelTengah.setLayout(new BoxLayout(panelTengah, BoxLayout.Y_AXIS));
            panelTengah.setBorder(BorderFactory.createTitledBorder("Detail & Aksi Perk"));

            JTextArea infoPerkTerpilihArea = new JTextArea(5, 25);
            infoPerkTerpilihArea.setEditable(false);
            infoPerkTerpilihArea.setLineWrap(true);
            infoPerkTerpilihArea.setWrapStyleWord(true);
            infoPerkTerpilihArea.setFont(fontNormal.deriveFont(14f));
            JScrollPane scrollInfoPerk = new JScrollPane(infoPerkTerpilihArea);
            panelTengah.add(scrollInfoPerk);
            panelTengah.add(Box.createRigidArea(new Dimension(0,10)));

            JButton btnUpgradePerk = new JButton("Upgrade Perk"); btnUpgradePerk.setEnabled(false);
            JButton btnUbahAbilityPerk = new JButton("Ubah Ability"); btnUbahAbilityPerk.setEnabled(false);
            JButton btnAktifkanSlot1 = new JButton("Aktifkan ke Slot 1"); btnAktifkanSlot1.setEnabled(false);
            JButton btnAktifkanSlot2 = new JButton("Aktifkan ke Slot 2"); btnAktifkanSlot2.setEnabled(false);
            JButton btnJualPerk = new JButton("Jual Perk (TODO)"); btnJualPerk.setEnabled(false);

            btnUpgradePerk.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnUbahAbilityPerk.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnAktifkanSlot1.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnAktifkanSlot2.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnJualPerk.setAlignmentX(Component.CENTER_ALIGNMENT);

            panelTengah.add(btnUpgradePerk);
            panelTengah.add(Box.createRigidArea(new Dimension(0,5)));
            panelTengah.add(btnUbahAbilityPerk);
            panelTengah.add(Box.createRigidArea(new Dimension(0,15)));
            panelTengah.add(btnAktifkanSlot1);
            panelTengah.add(Box.createRigidArea(new Dimension(0,5)));
            panelTengah.add(btnAktifkanSlot2);
            panelTengah.add(Box.createRigidArea(new Dimension(0,15)));
            panelTengah.add(btnJualPerk);
            panelTengah.add(Box.createVerticalGlue());


            // --- Panel Kanan: Perk Aktif ---
            JPanel panelKanan = new JPanel(new BorderLayout(5,5));
            panelKanan.setBorder(BorderFactory.createTitledBorder("Perk Aktif"));
            DefaultListModel<String> listModelAktif = new DefaultListModel<>();
            JList<String> listPerkAktif = new JList<>(listModelAktif); 
            listPerkAktif.setEnabled(false); 

            Runnable refreshListAktif = () -> {
                listModelAktif.clear();
                List<Perk> aktif = pemain.getPerkAktif();
                listModelAktif.addElement("Slot 1: " + (aktif.size() > 0 && aktif.get(0) != null ? aktif.get(0).getNama() : "[Kosong]"));
                listModelAktif.addElement("Slot 2: " + (aktif.size() > 1 && aktif.get(1) != null ? aktif.get(1).getNama() : "[Kosong]"));
            };
            refreshListAktif.run();
            panelKanan.add(new JScrollPane(listPerkAktif), BorderLayout.CENTER);

            JButton btnLepasSlot1 = new JButton("Lepas Slot 1");
            JButton btnLepasSlot2 = new JButton("Lepas Slot 2");
            JPanel panelTombolLepas = new JPanel(new GridLayout(1,2,5,0));
            panelTombolLepas.add(btnLepasSlot1);
            panelTombolLepas.add(btnLepasSlot2);
            panelKanan.add(panelTombolLepas, BorderLayout.SOUTH);


            // --- Listener untuk list Perk Dimiliki ---
            listPerkDimiliki.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    Perk terpilih = listPerkDimiliki.getSelectedValue();
                    if (terpilih != null) {
                        infoPerkTerpilihArea.setText(
                            "Nama: " + terpilih.getNama() + "\n" +
                            "Jenis: " + terpilih.getClass().getSimpleName().replace("Perk", "") + "\n" +
                            "Level: " + terpilih.getTingkatKesaktian() + " / 5\n" +
                            "Efek: " + terpilih.getDeskripsiEfek() + "\n\n" +
                            (terpilih.getTingkatKesaktian() < 5 ? "Biaya Upgrade: Rp" + String.format("%,d", terpilih.getHargaUpgrade()) : "Level Maksimal") + "\n" +
                            "Biaya Ubah Ability: Rp" + String.format("%,d", terpilih.getBiayaUbahAbility())
                        );
                        btnUpgradePerk.setEnabled(terpilih.getTingkatKesaktian() < 5);
                        btnUbahAbilityPerk.setEnabled(true);
                        btnAktifkanSlot1.setEnabled(true);
                        btnAktifkanSlot2.setEnabled(true);
                        btnJualPerk.setEnabled(true); 
                    } else {
                        infoPerkTerpilihArea.setText("Pilih perk untuk melihat detail.");
                        btnUpgradePerk.setEnabled(false);
                        btnUbahAbilityPerk.setEnabled(false);
                        btnAktifkanSlot1.setEnabled(false);
                        btnAktifkanSlot2.setEnabled(false);
                        btnJualPerk.setEnabled(false);
                    }
                }
            });

            // --- Action Listeners untuk Tombol ---
            btnUpgradePerk.addActionListener(e -> {
                Perk terpilih = listPerkDimiliki.getSelectedValue();
                if (terpilih != null && terpilih.getTingkatKesaktian() < 5) {
                    int biaya = terpilih.getHargaUpgrade();
                    int konfirmasi = JOptionPane.showConfirmDialog(aturPerkDialog,
                        "Upgrade perk '" + terpilih.getNama() + "' ke level " + (terpilih.getTingkatKesaktian() + 1) + " dengan biaya Rp" + String.format("%,d", biaya) + "?",
                        "Konfirmasi Upgrade", JOptionPane.YES_NO_OPTION);
                    if (konfirmasi == JOptionPane.YES_OPTION) {
                        if (pemain.getSaldo() >= biaya) {
                            try {
                                pemain.kurangiSaldo(biaya);
                                terpilih.upgrade();
                                JOptionPane.showMessageDialog(aturPerkDialog, "Perk '" + terpilih.getNama() + "' berhasil di-upgrade!");
                                refreshListDimiliki.run(); 
                                refreshListAktif.run();
                                listPerkDimiliki.setSelectedValue(terpilih, true); 
                            } catch (GameException ex) {
                                pemain.tambahSaldo(biaya); 
                                JOptionPane.showMessageDialog(aturPerkDialog, "Gagal upgrade: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(aturPerkDialog, "Saldo tidak cukup untuk upgrade.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });

            btnUbahAbilityPerk.addActionListener(e -> {
                Perk terpilihLama = listPerkDimiliki.getSelectedValue();
                if (terpilihLama != null) {
                    int biaya = terpilihLama.getBiayaUbahAbility();
                    String namaPerkBaruInput = JOptionPane.showInputDialog(aturPerkDialog, "Masukkan nama baru untuk perk setelah diubah (atau biarkan kosong untuk nama default):", terpilihLama.getNama());
                    if (namaPerkBaruInput == null) return; 

                    String namaPerkBaru = namaPerkBaruInput.trim().isEmpty() ? "Perk Baru" : namaPerkBaruInput.trim();


                    int konfirmasi = JOptionPane.showConfirmDialog(aturPerkDialog,
                        "Ubah ability perk '" + terpilihLama.getNama() + "' dengan biaya Rp" + String.format("%,d", biaya) + "?\nLevel akan direset ke 1.",
                        "Konfirmasi Ubah Ability", JOptionPane.YES_NO_OPTION);
                    if (konfirmasi == JOptionPane.YES_OPTION) {
                        if (pemain.getSaldo() >= biaya) {
                            try {
                                Perk perkBaruHasilUbah = terpilihLama.ubahAbility(namaPerkBaru); 
                                pemain.kurangiSaldo(biaya);
                                pemain.hapusPerkDimiliki(terpilihLama); 
                                pemain.tambahPerkDimiliki(perkBaruHasilUbah); 

                                JOptionPane.showMessageDialog(aturPerkDialog, "Ability perk berhasil diubah menjadi " + perkBaruHasilUbah.getClass().getSimpleName().replace("Perk","") + "!");
                                refreshListDimiliki.run();
                                refreshListAktif.run();
                                listPerkDimiliki.clearSelection(); 
                                infoPerkTerpilihArea.setText("Pilih perk untuk melihat detail.");
                            } catch (GameException ex) {
                                pemain.tambahSaldo(biaya);
                                JOptionPane.showMessageDialog(aturPerkDialog, "Gagal ubah ability: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(aturPerkDialog, "Saldo tidak cukup untuk ubah ability.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });

            btnAktifkanSlot1.addActionListener(e -> {
                Perk terpilih = listPerkDimiliki.getSelectedValue();
                if (terpilih != null) {
                    if (pemain.setPerkAktif(terpilih, 0)) {
                        refreshListAktif.run();
                        JOptionPane.showMessageDialog(aturPerkDialog, terpilih.getNama() + " diaktifkan di Slot 1.");
                    } else {
                         JOptionPane.showMessageDialog(aturPerkDialog, "Tidak bisa mengaktifkan perk ini di Slot 1 (mungkin sudah aktif di slot lain atau slot tidak valid).", "Gagal", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });
            btnAktifkanSlot2.addActionListener(e -> {
                Perk terpilih = listPerkDimiliki.getSelectedValue();
                 if (terpilih != null) {
                    if (pemain.setPerkAktif(terpilih, 1)) {
                        refreshListAktif.run();
                        JOptionPane.showMessageDialog(aturPerkDialog, terpilih.getNama() + " diaktifkan di Slot 2.");
                    } else {
                         JOptionPane.showMessageDialog(aturPerkDialog, "Tidak bisa mengaktifkan perk ini di Slot 2 (mungkin sudah aktif di slot lain atau slot tidak valid).", "Gagal", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });

            btnLepasSlot1.addActionListener(e -> {
                pemain.lepasPerkAktif(0);
                refreshListAktif.run();
            });
            btnLepasSlot2.addActionListener(e -> {
                pemain.lepasPerkAktif(1);
                refreshListAktif.run();
            });

            // TODO: Implementasi btnJualPerk jika diinginkan

            // --- Gabungkan Panel ---
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelKiri, panelTengah);
            splitPane.setDividerLocation(250);
            JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane, panelKanan);
            mainSplitPane.setDividerLocation(500);

            aturPerkDialog.add(mainSplitPane, BorderLayout.CENTER);

            JPanel panelInfoSaldo = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel labelSaldoPemain = new JLabel("Saldo Anda: Rp" + String.format("%,.0f", pemain.getSaldo()));
            labelSaldoPemain.setFont(fontNormal.deriveFont(Font.BOLD));
            panelInfoSaldo.add(labelSaldoPemain);
            aturPerkDialog.add(panelInfoSaldo, BorderLayout.NORTH);

            JButton btnTutupDialog = new JButton("Tutup");
            btnTutupDialog.addActionListener(e -> aturPerkDialog.dispose());
            JPanel panelTutup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            panelTutup.add(btnTutupDialog);
            aturPerkDialog.add(panelTutup, BorderLayout.SOUTH);


            aturPerkDialog.setVisible(true);
            updatePlayerInfo(); 
        }

//Custom ListCellRenderer untuk JList agar tampilan Perk lebih baik
class PerkListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Perk) {
            Perk perk = (Perk) value;
            setText(String.format("%s (Lv.%d %s)", perk.getNama(), perk.getTingkatKesaktian(), perk.getClass().getSimpleName().replace("Perk","")));
        }
        return this;
    }
}


        public void showBeliPerkScreen() {
            con.removeAll();
            setBackgroundImage("TP");
            con.add(backgroundLabel, Integer.valueOf(0));
            JLabel title = new JLabel("Toko Perk");
            title.setFont(fontJudul);
            title.setForeground(Color.WHITE);
            title.setBounds(220, 20, 600, 100);
            con.add(title, Integer.valueOf(2));

            gambarPenjual = new JLabel();
            gambarPenjual.setHorizontalAlignment(SwingConstants.CENTER);
            String pathPenjual = "Asset/Trader.png";
            gambarPenjual.setBounds(580, 0, 840, 840);
            try {
                ImageIcon iconPenjual = new ImageIcon(getClass().getResource("/GameAkhir/" + pathPenjual));
                Image imgPenjual = iconPenjual.getImage().getScaledInstance(gambarPenjual.getWidth(), gambarPenjual.getHeight(), Image.SCALE_SMOOTH);
                gambarPenjual.setIcon(new ImageIcon(imgPenjual));
                gambarPenjual.setText("");
            } catch (Exception ex) {
                gambarPenjual.setIcon(null);
                gambarPenjual.setText("Gambar tidak ditemukan");
            }
            con.add(gambarPenjual, Integer.valueOf(1));

            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
            itemPanel.setBackground(Color.WHITE);

            for (Perk perk : daftarPerk) {
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
                panel.setBackground(Color.WHITE);
                panel.setPreferredSize(new Dimension(750, 90));

                JPanel infoPanel = new JPanel(new GridLayout(2, 1));
                infoPanel.setBackground(Color.WHITE);
                infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel namaLabel = new JLabel(perk.getNama());
                namaLabel.setFont(new Font("Arial", Font.BOLD, 18));

                JLabel infoLabel = new JLabel("Efek: " + perk.getEfek() + "    Harga: Rp" + perk.getHarga());
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
                double totalHarga = perk.getHarga() * jumlahBeli;
                if (pemain.getSaldo() >= totalHarga) {
                    pemain.kurangiSaldo(totalHarga);
                    pemain.tambahPerkDimiliki(perk); 
                    JOptionPane.showMessageDialog(window, "Berhasil membeli: " + jumlahBeli + "x " + perk.getNama());
                    showBeliPerkScreen();
                } else {
                    JOptionPane.showMessageDialog(window, "Saldo tidak cukup!");
                }
            });
                tombolPanel.add(beli);


                panel.add(infoPanel, BorderLayout.CENTER);
                panel.add(tombolPanel, BorderLayout.EAST);
                itemPanel.add(panel);
            }
            itemPanel.setPreferredSize(new Dimension(600, daftarPerk.size() * 100));
            JScrollPane scroll = new JScrollPane(itemPanel);
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
        public void showBeliItemScreen() {
            con.removeAll();
            gambarPenjual = new JLabel();
            gambarPenjual.setHorizontalAlignment(SwingConstants.CENTER);
            String pathPenjual = "Asset/ItemP.png";
            gambarPenjual.setBounds(580, 0, 1040, 1040);
            try {
                ImageIcon iconPenjual = new ImageIcon(getClass().getResource("/GameAkhir/" + pathPenjual));
                Image imgPenjual = iconPenjual.getImage().getScaledInstance(gambarPenjual.getWidth(), gambarPenjual.getHeight(), Image.SCALE_SMOOTH);
                gambarPenjual.setIcon(new ImageIcon(imgPenjual));
                gambarPenjual.setText("");
            } catch (Exception ex) {
                gambarPenjual.setIcon(null);
                gambarPenjual.setText("Gambar tidak ditemukan");
            }
            con.add(gambarPenjual, Integer.valueOf(1));
            setBackgroundImage("TI");
            con.add(backgroundLabel, Integer.valueOf(0));
            JLabel title = new JLabel("Toko Item");
            title.setFont(fontJudul);
            title.setForeground(Color.WHITE);
            title.setBounds(220, 20, 600, 100);
            con.add(title, Integer.valueOf(2));

            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
            itemPanel.setBackground(Color.WHITE);

            for (Item item : daftarItem) {
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
                panel.setBackground(Color.WHITE);
                panel.setPreferredSize(new Dimension(450, 90));

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
                    pemain.tambahItem(item, jumlahBeli); 
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
            scroll.setBounds(100, 150, 600, 250);
            con.add(scroll, Integer.valueOf(2));

            JButton kembali = new JButton("Kembali");
            kembali.setFont(fontNormal);
            kembali.setBounds(800, 420, 200, 60);
            kembali.addActionListener(e -> kembaliMenu());
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
            JDialog inventoryDialog = new JDialog(window, "Manajemen Inventaris", true); 
            inventoryDialog.setSize(900, 600);
            inventoryDialog.setLocationRelativeTo(window);
            inventoryDialog.setLayout(new BorderLayout(10, 10));

            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel saldoLabel = new JLabel("Saldo: Rp" + String.format("%,.0f", pemain.getSaldo()));
            JLabel kapasitasLabel = new JLabel("Kapasitas Kendaraan: " + 
                                             pemain.getKendaraan().getInventori().values().stream().mapToInt(Integer::intValue).sum() + 
                                             "/" + pemain.getKendaraan().getKapasitas());
            topPanel.add(saldoLabel);
            topPanel.add(new JLabel("   |   ")); 
            topPanel.add(kapasitasLabel);
            inventoryDialog.add(topPanel, BorderLayout.NORTH);

            JPanel mainContentPanel = new JPanel(new GridLayout(1, 3, 10, 0)); 

            // --- KOLOM 1: STOK RUMAH ---
            JPanel panelStokRumah = new JPanel(new BorderLayout(0, 5));
            panelStokRumah.setBorder(BorderFactory.createTitledBorder("Stok di Rumah"));

            DefaultTableModel modelStokRumah = new DefaultTableModel(new String[]{"Nama Barang", "Jumlah", "Harga Beli"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; 
                }
            };
            JTable tabelStokRumah = new JTable(modelStokRumah);
            tabelStokRumah.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            Runnable populateRumahTable = () -> {
                modelStokRumah.setRowCount(0); 
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
            SpinnerModel spinnerModelJumlah = new SpinnerNumberModel(1, 1, 1, 1); 
            JSpinner spinnerJumlah = new JSpinner(spinnerModelJumlah);
            spinnerJumlah.setEnabled(false);

            JLabel labelHargaJual = new JLabel("Harga Jual Diinginkan (opsional):");

            NumberFormat format = NumberFormat.getIntegerInstance();
            format.setGroupingUsed(false);
            NumberFormatter formatter = new NumberFormatter(format);
            formatter.setValueClass(Integer.class);
            formatter.setMinimum(0);
            formatter.setMaximum(Integer.MAX_VALUE);
            formatter.setAllowsInvalid(false); 
            formatter.setCommitsOnValidEdit(true); 
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
                    return false; 
                }
            };
            JTable tabelStokKendaraan = new JTable(modelStokKendaraan);
            tabelStokKendaraan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            Runnable populateKendaraanTable = () -> {
                modelStokKendaraan.setRowCount(0);
                for (Map.Entry<Barang, Integer> entry : pemain.getKendaraan().getInventori().entrySet()) {
                    Barang barangDiKendaraan = entry.getKey();
                    modelStokKendaraan.addRow(new Object[]{
                            barangDiKendaraan.getNama(),
                            entry.getValue(),
                            String.format("%,.0f", barangDiKendaraan.getHargaJual()) 
                    });
                }
            };
            populateKendaraanTable.run();

            JButton btnKembalikanKeRumah = new JButton("Kembalikan ke Rumah");
            btnKembalikanKeRumah.setEnabled(false);
            panelStokKendaraan.add(new JScrollPane(tabelStokKendaraan), BorderLayout.CENTER);
            panelStokKendaraan.add(btnKembalikanKeRumah, BorderLayout.SOUTH);


            mainContentPanel.add(panelStokRumah);
            mainContentPanel.add(panelKontrol);
            mainContentPanel.add(panelStokKendaraan);
            inventoryDialog.add(mainContentPanel, BorderLayout.CENTER);

            JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton btnTutup = new JButton("Tutup");
            btnTutup.addActionListener(e -> inventoryDialog.dispose());
            bottomPanel.add(btnTutup);
            inventoryDialog.add(bottomPanel, BorderLayout.SOUTH);

            tabelStokRumah.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting() && tabelStokRumah.getSelectedRow() != -1) {
                    int selectedRow = tabelStokRumah.getSelectedRow();
                    Barang barangTerpilih = rumah.getStokBarangList().get(selectedRow);
                    int stokRumah = rumah.getJumlah(barangTerpilih);

                    spinnerJumlah.setEnabled(true);
                    ((SpinnerNumberModel) spinnerModelJumlah).setMaximum(stokRumah);
                    ((SpinnerNumberModel) spinnerModelJumlah).setValue(Math.min(1, stokRumah)); 

                    inputHargaJual.setEnabled(true);
                    inputHargaJual.setValue((int) barangTerpilih.getHargaJual()); 

                    btnPindahKeKendaraan.setEnabled(true);
                } else {
                    spinnerJumlah.setEnabled(false);
                    inputHargaJual.setEnabled(false);
                    inputHargaJual.setValue(null);
                    btnPindahKeKendaraan.setEnabled(false);
                }
            });

            tabelStokKendaraan.getSelectionModel().addListSelectionListener(e -> {
                btnKembalikanKeRumah.setEnabled(tabelStokKendaraan.getSelectedRow() != -1);
            });


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

                Barang barangUntukKendaraan;
                if (hargaJualKustom != barangAsliRumah.getHargaJual()) {
                    barangUntukKendaraan = new Barang(barangAsliRumah.getNama(), barangAsliRumah.getHargaBeli(), hargaJualKustom);
                } else {
                    barangUntukKendaraan = barangAsliRumah; 
                }

                rumah.kurangiStok(barangAsliRumah, jumlahPindah);
                pemain.getKendaraan().tambahBarang(barangUntukKendaraan, jumlahPindah);

                populateRumahTable.run();
                populateKendaraanTable.run();
                kapasitasLabel.setText("Kapasitas Kendaraan: " + 
                                       pemain.getKendaraan().getInventori().values().stream().mapToInt(Integer::intValue).sum() + 
                                       "/" + pemain.getKendaraan().getKapasitas());
                saldoLabel.setText("Saldo: Rp" + String.format("%,.0f", pemain.getSaldo())); 

                tabelStokRumah.clearSelection();
                if (inputHargaJual.isEnabled()) { 
                     inputHargaJual.setValue(null);
                }
            });

            btnKembalikanKeRumah.addActionListener(e -> {
                int selectedRow = tabelStokKendaraan.getSelectedRow();
                if (selectedRow == -1) return;

                String namaBarangDiKendaraan = (String) modelStokKendaraan.getValueAt(selectedRow, 0);
                int jumlahDiKendaraan = Integer.parseInt(modelStokKendaraan.getValueAt(selectedRow, 1).toString());

                Barang barangYangDikembalikan = null;
                for(Barang b : pemain.getKendaraan().getInventori().keySet()){
                    if(b.getNama().equals(namaBarangDiKendaraan) && pemain.getKendaraan().getJumlah(b) == jumlahDiKendaraan){
                         barangYangDikembalikan = b;
                         break;
                    }
                }
                if (barangYangDikembalikan == null) {
                     for(Barang b : pemain.getKendaraan().getInventori().keySet()){
                        if(b.getNama().equals(namaBarangDiKendaraan)){
                             barangYangDikembalikan = b; 
                             break;
                        }
                    }
                }

                if (barangYangDikembalikan == null) {
                     JOptionPane.showMessageDialog(inventoryDialog, "Error: Barang tidak ditemukan di kendaraan untuk dikembalikan.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int jumlahUntukDikembalikan = pemain.getKendaraan().getJumlah(barangYangDikembalikan); 
                Barang barangAsliUntukRumah = null;
                for (Barang barangMaster : daftarBarang) { 
                    if (barangMaster.getNama().equals(barangYangDikembalikan.getNama())) {
                        barangAsliUntukRumah = barangMaster;
                        break;
                    }
                }

                if (barangAsliUntukRumah == null) {
                    boolean foundInHouse = false;
                    for(Barang br : rumah.getStokBarangList()){
                        if(br.getNama().equals(barangYangDikembalikan.getNama()) && br.getHargaJual() == new Barang(br.getNama(), 0,0).getHargaJual() ){
                            barangAsliUntukRumah = br;
                            foundInHouse = true;
                            break;
                        }
                    }
                    if(!foundInHouse){
                        double hargaBeliAsli = barangYangDikembalikan.getHargaBeli(); 
                        Barang tempRefHargaJual = new Barang(barangYangDikembalikan.getNama(), 0, 0); 
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

                populateRumahTable.run();
                populateKendaraanTable.run();
                kapasitasLabel.setText("Kapasitas Kendaraan: " + 
                                       pemain.getKendaraan().getInventori().values().stream().mapToInt(Integer::intValue).sum() + 
                                       "/" + pemain.getKendaraan().getKapasitas());
                saldoLabel.setText("Saldo: Rp" + String.format("%,.0f", pemain.getSaldo())); 

                tabelStokKendaraan.clearSelection();
            });

            btnUpgradeKendaraan.addActionListener(e -> {    
                if(kendaraan.getlevel() <= 5) {
                if (pemain.getSaldo() >= kendaraan.getBiayaUpgrade()) {
                    pemain.kurangiSaldo(kendaraan.getBiayaUpgrade());
                    kendaraan.upgrade();
                    btnUpgradeKendaraan.setText("Upgrade Kendaraan (" + kendaraan.getBiayaUpgrade() + ")"); 
                    btnUpgradeKendaraan.repaint(); 

                    JOptionPane.showMessageDialog(inventoryDialog, "Kendaraan berhasil di-upgrade ke level "+kendaraan.getlevel());
                    kapasitasLabel.setText("Kapasitas Kendaraan: " + 
                                           pemain.getKendaraan().getInventori().values().stream().mapToInt(Integer::intValue).sum() + 
                                           "/" + pemain.getKendaraan().getKapasitas()); 
                    saldoLabel.setText("Saldo: Rp" + String.format("%,.0f", pemain.getSaldo()));
                } else {
                    JOptionPane.showMessageDialog(inventoryDialog, "Saldo tidak cukup untuk upgrade.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            });

            inventoryDialog.pack(); 
            inventoryDialog.setSize(Math.max(inventoryDialog.getWidth(), 900), Math.max(inventoryDialog.getHeight(), 600)); 
            inventoryDialog.setVisible(true);
        }
        public void pilihLokasi(){
            con.removeAll();
            con.add(backgroundLabel, Integer.valueOf(0));

            JButton btnKembaliBase = new JButton("Kembali ke Base");
            btnKembaliBase.setFont(fontNormal.deriveFont(18f));
            btnKembaliBase.setBounds(50, 620, 220, 50);
            btnKembaliBase.setBackground(new Color(128,128,128));
            btnKembaliBase.setForeground(Color.WHITE);
            btnKembaliBase.addActionListener(e -> GameScreen()); 
            con.add(btnKembaliBase, Integer.valueOf(1));

            JLabel titleLokasi = new JLabel("Pilih Lokasi Tujuan");
            titleLokasi.setFont(fontJudul.deriveFont(70f));
            titleLokasi.setForeground(new Color(255, 200, 0));
            titleLokasi.setBounds(0, 80, 1280, 100);
            titleLokasi.setHorizontalAlignment(SwingConstants.CENTER);
            con.add(titleLokasi, Integer.valueOf(1));
            JLabel titleLokasi1 = new JLabel("Pilih Lokasi Tujuan");
            titleLokasi1.setFont(fontJudul.deriveFont(70f));
            titleLokasi1.setForeground(Color.BLACK);
            titleLokasi1.setBounds(5, 80, 1280, 100);
            titleLokasi1.setHorizontalAlignment(SwingConstants.CENTER);
            con.add(titleLokasi1, Integer.valueOf(1));

            JButton kA = new JButton("Kota Malam");
            kA.setFont(fontNormal.deriveFont(Font.BOLD, 26f)); 
            kA.setBounds(440, 250, 400, 80);
            kA.setBackground(new Color(135, 206, 250)); 
            kA.addActionListener(e -> pindahLokasi(1)); 
            con.add(kA, Integer.valueOf(1));

            JButton kB = new JButton("Kota Victoria");
            kB.setFont(fontNormal.deriveFont(Font.BOLD, 26f));
            kB.setBounds(440, 360, 400, 80);
            kB.setBackground(new Color(255, 182, 193));
            kB.addActionListener(e -> pindahLokasi(2));
            con.add(kB, Integer.valueOf(1));

            JButton pasar = new JButton("Lorong dagang");
            pasar.setFont(fontNormal.deriveFont(Font.BOLD, 26f));
            pasar.setBounds(440, 470, 400, 80);
            pasar.setBackground(new Color(255, 228, 181));
            pasar.addActionListener(e -> pindahLokasi(3));
            con.add(pasar, Integer.valueOf(1));

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
                case 3:
                    pasar();
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
            kembali.setBounds(1000, 250, 200, 60);
            kembali.addActionListener(e -> kembaliMenu());
            con.add(kembali, Integer.valueOf(1));
        }
        public void kembaliMenu(){
            con.removeAll();
            pilihLokasi();
        }

        public void kembalidariTokoB(){
            con.removeAll();
            putarBGM("GameAkhir/Asset/Normal.wav");
            pilihLokasi();
        }
        public void AturBuff(){

        }

       public void kotaB(){
            this.kotaTransaksiSaatIni = "Kota Victoria";
            con.removeAll();
            setBackgroundImage("B");
            con.add(backgroundLabel, Integer.valueOf(0));
            buttonKembaliKota();

            JLabel judulKotaB = new JLabel("Distrik Bisnis Kota Victoria");
            judulKotaB.setFont(fontJudul.deriveFont(60f));
            judulKotaB.setForeground(Color.LIGHT_GRAY);
            judulKotaB.setHorizontalAlignment(SwingConstants.CENTER);
            judulKotaB.setBounds(0, 50, 1280, 100);
            con.add(judulKotaB, Integer.valueOf(1));
            JLabel judulKotaB2 = new JLabel("Distrik Bisnis Kota Victoria");
            judulKotaB2.setFont(fontJudul.deriveFont(60f));
            judulKotaB2.setForeground(Color.BLACK);
            judulKotaB2.setHorizontalAlignment(SwingConstants.CENTER);
            judulKotaB2.setBounds(5, 50, 1280, 100);
            con.add(judulKotaB2, Integer.valueOf(1));


            JButton btnCariPembeliDiKotaB = new JButton("Mulai Berjualan di Sini");
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
                    path = "Asset/kotaB.png";
                    break;
                case "M":
                    path = "Asset/Menu.jpeg";
                    break;
                case "S":
                    path = "Asset/Supplier.png";
                    break;
                case "TP":
                    path = "Asset/TokoPerk.png";
                    break;
                case "P":
                    path = "Asset/Pasar.png";
                    break;
                case "TI":
                    path = "Asset/TokoItem.png";
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
               return;
           }
           totalPembeliSesi = rand.nextInt(10) + 1;
           pembeliKe = 1;
           mulaiSesiPembeliBerikut();
       }

       private void mulaiSesiPembeliBerikut() {
           if (pembeliKe > totalPembeliSesi) {
               if ("Kota Malam".equals(kotaTransaksiSaatIni)) kotaA();
               else if ("Kota Victoria".equals(kotaTransaksiSaatIni)) kotaB();
               else pilihLokasi();
               return;
           }
           if(!Pembeli.peluangBertemu(pemain.getPerkAktif())){
               tidakAdaPembeli();
               pembeliKe++;
               mulaiSesiPembeliBerikut();
               return;
           }
           pembeliSaatIni = generatePembeliAcak("Pembeli dari " + this.kotaTransaksiSaatIni, pemain.getPerkAktif());
           pembeliSaatIni.resetKesabaran();

           List<Barang> barangDiKendaraanList = new ArrayList<>(pemain.getKendaraan().getInventori().keySet());
           if (barangDiKendaraanList.isEmpty()) {
               JOptionPane.showMessageDialog(window, "Aneh, kendaraan kosong padahal sudah dicek.", "Error", JOptionPane.ERROR_MESSAGE);
               if ("Kota Malam".equals(kotaTransaksiSaatIni)) kotaA();
               else if ("Kota Victoria".equals(kotaTransaksiSaatIni)) kotaB();
               else pilihLokasi();
               return;
           }
           List<Barang> preferensiPembeli = new ArrayList<>(supplier.getDaftarBarang());
           Barang barangPreferensi = pembeliSaatIni.pilihBarang(preferensiPembeli);

           barangYangDipilihPembeli = null;
           if (barangPreferensi != null) {
               for (Barang b : pemain.getKendaraan().getInventori().keySet()) {
                   if (b.getNama().equals(barangPreferensi.getNama())) {
                       barangYangDipilihPembeli = b;
                       break;
                   }
               }
           }

           if (barangYangDipilihPembeli == null) {
               JOptionPane.showMessageDialog(window, pembeliSaatIni.getNama() + " tidak menemukan barang yang dicarinya dan pergi.");
               pembeliKe++;
               mulaiSesiPembeliBerikut();
               return;
           }

           int hargaJualBarangOlehPlayer = (int) barangYangDipilihPembeli.getHargaJual();
           this.hargaTawaranAwalPembeli = pembeliSaatIni.tawarHarga(barangYangDipilihPembeli, hargaJualBarangOlehPlayer);

           tampilkanLayarNegosiasiDiCon(this.kotaTransaksiSaatIni);
       }
        public void tidakAdaPembeli() {
            JOptionPane.showMessageDialog(window, "Tidak ada pembeli yang datang di sesi ini.");
        }

         public void kotaA(){
            this.kotaTransaksiSaatIni = "Kota Malam";
            con.removeAll();
            setBackgroundImage("A");
            con.add(backgroundLabel, Integer.valueOf(0));
            buttonKembaliKota();

            JLabel judulKotaA = new JLabel("Kota Malam");
            judulKotaA.setFont(fontJudul.deriveFont(60f));
            judulKotaA.setForeground(Color.ORANGE);
            judulKotaA.setHorizontalAlignment(SwingConstants.CENTER);
            judulKotaA.setBounds(0, 50, 1280, 100);
            con.add(judulKotaA, Integer.valueOf(1));

            JButton btnCariPembeliDiKotaA = new JButton("Mulai Berjualan di Sini");
            btnCariPembeliDiKotaA.setFont(fontNormal.deriveFont(Font.BOLD, 22f));
            btnCariPembeliDiKotaA.setBounds(490, 550, 300, 70);
            btnCariPembeliDiKotaA.setBackground(new Color(0, 150, 50));
            btnCariPembeliDiKotaA.setForeground(Color.WHITE);
            btnCariPembeliDiKotaA.addActionListener(e -> {
                mulaiSesiBerjualan();
            });
            btnToko = new JButton("Toko Perk");
            btnToko.setFont(fontNormal.deriveFont(Font.BOLD, 22f));
            btnToko.setBounds(50, 250, 200, 60);
            btnToko.setBackground(new Color(47, 4, 48));
            btnToko.setForeground(Color.WHITE);
            btnToko.addActionListener(e -> {
                showBeliPerkScreen();
            });
            con.add(btnToko, Integer.valueOf(1));
            btnToko2 = new JButton("Toko Item");
            btnToko2.setFont(fontNormal.deriveFont(Font.BOLD, 22f));
            btnToko2.setBounds(50, 350, 200, 60);
            btnToko2.setBackground(new Color(47, 4, 48));
            btnToko2.setForeground(Color.WHITE);
            btnToko2.addActionListener(e -> {
                showBeliItemScreen();
            });
            con.add(btnToko2, Integer.valueOf(1));

            window.revalidate();
            window.repaint();
        }

       public void pasar(){
            con.removeAll();
            setBackgroundImage("P");
            con.add(backgroundLabel, Integer.valueOf(0));
            buttonKembaliKota();

            JLabel judulPasar = new JLabel("Lorong dagang");
            judulPasar.setFont(fontJudul.deriveFont(60f));
            judulPasar.setForeground(Color.ORANGE);
            judulPasar.setHorizontalAlignment(SwingConstants.CENTER);
            judulPasar.setBounds(0, 50, 1280, 100);
            con.add(judulPasar, Integer.valueOf(1));
            JLabel judulPasar1 = new JLabel("Lorong dagang");
            judulPasar1.setFont(fontJudul.deriveFont(60f));
            judulPasar1.setForeground(Color.BLACK);
            judulPasar1.setHorizontalAlignment(SwingConstants.CENTER);
            judulPasar1.setBounds(5, 50, 1280, 100);
            con.add(judulPasar1, Integer.valueOf(1));

            btnToko = new JButton("Toko Barang");
            btnToko.setFont(fontNormal.deriveFont(Font.BOLD, 22f));
            btnToko.setBounds(50, 250, 200, 60);
            btnToko.setBackground(new Color(47, 4, 48));
            btnToko.setForeground(Color.WHITE);
            btnToko.addActionListener(e -> {
                showBuyBarangScreen();
            });
            con.add(btnToko, Integer.valueOf(1));
           
            btnToko2 = new JButton("Toko Item");
            btnToko2.setFont(fontNormal.deriveFont(Font.BOLD, 22f));
            btnToko2.setBounds(50, 350, 200, 60);
            btnToko2.setBackground(new Color(47, 4, 48));
            btnToko2.setForeground(Color.WHITE);
            btnToko2.addActionListener(e -> {
                showBeliItemScreen();
            });
            con.add(btnToko2, Integer.valueOf(1));

            window.revalidate();
            window.repaint();
        }
       List<String> itemTersedia = new ArrayList<>();

       private void memilihItem() {
            for (Map.Entry<String, Integer> entry : pemain.getItemMap().entrySet()) {
                if (entry.getValue() > 0) {
                    itemTersedia.add(entry.getKey() + " x" + entry.getValue());
                }
            }
            if (itemTersedia.isEmpty()) {
                JOptionPane.showMessageDialog(window, "Kamu tidak punya item yang bisa digunakan.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            String itemDipilih = (String) JOptionPane.showInputDialog(
                window,
                "Pilih item yang ingin digunakan:",
                "Gunakan Item",
                JOptionPane.PLAIN_MESSAGE,
                null,
                itemTersedia.toArray(),
                itemTersedia.get(0)
            );

            if (itemDipilih == null) return; 

            String namaItem = itemDipilih.split(" x")[0];
            Item item = null;
            for (Item it : daftarItem) {
                if (it.getNama().equals(namaItem)) {
                    item = it;
                    break;
                }
            }
            if (item == null) {
                JOptionPane.showMessageDialog(window, "Item tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if ("Hipnotis".equalsIgnoreCase(item.getEfek())) {
                JOptionPane.showMessageDialog(window, "Efek Hipnotis aktif! Peluang pembeli menerima tawaran meningkat.");
                efekHipnotisAktif = true;
            } else if ("bonus".equalsIgnoreCase(item.getEfek())) {
                JOptionPane.showMessageDialog(window, "Bonus aktif! Harga jual +10%.");
                efekBonusAktif = true;
            }

            pemain.tambahItem(item, -1);
            updateInventarisKendaraanDiLayarJualCon();
        }

       public void showTokoItemPanel() {
            removeAll(); 
            setLayout(new BorderLayout());
        }


        private void tampilkanLayarNegosiasiDiCon(String namaLokasiAsal) {
            con.removeAll();
            con.add(backgroundLabel, Integer.valueOf(0));

            saldoPemainLabel_ModeJual.setText("Saldo: Rp" + String.format("%,.0f", pemain.getSaldo()));
            saldoPemainLabel_ModeJual.setBounds(1000, 20, 250, 30); // Sesuaikan bounds
            saldoPemainLabel_ModeJual.setFont(fontNormal.deriveFont(Font.BOLD, 18f));
            saldoPemainLabel_ModeJual.setForeground(Color.ORANGE);
            saldoPemainLabel_ModeJual.setBackground(Color.BLACK);
            saldoPemainLabel_ModeJual.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
            saldoPemainLabel_ModeJual.setHorizontalAlignment(SwingConstants.RIGHT);
            con.add(saldoPemainLabel_ModeJual, Integer.valueOf(1));

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

            JLabel namaPembeliNegosiasi = new JLabel(pembeliSaatIni.getNama());
            namaPembeliNegosiasi.setBounds(880, 330, 240, 30);
            namaPembeliNegosiasi.setFont(fontNormal.deriveFont(Font.BOLD, 20f));
            namaPembeliNegosiasi.setForeground(Color.CYAN);
            namaPembeliNegosiasi.setHorizontalAlignment(SwingConstants.CENTER);
            con.add(namaPembeliNegosiasi, Integer.valueOf(4));

            kesabaranPembeliLabelDiLayar.setText("Kesabaran: " + pembeliSaatIni.getKesabaranSaatIni() + "/" + pembeliSaatIni.getTingkatKesabaranDefault());
            kesabaranPembeliLabelDiLayar.setBounds(880, 360, 220, 30);
            kesabaranPembeliLabelDiLayar.setFont(fontNormal.deriveFont(16f));
            kesabaranPembeliLabelDiLayar.setForeground(Color.PINK);
            kesabaranPembeliLabelDiLayar.setHorizontalAlignment(SwingConstants.CENTER);
            con.add(kesabaranPembeliLabelDiLayar, Integer.valueOf(4));

            JLabel BorderInfoPembeli = new JLabel();
            BorderInfoPembeli.setBounds(870, 315, 260, 90);
            BorderInfoPembeli.setBackground(Color.BLACK);
            BorderInfoPembeli.setOpaque(true);
            con.add(BorderInfoPembeli, Integer.valueOf(3));

            dialogNPC.setText(pembeliSaatIni.getNama() + ": \"Saya tertarik dengan " + barangYangDipilihPembeli.getNama() + ". Saya tawar Rp" + String.format("%,d", this.hargaTawaranAwalPembeli) + ".\"");
            dialogNPC.setFont(fontNormal.deriveFont(18f));
            dialogNPC.setForeground(Color.WHITE);
            dialogNPC.setBackground(Color.BLACK); 
            dialogNPC.setOpaque(true);
            dialogNPC.setWrapStyleWord(true);
            dialogNPC.setLineWrap(true);
            dialogNPC.setEditable(false);
            dialogNPC.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            JScrollPane scrollDialog = new JScrollPane(dialogNPC);
            scrollDialog.setBounds(630, 100, 330, 90);
            scrollDialog.setOpaque(false); scrollDialog.getViewport().setOpaque(false); scrollDialog.setBorder(null);
            scrollDialog.getViewport().setBackground(new Color(0,0,0,190)); 
            con.add(scrollDialog, Integer.valueOf(1));

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
            infoBarangDitawarAreaDiLayar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); 
            infoBarangDitawarAreaDiLayar.setLineWrap(true); infoBarangDitawarAreaDiLayar.setWrapStyleWord(true);
            infoBarangDitawarAreaDiLayar.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            JScrollPane scrollInfoBarang = new JScrollPane(infoBarangDitawarAreaDiLayar);
            scrollInfoBarang.setBounds(370, 245, 400, 120);
            con.add(scrollInfoBarang, Integer.valueOf(1));
            
            JLabel labelInputHargaCon = new JLabel("Tawarkan Harga Anda (Rp):"); 
            labelInputHargaCon.setBounds(100, 290, 300, 30);
            labelInputHargaCon.setFont(fontNormal.deriveFont(Font.BOLD, 18f));
            labelInputHargaCon.setForeground(new Color(144, 238, 144));
            con.add(labelInputHargaCon, Integer.valueOf(1));

            inputHargaPlayerDiLayar.setValue((int) this.hargaTawaranAwalPembeli);
            inputHargaPlayerDiLayar.setBounds(100, 320, 220, 35);
            inputHargaPlayerDiLayar.setFont(fontNormal.deriveFont(18f));
            inputHargaPlayerDiLayar.setHorizontalAlignment(JTextField.RIGHT);
            inputHargaPlayerDiLayar.setEnabled(true);
            con.add(inputHargaPlayerDiLayar, Integer.valueOf(1));

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

            JButton btnGunakanItem = new JButton("Gunakan Item");
            btnGunakanItem.setFont(fontNormal.deriveFont(Font.BOLD, 16f));
            btnGunakanItem.setBounds(100, 75, 220, 45);
            btnGunakanItem.setBackground(new Color(75, 130, 180));
            btnGunakanItem.setForeground(Color.WHITE);
            btnGunakanItem.addActionListener(e -> memilihItem());
            con.add(btnGunakanItem, Integer.valueOf(1));

            JLabel labelInvKendaraan = new JLabel("Stok Kendaraan Anda:");
            labelInvKendaraan.setBounds(80, 380, 300, 30);
            labelInvKendaraan.setFont(fontNormal.deriveFont(Font.BOLD, 18f));
            labelInvKendaraan.setForeground(Color.ORANGE);
            con.add(labelInvKendaraan, Integer.valueOf(1));

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
            int hargaTawaranPlayer = 0;
                hargaTawaranPlayer = (Integer) inputHargaPlayerDiLayar.getValue();
                if( hargaTawaranPlayer < 0) {
                    JOptionPane.showMessageDialog(window, "Harga tawaran tidak boleh negatif!", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            pembeliSaatIni.kurangiKesabaran();
            kesabaranPembeliLabelDiLayar.setText("Kesabaran: " + pembeliSaatIni.getKesabaranSaatIni() + "/" + pembeliSaatIni.getTingkatKesabaranDefault());

            if (!pembeliSaatIni.masihSabar()) {
                dialogNPC.setText(
                pembeliSaatIni.getNama() + ": \"Sudah cukup! Saya tidak tertarik lagi.\"\n(Kesabaran habis, pergi)"
                );
                nonaktifkanKontrolJualCon("Kesabaran Habis");
                return;
            }

            boolean terimaTawaranPlayer = false;
            double persenHipnotis = hipnotisDigunakan();
            if(terimaTawaranPlayer == false){
                terimaTawaranPlayer = pembeliSaatIni.putuskanBeli(hargaTawaranPlayer, this.hargaTawaranAwalPembeli, this.pemain.getPerkAktif(), persenHipnotis);
            }
            if (terimaTawaranPlayer) {
                hargaTawaranPlayer = bonusdigunakan(hargaTawaranPlayer);
                prosesPenjualanBerhasilCon(hargaTawaranPlayer);
            } else {
                int hargaTawaranBaruPembeli = pembeliSaatIni.tawarHarga(barangYangDipilihPembeli, hargaTawaranPlayer);
                if (hargaTawaranBaruPembeli >= hargaTawaranPlayer && !(pembeliSaatIni instanceof PembeliTajir && Math.random() < 0.15)) { 
                    dialogNPC.setText(
                        pembeliSaatIni.getNama() + ": \"Hmm, harga Rp" + String.format("%,d", hargaTawaranPlayer) + " masih terlalu tinggi untuk saya.\"\n(Tidak menawar lagi)"
                    );
                } else {
                    this.hargaTawaranAwalPembeli = hargaTawaranBaruPembeli;
                    dialogNPC.setText(
                        pembeliSaatIni.getNama() + ": \"Bagaimana kalau Rp" + String.format("%,d", this.hargaTawaranAwalPembeli) + "?\""
                    );
                }
            }
        }
        private double hipnotisDigunakan(){
            double status = item.efekHipnotis(efekHipnotisAktif);
            efekHipnotisAktif = false;
            return status;
        }
        private void handleJualDenganHargaInputCon() {
            if (inputHargaPlayerDiLayar.getValue() == null) {
                JOptionPane.showMessageDialog(window, "Masukkan harga jual Anda!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int hargaFinalPlayer = (Integer) this.hargaTawaranAwalPembeli;

            boolean keputusanFinalPembeli = false;
            double persenHipnotis = hipnotisDigunakan();
            if(keputusanFinalPembeli == false){ 
                keputusanFinalPembeli = pembeliSaatIni.putuskanBeli(hargaFinalPlayer, this.hargaTawaranAwalPembeli, this.pemain.getPerkAktif(), persenHipnotis);
            }
            if (keputusanFinalPembeli) {
                hargaFinalPlayer = bonusdigunakan(hargaFinalPlayer);
                prosesPenjualanBerhasilCon(hargaFinalPlayer);
            } else {
                pembeliSaatIni.kurangiKesabaran();
                kesabaranPembeliLabelDiLayar.setText("Kesabaran: " + pembeliSaatIni.getKesabaranSaatIni() + "/" + pembeliSaatIni.getTingkatKesabaranDefault());
                if (!pembeliSaatIni.masihSabar()) {
                    dialogNPC.setText(
                        pembeliSaatIni.getNama() + ": \"Saya rasa kita tidak akan sepakat.\"\n(Kesabaran habis, pergi)"
                    );
                    nonaktifkanKontrolJualCon("Kesabaran Habis");
                } else {
                    dialogNPC.setText(
                        pembeliSaatIni.getNama() + ": \"Maaf, harga Rp" + String.format("%,d", hargaFinalPlayer) + " masih terlalu tinggi buat saya.\""
                    );
                }
            }
        }
        private int bonusdigunakan(int hargaTawaranPlayer) {
            int hargaJual = hargaTawaranPlayer;
            if (efekBonusAktif) {
            hargaJual = (int) (hargaTawaranPlayer * 1.1);
            efekBonusAktif = false;
            }
            return hargaJual;
        }
        private void prosesPenjualanBerhasilCon(int hargaTerjual) {
            pemain.tambahSaldo(hargaTerjual);
            pemain.getKendaraan().kurangiBarang(barangYangDipilihPembeli, 1);

           dialogNPC.setText(
            pembeliSaatIni.getNama() + ": \"Setuju! Terima kasih banyak.\"\n(Barang terjual seharga Rp" + String.format("%,d", hargaTerjual) + ")");

            updateInventarisKendaraanDiLayarJualCon();
            updateSaldoDiLayarJualCon();
            nonaktifkanKontrolJualCon("Transaksi Selesai");
            JOptionPane.showMessageDialog(window,
                    "Berhasil menjual " + barangYangDipilihPembeli.getNama() + " kepada " + pembeliSaatIni.getNama() + " seharga Rp" + String.format("%,d", hargaTerjual) + "!",
                    "Transaksi Berhasil", JOptionPane.INFORMATION_MESSAGE);

            perkDrop();
        }

        private void nonaktifkanKontrolJualCon(String alasanSelesai) {
            btnTawarDiLayar.setEnabled(false);
            btnJualDiLayar.setEnabled(false);
            inputHargaPlayerDiLayar.setEnabled(false);
            btnBatalJualDiLayar.setText("Lanjut (" + alasanSelesai.substring(0, Math.min(alasanSelesai.length(), 10))+"..)");
            btnBatalJualDiLayar.setBackground(new Color(0,100,0));

            for(ActionListener al : btnBatalJualDiLayar.getActionListeners()) btnBatalJualDiLayar.removeActionListener(al);
            btnBatalJualDiLayar.addActionListener(e -> {
                pembeliKe++;
                mulaiSesiPembeliBerikut();
            });
        }
        private void handleAkhiriSesiDenganPembeliCon(boolean dibatalkanOlehPemain, String kotaKembali) {
            if (dibatalkanOlehPemain && pembeliSaatIni != null && barangYangDipilihPembeli != null && btnTawarDiLayar.isEnabled()) {
                int konfirmasi = JOptionPane.showConfirmDialog(window,
                        "Yakin ingin mengakhiri sesi dengan " + pembeliSaatIni.getNama() + "?",
                        "Akhiri Sesi", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (konfirmasi == JOptionPane.NO_OPTION) {
                    return;
                }
               if(dialogNPC != null) {
                    dialogNPC.setText(
                    pembeliSaatIni.getNama() + ": \"Baiklah kalau begitu...\" (Kecewa dan pergi)"
                    );
                }
            }
            
            if ("Kota Malam".equals(kotaKembali)) kotaA();
            else if ("Kota Victoria".equals(kotaKembali)) kotaB(); 
            else pilihLokasi(); 
        }

        private void updateInventarisKendaraanDiLayarJualCon() {
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
            if (saldoPemainLabel_ModeJual != null)
                saldoPemainLabel_ModeJual.setText("Saldo: Rp" + String.format("%,.0f", pemain.getSaldo()));
        }
        
        private Pembeli generatePembeliAcak(String namaTambahan, List<Perk> perk) { 
            String[] daftarNama = {"Nel", "Budi", "Mike", "Dodi", "Brody", "Bill", "Wowo", "Yuanwu", "Cal", "Nick", "John"};
            String namaAcak = daftarNama[new Random().nextInt(daftarNama.length)] + " " + namaTambahan; 
            double buff = 0;
            double chance = Math.random();
            if (perk != null) {
                for (Perk p : perk) {
                    if (p instanceof PerkElegan) {
                        buff += p.getEfek();
                    }
                }
                if(buff > 0.5){
                    buff = 0.5; 
                }
            }
            chance += buff;
            if (chance < 0.35) return new PembeliMiskin(namaAcak); 
            else if (chance < 0.75) return new PembeliStandard(namaAcak);
            else return new PembeliTajir(namaAcak);
        }   
            private void perkDrop() {
            int chance = rand.nextInt(20) + 1; 
            if (chance == 1 && !daftarPerk.isEmpty()) { 
                Perk perkBaru = daftarPerk.get(rand.nextInt(daftarPerk.size()));
                    pemain.tambahPerkDimiliki(perkBaru);
                    JOptionPane.showMessageDialog(window, "Selamat! Kamu mendapatkan perk baru: " + perkBaru.getNama());
            }
        }
         public void save(){
              boolean sukses = saveFile.simpanProgress(this.pemain, this.kendaraan, this.rumah);
                 if (sukses) {
                     JOptionPane.showMessageDialog(window, "Game berhasil disimpan!", "Simpan Game", JOptionPane.INFORMATION_MESSAGE);
                 } else {
                     JOptionPane.showMessageDialog(window, "Gagal menyimpan game.", "Error Simpan", JOptionPane.ERROR_MESSAGE);
                 }
             }
         }
