/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class HuoneetTest {

    public HuoneetTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Test
    public void luotuHuoneetOikeanKokoisiksi() {
        boolean oikeanKokoisetHuoneet = true;
        LuolaGeneraattori lg = new LuolaGeneraattori(25, 4, 7);
        lg.alustaLuola();
        ArrayList<Integer> huoneLista = new ArrayList(lg.getHuoneet().getHuoneLista());
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
        ArrayList<Integer> huoneLista = new ArrayList(lg.getHuoneet().getHuoneLista());
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
        ArrayList<Integer> huoneLista = new ArrayList(lg.getHuoneet().getHuoneLista());
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
        ArrayList<Integer> huoneLista = new ArrayList(lg.getHuoneet().getHuoneLista());
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
    public void tarkistaaOikeinTyhjaksi() {
        int koko = 25;
        LuolaGeneraattori lg = new LuolaGeneraattori();
        lg.alustaKartta(koko);
        int[][] kartta = lg.getKartta();
        kartta[5][5] = 1;
        Huoneet uusi = new Huoneet(kartta, 3, 5, 8);
        boolean tark = uusi.tarkistaOnkoAvoin(2, 2, 5);
        assertTrue(!tark);
    }

    @Test
    public void tayttaaOikein() {
        int koko = 25;
        LuolaGeneraattori lg = new LuolaGeneraattori();
        lg.alustaKartta(koko);
        int[][] kartta = lg.getKartta();
        Huoneet uusi = new Huoneet(kartta, 3, 8, koko);
        uusi.taytaHuone(5, 5, 5);
        boolean tark = true;
        for (int i = 5; i < 10; i++) {
            for (int j = 5; j < 10; j++) {
                if (kartta[i][j] == 0) {
                    tark = false;
                    break;
                }
            }
        }
        assertTrue(tark);
    }
}
