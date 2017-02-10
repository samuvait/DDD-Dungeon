
package fi.samu.mekaniikat;

import java.util.ArrayList;
import java.util.Random;
import fi.samu.logiikka.LuolaGeneraattori;
import fi.samu.logiikka.Koordinaatti;
/**
 *
 * @author Samu
 */
public class Liikkuminen {
    
    private LuolaGeneraattori lg;
    private Koordinaatti alkuPaikka;
    private Koordinaatti loppuPaikka;
    private Pelaaja pelaaja;
    
    public Liikkuminen(LuolaGeneraattori lg) {
        this.lg = lg;
        this.pelaaja = new Pelaaja(0, 0);
    }
    
    public void aloitaLiikkuminen() {
        lg.alustaLuola();
        alkuPaikka = lg.getAlku();
        loppuPaikka = lg.getLoppu();
        pelaaja.setKoordinaatit(alkuPaikka);
        
    }
}
