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
import Types.Geometrie.Face;
import Types.Geometrie.FaceForTest;
import Types.Geometrie.Obj;
import Types.Geometrie.ObjIns;
import Types.Geometrie.ObjInsToTest;
import Types.Geometrie.Vector;
import Util.Prelude.JObjParse;
import java.util.LinkedList;
import java.util.Random;

public class EObjInObjCreator {
    private LinkedList<ObjInsToTest> tmpObjInsToTest;
    private LinkedList<ObjIns> createdObjs;
    private static LinkedList<EHullObject> hull;
    private String[] toWrite;
    private Face[][] faces;
    private FaceForTest[][] facesForTest;
    private Vector[] origin;
    private Vector[][] vertex;
    private float[] boundingMax;
    private float[] boundingMin;
    private float objInRadius;
    private int forObj;
    private Obj objParent;

    public static void main(String[] args) {
        int number = new Integer(args[0]).intValue();
        String outFile = args[1];
        String inObj = args[2];
        JObjParse objToPlace = new JObjParse(args[2]);
        objToPlace.makeBoundingSphere();

        //double the radius to make a obj fit
        float objRadius = objToPlace.boudingRadius * 2;

        //check if enought arguments are passed to make the
        //tool run
        if(args.length < 8 && (args.length - 3) % 5 == 0) {
            System.out.println("Error: to few arguments");
            System.exit(0);
        }

        //make hull spheres
        EObjInObjCreator.hull = new LinkedList<EHullObject>();
        for (int i = 3; i < args.length; i += 4) {
            //if -s is read the next for Strings make
            //up the hullobj
            if(args[i].contentEquals(new String("-s"))) {
                EObjInObjCreator.hull.add(new EHullObject(new Vector(new Float(args[i+1]).floatValue(),
                                                         new Float(args[i+2]).floatValue(),
                                                         new Float(args[i+3]).floatValue()),

                                                         new Float(args[i+4]).floatValue() * objRadius,
                                                         new Integer(args[i+5]).intValue()));
            }
        }


    }

    public EObjInObjCreator(String hullObj, String inObj, String outFile, int number, int forObj) {
        JObjParse inObjParse = new JObjParse(inObj);
        this.objInRadius = inObjParse.boudingRadius;
        this.forObj = forObj;
        toWrite = new String[number];
        makeMiddle();
        makeBoundingSphere();
        makeBoundingSphereMin();
        makeFacesForTest();

        createObjIns(number);
        write();
    }

    private void parseHullObj(String[] hull) {
        JObjParse toParse;
        for (int i = 0; i < hull.length; i++) {
            toParse = new JObjParse(hull[i]);
            this.vertex[i] = toParse.getVector();
            this.faces[i] = toParse.getFace();
        }
    }

    private void makeMiddle() {
        this.origin = new Vector[this.vertex.length];
        Vector forSave;
        for (int i = 0; i < this.vertex.length; i++) {
            for (Vector vecIdx : this.vertex[i]) {
                forSave = new Vector();
                forSave.x += (vecIdx.x / this.vertex[i].length);
                forSave.y += (vecIdx.y / this.vertex[i].length);
                forSave.z += (vecIdx.z / this.vertex[i].length);
                this.origin[i] = forSave;
            }
        }
    }

    private void makeFacesForTest() {
        for (int i = 0; i < this.faces.length; i++) {
            this.facesForTest[i] = new FaceForTest[this.faces.length];
            for (int j = 0; j < this.faces[i].length; j++) {
                this.facesForTest[i][j] = new FaceForTest(this.faces[i][j]);
            }
        }
    }

    private void makeBoundingSphere() {
        for (int i = 0; i < this.vertex.length; i++) {
            for (int j = 0; j < this.vertex[i].length; j++) {
                float dis = (float) Math.sqrt(Math.pow(this.vertex[i][j].x - this.origin[j].x, 2) +
                        Math.pow(this.vertex[i][j].y - this.origin[j].y, 2) +
                        Math.pow(this.vertex[i][j].z - this.origin[j].z, 2));
                if (dis > this.boundingMax[i]) {
                    this.boundingMax[i] = dis;
                }
            }
        }
    }

    private void makeBoundingSphereMin() {
        for (int i = 0; i < this.vertex.length; i++) {
            for (int j = 0; j < this.vertex[i].length; j++) {
                float dis = (float) Math.sqrt(Math.pow(this.vertex[i][j].x - this.origin[j].x, 2) +
                        Math.pow(this.vertex[i][j].y - this.origin[j].y, 2) +
                        Math.pow(this.vertex[i][j].z - this.origin[j].z, 2));
                if (dis < this.boundingMin[i]) {
                    this.boundingMin[i] = dis;
                }
            }
        }

    }

    private void createObjIns(int number) {
        this.createdObjs = new LinkedList<ObjIns>();
        for (int i = 0; i < this.boundingMax.length; i++) {
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
        float dis = (float) Math.sqrt(Math.pow(toCheck.origin.x - this.origin[obj].x, 2) +
                Math.pow(toCheck.origin.y - this.origin[obj].y, 2) +
                Math.pow(toCheck.origin.z - this.origin[obj].z, 2));

        if (dis + toCheck.boundSph >= this.boundingMax[obj]) {
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

    private boolean checkSphere(ObjInsToTest toCheck, int obj) {
        float dis = 0f;
        for (FaceForTest face : this.facesForTest[obj]) {
            dis = (float) Math.sqrt(Math.pow(toCheck.origin.x - face.middle.x, 2) +
                    Math.pow(toCheck.origin.y - face.middle.y, 2) +
                    Math.pow(toCheck.origin.z - face.middle.z, 2));

            if (dis - toCheck.boundSph - face.radius <= 0.0f) {
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
