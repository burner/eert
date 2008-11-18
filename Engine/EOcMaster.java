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
package Engine;

import Types.Obj;
import Types.ObjIns;
import Types.Vector;
import javax.media.opengl.GL;

public class EOcMaster {

    private ObjIns[] objs;
    public boolean[] drawn;
    private EOcNode root;
    private Vector middle;
    private float xSize = 0;
    private float ySize = 0;
    private float zSize = 0;

    public EOcMaster(ObjIns[] allObj) {
        this.objs = allObj;
        makeFirstCubeInfo();
        this.root = new EOcNode(this.objs, this.middle, this.xSize, this.ySize, this.zSize, this.drawn, 0);
    }

    private void makeFirstCubeInfo() {
        this.middle = new Vector();
        for(ObjIns obj : this.objs) {
            this.middle.x += obj.origin.x / this.objs.length;
            this.middle.y += obj.origin.y / this.objs.length;
            this.middle.z += obj.origin.z / this.objs.length;
            
            if(Math.abs(obj.origin.x) > this.xSize)
                this.xSize = Math.abs(obj.origin.x);
                        
            if(Math.abs(obj.origin.y) > this.ySize)
                this.ySize = Math.abs(obj.origin.y);
                        
            if(Math.abs(obj.origin.z) > this.zSize)
                this.zSize = Math.abs(obj.origin.z);
        }
        this.xSize *= 2.0f;
        this.ySize *= 2.0f;
        this.zSize *= 2.0f;
    }
    
    public void drawOctree(GL gl) {
        
    }
}
