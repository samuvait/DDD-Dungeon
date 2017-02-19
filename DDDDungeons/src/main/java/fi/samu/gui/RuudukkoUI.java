package fi.samu.gui;

import fi.samu.logiikka.LuolaGeneraattori;
import fi.samu.mekaniikat.Liikkuminen;
import fi.samu.mekaniikat.Otus;
import fi.samu.mekaniikat.Pelaaja;
import fi.samu.mekaniikat.Viholliset;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
/**
 *
 * @author Samu
 */
public class RuudukkoUI extends JFrame {
    
    private int sivunPituus;
    private int[][] kartta;
    private LuolaGeneraattori lg;
    private final ArrayList<JPanel> list;
    private Viholliset viholliset;
    private Liikkuminen liikkuminen;
    private JFrame f;
    
    public RuudukkoUI(int sivunPituus) {
        lg  = new LuolaGeneraattori(25);
        liikkuminen = new Liikkuminen(lg);
        viholliset = liikkuminen.getViholliset();
        kartta = lg.getKartta();
        this.list = new ArrayList();
        this.sivunPituus = sivunPituus;
        setResizable(false);
    }
    
    private JPanel createGridPanel() {
        JPanel p = new JPanel(new GridLayout(sivunPituus, sivunPituus));
        for (int i = 0; i < sivunPituus * sivunPituus; i++) {
            JPanel jp = new JPanel();
            jp.setPreferredSize(new Dimension(35, 35));
            Border border = BorderFactory.createLineBorder(Color.GRAY);
            jp.setBorder(border);
            list.add(jp);
            p.add(jp);
        }
        paivita();
        return p;
    }
    
    public void paivita() {
        kartta = lg.getKartta();
        viholliset = liikkuminen.getViholliset();
        f.invalidate();
        for (int i = 0; i < sivunPituus; i++) {
            for (int j = 0; j < sivunPituus; j++) {
                int index = (j * sivunPituus) + i;
                JPanel pan = list.get(index);
                pan.removeAll();
                if (kartta[j][i] == 0) {
                    pan.setBackground(Color.black);
                } else {
                    pan.setBackground(Color.white);
                    Font font = new Font("Courier", Font.BOLD,10);
                    Font hpFont = new Font("Courier", Font.PLAIN,8);
                    Pelaaja p = this.liikkuminen.getPelaaja();
                    if (i == p.getKoordinaatit().getX() && j == p.getKoordinaatit().getY()) {
                        JLabel lbl = new JLabel("@");
                        lbl.setFont(font);
                        lbl.setBorder(BorderFactory.createEmptyBorder( -3 /*top*/, 0, 0, 0 ));
                        JLabel hp = new JLabel(p.getHitPoints() + "/" + p.getHitPointsMax());
                        hp.setFont(hpFont);
                        hp.setVerticalTextPosition(JLabel.BOTTOM);
                        pan.add(lbl);
                        pan.add(hp);
                    } else if (this.viholliset.tarkistaOtukset(i, j)) {
                        Otus o = this.viholliset.palautaPiirrettava(i, j);
                        JLabel lbl = new JLabel(o.getTunnus());
                        lbl.setFont(font);
                        lbl.setBorder(BorderFactory.createEmptyBorder( -3 /*top*/, 0, 0, 0 ));
                        JLabel hp = new JLabel(o.getHitPoints() + "/" + o.getHitPointsMax());
                        hp.setFont(hpFont);
                        hp.setVerticalAlignment(JLabel.BOTTOM);
                        pan.add(lbl);
                        pan.add(hp);
                    } else {
                        if (kartta[j][i] == 5) {
                            JLabel lbl = new JLabel("%");
                            pan.add(lbl);
                        }
                    }
                }
            }
        }
        f.revalidate();
        f.repaint();
    }
//    public static void main(String[] args) {
//        try {
//            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        } catch (UnsupportedLookAndFeelException ex) {
//            ex.printStackTrace();
//        } catch (IllegalAccessException ex) {
//            ex.printStackTrace();
//        } catch (InstantiationException ex) {
//            ex.printStackTrace();
//        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
//        }
////        /* Turn off metal's use of bold fonts */
//        UIManager.put("swing.boldMetal", Boolean.FALSE);
////        
////        //Schedule a job for the event dispatch thread:
////        //creating and showing this application's GUI.
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new RuudukkoUI(25).display();
//            }
//        });
//    }
    
    public void display() {
//        //Create and set up the window.
        f = new JFrame("DDD-Dungeon");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(createGridPanel());
        Kuuntelija kuuntelija = new Kuuntelija(this.liikkuminen, this);
        f.addKeyListener(kuuntelija);
//        //Set up the content pane.
//        //Display the window.
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
    public Liikkuminen getLiikkuminen() {
        return this.liikkuminen;
    }
}
