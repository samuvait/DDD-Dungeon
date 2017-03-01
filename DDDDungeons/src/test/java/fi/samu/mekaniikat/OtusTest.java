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
    public void hamisOikein() {
        Otus uusiOtus = new Otus(5, 5, 1);
        assertEquals(8, uusiOtus.getHitPoints());
    }

    @Test
    public void zombiOikein() {
        Otus uusiOtus = new Otus(5, 5, 2);
        assertEquals(9, uusiOtus.getHitPoints());
    }

    @Test
    public void luurankoOikein() {
        Otus uusiOtus = new Otus(5, 5, 3);
        assertEquals(10, uusiOtus.getHitPoints());
    }

    @Test
    public void orkkiOikein() {
        Otus uusiOtus = new Otus(5, 5, 4);
        assertEquals(13, uusiOtus.getHitPoints());
    }

    @Test
    public void mantisOikein() {
        Otus uusiOtus = new Otus(5, 5, 5);
        assertEquals(15, uusiOtus.getHitPoints());
    }

    @Test
    public void ogreOikein() {
        Otus uusiOtus = new Otus(5, 5, 6);
        assertEquals(17, uusiOtus.getHitPoints());
    }

    @Test
    public void wolfOikein() {
        Otus uusiOtus = new Otus(5, 5, 7);
        assertEquals(20, uusiOtus.getHitPoints());
    }

    @Test
    public void gargoyleOikein() {
        Otus uusiOtus = new Otus(5, 5, 8);
        assertEquals(30, uusiOtus.getHitPoints());
    }

    @Test
    public void giantOikein() {
        Otus uusiOtus = new Otus(5, 5, 9);
        assertEquals(40, uusiOtus.getHitPoints());
    }

    @Test
    public void elementalOikein() {
        Otus uusiOtus = new Otus(5, 5, 10);
        assertEquals(32, uusiOtus.getHitPoints());
    }

    @Test
    public void minotaurOikein() {
        Otus uusiOtus = new Otus(5, 5, 11);
        assertEquals(45, uusiOtus.getHitPoints());
    }

    @Test
    public void rockGiantOikein() {
        Otus uusiOtus = new Otus(5, 5, 12);
        assertEquals(50, uusiOtus.getHitPoints());
    }

    @Test
    public void dragonOikein() {
        Otus uusiOtus = new Otus(5, 5, 13);
        assertEquals(100, uusiOtus.getHitPoints());
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
        assertEquals("  r  ", uusiOtus.getTunnus());
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
