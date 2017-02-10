package fi.samu.mekaniikat;

import fi.samu.logiikka.Koordinaatti;

/**
 *
 * @author Samu
 */
public class Otus {

    private Koordinaatti koordinaatit;
    private int hitPoints;
    private int attackPower;
    private int tyyppi;
    private String kuvaus;
    private int liikkuuko;
    private int taisteleeko;

    public Otus(int x, int y, int tyyppi) {
        int uusiX = x;
        if (x < 0) {
            uusiX = 0;
        }
        int uusiY = y;
        if (y < 0) {
            uusiY = 0;
        }
        koordinaatit = new Koordinaatti(uusiX, uusiY);
        liikkuuko = 0;
        taisteleeko = 0;
        if (tyyppi == 0) {
            this.hitPoints = 5;
            this.attackPower = 1;
            this.kuvaus = "rotta";
        } else {
            this.hitPoints = 5;
            this.attackPower = 1;
            this.kuvaus = "rotta";
        }
    }

    public void setKoordinaatit(Koordinaatti krdn) {
        koordinaatit.setX(krdn.getX());
        koordinaatit.setY(krdn.getY());
    }

    public void setHitPoints(int hp) {
        this.hitPoints = hp;
    }

    public void setTaisteleeko(int tst) {
        this.taisteleeko = tst;
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

    public int getTaisteleeko() {
        return this.taisteleeko;
    }

    @Override
    public String toString() {
        return ("Otuksen nimi: " + kuvaus + " x: " + this.koordinaatit.getX() + " y: " + this.koordinaatit.getY());
    }
}
