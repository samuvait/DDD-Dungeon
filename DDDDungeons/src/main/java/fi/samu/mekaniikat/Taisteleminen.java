/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.samu.mekaniikat;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Luolassa taistelemiseen tarvittava luokka, joka hoitaa taistelun sekä combat
 * messaget.
 */
public class Taisteleminen {

    private ArrayList<Otus> viholliset;
    private int[][] kartta;
    private ArrayList<String> tekstit;
    private int koko;
    private boolean parillinen;
    private boolean otusHyokkaa;
    private Pelaaja pelaaja;

    /**
     * Taistelemiseen tarvitaan vihollislista, sekä kartta tarkistamista varten.
     *
     * @param viholliset Luolan tässä kerroksessa olevat viholliset.
     * @param kartta Luolan kartta.
     */
    public Taisteleminen(ArrayList<Otus> viholliset, int[][] kartta) {
        this.viholliset = viholliset;
        this.kartta = kartta;
        this.koko = this.kartta.length;
        tekstit = new ArrayList();
        this.parillinen = false;
        this.otusHyokkaa = false;
    }

    /**
     * Tarkistaa, onko paikassa johon pelaaja haluaa liikkua hirviö ja jos on,
     * taistelee sitä vastaan.
     *
     * @param x Liikkeen x-koordinaatti.
     * @param y Liikkeen y-koordinaatti.
     * @return Palauttaa tosi, jos pelaaja on taistellut jotain hirviötä
     * vastaan.
     */
    public boolean tarkista(int x, int y) {
        boolean ret = false;
        if (viholliset != null) {
            for (Otus otus : viholliset) {
                if (otus.getKoordinaatit().getX() == x && otus.getKoordinaatit().getY() == y) {
                    otus.setTaisteleeko(1);
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * Otukset jotka on määritetty taistelemaan pelaajaa vastaan, taistelevat ja
     * satuttavat pelaajaa, sekä ottavat vahinkoa.
     *
     * @param pelaaja Pelaaja, jota vastaan taistellaan.
     */
    public void taistele(Pelaaja pelaaja) {
        this.pelaaja = pelaaja;
        if (viholliset != null) {
            for (Otus otus : viholliset) {
                if (otus.getTaisteleeko() == 1) {
                    int hp = pelaaja.getHitPoints();
                    int ap = pelaaja.getAttackPower();
                    int otusAP = otus.getAttackPower();
                    int otusHP = otus.getHitPoints();
                    hp -= otusAP;
                    otusHP -= ap;
                    otus.setHitPoints(otusHP);
                    pelaaja.setHitPoints(hp);
                    otus.setTaisteleeko(0);
                    if (!otusHyokkaa && this.parillinen) {
                        tekstit.add(" ");
                        tekstit.add(" ");
                        tekstit.add(" ");
                        parillinen = false;
                    } else if (!otusHyokkaa && !parillinen) {
                        parillinen = true;
                        tekstit.add(" ");
                        tekstit.add(" ");
                    }
                    tekstit.add("The " + otus.getKuvaus() + " attacks you for " + otusAP + " damage!");
                    tekstit.add("You attack the " + otus.getKuvaus() + " for " + ap + " damage!");
                    this.otusHyokkaa = false;
                }
            }
        }
    }

    /**
     * Katsoo, mitkä otukset ovat kuolleet eli niiden hp vähemmän kuin 1.
     *
     * @return Palauttaa tosi jos yksikään otus on kuollut.
     */
    public boolean tarkistaKuoliko() {
        boolean ret = false;
        for (Iterator<Otus> iterator = this.viholliset.iterator(); iterator.hasNext();) {
            Otus otus = iterator.next();
            if (otus.getHitPoints() < 1) {
                iterator.remove();
                kartta[otus.getKoordinaatit().getY()][otus.getKoordinaatit().getX()] = 1;
                pelaaja.growExperience(20 + otus.getXpBonus());
                tekstit.add("You defeated a " + otus.getKuvaus() + "!");
                ret = true;
            }
        }
        return ret;
    }

    /**
     * Katsoo, voiko annettu otus taistella sijaintinsa puolesta pelaajaa
     * vastaan.
     *
     * @param otus Otus joka taistelisi.
     * @param pelaaja Pelaajan kuvaus.
     * @return Palauttaa tosi jos voi taistella, epätosi jos ei.
     */
    public boolean voikoTaistella(Otus otus, Pelaaja pelaaja) {
        boolean ret = false;
        int x = otus.getKoordinaatit().getX();
        int y = otus.getKoordinaatit().getY();
        int pelX = pelaaja.getKoordinaatit().getX();
        int pelY = pelaaja.getKoordinaatit().getY();
        if (pelX >= koko || pelY >= koko) {
            return false;
        }
        if (abs(pelX - x) < 2 && abs(pelY - y) < 2) {
            if (voiTaistella(otus, pelX, pelY)) {
                otus.setTaisteleeko(1);
                if (this.parillinen) {
                    tekstit.add(" ");
                    parillinen = false;
                } else if (!parillinen) {
                    parillinen = true;
                }
                tekstit.add(" ");
                tekstit.add(" ");
                tekstit.add("The " + otus.getKuvaus() + " attacks!");
                tekstit.add(" ");
                this.otusHyokkaa = true;
                taistele(pelaaja);
                ret = true;
            }
        }
        return ret;
    }

    /**
     * Tarkistaa, voiko otus taistella eli onko se koordinaattien vieressä,
     * jotka on annettu.
     *
     * @param otus Otus jota tarkistetaan.
     * @param x x-koordinaatti, joka tarkastetaan.
     * @param y y-koordinaatti, joka tarkastetaan.
     * @return Jos voi taistella, eli on vieressä, palauttaa tosi.
     */
    public boolean voiTaistella(Otus otus, int x, int y) {
        boolean ret = false;
        int otusX = otus.getKoordinaatit().getX();
        int otusY = otus.getKoordinaatit().getY();
        if (abs(otusX - x) == 1 && otusY - y == 0) {
            ret = true;
        } else if (abs(otusY - y) == 1 && otusX - x == 0) {
            ret = true;
        }
        return ret;
    }

    public ArrayList<String> getTekstit() {
        return this.tekstit;
    }

    public void setTekstit(ArrayList<String> l) {
        this.tekstit = l;
    }

    public void setViholliset(ArrayList<String> l) {
        this.tekstit = l;
    }
}
