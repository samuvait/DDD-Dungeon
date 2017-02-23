package fi.samu.logiikka;

import fi.samu.gui.RuudukkoUI;
import fi.samu.mekaniikat.Liikkuminen;
import java.util.Scanner;
import javax.swing.UIManager;
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