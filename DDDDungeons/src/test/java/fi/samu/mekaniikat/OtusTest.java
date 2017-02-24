/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.samu.mekaniikat;

import fi.samu.logiikka.Koordinaatti;
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
public class OtusTest {

    Otus otus;

    @Before
    public void setUp() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void yPositiivinen() {
        Otus o = new Otus(1, 1, 0);
        o.setKoordinaatit(new Koordinaatti(2, 1));
        assertEquals(1, o.getKoordinaatit().getY());
    }

    @Test
    public void yNegatiivinen() {
        Otus o = new Otus(1, -1, 0);
        assertEquals(0, o.getKoordinaatit().getY());
    }
    
    @Test
    public void xPositiivinen() {
        Otus uusiOtus = new Otus(1, 1, 0);
        assertEquals(1, uusiOtus.getKoordinaatit().getX());
    }

    @Test
    public void xNegatiivinen() {
        Otus uusiOtus = new Otus(-1, 1, 0);
        assertEquals(0, uusiOtus.getKoordinaatit().getX());
    }

    @Test
    public void rottaOikein() {
        Otus uusiOtus = new Otus(5, 5, 0);
        assertEquals(5, uusiOtus.getHitPoints());
    }

    @Test
    public void liianSuurellaOikein() {
        Otus uusiOtus = new Otus(5, 5, 10000);
        assertEquals("rat", uusiOtus.getKuvaus());
    }

    @Test
    public void kuvausRatOikein() {
        Otus uusiOtus = new Otus(5, 5, 0);
        assertEquals("rat", uusiOtus.getKuvaus());
    }

    @Test
    public void tunnusRatOikein() {
        Otus uusiOtus = new Otus(5, 5, 0);
        assertEquals(" r ", uusiOtus.getTunnus());
    }

    @Test
    public void luoOtuksenOikein() {
        Otus uusiOtus = new Otus(5, 5, 0);
        uusiOtus.luoOtus(5, 2, "kissa", " k ");
        assertEquals(" k ", uusiOtus.getTunnus());
    }

    @Test
    public void piirretaankoOikein() {
        Otus uusiOtus = new Otus(5, 5, 0);
        uusiOtus.setPiirretaanko(1);
        assertEquals(1, uusiOtus.getPiirretaanko());
    }
    
    @Test
    public void stringOikein() {
        Otus o = new Otus(5, 5, 0);
        assertTrue(o.toString().equals("Otuksen nimi: rat x: 5 y: 5"));
    }
}
