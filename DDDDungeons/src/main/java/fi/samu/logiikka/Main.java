
package fi.samu.logiikka;

import fi.samu.mekaniikat.Liikkuminen;
import java.util.Scanner;

/**
 *
 * @author Samu
 */
public class Main {
    
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        LuolaGeneraattori lg = new LuolaGeneraattori(15, 3, 4);
        Liikkuminen liik = new Liikkuminen(lg);
        lg.tulostaKartta();
        while (true) {
            System.out.println("Enter a direction n, w, e, s or q to quit");
            String dir = reader.next();
            char d = dir.charAt(0);
            if (d == 'n') {
                liik.liiku(0);
            } else if (d == 'w') {
                liik.liiku(1);
            } else if (d == 'e') {
                liik.liiku(2);
            } else if (d == 's') {
                liik.liiku(3);
            } else if (d == 'q') {
                break;
            }
            lg.tulostaKartta();
        }
        
//        liik.liiku(0);
//        lg.tulostaKartta();
//        liik.liiku(1);
//        lg.tulostaKartta();
//        liik.liiku(3);
//        lg.tulostaKartta();
//        liik.liiku(2);
//        lg.tulostaKartta();
    }
}
