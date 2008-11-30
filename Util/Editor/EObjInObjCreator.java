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

import Types.Geometrie.Obj;
import Types.Geometrie.ObjIns;
import Types.Geometrie.Vector;
import Util.Prelude.ObjParse;
import java.util.LinkedList;

public class EObjInObjCreator {
    private LinkedList<ObjIns> createdObjs;
    private Obj hullObj;
    private String[] toWrite;
    public EObjInObjCreator(String hullObj, String outFile, int number) {
        ObjParse obParse = new ObjParse(hullObj);
        toWrite = new String[number+1];
        for(int i = 0; i < number; i++) {
            
        }
    }
    
    private void createObjIns() {
        //todo
    }
    
    private boolean checkObjIns(ObjIns toCheck) {
        return false;
    }
    
    private boolean checkObjInsObj(ObjIns toCheck) {
        return false;
    }
    
    private boolean checkObjInsObjIns(ObjIns toCheck) {
        return false;
    }
    
    private void write() {
        int size = this.createdObjs.size();
        for(int i = 1; i < size; i++) {
            this.toWrite[i] = this.createdObjs.pop().toString();
        }
    }        
           

}