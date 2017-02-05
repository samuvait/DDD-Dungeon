package fi.samu.logiikka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Samu
 */
public class LuolaGeneraattori {

    private int[][] kartta;
    private int koko;
    private ArrayList<Integer> huoneLista;
    private int tayttoAste;
    private int huoneenLeveysMinimi;
    private int huoneenLeveysMaksimi;
    private ArrayList<Huone> sijoitetutHuoneet;
    private int huoneidenMaara = 0;
    private int kaytavienMaara = 0;
    private Huone nykyinenHuone;
    private Huone seuraavaHuone;

    public LuolaGeneraattori() {
        alustaLuola(25, 3, 8);
    }

    public LuolaGeneraattori(int koko) {
        alustaLuola(koko, 3, 8);
    }

    public LuolaGeneraattori(int koko, int huoneenLeveysMinimi, int huoneenLeveysMaksimi) {
        alustaLuola(koko, huoneenLeveysMinimi, huoneenLeveysMaksimi);
    }

    public void alustaLuola(int koko, int huoneenLeveysMinimi, int huoneenLeveysMaksimi) {
        if (koko < 25) {
            this.koko = 25;  // Kartan minimikoko tällä hetkellä 25
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
        this.tayttoAste = (this.koko * this.koko) / 3; //Täyttöasteen määritys, vaikuttaa siihen paljonko kartan pinta-alasta täytetään huoneilla.
        alustaKartta(this.koko);
        luoHuoneet();
        sijoitaHuoneet();
        luoKaytavat();
//        System.out.println("Huoneiden Maara: " + this.huoneidenMaara);
//        System.out.println("Kaytavien Maara: " + this.kaytavienMaara);
    }

    public void tulostaKartta() {
        boolean rivinvaihto = false;
        for (int i = 0; i < koko; i++) {
            if (rivinvaihto) {
                System.out.println("");
            }
            for (int j = 0; j < koko; j++) {
                System.out.print(kartta[i][j]);
            }
            rivinvaihto = true;
        }
    }

    public void alustaKartta(int koko) { // Nollaa kartan kaikki alkiot
        kartta = new int[koko][koko];
        for (int i = 0; i < koko; i++) {
            for (int j = 0; j < koko; j++) {
                kartta[i][j] = 0; // Alustetaan kaikki kartan alkiot siten että niihin ei pysty liikkumaan eli 0
            }
        }
    }

    public void luoHuoneet() { // Luo listan huoneista, jotka sijoitetaan karttaan.
        Random rng = new Random();
        huoneLista = new ArrayList();
        int hlmin = huoneenLeveysMinimi;
        int hlmax = huoneenLeveysMaksimi;
        int kaytettyPintaAla = 0;
        while (kaytettyPintaAla < tayttoAste) {
            int huoneenLeveys = hlmin + rng.nextInt(hlmax - hlmin); // Huoneiden koon määritys tässä 3-8 leveys ja korkeus.
            huoneLista.add(huoneenLeveys);
            kaytettyPintaAla += huoneenLeveys * huoneenLeveys;
        }
    }

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
        for (int tarkistettavaX = aloitusX; tarkistettavaX <= viimeinenX && tarkistettavaX < koko; tarkistettavaX++) { // varmista että on taulukon rajoissa
            for (int tarkistettavaY = aloitusY; tarkistettavaY <= viimeinenY && tarkistettavaY < koko; tarkistettavaY++) {
                if (kartta[tarkistettavaX][tarkistettavaY] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void taytaHuone(int x, int y, int sijoitettavaHuone) { //Vaihtaa huoneen paikat kartalla 0:sta 1:ksi

        for (int muutettavaX = x; muutettavaX < sijoitettavaHuone + x && muutettavaX < koko; muutettavaX++) {
            for (int muutettavaY = y; muutettavaY < sijoitettavaHuone + y && muutettavaY < koko; muutettavaY++) {
                kartta[muutettavaX][muutettavaY] = 1;
            }
        }
    }

    public void luoKaytavat() { // Luodaan käytävät huoneiden välillä jotta niissä pystyy liikkumaan
        if (sijoitetutHuoneet != null) {
            nykyinenHuone = sijoitetutHuoneet.remove(sijoitetutHuoneet.size() - 1); // Otetaan viimeisin huone
            while (!sijoitetutHuoneet.isEmpty()) { // aloitetaan käymään läpi
                if (seuraavaHuone != null) { // jos ei vielä määritetty seuraavaa huonetta, eli ensimmäinen kerta
                    nykyinenHuone = seuraavaHuone; // muutetaan huone johon luotiin käytävä huoneeksi, josta aloitetaan luomaan käytävä
                }
                seuraavaHuone = sijoitetutHuoneet.remove(sijoitetutHuoneet.size() - 1); // otetaan huone, johon ensimmäisestä luodaan käytävä
                arvoAloitusPaikat(nykyinenHuone);
            }
        }
    }

    public void arvoAloitusPaikat(Huone huone) { // Arpoo käytävän aloituspaikat
        Random rng = new Random();
        int ehto = rng.nextInt(4); // luodaan 4 vaihtoehtoa 0 = huoneen yläosa 1 = vasen sivu 2 = oikea sivu 3 = alaosa
        kayVaihtoehdotLapi(ehto, huone);
    }

    public void kayVaihtoehdotLapi(int ehto, Huone huone) {
        Random rng = new Random();
        int huoneenLeveys = huone.getHuoneenLeveys();
        int muutettavaX = huone.getX();
        int muutettavaY = huone.getY();
        int aloitusSuunta;
        if (ehto == 1 || ehto == 2) {
            aloitusSuunta = 0; // aloitetaan siirtämällä y
            muutettavaY += rng.nextInt(huoneenLeveys - 1);
        } else {
            aloitusSuunta = 1; // aloitetaan siirtämällä x
            muutettavaX += rng.nextInt(huoneenLeveys - 1);
        }
        if (ehto == 2) {
            muutettavaX += huoneenLeveys;
        } else if (ehto == 3) {
            muutettavaY += huoneenLeveys;
        }
        teeKaytava(muutettavaX, muutettavaY, aloitusSuunta);
    }

    public void teeKaytava(int x, int y, int aloitusSuunta) { // aloitetaan liikuttamalla y
        int muutettavaX = x;
        int muutettavaY = y;
        int aloitus = aloitusSuunta;
        kartta[muutettavaY][muutettavaX] = 1;
        Random rng = new Random();
        Huone maaranpaa = this.seuraavaHuone; // otetaan maaranpaaksi seuraava huone
        int paaX = maaranpaa.getX() + rng.nextInt(maaranpaa.getHuoneenLeveys() - 1); // arvotaan jokin x-koordinaatti johon käytävä liitetään
        int paaY = maaranpaa.getY() + rng.nextInt(maaranpaa.getHuoneenLeveys() - 1); // arvotaan jokin y-koordinaatti johon käytävä liitetään
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

    public ArrayList<Integer> getHuoneLista() {
        return this.huoneLista;
    }

}
