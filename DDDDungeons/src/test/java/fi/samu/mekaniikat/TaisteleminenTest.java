/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.samu.mekaniikat;

import fi.samu.logiikka.LuolaGeneraattori;
import java.util.ArrayList;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Samu
 */
public class TaisteleminenTest {

    private LuolaGeneraattori lg;

    public TaisteleminenTest() {
        lg = new LuolaGeneraattori();
        lg.alustaKartta(25);
        Liikkuminen liik = new Liikkuminen(lg);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void tarkistaaPaikanOikein() {
        int x = 0;
        int y = 0;
        Random rng = new Random();
        ArrayList<Otus> vl = new ArrayList();
        Taisteleminen taisto = new Taisteleminen(vl, lg.getKartta());
        boolean oliko = true;
        for (int i = 0; i < 100; i++) {
            x = rng.nextInt(25);
            y = rng.nextInt(25);
            vl.add(new Otus(x, y, 0));
            if (!taisto.tarkista(x, y)) {
                oliko = false;
            }
            vl.remove(0);
        }
        assertTrue(oliko);
    }

    @Test
    public void taisteleeOikeinPelaajanHP() {
        Pelaaja pelaaja = new Pelaaja(1, 2);
        int x = 2;
        int y = 2;
        Random rng = new Random();
        ArrayList<Otus> vl = new ArrayList();
        Taisteleminen taisto = new Taisteleminen(vl, lg.getKartta());
        boolean oliko = true;
        vl.add(new Otus(x, y, 0));
        taisto.tarkista(x, y);
        taisto.taistele(pelaaja);
        assertEquals(28, pelaaja.getHitPoints());
    }

    @Test
    public void taisteleeOikeinOtuksenHP() {
        Pelaaja pelaaja = new Pelaaja(1, 2);
        int x = 2;
        int y = 2;
        Random rng = new Random();
        ArrayList<Otus> vl = new ArrayList();
        Taisteleminen taisto = new Taisteleminen(vl, lg.getKartta());
        taisto.setTekstit(new ArrayList());
        boolean oliko = true;
        Otus rat = new Otus(x, y, 0);
        vl.add(rat);
        taisto.tarkista(x, y);
        taisto.taistele(pelaaja);
        assertEquals(0, rat.getHitPoints());
    }

    @Test
    public void tarkistaaKuolikoOikein() {
        Pelaaja pelaaja = new Pelaaja(1, 2);
        int x = 2;
        int y = 2;
        Random rng = new Random();
        ArrayList<Otus> vl = new ArrayList();
        Taisteleminen taisto = new Taisteleminen(vl, lg.getKartta());
        taisto.setTekstit(new ArrayList());
        boolean oliko = true;
        Otus rat = new Otus(x, y, 0);
        vl.add(rat);
        taisto.tarkista(x, y);
        taisto.taistele(pelaaja);
        assertTrue(taisto.tarkistaKuoliko());
    }

    @Test
    public void tarkistaaVoikoTaistellaTrueOikein() {
        Pelaaja pelaaja = new Pelaaja(1, 2);
        int x = 2;
        int y = 2;
        ArrayList<Otus> vl = new ArrayList();
        Taisteleminen taisto = new Taisteleminen(vl, lg.getKartta());
        taisto.setTekstit(new ArrayList());
        Otus rat = new Otus(x, y, 0);
        assertTrue(taisto.voikoTaistella(rat, pelaaja));
    }

    @Test
    public void tarkistaaVoikoTaistellaFalseOikein() {
        Pelaaja pelaaja = new Pelaaja(1, 1);
        int x = 2;
        int y = 2;
        ArrayList<Otus> vl = new ArrayList();
        Taisteleminen taisto = new Taisteleminen(vl, lg.getKartta());
        taisto.setTekstit(new ArrayList());
        Otus rat = new Otus(x, y, 0);
        assertTrue(!taisto.voikoTaistella(rat, pelaaja));
    }

    @Test
    public void tarkistaaVoiTaistellaTrueOikein() {
        Pelaaja pelaaja = new Pelaaja(1, 2);
        int x = 2;
        int y = 2;
        Random rng = new Random();
        ArrayList<Otus> vl = new ArrayList();
        Taisteleminen taisto = new Taisteleminen(vl, lg.getKartta());
        taisto.setTekstit(new ArrayList());
        boolean oliko = true;
        Otus rat = new Otus(x, y, 0);
        assertTrue(taisto.voiTaistella(rat, pelaaja.getKoordinaatit().getX(), pelaaja.getKoordinaatit().getY()));
    }

    @Test
    public void tarkistaaVoiTaistellaFalseOikein() {
        Pelaaja pelaaja = new Pelaaja(1, 1);
        int x = 2;
        int y = 2;
        ArrayList<Otus> vl = new ArrayList();
        Taisteleminen taisto = new Taisteleminen(vl, lg.getKartta());
        taisto.setTekstit(new ArrayList());
        Otus rat = new Otus(x, y, 0);
        assertTrue(!taisto.voiTaistella(rat, pelaaja.getKoordinaatit().getX(), pelaaja.getKoordinaatit().getY()));
    }
}
