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

    private float distance;
    private static float objInRadius;
    private LinkedList<ObjInsToTest> tmpObjInsToTest;
    private LinkedList<ObjIns> createdObjs;
    private String[] toWrite;
    private static int posNumber;

    public static void main(String[] args) {
        int number = new Integer(args[0]).intValue();
        int times = new Integer(args[1]).intValue();
        EObjInObjCreator.posNumber = times;
        String outFile = args[2];
        String inObj = args[3];
        JObjParse objToPlace = new JObjParse(inObj);
        objToPlace.makeBoundingSphere();
        EObjInObjCreator.objInRadius = objToPlace.boudingRadius;



        EObjInObjCreator make = new EObjInObjCreator(outFile, number, times);


    }

    private void makeConMovements(int times) {
        if(EObjInObjCreator.posNumber == 1) {
            return;
        }
        for (ObjIns toMake : this.createdObjs) {
            toMake.conMove = new Vector[times-1];
            toMake.conMove[0] = VectorUtil.sub(toMake.places[1], toMake.origin);
            for (int i = 1; i < times - 1; i++) {
                toMake.conMove[i] = VectorUtil.sub(toMake.places[i], toMake.places[i - 1]);
                toMake.conMove[i].mult(0.00000001f);
            }
        }
    }

    public EObjInObjCreator(String outFile, int number, int times) {
        this.distance = EObjInObjCreator.objInRadius;
        createObjIns(number, times);
        makeConMovements(times);
        write(outFile);
    }


    private void createObjIns(int number, int times) {
        this.createdObjs = new LinkedList<ObjIns>();
        for (int i = 0; i < times +1; i++) {
            this.tmpObjInsToTest = new LinkedList<ObjInsToTest>();
            for (int j = 0; j < number;) {
                Vector tmpPos = createPos();
                Vector tmpRot = createRot();
                Vector tmpConRot = createConRot();
                ObjInsToTest tmpObjIns = new ObjInsToTest(tmpPos, tmpRot, tmpConRot, j, 0);
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
                for (int k = 0; k < this.tmpObjInsToTest.size(); k++) {
                    if (this.createdObjs.get(k).places == null) {
                        this.createdObjs.get(k).places = new Vector[times];
                        this.createdObjs.get(k).places[0] = new Vector(this.tmpObjInsToTest.get(k).origin);
                    } else {
                        this.createdObjs.get(k).places[i-1] = new Vector(this.tmpObjInsToTest.get(k).origin);
                    }
                }
            }
        }
    }

    private Vector createPos() {
        Random rand = new Random();
        Vector retVec = new Vector(rand.nextFloat() * this.distance * 5,
                rand.nextFloat() * this.distance * 5,
                rand.nextFloat() * this.distance * 5);
                
        boolean pos = rand.nextBoolean();
        if(!pos) {
            retVec.x *= -1;
        }
        pos = rand.nextBoolean();
        if(!pos) {
            retVec.y *= -1;
        }
        pos = rand.nextBoolean();
        if(!pos) {
            retVec.z *= -1;
        }

        return retVec;
    }

    private Vector createRot() {
        Random rand = new Random();
        Vector retVec = new Vector(rand.nextFloat() * 360.0f,
                rand.nextFloat() * 360.0f,
                rand.nextFloat() * 360.0f);

        boolean pos = rand.nextBoolean();
        if(!pos) {
            retVec.x *= -1;
        }
        pos = rand.nextBoolean();
        if(!pos) {
            retVec.y *= -1;
        }
        pos = rand.nextBoolean();
        if(!pos) {
            retVec.z *= -1;
        }

        return retVec;
    }

    private Vector createConRot() {
        Random rand = new Random();
        Vector retVec = new Vector(rand.nextFloat() * 0.0004f,
                rand.nextFloat() * 0.0004f,
                rand.nextFloat() * 0.0004f);

        boolean pos = rand.nextBoolean();
        if(!pos) {
            retVec.x *= -1;
        }
        pos = rand.nextBoolean();
        if(!pos) {
            retVec.y *= -1;
        }
        pos = rand.nextBoolean();
        if(!pos) {
            retVec.z *= -1;
        }

        return retVec;
    }

    private boolean checkObjIns(ObjInsToTest toCheck, int obj) {
        //do all tests
        if (checkObjInsObjIns(toCheck)) {
            return true;
        } else {
            return false;
        }

    }

    private boolean checkObjInsObjIns(ObjInsToTest toCheck) {
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

    private void write(String outFile) {
        int size = this.createdObjs.size();
        this.toWrite = new String[size];
        for (int i = 0; i < size; i++) {
            this.toWrite[i] = this.createdObjs.poll().toString();
        }

        EFileWrite write = new EFileWrite(this.toWrite, outFile);


    }
}
