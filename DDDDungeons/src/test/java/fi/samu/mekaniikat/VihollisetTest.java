/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.samu.mekaniikat;

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
 * @author Samu
 */
public class VihollisetTest {

    LuolaGeneraattori lg;
    int koko;
    int kartta[][];
    Viholliset viholliset;

    @Before
    public void setUp() {
        lg = new LuolaGeneraattori();
        lg.alustaLuola();
        lg.getKoko();
        kartta = lg.getKartta();
        viholliset = new Viholliset(koko, 1, lg.getSijoitetutHuoneet(), kartta);
        viholliset.luoViholliset();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void luoOikeanMaaran() {
        assertEquals(viholliset.lkm, viholliset.varmistus);
    }
    
    @Test
    public void luoOikeatHirviot() {
        boolean oliko = true;
        for (Otus o : viholliset.getVihollisLista()) {
            if (o.getTunnus() != " r ") {
                oliko = false;
                break;
            }
        }
        assertTrue(oliko);
    }
    
    @Test
    public void palauttaaPiirrettavan() {
        Otus o = viholliset.getVihollisLista().get(0);
        Otus b = viholliset.palautaPiirrettava(o.getKoordinaatit().getX(), o.getKoordinaatit().getY());
        assertEquals(o, b);
    }
    
    @Test
    public void tarkistaaOikein() {
        Otus o = viholliset.getVihollisLista().get(0);
        assertTrue(viholliset.tarkistaOtukset(o.getKoordinaatit().getX(), o.getKoordinaatit().getY()));
    }
    
    @Test
    public void liikuttaaOikein() {
        Taisteleminen taisto = new Taisteleminen(viholliset.getVihollisLista(), kartta);
        viholliset.setTaistelu(taisto);
        Pelaaja pelaaja = new Pelaaja(30, 30);
        Otus o = viholliset.getVihollisLista().get(0);
        int x = o.getKoordinaatit().getX();
        int y = o.getKoordinaatit().getY();
        o.setLiikkuuko(1);
        viholliset.liikuta(pelaaja);
        assertTrue(!viholliset.tarkistaOtukset(x, y));
    }
    
    @Test
    public void liikuttaaOtusOikein() {
        Taisteleminen taisto = new Taisteleminen(viholliset.getVihollisLista(), kartta);
        viholliset.setTaistelu(taisto);
        Pelaaja pelaaja = new Pelaaja(30, 30);
        Otus o = viholliset.getVihollisLista().get(0);
        o.setLiikkuuko(1);
        viholliset.liiku(o, pelaaja);
        assertTrue(viholliset.tarkistaOtukset(o.getKoordinaatit().getX(), o.getKoordinaatit().getY()));
    }
}
