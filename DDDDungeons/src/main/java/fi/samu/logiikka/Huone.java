package fi.samu.logiikka;

/**
 * Luokka antaa huoneen vasemman yläkulman koordinaatit sekä sen leveyden
 */
public class Huone {

    private int x;
    private int y;
    private int huoneenLeveys;
    private int huoneenPituus;

    public Huone(int x, int y, int huoneenLeveys) {
        luoHuone(x, y, huoneenLeveys);
    }

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
}
