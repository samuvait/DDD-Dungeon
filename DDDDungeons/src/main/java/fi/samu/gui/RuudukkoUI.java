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
import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
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
    private int dmn; // Desktop 30, Laptop 28
    private int rivienMaara = 45; // Desktop 45, Laptop 42
    private int nykyinenKerros;
    private Font boldKasi;
    private Font plainKasi;
    private Font plainKuus;
    private Font kerrosFont;
    private int border;

    public RuudukkoUI(int sivunPituus) {
        lg = new LuolaGeneraattori(sivunPituus);
        liikkuminen = new Liikkuminen(lg);
        viholliset = liikkuminen.getViholliset();
        kartta = lg.getKartta();
        this.sivunPituus = sivunPituus;
        this.dmn = 30; // Desktop 30, Laptop 27
        nykyinenKerros = liikkuminen.getKerros() - 1;
        this.boldKasi = new Font("Terminus", Font.BOLD, 8);
        this.plainKasi = new Font("Terminus", Font.PLAIN, 8);
        this.plainKuus = new Font("Terminus", Font.PLAIN, 6);
        this.kerrosFont = new Font("Terminus", Font.PLAIN, 10);
        this.border = -2;
    }
    
    public void deskvailap() {
        Object[] vaihtoEhdot = {
            "Desktop",
            "Laptop"
        };
        Pelaaja p = this.liikkuminen.getPelaaja();
            int reply = JOptionPane.showOptionDialog(null, "Are you playing on a desktop or a laptop?", "Question", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, vaihtoEhdot, vaihtoEhdot[0]);
        if (reply == 0) {
        } else {
            this.dmn = 28;
            this.rivienMaara = 42;
//            this.boldKasi = new Font("Terminus", Font.BOLD, 8);
            this.plainKasi = new Font("Terminus", Font.PLAIN, 7);
            this.kerrosFont = new Font("Terminus", Font.PLAIN, 8);
        }
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
        if (this.combatLog != null) {
            this.combatLog.removeAll();
        }
        JLabel otsikko = new JLabel();
        otsikko.setText("Combat Log");
        Font font = new Font("Terminus", Font.BOLD, 12);
        otsikko.setFont(font);
        this.combatLog.add(new JLabel(" "));
        this.combatLog.add(otsikko);
        this.combatLog.add(new JLabel(" "));
        this.lblLista = new ArrayList();
        for (int i = 0; i <= rivienMaara - 3; i++) {
            JLabel lbl = new JLabel(" ");
            this.combatLog.add(lbl);
            lblLista.add(lbl);
        }
        combatLog.revalidate();
        combatLog.repaint();
    }

    public void MuutaLabelit(ArrayList<String> tekstit) {
        ArrayList<String> muutettavat = new ArrayList<String>();
        for (int i = rivienMaara - 3; i >= 0; i--) {
            JLabel lbl = lblLista.get(i);
            String muut = lbl.getText();
            muutettavat.add(muut);
        }
        int i = tekstit.size() - 1;
        int h = rivienMaara - 3;
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
                    JLabel lbl = new JLabel("Floor");
                    lbl.setFont(plainKasi);
                    lbl.setBorder(BorderFactory.createEmptyBorder(this.border /*top*/, 0, 0, 0));
                    JLabel krs = new JLabel(kerros);
                    krs.setVerticalTextPosition(JLabel.BOTTOM);
                    krs.setFont(this.kerrosFont);
                    pan.add(lbl);
                    pan.add(krs);
                } else if (kartta[j][i] == 0) {
                    pan.setBackground(Color.black);
                } else {
                    Pelaaja p = this.liikkuminen.getPelaaja();
                    if (i == p.getKoordinaatit().getX() && j == p.getKoordinaatit().getY()) {
                        JLabel lbl = new JLabel("@");
                        lbl.setFont(boldKasi);
                        lbl.setBorder(BorderFactory.createEmptyBorder(this.border /*top*/, 0, 0, 0));
                        if (this.nykyinenKerros < uusiKerros) {
                            pan.setBackground(Color.YELLOW);
                            this.nykyinenKerros = uusiKerros;
                        } else {
                            pan.setBackground(Color.white);
                        }
                        JLabel hp = new JLabel(p.getHitPoints() + "/" + p.getHitPointsMax());
                        hp.setFont(plainKasi);
                        hp.setVerticalTextPosition(JLabel.BOTTOM);
                        pan.add(lbl);
                        pan.add(hp);
                    } else if (this.viholliset.tarkistaOtukset(i, j)) {
                        Font hpFont = plainKasi;
                        pan.setBackground(Color.white);
                        Otus o = this.viholliset.palautaPiirrettava(i, j);
                        JLabel lbl = new JLabel(o.getTunnus());
                        lbl.setFont(boldKasi);
                        if (o.getTunnus().equals(" D ")) {
                            lbl.setForeground(Color.RED);
                            hpFont = plainKuus;
                        }
                        lbl.setBorder(BorderFactory.createEmptyBorder(this.border /*top*/, 0, 0, 0));
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
        this.deskvailap();
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
        if (this.lblLista != null) {
            for (JLabel lbl : this.lblLista) {
                lbl.setText(" ");
            }
        }
    }

    public void lvlUp() {
        Pelaaja p = this.liikkuminen.getPelaaja();
        if (p.getExperience() > 99) {
            p.growExperience(-100);
            String[] vaihtoEhdot = {
                "<html>Health + 5<br />(Delete or 1)</html>",
                "<html>Health Refill<br />(End or 2)</html>",
                "<html>Attack Power + 2<br />(Page Down or 3)</html>"
            };
            JDialog dia = new JDialog(f, "lvlup", true);
            JPanel pan = new JPanel(new GridBagLayout());
            JLabel uusi = new JLabel("Level up! You can choose from 3 options:");
            Font font = new Font("Terminus", Font.PLAIN, 16);
            uusi.setFont(font);
            uusi.setHorizontalAlignment(JLabel.CENTER);
            pan.setPreferredSize(new Dimension(400, 150));
            dia.setResizable(false);
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 1;
            c.weighty = 1;
            c.gridwidth = 3;
            pan.add(uusi, c);
            JButton eka = new JButton();
            eka.setText(vaihtoEhdot[0]);
            eka.setFocusable(false);
            eka.setEnabled(true);
            eka.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "hpUp");
            eka.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0), "hpUp");
            eka.getActionMap().put("hpUp", new AbstractAction("hpUp") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    p.setHitPoints(p.getHitPoints() + 5);
                    p.setHitPointsMax(p.getHitPointsMax() + 5);
                    dia.dispose();
                }
            });
            eka.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    p.setHitPoints(p.getHitPoints() + 5);
                    p.setHitPointsMax(p.getHitPointsMax() + 5);
                    dia.dispose();
                }
            });
            JButton toka = new JButton();
            toka.setText(vaihtoEhdot[1]);
            toka.setFocusable(false);
            toka.setEnabled(true);
            toka.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_END, 0), "hpFill");
            toka.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0), "hpFill");
            toka.getActionMap().put("hpFill", new AbstractAction("hpFill") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    p.setHitPoints(p.getHitPointsMax());
                    dia.dispose();
                }
            });
            toka.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    p.setHitPoints(p.getHitPointsMax());
                    dia.dispose();
                }
            });
            JButton kolm = new JButton();
            kolm.setText(vaihtoEhdot[2]);
            kolm.setFocusable(false);
            kolm.setEnabled(true);
            kolm.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, 0), "apUP");
            kolm.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0), "apUP");
            kolm.getActionMap().put("apUP", new AbstractAction("apUP") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    p.setAttackPower(p.getAttackPower() + 2);
                    dia.dispose();
                }
            });
            kolm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    p.setAttackPower(p.getAttackPower() + 2);
                    dia.dispose();
                }
            });
            for (int j = 0; j < 3; j += 2) {
                for (int i = 0; i < 3; i++) {
                    GridBagConstraints co = new GridBagConstraints();
                    co.gridx = i;
                    co.gridy = j;
                    co.weighty = 0;
                    pan.add(new JLabel(" "), co);
                }
            }
            Insets uudet = new Insets(2, 2, 2, 2);
            GridBagConstraints co = new GridBagConstraints();
            co.gridx = 0;
            co.gridy = 3;
            co.weighty = 0.5;
            co.insets = uudet;
            GridBagConstraints con = new GridBagConstraints();
            con.gridx = 1;
            con.gridy = 3;
            con.weighty = 0.5;
            con.insets = uudet;
            GridBagConstraints cons = new GridBagConstraints();
            cons.gridx = 2;
            cons.gridy = 3;
            cons.weighty = 0.5;
            cons.insets = uudet;
            pan.add(eka, co);
            pan.add(toka, con);
            pan.add(kolm, cons);
            dia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dia.add(pan);
            dia.pack();
            dia.setLocationRelativeTo(f);
            dia.setVisible(true);
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
