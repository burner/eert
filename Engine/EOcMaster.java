package Engine;

import Types.Obj;
import Types.Vector;
import javax.media.opengl.GL;

public class EOcMaster {

    private Obj[] objs;
    public boolean[] drawn;
    private EOcNode root;
    private Vector middle;
    private float xSize = 0;
    private float ySize = 0;
    private float zSize = 0;

    public EOcMaster(Obj[] allObj) {
        this.objs = allObj;
        makeFirstCubeInfo();
        this.root = new EOcNode(this.objs, this.middle, this.xSize, this.ySize, this.zSize, this.drawn, 0);
    }

    private void makeFirstCubeInfo() {
        this.middle = new Vector();
        for(Obj obj : this.objs) {
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
