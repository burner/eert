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
package Util.Editor;

import Types.Geometrie.Face;
import Types.Geometrie.Obj;
import Types.Geometrie.ObjIns;
import Types.Geometrie.Vector;
import Util.Prelude.ObjParse;
import java.util.LinkedList;
import java.util.Random;

public class EObjInObjCreator {

    private LinkedList<ObjIns> createdObjs;
    private String[] toWrite;
    private Face[] faces;
    private Vector origin;
    private Vector[] vertex;
    private float boundingMax;
    private float boundingMin;
    private int forObj;
    private Obj objParent;

    public EObjInObjCreator(String hullObj, String outFile, int number, int forObj) {
        ObjParse obParse = new ObjParse(hullObj);
        this.forObj = forObj;
        toWrite = new String[number + 1];
        this.origin = makeMiddle();
        this.boundingMax = makeBoundingSphere();
        this.boundingMin = makeBoundingSphereMin();

        createObjIns(number);
    }

    private Vector makeMiddle() {
        Vector originRet = new Vector();
        for (Vector vecIdx : this.vertex) {
            origin.x += (vecIdx.x / this.vertex.length);
            origin.y += (vecIdx.y / this.vertex.length);
            origin.z += (vecIdx.z / this.vertex.length);
        }
        return originRet;
    }

    private float makeBoundingSphere() {
        float ret = 0.0f;
        for (int i = 0; i < this.vertex.length; i++) {
            Vector tmpVec = this.vertex[i];
            float dis = (float) Math.sqrt(Math.pow(tmpVec.x, 2) + Math.pow(tmpVec.y, 2) + Math.pow(tmpVec.z, 2));
            if (dis > this.boundingMax) {
                ret = dis;
            }
        }
        return ret;
    }

    private float makeBoundingSphereMin() {
        float ret = 0.0f;
        for (int i = 0; i < this.vertex.length; i++) {
            Vector tmpVec = this.vertex[i];
            float dis = (float) Math.sqrt(Math.pow(tmpVec.x, 2) + Math.pow(tmpVec.y, 2) + Math.pow(tmpVec.z, 2));
            if (dis < this.boundingMax) {
                ret = dis;
            }
        }
        return ret;
    }

    private void createObjIns(int number) {
        for (int i = 0; i < number;) {
            Vector tmpPos = createPos();
            Vector tmpRot = createRot();
            Vector tmpConRot = createConRot();
            ObjIns tmpObjIns = new ObjIns(this.objParent, tmpPos, tmpRot, tmpConRot, i, 0);
            if (checkObjIns(tmpObjIns)) {
                this.createdObjs.add(tmpObjIns);
                i++;
            } else {
                continue;
            }
        }
    }

    private Vector createPos() {
        Random rand = new Random();
        Vector retVec = new Vector(rand.nextFloat() * this.boundingMax,
                rand.nextFloat() * this.boundingMax,
                rand.nextFloat() * this.boundingMax);

        return retVec;
    }

    private Vector createRot() {
        Random rand = new Random();
        Vector retVec = new Vector(rand.nextFloat() * 360.0f,
                rand.nextFloat() * 360.0f,
                rand.nextFloat() * 360.0f);

        return retVec;
    }

    private Vector createConRot() {
        Random rand = new Random();
        Vector retVec = new Vector(rand.nextFloat() * 20.0f,
                rand.nextFloat() * 20.0f,
                rand.nextFloat() * 20.0f);

        return retVec;
    }

