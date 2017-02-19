package fi.samu.logiikka;

import fi.samu.gui.RuudukkoUI;
import fi.samu.mekaniikat.Liikkuminen;
import java.util.Scanner;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Samu
 */
public class Main {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
//        LuolaGeneraattori lg = new LuolaGeneraattori();
//        lg.alustaLuola();
//        lg.tulostaKartta();
//        tekstiKayttoLiittyma();
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        RuudukkoUI gui = new RuudukkoUI(25);
        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui.nayta();
            }
        });
    }

    public static void tekstiKayttoLiittyma() {
        Scanner reader = new Scanner(System.in);
        LuolaGeneraattori lg = new LuolaGeneraattori(15, 3, 4);
        Liikkuminen liik = new Liikkuminen(lg);
        lg.tulostaKartta();
        while (true) {
            System.out.println("Enter a direction n, w, e, s and a number of steps or q to quit");
            String dir = reader.next();
            if (dir.matches("[newsq][0-9]*")) {
                char d = dir.charAt(0);
                String i = dir.substring(1);
                int lkm;
                if (i.isEmpty()) {
                    lkm = 1;
                } else {
                    lkm = Integer.parseInt(i);
                }
                while (lkm > 0) {
                    if (d == 'n') {
                        liik.liiku(0);
                    } else if (d == 'w') {
                        liik.liiku(1);
                    } else if (d == 'e') {
                        liik.liiku(2);
                    } else if (d == 's') {
                        liik.liiku(3);
                    }
                    lkm--;
                }
                if (d == 'q') {
                    break;
                }
            } else {
                System.out.println("Wrong input.");
            }

            lg.tulostaKartta();
        }
    }
}
