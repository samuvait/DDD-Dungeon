package fi.samu.gui;

import fi.samu.mekaniikat.Liikkuminen;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

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
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            liikkuminen.liiku(0);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            liikkuminen.liiku(1);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            liikkuminen.liiku(2);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            liikkuminen.liiku(3);
        }
        gui.paivita();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
