

package fi.samu.logiikka;

import java.util.ArrayList;
import java.util.Arrays;
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
    private ArrayList<Huone> sijoitetutHuoneet;
    
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
        luoKaytavat();
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
        //System.out.println(kaytettyPintaAla);
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
                    if(tarkistaOnkoAvoin(x, y, sijoitettavaHuone)) { // tarkistaa onko huoneen paikka pelkästään 0
                        taytaHuone(x, y, sijoitettavaHuone);
                        Huone uusiHuone = new Huone(x, y, sijoitettavaHuone);
                        this.sijoitetutHuoneet.add(uusiHuone);
                        break;
                    }
                }
            }
        }
    }
    
    public boolean tarkistaOnkoAvoin (int x, int y, int sijoitettavaHuone) { // Tarkistaa, että huone mahtuu kokonaisuudessaan tilaan
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
   
    public void taytaHuone(int x, int y, int sijoitettavaHuone) { //Vaihtaa huoneen paikat kartalla 0:sta 1:ksi
   
        for (int muutettavaX = x; muutettavaX < sijoitettavaHuone + x && muutettavaX < koko; muutettavaX++)
            for (int muutettavaY = y; muutettavaY < sijoitettavaHuone + y && muutettavaY < koko; muutettavaY++) {
                kartta[muutettavaX][muutettavaY] = 1;
            }
    }
    
    public void luoKaytavat() {
        if (this.sijoitetutHuoneet != null) {
            ArrayList<Huone> huoneet = new ArrayList(this.sijoitetutHuoneet);
            while (!huoneet.isEmpty()) {
                Huone huone = huoneet.remove(huoneet.size() - 1);
                arvoAloitusPaikat(huone);
            }
        }
    }
    
    public void arvoAloitusPaikat(Huone huone) { // Arpoo käytävän aloituspaikat
        Random rng = new Random();
        int x = huone.getX();
        int y = huone.getY();
        int huoneenLeveys = huone.getHuoneenLeveys();
        ArrayList<Integer> vaihtoEhdot = new ArrayList();
        vaihtoEhdot.addAll(Arrays.asList(0, 1, 2, 3));
//        for (int i = 0; i < vaihtoEhdot.size(); i++) {
//            System.out.print(vaihtoEhdot.get(i));
//        }
        int huoneenSivu = rng.nextInt(4);
        if (y == 0) {
            if (x == 0) {
                huoneenSivu = 2 + rng.nextInt(2);
            } else {
                vaihtoEhdot.remove(0);
                huoneenSivu = vaihtoEhdot.get(rng.nextInt(3));
            }
        } else if (x == 0) {
            vaihtoEhdot.remove(1);
            huoneenSivu = vaihtoEhdot.get(rng.nextInt(3));
        } else if (y + huoneenLeveys == koko - 1) {
            if (x + huoneenLeveys == koko - 1) {
                huoneenSivu = rng.nextInt(2);
            } else {
                vaihtoEhdot.remove(2);
                huoneenSivu = vaihtoEhdot.get(rng.nextInt(3));
            }
        } else if (x + huoneenLeveys == koko - 1) {
            vaihtoEhdot.remove(3);
            huoneenSivu = vaihtoEhdot.get(rng.nextInt(3));
        }
        int riviX = 0;
        int riviY = 0;
        int suunta = huoneenSivu;
        int muutettavaX = 0;
        int muutettavaY = 0;
        switch (huoneenSivu) {
            case 0:
                riviX = x + rng.nextInt(huoneenLeveys);
                riviY = y;
                muutettavaX = riviX;
                muutettavaY = riviY - 1;
                while(true) {
                    kartta[muutettavaY][muutettavaX] = 1;
//                    System.out.println(x + " " + y + " huone: " + muutettavaX + " " + muutettavaY);
                    break;
                }
                break;
            case 1:
                riviX = x;
                riviY = y + rng.nextInt(huoneenLeveys);
                muutettavaX = riviX - 1;
                muutettavaY = riviY;
                while(true) {
                    kartta[muutettavaY][muutettavaX] = 1;
//                    System.out.println(x + " " + y + " huone: " + muutettavaX + " " + muutettavaY);
                    break;
                }
                break;
            case 2:
                riviX = x + huoneenLeveys;
                riviY = y + rng.nextInt(huoneenLeveys);
                muutettavaX = riviX;
                muutettavaY = riviY;
                while(true) {
                    kartta[muutettavaY][muutettavaX] = 1;
//                    System.out.println(x + " " + y + " huone: " + muutettavaX + " " + muutettavaY);
                    break;
                }
                break;
            case 3:
                riviX = x + rng.nextInt(huoneenLeveys);
                riviY = y + huoneenLeveys;
                muutettavaX = riviX;
                muutettavaY = riviY;
                while(true) {
                    kartta[muutettavaY][muutettavaX] = 1;
//                    System.out.println(x + " " + y + " huone: " + muutettavaX + " " + muutettavaY);
                    break;
                }
                break;
            default:
                System.out.println("default");
                break;
        }
        aloitaKaytava(riviX, riviY, suunta);
    }
    
    public void aloitaKaytava(int x, int y, int suunta) {
        
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
