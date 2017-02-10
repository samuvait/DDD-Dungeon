
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
    public void luotuHuoneetOikeanKokoisiksi() {
        boolean oikeanKokoisetHuoneet = true;
        LuolaGeneraattori lg = new LuolaGeneraattori(25, 4, 7);
        lg.alustaLuola();
        ArrayList<Integer> huoneLista = new ArrayList(lg.getHuoneLista());
        while (!huoneLista.isEmpty()) {
            int huoneenKoko = huoneLista.remove(huoneLista.size() - 1);
            if (huoneenKoko < 4 || huoneenKoko > 7) {
                oikeanKokoisetHuoneet = false;
                break;
            }
        }
        assertTrue(oikeanKokoisetHuoneet);
    }
    
    @Test
    public void luotuHuoneetOikeinLiianIsoillaArvoilla() {
        boolean oikeanKokoisetHuoneet = true;
        int minimi = 26;
        int maksimi = 50;
        LuolaGeneraattori lg = new LuolaGeneraattori(25, minimi, maksimi);
        lg.alustaLuola();
        ArrayList<Integer> huoneLista = new ArrayList(lg.getHuoneLista());
        while (!huoneLista.isEmpty()) {
            int huoneenKoko = huoneLista.remove(huoneLista.size() - 1);
            if (huoneenKoko < 3 || huoneenKoko > 8) {
                oikeanKokoisetHuoneet = false;
                break;
            }
        }
        assertTrue(oikeanKokoisetHuoneet);
    }
    
    @Test
    public void luotuHuoneetOikeinLiianPienillaArvoilla() {
        boolean oikeanKokoisetHuoneet = true;
        int minimi = 0;
        int maksimi = 2;
        LuolaGeneraattori lg = new LuolaGeneraattori(25, minimi, maksimi);
        lg.alustaLuola();
        ArrayList<Integer> huoneLista = new ArrayList(lg.getHuoneLista());
        while (!huoneLista.isEmpty()) {
            int huoneenKoko = huoneLista.remove(huoneLista.size() - 1);
            if (huoneenKoko < 3 || huoneenKoko > 8) {
                oikeanKokoisetHuoneet = false;
                break;
            }
        }
        assertTrue(oikeanKokoisetHuoneet);
    }
    
    @Test
    public void huoneetPienempiaKuinKartta() {
        boolean huoneetPienempia = true;
        LuolaGeneraattori lg = new LuolaGeneraattori();
        lg.alustaLuola();
        int summa = 0;
        ArrayList<Integer> huoneLista = new ArrayList(lg.getHuoneLista());
        while (!huoneLista.isEmpty()) {
            int huoneenKoko = huoneLista.remove(huoneLista.size() - 1);
            summa += huoneenKoko * huoneenKoko;
        }
        if (summa >= lg.getKoko() * lg.getKoko()) {
            huoneetPienempia = false;
        }
        assertTrue(huoneetPienempia);
    }
    
    @Test
    public void oikeaMaaraHuoneitaJaKaytavia() {
        boolean kaikissaOikeaMaara = true;
        for (int i = 0; i < 1000; i++) {
            LuolaGeneraattori lg = new LuolaGeneraattori();
            lg.alustaLuola();
            int huoneidenMaara = lg.getHuoneidenMaara();
            int kaytavienMaara = lg.getKaytavienMaara();
            if (huoneidenMaara != kaytavienMaara + 1) {
                kaikissaOikeaMaara = false;
                break;
            }
        }
        assertTrue(kaikissaOikeaMaara);
    }
}
