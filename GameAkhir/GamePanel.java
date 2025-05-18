package GameAkhir;

import java.util.ArrayList;
import java.util.Map;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class GamePanel {
        //Konstruktor
        String nama;
        Player pemain = new Player();
        Rumah rumah = new Rumah();
        
        Kendaraan kendaraan = new Kendaraan("Trek",5,1);
        ArrayList<Barang> daftarBarang = new ArrayList<>();
        ArrayList<Item> daftarItem = new ArrayList<>();
        Peta peta = new Peta();
        TokoItem tokoItem = new TokoItem(daftarItem, pemain, rumah, kendaraan);
        Supplier supplier = new Supplier(daftarBarang, rumah, pemain, this);
       /*ystem.out.print("Masukan nama pemain:");
        nama = in.next();
        in.nextLine();*/



        JFrame window;
        Container con;
        JPanel namaPanel, judulPanel, tombolStartPanel, mainTextPanel, tombolPilihanPanel, panelPemain;
        JLabel judulLabel, InfoLabel;
        JButton tombolStart, pil1, pil2, pil3, pil4, pil5;
        Font fontJudul = new Font("Times New Roman",Font.PLAIN, 90);
        Font fontNormal = new Font("Times New Roman",Font.PLAIN, 20);
        JTextArea mainTextArea;
        String position;

        TitleScreenHandler tsHandler = new TitleScreenHandler();
        private ArrayList<JSpinner> spinners;
        public GamePanel(){
        pemain.setKendaraan(kendaraan);
        window = new JFrame();
        window.setSize(1080, 720);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);
        window.setLayout(null);
        
        con = window.getContentPane();
        namaPanel = new JPanel();
        namaPanel.setBounds(450,300,150,25);
        namaPanel.setBackground(Color.WHITE);
        namaPanel.setForeground(Color.WHITE);
        JTextField textField = new JTextField(10); // 20 = lebar karakter
        namaPanel.add(new JLabel("Nama:"));
        namaPanel.add(textField);
        con.add(namaPanel);
        textField.addActionListener(e -> {
            String nama = textField.getText();
            pemain.setNama(nama); //input nama pemain
            
            // Hilangkan panel atau tutup window
            namaPanel.setVisible(false);
        });
        
        judulPanel = new JPanel();
        judulPanel.setBounds(220,100,600,150);
        judulPanel.setBackground(Color.BLACK);
        judulLabel = new JLabel("SHOP");
        judulLabel.setForeground(Color.WHITE);
        //judulPanel.setOpaque(false);
        judulLabel.setFont(fontJudul);
        
        
        tombolStartPanel = new JPanel();
        tombolStartPanel.setBounds(420, 400, 200, 100);
        tombolStartPanel.setBackground(Color.BLACK);
        
        tombolStart = new JButton("START");
        tombolStart.setBackground(Color.black);
        tombolStart.setForeground(Color.white);
        tombolStart.setFont(fontNormal);
        tombolStart.addActionListener(tsHandler);

        judulPanel.add(judulLabel);
        tombolStartPanel.add(tombolStart);
        con.add(judulPanel);
        con.add(tombolStartPanel);

        window.setVisible(true);
     }
     public void GameScreen(){

        judulPanel.setVisible(false);
        tombolStartPanel.setVisible(false);

        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(100, 100, 600, 250);
        mainTextPanel.setBackground(Color.BLUE);
        con.add(mainTextPanel);
        
        mainTextArea = new JTextArea(" ");
        mainTextArea.setBounds(100, 100, 600, 250);
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
        pil1.setBackground(Color.black);
        pil1.setForeground(Color.white);
        pil1.setFont(fontNormal);
        tombolPilihanPanel.add(pil1);
        pil2 = new JButton("Pilihan 2");
        pil2.setBackground(Color.black);
        pil2.setForeground(Color.white);
        pil2.setFont(fontNormal);
        tombolPilihanPanel.add(pil2);
        pil3 = new JButton("Pilihan 3");
        pil3.setBackground(Color.black);
        pil3.setForeground(Color.white);
        pil3.setFont(fontNormal);
        tombolPilihanPanel.add(pil3);
        pil4 = new JButton("Pilihan 4");
        pil4.setBackground(Color.black);
        pil4.setForeground(Color.white);
        pil4.setFont(fontNormal);
        tombolPilihanPanel.add(pil4);
        pil5 = new JButton("Pilihan 5");
        pil5.setBackground(Color.black);
        pil5.setForeground(Color.white);
        pil5.setFont(fontNormal);
        tombolPilihanPanel.add(pil5);

        panelPemain = new JPanel();
        panelPemain.setBounds(100, 400, 600, 250);
        panelPemain.setBackground(Color.BLUE);
        panelPemain.setLayout(new BorderLayout());
        con.add(panelPemain);

        //Aksi tombol pilihan
        pil1.addActionListener(e -> handleMenu(1));
        pil2.addActionListener(e -> handleMenu(2));
        pil3.addActionListener(e -> handleMenu(3));
        pil4.addActionListener(e -> handleMenu(4));
        pil5.addActionListener(e -> handleMenu(5));

        String info = pemain.tampilan();
        JTextArea infoArea = new JTextArea(info);
        infoArea.setFont(fontNormal);
        infoArea.setEditable(false);
        infoArea.setBackground(Color.BLACK);
        infoArea.setForeground(Color.WHITE);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);

         JScrollPane scroll = new JScrollPane(infoArea);
        // Pastikan scroll fill panel
        panelPemain.add(scroll, BorderLayout.CENTER);
        homeChoice();
        window.revalidate();
        window.repaint();
    }

    public class TitleScreenHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            GameScreen();
        }
    }
    public void beliScreen(){

    }
    public void homeChoice(){
        position = "rumah";
        pil1.setText("Beli Barang");
        pil2.setText("Beli Item");
        pil3.setText("Info Pemain");
        pil4.setText("Info Stok");
        pil5.setText("Berjualan");
    }
    public class choiceHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String pilihan = e.getActionCommand();
            switch(pilihan){
                case "Pilihan 1":
                    handleMenu(1);
                    break;
                case "Pilihan 2":
                    handleMenu(2);
                    break;
                case "Pilihan 3":
                    handleMenu(3);
                    break;
                case "Pilihan 4":
                    handleMenu(4);
                    break;
            }
        }
    }
    private void handleMenu(int pilihan) {
        switch(pilihan) {
            case 1:
                showBuyBarangScreen();
                updatePlayerInfo();
                break;
            case 2:
                tokoItem.beliItem(kendaraan);
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
    private void updatePlayerInfo() {
        String info = pemain.tampilan();
        JTextArea infoArea = (JTextArea) ((JScrollPane) panelPemain.getComponent(0)).getViewport().getView();
        infoArea.setText(info);
    }
     public void showBuyBarangScreen() {
       con.removeAll();

        // Judul
        JLabel title = new JLabel("SHOP");
        title.setFont(fontJudul);
        title.setForeground(Color.WHITE);
        title.setBounds(220, 20, 600, 150);
        con.add(title);

        // Panel daftar barang dengan spinner
        JPanel listPanel = new JPanel(new GridLayout(daftarBarang.size(), 3, 10, 10));
        listPanel.setBounds(50, 150, 700, 350);
        listPanel.setBackground(Color.WHITE);
        spinners = new ArrayList<>();
        for (Barang b : daftarBarang) {
            listPanel.add(new JLabel(b.getNama()));
            listPanel.add(new JLabel("Rp" + b.getHargaBeli()));
            JSpinner sp = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
            spinners.add(sp);
            listPanel.add(sp);
        }
        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBounds(100, 200, 500, 150);
        con.add(scroll);

        // Tombol Beli dan Batal
        JButton btnBeli = new JButton("Beli");
        btnBeli.setFont(fontNormal);
        btnBeli.setBounds(800, 100, 200, 60);
        btnBeli.addActionListener(e -> processAllPurchases());
        con.add(btnBeli);

        JButton btnBatal = new JButton("Batal");
        btnBatal.setFont(fontNormal);
        btnBatal.setBounds(800, 200, 200, 60);
        btnBatal.addActionListener(e -> resetSpinners());
        con.add(btnBatal);

        JButton kembali = new JButton("Kembali");
        kembali.setFont(fontNormal);
        kembali.setBounds(800, 300, 200, 60);
        kembali.addActionListener(e -> kembali());
        con.add(kembali);

        // Info pemain
        JTextArea infoArea = new JTextArea(pemain.tampilan());
        infoArea.setFont(fontNormal);
        infoArea.setEditable(false);
        infoArea.setBackground(Color.BLACK);
        infoArea.setForeground(Color.WHITE);
        infoArea.setLineWrap(true);
        JScrollPane infoScroll = new JScrollPane(infoArea);
        infoScroll.setBounds(50, 520, 950, 140);
        con.add(infoScroll);

        window.revalidate();
        window.repaint();
    }

    private void processAllPurchases() {
        for (int i = 0; i < daftarBarang.size(); i++) {
            int qty = (int) spinners.get(i).getValue();
            if (qty > 0) {
                supplier.processPurchase(i, qty);
            }
        }
        con.removeAll();
        GameScreen();
    }
    private void tampilkanStok(Rumah rumah) {
    con.removeAll();
    JTextArea infoArea = new JTextArea("Stok Barang di Rumah:\n");
    infoArea.setFont(fontNormal);
    infoArea.setEditable(false);
    infoArea.setBackground(Color.BLACK);
    infoArea.setForeground(Color.WHITE);
    infoArea.setLineWrap(true);

    Map<Barang, Integer> stok = rumah.getStokBarang();
    for (Map.Entry<Barang, Integer> entry : stok.entrySet()) {
        infoArea.append(entry.getKey().getNama() + ": " + entry.getValue() + "\n");
    }

    JScrollPane infoScroll = new JScrollPane(infoArea);
    infoScroll.setBounds(50, 150, 700, 350);
    con.add(infoScroll);

    JButton kembali = new JButton("Kembali");
    kembali.setFont(fontNormal);
    kembali.setBounds(800, 300, 200, 60);
    kembali.addActionListener(e -> kembali());
    con.add(kembali);

    window.revalidate();
    window.repaint();
    }
    public void Berjualan(){
        con.removeAll();
        JTextArea infoArea = new JTextArea("Berjualan");
        infoArea.setFont(fontNormal);
        infoArea.setEditable(false);
        infoArea.setBackground(Color.BLACK);
        infoArea.setForeground(Color.WHITE);
        infoArea.setLineWrap(true);

        JScrollPane infoScroll = new JScrollPane(infoArea);
        infoScroll.setBounds(50, 150, 700, 350);
        con.add(infoScroll);

        JButton kembali = new JButton("Kembali");
        kembali.setFont(fontNormal);
        kembali.setBounds(800, 300, 200, 60);
        kembali.addActionListener(e -> kembali());
        con.add(kembali);

        window.revalidate();
        window.repaint();
    }

    private void resetSpinners() {
        for (JSpinner sp : spinners) sp.setValue(0);
    }
    private void kembali(){
        con.removeAll();
        GameScreen();
    }
    public void showPilihLokasiScreen() {
    con.removeAll();

    JLabel label = new JLabel("Pilih Lokasi Tujuan:");
    label.setFont(fontNormal);
    label.setForeground(Color.WHITE);
    label.setBounds(100, 50, 300, 40);
    con.add(label);

    // Ambil daftar lokasi dari objek peta
    String[] lokasiArr = peta.getLokasiList().toArray(new String[0]);
    JComboBox<String> lokasiCombo = new JComboBox<>(lokasiArr);
    lokasiCombo.setBounds(100, 100, 300, 40);
    lokasiCombo.setSelectedIndex(peta.getPosisiSekarang());
    con.add(lokasiCombo);

    JButton btnPindah = new JButton("Pindah");
    btnPindah.setFont(fontNormal);
    btnPindah.setBounds(100, 160, 150, 40);
    btnPindah.addActionListener(e -> {
        int idx = lokasiCombo.getSelectedIndex();
        if (idx != peta.getPosisiSekarang()) {
            peta.pindahLokasi(idx);
            JOptionPane.showMessageDialog(window, "Pindah ke: " + peta.getLokasiSekarang());
        } else {
            JOptionPane.showMessageDialog(window, "Sudah di lokasi tersebut.");
        }
        GameScreen();
    });
    con.add(btnPindah);

    JButton kembali = new JButton("Kembali");
    kembali.setFont(fontNormal);
    kembali.setBounds(270, 160, 150, 40);
    kembali.addActionListener(e -> kembali());
    con.add(kembali);

    window.revalidate();
    window.repaint();
}

}
