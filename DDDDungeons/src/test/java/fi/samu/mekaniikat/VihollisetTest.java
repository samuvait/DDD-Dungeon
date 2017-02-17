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
        assertEquals(viholliset.getlkm(), viholliset.getVarmistus());
    }

    @Test
    public void tarkistaaOikein() {
        ArrayList<Otus> vihollisLista = viholliset.getVihollisLista();
        Otus a = vihollisLista.get(0);
        boolean oliko = false;
        if (viholliset.tarkista(a.getKoordinaatit().getX(), a.getKoordinaatit().getY())) {
            oliko = true;
        }
        assertTrue(oliko);
    }
}
