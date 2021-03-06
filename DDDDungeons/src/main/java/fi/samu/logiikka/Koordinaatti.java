package fi.samu.logiikka;

/**
 * Luokassa säilytetään x- ja y-koordinaatteja.
 */
public class Koordinaatti {

    private int x;
    private int y;

    /**
     * Luokan luonnin metodi.
     *
     * @param x x-koordinaatti.
     * @param y y-koordinaatti.
     */
    public Koordinaatti(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return ("x: " + this.x + " y: " + this.y);
    }
}
