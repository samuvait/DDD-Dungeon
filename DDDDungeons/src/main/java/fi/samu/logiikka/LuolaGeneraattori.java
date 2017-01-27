

package fi.samu.logiikka;

import java.util.ArrayList;
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
    
    public LuolaGeneraattori(int koko, int huoneenLeveysMinimi, int huoneenLeveysMaksimi) {
        if (koko < 25) {
            this.koko = 25;  // kartan minimikoko tällä hetkellä 25
        } else {
            this.koko = koko;  // Kartan sivun pituus, vaatii kokeilua oikean löytämiseksi
        }
        if (huoneenLeveysMinimi < 3) {
            this.huoneenLeveysMinimi = 3;  // kartan minimikoko tällä hetkellä 25
        } else {
            this.huoneenLeveysMinimi = huoneenLeveysMinimi;  // Kartan sivun pituus, vaatii kokeilua oikean löytämiseksi
        }
        if (huoneenLeveysMaksimi > 8) {
            this.huoneenLeveysMaksimi = 8;  // kartan minimikoko tällä hetkellä 25
        } else {
            this.huoneenLeveysMaksimi = huoneenLeveysMaksimi;  // Kartan sivun pituus, vaatii kokeilua oikean löytämiseksi
        }
        this.tayttoAste = (this.koko * this.koko) / 2; //Täyttöasteen määritys, vaikuttaa siihen paljonko kartan pinta-alasta täytetään huoneilla.
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
    
    public void alustaKartta(int koko) {
        kartta = new int[koko][koko];
        for (int i = 0; i < koko; i++) {
            for (int j = 0; j < koko; j++) {
                kartta[i][j] = 0; // Alustetaan kaikki kartan alkiot siten että niihin ei pysty liikkumaan eli 0
            }
        }
    }
    
    public void luoHuoneet() {
        Random rng = new Random();
        huoneLista = new ArrayList<Integer>();
        int kaytettyPintaAla = 0;
        while (kaytettyPintaAla < tayttoAste) {
            int huoneenLeveys =  3 + rng.nextInt(5); // Huoneiden koon määritys tässä 3-8 leveys ja korkeus.
            huoneLista.add(huoneenLeveys);
            kaytettyPintaAla += huoneenLeveys * huoneenLeveys;
        }
        //System.out.println(kaytettyPintaAla);
    }
    public void sijoitaHuoneet() {
        while (!huoneLista.isEmpty()) {
            int sijoitettavaHuone = huoneLista.remove(huoneLista.size() - 1);
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
