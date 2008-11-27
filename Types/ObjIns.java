/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Types;

import Util.Logic.UHPT;

public class ObjIns {
    public Obj parent;
    public int objNumber;
    public Vector origin;
    public Vector rotation;
    public Vector conMove;
    public Vector conRot;
    public float boundSph;
    public int objInsNumber;
    
    public ObjIns(Obj parent, Vector origin, Vector rotation, int objInsNumber, int number) {
        this.parent = parent;
        this.boundSph = this.parent.bR;
        this.rotation = rotation;
        this.conMove = new Vector();
        this.conRot = new Vector();
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
    
    public void conMove() {
        this.origin.x += this.conMove.x * UHPT.getETime() / 1000000000;
        this.origin.y += this.conMove.y * UHPT.getETime() / 1000000000;
        this.origin.z += this.conMove.z * UHPT.getETime() / 1000000000;
    }

    public void conRotate() {
        this.rotation.x += this.conRot.x * UHPT.getETime() / 1000000000 % 360;
        this.rotation.y += this.conRot.y * UHPT.getETime() / 1000000000 % 360;
        this.rotation.z += this.conRot.z * UHPT.getETime() / 1000000000 % 360;
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
    
    public void setConRot(Vector newRot) {
        this.conRot = newRot;
    }
    
    @Override
    public String toString() {
        return new String("oi " + this.objInsNumber + " "
                          + this.origin.x + " " + this.origin.y + " " + this.origin.z + " "
                          + this.rotation.x + " " + this.rotation.y + " " + this.rotation.z);
    } 
}
