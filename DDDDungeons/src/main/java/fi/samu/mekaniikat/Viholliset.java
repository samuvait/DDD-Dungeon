package fi.samu.mekaniikat;

import fi.samu.logiikka.Koordinaatti;
import fi.samu.logiikka.Huone;
import java.util.ArrayList;
import java.util.Random;

/**
 * Luokka luo ja sijoittaa hirviöt joita vastaan pelaaja taistelee, sekä
 * tarkistaa osuuko pelaaja niihin ja hoitaa niiden liikkeen
 */
public class Viholliset {

    private int kerros;
    private int koko;
    private ArrayList<Otus> viholliset;
    private ArrayList<Huone> sijoitetutHuoneet;
    private int[][] kartta;
    private int varmistus;
    private int lkm;

    public Viholliset(int koko, int kerros, ArrayList<Huone> sijoitetutHuoneet, int[][] kartta) {
        this.kartta = kartta;
        this.kerros = kerros;
        this.sijoitetutHuoneet = new ArrayList(sijoitetutHuoneet);
    }

    public void luoViholliset() {
        viholliset = new ArrayList();
        Random rng = new Random();
        this.sijoitetutHuoneet.remove(0);
        varmistus = sijoitetutHuoneet.size();
        lkm = 0;
        int tyyppiMax = kerros;
        if (tyyppiMax > 1) {
            tyyppiMax = 1;
        }
        for (int i = 0; i < sijoitetutHuoneet.size(); i++) {
            Huone otuksenHuone = sijoitetutHuoneet.get(i);
            int tyyppi = rng.nextInt(tyyppiMax);
            int otusX = otuksenHuone.getX() + rng.nextInt(otuksenHuone.getHuoneenLeveys());
            int otusY = otuksenHuone.getY() + rng.nextInt(otuksenHuone.getHuoneenLeveys());
            if (kartta[otusY][otusX] == 1) {
                Otus lisattava = new Otus(otusX, otusY, tyyppi);
                viholliset.add(lisattava);
                lkm++;
            }
        }
        //System.out.println(varmistus + " =? " + lkm);
    }

    public boolean tarkista(Koordinaatti koordinaatti) {
        boolean ret = false;
        if (viholliset != null) {
            for (Otus otus : viholliset) {
                if (otus.getKoordinaatit().getX() == koordinaatti.getX() && otus.getKoordinaatit().getY() == koordinaatti.getY()) {
                    otus.setTaisteleeko(1);
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

    public ArrayList<Otus> getVihollisLista() {
        return this.viholliset;
    }

    public int getVarmistus() {
        return this.varmistus;
    }

    public int getlkm() {
        return this.varmistus;
    }

    public void tulostaViholliset() {
        for (Otus otus : viholliset) {
            System.out.println(otus);
        }
    }
}
