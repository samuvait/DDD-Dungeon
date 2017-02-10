/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.samu.logiikka;

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
public class KoordinaattiTest {
    
    public KoordinaattiTest() {
    }

    @Before
    public void setUp() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void koordinaatinAsettaminenOikeinX() {
        Koordinaatti uusi = new Koordinaatti(3, 2);
        assertEquals(3, uusi.getX());
    }
    
    @Test
    public void koordinaatinAsettaminenOikeinY() {
        Koordinaatti uusi = new Koordinaatti(3, 2);
        assertEquals(2, uusi.getY());
    }
}
