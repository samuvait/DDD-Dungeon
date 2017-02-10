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
public class PelaajaTest {
    
    Pelaaja pelaaja;
    
    @Before
    public void setUp() {
        pelaaja = new Pelaaja(0, 0);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void asettaaXOikein() {
        Koordinaatti krdn = new Koordinaatti(3, 2);
        pelaaja.setKoordinaatit(krdn);
        assertEquals(3, pelaaja.getKoordinaatit().getX());
    }
    
    @Test
    public void asettaaYOikein() {
        Koordinaatti krdn = new Koordinaatti(3, 2);
        pelaaja.setKoordinaatit(krdn);
        assertEquals(2, pelaaja.getKoordinaatit().getY());
    }
    
    @Test
    public void asettaaHPOikein() {
        assertEquals(30, pelaaja.getHitPoints());
    }
    
    @Test
    public void asettaaAPOikein() {
        assertEquals(5, pelaaja.getAttackPower());
    }
}
