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
    public void asettaaHPOikeinUudestaan() {
        pelaaja.setHitPoints(25);
        assertEquals(25, pelaaja.getHitPoints());
    }
    
    @Test
    public void asettaaAPOikein() {
        assertEquals(5, pelaaja.getAttackPower());
    }
    
    @Test
    public void lisaaHPOikein() {
        pelaaja.setHitPointsMax(35);
        assertEquals(35, pelaaja.getHitPointsMax());
    }
    
    @Test
    public void asettaaAPUudelleen() {
        pelaaja.setAttackPower(10);
        assertEquals(10, pelaaja.getAttackPower());
    }
    
    @Test
    public void kasvattaaXPOikein() {
        pelaaja.growExperience(20);
        assertEquals(20, pelaaja.getExperience());
    }
    
    @Test
    public void asettaaXPOikein() {
        pelaaja.setExperience(50);
        assertEquals(50, pelaaja.getExperience());
    }
    
    @Test
    public void kasvattaaJaVahentaaXPOikein() {
        pelaaja.growExperience(20);
        pelaaja.growExperience(-10);
        assertEquals(10, pelaaja.getExperience());
    }
    
    @Test
    public void vaihtaaXOikein() {
        pelaaja.setKoordinaatit(new Koordinaatti(1, pelaaja.getKoordinaatit().getY()));
        assertEquals(1, pelaaja.getKoordinaatit().getX());
    }
    
    @Test
    public void vaihtaaYOikein() {
        pelaaja.setKoordinaatit(new Koordinaatti(pelaaja.getKoordinaatit().getX(), 2));
        assertEquals(2, pelaaja.getKoordinaatit().getY());
    }
}
