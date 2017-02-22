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
    private ArrayList<JPanel> list;
    private Viholliset viholliset;
    private Liikkuminen liikkuminen;
    private JFrame f;
    private Kuuntelija k;
    private JPanel combatLog;
    private ArrayList<JLabel> lblLista;
    private int dmn;

    public RuudukkoUI(int sivunPituus) {
        lg = new LuolaGeneraattori(sivunPituus);
        liikkuminen = new Liikkuminen(lg);
        viholliset = liikkuminen.getViholliset();
        kartta = lg.getKartta();
        this.sivunPituus = sivunPituus;
        this.dmn = 30;
    }

    private JPanel luoRuudukkoPaneeli() {
        this.list = new ArrayList();
        JPanel p = new JPanel(new GridLayout(sivunPituus, sivunPituus));
        for (int i = 0; i < sivunPituus * sivunPituus; i++) {
            JPanel jp = new JPanel();
            jp.setPreferredSize(new Dimension(dmn, dmn));
            Border border = BorderFactory.createLineBorder(Color.GRAY);
            jp.setBorder(border);
            list.add(jp);
            p.add(jp);
        }
        paivita(new ArrayList<String>());
        return p;
    }
    
    private JPanel luoRuudukkoBagPaneeli() {
        this.list = new ArrayList();
        JPanel p = new JPanel(new GridBagLayout());
        for (int i = 0; i < sivunPituus; i++) {
            for (int j = 0; j < sivunPituus; j++) {
                JPanel jp = new JPanel();
                jp.setPreferredSize(new Dimension(dmn, dmn));
                Border border = BorderFactory.createLineBorder(Color.GRAY);
                jp.setBorder(border);
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = j;
                c.gridy = i;
                list.add(jp);
                p.add(jp, c);
            }
        }
        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        jp.setPreferredSize(new Dimension(200, dmn * sivunPituus));
        GridBagConstraints c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.weightx = 5;
        c.gridx = 25;
        c.gridy = 0;
        c.weightx = 1;
        c.gridheight = sivunPituus;
        p.add(jp, c);
        combatLog = jp;
        lisaaLabelit();
        paivita(new ArrayList<String>());
        return p;
    }
    
    public void lisaaLabelit() {
        this.combatLog.removeAll();
        this.lblLista = new ArrayList();
        for (int i = 0; i < 47; i ++) {
            JLabel lbl = new JLabel(" ");
            this.combatLog.add(lbl);
            lblLista.add(lbl);
        }
        combatLog.revalidate();
        combatLog.repaint();
    }
    
    public void MuutaLabelit(ArrayList<String> tekstit) {
        ArrayList<String> muutettavat = new ArrayList<String>();
        for (int i = 46; i >= 0; i--) {
            JLabel lbl = lblLista.get(i);
            String muut = lbl.getText();
            muutettavat.add(muut);
        }
        int i = tekstit.size() - 1;
        int h = 46;
        while (i >= 0 && h >= 0) {
            String s = tekstit.get(i);
            JLabel lbl = lblLista.get(h);
            lbl.setText(s);
            h--;
            i--;
        }
        int j = 0;
        while (h >= 0 && j < muutettavat.size()) {
            String s = muutettavat.get(j);
            JLabel lbl = lblLista.get(h);
            lbl.setText(s);
            h--;
            j++;
        }
    }

    public void paivita(ArrayList<String> tekstit) {
        kartta = lg.getKartta();
        viholliset = liikkuminen.getViholliset();
        String kerros = "" + liikkuminen.getKerros();
        f.invalidate();
        for (int i = 0; i < sivunPituus; i++) {
            for (int j = 0; j < sivunPituus; j++) {
                int index = (j * sivunPituus) + i;
                JPanel pan = list.get(index);
                pan.removeAll();
                if (i == 0 && j == 0) {
                    pan.setBackground(Color.gray);
                    Font font = new Font("Courier", Font.PLAIN, 8);
                    JLabel lbl = new JLabel("Floor");
                    lbl.setFont(font);
                    lbl.setBorder(BorderFactory.createEmptyBorder(-3 /*top*/, 0, 0, 0));
                    JLabel krs = new JLabel(kerros);
                    krs.setVerticalTextPosition(JLabel.BOTTOM);
                    Font f = new Font("Courier", Font.PLAIN, 10);
                    krs.setFont(f);
                    pan.add(lbl);
                    pan.add(krs);
                } else if (kartta[j][i] == 0) {
                    pan.setBackground(Color.black);
                } else {
                    pan.setBackground(Color.white);
                    Font font = new Font("Courier", Font.BOLD, 8);
                    Font hpFont = new Font("Courier", Font.PLAIN, 8);
                    Pelaaja p = this.liikkuminen.getPelaaja();
                    if (i == p.getKoordinaatit().getX() && j == p.getKoordinaatit().getY()) {
                        JLabel lbl = new JLabel("@");
                        lbl.setFont(font);
                        lbl.setBorder(BorderFactory.createEmptyBorder(-3 /*top*/, 0, 0, 0));
                        JLabel hp = new JLabel(p.getHitPoints() + "/" + p.getHitPointsMax());
                        hp.setFont(hpFont);
                        hp.setVerticalTextPosition(JLabel.BOTTOM);
                        pan.add(lbl);
                        pan.add(hp);
                    } else if (this.viholliset.tarkistaOtukset(i, j)) {
                        Otus o = this.viholliset.palautaPiirrettava(i, j);
                        JLabel lbl = new JLabel(o.getTunnus());
                        lbl.setFont(font);
                        lbl.setBorder(BorderFactory.createEmptyBorder(-3 /*top*/, 0, 0, 0));
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
        if (tekstit.size() > 0) {
            MuutaLabelit(tekstit);
        }
        f.revalidate();
        f.repaint();
        this.kuoliko();
        this.lvlUp();
        
    }

    public void nayta() {
        f = new JFrame("DDD-Dungeon");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(luoRuudukkoBagPaneeli());
        k = new Kuuntelija(this.liikkuminen, this);
        f.addKeyListener(k);
        f.setResizable(false);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public Liikkuminen getLiikkuminen() {
        return this.liikkuminen;
    }
    
    public void kuoliko() {
        Object[] vaihtoEhdot = {
            "Restart",
            "Give up"
        };
        Pelaaja p = this.liikkuminen.getPelaaja();
        if (p.getHitPoints() < 1) {
            int reply = JOptionPane.showOptionDialog(f, "You have died! Do you want to restart?", "Death", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, vaihtoEhdot, vaihtoEhdot[0]);
            if (reply == 0) {
                lg = new LuolaGeneraattori(sivunPituus);
                liikkuminen = new Liikkuminen(lg);
                viholliset = liikkuminen.getViholliset();
                kartta = lg.getKartta();
                k.setLiikkuminen(liikkuminen);
                this.tyhjennaLogi();
                this.lisaaLabelit();
                this.paivita(new ArrayList<String>());
            } else {
               f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
            }
       }
    }
    
    public void tyhjennaLogi() {
        for (JLabel lbl : this.lblLista) {
            lbl.setText("");
        }
    }
    
    public void lvlUp() {
        Pelaaja p = this.liikkuminen.getPelaaja();
        if (p.getExperience() > 99) {
            p.growExperience(-100);
            Object[] vaihtoEhdot = {
                "Health + 5",
                "Health Refill",
                "Attack Power + 2"
            };
            int reply = JOptionPane.showOptionDialog(f, "Level up! You can choose from 3 options:", "lvlup", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, vaihtoEhdot, vaihtoEhdot[0]);
            int hp = p.getHitPoints();
            int hpMax = p.getHitPointsMax();
            if (reply == 0) {
                hpMax += 5;
                hp += 5;
                p.setHitPoints(hp);
                p.setHitPointsMax(hpMax);
            } else if (reply == 1) {
                hp = hpMax;
                p.setHitPoints(hp);
            } else {
                int ap = p.getAttackPower();
                p.setAttackPower(ap + 2);
            }
            this.paivita(new ArrayList<String>());
        }
    }
}
