package fi.samu.mekaniikat;

import java.util.ArrayList;
import java.util.Random;
import fi.samu.logiikka.LuolaGeneraattori;
import fi.samu.logiikka.Koordinaatti;
import fi.samu.logiikka.Huone;

/**
 * Luokka hoitaa luolastossa liikkumisen Pelaajan osalta, sekä päivittää
 * luolastoa pelaajan edetessä syvemmälle ja syvemmälle.
 */
public class Liikkuminen {

    private LuolaGeneraattori lg;
    private Koordinaatti alkuPaikka;
    private Koordinaatti loppuPaikka;
    private Pelaaja pelaaja;
    private int kerros;
    private ArrayList<Otus> vihollisLista;
    private int[][] kartta;
    private int koko;
    private Viholliset viholliset;
    private int suunta;
    private Taisteleminen taisto;

    /**
     * Määrittelee luolageneraattorin ja luo pelaajan, sekä aloittaa
     * liikkumisen.
     *
     * @param lg Luolageneraattori, jota käytetään kerroksen luomiseen ja
     * liikkumiseen luolastossa.
     */
    public Liikkuminen(LuolaGeneraattori lg) {
        this.lg = lg;
        this.pelaaja = new Pelaaja(0, 0);
        this.kerros = 1;
        koko = lg.getKoko();
        aloitaLiikkuminen();
    }

    /**
     * Aloittaa liikkumisen uudessa kerroksessa alustamalla luolan ja
     * sijoittamalla pelaajan ja lopetuspisteen kartalle.
     */
    public void aloitaLiikkuminen() {
        lg.alustaLuola();
        alkuPaikka = lg.getAlku();
        loppuPaikka = lg.getLoppu();
        kartta = lg.getKartta();
        kartta[loppuPaikka.getY()][loppuPaikka.getX()] = 5; // tekstikäyttöliittymän line
        pelaaja.setKoordinaatit(alkuPaikka);
        kartta[alkuPaikka.getY()][alkuPaikka.getX()] = 8; // tekstikäyttöliittymän line
        ArrayList<Huone> huoneet = lg.getSijoitetutHuoneet();
        viholliset = new Viholliset(koko, this.kerros, huoneet, lg.getKartta());
        viholliset.luoViholliset();
        taisto = new Taisteleminen(viholliset.getVihollisLista(), kartta);
        viholliset.setTaistelu(taisto);
    }

    /**
     * Liikuttaa pelaajaa annettuun suuntaan, ja tarkistaa voiko sinne liikkua.
     * Jos pelaaja osuu viholliseen, pelaaja taistelee vihollisen kanssa.
     *
     * @param suunta Suunta johon pelaaja liikkuu 0 = ylos 1 = vasen 2 = oikea 3
     * = alas
     * @return Palauttaa combat logiin tulostettavien tekstien listan.
     */
    public ArrayList<String> liiku(int suunta) { // 0 = ylos 1 = vasen 2 = oikea 3 = alas
        Koordinaatti pelKoor = pelaaja.getKoordinaatit();
        int x = pelKoor.getX();
        int y = pelKoor.getY();
        ArrayList<String> tekstit = new ArrayList();
        kartta[y][x] = 1; // tekstikäyttöliittymän line
        if (suunta == 0 && y > 0) {
            if (kartta[y - 1][x] != 0) {
                y--;
                if (taisto.tarkista(x, y)) {
                    taisto.taistele(pelaaja);
                    y++;
                }
            }
        } else if (suunta == 1 && x > 0) {
            if (kartta[y][x - 1] != 0) {
                x--;
                if (taisto.tarkista(x, y)) {
                    taisto.taistele(pelaaja);
                    x++;
                }
            }
        } else if (suunta == 2 && x < koko - 1) {
            if (kartta[y][x + 1] != 0) {
                x++;
                if (taisto.tarkista(x, y)) {
                    taisto.taistele(pelaaja);
                    x--;
                }
            }
        } else if (suunta == 3 && y < koko - 1) {
            if (kartta[y + 1][x] != 0) {
                y++;
                if (taisto.tarkista(x, y)) {
                    taisto.taistele(pelaaja);
                    y--;
                }
            }
        }
        if (x == this.loppuPaikka.getX() && y == this.loppuPaikka.getY()) {
            kerros++;
            tekstit.add("You go down the stairs");
            tekstit.add(" ");
            tekstit.add(" ");
            aloitaLiikkuminen();
        } else {
            taisto.tarkistaKuoliko();
            kartta[y][x] = 8; // annetaan pelaajalle merkki 8 ; tekstikäyttöliittymän line
            pelaaja.setKoordinaatit(new Koordinaatti(x, y));
            viholliset.liikuta(this.pelaaja);
            tekstit = taisto.getTekstit();
        }
        taisto.setTekstit(new ArrayList());
        return tekstit;
    }

    public void setSuunta(int i) {
        this.suunta = i;
    }

    public int getSuunta() {
        return this.suunta;
    }

    public Viholliset getViholliset() {
        return this.viholliset;
    }

    public int getKerros() {
        return this.kerros;
    }

    public Pelaaja getPelaaja() {
        return this.pelaaja;
    }

    public void lisaaLista(ArrayList<String> alkup, ArrayList<String> lisattava) {
        for (String s : lisattava) {
            alkup.add(s);
        }
    }
}
