package fi.samu.logiikka;

import fi.samu.gui.RuudukkoUI;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Samu
 */
public class Main {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        RuudukkoUI gui = new RuudukkoUI(25);
        javax.swing.SwingUtilities.invokeLater(gui);
    }
}