    private boolean checkObjIns(ObjIns toCheck) {
        //do all tests
        if (checkObjInsOutside(toCheck)) {
            return false;
        }
        if (checkObjInsInside(toCheck)) {
            return true;
        }
        if (checkObjInsObj(toCheck)) {
            if (checkObjInsObjIns(toCheck)) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean checkObjInsOutside(ObjIns toCheck) {
        float dis = (float) Math.sqrt(Math.pow(toCheck.origin.x - this.origin.x, 2) +
                Math.pow(toCheck.origin.y - this.origin.y, 2) +
                Math.pow(toCheck.origin.z - this.origin.z, 2));

        if (dis + toCheck.boundSph >= this.boundingMax) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkObjInsInside(ObjIns toCheck) {
        float dis = (float) Math.sqrt(Math.pow(toCheck.origin.x - this.origin.x, 2) +
                Math.pow(toCheck.origin.y - this.origin.y, 2) +
                Math.pow(toCheck.origin.z - this.origin.z, 2));

        if (dis + toCheck.boundSph <= this.boundingMin) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkObjInsObj(ObjIns toCheck) {
        for (Face face : this.faces) {
            if (!checkSphereTri(toCheck, face.v1, face.v2, face.v3)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkObjInsObjIns(ObjIns toCheck) {
        for (ObjIns forCheck : this.createdObjs) {
            float dis = (float) Math.sqrt(Math.pow(toCheck.origin.x - forCheck.origin.x, 2) +
                    Math.pow(toCheck.origin.y - forCheck.origin.y, 2) +
                    Math.pow(toCheck.origin.z - forCheck.origin.z, 2));

            if (dis - toCheck.boundSph - forCheck.boundSph <= 0.0f) {
                return false;
            }
        }
        return true;
    }

    private boolean checkSphereTri(ObjIns toCheck, int vec1, int vec2, int vec3) {
        Vector place = this.vertex[vec1];
        //This should make a correct normal standing on the three vector's 
        //faces outward
        Vector normal = new Vector().getCrossProdR(this.vertex[vec1], this.vertex[vec2]);
        normal.getCrossProd(this.vertex[vec3]);
        normal.normalize();

        //get max vector to plane direction
        //from ObjIns origin
        normal.mult(toCheck.boundSph);
        Vector maxVec = new Vector(toCheck.origin.addR(normal));

        //is should give us the distance to the plane
        float div = (float) Math.sqrt(Math.pow(normal.x, 2) + Math.pow(normal.y, 2) + Math.pow(normal.z, 2));
        Vector pointPlace = new Vector(this.origin.x - place.x,
                this.origin.y - place.y,
                this.origin.z - place.z);
        float dis = pointPlace.x * normal.x + pointPlace.y * normal.y + pointPlace.z * normal.z;
        dis /= div;

        //now check if dis - boundingSphere of objToCheck is greater than zero
        if (dis - toCheck.boundSph < 0.0f || dis < 0.0f) {
            return false;
        }

        //point divided by normal must be positv
        if (dis < 0.0f) {
            return false;
        }

        Vector point1 = this.vertex[vec1];
        Vector point2 = this.vertex[vec2];
        Vector point3 = this.vertex[vec3];

        //check if point is outside triangle

        Vector middle = new Vector((point1.x + point2.x + point3.x) / 3,
                (point1.y + point2.y + point3.y) / 3,
                (point1.z + point2.z + point3.z) / 3);

        float triangleRadius = 0.0f;
        float tmp;
        for (int i = 0; i < 3; i++) {
            tmp = (float) Math.sqrt(Math.pow(point1.x - middle.x, 2) +
                    Math.pow(point1.y - middle.y, 2) +
                    Math.pow(point1.z - middle.z, 2));
            if (tmp > triangleRadius) {
                triangleRadius = tmp;
            }
        }

        //middle of triangle to middle of objIns
        //if negativ return false;
        float disMiddle = (float) Math.sqrt(Math.pow(toCheck.origin.x - middle.x, 2) +
                Math.pow(toCheck.origin.y - middle.y, 2) +
                Math.pow(toCheck.origin.z - middle.z, 2));

        if(disMiddle - triangleRadius < 0.0f) {
            return false;
        }

        //All checks passed
        return true;
    }

    private void write() {
        int size = this.createdObjs.size();
        for (int i = 1; i < size; i++) {
            this.toWrite[i] = this.createdObjs.poll().toString();
        }
    }
}
