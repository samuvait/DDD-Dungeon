package fi.samu.logiikka;

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
public class LuolaGeneraattoriTest {
    
    int luolanKokoMin;
    
    @Before
    public void setUp() {
        luolanKokoMin = 10;
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void luolanLuominenNegatiivisella() {
        LuolaGeneraattori lg = new LuolaGeneraattori(-1, 3, 8);
        lg.alustaLuola();
        assertEquals(10, lg.getKoko());
    }
    
    @Test
    public void luolanLuominenNollalla() {
        LuolaGeneraattori lg = new LuolaGeneraattori(0, 3, 8);
        assertEquals(10, lg.getKoko());
    }
    
    @Test
    public void luolanLuominenOikeallaLuvulla() {
        LuolaGeneraattori lg = new LuolaGeneraattori(100, 3, 8);
        assertEquals(100, lg.getKoko());
    }
    
    @Test
    public void alustettuOikeinTyhjaksi() {
        int luolanKoko = 50;
        boolean kaviLapiOikein = true;
        LuolaGeneraattori lg = new LuolaGeneraattori(luolanKoko, 3, 8);
        lg.alustaLuola();
        int[][] kartta = lg.getKartta();
        for (int i = 0; i < luolanKoko; i++) {
            for (int j = 0; j < luolanKoko; j++) {
                if (!(kartta[i][j] == 0 || kartta[i][j] == 1)) {
                    kaviLapiOikein = false;
                }
            }
        }
        assertTrue(kaviLapiOikein);
    }
    
    @Test
    public void alustettuOikeinTyhjaksiNollalla() {
        boolean kaviLapiOikein = true;
        LuolaGeneraattori lg = new LuolaGeneraattori(0, 3, 8);
        lg.alustaLuola();
        int[][] kartta = lg.getKartta();
        for (int i = 0; i < luolanKokoMin; i++) {
            for (int j = 0; j < luolanKokoMin; j++) {
                if (!(kartta[i][j] == 0 || kartta[i][j] == 1)) {
                    kaviLapiOikein = false;
                }
            }
        }
        assertTrue(kaviLapiOikein);
    }
    
    @Test
    public void alustettuOikeinTyhjaksiNegatiivisella() {
        boolean kaviLapiOikein = true;
        LuolaGeneraattori lg = new LuolaGeneraattori(-1, 3, 8);
        lg.alustaLuola();
        int[][] kartta = lg.getKartta();
        for (int i = 0; i < luolanKokoMin; i++) {
            for (int j = 0; j < luolanKokoMin; j++) {
                if (!(kartta[i][j] == 0 || kartta[i][j] == 1)) {
                    kaviLapiOikein = false;
                    break;
                }
            }
            if (!kaviLapiOikein) {
                break;
            }
        }
        assertTrue(kaviLapiOikein);
    }
    
    @Test
    public void sijoittaaAlunJaLopun() {
        ArrayList<Huone> huo = new ArrayList();
        huo.add(new Huone(1, 1, 1));
        huo.add(new Huone(3, 3, 1));
        LuolaGeneraattori lg = new LuolaGeneraattori();
        lg.alustaKartta(25);
        int[][] kartta = lg.getKartta();
        boolean sijoitti = false;
        lg.setSijoitetutHuoneet(huo);
        lg.sijoitaAlkuJaLoppu();
        Koordinaatti a = lg.getAlku();
        Koordinaatti l = lg.getLoppu();
        if (a.getX() == 1 && a.getY() == 1 && l.getX() == 3 && l.getY() == 3) {
            sijoitti = true;
        }
        assertTrue(sijoitti);
    }
}
