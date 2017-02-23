/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.samu.logiikka;

import java.util.ArrayList;
import java.util.Random;

/**
 * Luo Kaytavat huoneiden välille ja arpoo niiden aloitusreunan huoneessa.
 */
public class Kaytavat {

    private int[][] kartta;
    private ArrayList<Huone> sijoitetutHuoneet;
    private ArrayList<Huone> sijoitetutHuoneetPoisto;
    private Huone nykyinenHuone;
    private Huone seuraavaHuone;
    private int kaytavienMaara;

    /**
     * Annetaan tarvittavat tiedot käytävien luomiseen ja kartta niiden
     * sijoittamiseen.
     *
     * @param kartta Luolaston kartta, jonne käytävät sijoitetaan.
     * @param sijoitetutHuoneet Sijoitetut huoneet, joiden välille sijoitetaan
     * käytäviä.
     */
    public Kaytavat(int[][] kartta, ArrayList<Huone> sijoitetutHuoneet) {
        this.kartta = kartta;
        this.sijoitetutHuoneet = sijoitetutHuoneet;
        this.kaytavienMaara = 0;
    }

    /**
     * Käy läpi sijoitettujen käytävien listaa ja sijoittaa käytävän nykyisen ja
     * seuraavan huoneen välille päivittäen niiden arvoja.
     *
     * @return Palauttaa luotujen käytävien määrän testejä varten.
     */
    public int luoKaytavat() { // Luodaan käytävät huoneiden välillä jotta niissä pystyy liikkumaan
        sijoitetutHuoneetPoisto = new ArrayList(sijoitetutHuoneet);
        seuraavaHuone = new Huone(0, 0, 0);
        if (!sijoitetutHuoneetPoisto.isEmpty()) {
            nykyinenHuone = sijoitetutHuoneetPoisto.remove(sijoitetutHuoneetPoisto.size() - 1); // Otetaan viimeisin huone
//            System.out.println("sijoitetut huoneet poisto size: " + sijoitetutHuoneetPoisto.size());
            while (!sijoitetutHuoneetPoisto.isEmpty()) { // aloitetaan käymään läpi
                if (seuraavaHuone.getHuoneenLeveys() != 0) { // jos ei vielä määritetty seuraavaa huonetta, eli ensimmäinen kerta !!!AIHEUTTI VIAN JOSSA UUDEN KERROKSEN LUONTI EI TOIMI
                    nykyinenHuone = seuraavaHuone; // muutetaan huone johon luotiin käytävä huoneeksi, josta aloitetaan luomaan käytävä
                }
                seuraavaHuone = sijoitetutHuoneetPoisto.remove(sijoitetutHuoneetPoisto.size() - 1); // otetaan huone, johon ensimmäisestä luodaan käytävä
//                System.out.println("sijoitetut huoneet poisto size: " + sijoitetutHuoneetPoisto.size());
                arvoAloitusPaikat();
            }
        }
        return this.kaytavienMaara;
    }

    /**
     * Arpoo neljästä vaihtoehdosta käytävän aloituspaikan. 0 = huoneen yläosa 1
     * = vasen sivu 2 = oikea sivu 3 = alaosa.
     */
    public void arvoAloitusPaikat() { // Arpoo käytävän aloituspaikat
        Random rng = new Random();
        int ehto = rng.nextInt(4); // luodaan 4 vaihtoehtoa 0 = huoneen yläosa 1 = vasen sivu 2 = oikea sivu 3 = alaosa
        kayVaihtoehdotLapi(ehto);
    }

