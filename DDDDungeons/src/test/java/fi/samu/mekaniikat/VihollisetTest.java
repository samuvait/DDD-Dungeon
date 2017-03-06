/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.samu.mekaniikat;

import fi.samu.logiikka.Huone;
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

    @Test
    public void luoOikeanMaaran() {
        assertEquals(viholliset.lkm, viholliset.varmistus);
    }

    @Test
    public void luoOikeatHirviot() {
        boolean oliko = true;
        for (Otus o : viholliset.getVihollisLista()) {
            if (!(o.getTunnus().matches("\\s+[rZSoOWMgGD]\\s+") || o.getTunnus().equals("}X{") || o.getTunnus().equals("\\Y/") || o.getTunnus().equals("<^<"))) {
                oliko = false;
                break;
            }
        }
        assertTrue(oliko);
    }

    @Test
    public void palauttaaPiirrettavan() {
        Otus o = viholliset.getVihollisLista().get(0);
        Otus b = viholliset.palautaPiirrettava(o.getKoordinaatit().getX(), o.getKoordinaatit().getY());
        assertEquals(o, b);
    }

    @Test
    public void tarkistaaOikein() {
        Otus o = viholliset.getVihollisLista().get(0);
        assertTrue(viholliset.tarkistaOtukset(o.getKoordinaatit().getX(), o.getKoordinaatit().getY()));
    }

    @Test
    public void liikuttaaOikein() {
        boolean oliko = true;
        for (int i = 0; i < 100; i++) {
            LuolaGeneraattori lg = new LuolaGeneraattori();
            lg.alustaKartta(25);
            int[][] kartta = lg.getKartta();
            kartta[4][4] = 1;
            kartta[4][5] = 1;
            ArrayList<Huone> shuo = new ArrayList();
            shuo.add(new Huone(4, 4, 3));
            Viholliset viholliset = new Viholliset(25, 1, shuo, kartta);
            viholliset.getVihollisLista().clear();
            Otus o = new Otus(4, 4, 0);
            ArrayList<Otus> lista = viholliset.getVihollisLista();
            lista.add(o);
            kartta[4][4] = 1;
            kartta[5][4] = 1;
            kartta [4][5] = 1;
            kartta[4][3] = 1;
            kartta[3][4] = 1;
            Taisteleminen taisto = new Taisteleminen(lista, kartta);
            viholliset.setTaistelu(taisto);
            Pelaaja pelaaja = new Pelaaja(30, 30);
            int x = 4;
            int y = 4;
            o.setLiikkuuko(1);
            viholliset.liikuta(pelaaja);
            o = viholliset.getVihollisLista().get(0);
            if(viholliset.tarkistaOtukset(4, 4)) {
                oliko = false;
            }
        }
        assertTrue(oliko);
    }

    @Test
    public void liikuttaaOtusOikein() {
        Taisteleminen taisto = new Taisteleminen(viholliset.getVihollisLista(), kartta);
        viholliset.setTaistelu(taisto);
        Pelaaja pelaaja = new Pelaaja(30, 30);
        Otus o = viholliset.getVihollisLista().get(0);
        o.setLiikkuuko(1);
        viholliset.liiku(o, pelaaja);
        assertTrue(viholliset.tarkistaOtukset(o.getKoordinaatit().getX(), o.getKoordinaatit().getY()));
    }

    @Test
    public void otusLiikkuuOikeinYlos() {
        LuolaGeneraattori lg = new LuolaGeneraattori();
        lg.alustaKartta(25);
        int[][] kartta = lg.getKartta();
        ArrayList<Huone> shuo = new ArrayList();
        Viholliset viholliset = new Viholliset(25, 1, shuo, kartta);
        Taisteleminen taisto = new Taisteleminen(viholliset.getVihollisLista(), kartta);
        viholliset.setTaistelu(taisto);
        ArrayList<Otus> vihollisLista = viholliset.getVihollisLista();
        Otus o = new Otus(3, 3, 0);
        vihollisLista.clear();
        vihollisLista.add(o);
        kartta[3][3] = 1;
        kartta[2][3] = 1;
        o.setLiikkuuko(1);
        viholliset.liiku(o, new Pelaaja(30, 30));
        assertEquals(2, o.getKoordinaatit().getY());
        assertTrue(viholliset.tarkistaOtukset(3, 2));
    }

    @Test
    public void otusLiikkuuOikeinVasen() {
        LuolaGeneraattori lg = new LuolaGeneraattori();
        lg.alustaKartta(25);
        int[][] kartta = lg.getKartta();
        ArrayList<Huone> shuo = new ArrayList();
        Viholliset viholliset = new Viholliset(25, 1, shuo, kartta);
        Taisteleminen taisto = new Taisteleminen(viholliset.getVihollisLista(), kartta);
        viholliset.setTaistelu(taisto);
        ArrayList<Otus> vihollisLista = viholliset.getVihollisLista();
        Otus o = new Otus(3, 3, 0);
        vihollisLista.clear();
        vihollisLista.add(o);
        kartta[3][3] = 1;
        kartta[3][2] = 1;
        o.setLiikkuuko(1);
        viholliset.liiku(o, new Pelaaja(30, 30));
        assertEquals(2, o.getKoordinaatit().getX());
        assertTrue(viholliset.tarkistaOtukset(2, 3));
    }

    @Test
    public void otusLiikkuuOikeinOikea() {
        LuolaGeneraattori lg = new LuolaGeneraattori();
        lg.alustaKartta(25);
        int[][] kartta = lg.getKartta();
        ArrayList<Huone> shuo = new ArrayList();
        Viholliset viholliset = new Viholliset(25, 1, shuo, kartta);
        Taisteleminen taisto = new Taisteleminen(viholliset.getVihollisLista(), kartta);
        viholliset.setTaistelu(taisto);
        ArrayList<Otus> vihollisLista = viholliset.getVihollisLista();
        Otus o = new Otus(3, 3, 0);
        vihollisLista.clear();
        vihollisLista.add(o);
        kartta[3][3] = 1;
        kartta[3][4] = 1;
        o.setLiikkuuko(1);
        viholliset.liiku(o, new Pelaaja(30, 30));
        assertEquals(4, o.getKoordinaatit().getX());
        assertTrue(viholliset.tarkistaOtukset(4, 3));
    }

    @Test
    public void otusLiikkuuOikeinAlas() {
        LuolaGeneraattori lg = new LuolaGeneraattori();
        lg.alustaKartta(25);
        int[][] kartta = lg.getKartta();
        ArrayList<Huone> shuo = new ArrayList();
        Viholliset viholliset = new Viholliset(25, 1, shuo, kartta);
        Taisteleminen taisto = new Taisteleminen(viholliset.getVihollisLista(), kartta);
        viholliset.setTaistelu(taisto);
        ArrayList<Otus> vihollisLista = viholliset.getVihollisLista();
        Otus o = new Otus(3, 3, 0);
        vihollisLista.clear();
        vihollisLista.add(o);
        kartta[3][3] = 1;
        kartta[4][3] = 1;
        o.setLiikkuuko(1);
        viholliset.liiku(o, new Pelaaja(30, 30));
        assertEquals(4, o.getKoordinaatit().getY());
        assertTrue(viholliset.tarkistaOtukset(3, 4));
    }

    @Test
    public void otusLiikkuuOikeinKunVihollinenTiella() {
        LuolaGeneraattori lg = new LuolaGeneraattori();
        lg.alustaKartta(25);
        int[][] kartta = lg.getKartta();
        ArrayList<Huone> shuo = new ArrayList();
        Viholliset viholliset = new Viholliset(25, 1, shuo, kartta);
        Taisteleminen taisto = new Taisteleminen(viholliset.getVihollisLista(), kartta);
        viholliset.setTaistelu(taisto);
        ArrayList<Otus> vihollisLista = viholliset.getVihollisLista();
        Otus o = new Otus(3, 3, 0);
        Otus ot = new Otus(3, 4, 0);
        vihollisLista.clear();
        vihollisLista.add(o);
        vihollisLista.add(ot);
        kartta[3][3] = 1;
        kartta[4][3] = 1;
        o.setLiikkuuko(1);
        viholliset.liiku(o, new Pelaaja(30, 30));
        assertEquals(3, o.getKoordinaatit().getY());
        assertTrue(viholliset.tarkistaOtukset(3, 3));
    }

    @Test
    public void otusLiikkuuOikeinRajoillaY() {
        LuolaGeneraattori lg = new LuolaGeneraattori();
        lg.alustaKartta(25);
        int[][] kartta = lg.getKartta();
        ArrayList<Huone> shuo = new ArrayList();
        Viholliset viholliset = new Viholliset(25, 1, shuo, kartta);
        Taisteleminen taisto = new Taisteleminen(viholliset.getVihollisLista(), kartta);
        viholliset.setTaistelu(taisto);
        ArrayList<Otus> vihollisLista = viholliset.getVihollisLista();
        Otus o = new Otus(23, 23, 0);
        vihollisLista.clear();
        vihollisLista.add(o);
        kartta[23][23] = 1;
        kartta[24][23] = 1;
        o.setLiikkuuko(1);
        viholliset.liiku(o, new Pelaaja(30, 30));
        assertEquals(24, o.getKoordinaatit().getY());
        assertTrue(viholliset.tarkistaOtukset(23, 24));
    }

    @Test
    public void otusLiikkuuOikeinRajoillaX() {
        LuolaGeneraattori lg = new LuolaGeneraattori();
        lg.alustaKartta(25);
        int[][] kartta = lg.getKartta();
        ArrayList<Huone> shuo = new ArrayList();
        Viholliset viholliset = new Viholliset(25, 1, shuo, kartta);
        Taisteleminen taisto = new Taisteleminen(viholliset.getVihollisLista(), kartta);
        viholliset.setTaistelu(taisto);
        ArrayList<Otus> vihollisLista = viholliset.getVihollisLista();
        Otus o = new Otus(23, 23, 0);
        vihollisLista.clear();
        vihollisLista.add(o);
        kartta[23][23] = 1;
        kartta[23][24] = 1;
        o.setLiikkuuko(1);
        viholliset.liiku(o, new Pelaaja(30, 30));
        assertEquals(24, o.getKoordinaatit().getX());
        assertTrue(viholliset.tarkistaOtukset(24, 23));
    }

    @Test
    public void otusJattaaLiikkumattaKunRajoilla() {
        LuolaGeneraattori lg = new LuolaGeneraattori();
        lg.alustaKartta(25);
        int[][] kartta = lg.getKartta();
        ArrayList<Huone> shuo = new ArrayList();
        Viholliset viholliset = new Viholliset(25, 1, shuo, kartta);
        Taisteleminen taisto = new Taisteleminen(viholliset.getVihollisLista(), kartta);
        viholliset.setTaistelu(taisto);
        ArrayList<Otus> vihollisLista = viholliset.getVihollisLista();
        Otus o = new Otus(24, 24, 0);
        vihollisLista.clear();
        vihollisLista.add(o);
        kartta[24][24] = 1;
        o.setLiikkuuko(1);
        viholliset.liiku(o, new Pelaaja(30, 30));
        assertTrue(viholliset.tarkistaOtukset(24, 24));
    }

    @Test
    public void otusKuolee() {
        LuolaGeneraattori lg = new LuolaGeneraattori();
        lg.alustaKartta(25);
        int[][] kartta = lg.getKartta();
        ArrayList<Huone> shuo = new ArrayList();
        Viholliset viholliset = new Viholliset(25, 1, shuo, kartta);
        Taisteleminen taisto = new Taisteleminen(viholliset.getVihollisLista(), kartta);
        viholliset.setTaistelu(taisto);
        ArrayList<Otus> vihollisLista = viholliset.getVihollisLista();
        Otus o = new Otus(24, 24, 0);
        vihollisLista.clear();
        vihollisLista.add(o);
        kartta[24][24] = 1;
        kartta[23][24] = 1;
        o.setLiikkuuko(1);
        viholliset.liiku(o, new Pelaaja(24, 23));
        assertTrue(vihollisLista.isEmpty());
    }

    @Test
    public void otusJattaaLiikkumatta() {
        LuolaGeneraattori lg = new LuolaGeneraattori();
        lg.alustaKartta(25);
        int[][] kartta = lg.getKartta();
        ArrayList<Huone> shuo = new ArrayList();
        Viholliset viholliset = new Viholliset(25, 1, shuo, kartta);
        Taisteleminen taisto = new Taisteleminen(viholliset.getVihollisLista(), kartta);
        viholliset.setTaistelu(taisto);
        ArrayList<Otus> vihollisLista = viholliset.getVihollisLista();
        Otus o = new Otus(3, 3, 0);
        Otus ot = new Otus(3, 4, 0);
        vihollisLista.clear();
        vihollisLista.add(o);
        vihollisLista.add(ot);
        kartta[3][3] = 1;
        kartta[4][3] = 1;
        o.setLiikkuuko(1);
        viholliset.liiku(o, new Pelaaja(30, 30));
        assertEquals(0, o.getLiikkuuko());
    }
}
