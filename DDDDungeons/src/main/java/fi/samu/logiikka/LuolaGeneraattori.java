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
                int x = rng.nextInt(koko - sijoitettavaHuone - 1);
                int y = rng.nextInt(koko - sijoitettavaHuone - 1);
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
        int viimeinenX = sijoitettavaHuone + x + 2; // viimeisen tarkistettavan täytyy mennä yli, jotta huoneet eivät ole vierekkäin
        int aloitusX = x - 1; //viimeisen aloitettavan täytyy alkaa aiemmin samasta syystä
        if (aloitusX < 0) {
            aloitusX = 0;
            viimeinenX = sijoitettavaHuone + 1; // jos aloitetaan 0
        }
        int viimeinenY = sijoitettavaHuone + y + 2;
        int aloitusY = y - 1;
        if (aloitusY < 0) {
            aloitusY = 0;
            viimeinenY = sijoitettavaHuone + 1;
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
        int x = huone.getX();
        int y = huone.getY();
        int huoneenLeveys = huone.getHuoneenLeveys();
        ArrayList<Integer> vaihtoEhdot = new ArrayList();
        vaihtoEhdot.addAll(Arrays.asList(0, 1, 2, 3)); // luodaan 4 vaihtoehtoa 0 = huoneen yläosa 1 = vasen sivu 2 = oikea sivu 3 = alaosa
        int ehto = rng.nextInt(4); // Arvotaan neljästä yksi
        if (y == 0) { // jos y = 0 ei voida sijoittaa yläosaan
            if (x == 0) { // jos x = 0 ei voida sijoittaa vasemmalle sivulle
                ehto = 2 + rng.nextInt(2); // jää 2 ja 3
            } else {
                vaihtoEhdot.remove(0); // poistetaan yläosa
                ehto = vaihtoEhdot.get(rng.nextInt(3));
            }
        } else if (x == 0) { // Jos vain x = 0
            vaihtoEhdot.remove(1); // poistetaan vasen sivu valikoimasta
            ehto = vaihtoEhdot.get(rng.nextInt(3));
        } else if (y + huoneenLeveys == koko - 1) { // jos huone menee aivan alareunaan
            if (x + huoneenLeveys == koko - 1) {
                ehto = rng.nextInt(2); // ei voida sijoittaa 2 ja 3
            } else {
                vaihtoEhdot.remove(3);
                ehto = vaihtoEhdot.get(rng.nextInt(3));
            }
        } else if (x + huoneenLeveys == koko - 1) { // jos huone menee oikeaan reunaan
            vaihtoEhdot.remove(2);
            ehto = vaihtoEhdot.get(rng.nextInt(3)); // ei voida sijoittaa alaosaan
        }
        kayVaihtoehdotLapi(ehto, huone);
    }
    
    public void kayVaihtoehdotLapi(int ehto, Huone huone) {
        Random rng = new Random();
        int x = huone.getX();
        int y = huone.getY();
        int huoneenLeveys = huone.getHuoneenLeveys();
        int muutettavaX;
        int muutettavaY;
        switch (ehto) { //kaikki tapaukset 0 = huoneen yläosa 1 = vasen sivu 2 = oikea sivu 3 = alaosa
            case 0:
                muutettavaX = x + rng.nextInt(huoneenLeveys - 1);
                muutettavaY = y; // System.out.println(x + " " + y + " huone: " + muutettavaX + " " + muutettavaY);
                teeKaytavaX(muutettavaX, muutettavaY); // aloitetaan siirtämällä x
                break;
            case 1:
                muutettavaX = x;
                muutettavaY = y + rng.nextInt(huoneenLeveys - 1); // System.out.println(x + " " + y + " huone: " + muutettavaX + " " + muutettavaY);
                teeKaytavaY(muutettavaX, muutettavaY); // aloitetaan siirtämällä y
                break;
            case 2:
                muutettavaX = x + huoneenLeveys;
                muutettavaY = y + rng.nextInt(huoneenLeveys - 1); // System.out.println(x + " " + y + " huone: " + muutettavaX + " " + muutettavaY);
                teeKaytavaY(muutettavaX, muutettavaY);
                break;
            case 3:
                muutettavaX = x + rng.nextInt(huoneenLeveys - 1);
                muutettavaY = y + huoneenLeveys; // System.out.println(x + " " + y + " huone: " + muutettavaX + " " + muutettavaY);
                teeKaytavaX(muutettavaX, muutettavaY);
                break;
        }
    }

    public void teeKaytavaY(int x, int y) { // aloitetaan liikuttamalla y
        this.kaytavienMaara++;
        int muutettavaX = x;
        int muutettavaY = y;
        kartta[muutettavaY][muutettavaX] = 1;
        Random rng = new Random();
        Huone maaranpaa = this.seuraavaHuone; // otetaan maaranpaaksi seuraava huone
        int paaX = maaranpaa.getX() + rng.nextInt(maaranpaa.getHuoneenLeveys() - 1); // arvotaan jokin x-koordinaatti johon käytävä liitetään
        int paaY = maaranpaa.getY() + rng.nextInt(maaranpaa.getHuoneenLeveys() - 1); // arvotaan jokin y-koordinaatti johon käytävä liitetään
        while (paaX != muutettavaX && paaY != muutettavaY) { // silloin kun ei olla päätekoordinaateissa
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

    public void teeKaytavaX(int x, int y) { // aloitetaan liikuttamalla x
        this.kaytavienMaara++;
        int muutettavaX = x;
        int muutettavaY = y;
        kartta[muutettavaY][muutettavaX] = 1;
        Random rng = new Random();
        Huone maaranpaa = this.seuraavaHuone;
        int paaX = maaranpaa.getX() + rng.nextInt(maaranpaa.getHuoneenLeveys() - 1); // arvotaan jokin x-koordinaatti johon käytävä liitetään
        int paaY = maaranpaa.getY() + rng.nextInt(maaranpaa.getHuoneenLeveys() - 1); // arvotaan jokin y-koordinaatti johon käytävä liitetään
        while (paaX != muutettavaX && paaY != muutettavaY) { // silloin kun ei olla päätekoordinaateissa
            if (muutettavaX < paaX) { // jos alku x pienempi kuin pääte x, kasvatetaan kunnes saman arvoinen
                while (muutettavaX != paaX) {
                    muutettavaX++;
                    kartta[muutettavaY][muutettavaX] = 1;
                }
            } else { // jos alku x suurempi kuin pääte x, vähennetään kunnes saman arvoinen
                while (muutettavaX != paaX) {
                    muutettavaX--;
                    kartta[muutettavaY][muutettavaX] = 1;
                }
            }
            if (muutettavaY < paaY) { // sama y:n arvoille x:n jälkeen
                while (muutettavaY != paaY) {
                    muutettavaY++;
                    kartta[muutettavaY][muutettavaX] = 1;
                }
            } else {
                while (muutettavaY != paaY) {
                    muutettavaY--;
                    kartta[muutettavaY][muutettavaX] = 1;
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
