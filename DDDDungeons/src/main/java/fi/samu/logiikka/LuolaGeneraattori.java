package fi.samu.logiikka;

import java.util.ArrayList;
import java.util.Random;

/**
 * Luokka luo luolaan kerroksen, ja sijoittaa sinne huoneita ja käytäviä.
 */
public class LuolaGeneraattori {

    private int[][] kartta;
    private int koko;
    private ArrayList<Integer> huoneLista;
    private int huoneenLeveysMinimi;
    private int huoneenLeveysMaksimi;
    private Huoneet huoneet;
    private ArrayList<Huone> sijoitetutHuoneet;
    private int huoneidenMaara = 0;
    private int kaytavienMaara = 0;
    private Koordinaatti alkuPiste;
    private Koordinaatti loppuPiste;

    /**
     * Luo luolan perusarvoilla.
     *
     */
    public LuolaGeneraattori() {
        this.koko = 25;
        this.huoneenLeveysMinimi = 3;
        this.huoneenLeveysMaksimi = 8;
    }

    /**
     * Luo luolan siten, että koko on annettu.
     *
     * @param koko Luolan koko ja kartan sivun pituus.
     */
    public LuolaGeneraattori(int koko) {
        if (koko < 10) {
            this.koko = 10;  // Kartan minimikoko tällä hetkellä 25
        } else {
            this.koko = koko;  // Kartan sivun pituus, vaatii kokeilua oikean löytämiseksi
        }
        this.huoneenLeveysMinimi = 3;
        this.huoneenLeveysMaksimi = 8;
    }

    /**
     * Alustaa luolan siten, että koko ja huoneen leveyksien minimi ja maksimi
     * on annettu.
     *
     * @param koko Luolan sivun koko ja kartan sivun pituus.
     * @param huoneenLeveysMinimi Pienin sallittu huoneen leveys.
     * @param huoneenLeveysMaksimi Suurin sallittu huoneen leveys.
     */
    public LuolaGeneraattori(int koko, int huoneenLeveysMinimi, int huoneenLeveysMaksimi) {
        if (koko < 10) {
            this.koko = 10;  // Kartan minimikoko tällä hetkellä 25
        } else {
            this.koko = koko;  // Kartan sivun pituus, vaatii kokeilua oikean löytämiseksi
        }
        if (huoneenLeveysMinimi < 3 || huoneenLeveysMinimi > 8) {
            this.huoneenLeveysMinimi = 3;  // huoneen minimikoko tällä hetkellä 3
        } else {
            this.huoneenLeveysMinimi = huoneenLeveysMinimi;
        }
        if (huoneenLeveysMaksimi > 8 || huoneenLeveysMaksimi < 3) {
            this.huoneenLeveysMaksimi = 8;  // huoneen maksimikoko tällä hetkellä 8
        } else {
            this.huoneenLeveysMaksimi = huoneenLeveysMaksimi;
        }
    }

    /**
     * Metodi luo luolan kerroksen, kaikki huoneet ja käytävät.
     */
    public void alustaLuola() {
        alustaKartta(this.koko);
        huoneet = new Huoneet(kartta, this.huoneenLeveysMinimi, this.huoneenLeveysMaksimi, this.koko);
        this.sijoitetutHuoneet = huoneet.luoKaikki();
        this.huoneidenMaara = huoneet.getHuoneidenMaara();
        Kaytavat kaytavat = new Kaytavat(kartta, sijoitetutHuoneet);
        this.kaytavienMaara = kaytavat.luoKaytavat();
        sijoitaAlkuJaLoppu();
    }

    /**
     * Nollaa kaikki alkiot kartan sisällä.
     *
     * @param koko sivun pituus kartalla
     */
    public void alustaKartta(int koko) { // Nollaa kartan kaikki alkiot
        kartta = new int[koko][koko];
        for (int i = 0; i < koko; i++) {
            for (int j = 0; j < koko; j++) {
                kartta[i][j] = 0; // Alustetaan kaikki kartan alkiot siten että niihin ei pysty liikkumaan eli 0
            }
        }
    }

    /**
     * Sijoittaa paikat, joista pelaaja aloittaa eli alkuPiste ja portaat alas
     * eli loppuPiste.
     */
    public void sijoitaAlkuJaLoppu() { //sijoittaa portaat, josta pelaaja tuli luolan tähän kerrokseen
        Random rng = new Random();
        Huone aloitusHuone = this.sijoitetutHuoneet.get(0);
        Huone lopetusHuone = this.sijoitetutHuoneet.get(sijoitetutHuoneet.size() - 1);
        int aloitusX = aloitusHuone.getX() + rng.nextInt(aloitusHuone.getHuoneenLeveys());
        int aloitusY = aloitusHuone.getY() + rng.nextInt(aloitusHuone.getHuoneenLeveys());
        this.alkuPiste = new Koordinaatti(aloitusX, aloitusY);
        int lopetusX = lopetusHuone.getX() + rng.nextInt(lopetusHuone.getHuoneenLeveys());
        int lopetusY = lopetusHuone.getY() + rng.nextInt(lopetusHuone.getHuoneenLeveys());
        while (lopetusX == aloitusX && lopetusY == aloitusY) {
            lopetusX = lopetusHuone.getX() + rng.nextInt(lopetusHuone.getHuoneenLeveys());
            lopetusY = lopetusHuone.getY() + rng.nextInt(lopetusHuone.getHuoneenLeveys());
        }
        this.loppuPiste = new Koordinaatti(lopetusX, lopetusY);
    }

    public Koordinaatti getAlku() {
        return this.alkuPiste;
    }

    public Koordinaatti getLoppu() {
        return this.loppuPiste;
    }

    public int getKoko() {
        return this.koko;
    }

    public int getHuoneidenMaara() {
        return this.huoneidenMaara;
    }

    public int getKaytavienMaara() {
        return this.kaytavienMaara;
    }

    public int[][] getKartta() {
        return this.kartta;
    }

    public Huoneet getHuoneet() {
        return this.huoneet;
    }

    public ArrayList<Huone> getSijoitetutHuoneet() {
        return this.sijoitetutHuoneet;
    }

    public void setSijoitetutHuoneet(ArrayList<Huone> shuo) {
        this.sijoitetutHuoneet = shuo;
    }
}
