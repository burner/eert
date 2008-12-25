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

import Types.Geometrie.EHullObject;
import Types.Geometrie.Obj;
import Types.Geometrie.ObjIns;
import Types.Geometrie.ObjInsToTest;
import Types.Geometrie.Vector;
import Util.Geometrie.VectorUtil;
import Util.Prelude.JObjParse;
import java.util.LinkedList;
import java.util.Random;

public class EObjInObjCreator {
    //Infos needed for making
    //all the ObjIns's

    private int numberOfTime;
    private float[] boundingMax;
    private static float objInRadius;
    private LinkedList<ObjInsToTest> tmpObjInsToTest;
    private LinkedList<ObjIns> createdObjs;
    private static LinkedList<EHullObject> hull;
    private String[] toWrite;
    private Obj objParent;

    public static void main(String[] args) {
        int number = new Integer(args[0]).intValue();
        String outFile = args[1];
        String inObj = args[2];
        JObjParse objToPlace = new JObjParse(args[2]);
        objToPlace.makeBoundingSphere();

        //double the radius to make a obj fit
        EObjInObjCreator.objInRadius = objToPlace.boudingRadius * 2;

        //check if enought arguments are passed to make the
        //tool run
        if (args.length < 8 && (args.length - 3) % 5 == 0) {
            System.out.println("Error: to few arguments");
            System.exit(0);
        }

        //make hull spheres
        EObjInObjCreator.hull = new LinkedList<EHullObject>();
        for (int i = 3; i < args.length; i += 4) {
            //if -s is read the next for Strings make
            //up the hullobj
            if (args[i].contentEquals(new String("-s"))) {
                EObjInObjCreator.hull.add(new EHullObject(new Vector(new Float(args[i + 1]).floatValue(),
                        new Float(args[i + 2]).floatValue(),
                        new Float(args[i + 3]).floatValue()),
                        new Float(args[i + 4]).floatValue() * EObjInObjCreator.objInRadius,
                        new Integer(args[i + 5]).intValue()));
            }
        }
        EObjInObjCreator hereComesIt = new EObjInObjCreator(inObj, outFile, number);

    }

    private void makeConMovements() {
        for(ObjIns toMake : this.createdObjs) {
            toMake.conMove[0] = VectorUtil.sub(toMake.places[0], toMake.origin);
            for(int i = 1; i < this.numberOfTime; i++) {
                toMake.conMove[0] = VectorUtil.sub(toMake.places[i], toMake.places[i-1]);
            }
        }
    }

    private void makeInfosNeeded() {
        for (EHullObject toTest : EObjInObjCreator.hull) {
            if (toTest.timeIdx > this.numberOfTime) {
                this.numberOfTime = toTest.timeIdx;
            }
        }
    }

    private void makeBoundingSphereAroundHullSpheres() {
        for (int i = 0; i < this.numberOfTime; i++) {
            for (EHullObject hulls : EObjInObjCreator.hull) {
                if (hulls.timeIdx == i) {
                    float tmpDis = (float) Math.sqrt(Math.pow(hulls.origin.x, 2) +
                            Math.pow(hulls.origin.y, 2) +
                            Math.pow(hulls.origin.z, 2));
                    if (this.boundingMax[i] < tmpDis) {
                        this.boundingMax[i] = tmpDis;
                    }
                }
            }
        }
    }

    public EObjInObjCreator(String hullObj, String outFile, int number) {
        makeInfosNeeded();
        makeBoundingSphereAroundHullSpheres();

        createObjIns(number);
        makeConMovements();
        write();
    }

    private boolean checkSpheres(ObjInsToTest toCheck, int obj) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void createObjIns(int number) {
        this.createdObjs = new LinkedList<ObjIns>();
        for (int i = 0; i < this.numberOfTime; i++) {
            this.tmpObjInsToTest = new LinkedList<ObjInsToTest>();
            for (int j = 0; j < number;) {
                Vector tmpPos = createPos(i);
                Vector tmpRot = createRot();
                Vector tmpConRot = createConRot();
                ObjInsToTest tmpObjIns = new ObjInsToTest(this.objParent, tmpPos, tmpRot, tmpConRot, j, 0);
                if (checkObjIns(tmpObjIns, i)) {
                    this.tmpObjInsToTest.add(tmpObjIns);
                    j++;
                } else {
                    continue;
                }
            }
            //Save all the ObjectInstancePosition

            //if ObjIns List doesn't exists
            if (i == 0) {
                for (int k = 0; k < this.tmpObjInsToTest.size(); k++) {
                    //place the first on at origin
                    this.createdObjs.add(new ObjIns(this.tmpObjInsToTest.get(k).origin,
                            this.tmpObjInsToTest.get(k).rotation,
                            this.tmpObjInsToTest.get(k).conRot,
                            k,
                            0));
                }
            } else {
                //if the ObjIns List exists
                //place all new places at objIns.places
                for(int k = 0; k < this.tmpObjInsToTest.size(); k++) {
                    if(this.createdObjs.get(k).places == null) {
                        this.createdObjs.get(k).places = new Vector[this.numberOfTime];
                        this.createdObjs.get(k).places[i] = new Vector(this.tmpObjInsToTest.get(k).origin);
                    } else {
                        this.createdObjs.get(k).places[i] = new Vector(this.tmpObjInsToTest.get(k).origin);
                    }
                }
            }
        }
    }

    private Vector createPos(int obj) {
        Random rand = new Random();
        Vector retVec = new Vector(rand.nextFloat() * this.boundingMax[obj],
                rand.nextFloat() * this.boundingMax[obj],
                rand.nextFloat() * this.boundingMax[obj]);

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

    private boolean checkObjIns(ObjInsToTest toCheck, int obj) {
        //do all tests
        if (checkObjInsOutside(toCheck, obj)) {
            return false;
        }
        if (checkSpheres(toCheck, obj)) {
            if (checkObjInsObjIns(toCheck, obj)) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean checkObjInsOutside(ObjInsToTest toCheck, int obj) {
        float dis = (float) Math.sqrt(Math.pow(toCheck.origin.x, 2) +
                Math.pow(toCheck.origin.y, 2) +
                Math.pow(toCheck.origin.z, 2));

        if (dis + toCheck.boundSph >= this.boundingMax[obj] && dis - toCheck.boundSph <= -this.boundingMax[obj]) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkObjInsObjIns(ObjInsToTest toCheck, int pos) {
        for (ObjInsToTest forCheck : this.tmpObjInsToTest) {
            float dis = (float) Math.sqrt(Math.pow(toCheck.origin.x - forCheck.origin.x, 2) +
                    Math.pow(toCheck.origin.y - forCheck.origin.y, 2) +
                    Math.pow(toCheck.origin.z - forCheck.origin.z, 2));

            if (dis - toCheck.boundSph - forCheck.boundSph <= 0.0f) {
                return false;
            }
        }
        return true;
    }

    private void write() {
        int size = this.createdObjs.size();
        for (int i = 1; i < size; i++) {
            this.toWrite[i] = this.createdObjs.poll().toString();
        }

    }
}
