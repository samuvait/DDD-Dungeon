/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.samu.logiikka;

import java.util.ArrayList;
import java.util.Random;

/**
 * Huoneiden luomiseen ja sijoittamiseen sekä vapauden tarkistamiseen
 * tarkoitettu luokka.
 */
public class Huoneet {

    private int[][] kartta;
    private ArrayList<Integer> huoneLista;
    private int huoneenLeveysMinimi;
    private int huoneenLeveysMaksimi;
    private int tayttoAste;
    private int koko;
    private int huoneidenMaara = 0;
    private ArrayList<Huone> sijoitetutHuoneet;

    /**
     * Antaa huoneiden luomiseen tarvittavat tiedot ja kartan.
     *
     * @param kartta Luolaston kartta, johon huoneet sijoitetaan.
     * @param min Huoneiden minimikoko.
     * @param max Huoneiden maksimikoko.
     * @param koko Kartan sivun pituus.
     */
    public Huoneet(int[][] kartta, int min, int max, int koko) {
        this.kartta = kartta;
        this.huoneenLeveysMinimi = min;
        this.huoneenLeveysMaksimi = max;
        this.koko = koko;
        tayttoAste = (this.koko * this.koko) / 3; //Täyttöasteen määritys, vaikuttaa siihen paljonko kartan pinta-alasta täytetään huoneilla.
    }

    /**
     * Luo kaikki huoneet ja sitten sijoittaa ne karttaan.
     *
     * @return Antaa luolageneraattorille listan huoneista, jotta käytävät
     * voidaan sijoittaa oikein huoneiden välille.
     */
    public ArrayList<Huone> luoKaikki() {
        luoHuoneet();
        sijoitaHuoneet();
        return this.sijoitetutHuoneet;
    }

    /**
     * Luo listan huoneista perustuen täyttöasteeseen joka on määritelty
     * aiemmin.
     */
    public void luoHuoneet() { // Luo listan huoneista, jotka sijoitetaan karttaan.
        Random rng = new Random();
        huoneLista = new ArrayList();
        int hlmin = huoneenLeveysMinimi;
        int hlmax = huoneenLeveysMaksimi;
        int kaytettyPintaAla = 0;
        while (kaytettyPintaAla < tayttoAste) {
            int huoneenLeveys = hlmin + rng.nextInt(1 + hlmax - hlmin); // Huoneiden koon määritys tässä 3-8 leveys ja korkeus.
            huoneLista.add(huoneenLeveys);
            kaytettyPintaAla += huoneenLeveys * huoneenLeveys;
        }
    }

    /**
     * Pyrkii sijoittamaan aiemmin luodut huoneet kartalle, kokeillen jokaista
     * huonetta 100 kertaa.
     */
    public void sijoitaHuoneet() { // Huoneiden sijoittamisen algoritmi
        ArrayList<Integer> tempHuoneLista = new ArrayList(huoneLista);
        this.sijoitetutHuoneet = new ArrayList();
        Random rng = new Random();
        while (!tempHuoneLista.isEmpty()) {
            int sijoitettavaHuone = tempHuoneLista.remove(tempHuoneLista.size() - 1);
            for (int i = 0; i < 100; i++) { // i kertoo montako kertaa huonetta yritetään sijoittaa tyhjään tilaan.
                int x = 1 + rng.nextInt(koko - sijoitettavaHuone - 1); // ei sijoiteta reunoille, jotta käytävä voi alkaa miltä tahansa sivulta
                int y = 1 + rng.nextInt(koko - sijoitettavaHuone - 1);
                if (kartta[x][y] == 0) {
                    if (tarkistaOnkoAvoin(x, y, sijoitettavaHuone)) { // tarkistaa onko huoneen paikka pelkästään 0
                        taytaHuone(x, y, sijoitettavaHuone);
                        this.huoneidenMaara++;
                        Huone uusiHuone = new Huone(x, y, sijoitettavaHuone);
                        this.sijoitetutHuoneet.add(uusiHuone);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Tarkistaa voidaanko tähän tilaan sijoittaa haluttu huone eli mahtuuko se
     * siihen, ilman että se menee minkään muun huoneen vierelle.
     *
     * @param x huoneen vasemman yläkulman x koordinaatti
     * @param y huoneen vasemman yläkulman y koordinaatti
     * @param sijoitettavaHuone huoneen sivun leveys.
     * @return Jos voidaan sijoittaa, palauttaa tosi.
     */
    public boolean tarkistaOnkoAvoin(int x, int y, int sijoitettavaHuone) { // Tarkistaa, että huone mahtuu kokonaisuudessaan tilaan
        int viimeinenX = sijoitettavaHuone + x + 1; // viimeisen tarkistettavan täytyy mennä yli, jotta huoneet eivät ole vierekkäin
        int aloitusX = x - 1; //viimeisen aloitettavan täytyy alkaa aiemmin samasta syystä
        if (aloitusX < 0) {
            aloitusX = 0;
        }
        int viimeinenY = sijoitettavaHuone + y + 1;
        int aloitusY = y - 1;
        if (aloitusY < 0) {
            aloitusY = 0;
        }
        for (int tarkistettavaY = aloitusY; tarkistettavaY <= viimeinenY && tarkistettavaY < koko; tarkistettavaY++) { // varmista että on taulukon rajoissa
            for (int tarkistettavaX = aloitusX; tarkistettavaX <= viimeinenX && tarkistettavaX < koko; tarkistettavaX++) {
                if (kartta[tarkistettavaY][tarkistettavaX] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Täyttää huoneen ykkösillä kartalla.
     *
     * @param x vasen yläkulma x
     * @param y vasen yläkulma y
     * @param sijoitettavaHuone sivun pituus
     */
    public void taytaHuone(int x, int y, int sijoitettavaHuone) { //Vaihtaa huoneen paikat kartalla 0:sta 1:ksi
        for (int muutettavaY = y; muutettavaY < sijoitettavaHuone + y && muutettavaY < koko; muutettavaY++) {
            for (int muutettavaX = x; muutettavaX < sijoitettavaHuone + x && muutettavaX < koko; muutettavaX++) {
                kartta[muutettavaY][muutettavaX] = 1;
            }
        }
    }

    public int getHuoneidenMaara() {
        return this.huoneidenMaara;
    }

    public ArrayList<Integer> getHuoneLista() {
        return this.huoneLista;
    }

    public int[][] getKartta() {
        return this.kartta;
    }
}
