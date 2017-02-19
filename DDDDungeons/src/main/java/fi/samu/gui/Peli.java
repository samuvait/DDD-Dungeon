package fi.samu.gui;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;
import fi.samu.mekaniikat.Liikkuminen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Samu
 */
public class Peli implements ActionListener {
    
    private Liikkuminen liikkuminen;
    private RuudukkoUI gui;
    
    public Peli(Liikkuminen liikkuminen, RuudukkoUI gui) {
        this.liikkuminen = liikkuminen;
        this.gui = gui;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        liikkuminen.liiku(liikkuminen.getSuunta());
        gui.paivita();
    }

    public void start() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
