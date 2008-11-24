/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Types;

import Util.UHPT;

public class ObjIns {
    public Obj parent;
    public int objNumber;
    public Vector origin;
    public Vector rotation;
    public float boundSph;
    public int objInsNumber;
    
    public ObjIns(Obj parent, Vector origin, Vector rotation, int objInsNumber, int number) {
        this.parent = parent;
        this.boundSph = this.parent.bR;
        this.rotation = rotation;
        place(origin);
        this.objNumber = number;
        this.objInsNumber = objInsNumber -1;        
    }
    
    //Place the instance at the right place according to the center of the ParentObj
    private void place(Vector origin) {
        this.origin = new Vector(this.parent.origin.x + origin.x,
                                 this.parent.origin.y + origin.y,
                                 this.parent.origin.z + origin.z);
    }
    
    public void conMove(float x, float y, float z) {
        this.origin.x += x * UHPT.getETime() / 1000000000;
        this.origin.y += y * UHPT.getETime() / 1000000000;
        this.origin.z += z * UHPT.getETime() / 1000000000;
    }

    public void conRotate(float rX, float rY, float rZ) {
        this.rotation.x += rX * UHPT.getETime() / 1000000000;
        this.rotation.y += rY * UHPT.getETime() / 1000000000;
        this.rotation.z += rZ * UHPT.getETime() / 1000000000;
    }

    public void setPos(float x, float y, float z) {
        this.origin.x = x;
        this.origin.y = y;
        this.origin.z = z;
    }

    public void setRot(float xR, float yR, float zR) {
        this.rotation.x = xR;
        this.rotation.y = yR;
        this.rotation.z = zR;
    }
}
