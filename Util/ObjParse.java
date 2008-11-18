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

public class ObjParse {

    private int vecIdx = 0;
    private String file;
    private String curLine;
    private LinkedList<Types.Vector> tempVec;
    private LinkedList<Normal> tempNor;
    private LinkedList<TexCoor> tempTex;
    private LinkedList<Face> tempFac;

    public ObjParse(String file) {
        this.file = file;
        this.tempVec = new LinkedList<Types.Vector>();
        this.tempNor = new LinkedList<Normal>();
        this.tempTex = new LinkedList<TexCoor>();
        this.tempFac = new LinkedList<Face>();
        parse();
    }

    public Types.Vector[] getVec() {
        if(this.tempVec.size() == 0) return null;
        return this.tempVec.toArray(new Types.Vector[0]);
    }

    public Types.Normal[] getNor() {
        if(this.tempNor.size() == 0) return null;
        return this.tempNor.toArray(new Types.Normal[0]);
    }

    public Types.TexCoor[] getTex() {
        if(this.tempTex.size() == 0) return null;
        return this.tempTex.toArray(new Types.TexCoor[0]);
    }

    public Types.Face[] getFace() {
        if(this.tempFac.size() == 0) return null;
        return this.tempFac.toArray(new Types.Face[0]);
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
                if(curLine == null)
                    break;
                if (curLine.charAt(0) == 'v') {
                    if (curLine.charAt(1) == ' ') {
                        addVertex();
                    } else if (curLine.charAt(1) == 'n') {
                        addVertexN();
                    } else if (curLine.charAt(1) == 't') {
                        addVertexTex();
                    }
                } else if (curLine.charAt(0) == 'f') {
                    addFace();
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

    private void addVertex() {
        StringBuffer[] buffer = new StringBuffer[3];
        buffer[0] = new StringBuffer();
        buffer[1] = new StringBuffer();
        buffer[2] = new StringBuffer();
        byte fIdx = 0;
        for (int i = 2; i < curLine.length(); i++) {
            if (curLine.charAt(i) ==?
                ' ') {
                fIdx++;
            } else {
                buffer[fIdx].append(curLine.charAt(i));
            }
        }
        this.tempVec.add(new Types.Vector(new Float(buffer[0].toString()).floatValue(), new Float(buffer[1].toString()).floatValue(), new Float(buffer[2].toString()).floatValue()));
    }

    private void addVertexN() {
        StringBuffer[] buffer = new StringBuffer[3];
        buffer[0] = new StringBuffer();
        buffer[1] = new StringBuffer();
        buffer[2] = new StringBuffer();
        byte fIdx = 0;
        for (int i = 3; i < curLine.length(); i++) {
            if (curLine.charAt(i) == ' ') {
                fIdx++;
            } else {
                buffer[fIdx].append(curLine.charAt(i));
            }
        }
        this.tempNor.add(new Normal(new Float(buffer[0].toString()).floatValue(), new Float(buffer[1].toString()).floatValue(), new Float(buffer[2].toString()).floatValue()));
        this.vecIdx++;
    }

    private void addVertexTex() {
        StringBuffer[] buffer = new StringBuffer[3];
        buffer[0] = new StringBuffer();
        buffer[1] = new StringBuffer();
        buffer[2] = new StringBuffer();
        byte fIdx = 0;
        for (int i = 3; i < curLine.length(); i++) {
            if (curLine.charAt(i) == ' ') {
                fIdx++;
            } else {
                buffer[fIdx].append(curLine.charAt(i));
            }
        }
        this.tempTex.add(new TexCoor(new Float(buffer[0].toString()).floatValue(), new Float(buffer[1].toString()).floatValue(), new Float(buffer[2].toString()).floatValue()));
    }

    private void addFace() {
        StringBuffer[] buf = new StringBuffer[9];
        buf[0] = new StringBuffer();
        buf[1] = new StringBuffer();
        buf[2] = new StringBuffer();
        buf[3] = new StringBuffer();
        buf[4] = new StringBuffer();
        buf[5] = new StringBuffer();
        buf[6] = new StringBuffer();
        buf[7] = new StringBuffer();
        buf[8] = new StringBuffer();

        int fIdx = 0;
        int tmp;
        for (int i = 2; i < curLine.length(); i++) {
            char a = curLine.charAt(i);
            if (curLine.charAt(i) == '/') {
                fIdx++;
            } else if (curLine.charAt(i) == ' ') {
                fIdx++;
                continue;
            } else {
                buf[fIdx].append(curLine.charAt(i));
            }
        }
        int k0;
        int k1;
        int k2;
        int k3;
        int k4;
        int k5;
        int k6;
        int k7;
        int k8;

        if (buf[0].toString().contentEquals("")) {
            k0 = -99;
        } else {
            k0 = Integer.parseInt(buf[0].toString());
        }

        if (buf[1].toString().contentEquals("")) {
            k1 = -99;
        } else {
            k1 = Integer.parseInt(buf[1].toString());
        }
        
        if (buf[2].toString().contentEquals("")) {
            k2 = -99;
        } else {
            k2 = Integer.parseInt(buf[2].toString());
        }

        if (buf[3].toString().contentEquals("")) {
            k3 = -99;
        } else {
            k3 = Integer.parseInt(buf[3].toString());
        }
        
        if (buf[4].toString().contentEquals("")) {
            k4 = -99;
        } else {
            k4 = Integer.parseInt(buf[4].toString());
        }

        if (buf[5].toString().contentEquals("")) {
            k5 = -99;
        } else {
            k5 = Integer.parseInt(buf[5].toString());
        }
        
        if (buf[6].toString().contentEquals("")) {
            k6 = -99;
        } else {
            k6 = Integer.parseInt(buf[6].toString());
        }

        if (buf[7].toString().contentEquals("")) {
            k7 = -99;
        } else {
            k7 = Integer.parseInt(buf[7].toString());
        }
                
        if (buf[8].toString().contentEquals("")) {
            k8 = -99;
        } else {
            k8 = Integer.parseInt(buf[8].toString());
        }

        this.tempFac.add(new Face(k0 -1, k1 -1 , k2 -1, k3 -1, k4 -1, k5 -1, k6 -1, k7 -1, k8 -1));
    }
}
