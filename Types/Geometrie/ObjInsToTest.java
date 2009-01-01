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
import java.util.LinkedList;

public class ObjInsToTest {

    public Obj parent;
    public int objNumber;
    public Vector origin;
    public LinkedList<Vector> moveTo;
    public Vector rotation;
    public Vector conRot;
    public float boundSph;
    public int objInsNumber;
    public Engine engine;
    private Vector conMove;

    public ObjInsToTest(Obj parent, Vector origin, Vector rotation, int objInsNumber, int number) {
        this.engine = parent.engine;
        this.parent = parent;
        this.boundSph = this.parent.bR;
        this.rotation = rotation;
        this.conMove = null;
        this.conRot = new Vector();
        place(origin);
        this.objNumber = number;
        this.objInsNumber = objInsNumber - 1;
        this.moveTo = new LinkedList<Vector>();
    }

    public ObjInsToTest(Vector origin, Vector rotation, Vector conRot, int objInsNumber, int number) {
        //this.boundSph = this.parent.bR;
        this.rotation = rotation;
        this.conMove = null;
        this.conRot = conRot;
        place(origin);
        this.objNumber = number;
        this.objInsNumber = objInsNumber - 1;
        this.moveTo = new LinkedList<Vector>();
    }

    //Place the instance at the right place according to the center of the ParentObj
    private void place(Vector origin) {
        this.origin = new Vector(origin.x,
                origin.y,
                origin.z);

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
}
