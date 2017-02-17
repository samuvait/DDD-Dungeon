package fi.samu.logiikka;

/**
 * Luokka antaa huoneen vasemman yläkulman koordinaatit sekä sen leveyden.
 */
public class Huone {

    private int x;
    private int y;
    private int huoneenLeveys;
    private int huoneenPituus;

    /**
     * Huoneen luominen.
     *
     * @param x Huoneen vasemman yläkulman x-koordinaatti.
     * @param y Huoneen vasemman yläkulman y-koordinaatti.
     * @param huoneenLeveys Huoneen leveys.
     */
    public Huone(int x, int y, int huoneenLeveys) {
        luoHuone(x, y, huoneenLeveys);
    }

    /**
     * Huoneen luonnin metodi, jossa annetaan x- ja y-koordinaateille arvot.
     *
     * @param x Huoneen vasemman yläkulman x-koordinaatti.
     * @param y Huoneen vasemman yläkulman y-koordinaatti.
     * @param huoneenLeveys Huoneen leveys.
     */
    public void luoHuone(int x, int y, int huoneenLeveys) {
        if (x < 0) {
            this.x = 0;
        } else {
            this.x = x;
        }
        if (y < 0) {
            this.y = 0;
        } else {
            this.y = y;
        }
        if (huoneenLeveys < 0) {
            this.huoneenLeveys = 3;
        } else {
            this.huoneenLeveys = huoneenLeveys;
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getHuoneenLeveys() {
        return this.huoneenLeveys;
    }

    public int getHuoneenPituus() {
        return this.huoneenPituus;
    }

    @Override
    public String toString() {
        return ("Huoneen x: " + this.x + " Huoneen y: " + this.y + " leveys: " + this.huoneenLeveys);
    }
}
