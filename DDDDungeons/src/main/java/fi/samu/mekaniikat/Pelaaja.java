package fi.samu.mekaniikat;

import fi.samu.logiikka.Koordinaatti;

/**
 * Luokka on pelaajan hahmo pelissä ja sisältää taistelemiseen ja liikkumiseen
 * tarvittavat tiedot
 */
public class Pelaaja {

    private Koordinaatti koordinaatit;
    private int hitPoints;
    private int attackPower;
    private int experience;

    public Pelaaja(int x, int y) {
        koordinaatit = new Koordinaatti(x, y);
        this.hitPoints = 30;
        this.attackPower = 5;
        this.experience = 0;
    }

    public void setKoordinaatit(Koordinaatti krdn) {
        koordinaatit.setX(krdn.getX());
        koordinaatit.setY(krdn.getY());
    }

    public void setHitPoints(int hp) {
        this.hitPoints = hp;
    }

    public void setAttackPower(int ap) {
        this.attackPower = ap;
    }

    public void setExperience(int exp) {
        this.experience = exp;
    }

    public void growExperience(int exp) {
        this.experience += exp;
    }

    public Koordinaatti getKoordinaatit() {
        return this.koordinaatit;
    }

    public int getHitPoints() {
        return this.hitPoints;
    }

    public int getAttackPower() {
        return this.attackPower;
    }

    public int getExperience() {
        return this.experience;
    }
}
