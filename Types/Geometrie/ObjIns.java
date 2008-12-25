/*
 *EERT = EERT enhanced rendering technology
 *
 *Copyright (C) [2008] [Robert "BuRnEr" Schadek]

 *This program is free software; you can redistribute it and/or modify it under
 *the terms of the GNU General Public License as published by the Free Software
 *Foundation; either version 3 of the License,
 *or (at your option) any later version.

 *This program is distributed in the hope that it will be useful, but WITHOUT
 *ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 *You should have received a copy of the GNU General Public License along with
 *this program; if not, see <http://www.gnu.org/licenses/>.
 */
package Types.Geometrie;

import Engine.Engine;
import Util.Logic.UHPT;

public class ObjIns {

    public Obj parent;
    public int objNumber;
    public Vector origin;
    public Vector[] places;
    public Vector rotation;
    public Vector[] conMove;
    public Vector conRot;
    public float boundSph;
    public int objInsNumber;
    public Engine engine;

    public ObjIns(Obj parent, Vector origin, Vector rotation, int objInsNumber, int number) {
        this.engine = parent.engine;
        this.parent = parent;
        this.boundSph = this.parent.bR;
        this.rotation = rotation;
        this.conMove = null;
        this.conRot = new Vector();
        place(origin);
        this.objNumber = number;
        this.objInsNumber = objInsNumber - 1;
    }

    public ObjIns(Obj parent, Vector origin, Vector rotation, Vector conRot, int objInsNumber, int number) {
        this.parent = parent;
        this.boundSph = this.parent.bR;
        this.rotation = rotation;
        this.conMove = null;
        this.conRot = conRot;
        place(origin);
        this.objNumber = number;
        this.objInsNumber = objInsNumber - 1;
    }

    public ObjIns(Vector origin, Vector rotation, Vector conRot, int objInsNumber, int number) {
        this.boundSph = this.parent.bR;
        this.rotation = rotation;
        this.conMove = null;
        this.conRot = conRot;
        place(origin);
        this.objNumber = number;
        this.objInsNumber = objInsNumber - 1;
    }

    //Place the instance at the right place according to the center of the ParentObj
    private void place(Vector origin) {
        this.origin = new Vector(this.parent.origin.x + origin.x,
                this.parent.origin.y + origin.y,
                this.parent.origin.z + origin.z);

    }

    public void conMove(int number) {
        if (this.conMove != null) {
            if (number <= this.conMove.length) {
                this.origin.x += this.conMove[number].x * UHPT.getETime() / 1000000000;
                this.origin.y += this.conMove[number].y * UHPT.getETime() / 1000000000;
                this.origin.z += this.conMove[number].z * UHPT.getETime() / 1000000000;
            }
        }
    }

    public void conRotate() {
        this.rotation.x += this.conRot.x * UHPT.getETime() / 1000000000 % 360;
        this.rotation.y += this.conRot.y * UHPT.getETime() / 1000000000 % 360;
        this.rotation.z += this.conRot.z * UHPT.getETime() / 1000000000 % 360;
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
        StringBuffer retString = new StringBuffer("oi " + this.objInsNumber + " " + this.rotation.x + " " + this.rotation.y + " " + this.rotation.z + " ");
        retString.append(this.origin.x + " " + this.origin.y + " " + this.origin.z + " ");
        for (int i = 0; i < this.conMove.length; i++) {
            retString.append(this.conMove[i].x + " " + this.conMove[i].y + " " + this.conMove[i].z + " ");
        }
        return retString.toString();
    }
}
