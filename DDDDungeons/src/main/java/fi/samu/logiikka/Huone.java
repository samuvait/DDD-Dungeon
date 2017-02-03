
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
    
    public Huone(int x, int y, int huoneenLeveys) {
        luoHuone(x, y, huoneenLeveys, huoneenLeveys);
    }
    
    public Huone(int x, int y, int huoneenLeveys, int huoneenPituus) {
        luoHuone(x, y, huoneenLeveys, huoneenPituus);
    }
    
    public void luoHuone(int x, int y, int huoneenLeveys, int huoneenPituus) {
        if (y < 0) {
            this.y = 0;
        } else {
            this.y = x;
        }
        if (x < 0) {
            this.x = 0;
        } else {
            this.x = y;
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
