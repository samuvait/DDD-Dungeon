package fi.samu.gui;

import fi.samu.mekaniikat.Liikkuminen;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Samu
 */
public class Kuuntelija implements KeyListener {

    private Liikkuminen liikkuminen;
    private RuudukkoUI gui;

    public Kuuntelija(Liikkuminen liikkuminen, RuudukkoUI gui) {
        this.liikkuminen = liikkuminen;
        this.gui = gui;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        ArrayList<String> tekstit = new ArrayList();
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            tekstit = liikkuminen.liiku(0);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            tekstit = liikkuminen.liiku(1);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            tekstit = liikkuminen.liiku(2);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            tekstit = liikkuminen.liiku(3);
        }
        gui.paivita(tekstit);
    }

    public void setLiikkuminen(Liikkuminen l) {
        this.liikkuminen = l;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