    /**
     * Arvotun vaihtoehdon perusteella vaihtaa aloituspaikan koordinaatteja
     * niin, että ne ovat oikealla sivustalla ja lähtevät liikkumaan oikeaan
     * suuntaan.
     *
     * @param ehto Huoneen sivu, jolta käytävä aloitetaan 0 = huoneen yläosa 1 =
     * vasen sivu 2 = oikea sivu 3 = alaosa.
     */
    public void kayVaihtoehdotLapi(int ehto) {
        Random rng = new Random();
        Huone huone = nykyinenHuone;
        int huoneenLeveys = huone.getHuoneenLeveys();
        int muutettavaX = huone.getX() - 1;
        int muutettavaY = huone.getY() - 1;
        int aloitusSuunta;
        if (ehto == 1 || ehto == 2) { // Vasen tai oikea sivu
            aloitusSuunta = 0; // aloitetaan siirtämällä y
            muutettavaY += 1 + rng.nextInt(huoneenLeveys);
        } else { // ylös tai alas
            aloitusSuunta = 1; // aloitetaan siirtämällä x
            muutettavaX += 1 + rng.nextInt(huoneenLeveys);
        }
        if (ehto == 2) {
            muutettavaX += huoneenLeveys + 1;
        } else if (ehto == 3) {
            muutettavaY += huoneenLeveys + 1;
        }
        Huone maaranpaa = this.seuraavaHuone; // otetaan maaranpaaksi seuraava huone
        int paaX = maaranpaa.getX() + rng.nextInt(maaranpaa.getHuoneenLeveys()); // arvotaan jokin x-koordinaatti johon käytävä liitetään
        int paaY = maaranpaa.getY() + rng.nextInt(maaranpaa.getHuoneenLeveys()); // arvotaan jokin y-koordinaatti johon käytävä liitetään
        teeKaytava(muutettavaX, muutettavaY, paaX, paaY, aloitusSuunta);
    }

    /**
     * Luo käytävän kahden pisteen välille, muuttamalla ensin x tai y
     * koordinaatit oikeaan kohtaan ja sen jälkeen jäljelle jäävän suunnan.
     *
     * @param x Käytävän aloituspaikan x-koordinaatti.
     * @param y Käytävän aloituspaikan y-koordinaatti.
     * @param pX Käytävän lopetuspaikan x-koordinaatti.
     * @param pY Käytävän lopetuspaikan y-koordinaatti.
     * @param aloitusSuunta Suunta, johon käytävää ensin aletaan luomaan, 0 = y
     * ja 1 = x;
     */
    public void teeKaytava(int x, int y, int pX, int pY, int aloitusSuunta) { // Tehdaan kaytava kahden pisteen valille
        int muutettavaX = x;
        int muutettavaY = y;
        int aloitus = aloitusSuunta;
        kartta[muutettavaY][muutettavaX] = 1;
        Random rng = new Random();
        Huone maaranpaa = this.seuraavaHuone; // otetaan maaranpaaksi seuraava huone
        int paaX = pX;
        int paaY = pY;
        while (true) {
            if (paaX == muutettavaX && paaY == muutettavaY) {
                this.kaytavienMaara++;
                break;
            }
            if (aloitus == 0) {
                aloitus = 1;
                if (muutettavaY < paaY) { // katsotaan onko lähtö y pienempi vai suurempi, ja liikutaan sen mukaan
                    while (muutettavaY != paaY) { // jos y pienempi kasvatetaan y kunnes saman arvoinen
                        muutettavaY++;
                        kartta[muutettavaY][muutettavaX] = 1;
                    }
                } else {
                    while (muutettavaY != paaY) { //jos y suurempi vähennetään y kunnes saman arvoinen
                        muutettavaY--;
                        kartta[muutettavaY][muutettavaX] = 1;
                    }
                }
            } else {
                aloitus = 0;
                if (muutettavaX < paaX) { // sama x:n arvoille y:n jälkeen
                    while (muutettavaX != paaX) {
                        muutettavaX++;
                        kartta[muutettavaY][muutettavaX] = 1;
                    }
                } else {
                    while (muutettavaX != paaX) {
                        muutettavaX--;
                        kartta[muutettavaY][muutettavaX] = 1;
                    }
                }
            }
        }
    }

    /**
     * Asettaa nykyisen huoneen. Lähinnä testejä varten.
     *
     * @param nyk Nykyiseksi huoneeksi asetettava huone.
     */
    public void setNykyinenHuone(Huone nyk) {
        if (this.nykyinenHuone == null) {
            this.nykyinenHuone = nyk;
        }
    }

    /**
     * Asettaa seuravaan huoneen. Lähinnä testejä varten.
     *
     * @param seur Seuraavaksi huoneeksi asetettava huone.
     */
    public void setSeuraavaHuone(Huone seur) {
        if (this.seuraavaHuone == null) {
            this.seuraavaHuone = seur;
        }
    }
}
