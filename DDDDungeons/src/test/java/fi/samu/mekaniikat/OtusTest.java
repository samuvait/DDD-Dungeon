/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.samu.mekaniikat;

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
}
