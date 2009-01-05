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
package Util.Prelude;

import Engine.Engine;
import Types.Geometrie.Obj;
import Types.Geometrie.ObjIns;
import Util.Logic.Camera;
import Util.*;
import java.io.*;

import Types.*;
import Types.Geometrie.ESkyBox;
import Types.Geometrie.Vector;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;

public class EObjParse {

    private int vecIdx = 0;
    private String file;
    private String curLine;
    private int objCount;
    private int lastObj;
    private int obj;
    private int objIns;
    public LinkedList<Obj> objects;
    public LinkedList<ObjIns> objectIns;
    private GL gl;
    private Camera cam;
    private Engine engine;
    public String[] textures;

    public EObjParse(Camera cam, String file, GL gl, Engine engine) {
        //Save parameter
        this.engine = engine;
        this.cam = cam;
        this.gl = gl;
        this.file = file;

        //Create all needed later
        this.objects = new LinkedList<Types.Geometrie.Obj>();
        this.objectIns = new LinkedList<Types.Geometrie.ObjIns>();
        this.objIns = 0;
        this.textures = new String[6];

        //actually parse the szene File
        parse();

        //add objIns to the corresponding Obj
        addObjInsToObj();
    }


    //walk along all objIns's and add them to the
    //ObjIns LinkedList within the right obj
    private void addObjInsToObj() {
        for (ObjIns obIns : this.objectIns) {
            this.objects.get(obIns.objNumber).objIns.add(obIns);
        }
    }

    private void parse() {
        //needed to read a file
        FileInputStream input;
        DataInputStream data;
        BufferedReader reader;
        try {
            //to know where you are
            System.out.println(new File(".").getAbsolutePath());

            //setup to read the file line by line
            input = new FileInputStream("./Szenes/" + this.file);
            data = new DataInputStream(input);
            reader = new BufferedReader(new InputStreamReader(data));

            //read first line
            curLine = reader.readLine();

            while (curLine != null) {
                //if no new line break
                if (curLine == null) {
                    break;
                }

                //blank line
                if (curLine.charAt(0) == ' ') {
                    curLine = reader.readLine();
                    continue;
                }

                //call the right methode to parse the line
                if (curLine.charAt(0) == 'o') {
                    if (curLine.charAt(1) == ' ') {
                        addObj();
                    } else if (curLine.charAt(1) == 'i') {
                        addObjIns();
                    } else if (curLine.charAt(1) == 't') {
                        addObjTex();
                    }
                } else if (curLine.charAt(0) == 'p') {
                    addPathPoint();
                } else if (curLine.charAt(0) == 's') {
                    addSkyBox();
                } else {
                    continue;
                }

                //read new line for next round
                curLine = reader.readLine();
            }
            reader.close();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("File " + this.file + " does not exist!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Unknown Error");
            e.printStackTrace();
        }

    }

    //parse a new object
    private void addObj() {
        //setup the StringBuffer Array
        //to take up the six filenames
        //for the object
        StringBuffer[] buffer = new StringBuffer[6];
        buffer[0] = new StringBuffer();
        buffer[1] = new StringBuffer();
        buffer[2] = new StringBuffer();
        buffer[3] = new StringBuffer();
        buffer[4] = new StringBuffer();
        buffer[5] = new StringBuffer();

        //do the actually parsing
        //fIdx indecies the StringBuffer Array
        byte fIdx = 0;
        for (int i = 2; i < curLine.length(); i++) {
            if (curLine.charAt(i) == ' ') {
                fIdx++;
            } else {
                buffer[fIdx].append(curLine.charAt(i));
            }
        }

        //make a StringArray out of the buffer
        //to give it to the object
        String[] objName = new String[6];
        objName[0] = buffer[0].toString();
        objName[1] = buffer[1].toString();
        objName[2] = buffer[2].toString();
        objName[3] = buffer[3].toString();
        objName[4] = buffer[4].toString();
        objName[5] = buffer[5].toString();

        //create the object
        try {
            this.objects.add(new Obj(this.cam, objName, this.objCount, this.gl, this.engine));
        } catch (IOException ex) {
            Logger.getLogger(EObjParse.class.getName()).log(Level.SEVERE, null, ex);
        }
        //make note of the created object
        this.objCount++;
    }

