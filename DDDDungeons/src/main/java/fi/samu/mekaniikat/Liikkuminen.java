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
        if (suunta == 0 && y > 0 && kartta[y - 1][x] != 0) {
            y = tarkistaLiike(x, y, true, -1, tekstit);
        } else if (suunta == 1 && x > 0 && kartta[y][x - 1] != 0) {
            x = tarkistaLiike(y, x, false, -1, tekstit);
        } else if (suunta == 2 && x < koko - 1 && kartta[y][x + 1] != 0) {
            x = tarkistaLiike(y, x, false, 1, tekstit);
        } else if (suunta == 3 && y < koko - 1 && kartta[y + 1][x] != 0) {
            y = tarkistaLiike(x, y, true, 1, tekstit);
        }
        if (x == this.loppuPaikka.getX() && y == this.loppuPaikka.getY()) {
            kerros++;
            tekstit.add(" ");
            tekstit.add(" ");
            tekstit.add("You go down the stairs");
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

    /**
     * Tarkistaa voiko pelaaja liikkua haluttuun suuntaan vai taisteleeko, ja
     * laskee sen perusteella koordinaatin.
     *
     * @param muuttumaton Koordinaatti, jota ei muutettaisi pelaajan liikkeen
     * aikana.
     * @param koordinaatti Koordinaatti, jota voidaan muuttaa pelajaan liikkeen
     * aikana.
     * @param onY Onko muutettava koordinaatti y-koordinaatti.
     * @param muutos Muutos, jolla koordinaattia muutetaan.
     * @param tekstit Combat logiin lisättävien tekstien lista.
     * @return Palauttaa muutetun koordinaatin arvon jos pelaaja pystyi
     * liikkumaan.
     */
    public int tarkistaLiike(int muuttumaton, int koordinaatti, boolean onY, int muutos, ArrayList<String> tekstit) {
        int x = 0;
        int y = 0;
        int k = koordinaatti;
        k += muutos;
        if (onY) {
            x = muuttumaton;
            y = k;
        } else if (!onY) {
            x = k;
            y = muuttumaton;
        }
        if (taisto.tarkista(x, y)) {
            tekstit.add(" ");
            tekstit.add(" ");
            taisto.taistele(pelaaja);
            k -= muutos;
        }
        return k;
    }

//    public ArrayList<String> yhdistaListat(ArrayList<String> palautettava, ArrayList<String> lisattava) {
//        for (String s : lisattava) {
//            palautettava.add(s);
//        }
//        return palautettava;
//    }
    public void setSuunta(int i) {
        this.suunta = i;
    }

    public void setLoppu(Koordinaatti k) {
        this.loppuPaikka = k;
    }

    public void setTaisteleminen(Taisteleminen taisteleminen) {
        this.taisto = taisteleminen;
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
}
