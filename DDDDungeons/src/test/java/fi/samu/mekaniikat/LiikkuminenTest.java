/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.samu.mekaniikat;

import fi.samu.logiikka.Koordinaatti;
import fi.samu.logiikka.LuolaGeneraattori;
import java.util.ArrayList;
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
        liik.getViholliset().getVihollisLista().clear();
        liik.setLoppu(new Koordinaatti(30, 30));
        this.kartta[2][2] = 1;
        this.kartta[1][2] = 1;
        pelaaja.setKoordinaatit(new Koordinaatti(2, 2));
        liik.liiku(0);
        assertEquals(1, pelaaja.getKoordinaatit().getY());
    }

    @Test
    public void liikuOikeinVasemmalle() {
        liik.getViholliset().getVihollisLista().clear();
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

    @Test
    public void liikuOikeinOikealle() {
        LuolaGeneraattori lg = new LuolaGeneraattori();
        Liikkuminen liik = new Liikkuminen(lg);
        liik.getViholliset().getVihollisLista().clear();
        liik.setLoppu(new Koordinaatti(30, 30));
        Pelaaja pelaaja = liik.getPelaaja();
        int[][] kartta = lg.getKartta();
        kartta[3][3] = 1;
        kartta[3][4] = 1;
        pelaaja.setKoordinaatit(new Koordinaatti(3, 3));
        liik.liiku(2);
        assertEquals(4, pelaaja.getKoordinaatit().getX());
    }

    @Test
    public void liikuOikeinAlas() {
        LuolaGeneraattori lg = new LuolaGeneraattori();
        Liikkuminen liik = new Liikkuminen(lg);
        liik.getViholliset().getVihollisLista().clear();
        liik.setLoppu(new Koordinaatti(30, 30));
        Pelaaja pelaaja = liik.getPelaaja();
        liik.getViholliset().getVihollisLista().clear();
        int[][] kartta = lg.getKartta();
        kartta[2][2] = 1;
        kartta[3][2] = 1;
        pelaaja.setKoordinaatit(new Koordinaatti(2, 2));
        liik.liiku(3);
        assertEquals(3, pelaaja.getKoordinaatit().getY());
    }

    @Test
    public void liikuOikeinPortaat() {
        LuolaGeneraattori lg = new LuolaGeneraattori();
        Liikkuminen liik = new Liikkuminen(lg);
        liik.getViholliset().getVihollisLista().clear();
        Pelaaja pelaaja = liik.getPelaaja();
        int[][] kartta = lg.getKartta();
        kartta[5][5] = 1;
        kartta[5][6] = 1;
        liik.setLoppu(new Koordinaatti(6, 5));
        pelaaja.setKoordinaatit(new Koordinaatti(5, 5));
        ArrayList<String> l = liik.liiku(2);
        assertTrue(l.get(2).equals("You go down the stairs"));
    }

    @Test
    public void jataLiikkumattaOikein() {
        this.kartta[5][5] = 1;
        this.kartta[5][4] = 0;
        liik.setLoppu(new Koordinaatti(5, 6));
        pelaaja.setKoordinaatit(new Koordinaatti(5, 5));
        ArrayList<String> l = liik.liiku(1);
        assertEquals(5, pelaaja.getKoordinaatit().getX());
    }

    @Test
    public void taisteleeOikeinVasemmalle() {
        LuolaGeneraattori lg = new LuolaGeneraattori();
        Liikkuminen liik = new Liikkuminen(lg);
        Pelaaja pelaaja = liik.getPelaaja();
        int[][] kartta = lg.getKartta();
        int x = 4;
        int y = 5;
        kartta[5][5] = 1;
        kartta[y][x] = 1;
        pelaaja.setKoordinaatit(new Koordinaatti(5, 5));
        ArrayList<Otus> vl = new ArrayList();
        Otus o = new Otus(x, y, 0);
        vl.add(o);
        Taisteleminen taisto = new Taisteleminen(vl, kartta);
        liik.setTaisteleminen(taisto);
        ArrayList<String> l = liik.liiku(1);
        assertEquals(0, o.getHitPoints());
    }

    @Test
    public void taisteleeOikeinYlos() {
        LuolaGeneraattori lg = new LuolaGeneraattori();
        Liikkuminen liik = new Liikkuminen(lg);
        Pelaaja pelaaja = liik.getPelaaja();
        int[][] kartta = lg.getKartta();
        int x = 5;
        int y = 4;
        kartta[5][5] = 1;
        kartta[y][x] = 1;
        pelaaja.setKoordinaatit(new Koordinaatti(5, 5));
        ArrayList<Otus> vl = new ArrayList();
        Otus o = new Otus(x, y, 0);
        vl.add(o);
        Taisteleminen taisto = new Taisteleminen(vl, kartta);
        liik.setTaisteleminen(taisto);
        ArrayList<String> l = liik.liiku(0);
        assertEquals(0, o.getHitPoints());
    }

    @Test
    public void taisteleeOikeinOikealle() {
        LuolaGeneraattori lg = new LuolaGeneraattori();
        Liikkuminen liik = new Liikkuminen(lg);
        Pelaaja pelaaja = liik.getPelaaja();
        int[][] kartta = lg.getKartta();
        int x = 6;
        int y = 5;
        kartta[5][5] = 1;
        kartta[y][x] = 6;
        pelaaja.setKoordinaatit(new Koordinaatti(5, 5));
        ArrayList<Otus> vl = new ArrayList();
        Otus o = new Otus(x, y, 0);
        vl.add(o);
        Taisteleminen taisto = new Taisteleminen(vl, kartta);
        liik.setTaisteleminen(taisto);
        ArrayList<String> l = liik.liiku(2);
        assertEquals(0, o.getHitPoints());
    }

    @Test
    public void taisteleeOikeinAlas() {
        LuolaGeneraattori lg = new LuolaGeneraattori();
        Liikkuminen liik = new Liikkuminen(lg);
        Pelaaja pelaaja = liik.getPelaaja();
        int[][] kartta = lg.getKartta();
        int x = 5;
        int y = 6;
        kartta[5][5] = 1;
        kartta[y][x] = 1;
        pelaaja.setKoordinaatit(new Koordinaatti(5, 5));
        ArrayList<Otus> vl = new ArrayList();
        Otus o = new Otus(x, y, 0);
        vl.add(o);
        Taisteleminen taisto = new Taisteleminen(vl, kartta);
        liik.setTaisteleminen(taisto);
        ArrayList<String> l = liik.liiku(3);
        assertEquals(0, o.getHitPoints());
    }

    @Test
    public void asettaaSuunnan() {
        liik.setSuunta(2);
        assertEquals(2, liik.getSuunta());
    }
}
