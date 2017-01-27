

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
    
    public LuolaGeneraattori() {
        this.koko = 25;  // Kartan sivun pituus, vaatii kokeilua oikean löytämiseksi
        this.tayttoAste = (koko * koko) / 2; //Täyttöasteen määritys, vaikuttaa siihen paljonko kartan pinta-alasta täytetään huoneilla.
        alustaKartta();
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
    
    public void alustaKartta() {
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
}
