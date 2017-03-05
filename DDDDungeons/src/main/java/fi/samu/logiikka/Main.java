package fi.samu.logiikka;

import fi.samu.gui.RuudukkoUI;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Käynnistää pelin.
 */
public class Main {

    /**
     * Käynnistää graafisen käyttöliittymän.
     *
     * @param args Command-line arguments
     * @throws ClassNotFoundException Virhe jos luokkaa ei löydetä.
     * @throws InstantiationException Virhe jos ei voida aloittaa.
     * @throws IllegalAccessException Virhe jos ei voida käsitellä.
     * @throws UnsupportedLookAndFeelException Virhe jos haluttua look and
     * feelia ei saada
     */
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        RuudukkoUI gui = new RuudukkoUI(25);
        javax.swing.SwingUtilities.invokeLater(gui);
    }
}
