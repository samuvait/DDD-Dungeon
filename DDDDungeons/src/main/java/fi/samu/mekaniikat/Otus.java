package fi.samu.mekaniikat;

import fi.samu.logiikka.Koordinaatti;
import static java.lang.Math.abs;

/**
 * Luokka kuvaa hirviöitä, joita vastaan pelaaja taistelee.
 */
public class Otus {

    private Koordinaatti koordinaatit;
    private int hitPoints;
    private int attackPower;
    private int tyyppi;
    private String kuvaus;
    private int liikkuuko;
    private int taisteleeko;
    private int piirretaanko;
    private String tunnus;
    private int hpMax;

    /**
     * Antaa luotaessa otuksen koordinaatit, sekä sen tyypin.
     *
     * @param x Otuksen x-koordinaatti.
     * @param y Otuksen y-koordinaatti.
     * @param tyyppi Otuksen tyyppi eli sen vahvuus eli ap ja hp.
     */
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
        piirretaanko = 0;
        this.tyyppi = tyyppi;
        if (tyyppi == 0) {
            this.hitPoints = 5;
            this.hpMax = 5;
            this.attackPower = 1;
            this.kuvaus = "rat";
            this.tunnus = " r ";
        } else {
            this.hitPoints = 5;
            this.hpMax = 5;
            this.attackPower = 1;
            this.kuvaus = "rat";
            this.tunnus = " r ";
        }
    }

    /**
     * Tarkistaa, voiko otus taistella eli onko se koordinaattien vieressä,
     * jotka on annettu.
     *
     * @param x x-koordinaatti, joka tarkastetaan.
     * @param y y-koordinaatti, joka tarkastetaan.
     * @return Jos voi taistella, eli on vieressä, palauttaa tosi.
     */
    public boolean voiTaistella(int x, int y) {
        boolean ret = false;
        int otusX = this.getKoordinaatit().getX();
        int otusY = this.getKoordinaatit().getY();
        if (abs(otusX - x) == 1 && otusY - y == 0) {
            ret = true;
        } else if (abs(otusY - y) == 1 && otusX - x == 0) {
            ret = true;
        }
        return ret;
    }

    /**
     * Muuttaa otuksen koordinaatit.
     *
     * @param krdn muutettavat koordinaatit.
     */
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

    public void setLiikkuuko(int liikkuuko) {
        this.liikkuuko = liikkuuko;
    }
    
    public void setPiirretaanko(int piir) {
        this.piirretaanko = piir;
    }

    public int getLiikkuuko() {
        return this.liikkuuko;
    }

    public Koordinaatti getKoordinaatit() {
        return this.koordinaatit;
    }

    public int getHitPoints() {
        return this.hitPoints;
    }
    
    public int getHitPointsMax() {
        return this.hpMax;
    }

    public int getAttackPower() {
        return this.attackPower;
    }

    public int getTaisteleeko() {
        return this.taisteleeko;
    }

    public String getKuvaus() {
        return this.kuvaus;
    }
    
    public int getPiirretaanko() {
        return this.piirretaanko;
    }
    
    public int getTyyppi() {
        return this.tyyppi;
    }
    
    public String getTunnus() {
        return this.tunnus;
    }
    
    @Override
    public String toString() {
        return ("Otuksen nimi: " + kuvaus + " x: " + this.koordinaatit.getX() + " y: " + this.koordinaatit.getY());
    }
}
