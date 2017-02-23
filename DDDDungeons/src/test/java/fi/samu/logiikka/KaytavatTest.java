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
public class KaytavatTest {

    public KaytavatTest() {
    }

    @BeforeClass
    public static void setUpClass() {
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

    @Test
    public void tekeeKaytavanOikeinY() {
        int koko = 25;
        LuolaGeneraattori lg = new LuolaGeneraattori();
        lg.alustaKartta(koko);
        int[][] kartta = lg.getKartta();
        int muutettavaX = 5;
        int muutettavaY = 5;
        int paaX = 10;
        int paaY = 10;
        int suunta = 0;
        boolean tarkistus = true;
        Kaytavat uusi = new Kaytavat(kartta, new ArrayList<Huone>());
        uusi.teeKaytava(muutettavaX, muutettavaY, paaX, paaY, suunta);
        if (muutettavaY < paaY) { // katsotaan onko lähtö y pienempi vai suurempi, ja liikutaan sen mukaan
            while (muutettavaY != paaY) { // jos y pienempi kasvatetaan y kunnes saman arvoinen
                muutettavaY++;
                if (kartta[muutettavaY][muutettavaX] == 0) {
                    tarkistus = false;
                    break;
                }
            }
        }
        if (muutettavaX < paaX && tarkistus) { // sama x:n arvoille y:n jälkeen
            while (muutettavaX != paaX) {
                muutettavaX++;
                if (kartta[muutettavaY][muutettavaX] == 0) {
                    tarkistus = false;
                    break;
                }
            }
        }
        assertTrue(tarkistus);
    }

    @Test
    public void tekeeKaytavanOikeinX() {
        int koko = 25;
        LuolaGeneraattori lg = new LuolaGeneraattori();
        lg.alustaKartta(koko);
        int[][] kartta = lg.getKartta();
        int muutettavaX = 5;
        int muutettavaY = 5;
        int paaX = 10;
        int paaY = 10;
        int suunta = 1;
        boolean tarkistus = true;
        Kaytavat uusi = new Kaytavat(kartta, new ArrayList<Huone>());
        uusi.teeKaytava(muutettavaX, muutettavaY, paaX, paaY, suunta);
        if (muutettavaX < paaX && tarkistus) { // sama x:n arvoille y:n jälkeen
            while (muutettavaX != paaX) {
                muutettavaX++;
                if (kartta[muutettavaY][muutettavaX] == 0) {
                    tarkistus = false;
                    break;
                }
            }
        }
        if (muutettavaY < paaY) { // katsotaan onko lähtö y pienempi vai suurempi, ja liikutaan sen mukaan
            while (muutettavaY != paaY) { // jos y pienempi kasvatetaan y kunnes saman arvoinen
                muutettavaY++;
                if (kartta[muutettavaY][muutettavaX] == 0) {
                    tarkistus = false;
                    break;
                }
            }
        }
        assertTrue(tarkistus);
    }

    @Test
    public void aloittaaOikeastaSuunnastaYlos() {
        int koko = 25;
        LuolaGeneraattori lg = new LuolaGeneraattori();
        lg.alustaKartta(koko);
        int[][] kartta = lg.getKartta();
        Huone nykyinen = new Huone(3, 3, 1);
        Huone seuraava = new Huone(10, 10, 1);
        boolean tarkistus = true;
        int ehto = 0;
        int muutettavaX = 3;
        int muutettavaY = 2;
        int paaX = 10;
        int paaY = 10;
        Kaytavat uusi = new Kaytavat(kartta, new ArrayList<Huone>());
        uusi.setNykyinenHuone(nykyinen);
        uusi.setSeuraavaHuone(seuraava);
        uusi.kayVaihtoehdotLapi(ehto);
        if (muutettavaX < paaX && tarkistus) { // sama x:n arvoille y:n jälkeen
            while (muutettavaX != paaX) {
                muutettavaX++;
                if (kartta[muutettavaY][muutettavaX] == 0) {
                    tarkistus = false;
                    break;
                }
            }
        }
        if (muutettavaY < paaY) { // katsotaan onko lähtö y pienempi vai suurempi, ja liikutaan sen mukaan
            while (muutettavaY != paaY) { // jos y pienempi kasvatetaan y kunnes saman arvoinen
                muutettavaY++;
                if (kartta[muutettavaY][muutettavaX] == 0) {
                    tarkistus = false;
                    break;
                }
            }
        }
        assertTrue(tarkistus);
    }

    @Test
    public void aloittaaOikeastaSuunnastaAlas() {
        int koko = 25;
        LuolaGeneraattori lg = new LuolaGeneraattori();
        lg.alustaKartta(koko);
        int[][] kartta = lg.getKartta();
        Huone nykyinen = new Huone(3, 3, 1);
        Huone seuraava = new Huone(10, 10, 1);
        boolean tarkistus = true;
        int ehto = 3;
        int muutettavaX = 3;
        int muutettavaY = 4;
        int paaX = 10;
        int paaY = 10;
        Kaytavat uusi = new Kaytavat(kartta, new ArrayList<Huone>());
        uusi.setNykyinenHuone(nykyinen);
        uusi.setSeuraavaHuone(seuraava);
        uusi.kayVaihtoehdotLapi(ehto);
        if (muutettavaX < paaX && tarkistus) { // sama x:n arvoille y:n jälkeen
            while (muutettavaX != paaX) {
                muutettavaX++;
                if (kartta[muutettavaY][muutettavaX] == 0) {
                    tarkistus = false;
                    break;
                }
            }
        }
        if (muutettavaY < paaY) { // katsotaan onko lähtö y pienempi vai suurempi, ja liikutaan sen mukaan
            while (muutettavaY != paaY) { // jos y pienempi kasvatetaan y kunnes saman arvoinen
                muutettavaY++;
                if (kartta[muutettavaY][muutettavaX] == 0) {
                    tarkistus = false;
                    break;
                }
            }
        }
        assertTrue(tarkistus);
    }

    @Test
    public void aloittaaOikeastaSuunnastaVasen() {
        int koko = 25;
        LuolaGeneraattori lg = new LuolaGeneraattori();
        lg.alustaKartta(koko);
        int[][] kartta = lg.getKartta();
        Huone nykyinen = new Huone(3, 3, 1);
        Huone seuraava = new Huone(10, 10, 1);
        boolean tarkistus = true;
        int ehto = 1;
        int muutettavaX = 2;
        int muutettavaY = 3;
        int paaX = 10;
        int paaY = 10;
        Kaytavat uusi = new Kaytavat(kartta, new ArrayList<Huone>());
        uusi.setNykyinenHuone(nykyinen);
        uusi.setSeuraavaHuone(seuraava);
        uusi.kayVaihtoehdotLapi(ehto);
        if (muutettavaY < paaY) { // katsotaan onko lähtö y pienempi vai suurempi, ja liikutaan sen mukaan
            while (muutettavaY != paaY) { // jos y pienempi kasvatetaan y kunnes saman arvoinen
                muutettavaY++;
                if (kartta[muutettavaY][muutettavaX] == 0) {
                    tarkistus = false;
                    break;
                }
            }
        }
        if (muutettavaX < paaX && tarkistus) { // sama x:n arvoille y:n jälkeen
            while (muutettavaX != paaX) {
                muutettavaX++;
                if (kartta[muutettavaY][muutettavaX] == 0) {
                    tarkistus = false;
                    break;
                }
            }
        }
        assertTrue(tarkistus);
    }

    @Test
    public void aloittaaOikeastaSuunnastaOikea() {
        int koko = 25;
        LuolaGeneraattori lg = new LuolaGeneraattori();
        lg.alustaKartta(koko);
        int[][] kartta = lg.getKartta();
        Huone nykyinen = new Huone(3, 3, 1);
        Huone seuraava = new Huone(10, 10, 1);
        boolean tarkistus = true;
        int ehto = 2;
        int muutettavaX = 4;
        int muutettavaY = 3;
        int paaX = 10;
        int paaY = 10;
        Kaytavat uusi = new Kaytavat(kartta, new ArrayList<Huone>());
        uusi.setNykyinenHuone(nykyinen);
        uusi.setSeuraavaHuone(seuraava);
        uusi.kayVaihtoehdotLapi(ehto);
        if (muutettavaY < paaY) { // katsotaan onko lähtö y pienempi vai suurempi, ja liikutaan sen mukaan
            while (muutettavaY != paaY) { // jos y pienempi kasvatetaan y kunnes saman arvoinen
                muutettavaY++;
                if (kartta[muutettavaY][muutettavaX] == 0) {
                    tarkistus = false;
                    break;
                }
            }
        }
        if (muutettavaX < paaX && tarkistus) { // sama x:n arvoille y:n jälkeen
            while (muutettavaX != paaX) {
                muutettavaX++;
                if (kartta[muutettavaY][muutettavaX] == 0) {
                    tarkistus = false;
                    break;
                }
            }
        }
        assertTrue(tarkistus);
    }
}
