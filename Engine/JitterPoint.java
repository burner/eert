/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

public class JitterPoint {

    private static final int MAX_SAMPLES = 66;
    float x, y;

    public JitterPoint(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
    }
}
