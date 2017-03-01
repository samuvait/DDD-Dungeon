package fi.samu.mekaniikat;

import fi.samu.logiikka.Koordinaatti;
import fi.samu.logiikka.Huone;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.Iterator;
import java.util.Random;

/**
 * Luokka luo ja sijoittaa hirviöt joita vastaan pelaaja taistelee, sekä
 * tarkistaa osuuko pelaaja niihin ja hoitaa niiden liikkeen.
 */
public class Viholliset {

    private int kerros;
    private int koko;
    private ArrayList<Otus> viholliset;
    private ArrayList<Huone> sijoitetutHuoneet;
    private int[][] kartta;
    public int varmistus;
    public int lkm;
    private Taisteleminen taisto;
    private final int otustenMaara = 14;

    /**
     * Luo vihollisten esiintymät kerrokseen.
     *
     * @param koko Luolan koko.
     * @param kerros Luolan kerros, mikä määrittää vihollisten vaikeuden.
     * @param sijoitetutHuoneet Huoneet jotka on sijoitettu, ja joihin
     * sijoitetaan otuksia.
     * @param kartta Kartta, johon sijoitetaan otuksia.
     */
    public Viholliset(int koko, int kerros, ArrayList<Huone> sijoitetutHuoneet, int[][] kartta) {
        this.kartta = kartta;
        this.kerros = kerros;
        this.sijoitetutHuoneet = new ArrayList(sijoitetutHuoneet);
        this.koko = koko;
    }

    /**
     * Luo tietyn kerroksen viholliset. Mitä suurempi kerros, sitä voimakkaampia
     * vihollisia voi syntyä.
     */
    public void luoViholliset() {
        viholliset = new ArrayList();
        Random rng = new Random();
        this.sijoitetutHuoneet.remove(0);
        varmistus = sijoitetutHuoneet.size();
        lkm = 0;
        int tyyppiMax = 1 + kerros / 2;
        if (tyyppiMax > otustenMaara) {
            tyyppiMax = otustenMaara;
        }
        for (int i = 0; i < sijoitetutHuoneet.size(); i++) {
            Huone otuksenHuone = sijoitetutHuoneet.get(i);
            int tyyppi = rng.nextInt(tyyppiMax);
            int otusX = otuksenHuone.getX() + rng.nextInt(otuksenHuone.getHuoneenLeveys());
            int otusY = otuksenHuone.getY() + rng.nextInt(otuksenHuone.getHuoneenLeveys());
            if (kartta[otusY][otusX] == 1) {
                Otus lisattava = new Otus(otusX, otusY, tyyppi);
                viholliset.add(lisattava);
                kartta[otusY][otusX] = 6;
                lkm++;
            }
        }
    }

    /**
     * Palauttaa otuksen, joka on kohdassa x ja y.
     *
     * @param x Otuksen x-koordinaatti.
     * @param y Otuksen y-koordinaatti.
     * @return Palautettava otus.
     */
    public Otus palautaPiirrettava(int x, int y) {
        Otus uusi = new Otus(0, 0, 1);
        for (Otus otus : viholliset) {
            if (otus.getKoordinaatit().getX() == x && otus.getKoordinaatit().getY() == y) {
                uusi = otus;
            }
        }
        return uusi;
    }

    /**
     * Tarkistaa onko paikassa, johon Otus haluaa liikkua jo toinen otus.
     *
     * @param x Liikuttava paikan x-koordinaatti.
     * @param y Liikuttavan paikan y-koordinaatti.
     * @return Palauttaa true, jos toinen otus on ja ei voida liikkua.
     */
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

    /**
     * Liikuttaa kaikkia otuksia, joiden vuoro on liikkua.
     *
     * @param pelaaja Pelaaja, jotta otukset tietävät voivatko ne liikkua
     * siihen.
     */
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
            this.liiku(otus, pelaaja);
        }
    }

    /**
     * Liikuttaa otusta pelaajan liikkumisen jälkeen.
     *
     * @param otus Otus jota liikutetaan.
     * @param pelaaja Pelaaja, jotta otus tietää osuuko se pelaajaan.
     */
    public void liiku(Otus otus, Pelaaja pelaaja) { // 0 = ylos 1 = vasen 2 = oikea 3 = alas
        int x = otus.getKoordinaatit().getX();
        int y = otus.getKoordinaatit().getY();
        Random rng = new Random();
        ArrayList<Integer> vaihtoEhdot = new ArrayList(asList(0, 1, 2, 3));
        kartta[y][x] = 1; // tekstikäyttöliittymän line
        boolean taisteli = taisto.voikoTaistella(otus, pelaaja);
        if (!taisteli) {
            while (true) {
                int suunta = vaihtoEhdot.get(rng.nextInt(vaihtoEhdot.size()));
                if (suunta == 0 && y > 0 && kartta[y - 1][x] == 1 && !tarkistaOtukset(x, y - 1)) {
                    y--;
                    break;
                } else if (suunta == 1 && x > 0 && kartta[y][x - 1] == 1 && !tarkistaOtukset(x - 1, y)) {
                    x--;
                    break;
                } else if (suunta == 2 && x < koko - 1 && kartta[y][x + 1] == 1 && !tarkistaOtukset(x + 1, y)) {
                    x++;
                    break;
                } else if (suunta == 3 && y < koko - 1 && kartta[y + 1][x] == 1 && !tarkistaOtukset(x, y + 1)) {
                    y++;
                    break;
                }
                poistaSuunta(vaihtoEhdot, suunta);
                if (vaihtoEhdot.isEmpty()) {
                    break;
                }
            }
        }
        if (otus.getHitPoints() < 1) {
            taisto.tarkistaKuoliko();
        } else {
            kartta[y][x] = 6; // tekstikäyttöliittymän line
            otus.setLiikkuuko(0);
            otus.setKoordinaatit(new Koordinaatti(x, y));
        }
    }

    /**
     * Poistaa annetun suunnan vaihtoehdoista, jotta voidaan valita oikea
     * suunta.
     *
     * @param vaihtoEhdot Vaihtoehdot, joihin otus voi liikkua.
     * @param suunta Poistettava vaihtoehto.
     */
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

    public void setTaistelu(Taisteleminen taistelu) {
        this.taisto = taistelu;
    }
}