    //parse line containing an ObjectInstance
    private void addObjIns() {

        //get the number of the instance
        StringBuffer objNumber = new StringBuffer();
        int i = 3;
        while (curLine.charAt(i) != ' ') {
            objNumber.append(curLine.charAt(i));
            i++;
        }
        i++;

        int number = new Integer(objNumber.toString()).intValue();
        //if number != lastNumber objInsInterator = 0
        //this is done to give the ObjIns a right number 
        if (this.lastObj == number) {
            this.obj = lastObj;
        } else {
            this.obj++;
        }
        this.lastObj = number;
        this.objIns += 1;


        //parse the origin the startrotation and the constatn rotation
        StringBuffer[] buffer = new StringBuffer[9];
        buffer[0] = new StringBuffer();
        buffer[1] = new StringBuffer();
        buffer[2] = new StringBuffer();
        buffer[3] = new StringBuffer();
        buffer[4] = new StringBuffer();
        buffer[5] = new StringBuffer();
        buffer[6] = new StringBuffer();
        buffer[7] = new StringBuffer();
        buffer[8] = new StringBuffer();

        //read origin, start rotation and constant rotation
        //from line
        int fIdx = 0;
        for (; i < curLine.length(); i++) {
            if (fIdx > 8) {
                break;
            }
            if (curLine.charAt(i) == ' ') {
                fIdx++;
            } else {
                buffer[fIdx].append(curLine.charAt(i));
            }
        }

        //Vector for Origin
        Types.Geometrie.Vector pos = new Types.Geometrie.Vector(new Float(buffer[0].toString()).floatValue(),
                new Float(buffer[1].toString()).floatValue(),
                new Float(buffer[2].toString()).floatValue());

        //Vector for start Rotation
        Types.Geometrie.Vector rot = new Types.Geometrie.Vector(new Float(buffer[3].toString()).floatValue(),
                new Float(buffer[4].toString()).floatValue(),
                new Float(buffer[5].toString()).floatValue());

        //Vector for constant Rotation
        Types.Geometrie.Vector conRot = new Types.Geometrie.Vector(new Float(buffer[6].toString()).floatValue(),
                new Float(buffer[7].toString()).floatValue(),
                new Float(buffer[8].toString()).floatValue());


        //tmp Stringbuffer for the three floats
        //needed for one of the Positions
        //the movements are computed later
        //buffers are reused every round
        StringBuffer conMov0 = new StringBuffer();
        StringBuffer conMov1 = new StringBuffer();
        StringBuffer conMov2 = new StringBuffer();

        ArrayList<Vector> conMoveVector = new ArrayList<Vector>(0);

        fIdx = 0;
        for (; i < curLine.length(); i++) {
            //Blank
            if (curLine.charAt(i) == ' ') {
                //maximal three float for a conMov
                if (fIdx == 2) {
                    fIdx = 0;

                    //if conMov2 is full
                    //save new conMove
                    conMoveVector.add(new Vector(new Float(conMov0.toString()).floatValue(),
                            new Float(conMov1.toString()).floatValue(),
                            new Float(conMov2.toString()).floatValue()));

                    conMov0 = new StringBuffer();
                    conMov1 = new StringBuffer();
                    conMov2 = new StringBuffer();
                } else {
                    fIdx++;
                }
            //none Blank
            } else {
                if (fIdx == 0) {
                    conMov0.append(curLine.charAt(i));
                } else if (fIdx == 1) {
                    conMov1.append(curLine.charAt(i));
                } else if (fIdx == 2) {
                    conMov2.append(curLine.charAt(i));
                }
            }
        }
        if (conMov0.length() > 0 && conMov1.length() > 0 && conMov2.length() > 0) {
            conMoveVector.add(new Vector(new Float(conMov0.toString()).floatValue(),
                    new Float(conMov1.toString()).floatValue(),
                    new Float(conMov2.toString()).floatValue()));
        }
        //Copy the LinkedList into an array
        Vector[] conMoveArray = new Vector[conMoveVector.size()];
        conMoveArray = conMoveVector.toArray(conMoveArray);

        this.objectIns.add(new ObjIns(this.objects.get(number), pos, rot, conRot, conMoveArray, this.objIns, this.obj));
    }

    //Parse a line containing texture url
    private void addObjTex() {
        //Buffer
        StringBuffer[] buffer = new StringBuffer[7];
        buffer[0] = new StringBuffer();
        buffer[1] = new StringBuffer();
        buffer[2] = new StringBuffer();
        buffer[3] = new StringBuffer();
        buffer[4] = new StringBuffer();
        buffer[5] = new StringBuffer();
        buffer[6] = new StringBuffer();

        //actually parse the line
        byte fIdx = 0;
        for (int i = 3; i < curLine.length(); i++) {
            if (curLine.charAt(i) == ' ') {
                fIdx++;
            } else {
                buffer[fIdx].append(curLine.charAt(i));
            }
        }

        //Make an String array
        //and copy the StringBuffer into it
        String[] objName = new String[6];
        objName[0] = buffer[0].toString();
        objName[1] = buffer[1].toString();
        objName[2] = buffer[2].toString();
        objName[3] = buffer[3].toString();
        objName[4] = buffer[4].toString();
        objName[5] = buffer[5].toString();
        this.textures[0] = objName[0];
        this.textures[1] = objName[1];
        this.textures[2] = objName[2];
        this.textures[3] = objName[3];
        this.textures[4] = objName[4];
        this.textures[5] = objName[5];
        this.engine.textures = new String[6];
        this.engine.textures[0] = this.textures[0];
        this.engine.textures[1] = this.textures[1];
        this.engine.textures[2] = this.textures[2];
        this.engine.textures[3] = this.textures[3];
        this.engine.textures[4] = this.textures[4];
        this.engine.textures[5] = this.textures[5];
    }

    private void addSkyBox() throws FileNotFoundException {
        //parse the float representing the expanse of the skybox
        StringBuffer distance = new StringBuffer();
        int i;
        for (i = 2; i < curLine.length(); i++) {
            if (curLine.charAt(i) == ' ') {
                break;
            }
            distance.append(curLine.charAt(i));
        }
        float expanse = new Float(distance.toString()).floatValue();

        StringBuffer buffer = new StringBuffer();
                
        i++;
        for (; i < curLine.length(); i++) {
            if (curLine.charAt(i) == ' ') {
                break;
            } else {
                buffer.append(curLine.charAt(i));
            }
        }
        String texName = buffer.toString();

        this.engine.skybox = new ESkyBox(gl, this.engine, this.engine.cam, texName, expanse);

    }

    private void addPathPoint() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}