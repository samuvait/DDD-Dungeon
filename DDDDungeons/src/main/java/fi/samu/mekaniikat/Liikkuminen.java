package fi.samu.mekaniikat;

import java.util.ArrayList;
import java.util.Random;
import fi.samu.logiikka.LuolaGeneraattori;
import fi.samu.logiikka.Koordinaatti;
import fi.samu.logiikka.Huone;

/**
 *
 * @author Samu
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

    public Liikkuminen(LuolaGeneraattori lg) {
        this.lg = lg;
        this.pelaaja = new Pelaaja(0, 0);
        this.kerros = 1;
        koko = lg.getKoko();
        aloitaLiikkuminen();
    }

    public void aloitaLiikkuminen() {
        lg.alustaLuola();
        alkuPaikka = lg.getAlku();
        loppuPaikka = lg.getLoppu();
        kartta = lg.getKartta();
        kartta[loppuPaikka.getY()][loppuPaikka.getX()] = 5;
        pelaaja.setKoordinaatit(alkuPaikka);
        kartta[alkuPaikka.getY()][alkuPaikka.getX()] = 8;
        ArrayList<Huone> huoneet = lg.getSijoitetutHuoneet();
        viholliset = new Viholliset(koko, this.kerros, huoneet, lg.getKartta());
        //viholliset.luoViholliset();
//        viholliset.tulostaViholliset();
    }

    public void liiku(int suunta) { // 0 = ylos 1 = vasen 2 = oikea 3 = alas
        Koordinaatti pelKoor = pelaaja.getKoordinaatit();
        int x = pelKoor.getX();
        int y = pelKoor.getY();
        kartta[y][x] = 1;
        if (suunta == 0 && y > 0) {
            if (kartta[y - 1][x] != 0) {
                if (viholliset.tarkista(pelKoor)) {

                } else {
                    y--;
                }
            }
        } else if (suunta == 1 && x > 0) {
            if (kartta[y][x - 1] != 0) {
                x--;
            }
        } else if (suunta == 2 && x < koko - 1) {
            if (kartta[y][x + 1] != 0) {
                if (viholliset.tarkista(pelKoor)) {

                } else {
                    x++;
                }
            }
        } else if (suunta == 3 && y < koko - 1) {
            if (kartta[y + 1][x] != 0) {
                if (viholliset.tarkista(pelKoor)) {

                } else {
                    y++;
                }
            }
        }
//        if (x == this.loppuPaikka.getX() && y == this.loppuPaikka.getY()) {
//            kerros++;
//            System.out.println("Kerros vaihdettu");
//            aloitaLiikkuminen();
//        } else {
        kartta[y][x] = 8; // annetaan pelaajalle merkki 8
        pelaaja.setKoordinaatit(new Koordinaatti(x, y));
//        }
    }

    public Pelaaja getPelaaja() {
        return this.pelaaja;
    }
}
