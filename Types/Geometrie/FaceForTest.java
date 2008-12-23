/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Types.Geometrie;

public class FaceForTest {
    public Vector v1, v2, v3;
    public Vector middle;
    public float radius;
    public FaceForTest(Face face){
        this.v1 = face.v1;
        this.v2 = face.v2;
        this.v3 = face.v3;

        this.middle = new Vector();
        middle.x += v1.x / 3;
        middle.x += v2.x / 3;
        middle.x += v3.x / 3;

        middle.y += v1.y / 3;
        middle.y += v2.y / 3;
        middle.y += v3.y / 3;

        middle.z += v1.z / 3;
        middle.z += v2.z / 3;
        middle.z += v3.z / 3;

        this.radius = 0f;
        for(int i = 0; i < 3;i++) {
            float dis = (float)Math.sqrt(Math.pow(middle.x, 2) + Math.pow(middle.y, 2) + Math.pow(middle.z, 2));
            if(dis > this.radius)
                this.radius = dis;
        }
    }
}
