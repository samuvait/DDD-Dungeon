
package fi.samu.logiikka;

/**
 *
 * @author Samu
 */
public class Main {
    
    public static void main(String[] args) {
        LuolaGeneraattori lg = new LuolaGeneraattori();
        lg.alustaLuola();
        lg.tulostaKartta();
        System.out.println(lg.getAlku());
        System.out.println(lg.getLoppu());
    }
    
}
