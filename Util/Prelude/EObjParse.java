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
import java.util.*;

import Types.*;
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
        this.engine = engine;
        this.cam = cam;
        this.gl = gl;
        this.file = file;
        this.objects = new LinkedList<Types.Geometrie.Obj>();
        this.objectIns = new LinkedList<Types.Geometrie.ObjIns>();
        this.objIns = 0;
        this.textures = new String[6];
        
        parse();
        addObjInsToObj();
    }

    private void addObjInsToObj() {
        for(ObjIns obIns : this.objectIns) {
            this.objects.get(obIns.objNumber).objIns.add(obIns);
        }
    }


    private void parse() {
        FileInputStream input;
        DataInputStream data;
        BufferedReader reader;
        try {
            System.out.println(new File(".").getAbsolutePath());
            input = new FileInputStream("./SuzannTest3.eob");
            data = new DataInputStream(input);
            reader = new BufferedReader(new InputStreamReader(data));

            curLine = reader.readLine();

            while (curLine != null) {
                if (curLine == null) {
                    break;
                }
                if(curLine.charAt(0) == ' ') {
                    continue;
                }
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
                } else {
                    continue;
                }
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

    private void addObj() {
        StringBuffer[] buffer = new StringBuffer[6];
        buffer[0] = new StringBuffer();
        buffer[1] = new StringBuffer();
        buffer[2] = new StringBuffer();
        buffer[3] = new StringBuffer();
        buffer[4] = new StringBuffer();
        buffer[5] = new StringBuffer();
        byte fIdx = 0;
        for (int i = 2; i < curLine.length(); i++) {
            if (curLine.charAt(i) == ' ') {
                fIdx++;
            } else {
                buffer[fIdx].append(curLine.charAt(i));
            }
        }
        String[] objName = new String[6];
        objName[0] = buffer[0].toString();
        objName[1] = buffer[1].toString();
        objName[2] = buffer[2].toString();
        objName[3] = buffer[3].toString();
        objName[4] = buffer[4].toString();
        objName[5] = buffer[5].toString();
        try {
            this.objects.add(new Obj(this.cam, objName, this.objCount, this.gl, this.engine));
        } catch (IOException ex) {
            Logger.getLogger(EObjParse.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.objCount++;
    }   

    private void addObjIns() {
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
        if(this.lastObj == number) {
            this.obj = lastObj;
        } else {
            this.obj++; 
        }
        this.lastObj = number;
        this.objIns += 1;
        
        
        StringBuffer[] buffer = new StringBuffer[7];
        buffer[0] = new StringBuffer();
        buffer[1] = new StringBuffer();
        buffer[2] = new StringBuffer();
        buffer[3] = new StringBuffer();
        buffer[4] = new StringBuffer();
        buffer[5] = new StringBuffer();
        buffer[6] = new StringBuffer();
        byte fIdx = 0;
        for (; i < curLine.length(); i++) {
            if (curLine.charAt(i) == ' ') {
                fIdx++;
            } else {
                buffer[fIdx].append(curLine.charAt(i));
            }
        }
        Types.Geometrie.Vector pos = new Types.Geometrie.Vector(new Float(buffer[0].toString()).floatValue(), new Float(buffer[1].toString()).floatValue(), new Float(buffer[2].toString()).floatValue());
        Types.Geometrie.Vector rot = new Types.Geometrie.Vector(new Float(buffer[3].toString()).floatValue(), new Float(buffer[4].toString()).floatValue(), new Float(buffer[5].toString()).floatValue());
        
        //this.objInsInterator
        
        this.objectIns.add(new ObjIns(this.objects.get(number), pos, rot, this.objIns, this.obj));    
    }

    private void addObjTex() {
        StringBuffer[] buffer = new StringBuffer[7];
        buffer[0] = new StringBuffer();
        buffer[1] = new StringBuffer();
        buffer[2] = new StringBuffer();
        buffer[3] = new StringBuffer();
        buffer[4] = new StringBuffer();
        buffer[5] = new StringBuffer();
        buffer[6] = new StringBuffer();
        byte fIdx = 0;
        for (int i = 3; i < curLine.length(); i++) {
            if (curLine.charAt(i) == ' ') {
                fIdx++;
            } else {
                buffer[fIdx].append(curLine.charAt(i));
            }
        }
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

    private void addPathPoint() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}


