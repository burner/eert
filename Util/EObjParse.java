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

public class EObjParse {

    private int vecIdx = 0;
    private String file;
    private String curLine;
    private LinkedList<Obj> objects;
    private LinkedList<ObjIns> objectIns;

    public EObjParse(String file) {
        this.file = file;
        this.objects = new LinkedList<Types.Obj>();
        this.objectIns = new LinkedList<Types.ObjIns>();

        parse();
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
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void addObjIns() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void addPathPoint() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}


