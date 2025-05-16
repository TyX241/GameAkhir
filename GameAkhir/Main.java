package GameAkhir;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

public class Main {
    public static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        //testGame();
        new Game();
    }
    public void GameScreen(){
        
    }
    public static void testGame(){
        String nama;
        Player pemain = new Player();
        Rumah rumah = new Rumah();
        ArrayList<Barang> daftarBarang = new ArrayList<>();
        Supplier supplier = new Supplier(daftarBarang, rumah, pemain);
        System.out.println("Masukan nama pemain:");
        nama = in.next();
        pemain.setNama(nama);
        
        supplier.beliBarang();
        pemain.tampilan();
        rumah.infoStok();
    }
}
     class Game{
        Player pemain;
        JFrame window;
        Container con;
        JPanel judulPanel, tombolStartPanel, mainTextPanel, tombolPilihanPanel, panelPemain;
        JLabel judulLabel, InfoLabel;
        JButton tombolStart, pil1, pil2, pil3, pil4;
        Font fontJudul = new Font("Times New Roman",Font.PLAIN, 90);
        Font fontNormal = new Font("Times New Roman",Font.PLAIN, 30);
        JTextArea mainTextArea;

        TitleScreenHandler tsHandler = new TitleScreenHandler();

        public Game(){
        pemain = new Player();
        window = new JFrame();
        window.setSize(1080, 720);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);
        window.setLayout(null);
        
        con = window.getContentPane();
        String nama = JOptionPane.showInputDialog("Masukkan nama pemain:");
        pemain.setNama(nama);
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
        
        mainTextArea = new JTextArea("Rumah");
        mainTextArea.setBounds(100, 100, 600, 250);
        mainTextArea.setBackground(Color.BLACK);
        mainTextArea.setForeground(Color.WHITE);
        mainTextArea.setFont(fontNormal);
        mainTextArea.setLineWrap(true);
        mainTextPanel.add(mainTextArea);

        tombolPilihanPanel = new JPanel();
        tombolPilihanPanel.setBounds(750, 100, 200, 350);
        tombolPilihanPanel.setBackground(Color.BLACK);
        tombolPilihanPanel.setLayout(new GridLayout(4, 1));
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

        panelPemain = new JPanel();
        panelPemain.setBounds(100, 400, 600, 250);
        panelPemain.setBackground(Color.BLUE);
        panelPemain.setLayout(new BorderLayout());
        con.add(panelPemain);

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

        window.revalidate();
        window.repaint();
    }

    public class TitleScreenHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            GameScreen();
        }
    }
     
}
