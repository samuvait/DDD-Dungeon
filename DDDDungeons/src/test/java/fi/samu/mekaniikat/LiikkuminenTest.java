/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.samu.mekaniikat;

import fi.samu.logiikka.LuolaGeneraattori;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class LiikkuminenTest {

    LuolaGeneraattori lg;
    Liikkuminen liik;
    Pelaaja pelaaja;
    int pX;
    int pY;
    int loppuX;
    int loppuY;
    int koko;
    int kartta[][];

    @Before
    public void setUp() {
        lg = new LuolaGeneraattori();
        liik = new Liikkuminen(lg);
        pelaaja = liik.getPelaaja();
        loppuX = lg.getLoppu().getX();
        loppuY = lg.getLoppu().getY();
        pX = pelaaja.getKoordinaatit().getX();
        pY = pelaaja.getKoordinaatit().getY();
        lg.getKoko();
        kartta = lg.getKartta();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void alustettuOikeinPelaaja() {
        boolean oliko = false;
        for (int i = 0; i < lg.getKoko(); i++) {
            for (int j = 0; j < lg.getKoko(); j++) {
                if (lg.getKartta()[i][j] == 8) {
                    if (pX == j && pY == i) {
                        oliko = true;
                        break;
                    }
                }
            }
        }
        assertTrue(oliko);
    }

    @Test
    public void alustettuOikeinPortaat() {
        boolean oliko = false;
        for (int i = 0; i < lg.getKoko(); i++) {
            for (int j = 0; j < lg.getKoko(); j++) {
                if (lg.getKartta()[i][j] == 5) {
                    if (loppuX == j && loppuY == i) {
                        oliko = true;
                        break;
                    }
                }
            }
        }
        assertTrue(oliko);
    }

    @Test
    public void karttaOikeassaMuodossa() {
        boolean oliko = true;
        for (int i = 0; i < lg.getKoko(); i++) {
            for (int j = 0; j < lg.getKoko(); j++) {
                if (!(lg.getKartta()[i][j] < 10 && lg.getKartta()[i][j] >= 0)) {
                    oliko = false;
                    break;
                }
            }
        }
        assertTrue(oliko);
    }

    @Test
    public void liikuOikeinYlos() {
        liik.liiku(0);
        if (pY > 0) {
            if (kartta[pY - 1][pX] != 0) {
                assertEquals(pY - 1, pelaaja.getKoordinaatit().getY());
            } else {
                assertEquals(pY, pelaaja.getKoordinaatit().getY());
            }
        } else {
            assertEquals(pY, 0);
        }
    }

    @Test
    public void liikuOikeinVasemmalle() {
        liik.liiku(1);
        if (pX > 0) {
            if (kartta[pY][pX - 1] != 0) {
                assertEquals(pX - 1, pelaaja.getKoordinaatit().getX());
            } else {
                assertEquals(pX, pelaaja.getKoordinaatit().getX());
            }
        } else {
            assertEquals(pX, 0);
        }
    }
}
