package fi.samu.logiikka;

import java.util.ArrayList;
import java.util.Random;

/**
 * Luokka luo luolaan kerroksen, ja sijoittaa sinne huoneita ja käytäviä.
 */
public class LuolaGeneraattori {

    private int[][] kartta;
    private int koko;
    private ArrayList<Integer> huoneLista;
    private int tayttoAste;
    private int huoneenLeveysMinimi;
    private int huoneenLeveysMaksimi;
    private ArrayList<Huone> sijoitetutHuoneet;
    private ArrayList<Huone> sijoitetutHuoneetPoisto;
    private int huoneidenMaara = 0;
    private int kaytavienMaara = 0;
    private Huone nykyinenHuone;
    private Huone seuraavaHuone;
    private Koordinaatti alkuPiste;
    private Koordinaatti loppuPiste;

    /**
     * Luo luolan perusarvoilla.
     *
     */
    public LuolaGeneraattori() {
        this.koko = 25;
        this.huoneenLeveysMinimi = 3;
        this.huoneenLeveysMaksimi = 8;
    }

    /**
     * Luo luolan siten, että koko on annettu.
     *
     * @param koko Luolan koko ja kartan sivun pituus.
     */
    public LuolaGeneraattori(int koko) {
        if (koko < 10) {
            this.koko = 10;  // Kartan minimikoko tällä hetkellä 25
        } else {
            this.koko = koko;  // Kartan sivun pituus, vaatii kokeilua oikean löytämiseksi
        }
        this.huoneenLeveysMinimi = 3;
        this.huoneenLeveysMaksimi = 8;
    }

    /**
     * Alustaa luolan siten, että koko ja huoneen leveyksien minimi ja maksimi
     * on annettu.
     *
     * @param koko Luolan sivun koko ja kartan sivun pituus.
     * @param huoneenLeveysMinimi Pienin sallittu huoneen leveys.
     * @param huoneenLeveysMaksimi Suurin sallittu huoneen leveys.
     */
    public LuolaGeneraattori(int koko, int huoneenLeveysMinimi, int huoneenLeveysMaksimi) {
        if (koko < 10) {
            this.koko = 10;  // Kartan minimikoko tällä hetkellä 25
        } else {
            this.koko = koko;  // Kartan sivun pituus, vaatii kokeilua oikean löytämiseksi
        }
        if (huoneenLeveysMinimi < 3 || huoneenLeveysMinimi > 8) {
            this.huoneenLeveysMinimi = 3;  // huoneen minimikoko tällä hetkellä 3
        } else {
            this.huoneenLeveysMinimi = huoneenLeveysMinimi;
        }
        if (huoneenLeveysMaksimi > 8 || huoneenLeveysMaksimi < 3) {
            this.huoneenLeveysMaksimi = 8;  // huoneen maksimikoko tällä hetkellä 8
        } else {
            this.huoneenLeveysMaksimi = huoneenLeveysMaksimi;
        }
    }

    /**
     * Metodi luo luolan kerroksen, kaikki huoneet ja käytävät.
     */
    public void alustaLuola() {
        this.tayttoAste = (this.koko * this.koko) / 3; //Täyttöasteen määritys, vaikuttaa siihen paljonko kartan pinta-alasta täytetään huoneilla.
        alustaKartta(this.koko);
        luoHuoneet();
        sijoitaHuoneet();
        luoKaytavat();
        sijoitaAlkuJaLoppu();
    }

    /**
     * Tulostaa kartan.
     */
    public void tulostaKartta() {
        boolean rivinvaihto = false;
        for (int i = 0; i < koko; i++) {
            if (rivinvaihto) {
                System.out.println("");
            }
            for (int j = 0; j < koko; j++) {
                //if (i == 2 && j == 3)
                System.out.print(kartta[i][j]);
            }
            rivinvaihto = true;
        }
        System.out.println();
        System.out.println();
    }

    /**
     * Nollaa kaikki alkiot kartan sisällä.
     *
     * @param koko sivun pituus kartalla
     */
    public void alustaKartta(int koko) { // Nollaa kartan kaikki alkiot
        kartta = new int[koko][koko];
        for (int i = 0; i < koko; i++) {
            for (int j = 0; j < koko; j++) {
                kartta[i][j] = 0; // Alustetaan kaikki kartan alkiot siten että niihin ei pysty liikkumaan eli 0
            }
        }
    }

