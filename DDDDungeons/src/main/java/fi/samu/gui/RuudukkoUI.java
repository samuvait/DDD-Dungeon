package fi.samu.gui;

import fi.samu.logiikka.LuolaGeneraattori;
import fi.samu.mekaniikat.Liikkuminen;
import fi.samu.mekaniikat.Otus;
import fi.samu.mekaniikat.Pelaaja;
import fi.samu.mekaniikat.Viholliset;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;

/**
 *
 * @author Samu
 */
public class RuudukkoUI implements Runnable {

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
    private int rivienMaara = 45;
    private int nykyinenKerros;

    public RuudukkoUI(int sivunPituus) {
        lg = new LuolaGeneraattori(sivunPituus);
        liikkuminen = new Liikkuminen(lg);
        viholliset = liikkuminen.getViholliset();
        kartta = lg.getKartta();
        this.sivunPituus = sivunPituus;
        this.dmn = 30;
        nykyinenKerros = liikkuminen.getKerros() - 1;
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
        jp.setPreferredSize(new Dimension(300, dmn * sivunPituus));
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
        for (int i = 0; i <= rivienMaara; i++) {
            JLabel lbl = new JLabel(" ");
            this.combatLog.add(lbl);
            lblLista.add(lbl);
        }
        combatLog.revalidate();
        combatLog.repaint();
    }

    public void MuutaLabelit(ArrayList<String> tekstit) {
        ArrayList<String> muutettavat = new ArrayList<String>();
        for (int i = rivienMaara; i >= 0; i--) {
            JLabel lbl = lblLista.get(i);
            String muut = lbl.getText();
            muutettavat.add(muut);
        }
        int i = tekstit.size() - 1;
        int h = rivienMaara;
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
        int uusiKerros = liikkuminen.getKerros();
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
                    Font font = new Font("Terminus", Font.PLAIN, 8);
                    JLabel lbl = new JLabel("Floor");
                    lbl.setFont(font);
                    lbl.setBorder(BorderFactory.createEmptyBorder(-2 /*top*/, 0, 0, 0));
                    JLabel krs = new JLabel(kerros);
                    krs.setVerticalTextPosition(JLabel.BOTTOM);
                    Font f = new Font("Terminus", Font.PLAIN, 10);
                    krs.setFont(f);
                    pan.add(lbl);
                    pan.add(krs);
                } else if (kartta[j][i] == 0) {
                    pan.setBackground(Color.black);
                } else {
                    Font font = new Font("Terminus", Font.BOLD, 8);
                    Font hpFont = new Font("Terminus", Font.PLAIN, 8);
                    Pelaaja p = this.liikkuminen.getPelaaja();
                    if (i == p.getKoordinaatit().getX() && j == p.getKoordinaatit().getY()) {
                        JLabel lbl = new JLabel("@");
                        lbl.setFont(font);
                        lbl.setBorder(BorderFactory.createEmptyBorder(-2 /*top*/, 0, 0, 0));
                        if (this.nykyinenKerros < uusiKerros) {
                            pan.setBackground(Color.YELLOW);
                            this.nykyinenKerros = uusiKerros;
                        } else {
                            pan.setBackground(Color.white);
                        }
                        JLabel hp = new JLabel(p.getHitPoints() + "/" + p.getHitPointsMax());
                        hp.setFont(hpFont);
                        hp.setVerticalTextPosition(JLabel.BOTTOM);
                        pan.add(lbl);
                        pan.add(hp);
                    } else if (this.viholliset.tarkistaOtukset(i, j)) {
                        pan.setBackground(Color.white);
                        Otus o = this.viholliset.palautaPiirrettava(i, j);
                        JLabel lbl = new JLabel(o.getTunnus());
                        lbl.setFont(font);
                        if (o.getTunnus().equals(" D ")) {
                            lbl.setForeground(Color.RED);
                            hpFont = new Font("Terminus", Font.PLAIN, 6);
                        }
                        lbl.setBorder(BorderFactory.createEmptyBorder(-2 /*top*/, 0, 0, 0));
                        JLabel hp = new JLabel(o.getHitPoints() + "/" + o.getHitPointsMax());
                        hp.setFont(hpFont);
                        hp.setVerticalAlignment(JLabel.BOTTOM);
                        pan.add(lbl);
                        pan.add(hp);
                    } else {
                        if (kartta[j][i] == 5) {
                            pan.setBackground(Color.white);
                            JLabel lbl = new JLabel("%");
                            pan.add(lbl);
                        } else {
                            pan.setBackground(Color.white);
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
        this.pelinLoppu();
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
    
    private void pelinLoppu() {
        Object[] vaihtoEhdot = {
            "Restart",
            "Retire"
        };
        Pelaaja p = this.liikkuminen.getPelaaja();
        if (this.liikkuminen.getKerros() == 50) {
            f.invalidate();
            for (int i = 0; i < sivunPituus; i++) {
                for (int j = 0; j < sivunPituus; j++) {
                    int index = (j * sivunPituus) + i;
                    JPanel pan = list.get(index);
                    pan.removeAll();
                    pan.setBackground(Color.WHITE);
                }
            }
            f.revalidate();
            f.repaint();
            int reply = JOptionPane.showOptionDialog(f, "You descended 50 floors and won the game! Do you want to restart or retire as a winner?", "Victory", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, vaihtoEhdot, vaihtoEhdot[0]);
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

    @Override
    public void run() {
        //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RuudukkoUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(RuudukkoUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(RuudukkoUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(RuudukkoUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        nayta();
    }
}
