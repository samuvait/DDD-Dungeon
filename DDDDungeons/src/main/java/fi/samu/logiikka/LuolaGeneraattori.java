

package fi.samu.logiikka;

import java.util.ArrayList;
import java.util.Random;
import javafx.util.Pair;

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
    private ArrayList<Pair> sijoitetutHuoneet;
    
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
        if (huoneenLeveysMinimi < 3 ||huoneenLeveysMinimi > 8) {
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
        huoneLista = new ArrayList<Integer>();
        int hlmin = huoneenLeveysMinimi;
        int hlmax = huoneenLeveysMaksimi;
        int kaytettyPintaAla = 0;
        while (kaytettyPintaAla < tayttoAste) {
            int huoneenLeveys = hlmin + rng.nextInt(hlmax - hlmin); // Huoneiden koon määritys tässä 3-8 leveys ja korkeus.
            huoneLista.add(huoneenLeveys);
            kaytettyPintaAla += huoneenLeveys * huoneenLeveys;
        }
        //System.out.println(kaytettyPintaAla);
    }
    public void sijoitaHuoneet() { // Huoneiden sijoittamisen algoritmi
        ArrayList<Integer> tempHuoneLista = new ArrayList(huoneLista);
        Random rng = new Random();
        while (!tempHuoneLista.isEmpty()) {
            int sijoitettavaHuone = tempHuoneLista.remove(tempHuoneLista.size() - 1);
            for (int i = 0; i < 100; i++) { // i kertoo montako kertaa huonetta yritetään sijoittaa tyhjään tilaan.
                int x = rng.nextInt(koko - sijoitettavaHuone - 1);
                int y = rng.nextInt(koko - sijoitettavaHuone - 1);
                if (kartta[x][y] == 0) {
                    if(tarkistaOnkoAvoin(x, y, sijoitettavaHuone)) { // tarkistaa onko huoneen paikka pelkästään 0
                        taytaHuone(x, y, sijoitettavaHuone);
                        break;
                    }
                }
            }
        }
    }
    
    public boolean tarkistaOnkoAvoin (int x, int y, int sijoitettavaHuone) { // Tarkistaa, että huone mahtuu kokonaisuudessaan tilaan
//        if (!tarkistaYmparoivat(x, y, sijoitettavaHuone)) {
//            return false;
//        }
        int viimeinenX = sijoitettavaHuone + x + 2;
        int aloitusX = x - 1;
        if (aloitusX < 0) {
            aloitusX = 0;
            viimeinenX = sijoitettavaHuone + 1;
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
    
//    public boolean tarkistaYmparoivat(int x, int y, int sijoitettavaHuone) {
//        int ylempiX = x - 1;
//        int ylempiY = x - 1;
//        int matkaX = sijoitettavaHuone + x;
//        int matkaY = sijoitettavaHuone + y;
//        if(!tarkistaRivi(ylempiX, ylempiY, matkaX + 2)) {
//            return false;
//        } else if (!tarkistaSarake(ylempiX, ylempiY, matkaY + 2)) {
//            return false;
//        } else if (!tarkistaRivi(matkaX, y, matkaX + 2)) {
//            return false;
//        } else if (!tarkistaSarake(x, matkaY, matkaY + 2)) {
//            return false;
//        }
//        return true;
//    }
//    
//    public boolean tarkistaRivi(int x, int y, int maara) { // Tarkistaa onko tietyllä rivillä pelkästään 0
//        int testX = x;
//        if (x < 0) {
//            if (y < 0) {
//                return true;
//            }
//            testX = 0;
//        }
//        for (int i = testX; i < maara || i < koko; i++) {
//            if (kartta[i][y] != 0) {
//                return false;
//            }
//        }
//        return true;
//    }
//    
//    public boolean tarkistaSarake(int x, int y, int maara) { //Tarkistaa onko tietyllä sarakkeella pelkästään 0
//        int testY = y;
//        if (y < 0) {
//            if (x < 0) {
//                return true;
//            }
//            testY = 0;
//        }
//        for (int i = testY; i < maara || i < koko; i++) {
//            if (kartta[x][i] != 0) {
//                return false;
//            }
//        }
//        return true;
//    }
    
    public void taytaHuone(int x, int y, int sijoitettavaHuone) { //Vaihtaa huoneen paikat kartalla 0:sta 1:ksi
   
        for (int muutettavaX = x; muutettavaX < sijoitettavaHuone + x && muutettavaX < koko; muutettavaX++)
            for (int muutettavaY = y; muutettavaY < sijoitettavaHuone + y && muutettavaY < koko; muutettavaY++) {
                kartta[muutettavaX][muutettavaY] = 1;
            }
    }
    
    public void luoKaytavat() {
        
    }
    
    public int getKoko() {
        if (this.koko > 0) {
            return this.koko;
        } else {
            return 0;
        }
    }
    
    public int[][] getKartta() {
        return this.kartta;
    }
    
    public ArrayList<Integer> getHuoneLista() {
        return this.huoneLista;
    }
    
}
