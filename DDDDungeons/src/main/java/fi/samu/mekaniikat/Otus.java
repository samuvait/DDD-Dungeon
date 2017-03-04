package fi.samu.mekaniikat;

import fi.samu.logiikka.Koordinaatti;
import static java.lang.Math.abs;
import java.util.Random;

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
    private int xpBonus;

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
        liikkuuko = new Random().nextInt(2);
        taisteleeko = 0;
        piirretaanko = 0;
        this.tyyppi = tyyppi;
        this.xpBonus = tyyppi * 4;
        if (tyyppi == 0) {
            luoOtus(5, 2, "rat", "  r  ");
        } else if (tyyppi == 1) {
            luoOtus(8, 2, "giant spider", "}X{");
        } else if (tyyppi == 2) {
            luoOtus(9, 3, "zombie", " Z ");
        } else if (tyyppi == 3) {
            luoOtus(10, 4, "skeleton", " S ");
        } else if (tyyppi == 4) {
            luoOtus(13, 3, "orc", "  o  ");
        } else if (tyyppi == 5) {
            luoOtus(15, 5, "giant mantis", "\\Y/");
        } else if (tyyppi == 6) {
            luoOtus(17, 5, "ogre", " O ");
        } else if (tyyppi == 7) {
            luoOtus(20, 5, "werewolf", " W ");
        } else if (tyyppi == 8) {
            luoOtus(30, 3, "gargoyle", " M ");
        } else if (tyyppi == 9) {
            luoOtus(40, 10, "small giant", "  g  ");
        } else if (tyyppi == 10) {
            luoOtus(32, 7, "fire elemental", "<^<");
        } else if (tyyppi == 11) {
            luoOtus(45, 10, "minotaur", " M ");
        } else if (tyyppi == 12) {
            luoOtus(50, 12, "rock giant", " G ");
        } else if (tyyppi == 13) {
            luoOtus(100, 15, "red dragon", " D ");
        } else {
            luoOtus(5, 2, "rat", " r ");
        }
    }

    /**
     * Luo uuden otuksen annetuilla parametreillä.
     *
     * @param hp Vihollisen elämäpisteet.
     * @param attackPower Vihollisen hyökkäysvoima.
     * @param kuvaus Vihollisen kuvaus Combal logia varten.
     * @param tunnus Vihollisen tunnus guita varten.
     */
    public void luoOtus(int hp, int attackPower, String kuvaus, String tunnus) {
        this.hitPoints = hp;
        this.hpMax = hp;
        this.attackPower = attackPower;
        this.kuvaus = kuvaus;
        this.tunnus = tunnus;
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

    public int getXpBonus() {
        return this.xpBonus;
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
