package fi.samu.mekaniikat;

import fi.samu.logiikka.Koordinaatti;
import fi.samu.logiikka.Huone;
import static java.lang.Math.abs;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.Iterator;
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

    public void sijoitaViholliset() {
        if (viholliset != null) {
            for (Otus otus : viholliset) {
                int x = otus.getKoordinaatit().getX();
                int y = otus.getKoordinaatit().getY();
                kartta[y][x] = 6;
            }
        }
    }

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

    public boolean tarkistaOtukset(int x, int y) {
        boolean ret = false;
        if (viholliset != null) {
            for (Otus otus : viholliset) {
                if (otus.getKoordinaatit().getX() == x && otus.getKoordinaatit().getY() == y) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

    public void taistele(Pelaaja pelaaja) {
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
                    pelaaja.growExperience(20);
                    otus.setTaisteleeko(0);
                    System.out.println("You have " + hp + " hp left");
                }
            }
        }
    }

    public void tarkistaKuoliko() {
        for (Iterator<Otus> iterator = this.viholliset.iterator(); iterator.hasNext();) {
            Otus otus = iterator.next();
            if (otus.getHitPoints() < 1) {
                iterator.remove();
                kartta[otus.getKoordinaatit().getY()][otus.getKoordinaatit().getX()] = 1;
                System.out.println("You have defeated a " + otus.getKuvaus() + ".");
            }
        }
    }

    public void liikuta(Pelaaja pelaaja) {
        ArrayList<Otus> liikutettavat = new ArrayList();
        for (Otus otus : viholliset) {
            if (otus.getLiikkuuko() == 0) {
                otus.setLiikkuuko(1);
            } else {
                liikutettavat.add(otus);
            }
        }
        for (Iterator<Otus> iterator = liikutettavat.iterator(); iterator.hasNext();) {
            Otus otus = iterator.next();
            System.out.println(otus.getKuvaus() + " moved");
            liiku(otus, pelaaja);
        }
    }

    public void liiku(Otus otus, Pelaaja pelaaja) { // 0 = ylos 1 = vasen 2 = oikea 3 = alas
        Koordinaatti otusKoor = otus.getKoordinaatit();
        int x = otusKoor.getX();
        int y = otusKoor.getY();
        int pelX = pelaaja.getKoordinaatit().getX();
        int pelY = pelaaja.getKoordinaatit().getY();
        Random rng = new Random();
        boolean kokeiltuYlos = false;
        boolean kokeiltuVasen = false;
        boolean kokeiltuOikea = false;
        boolean kokeiltuAlas = false;
        ArrayList<Integer> vaihtoEhdot = new ArrayList(asList(0, 1, 2, 3));
        kartta[y][x] = 1; // tekstikäyttöliittymän line
        if (abs(pelX - x) < 2 && abs(pelY - y) < 2) {
            if (otus.voiTaistella(pelX, pelY)) {
                otus.setTaisteleeko(1);
                this.taistele(pelaaja);
            }
        } else {
            while (true) {
                if (vaihtoEhdot.isEmpty()) {
                    break;
                } else {
                    int suunta = vaihtoEhdot.get(rng.nextInt(vaihtoEhdot.size()));
                    if (suunta == 0 && y > 0) {
                        if (kartta[y - 1][x] == 1) {
                            y--;
                            if (tarkistaOtukset(x, y)) {
                                y++;
                            } else {
                                break; 
                            }
                        }
                    } else if (suunta == 1 && x > 0) {
                        if (kartta[y][x - 1] == 1) {
                            x--;
                            if (tarkistaOtukset(x, y)) {
                                x++;
                            } else {
                                break; 
                            }
                        }
                    } else if (suunta == 2 && x < koko - 1) {
                        if (kartta[y][x + 1] == 1) {
                            x++;
                            if (tarkistaOtukset(x, y)) {
                                x--;
                            } else {
                                break; 
                            }
                        }
                    } else if (suunta == 3 && y < koko - 1) {
                        if (kartta[y + 1][x] == 1) {
                            y++;
                            if (tarkistaOtukset(x, y)) {
                                y--;
                            } else {
                                break;
                            }
                        }
                    }
                    poistaSuunta(vaihtoEhdot, suunta);
                }
            }
        }
        if (otus.getHitPoints() < 1) {
            tarkistaKuoliko();
        } else {
            kartta[y][x] = 6; // tekstikäyttöliittymän line
            otus.setLiikkuuko(0);
            otus.setKoordinaatit(new Koordinaatti(x, y));
        }
    }

    public void poistaSuunta(ArrayList<Integer> vaihtoEhdot, int suunta) {
        for (Iterator<Integer> iterator = vaihtoEhdot.iterator(); iterator.hasNext();) {
            int i = iterator.next();
            if (i == suunta) {
                iterator.remove();
            }
        }
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
