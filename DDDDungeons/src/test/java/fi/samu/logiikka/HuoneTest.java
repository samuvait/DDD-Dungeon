
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
public class HuoneTest {
    
    public HuoneTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void huoneenLuominenNegatiivisellaX() {
        Huone uusiHuone = new Huone(-1, -2, 3);
        assertEquals(0, uusiHuone.getX());
    }
    
    @Test
    public void huoneenLuominenNegatiivisellaY() {
        Huone uusiHuone = new Huone(-1, -2, 3);
        assertEquals(0, uusiHuone.getY());
    }
    
    @Test
    public void huoneOikeanKokoinen() {
        Huone uusiHuone = new Huone(2, 5, 7);
        assertEquals(7, uusiHuone.getHuoneenLeveys());
    }
    
    @Test
    public void huoneenLeveysNegatiivisella() {
        Huone uusiHuone = new Huone(2, 5, -1);
        assertEquals(3, uusiHuone.getHuoneenLeveys());
    }
    
    @Test
    public void huoneenPituusOikein() {
        Huone uusiHuone = new Huone(2, 5, 3, 6);
        assertEquals(6, uusiHuone.getHuoneenPituus());
    }
}
