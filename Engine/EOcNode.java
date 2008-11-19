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
package Engine;

import Types.Obj;
import Types.ObjIns;
import Types.Vector;
import java.util.LinkedList;
import javax.media.opengl.GL;

public class EOcNode {

    private EOcMaster root;
    public EOcNode[] childs;
    public ObjIns[] objs;
    private boolean[] drawn;
    private float xSize,  ySize,  zSize;
    private Vector middle;
    private float radius;
    private Obj[] realObjs;

    public EOcNode(EOcMaster root, Obj[] realObjs, ObjIns[] objs, Vector middle, float xSize, float ySize, float zSize, boolean[] drawn, int depth) {
        this.realObjs = realObjs;
        this.root = root;
        this.middle = middle;

        this.drawn = drawn;
        this.xSize = xSize;
        this.ySize = ySize;
        this.zSize = zSize;

        float xSizeH = xSize / 2;
        float ySizeH = ySize / 2;
        float zSizeH = zSize / 2;

        this.radius = (float) Math.abs(Math.pow(xSizeH, 2) + Math.pow(ySizeH, 2) + Math.pow(zSizeH, 2));

        this.objs = checkAllObjects(objs);
        byte idx = 0;
        if (objs.length > 1 && depth < 5) {

            //important needs to be created within this contidional execution
            //otherwise the child test in draw does not work
            this.childs = new EOcNode[8];

            EOcNode ch1 = new EOcNode(this.root, this.realObjs, objs, new Vector(middle.x - xSize / 2, middle.y + ySize / 2, middle.z + zSize / 2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if (ch1.objs != null) {
                this.childs[idx] = ch1;
                ++idx;
            }

            EOcNode ch2 = new EOcNode(this.root, this.realObjs, objs, new Vector(middle.x + xSize / 2, middle.y + ySize / 2, middle.z + zSize / 2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if (ch2.objs != null) {
                this.childs[idx] = ch2;
                ++idx;
            }

            EOcNode ch3 = new EOcNode(this.root, this.realObjs, objs, new Vector(middle.x - xSize / 2, middle.y - ySize / 2, middle.z + zSize / 2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if (ch1.objs != null) {
                this.childs[idx] = ch3;
                ++idx;
            }

            EOcNode ch4 = new EOcNode(this.root, this.realObjs, objs, new Vector(middle.x + xSize / 2, middle.y - ySize / 2, middle.z + zSize / 2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if (ch2.objs != null) {
                this.childs[idx] = ch4;
                ++idx;
            }

            EOcNode ch5 = new EOcNode(this.root, this.realObjs, objs, new Vector(middle.x - xSize / 2, middle.y + ySize / 2, middle.z - zSize / 2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if (ch1.objs != null) {
                this.childs[idx] = ch5;
                ++idx;
            }

            EOcNode ch6 = new EOcNode(this.root, this.realObjs, objs, new Vector(middle.x + xSize / 2, middle.y + ySize / 2, middle.z - zSize / 2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if (ch2.objs != null) {
                this.childs[idx] = ch6;
                ++idx;
            }

            EOcNode ch7 = new EOcNode(this.root, this.realObjs, objs, new Vector(middle.x - xSize / 2, middle.y - ySize / 2, middle.z - zSize / 2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if (ch1.objs != null) {
                this.childs[idx] = ch7;
                ++idx;
            }

            EOcNode ch8 = new EOcNode(this.root, this.realObjs, objs, new Vector(middle.x + xSize / 2, middle.y - ySize / 2, middle.z - zSize / 2), xSizeH, ySizeH, zSizeH, this.drawn, depth++);
            if (ch2.objs != null) {
                this.childs[idx] = ch8;
            }
        }
    }

    public void draw(GL gl) {
        if (this.childs == null) {
            //frustum check
            for (int p = 0; p < 6; p++) {
                if (this.root.frustum[p][0] * this.middle.x + this.root.frustum[p][1] * this.middle.y + this.root.frustum[p][2] * this.middle.z + this.root.frustum[p][3] <= -radius) {
                    return;
                } else {
                    for (ObjIns obIns : this.objs) {
                        obIns.parent.render(obIns.number);
                    }
                }
            }
        } else {
            for (EOcNode child : this.childs) {
                child.draw(gl);
            }
        }
    }

    private ObjIns[] checkAllObjects(ObjIns[] objs) {
        LinkedList<ObjIns> objects = new LinkedList<ObjIns>();
        for (ObjIns obj : objs) {
            if (checkForObj(obj)) {
                objects.add(obj);
            }

        }
        return (ObjIns[]) objects.toArray();
    }

    boolean checkForObj(ObjIns obj) {
        //Cheated a bit 
        //Just check a sphear around the box against the sphear around the Obj
        //the error-margin shouldn't be to big, but the check is much cheaper
        //and easier to understand

        float boxSph = (float) Math.sqrt(Math.pow(xSize / 2, 2) + Math.pow(ySize / 2, 2) + Math.pow(zSize / 2, 2));
        //Euclidean distance
        float dis = (float) Math.abs(Math.sqrt(Math.pow(this.middle.x - obj.origin.x, 2) + Math.pow(this.middle.y - obj.origin.y, 2) + Math.pow(this.middle.z - obj.origin.y, 2)));

        if (dis - boxSph - obj.boundSph < 0.0f) {
            return true;
        } else {
            return false;
        }
    }
}
