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
package Util;

import java.io.*;
import java.util.*;

import Types.*;
import javax.media.opengl.GL;

public class EObjParse {

    private int vecIdx = 0;
    private String file;
    private String curLine;
    private int objCount;
    private int lastObj;
    private int objInsIterator;
    public LinkedList<Obj> objects;
    public LinkedList<ObjIns> objectIns;
    private GL gl;
    private Camera cam;

    public EObjParse(Camera cam, String file, GL gl) {
        this.cam = cam;
        this.gl = gl;
        this.file = file;
        this.objects = new LinkedList<Types.Obj>();
        this.objectIns = new LinkedList<Types.ObjIns>();
        parse();
        addObjInsToObj();
    }

    private void addObjInsToObj() {
        for(ObjIns obIns : this.objectIns) {
            this.objects.get(obIns.number).objIns.add(obIns);
        }
    }

    private void parse() {
        FileInputStream input;
        DataInputStream data;
        BufferedReader reader;
        try {
            System.out.println(new File(".").getAbsolutePath());
            input = new FileInputStream(this.file);
            data = new DataInputStream(input);
            reader = new BufferedReader(new InputStreamReader(data));

            curLine = reader.readLine();

            while (curLine != null) {
                curLine = reader.readLine();
                if (curLine == null) {
                    break;
                }
                if (curLine.charAt(0) == 'o') {
                    if (curLine.charAt(1) == ' ') {
                        addObj();
                    } else if (curLine.charAt(1) == 'i') {
                        addObjIns();
                    }
                } else if (curLine.charAt(0) == 'p') {
                    addPathPoint();
                }
            }
            reader.close();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("File " + this.file + " does not exist!");
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

        this.objects.add(new Obj(this.cam, objName, this.objCount, this.gl));
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
        if(this.lastObj != number) {
            this.objInsIterator = 0;
        } else {
            this.objInsIterator++; 
        }
        this.lastObj = number;
        
        
        StringBuffer[] buffer = new StringBuffer[6];
        buffer[0] = new StringBuffer();
        buffer[1] = new StringBuffer();
        buffer[2] = new StringBuffer();
        buffer[3] = new StringBuffer();
        buffer[4] = new StringBuffer();
        buffer[5] = new StringBuffer();
        byte fIdx = 0;
        for (; i < curLine.length(); i++) {
            if (curLine.charAt(i) == ' ') {
                fIdx++;
            } else {
                buffer[fIdx].append(curLine.charAt(i));
            }
        }
        Types.Vector pos = new Types.Vector(new Float(buffer[0].toString()).floatValue(), new Float(buffer[1].toString()).floatValue(), new Float(buffer[2].toString()).floatValue());
        Types.Vector rot = new Types.Vector(new Float(buffer[3].toString()).floatValue(), new Float(buffer[4].toString()).floatValue(), new Float(buffer[5].toString()).floatValue());
        
        //this.objInsInterator
        
        this.objectIns.add(new ObjIns(this.objects.get(number), pos, rot, this.objInsIterator));    
    }

    private void addPathPoint() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}