    /**
     * Luo listan huoneista perustuen täyttöasteeseen joka on määritelty
     * aiemmin.
     */
    public void luoHuoneet() { // Luo listan huoneista, jotka sijoitetaan karttaan.
        Random rng = new Random();
        huoneLista = new ArrayList();
        int hlmin = huoneenLeveysMinimi;
        int hlmax = huoneenLeveysMaksimi;
        int kaytettyPintaAla = 0;
        while (kaytettyPintaAla < tayttoAste) {
            int huoneenLeveys = hlmin + rng.nextInt(1 + hlmax - hlmin); // Huoneiden koon määritys tässä 3-8 leveys ja korkeus.
            huoneLista.add(huoneenLeveys);
            kaytettyPintaAla += huoneenLeveys * huoneenLeveys;
        }
    }

    /**
     * Pyrkii sijoittamaan aiemmin luodut huoneet kartalle, kokeillen jokaista
     * huonetta 100 kertaa.
     */
    public void sijoitaHuoneet() { // Huoneiden sijoittamisen algoritmi
        ArrayList<Integer> tempHuoneLista = new ArrayList(huoneLista);
        this.sijoitetutHuoneet = new ArrayList();
        Random rng = new Random();
        while (!tempHuoneLista.isEmpty()) {
            int sijoitettavaHuone = tempHuoneLista.remove(tempHuoneLista.size() - 1);
            for (int i = 0; i < 100; i++) { // i kertoo montako kertaa huonetta yritetään sijoittaa tyhjään tilaan.
                int x = 1 + rng.nextInt(koko - sijoitettavaHuone - 1); // ei sijoiteta reunoille, jotta käytävä voi alkaa miltä tahansa sivulta
                int y = 1 + rng.nextInt(koko - sijoitettavaHuone - 1);
                if (kartta[x][y] == 0) {
                    if (tarkistaOnkoAvoin(x, y, sijoitettavaHuone)) { // tarkistaa onko huoneen paikka pelkästään 0
                        taytaHuone(x, y, sijoitettavaHuone);
                        this.huoneidenMaara++;
                        Huone uusiHuone = new Huone(x, y, sijoitettavaHuone);
                        this.sijoitetutHuoneet.add(uusiHuone);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Tarkistaa voidaanko tähän tilaan sijoittaa haluttu huone eli mahtuuko se
     * siihen, ilman että se menee minkään muun huoneen vierelle.
     *
     * @param x huoneen vasemman yläkulman x koordinaatti
     * @param y huoneen vasemman yläkulman y koordinaatti
     * @param sijoitettavaHuone huoneen sivun leveys.
     * @return Jos voidaan sijoittaa, palauttaa tosi.
     */
    public boolean tarkistaOnkoAvoin(int x, int y, int sijoitettavaHuone) { // Tarkistaa, että huone mahtuu kokonaisuudessaan tilaan
        int viimeinenX = sijoitettavaHuone + x + 1; // viimeisen tarkistettavan täytyy mennä yli, jotta huoneet eivät ole vierekkäin
        int aloitusX = x - 1; //viimeisen aloitettavan täytyy alkaa aiemmin samasta syystä
        if (aloitusX < 0) {
            aloitusX = 0;
        }
        int viimeinenY = sijoitettavaHuone + y + 1;
        int aloitusY = y - 1;
        if (aloitusY < 0) {
            aloitusY = 0;
        }
        for (int tarkistettavaY = aloitusY; tarkistettavaY <= viimeinenY && tarkistettavaY < koko; tarkistettavaY++) { // varmista että on taulukon rajoissa
            for (int tarkistettavaX = aloitusX; tarkistettavaX <= viimeinenX && tarkistettavaX < koko; tarkistettavaX++) {
                if (kartta[tarkistettavaY][tarkistettavaX] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Täyttää huoneen ykkösillä kartalla.
     *
     * @param x vasen yläkulma x
     * @param y vasen yläkulma y
     * @param sijoitettavaHuone sivun pituus
     */
    public void taytaHuone(int x, int y, int sijoitettavaHuone) { //Vaihtaa huoneen paikat kartalla 0:sta 1:ksi
        for (int muutettavaY = y; muutettavaY < sijoitettavaHuone + y && muutettavaY < koko; muutettavaY++) {
            for (int muutettavaX = x; muutettavaX < sijoitettavaHuone + x && muutettavaX < koko; muutettavaX++) {
                kartta[muutettavaY][muutettavaX] = 1;
            }
        }
    }

    /**
     * Käy läpi sijoitettujen käytävien listaa ja sijoittaa käytävän nykyisen ja
     * seuraavan huoneen välille päivittäen niiden arvoja.
     */
    public void luoKaytavat() { // Luodaan käytävät huoneiden välillä jotta niissä pystyy liikkumaan
        sijoitetutHuoneetPoisto = new ArrayList(sijoitetutHuoneet);
        seuraavaHuone = new Huone(0, 0, 0);
        if (!sijoitetutHuoneetPoisto.isEmpty()) {
            nykyinenHuone = sijoitetutHuoneetPoisto.remove(sijoitetutHuoneetPoisto.size() - 1); // Otetaan viimeisin huone
//            System.out.println("sijoitetut huoneet poisto size: " + sijoitetutHuoneetPoisto.size());
            while (!sijoitetutHuoneetPoisto.isEmpty()) { // aloitetaan käymään läpi
                if (seuraavaHuone.getHuoneenLeveys() != 0) { // jos ei vielä määritetty seuraavaa huonetta, eli ensimmäinen kerta !!!AIHEUTTI VIAN JOSSA UUDEN KERROKSEN LUONTI EI TOIMI
                    nykyinenHuone = seuraavaHuone; // muutetaan huone johon luotiin käytävä huoneeksi, josta aloitetaan luomaan käytävä
                }
                seuraavaHuone = sijoitetutHuoneetPoisto.remove(sijoitetutHuoneetPoisto.size() - 1); // otetaan huone, johon ensimmäisestä luodaan käytävä
//                System.out.println("sijoitetut huoneet poisto size: " + sijoitetutHuoneetPoisto.size());
                arvoAloitusPaikat();
            }
        }
    }

    /**
     * Arpoo neljästä vaihtoehdosta käytävän aloituspaikan. 0 = huoneen yläosa 1
     * = vasen sivu 2 = oikea sivu 3 = alaosa.
     */
    public void arvoAloitusPaikat() { // Arpoo käytävän aloituspaikat
        Random rng = new Random();
        int ehto = rng.nextInt(4); // luodaan 4 vaihtoehtoa 0 = huoneen yläosa 1 = vasen sivu 2 = oikea sivu 3 = alaosa
        kayVaihtoehdotLapi(ehto);
    }

    /**
     * Arvotun vaihtoehdon perusteella vaihtaa aloituspaikan koordinaatteja
     * niin, että ne ovat oikealla sivustalla ja lähtevät liikkumaan oikeaan
     * suuntaan.
     *
     * @param ehto Huoneen sivu, jolta käytävä aloitetaan 0 = huoneen yläosa 1 =
     * vasen sivu 2 = oikea sivu 3 = alaosa.
     */
    public void kayVaihtoehdotLapi(int ehto) {
        Random rng = new Random();
        Huone huone = nykyinenHuone;
        int huoneenLeveys = huone.getHuoneenLeveys();
        int muutettavaX = huone.getX() - 1;
        int muutettavaY = huone.getY() - 1;
        int aloitusSuunta;
        if (ehto == 1 || ehto == 2) { // Vasen tai oikea sivu
            aloitusSuunta = 0; // aloitetaan siirtämällä y
            muutettavaY += 1 + rng.nextInt(huoneenLeveys);
        } else { // ylös tai alas
            aloitusSuunta = 1; // aloitetaan siirtämällä x
            muutettavaX += 1 + rng.nextInt(huoneenLeveys);
        }
        if (ehto == 2) {
            muutettavaX += huoneenLeveys + 1;
        } else if (ehto == 3) {
            muutettavaY += huoneenLeveys + 1;
        }
        teeKaytava(muutettavaX, muutettavaY, aloitusSuunta);
    }

    /**
     * Luo käytävän kahden pisteen välille, muuttamalla ensin x tai y
     * koordinaatit oikeaan kohtaan ja sen jälkeen jäljelle jäävän suunnan.
     *
     * @param x Käytävän aloituspaikan x-koordinaatti.
     * @param y Käytävän aloituspaikan y-koordinaatti.
     * @param aloitusSuunta Suunta, johon käytävää ensin aletaan luomaan, 0 = y
     * ja 1 = x;
     */
    public void teeKaytava(int x, int y, int aloitusSuunta) { // Tehdaan kaytava kahden pisteen valille
        int muutettavaX = x;
        int muutettavaY = y;
        int aloitus = aloitusSuunta;
        kartta[muutettavaY][muutettavaX] = 1;
        Random rng = new Random();
        Huone maaranpaa = this.seuraavaHuone; // otetaan maaranpaaksi seuraava huone
//        System.out.println("Aloitus x: " + muutettavaX + " Aloitus y: " + muutettavaY);
        int paaX = maaranpaa.getX() + rng.nextInt(maaranpaa.getHuoneenLeveys()); // arvotaan jokin x-koordinaatti johon käytävä liitetään
        int paaY = maaranpaa.getY() + rng.nextInt(maaranpaa.getHuoneenLeveys()); // arvotaan jokin y-koordinaatti johon käytävä liitetään
//        System.out.println("Lopetus x: " + paaX + " Lopetus y: " + paaY);
        while (true) {
            if (paaX == muutettavaX && paaY == muutettavaY) {
                this.kaytavienMaara++;
                break;
            }
            if (aloitus == 0) {
                aloitus = 1;
                if (muutettavaY < paaY) { // katsotaan onko lähtö y pienempi vai suurempi, ja liikutaan sen mukaan
                    while (muutettavaY != paaY) { // jos y pienempi kasvatetaan y kunnes saman arvoinen
                        muutettavaY++;
                        kartta[muutettavaY][muutettavaX] = 1;
                    }
                } else {
                    while (muutettavaY != paaY) { //jos y suurempi vähennetään y kunnes saman arvoinen
                        muutettavaY--;
                        kartta[muutettavaY][muutettavaX] = 1;
                    }
                }
            } else {
                aloitus = 0;
                if (muutettavaX < paaX) { // sama x:n arvoille y:n jälkeen
                    while (muutettavaX != paaX) {
                        muutettavaX++;
                        kartta[muutettavaY][muutettavaX] = 1;
                    }
                } else {
                    while (muutettavaX != paaX) {
                        muutettavaX--;
                        kartta[muutettavaY][muutettavaX] = 1;
                    }
                }
            }
        }
//        System.out.println("Paatetty x: " + muutettavaX + " Paatetty y: " + muutettavaY);
    }

    /**
     * Sijoittaa paikat, joista pelaaja aloittaa eli alkuPiste ja portaat alas
     * eli loppuPiste.
     */
    public void sijoitaAlkuJaLoppu() { //sijoittaa portaat, josta pelaaja tuli luolan tähän kerrokseen
        Random rng = new Random();
        Huone aloitusHuone = this.sijoitetutHuoneet.get(0);
        Huone lopetusHuone = this.sijoitetutHuoneet.get(sijoitetutHuoneet.size() - 1);
        int aloitusX = aloitusHuone.getX() + rng.nextInt(aloitusHuone.getHuoneenLeveys());
        int aloitusY = aloitusHuone.getY() + rng.nextInt(aloitusHuone.getHuoneenLeveys());
        this.alkuPiste = new Koordinaatti(aloitusX, aloitusY);
        int lopetusX = lopetusHuone.getX() + rng.nextInt(lopetusHuone.getHuoneenLeveys());
        int lopetusY = lopetusHuone.getY() + rng.nextInt(lopetusHuone.getHuoneenLeveys());
        while (lopetusX == aloitusX && lopetusY == aloitusY) {
            lopetusX = lopetusHuone.getX() + rng.nextInt(lopetusHuone.getHuoneenLeveys());
            lopetusY = lopetusHuone.getY() + rng.nextInt(lopetusHuone.getHuoneenLeveys());
        }
        this.loppuPiste = new Koordinaatti(lopetusX, lopetusY);
    }

    public Koordinaatti getAlku() {
        return this.alkuPiste;
    }

    public Koordinaatti getLoppu() {
        return this.loppuPiste;
    }

    public int getKoko() {
        return this.koko;
    }

    public int getHuoneidenMaara() {
        return this.huoneidenMaara;
    }

    public int getKaytavienMaara() {
        return this.kaytavienMaara;
    }

    public int[][] getKartta() {
        return this.kartta;
    }

    public ArrayList<Integer> getHuoneLista() {
        return this.huoneLista;
    }

    public ArrayList<Huone> getSijoitetutHuoneet() {
        return this.sijoitetutHuoneet;
    }
}
