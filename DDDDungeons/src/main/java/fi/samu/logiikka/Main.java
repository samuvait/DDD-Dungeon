package fi.samu.logiikka;

import fi.samu.mekaniikat.Liikkuminen;
import java.util.Scanner;

/**
 *
 * @author Samu
 */
public class Main {

    public static void main(String[] args) {
//        LuolaGeneraattori lg = new LuolaGeneraattori(15, 3, 4);
//        lg.alustaLuola();
//        lg.tulostaKartta();
        tekstiKayttoLiittyma();
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
