
package fi.samu.logiikka;

/**
 *
 * @author Samu
 */
public class Huone {
    
    private int x;
    private int y;
    private int huoneenLeveys;
    private int huoneenPituus;
    
    public Huone(int y, int x, int huoneenLeveys) {
        luoHuone(y, x, huoneenLeveys, huoneenLeveys);
    }
    
    public Huone(int y, int x, int huoneenLeveys, int huoneenPituus) {
        luoHuone(y, x, huoneenLeveys, huoneenPituus);
    }
    
    public void luoHuone(int y, int x, int huoneenLeveys, int huoneenPituus) {
        if (y < 0) {
            this.y = 0;
        } else {
            this.y = y;
        }
        if (x < 0) {
            this.x = 0;
        } else {
            this.x = x;
        }
        if (huoneenLeveys < 0) {
            this.huoneenLeveys = 3;
        } else {
            this.huoneenLeveys = huoneenLeveys;
        }
        if (huoneenPituus < 0) {
            this.huoneenPituus = 3;
        } else {
            this.huoneenPituus = huoneenPituus;
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
